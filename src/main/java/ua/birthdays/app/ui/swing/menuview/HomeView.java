package ua.birthdays.app.ui.swing.menuview;

import ua.birthdays.app.ui.swing.auth.AuthorizationView;
import ua.birthdays.app.ui.swing.auth.RegistrationView;

import javax.swing.*;
import java.awt.*;

public class HomeView extends JDialog{
    private JPanel panelHomeView;
    private JButton auth;
    private JButton register;
    private JButton exit;

    public HomeView() {
        setUndecorated(true);
        setContentPane(panelHomeView);
        setMinimumSize(new Dimension(520, 300));

        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        exit.addActionListener(e -> dispose());
        auth.addActionListener(e -> {
            dispose();
//            new AuthorizationForm();
            new AuthorizationView();
        });
        register.addActionListener(e -> {
            dispose();
//            new RegistrationUserForm();
            new RegistrationView();
        });
        setVisible(true);
    }
}
