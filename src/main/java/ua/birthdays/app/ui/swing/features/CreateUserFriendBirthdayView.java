package ua.birthdays.app.ui.swing.features;

import com.toedter.calendar.JDateChooser;
import ua.birthdays.app.dao.env.PeriodTimeEnum;
import ua.birthdays.app.domain.impl.MainFeaturesServiceImpl;
import ua.birthdays.app.domain.interfaces.MainFeaturesService;
import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;
import ua.birthdays.app.ui.swing.util.UtilForm;

import javax.swing.*;
import java.awt.*;
import java.time.ZoneId;
import java.util.Calendar;

public class CreateUserFriendBirthdayView extends JDialog {
    private JPanel panelCreateUserFriendBirthday;
    private JButton cancelButton;
    private JButton confirmButton;
    private JTextField textFieldFriendName;
    private JPanel jPicker;
    private JTextField textFieldRemindingHour;
    private JTextField textFieldRemindingMinutes;
    private JTextField textFieldRemindingCountDaysBeforeBirthday;
    private JComboBox comboBoxPeriodTime;
    private Calendar calendar = Calendar.getInstance();
    private final JDateChooser jDateChooser = new JDateChooser(calendar.getTime());

    public CreateUserFriendBirthdayView(User user) {
        setContentPane(panelCreateUserFriendBirthday);

        setMinimumSize(new Dimension(580, 500));

        jDateChooser.setDateFormatString("yyyy-MM-dd");
        jPicker.add(jDateChooser);

        confirmButton.addActionListener(e -> createUserFriendBirthday(user));

        cancelButton.addActionListener(e -> dispose());

        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    private void createUserFriendBirthday(User user) {
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

        if (!UtilForm.isNumber(textFieldRemindingHour.getText()) ||
                !UtilForm.isNumber(textFieldRemindingMinutes.getText()) ||
                !UtilForm.isNumber(textFieldRemindingCountDaysBeforeBirthday.getText())
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

        MainFeaturesService mainFeaturesService = new MainFeaturesServiceImpl();
        if (!mainFeaturesService.createUserFriendsData(new UserFriendsData(
                new FriendBirthdayDate(
                        jDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        Integer.parseInt(textFieldRemindingHour.getText()),
                        Integer.parseInt(textFieldRemindingMinutes.getText()),
                        PeriodTimeEnum.valueOf((String) comboBoxPeriodTime.getSelectedItem()),
                        Integer.parseInt(textFieldRemindingCountDaysBeforeBirthday.getText())
                ),
                new AboutFriend(
                        textFieldFriendName.getText()
                ),
                user
        ))) {
            JOptionPane.showMessageDialog(this,
                    "Creating User Friend Birthday was unsuccessful! Please check name friend and date birthday, maybe it's exist in table... ",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

}
