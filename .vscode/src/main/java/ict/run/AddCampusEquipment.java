package ict.run;

import ict.bean.CampusEquipment;
import ict.db.CampusEquipmentDB;
import ict.db.EquipmentDB;

import java.util.Random;

public class AddCampusEquipment {
    private final static String[] defaultCampus = { "CW", "LWL", "ST", "TM", "TY" };

    public static void main(String[] args) {
        CampusEquipmentDB cddb;
        EquipmentDB ddb;
        Random random = new Random();
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        cddb = new CampusEquipmentDB(dbUrl, dbUser, dbPassword);

        ddb = new EquipmentDB(dbUrl, dbUser, dbPassword);

        int [] EquipmentIds;

        EquipmentIds = ddb.getAllEquipmentIds();

        for (String campus : defaultCampus) {
            for (int EquipmentId : EquipmentIds) {
                CampusEquipment campusEquipment = new CampusEquipment(campus, EquipmentId, random.nextInt(11), "available");
                if (cddb.addCampusEquipment(campusEquipment)) {
                    System.out.println("Campus Equipment " + campus + " added.");
                } else {
                    System.out.println("Campus Equipment " + campus + " exists.");
                }
            }
        }

        cddb.updateStatusForQuantityLessThanOne();

        System.out.println("addCampusEquipment() done.");
    }
}
