package ua.birthdays.app.ui.swing.features;

import com.toedter.calendar.JDateChooser;
import ua.birthdays.app.dao.env.PeriodTimeEnum;
import ua.birthdays.app.domain.impl.MainFeaturesServiceImpl;
import ua.birthdays.app.domain.interfaces.MainFeaturesService;
import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;
import ua.birthdays.app.ui.swing.util.UtilFormImpl;

import javax.swing.*;
import java.awt.*;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class EditUserFriendBirthdayView extends JDialog {
    private JButton cancelButton;
    private JButton confirmButton;
    private JTextField textFieldFriendName;
    private JTextField textFieldRemindingHour;
    private JTextField textFieldRemindingMinutes;
    private JTextField textFieldRemindingCountDaysBeforeBirthday;
    private JComboBox comboBoxPeriodTime;
    private JPanel jPicker;
    private JPanel panelEditUserFriendBirthday;

    private Calendar calendar = Calendar.getInstance();
    private final JDateChooser jDateChooser = new JDateChooser(calendar.getTime());

    public EditUserFriendBirthdayView(UserFriendsData userFriendsData) {
        setUndecorated(true);
        setContentPane(panelEditUserFriendBirthday);

        setMinimumSize(new Dimension(580, 500));

        jDateChooser.setDateFormatString("yyyy-MM-dd");
        jPicker.add(jDateChooser);

        initFieldsForm(userFriendsData);

        confirmButton.addActionListener(e -> {
            editUserFriendBirthday(userFriendsData);
        });

        cancelButton.addActionListener(e -> {
            dispose();
            new ListUserFriendBirthdaysView(userFriendsData.getUser());
        });

        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    private void initFieldsForm(UserFriendsData userFriendsData) {
        textFieldFriendName.setText(userFriendsData.getAboutFriend().getNameFriend());
        textFieldRemindingHour.setText(String.valueOf(userFriendsData.getFriendBirthdayDate().getRemindedFriendHour()));
        textFieldRemindingMinutes.setText(String.valueOf(userFriendsData.getFriendBirthdayDate().getRemindedFriendMinutes()));
        textFieldRemindingCountDaysBeforeBirthday.setText(String.valueOf(userFriendsData.getFriendBirthdayDate().getRemindedCountDaysBeforeBirthday()));
        comboBoxPeriodTime.setSelectedItem(userFriendsData.getFriendBirthdayDate().getPeriodTimeEnum());
        jDateChooser.setDate(Date.from(userFriendsData.getFriendBirthdayDate().getFriendDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    private void editUserFriendBirthday(UserFriendsData userFriendsData) {
        MainFeaturesService mainFeaturesService = new MainFeaturesServiceImpl();
        if (jDateChooser.getDate().toInstant().atZone(
                ZoneId.systemDefault()).toLocalDate().isEqual(userFriendsData.getFriendBirthdayDate().getFriendDate()) &&
                textFieldFriendName.getText().equals(userFriendsData.getAboutFriend().getNameFriend()) &&
                Integer.parseInt(textFieldRemindingHour.getText()) == userFriendsData.getFriendBirthdayDate().getRemindedFriendHour() &&
                Integer.parseInt(textFieldRemindingMinutes.getText()) == userFriendsData.getFriendBirthdayDate().getRemindedFriendMinutes() &&
                Integer.parseInt(textFieldRemindingCountDaysBeforeBirthday.getText()) == userFriendsData.getFriendBirthdayDate().getRemindedCountDaysBeforeBirthday()
        ) {
            JOptionPane.showMessageDialog(this,
                    "You not edit any parameter. Edit one of them. Or cancel operation.",
                    "Try again",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (textFieldFriendName.getText().isEmpty() ||
                textFieldRemindingHour.getText().isEmpty() ||
                textFieldRemindingMinutes.getText().isEmpty() ||
                textFieldRemindingCountDaysBeforeBirthday.getText().isEmpty()
        ) {
            JOptionPane.showMessageDialog(this,
                    "Please full all fields!",
                    "Try again",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!UtilFormImpl.isNumber(textFieldRemindingHour.getText()) ||
                !UtilFormImpl.isNumber(textFieldRemindingMinutes.getText()) ||
                !UtilFormImpl.isNumber(textFieldRemindingCountDaysBeforeBirthday.getText())
        ) {
            JOptionPane.showMessageDialog(this,
                    "Please remove letters from fields where you have to write numbers!",
                    "Try again",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int remindingHour = Integer.parseInt(textFieldRemindingHour.getText());
        int remindingMinutes = Integer.parseInt(textFieldRemindingMinutes.getText());
        int remindingCountDaysBeforeBirthday = Integer.parseInt(textFieldRemindingCountDaysBeforeBirthday.getText());

        if (remindingHour < 0 || remindingHour > 12) {
            JOptionPane.showMessageDialog(this,
                    "Hour can not be upper than 12 and less 0!",
                    "Try again",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (remindingMinutes < 0 || remindingMinutes > 59) {
            JOptionPane.showMessageDialog(this,
                    "Minutes can not be upper than 59 and less 0!",
                    "Try again",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (remindingCountDaysBeforeBirthday < 0 || remindingCountDaysBeforeBirthday > 10) {
            JOptionPane.showMessageDialog(this,
                    "Reminding Count Days Before Birthday can not be upper than 10 and less 0!",
                    "Try again",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!mainFeaturesService.updateUserFriendsData(userFriendsData,
                new UserFriendsData(
                        new FriendBirthdayDate(
                                jDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                Integer.parseInt(textFieldRemindingHour.getText()),
                                Integer.parseInt(textFieldRemindingMinutes.getText()),
                                PeriodTimeEnum.valueOf((String) comboBoxPeriodTime.getSelectedItem()),
                                Integer.parseInt(textFieldRemindingCountDaysBeforeBirthday.getText())
                        ),
                        new AboutFriend(textFieldFriendName.getText()),
                        userFriendsData.getUser()
                )
        )) {
            JOptionPane.showMessageDialog(this,
                    "Editing select User Friend Birthday was unsuccessful!",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
        new ListUserFriendBirthdaysView(userFriendsData.getUser());
    }
}
