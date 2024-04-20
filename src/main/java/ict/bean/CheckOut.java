package ict.bean;

import java.sql.Date;

public class CheckOut {
    private int checkOutid;
    private int userId;
    private int reserveID;
    private int equipmentId;
    private String campusId;
    private Date dateCheckedOut;
    private String status;
    // Additional fields can be added as required
    
    // Default constructor
    public CheckOut() {
    }

    // Constructor with all fields
    public CheckOut(int checkOutid, int userId, int equipmentId, int reserveID, String campusId, Date dateCheckedOut, Date dateDueBack, String status) {
        this.checkOutid = checkOutid;
        this.userId = userId;
        this.reserveID = reserveID;
        this.equipmentId = equipmentId;
        this.campusId = campusId;
        this.dateCheckedOut = dateCheckedOut;
        this.status = status;
    }

    // Getters and setters
    public int getcheckOutid() {
        return checkOutid;
    }

    public void setCheckOutid(int id) {
        this.checkOutid = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReserveId() {
        return reserveID;
    }

    public void setReserveId(int reserveID) {
        this.reserveID = reserveID;
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

    public Date getDateCheckedOut() {
        return dateCheckedOut;
    }

    public void setDateCheckedOut(Date dateCheckedOut) {
        this.dateCheckedOut = dateCheckedOut;
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
        return "CheckOutEquipment{" +
               "id=" + checkOutid +
               ", userId=" + userId +
               ", equipmentId=" + equipmentId +
               ", campusId='" + campusId + '\'' +
               ", dateCheckedOut=" + dateCheckedOut +
               ", status='" + status + '\'' +
               '}';
    }
}