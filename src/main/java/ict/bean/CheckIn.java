package ict.bean;

import java.sql.Date;

public class CheckIn {
    private int checkInId;
    private int userId;
    private int reserveID;
    private int equipmentId;
    private String campusId;
    private Date checkInDate;
    private String status;
    // Additional fields can be added as required
    
    // Default constructor
    public CheckIn() {
    }

    // Constructor with all fields
    public CheckIn(int checkInId, int userId, int reserveID,int equipmentId, String campusId, Date checkInDate, String status) {
        this.checkInId = checkInId;
        this.userId = userId;
        this.reserveID = reserveID;
        this.equipmentId = equipmentId;
        this.campusId = campusId;
        this.checkInDate = checkInDate;
        this.status = status;
    }

    // Getters and setters
    public int getCheckInId() {
        return checkInId;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // toString method can be overridden to print the details of the bean, if necessary
    @Override
    public String toString() {
        return "CheckInEquipment{" +
               "checkInId=" + checkInId +
               ", userId=" + userId +
               ", equipmentId=" + equipmentId +
               ", campusId='" + campusId + '\'' +
               ", checkInDate=" + checkInDate +
               ", status='" + status + '\'' +
               '}';
    }
}