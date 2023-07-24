package ua.birthdays.app.ui.swing.menuview;

import ua.birthdays.app.ui.swing.auth.AuthorizationView;

import javax.swing.*;
import java.awt.*;

public class UserMainMenuView extends JDialog {
    private JPanel panelUserMainMenu;
    private JButton checkListBirthdaysButton;
    private JButton addNewFriendBirthdayButton;
    private JButton exitAnAccountButton;

    public UserMainMenuView() {
        setUndecorated(true);
        setContentPane(panelUserMainMenu);
        setMinimumSize(new Dimension(480, 300));

        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        checkListBirthdaysButton.addActionListener(e -> {
            dispose();
//            new RegistrationView();
        });

        addNewFriendBirthdayButton.addActionListener(e -> {
            dispose();
//            new RegistrationView();
        });

        exitAnAccountButton.addActionListener(e -> {
            dispose();
            new AuthorizationView();
        });
        setVisible(true);
    }
}
