package ua.birthdays.app.ui.swing.features;

import ua.birthdays.app.domain.impl.MainFeaturesServiceImpl;
import ua.birthdays.app.domain.interfaces.MainFeaturesService;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;
import ua.birthdays.app.ui.swing.menuview.HomeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListUserFriendBirthdaysView extends JDialog {
    private final static int COUNT_COLUMN = 3;

    private JPanel panelListFriendBirthdays;
    private JTable table;
    private JButton ascendingButton;
    private JButton descendingButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton exitButton;
    private JButton editButton;
    private JButton defaultButton;

    public ListUserFriendBirthdaysView(User user) {
        setUndecorated(true);
        setContentPane(panelListFriendBirthdays);
        createTable(user);
        setMinimumSize(new Dimension(580, 500));

        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    private void createTable(User user) {
        //get data
        MainFeaturesService mainFeaturesService = new MainFeaturesServiceImpl();

        List<UserFriendsData> userFriendsDataList = mainFeaturesService.readAllUserFriendsData(user);

        Object[][] data = dataTableModelInit(userFriendsDataList);

        setDataTableModel(data);

        ascendingButton.addActionListener(e -> {

        });

        descendingButton.addActionListener(e -> {

        });

        defaultButton.addActionListener(e -> {

        });

        editButton.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                dispose();

            }
        });

        deleteButton.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                dispose();

            }
        });

        addButton.addActionListener(e -> {
            dispose();
            new CreateUserFriendBirthdayView(user);
        });

        exitButton.addActionListener(e -> {
            dispose();
            new HomeView();
        });

    }

    private Object[][] dataTableModelInit(List<UserFriendsData> readAllUserFriendsData) {
        int countRows = readAllUserFriendsData.size();
        Object[][] dataTableModel = new Object[countRows][COUNT_COLUMN];

        for (int i = 0; i < readAllUserFriendsData.size(); i++) {
            dataTableModel[i][0] = i + 1;
            dataTableModel[i][1] = readAllUserFriendsData.get(i).getAboutFriend().getNameFriend();
            dataTableModel[i][2] = readAllUserFriendsData.get(i).getFriendBirthdayDate().getFriendDate();
        }
        return dataTableModel;
    }

    private void setDataTableModel(Object[][] data) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(
                data, new String[]{"ID", "Name Friend", "Date Birthday`s"}
        );

        table.setModel(defaultTableModel);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
