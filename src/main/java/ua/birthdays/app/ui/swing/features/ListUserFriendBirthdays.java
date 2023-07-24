package ua.birthdays.app.ui.swing.features;

import ua.birthdays.app.ui.swing.auth.RegistrationView;
import ua.birthdays.app.ui.swing.menuview.HomeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListUserFriendBirthdays extends JDialog {
    private JPanel panelListFriendBirthdays;
    private JTable table;

    public ListUserFriendBirthdays() {
        setUndecorated(true);
        setContentPane(panelListFriendBirthdays);
        setMinimumSize(new Dimension(480, 300));

        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        setVisible(true);
    }

    private void createTable() {
        Object[][] data = null;

        //get data


        DefaultTableModel defaultTableModel = new DefaultTableModel(
                data, new String[]{"ID", "Title", "Note"}
        );

        table.setModel(defaultTableModel);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//        chooseButton.addActionListener(e -> {
//            if (table.getSelectedRow() != -1) {
//                dispose();
//
//            }
//        });
//
//        buttonRemoveNote.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (table.getSelectedRow() != -1) {
//                    dispose();
//
//                }
//            }
//        });
    }

}
