package ict.bean;

import java.util.ArrayList;
import java.util.List;

public class UserReserves {
    private List<UserReserve> userReserves;

    public UserReserves() {
        userReserves = new ArrayList<UserReserve>();
    }

    public List<UserReserve> getList() {
        return userReserves;
    }
    
    public void setUserReserves(List<UserReserve> userReserves) {
        this.userReserves = userReserves;
    }

    public boolean addUserReserve(UserReserve userReserve) {
        boolean isAdded = true;
        for (UserReserve ur : userReserves) {
            System.out.println(ur.getCampusEquipmentId() == userReserve.getCampusEquipmentId());
            if (ur.getCampusEquipmentId() == userReserve.getCampusEquipmentId()) {
                return isAdded = false;
            }
        }
        userReserves.add(userReserve);
        return isAdded;
    }

    public String removeUserReserve(int userReserve) {
        UserReserve removeReserve =userReserves.remove(userReserve);
        return removeReserve.getEquipmentName();
    }

    public void updateUserReserve(int id, int quantity) {
        UserReserve updatedUserReserve = null;
        int index = 0;
        for (UserReserve ur : userReserves) {
            if (ur.getCampusEquipmentId() == id) {
                ur.setQuantity(quantity);
                updatedUserReserve = ur;
                break;
            }
            index++;
        }
        if (updatedUserReserve != null) {
            userReserves.remove(updatedUserReserve);
            userReserves.add(index, updatedUserReserve);
        }
    }

    public boolean isUserReserveEmpty() {
        return userReserves.isEmpty();
    }
}
