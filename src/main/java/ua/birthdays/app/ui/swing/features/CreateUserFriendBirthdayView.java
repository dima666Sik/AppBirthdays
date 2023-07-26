package ua.birthdays.app.ui.swing.features;

import com.toedter.calendar.JDateChooser;
import ua.birthdays.app.domain.impl.MainFeaturesServiceImpl;
import ua.birthdays.app.domain.interfaces.MainFeaturesService;
import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;
import ua.birthdays.app.ui.swing.menuview.HomeView;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
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
    private Calendar calendar = Calendar.getInstance();
    private final JDateChooser jDateChooser = new JDateChooser(calendar.getTime());

    public CreateUserFriendBirthdayView(User user) {
        setUndecorated(true);
        setContentPane(panelCreateUserFriendBirthday);

        setMinimumSize(new Dimension(580, 500));

        jDateChooser.setDateFormatString("yyyy-MM-dd");
        jPicker.add(jDateChooser);

        confirmButton.addActionListener(e -> {
            dispose();
            if (createUserFriendBirthday(user))
                new ListUserFriendBirthdaysView(user);
            else {
                new ListUserFriendBirthdaysView(user);
                JOptionPane.showMessageDialog(this,
                        "Creating User Friend Birthday was unsuccessful!",
                        "Try again",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            dispose();
            new ListUserFriendBirthdaysView(user);
        });

        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    private boolean createUserFriendBirthday(User user) {
        MainFeaturesService mainFeaturesService = new MainFeaturesServiceImpl();
        return mainFeaturesService.createUserFriendsData(new UserFriendsData(
                new FriendBirthdayDate(
                        jDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        Integer.parseInt(textFieldRemindingHour.getText()),
                        Integer.parseInt(textFieldRemindingMinutes.getText()),
                        Integer.parseInt(textFieldRemindingCountDaysBeforeBirthday.getText())
                ),
                new AboutFriend(
                        textFieldFriendName.getText()
                ),
                user
        ));
    }


}
