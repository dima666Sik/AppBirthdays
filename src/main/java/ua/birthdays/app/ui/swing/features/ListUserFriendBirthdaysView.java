package ua.birthdays.app.ui.swing.features;

import ua.birthdays.app.dao.env.EnumStateSorted;
import ua.birthdays.app.dao.env.PeriodTimeEnum;
import ua.birthdays.app.domain.exceptions.DomainException;
import ua.birthdays.app.domain.impl.CSVWriterServiceImpl;
import ua.birthdays.app.domain.impl.MainFeaturesServiceImpl;
import ua.birthdays.app.domain.impl.PDFWriterServiceImpl;
import ua.birthdays.app.domain.interfaces.FileWriterService;
import ua.birthdays.app.domain.interfaces.MainFeaturesService;
import ua.birthdays.app.models.*;
import ua.birthdays.app.models.audio.Audio;
import ua.birthdays.app.models.audio.MP3Player;
import ua.birthdays.app.ui.swing.menuview.HomeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

import java.util.concurrent.*;

public class ListUserFriendBirthdaysView extends JFrame {
    private final static int COUNT_COLUMN = 7;

    private JPanel panelListFriendBirthdays;
    private JTable table;
    private JButton ascendingNameFriendButton;
    private JButton descendingNameFriendButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton exitButton;
    private JButton copyButton;
    private JButton defaultButton;
    private JButton ascendingFriendBirthdayDateButton;
    private JButton descendingFriendBirthdayDateButton;
    private JLabel labelCurrentUser;
    private JLabel labelCurrentUserEmail;
    private JRadioButton onOffBackgroundMusic;
    private JLabel minimizeWin;
    private JButton downloadFriendSBirthdaysCSVButton;
    private JButton downloadFriendSBirthdaysPDFButton;
    private JPanel panelScrollTable;
    private Object[][] dataUserFriendsBirthdayOnTable;
    private Object[] objectRowUserFriendBirthday;
    private final Audio audioCongratulationsMP3 = new MP3Player("src/main/resources/sounds/congratulations_sound.mp3");
    private final Audio backgroundSoundMP3 = new MP3Player("src/main/resources/sounds/background.mp3");
    private final User user;
    private MainFeaturesService mainFeaturesService;
    private EnumStateSorted enumStateSorted;
    private List<UserFriendsData> userFriendsDataList;

    public ListUserFriendBirthdaysView(User user) {
        this.user = user;
        setUndecorated(true);
        setContentPane(panelListFriendBirthdays);
        labelCurrentUser.setText(user.getFirstName().concat(","));
        labelCurrentUserEmail.setText(user.getEmail());
        startRemainingService();
        createTable();

        setMinimumSize(new Dimension(1080, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void startRemainingService() {
        // Create ScheduledExecutorService with one work thread
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        MainFeaturesService mainFeaturesService = new MainFeaturesServiceImpl();
        Icon icon = new ImageIcon("src/main/resources/imgs/present.png");

        List<UserFriendsData> userFriendsDataList = mainFeaturesService.readAllUserFriendsDataByDefault(user);

        System.out.println(LocalDateTime.now());

        for (UserFriendsData userFriendsData : userFriendsDataList) {

            long reminderDaysBefore = userFriendsData.getFriendBirthdayDate().getRemindedCountDaysBeforeBirthday();
            LocalDate today = LocalDate.now();
            LocalDate nextBirthday = userFriendsData.getFriendBirthdayDate().getFriendDate().withYear(today.getYear());

            if (nextBirthday.isBefore(today) || nextBirthday.isEqual(today)) {
                continue;
            }

            long daysUntilBirthday = today.until(nextBirthday, ChronoUnit.DAYS);
            if (daysUntilBirthday > reminderDaysBefore) continue;
            long initialDelay = daysUntilBirthday - reminderDaysBefore;

            int remindedFriendHour = userFriendsData.getFriendBirthdayDate().getRemindedFriendHour();
            int remindedFriendHourWithPeriod = (userFriendsData.getFriendBirthdayDate().getPeriodTimeEnum() == PeriodTimeEnum.AM)
                    ? remindedFriendHour
                    : remindedFriendHour + 12;

            // Target time
            LocalTime targetTime = LocalTime.of(remindedFriendHourWithPeriod,
                    userFriendsData.getFriendBirthdayDate().getRemindedFriendMinutes());

            // convert reminding date on minute
            long hourMinutesOnScheduler = LocalTime.now().until(targetTime, ChronoUnit.MINUTES);
            if (hourMinutesOnScheduler < 0)
                hourMinutesOnScheduler = targetTime.until(LocalTime.now(), ChronoUnit.MINUTES);

            //convert day to minute
            long initialDelayInMinutes = initialDelay * 24 * 60;

            //sum delay
            long finalInitialDelay = initialDelayInMinutes + hourMinutesOnScheduler;

            scheduler.schedule(() -> {

                audioCongratulationsMP3.play();

                JOptionPane.showOptionDialog(this,
                        "Your friend's '" +
                                userFriendsData.getAboutFriend().getNameFriend() +
                                "' birthday is in " +
                                daysUntilBirthday +
                                " days! \n\t" +
                                "Happy " +
                                (LocalDate.now().getYear() - userFriendsData.getFriendBirthdayDate().getFriendDate().getYear()) +
                                "-th birthday\n",
                        "REMIND INFORMATION MESSAGE ABOUT FRIEND`S BIRTHDAYS",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        icon,
                        new String[]{"Amazing!"}, null);

                audioCongratulationsMP3.stop();

            }, finalInitialDelay, TimeUnit.MILLISECONDS); // change MILLISECONDS on MINUTES! (Now stay in test version)
        }

        scheduler.shutdown();
    }

    private void createTable() {
        //get data
        mainFeaturesService = new MainFeaturesServiceImpl();

        chooseSortedByEnum(EnumStateSorted.DEFAULT);

        ascendingNameFriendButton.addActionListener(e -> chooseSortedByEnum(EnumStateSorted.ASCENDING_NAME_FRIEND));

        descendingNameFriendButton.addActionListener(e -> chooseSortedByEnum(EnumStateSorted.DESCENDING_NAME_FRIEND));

        ascendingFriendBirthdayDateButton.addActionListener(e -> chooseSortedByEnum(EnumStateSorted.ASCENDING_FRIEND_BIRTHDAY_DATE));

        descendingFriendBirthdayDateButton.addActionListener(e -> chooseSortedByEnum(EnumStateSorted.DESCENDING_FRIEND_BIRTHDAY_DATE));

        defaultButton.addActionListener(e -> chooseSortedByEnum(EnumStateSorted.DEFAULT));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (table.getSelectedRow() == -1) return;

                    objectRowUserFriendBirthday = dataUserFriendsBirthdayOnTable[table.getSelectedRow()];
                    LocalDate oldVersionDate = LocalDate.parse(objectRowUserFriendBirthday[2].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.getDefault()));

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
                    chooseSortedByEnum(enumStateSorted);
                }
            }
        });

        deleteButton.addActionListener(e -> {
            if (table.getSelectedRow() == -1) return;

            if (JOptionPane.showOptionDialog(null,
                    "Do you confident that delete this row?",
                    "Confirm",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Yes I do", "No I don't"}, null) == 1) return;

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
            objectRowUserFriendBirthday = null;
            chooseSortedByEnum(enumStateSorted);
        });

        addButton.addActionListener(e -> {
            new CreateUserFriendBirthdayView(user);
            chooseSortedByEnum(enumStateSorted);
        });

        exitButton.addActionListener(e -> {
            if (onOffBackgroundMusic.isSelected()) {
                backgroundSoundMP3.stop();
            }
            dispose();
            new HomeView();
        });

        copyButton.addActionListener(e -> {
            if (table.getSelectedRow() == -1) return;

            objectRowUserFriendBirthday = dataUserFriendsBirthdayOnTable[table.getSelectedRow()];
        });

        panelScrollTable.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK), "ctrlV");
        panelScrollTable.getActionMap().put("ctrlV", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (objectRowUserFriendBirthday != null) {
                    LocalDate oldVersionDate = LocalDate.parse(objectRowUserFriendBirthday[2].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.getDefault()));

                    new CreateUserFriendBirthdayView(new UserFriendsData(
                            new FriendBirthdayDate(oldVersionDate,
                                    Integer.parseInt(objectRowUserFriendBirthday[3].toString()),
                                    Integer.parseInt(objectRowUserFriendBirthday[4].toString()),
                                    PeriodTimeEnum.valueOf(objectRowUserFriendBirthday[5].toString()),
                                    Integer.parseInt(objectRowUserFriendBirthday[6].toString())
                            ),
                            new AboutFriend(objectRowUserFriendBirthday[1].toString()),
                            user
                    ));
                    chooseSortedByEnum(enumStateSorted);
                }
            }
        });

        onOffBackgroundMusic.addActionListener(e -> {
            if (onOffBackgroundMusic.isSelected()) {
                backgroundSoundMP3.playInBackground();
            } else {
                backgroundSoundMP3.stop();
            }
        });

        minimizeWin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setState(JFrame.ICONIFIED);
            }
        });

        downloadFriendSBirthdaysCSVButton.addActionListener(e -> {
            String patch = getAbsolutePathToChooseDirectory();
            if (patch != null) {
                FileWriterService csvWriterService = new CSVWriterServiceImpl();
                try {
                    csvWriterService.writeDataIntoFile(patch, userFriendsDataList);
                } catch (DomainException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Create file isn't successful!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        downloadFriendSBirthdaysPDFButton.addActionListener(e -> {
            String patch = getAbsolutePathToChooseDirectory();
            if (patch != null) {
                FileWriterService csvWriterService = new PDFWriterServiceImpl();
                try {
                    csvWriterService.writeDataIntoFile(patch, userFriendsDataList);
                } catch (DomainException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Create file isn't successful!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private String getAbsolutePathToChooseDirectory() {
        try {
            // Save current view, in order to restore it later.
            String currentLookAndFeel = UIManager.getLookAndFeel().getClass().getName();

            // Set system appearance only for JFileChooser
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Create JFileChooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            // Add listener for close JFileChooser
            fileChooser.addActionListener(fc -> {
                // Restore previous view after close JFileChooser
                try {
                    UIManager.setLookAndFeel(currentLookAndFeel);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
            });

            // Open JFileChooser
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
                return fileChooser.getSelectedFile().getAbsolutePath();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    private void chooseSortedByEnum(EnumStateSorted enumStateSorted) {

        switch (enumStateSorted) {
            case ASCENDING_NAME_FRIEND: {
                userFriendsDataList = mainFeaturesService.readAllUserFriendsDataAscendingByNameFriend(user);
                break;
            }
            case DESCENDING_NAME_FRIEND: {
                userFriendsDataList = mainFeaturesService.readAllUserFriendsDataDescendingByNameFriend(user);
                break;
            }
            case ASCENDING_FRIEND_BIRTHDAY_DATE: {
                userFriendsDataList = mainFeaturesService.readAllUserFriendsDataAscendingByFriendBirthdayDate(user);
                break;
            }
            case DESCENDING_FRIEND_BIRTHDAY_DATE: {
                userFriendsDataList = mainFeaturesService.readAllUserFriendsDataDescendingByFriendBirthdayDate(user);
                break;
            }
            case DEFAULT: {
                userFriendsDataList = mainFeaturesService.readAllUserFriendsDataByDefault(user);
                break;
            }
        }

        dataUserFriendsBirthdayOnTable = dataTableModelInit(userFriendsDataList);
        setDataTableModel(dataUserFriendsBirthdayOnTable);

        this.enumStateSorted = enumStateSorted;
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
                data, new String[]{"ID", "Name Friend", "Date Birthday`s", "Remind Hour", " Remind Minutes", "Remind Period Time", "Count Days Before Birthday"}
        );

        table.setModel(defaultTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
