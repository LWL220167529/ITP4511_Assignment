package ict.bean;

import java.util.List;
import java.util.ArrayList;

public class WishList {
    private List<WishEquipment> wishEquipments;
    private int userId;

    public WishList() {
        wishEquipments = new ArrayList<WishEquipment>();
    }

    public WishList(List<WishEquipment> wishEquipments) {
        this.wishEquipments = wishEquipments;
    }

    public void newWishList() {
        wishEquipments = new ArrayList<WishEquipment>();
    }

    public List<WishEquipment> getList() {
        return wishEquipments;
    }

    public void setWishEquipments(List<WishEquipment> wishEquipments) {
        this.wishEquipments = wishEquipments;
    }

    public void addWishEquipment(WishEquipment wishEquipment) {
        wishEquipments.add(wishEquipment);
    }

    public void removeWishEquipment(WishEquipment wishEquipment) {
        wishEquipments.remove(wishEquipment);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
