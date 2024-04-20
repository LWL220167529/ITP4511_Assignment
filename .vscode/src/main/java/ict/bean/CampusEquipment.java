package ict.bean;

/**
 * AvailableEquipment
 */
public class CampusEquipment extends Equipment {
    private final String[] defaultCampus = { "CW", "LWL", "ST", "TM", "TY" };

    private int id;
    private String campus;
    private int quantity;
    private String status;

    public CampusEquipment() {
    }

    public CampusEquipment(String campus, int equipment_id, int quantity, String status) {
        this.campus = campus;
        super.setId(equipment_id);
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEquipmentId() {
        return super.getId();
    }

    public void setEquipmentId(int equipment_id) {
        super.setId(equipment_id);
    }

    public String getEquipmentName() {
        return super.getName();
    }

    public void setEquipmentName(String equipment_name) {
        super.setName(equipment_name);
    }

    public String getCampus() {
        return this.campus;
    }

    public void setCampus(String campus) {
        if (campus.equals(defaultCampus[0]) || campus.equals(defaultCampus[1]) || campus.equals(defaultCampus[2])
                || campus.equals(defaultCampus[3]) || campus.equals(defaultCampus[4])) {
            this.campus = campus;
        } else {
            throw new IllegalArgumentException("Invalid campus");
        }
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEquipmentImage() {
        return super.getImage();
    }

    public void setEquipmentImage(String equipment_image) {
        super.setImage(equipment_image);
    }
}