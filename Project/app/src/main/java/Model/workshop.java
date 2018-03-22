package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class workshop {
    private Map<String, Object> location = null;
    private String workshopPhoneNum = "";
    private Integer workshopRate = 0;
    private List<Object> workingHours = null;

    public String getWorkshopPhoneNum() {
        return workshopPhoneNum;
    }

    public void setWorkshopPhoneNum(String workshopPhoneNum) {
        this.workshopPhoneNum = workshopPhoneNum;
    }

    public workshop(Object locationInfo) {
        location = new HashMap<>();
        location.put("location", locationInfo);
    }

    public Map<String, Object> getLocation() {
        return location;
    }

    public void setLocation(Map<String, Object> location) {
        this.location = location;
    }

    public String getPhoneNum() {
        return workshopPhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.workshopPhoneNum = phoneNum;
    }

    public Integer getWorkshopRate() {
        return workshopRate;
    }

    public void setWorkshopRate(Integer workshopRate) {
        this.workshopRate = workshopRate;
    }

    public List<Object> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<Object> workingHours) {
        this.workingHours = workingHours;
    }

    public workshop(Map<String, Object> location, String phoneNum, Integer workshopRate, List<Object> workingHours) {

        this.location = location;
        this.workshopPhoneNum = phoneNum;
        this.workshopRate = workshopRate;
        this.workingHours = workingHours;
    }
}

