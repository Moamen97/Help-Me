package Model;

import com.google.firebase.firestore.FirebaseFirestore;

public class users {

    String eMail, firstName, midName, lastName, gender, image, password, phoneNum, user_name;

    public users(String eMail, String firstName, String midName, String lastName, String gender, String image, String password, String phoneNum, String user_name) {
        this.eMail = eMail;
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.gender = gender;
        this.image = image;
        this.password = password;
        this.phoneNum = phoneNum;
        this.user_name = user_name;
    }

    public users() {
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String geteMail() {
        return eMail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMidName() {
        return midName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getImage() {
        return image;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getUser_name() {
        return user_name;
    }
}
