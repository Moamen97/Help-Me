package Model;

import java.security.Timestamp;
import java.util.List;
import java.util.Map;

public class user {

    private String eMail = "", firstName = "", midName = "", lastName = "", gender = "", image = "", password = "", phoneNum = "", userName = "";
    private Integer behav_rate = 0;
    private String birthDate = "";
    private List<notification> notificationsList = null;
    private List<post> postsList = null;
    private profession userProfession;

    public user() {

    }

    public user(user User) {
        this.eMail = User.eMail;
        this.firstName = User.firstName;
        this.midName = User.midName;
        this.lastName = User.lastName;
        this.gender = User.gender;
        this.image = User.image;
        this.password = User.password;
        this.phoneNum = User.phoneNum;
        this.userName = User.userName;
        this.behav_rate = User.behav_rate;
        this.birthDate = User.birthDate;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBehav_rate() {
        return behav_rate;
    }

    public void setBehav_rate(Integer behav_rate) {
        this.behav_rate = behav_rate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<notification> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(List<notification> notificationsList) {
        this.notificationsList = notificationsList;
    }

    public List<post> getPostsList() {
        return postsList;
    }

    public void setPostsList(List<post> postsList) {
        this.postsList = postsList;
    }

    public profession getUserProfession() {
        return userProfession;
    }

    public void setUserProfession(profession userProfession) {
        this.userProfession = userProfession;
    }

    public user(String eMail, String firstName, String midName,
                String lastName, String gender, String image, String password,
                String phoneNum, String userName, Integer behav_rate, String birthDate,
                List<notification> notificationsList, List<post> postsList, profession userProfession) {
        this.eMail = eMail;
        this.firstName = firstName;
        this.midName = midName;

        this.lastName = lastName;
        this.gender = gender;
        this.image = image;
        this.password = password;
        this.phoneNum = phoneNum;
        this.userName = userName;
        this.behav_rate = behav_rate;
        this.birthDate = birthDate;
        this.notificationsList = notificationsList;
        this.postsList = postsList;
        this.userProfession = userProfession;
    }
}
