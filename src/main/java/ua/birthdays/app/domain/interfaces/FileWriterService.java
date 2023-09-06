package ua.birthdays.app.domain.interfaces;

import ua.birthdays.app.domain.exceptions.DomainException;
import ua.birthdays.app.models.UserFriendsData;

import java.util.List;

public interface FileWriterService {
    void writeDataIntoFile(final String patch, final List<UserFriendsData> userFriendsDataList) throws DomainException;
}
