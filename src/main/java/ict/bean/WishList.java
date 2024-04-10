package ict.bean;

import java.util.List;
import java.util.ArrayList;

public class WishList {
    private List<WishEquipment> wishEquipments;

    public WishList() {
        wishEquipments = new ArrayList<WishEquipment>();
    }

    public WishList(List<WishEquipment> wishEquipments) {
        this.wishEquipments = wishEquipments;
    }

    public List<WishEquipment> getList() {
        return wishEquipments;
    }

    public void setWishEquipments(List<WishEquipment> wishEquipments) {
        this.wishEquipments = wishEquipments;
    }
}
