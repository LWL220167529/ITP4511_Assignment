package ict.bean;
import java.util.List;
import java.util.ArrayList;

public class CampusEquipments {
    private List<CampusEquipment> campusEquipments;
    private String campus;

    public CampusEquipments() {
        campusEquipments = new ArrayList<CampusEquipment>();
    }
    
    public void setCampusEquipments(List<CampusEquipment> campusEquipments) {
        this.campusEquipments = campusEquipments;
    }

    public List<CampusEquipment> getCampusEquipments() {
        return campusEquipments;
    }
    
    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCampus() {
        return campus;
    }
}
