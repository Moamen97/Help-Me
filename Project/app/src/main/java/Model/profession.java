package Model;

import java.util.List;

public class profession {
    private String professionCategory = "";
    private Boolean isProfessional = false;
    private Integer professionalRate = 0;
    private List<workshop> workshopsList = null;

    public profession() {

    }

    public profession(String professionCategory, Boolean isProfessional, Integer professionalRate, List<workshop> workshopsList) {

        this.professionCategory = professionCategory;
        this.isProfessional = isProfessional;
        this.professionalRate = professionalRate;
        this.workshopsList = workshopsList;
    }

    public List<workshop> getWorkshopsList() {
        return workshopsList;
    }

    public void setWorkshopsList(List<workshop> workshopsList) {
        this.workshopsList = workshopsList;
    }

    public String getProfessionCategory() {
        return professionCategory;
    }

    public void setProfessionCategory(String professionCategory) {
        this.professionCategory = professionCategory;
    }

    public Boolean getProfessional() {
        return isProfessional;
    }

    public void setProfessional(Boolean professional) {
        isProfessional = professional;
    }

    public Integer getProfessionalRate() {
        return professionalRate;
    }

    public void setProfessionalRate(Integer professionalRate) {
        this.professionalRate = professionalRate;
    }
}
