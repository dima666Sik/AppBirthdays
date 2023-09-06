package ua.birthdays.app.domain.impl;

import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.domain.exceptions.DomainException;
import ua.birthdays.app.domain.interfaces.FileWriterService;
import ua.birthdays.app.domain.util.Randomize;
import ua.birthdays.app.models.UserFriendsData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Implementation of the {@link FileWriterService} interface that writes user friend birthday data into a CSV file.
 */
public class CSVWriterServiceImpl implements FileWriterService {
    private final static Logger logger = LogManager.getLogger(CSVWriterServiceImpl.class.getName());

    @Override
    public void writeDataIntoFile(final String patch, final List<UserFriendsData> userFriendsDataList) throws DomainException {
        String nameCSVFile = "yourFriendsBirthdays" + Randomize.generateRandomize(0, 100000) + ".csv";

        try {
            File file = new File(patch, nameCSVFile);

            // Open file to write data.
            // Param "true" meaning that data add into end file.
            FileWriter fileWriter = new FileWriter(file, true);

            // Create object CSVWriter
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            // Record data into CSV-file
            csvWriter.writeNext(new String[]{"Name Friend", "Date Birthday`s", "Count Days Before Birthday"});

            for (UserFriendsData userFriendsData : userFriendsDataList) {
                csvWriter.writeNext(new String[]{
                        userFriendsData.getAboutFriend().getNameFriend(),
                        userFriendsData.getFriendBirthdayDate().getFriendDate().toString(),
                        String.valueOf(userFriendsData.getFriendBirthdayDate().getRemindedCountDaysBeforeBirthday())
                });
            }

            // Close CSVWriter
            csvWriter.close();

            logger.info("Your data successful added to csv file. in this patch: " + patch + nameCSVFile);

        } catch (IOException e) {
            logger.info("Error find file .csv!", e);
            throw new DomainException("Error find file .csv!", e);
        }
    }
}
