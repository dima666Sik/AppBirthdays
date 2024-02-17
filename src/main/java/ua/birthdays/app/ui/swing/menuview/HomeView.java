package ua.birthdays.app.ui.swing.menuview;

import ua.birthdays.app.ui.swing.auth.AuthorizationView;
import ua.birthdays.app.ui.swing.auth.RegistrationView;
import ua.birthdays.app.ui.swing.listeners.SignOutApplicationListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HomeView extends JDialog {
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
        exit.addActionListener(new SignOutApplicationListener(this));
        auth.addActionListener(e -> {
            dispose();
            new AuthorizationView();
        });
        register.addActionListener(e -> {
            dispose();
            new RegistrationView();
        });
        setVisible(true);
    }
}
