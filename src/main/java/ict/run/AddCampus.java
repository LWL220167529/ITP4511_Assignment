package ict.run;

import ict.bean.Campus;
import ict.db.CampusDB;

public class AddCampus {
    public static void main(String[] args) {
        String[] defaultCampus = { "CW", "LWL", "ST", "TM", "TY" };
        String[] defaultCampusName = { "Chai Wan", "Lee Wai Lee", "Sha Tin", "Tuen Mun", "Tsing Yi", };
        CampusDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "";

        db = new CampusDB(dbUrl, dbUser, dbPassword);

        for (int i = 0; i < defaultCampus.length; i++) {
            Campus campus = new Campus(defaultCampus[i], defaultCampusName[i]);
            db.addCampus(campus);
        System.out.println("Adding campus " + defaultCampus[i] + " done.");
        }

    }

}
