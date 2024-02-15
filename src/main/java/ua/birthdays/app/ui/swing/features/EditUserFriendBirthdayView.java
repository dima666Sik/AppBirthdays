package ua.birthdays.app.ui.swing.features;

import com.toedter.calendar.JDateChooser;
import ua.birthdays.app.dao.env.PeriodTimeEnum;
import ua.birthdays.app.domain.impl.UserFriendsDataServiceImpl;
import ua.birthdays.app.domain.UserFriendsDataService;
import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.UserFriendsData;
import ua.birthdays.app.ui.swing.util.CheckValidDataForm;
import ua.birthdays.app.ui.swing.util.ConstantPhrases;
import ua.birthdays.app.ui.swing.util.UtilForm;

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

    private final Calendar calendar = Calendar.getInstance();
    private final JDateChooser jDateChooser = new JDateChooser(calendar.getTime());

    public EditUserFriendBirthdayView(UserFriendsData userFriendsData) {
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
        comboBoxPeriodTime.setSelectedItem(userFriendsData.getFriendBirthdayDate().getPeriodTimeEnum().name());
        jDateChooser.setDate(Date.from(userFriendsData.getFriendBirthdayDate().getFriendDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    private void editUserFriendBirthday(UserFriendsData userFriendsData) {
        UserFriendsDataService userFriendsDataService = new UserFriendsDataServiceImpl();
        if (jDateChooser.getDate().toInstant().atZone(
                ZoneId.systemDefault()).toLocalDate().isEqual(userFriendsData.getFriendBirthdayDate().getFriendDate()) &&
                textFieldFriendName.getText().equals(userFriendsData.getAboutFriend().getNameFriend()) &&
                Integer.parseInt(textFieldRemindingHour.getText()) == userFriendsData.getFriendBirthdayDate().getRemindedFriendHour() &&
                Integer.parseInt(textFieldRemindingMinutes.getText()) == userFriendsData.getFriendBirthdayDate().getRemindedFriendMinutes() &&
                Integer.parseInt(textFieldRemindingCountDaysBeforeBirthday.getText()) == userFriendsData.getFriendBirthdayDate().getRemindedCountDaysBeforeBirthday()
        ) {
            JOptionPane.showMessageDialog(this,
                    "You not edit any parameter. Edit one of them. Or cancel operation.",
                    ConstantPhrases.TRY_AGAIN_MESSAGE,
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (CheckValidDataForm.isFieldsEmpty(this, textFieldFriendName.getText(),
                textFieldRemindingHour.getText(),
                textFieldRemindingMinutes.getText(),
                textFieldRemindingCountDaysBeforeBirthday.getText()))
            return;

        if (!CheckValidDataForm.isFieldsNumber(this,
                textFieldRemindingHour.getText(),
                textFieldRemindingMinutes.getText(),
                textFieldRemindingCountDaysBeforeBirthday.getText()))
            return;

        int remindingHour = Integer.parseInt(textFieldRemindingHour.getText());
        int remindingMinutes = Integer.parseInt(textFieldRemindingMinutes.getText());
        int remindingCountDaysBeforeBirthday = Integer.parseInt(textFieldRemindingCountDaysBeforeBirthday.getText());

        if(!CheckValidDataForm.isCheckReminderDistance(this, remindingHour,
                0, 12,
                ConstantPhrases.HOUR_ERROR_MESSAGE)) return;

        if(!CheckValidDataForm.isCheckReminderDistance(this, remindingMinutes,
                0, 59,
                ConstantPhrases.MINUTES_ERROR_MESSAGE)) return;

        if(!CheckValidDataForm.isCheckReminderDistance(this, remindingCountDaysBeforeBirthday,
                0, 10,
                ConstantPhrases.REMINDING_COUNT_DAYS_ERROR_MESSAGE)) return;

        if (!userFriendsDataService.updateUserFriendsData(userFriendsData,
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
                    ConstantPhrases.TRY_AGAIN_MESSAGE,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }
}
