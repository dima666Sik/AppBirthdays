package ua.birthdays.app.dao.interfaces;

import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.models.UserFriendsData;

import java.util.List;

public interface UserFriendsDataDAO {
    boolean createUserFriendsData(UserFriendsData userFriendsData) throws DAOException;
    UserFriendsData readUserFriendsData(int idUserFriendsData) throws DAOException;
    List<UserFriendsData> readAllUserFriendsData(int idUser) throws DAOException;
    boolean updateUserFriendsData(int idUserFriendsData) throws DAOException;
    boolean deleteUserFriendsData(int idUserFriendsData) throws DAOException;
}
