package ua.birthdays.app.ui.swing.features;

import com.toedter.calendar.JDateChooser;
import ua.birthdays.app.dao.env.PeriodTimeEnum;
import ua.birthdays.app.service.impl.UserFriendsDataServiceImpl;
import ua.birthdays.app.service.UserFriendsDataService;
import ua.birthdays.app.model.AboutFriend;
import ua.birthdays.app.model.FriendBirthdayDate;
import ua.birthdays.app.model.User;
import ua.birthdays.app.model.UserFriendsData;
import ua.birthdays.app.ui.swing.util.CheckValidDataForm;
import ua.birthdays.app.ui.swing.util.ConstantPhrases;

import javax.swing.*;
import java.awt.*;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class CreateUserFriendBirthdayView extends JDialog {
    private JPanel panelCreateUserFriendBirthday;
    private JButton cancelButton;
    private JButton confirmButton;
    private JTextField textFieldFriendName;
    private JPanel jPicker;
    private JTextField textFieldRemindingHour;
    private JTextField textFieldRemindingMinutes;
    private JTextField textFieldRemindingCountDaysBeforeBirthday;
    private JComboBox<String> comboBoxPeriodTime;
    private final Calendar calendar = Calendar.getInstance();
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

    public CreateUserFriendBirthdayView(UserFriendsData userFriendsData) {
        setContentPane(panelCreateUserFriendBirthday);

        setMinimumSize(new Dimension(580, 500));

        jDateChooser.setDateFormatString("yyyy-MM-dd");
        jPicker.add(jDateChooser);

        initFieldsForm(userFriendsData);

        confirmButton.addActionListener(e -> createUserFriendBirthday(userFriendsData.getUser()));

        cancelButton.addActionListener(e -> dispose());

        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    private void initFieldsForm(UserFriendsData userFriendsData) {
        textFieldFriendName.setText(userFriendsData.getAboutFriend()
                                                   .getNameFriend());
        textFieldRemindingHour.setText(String.valueOf(userFriendsData.getFriendBirthdayDate()
                                                                     .getRemindedFriendHour()));
        textFieldRemindingMinutes.setText(String.valueOf(userFriendsData.getFriendBirthdayDate()
                                                                        .getRemindedFriendMinutes()));
        textFieldRemindingCountDaysBeforeBirthday.setText(String.valueOf(userFriendsData.getFriendBirthdayDate()
                                                                                        .getRemindedCountDaysBeforeBirthday()));
        comboBoxPeriodTime.setSelectedItem(userFriendsData.getFriendBirthdayDate()
                                                          .getPeriodTimeEnum()
                                                          .name());
        jDateChooser.setDate(Date.from(userFriendsData.getFriendBirthdayDate()
                                                      .getFriendDate()
                                                      .atStartOfDay(ZoneId.systemDefault())
                                                      .toInstant()));
    }

    private void createUserFriendBirthday(User user) {
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

        if (!CheckValidDataForm.isCheckReminderDistance(this, remindingHour,
                0, 12,
                ConstantPhrases.HOUR_ERROR_MESSAGE)) return;

        if (!CheckValidDataForm.isCheckReminderDistance(this, remindingMinutes,
                0, 59,
                ConstantPhrases.MINUTES_ERROR_MESSAGE)) return;

        if (!CheckValidDataForm.isCheckReminderDistance(this, remindingCountDaysBeforeBirthday,
                0, 10,
                ConstantPhrases.REMINDING_COUNT_DAYS_ERROR_MESSAGE)) return;


        UserFriendsDataService userFriendsDataService = new UserFriendsDataServiceImpl();
        if (!userFriendsDataService.createUserFriendsData(new UserFriendsData(
                new FriendBirthdayDate(
                        jDateChooser.getDate()
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate(),
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
                    ConstantPhrases.TRY_AGAIN_MESSAGE,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

}
