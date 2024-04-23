package ict.bean;

import java.sql.Date;

public class CheckOut {
    private int checkOutid;
    private int userId;
    private int reserveID;
    private int equipmentId;
    private String image;
    private String campusId;
    private Date checkOutDate;
    private boolean confirmedCheckOut;
    private boolean deleted;
    // Additional fields can be added as required
    
    // Default constructor
    public CheckOut() {
    }

    // Constructor with all fields
    public CheckOut(int checkOutid, int userId, int equipmentId, int reserveID, String campusId,String image, Date checkOutDate, boolean confirmedCheckOut,boolean deleted) {
        this.checkOutid = checkOutid;
        this.userId = userId;
        this.reserveID = reserveID;
        this.equipmentId = equipmentId;
        this.image = image;
        this.campusId = campusId;
        this.checkOutDate = checkOutDate;
        this.confirmedCheckOut = confirmedCheckOut;
        this.deleted = deleted;
    }

    public boolean isConfirmedCheckOut() {
        return confirmedCheckOut;
    }

    public void setConfirmedCheckOut(boolean confirmedCheckOut) {
        this.confirmedCheckOut = confirmedCheckOut;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getReserveID() {
        return reserveID;
    }

    public void setReserveID(int reserveID) {
        this.reserveID = reserveID;
    }

    public Date getCheckInDate() {
        return checkOutDate;
    }

    public void setcheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public Date checkOutDate() {
        return checkOutDate;
    }

    public void setDateCheckedOut(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }




    // toString method can be overridden to print the details of the bean, if necessary
    @Override
    public String toString() {
        return "CheckOutEquipment{" +
               "id=" + checkOutid +
               ", userId=" + userId +
               ", equipmentId=" + equipmentId +
               ", campusId='" + campusId + '\'' +
               ", checkOutDate=" + checkOutDate +
               ", confirmedCheckOut='" + confirmedCheckOut + '\'' +
               '}';
    }
}