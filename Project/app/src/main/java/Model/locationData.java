package Model;

/**
 * Created by Moamen Hassan on 3/22/2018.
 */

public class locationData {
    private Integer apartmentNum = 1, floorNum = 0;
    // gpsLocation
    private String streetName = "", state = "", district = "";

    public Integer getApartmentNum() {
        return apartmentNum;
    }

    public void setApartmentNum(Integer apartmentNum) {
        this.apartmentNum = apartmentNum;
    }

    public Integer getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(Integer floorNum) {
        this.floorNum = floorNum;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public locationData(Integer apartmentNum, Integer floorNum, String streetName, String state, String district) {

        this.apartmentNum = apartmentNum;
        this.floorNum = floorNum;
        this.streetName = streetName;
        this.state = state;
        this.district = district;
    }
}
