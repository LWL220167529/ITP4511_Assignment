package ict.bean;

import java.sql.Date;

public class CheckOut {
    private int checkOutId;
    private int userId;
    private String userName;
    private String equipmentName;
    private int quantity;
    private String image;
    private String campusName;
    private Date checkOutDate;
    private boolean returned;
    private boolean confirmedCheckOut;
    private boolean deleted;


    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
    
    // Default constructor
    public CheckOut() {
    }

    // Constructor with all fields
    public CheckOut(int checkOutId, int userId, String userName, String equipmentName,int quantity, String campusName ,String image, Date checkOutDate, boolean returned, boolean confirmedCheckOut,boolean deleted) {
        this.checkOutId = checkOutId;
        this.userId = userId;
        this.userName= userName;
        this.equipmentName = equipmentName;
        this.quantity = quantity;
        this.image = image;
        this.campusName = campusName;
        this.checkOutDate = checkOutDate;
        this.returned = returned;
        this.confirmedCheckOut = confirmedCheckOut;
        this.deleted = deleted;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
    public int getCheckOutId() {
        return checkOutId;
    }

    public void setCheckOutId(int id) {
        this.checkOutId = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    

    public String getCampusName() {
        return campusName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
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
               "id=" + checkOutId +
               ", userId=" + userId +
               ", equipmentName=" + equipmentName +
               ", campusName='" + campusName + '\'' +
               ", checkOutDate=" + checkOutDate +
               ", confirmedCheckOut='" + confirmedCheckOut + '\'' +
               '}';
    }
}