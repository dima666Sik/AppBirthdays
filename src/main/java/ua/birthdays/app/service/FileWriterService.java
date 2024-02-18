package ua.birthdays.app.service;

import ua.birthdays.app.service.exceptions.OpenFileException;
import ua.birthdays.app.model.UserFriendsData;

import java.util.List;

@FunctionalInterface
public interface FileWriterService {
    void writeDataIntoFile(final String patch, final List<UserFriendsData> userFriendsDataList) throws OpenFileException;
}
