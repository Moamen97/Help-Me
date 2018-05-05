package Model;

import java.util.List;

public class notification {
    private String notificationData = "", notificationTime = "";

    public String getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(String notificationData) {
        this.notificationData = notificationData;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public notification(String notificationData, String notificationTime) {

        this.notificationData = notificationData;
        this.notificationTime = notificationTime;
    }
}
