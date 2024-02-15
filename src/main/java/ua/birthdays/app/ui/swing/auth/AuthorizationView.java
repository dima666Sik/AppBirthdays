package ua.birthdays.app.ui.swing.auth;

import ua.birthdays.app.domain.impl.AuthServiceImpl;
import ua.birthdays.app.domain.AuthService;
import ua.birthdays.app.models.User;
import ua.birthdays.app.ui.swing.features.ListUserFriendBirthdaysView;
import ua.birthdays.app.ui.swing.menuview.HomeView;
import ua.birthdays.app.ui.swing.util.ConstantPhrases;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class AuthorizationView extends JDialog {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton previousButton;
    private JButton registrationButton;
    private JButton confirmAuthorizationButton;
    private JPanel panelAuthorization;

    public AuthorizationView() {
        setUndecorated(true);
        setContentPane(panelAuthorization);
        setMinimumSize(new Dimension(480, 300));

        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        previousButton.addActionListener(e -> {
            dispose();
            new HomeView();

        });

        confirmAuthorizationButton.addActionListener(e -> authorization());

        registrationButton.addActionListener(e -> {
            dispose();
            new RegistrationView();
        });
        setVisible(true);
    }

    private void authorization() {
        if (emailField.getText()
                      .isEmpty() ||
                passwordField.getPassword().length == 0
        ) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all fields...",
                    ConstantPhrases.TRY_AGAIN_MESSAGE,
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (passwordField.getPassword().length < 8) {
            JOptionPane.showMessageDialog(this,
                    "Password less 8 symbols.",
                    ConstantPhrases.TRY_AGAIN_MESSAGE,
                    JOptionPane.WARNING_MESSAGE);
            return;

        }

        AuthService authService = new AuthServiceImpl();
        Optional<User> user = authService.authorization(emailField.getText(), passwordField.getPassword());

        if (user.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Authorization not successful!",
                    ConstantPhrases.TRY_AGAIN_MESSAGE,
                    JOptionPane.WARNING_MESSAGE);

            clearFieldsForm();
            return;
        }

        dispose();
        new ListUserFriendBirthdaysView(user.get());
    }

    private void clearFieldsForm() {
        emailField.setText("");
        passwordField.setText("");
    }
}
