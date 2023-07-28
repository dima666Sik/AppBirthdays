package ua.birthdays.app.ui.swing.features;

import ua.birthdays.app.dao.env.PeriodTimeEnum;
import ua.birthdays.app.domain.impl.MainFeaturesServiceImpl;
import ua.birthdays.app.domain.interfaces.MainFeaturesService;
import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;
import ua.birthdays.app.ui.swing.menuview.HomeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ListUserFriendBirthdaysView extends JDialog {
    private final static int COUNT_COLUMN = 7;

    private JPanel panelListFriendBirthdays;
    private JTable table;
    private JButton ascendingNameFriendButton;
    private JButton descendingNameFriendButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton exitButton;
    private JButton editButton;
    private JButton defaultButton;
    private JButton ascendingFriendBirthdayDateButton;
    private JButton descendingFriendBirthdayDateButton;
    private JLabel labelCurrentUser;
    private JLabel labelCurrentUserEmail;
    private Object[][] dataUserFriendsBirthdayOnTable;
    private Object[] objectRowUserFriendBirthday;
    private List<UserFriendsData> userFriendsDataList;

    public ListUserFriendBirthdaysView(User user) {
        setUndecorated(true);
        setContentPane(panelListFriendBirthdays);
        labelCurrentUser.setText(user.getFirstName().concat(","));
        labelCurrentUserEmail.setText(user.getEmail());
        createTable(user);
        setMinimumSize(new Dimension(880, 500));

        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    private void createTable(User user) {
        //get data
        MainFeaturesService mainFeaturesService = new MainFeaturesServiceImpl();

        userFriendsDataList = mainFeaturesService.readAllUserFriendsDataByDefault(user);

        dataUserFriendsBirthdayOnTable = dataTableModelInit(userFriendsDataList);

        setDataTableModel(dataUserFriendsBirthdayOnTable);

        ascendingNameFriendButton.addActionListener(e -> {

            userFriendsDataList = mainFeaturesService.readAllUserFriendsDataAscendingByNameFriend(user);

            dataUserFriendsBirthdayOnTable = dataTableModelInit(userFriendsDataList);

            setDataTableModel(dataUserFriendsBirthdayOnTable);

        });

        descendingNameFriendButton.addActionListener(e -> {
            userFriendsDataList = mainFeaturesService.readAllUserFriendsDataDescendingByNameFriend(user);

            dataUserFriendsBirthdayOnTable = dataTableModelInit(userFriendsDataList);

            setDataTableModel(dataUserFriendsBirthdayOnTable);
        });

        ascendingFriendBirthdayDateButton.addActionListener(e -> {
            userFriendsDataList = mainFeaturesService.readAllUserFriendsDataAscendingByFriendBirthdayDate(user);

            dataUserFriendsBirthdayOnTable = dataTableModelInit(userFriendsDataList);

            setDataTableModel(dataUserFriendsBirthdayOnTable);

        });

        descendingFriendBirthdayDateButton.addActionListener(e -> {
            userFriendsDataList = mainFeaturesService.readAllUserFriendsDataDescendingByFriendBirthdayDate(user);

            dataUserFriendsBirthdayOnTable = dataTableModelInit(userFriendsDataList);

            setDataTableModel(dataUserFriendsBirthdayOnTable);
        });

        defaultButton.addActionListener(e -> {
            userFriendsDataList = mainFeaturesService.readAllUserFriendsDataByDefault(user);

            dataUserFriendsBirthdayOnTable = dataTableModelInit(userFriendsDataList);

            setDataTableModel(dataUserFriendsBirthdayOnTable);
        });

        editButton.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {

                objectRowUserFriendBirthday = dataUserFriendsBirthdayOnTable[table.getSelectedRow()];
                LocalDate oldVersionDate = LocalDate.parse(objectRowUserFriendBirthday[2].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.getDefault()));

                dispose();
                new EditUserFriendBirthdayView(
                        new UserFriendsData(
                                new FriendBirthdayDate(oldVersionDate,
                                        Integer.parseInt(objectRowUserFriendBirthday[3].toString()),
                                        Integer.parseInt(objectRowUserFriendBirthday[4].toString()),
                                        PeriodTimeEnum.valueOf(objectRowUserFriendBirthday[5].toString()),
                                        Integer.parseInt(objectRowUserFriendBirthday[6].toString())
                                        ),
                                new AboutFriend(objectRowUserFriendBirthday[1].toString()),
                                user
                        )
                );
            }
        });

        deleteButton.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                objectRowUserFriendBirthday = dataUserFriendsBirthdayOnTable[table.getSelectedRow()];
                LocalDate friendBirthdayDate = LocalDate.parse(objectRowUserFriendBirthday[2].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.getDefault()));

                if (!mainFeaturesService.deleteUserFriendsData(new UserFriendsData(
                        new FriendBirthdayDate(friendBirthdayDate,
                                Integer.parseInt(objectRowUserFriendBirthday[3].toString()),
                                Integer.parseInt(objectRowUserFriendBirthday[4].toString()),
                                PeriodTimeEnum.valueOf(objectRowUserFriendBirthday[5].toString()),
                                Integer.parseInt(objectRowUserFriendBirthday[6].toString())
                        ),
                        new AboutFriend(objectRowUserFriendBirthday[1].toString()),
                        user
                ))) {
                    JOptionPane.showMessageDialog(this,
                            "Deleting select User Friend Birthday was unsuccessful!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                return;
                }

                userFriendsDataList.remove(table.getSelectedRow());

                dataUserFriendsBirthdayOnTable = dataTableModelInit(userFriendsDataList);

                setDataTableModel(dataUserFriendsBirthdayOnTable);
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
            dataTableModel[i][3] = readAllUserFriendsData.get(i).getFriendBirthdayDate().getRemindedFriendHour();
            dataTableModel[i][4] = readAllUserFriendsData.get(i).getFriendBirthdayDate().getRemindedFriendMinutes();
            dataTableModel[i][5] = readAllUserFriendsData.get(i).getFriendBirthdayDate().getPeriodTimeEnum();
            dataTableModel[i][6] = readAllUserFriendsData.get(i).getFriendBirthdayDate().getRemindedCountDaysBeforeBirthday();
        }
        return dataTableModel;
    }

    private void setDataTableModel(Object[][] data) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(
                data, new String[]{"ID", "Name Friend", "Date Birthday`s", "Hour", "Minutes", "Period Time", "Count Days Before Birthday"}
        );

        table.setModel(defaultTableModel);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
