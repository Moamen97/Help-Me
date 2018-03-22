package Interface;


import Model.user;

public interface userQueries {
    void addUser(user user);

    user getUserByName(String Name);

    user getUserByUserName(String userName);

    void getUserByUserNameAndChecksPassword(String userName, String password);

    user getUserByEmail(String Email);
}
