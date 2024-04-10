package ict.bean;

import java.sql.Date;

public class UserReserve extends CampusEquipment{

    private int id;
    private int userId;
    private String destinationCampusId;
    private int quantity;
    private String status;
    private Date date;

    public UserReserve() {
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

    public int getEquipmentId() {
        return super.getId();
    }

    public void setEquipmentId(int equipmentId) {
        super.setId(id);
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

    // Equipment getters and setters
    public String getEquipmentName() {
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
}