package ua.birthdays.app.domain;

import ua.birthdays.app.domain.exceptions.OpenFileException;
import ua.birthdays.app.models.UserFriendsData;

import java.util.List;

@FunctionalInterface
public interface FileWriterService {
    void writeDataIntoFile(final String patch, final List<UserFriendsData> userFriendsDataList) throws OpenFileException;
}
