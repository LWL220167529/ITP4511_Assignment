package ict.bean;
import java.util.List;
import java.util.ArrayList;

public class CampusDevices {
    private List<CampusDevice> campusDevices;
    private String campus;

    public CampusDevices() {
        campusDevices = new ArrayList<CampusDevice>();
    }
    
    public void setCampusDevices(List<CampusDevice> campusDevices) {
        this.campusDevices = campusDevices;
    }

    public List<CampusDevice> getCampusDevices() {
        return campusDevices;
    }
    
    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCampus() {
        return campus;
    }
}
