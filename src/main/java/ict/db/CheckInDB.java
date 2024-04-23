package ict.db;

import ict.bean.CheckIn;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class CheckInDB {
    private String dburl;
    private String dbUser;
    private String dbPassword;

    public CheckInDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(); // Log or handle the class not found exception
            throw new SQLException("Driver not found", ex);
        }
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

        public boolean createCheckInTable() {
            String sql = "CREATE TABLE IF NOT EXISTS checkin (" +
                    "checkInId INT PRIMARY KEY AUTO_INCREMENT, " +
                    "userId INT, " +
                    "reserveID INT, " +
                    "equipmentId INT, " +
                    "campusId VARCHAR(255), " +
                    "image VARCHAR(255), " +
                    "checkInDate DATE, " +
                    "confirmedCheckIn BOOLEAN DEFAULT FALSE, " + // Set default to FALSE
                    "damageReport TEXT DEFAULT NULL,, " +
                    "confirmedDamage BOOLEAN DEFAULT FALSE, " + // Set default to FALSE
                    "deleted BOOLEAN DEFAULT FALSE)"; // Set default to FALSE

            try (Connection con = getConnection();
                 Statement stmt = con.createStatement()) {
                stmt.execute(sql);
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }



        public boolean insertCheckIn(CheckIn checkIn) {
            // Correct the SQL query to remove the extra comma
            String sql = "INSERT INTO checkin (userId, reserveID, equipmentId, campusId, image, checkInDate) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, checkIn.getUserId());
                ps.setInt(2, checkIn.getReserveID());
                ps.setInt(3, checkIn.getEquipmentId());
                ps.setString(4, checkIn.getCampusId());
                ps.setString(5, checkIn.getImage());
                ps.setDate(6, new java.sql.Date(checkIn.getCheckInDate().getTime()));

                int affectedRows = ps.executeUpdate();
                return affectedRows > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }


       public boolean updateCheckIn(CheckIn checkIn) {
            String sql = "UPDATE checkin SET userId = ?, reserveID = ?, equipmentId = ?, campusId = ?, image = ?, checkInDate = ?, confirmedCheckIn = ?, damageReport = ?, confirmedDamage = ?, deleted = ? WHERE checkInId = ?";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, checkIn.getUserId());
                ps.setInt(2, checkIn.getReserveID());
                ps.setInt(3, checkIn.getEquipmentId());
                ps.setString(4, checkIn.getCampusId());
                ps.setString(5, checkIn.getImage());  
                ps.setDate(6, new java.sql.Date(checkIn.getCheckInDate().getTime()));
                ps.setBoolean(7, checkIn.isConfirmedCheckIn());
                ps.setString(8, checkIn.getDamageReport());
                ps.setBoolean(9, checkIn.isConfirmedDamage());
                ps.setBoolean(10, checkIn.isDeleted());
                ps.setInt(11, checkIn.getCheckInId());

                int affectedRows = ps.executeUpdate();
                return affectedRows > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }


    public boolean updateCheckInStatusToConfirmed(int checkInId) {
        // Assuming 'confirmedCheckIn' is a BOOLEAN column in your database
        String sql = "UPDATE checkin SET confirmedCheckIn = TRUE WHERE checkInId = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, checkInId);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean reportDamage(int checkInId, String damageReport) {
    String sql = "UPDATE checkin SET damageReport = ? WHERE checkInId = ?";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, damageReport); // Set the new damage report text
        ps.setInt(2, checkInId); // Specify which check-in to update

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0; // Return true if the update was successful
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false; // Return false if there was a SQL exception
    }
   }

    
    
    
    public boolean confirmDamage(int checkInId) {
    String sql = "UPDATE checkin SET confirmedDamage = ? WHERE checkInId = ?";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setBoolean(1, true); // Set the confirmedDamage flag to true
        ps.setInt(2, checkInId);

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
  }

    
    
    
    public boolean deleteCheckIn(int checkInId) {
        String sql = "DELETE FROM checkin WHERE checkInId = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, checkInId);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    




    public List<CheckIn> getAllCheckIns() {
        List<CheckIn> checkIns = new ArrayList<>();
        String sql = "SELECT * FROM checkin";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int checkInId = rs.getInt("checkInId");
                int userId = rs.getInt("userId");
                int reserveID = rs.getInt("reserveID");
                int equipmentId = rs.getInt("equipmentId");
                String campusId = rs.getString("campusId");
                String image = rs.getString("image");
                java.sql.Date checkInDate = rs.getDate("checkInDate");
                boolean confirmedCheckIn = rs.getBoolean("confirmedCheckIn");
                String damageReport = rs.getString("damageReport");
                boolean confirmedDamage = rs.getBoolean("confirmedDamage");
                boolean deleted = rs.getBoolean("deleted");

                CheckIn checkIn = new CheckIn(checkInId, userId, reserveID, equipmentId, campusId, image, checkInDate, confirmedCheckIn, damageReport, deleted, confirmedDamage);
                checkIns.add(checkIn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return checkIns;
    }
    
    public List<CheckIn> getAllConfirmedCheckIns() {
        List<CheckIn> checkIns = new ArrayList<>();
        // Include the condition to check for records where deleted is FALSE
        String sql = "SELECT * FROM checkin WHERE confirmedCheckIn = TRUE AND deleted = FALSE";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int checkInId = rs.getInt("checkInId");
                int userId = rs.getInt("userId");
                int reserveID = rs.getInt("reserveID");
                int equipmentId = rs.getInt("equipmentId");
                String campusId = rs.getString("campusId");
                String image = rs.getString("image");
                java.sql.Date checkInDate = rs.getDate("checkInDate");
                String damageReport = rs.getString("damageReport");
                boolean confirmedDamage = rs.getBoolean("confirmedDamage");
    

                CheckIn checkIn = new CheckIn(checkInId, userId, reserveID, equipmentId, campusId, image, checkInDate, true, damageReport, false, confirmedDamage);
                checkIns.add(checkIn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return checkIns;
    }


    
    

    public List<CheckIn> getAllUnconfirmedCheckIns() {
        List<CheckIn> checkIns = new ArrayList<>();
        // Include the condition to check for records where deleted is FALSE
        String sql = "SELECT * FROM checkin WHERE confirmedCheckIn = FALSE AND deleted = FALSE";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int checkInId = rs.getInt("checkInId");
                int userId = rs.getInt("userId");
                int reserveID = rs.getInt("reserveID");
                int equipmentId = rs.getInt("equipmentId");
                String campusId = rs.getString("campusId");
                String image = rs.getString("image");
                java.sql.Date checkInDate = rs.getDate("checkInDate");
                String damageReport = rs.getString("damageReport");
                boolean confirmedDamage = rs.getBoolean("confirmedDamage");
    

                CheckIn checkIn = new CheckIn(checkInId, userId, reserveID, equipmentId, campusId, image, checkInDate, false, damageReport, false, confirmedDamage);
                checkIns.add(checkIn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return checkIns;
    }

}

