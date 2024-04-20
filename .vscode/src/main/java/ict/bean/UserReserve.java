package ict.bean;

import java.sql.Date;

public class UserReserve extends CampusEquipment{

    private int id;
    private int userId;
    private String destinationCampusId;
    private int quantity = 1;
    private String status;
    private Date date;
    private int deliveryUserId;

    public UserReserve() {
    }

    public UserReserve(CampusEquipment campusEquipment) {
        super(campusEquipment.getCampus(), campusEquipment.getEquipmentId(), 
        campusEquipment.getQuantity(), campusEquipment.getStatus());
        super.setId(campusEquipment.getId());
        super.setName(campusEquipment.getName());
        super.setImage(campusEquipment.getImage());
    }

    // Getters and Setters

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getBelongCampusId() {
        return super.getCampus();
    }

    public void setBelongCampusId(String belongCampusId) {
        super.setCampus(belongCampusId);
    }

    public String getDestinationCampusId() {
        return this.destinationCampusId;
    }

    public void setDestinationCampusId(String destinationCampusId) {
        this.destinationCampusId = destinationCampusId;
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

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDeliveryUserId() {
        return this.deliveryUserId;
    }

    public void setDeliveryUserId(int deliveryUserId) {
        this.deliveryUserId = deliveryUserId;
    }

    // Equipment getters and setters

    public int getCampusEquipmentId() {
        return super.getId();
    }

    public void setCampusEquipmentId(int equipmentId) {
        super.setId(id);
    }
    public String setCampusEquipmentName() {
        return super.getName();
    }

    public String getCampusEquipmentImage() {
        return super.getImage();
    }

    public void setCampusEquipmentName(String name) {
        super.setName(name);
    }

    public void setCampusEquipmentImage(String image) {
        super.setImage(image);
    }

    public void setCampusEquipmentQuantity(int quantity) {
        super.setQuantity(quantity);
    }

    public int getCampusEquipmentQuantity() {
        return super.getQuantity();
    }

    public void setCampusEquipmentStatus(String status) {
        super.setStatus(status);
    }

    public String getCampusEquipmentStatus() {
        return super.getStatus();
    }
}