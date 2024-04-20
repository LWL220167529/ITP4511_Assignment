package ict.run;

import ict.db.ReserveDB;

public class CreateReserve {
    public static void main(String[] args) {
        ReserveDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "";

        db = new ReserveDB(dbUrl, dbUser, dbPassword);
        
        db.createReserveTable();

        System.out.println("Creating reserve table");
    }
    
}
