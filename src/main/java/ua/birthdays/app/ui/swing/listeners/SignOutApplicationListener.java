package ua.birthdays.app.ui.swing.listeners;

import ua.birthdays.app.dao.util.DBConnector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignOutApplicationListener implements ActionListener {
    private final JDialog dialog;

    public SignOutApplicationListener(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.dispose();
        DBConnector.closePool();
    }
}
