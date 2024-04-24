package ict.bean;

import java.sql.Date;

public class WishEquipment {
    private int id;
    private int userId;
    private String username;
    private int equipmentId;
    private int quantity;
    private String belongCampusId;
    private String destinationCampusId;
    private String status;
    private String equipmentName;
    private String equipmentImage;
    private int deliveryUserId;
    private String deliveryUsername;
    private Date date;
    private String belongCampusName;
    private String destinationCampusName;
    private CampusEquipment campusEquipment;

    public WishEquipment() {
    }

    public WishEquipment(int id, int userId, String username, int equipmentId, int quantity, String belongCampusId,
                    String destinationCampusId, String status, String equipmentName, String equipmentImage,
                    Date date, String belongCampusName, String destinationCampusName) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.equipmentId = equipmentId;
        this.quantity = quantity;
        this.belongCampusId = belongCampusId;
        this.destinationCampusId = destinationCampusId;
        this.status = status;
        this.equipmentName = equipmentName;
        this.equipmentImage = equipmentImage;
        this.date = date;
        this.belongCampusName = belongCampusName;
        this.destinationCampusName = destinationCampusName;
    }

    // Getter and Setter methods for all the fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBelongCampusId() {
        return belongCampusId;
    }

    public void setBelongCampusId(String belongCampusId) {
        this.belongCampusId = belongCampusId;
    }

    public String getDestinationCampusId() {
        return destinationCampusId;
    }

    public void setDestinationCampusId(String destinationCampusId) {
        this.destinationCampusId = destinationCampusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentImage() {
        return equipmentImage;
    }

    public void setEquipmentImage(String equipmentImage) {
        this.equipmentImage = equipmentImage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBelongCampusName() {
        return belongCampusName;
    }

    public void setBelongCampusName(String belongCampusName) {
        this.belongCampusName = belongCampusName;
    }

    public String getDestinationCampusName() {
        return destinationCampusName;
    }

    public void setDestinationCampusName(String destinationCampusName) {
        this.destinationCampusName = destinationCampusName;
    }

    public CampusEquipment getCampusEquipment() {
        return campusEquipment;
    }

    public void setCampusEquipment(CampusEquipment campusEquipment) {
        this.campusEquipment = campusEquipment;
    }

    public int getDeliveryUserId() {
        return deliveryUserId;
    }

    public void setDeliveryUserId(int deliveryUserId) {
        this.deliveryUserId = deliveryUserId;
    }

    public String getDeliveryUsername() {
        return deliveryUsername;
    }

    public void setDeliveryUsername(String deliveryUsername) {
        this.deliveryUsername = deliveryUsername;
    }
}
