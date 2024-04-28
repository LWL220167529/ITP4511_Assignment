package ict.bean;

import java.sql.Date;

public class CheckIn {
    private int checkInId;
    private int userId;
    private String userName;
    private String equipmentName;
    private int quantity;
    private String image;
    private String campusName;
    private Date checkInDate;
    private String damageReport;
    private boolean confirmedCheckIn;
    private boolean confirmedDamage;
    private boolean deleted;
    



    
    // Default constructor
    public CheckIn() {
    }

    // Constructor with all fields
    public CheckIn(int checkInId, int userId,String userName, String equipmentName, int quantity,String campusName, String image, Date checkInDate, boolean confirmedCheckIn, String damageReport, boolean deleted,boolean confirmedDamage) {
        this.checkInId = checkInId;
        this.userId = userId;
        this.userName= userName;
        this.equipmentName = equipmentName;
        this.quantity = quantity;
        this.image = image;
        this.campusName = campusName;
        this.checkInDate = checkInDate;
        this.damageReport = damageReport ;
        this.confirmedCheckIn = confirmedCheckIn;
        this.deleted = deleted;
        this.confirmedDamage = confirmedDamage;
    }
    
    public boolean isDeleted() {
        return deleted;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isConfirmedDamage() {
        return confirmedDamage;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setConfirmedDamage(boolean confirmedDamage) {
        this.confirmedDamage = confirmedDamage;
    }

    // Getters and setters
    public int getCheckInId() {
        return checkInId;
    }



    public void setDamageReport(String damageReport) {
        this.damageReport = damageReport;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setCheckInId(int id) {
        this.checkInId = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getDamageReport() {
        return damageReport;
    }

    public boolean isConfirmedCheckIn() {
        return confirmedCheckIn;
    }

    public void setConfirmedCheckIn(boolean confirmedCheckIn) {
        this.confirmedCheckIn = confirmedCheckIn;
    }


    // toString method can be overridden to print the details of the bean, if necessary
    @Override
    public String toString() {
        return "CheckInEquipment{" +
               "checkInId=" + checkInId +
               ", userId=" + userId +
               ", equipmentName=" + equipmentName +
               ", campusName='" + campusName + '\'' +
               ", checkInDate=" + checkInDate + '\'' +
               ", damageReport=" + damageReport + '\'' +
               ", confirmedCheckIN='" + confirmedCheckIn + '\'' +
               '}';
    }
}