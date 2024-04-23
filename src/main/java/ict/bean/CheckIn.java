package ict.bean;

import java.sql.Date;

public class CheckIn {
    private int checkInId;
    private int userId;
    private int reserveID;
    private int equipmentId;
    private String image;
    private String campusId;
    private Date checkInDate;
    private String damageReport;
    private boolean confirmedCheckIn;
    private boolean confirmedDamage;
    private boolean deleted;
    // Additional fields can be added as required

    public int getReserveID() {
        return reserveID;
    }

    public boolean isDeleted() {
        return deleted;
    }
    
    // Default constructor
    public CheckIn() {
    }

    // Constructor with all fields
    public CheckIn(int checkInId, int userId, int reserveID,int equipmentId, String campusId, String image, Date checkInDate, boolean confirmedCheckIn, String damageReport, boolean deleted,boolean confirmedDamage) {
        this.checkInId = checkInId;
        this.userId = userId;
        this.reserveID = reserveID;
        this.equipmentId = equipmentId;
        this.image = image;
        this.campusId = campusId;
        this.checkInDate = checkInDate;
        this.damageReport = damageReport ;
        this.confirmedCheckIn = confirmedCheckIn;
        this.deleted = deleted;
        this.confirmedDamage = confirmedDamage;
    }

    public boolean isConfirmedDamage() {
        return confirmedDamage;
    }

    public String getImage() {
        return image;
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

    public void setReserveID(int reserveID) {
        this.reserveID = reserveID;
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

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
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
               ", equipmentId=" + equipmentId +
               ", campusId='" + campusId + '\'' +
               ", checkInDate=" + checkInDate + '\'' +
               ", damageReport=" + damageReport + '\'' +
               ", confirmedCheckIN='" + confirmedCheckIn + '\'' +
               '}';
    }
}