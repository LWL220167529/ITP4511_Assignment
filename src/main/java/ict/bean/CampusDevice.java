package ict.bean;

/**
 * AvailableDevice
 */
public class CampusDevice {
    private final String[] defaultCampus = { "CW", "LWL", "ST", "TM", "TY" };

    private int id;
    private int device_id;
    private String device_name;
    private String device_image;
    private String campus;
    private int quantity;
    private String status;

    public CampusDevice() {
    }

    public CampusDevice(String campus, int device_id, int quantity, String status) {
        this.campus = campus;
        this.device_id = device_id;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return device_id;
    }

    public void setDeviceId(int device_id) {
        this.device_id = device_id;
    }

    public String getDeviceName() {
        return device_name;
    }

    public void setDeviceName(String device_name) {
        this.device_name = device_name;
    }

    public String getCampus() {
        return campus;
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
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeviceImage() {
        return device_image;
    }

    public void setDeviceImage(String device_image) {
        this.device_image = device_image;
    }
}