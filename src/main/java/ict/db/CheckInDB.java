package ict.db;

import ict.bean.CheckIn;
import ict.bean.CheckOut;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import ict.bean.User;
import ict.bean.Users;

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
                    "userName VARCHAR(255), " +
                    "equipmentName VARCHAR(255), " +
                    "quantity INT, " +
                    "campusName VARCHAR(255), " +
                    "image VARCHAR(255), " +
                    "checkInDate DATE," +
                    "confirmedCheckIn BOOLEAN DEFAULT FALSE, " + // Set default to FALSE
                    "damageReport TEXT DEFAULT NULL, " +
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




        
public boolean insertCheckIn(int userId, String userName, String equipmentName, int quantity, String campusName, String image, java.util.Date checkInDate) {
    // The SQL does not include the columns with default values.
    String sql = "INSERT INTO checkin (userId, userName, equipmentName, quantity, campusName, image, checkInDate) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ps.setString(2, userName);
        ps.setString(3, equipmentName);
        ps.setInt(4, quantity);
        ps.setString(5, campusName);
        ps.setString(6, image);
        // Set the checkInDate with the current date passed to the method.
        ps.setDate(7, new java.sql.Date(checkInDate.getTime()));

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}




       public boolean updateCheckIn(CheckIn checkIn) {
            String sql = "UPDATE checkin SET userId = ?, UserName = ?, equipmentName = ?, quantity = ?, CampusName = ?, image = ?, checkInDate = ?, confirmedCheckIn = ?, damageReport = ?, confirmedDamage = ?, deleted = ? WHERE checkInId = ?";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, checkIn.getUserId());
                ps.setString(2, checkIn.getUserName());
                ps.setString(3, checkIn.getEquipmentName());
                ps.setInt(4, checkIn.getQuantity());
                ps.setString(5, checkIn.getCampusName());
                ps.setString(6, checkIn.getImage());  
                ps.setDate(7, new java.sql.Date(checkIn.getCheckInDate().getTime()));
                ps.setBoolean(8, checkIn.isConfirmedCheckIn());
                ps.setString(9, checkIn.getDamageReport());
                ps.setBoolean(10, checkIn.isConfirmedDamage());
                ps.setBoolean(11, checkIn.isDeleted());
                ps.setInt(12, checkIn.getCheckInId());

                int affectedRows = ps.executeUpdate();
                return affectedRows > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }


    public boolean confirmCheckIn(int checkInId) {
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

    

    public CheckIn getCheckInById(int checkInId) {
        String sql = "SELECT * FROM checkin WHERE checkInId = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, checkInId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CheckIn(
                    rs.getInt("checkInId"),
                    rs.getInt("userId"),
                    rs.getString("userName"),
                    rs.getString("equipmentName"),
                    rs.getInt("quantity"),
                    rs.getString("campusName"),
                    rs.getString("image"),
                    rs.getDate("checkInDate"),
                    rs.getBoolean("confirmedCheckIn"),
                    rs.getString("damageReport"),
                    rs.getBoolean("deleted"),
                    rs.getBoolean("confirmedDamage")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
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
                String userName = rs.getString("userName");
                String equipmentName = rs.getString("equipmentName");
                int quantity = rs.getInt("quantity");
                String campusName = rs.getString("campusName");
                String image = rs.getString("image");
                java.sql.Date checkInDate = rs.getDate("checkInDate");
                boolean confirmedCheckIn = rs.getBoolean("confirmedCheckIn");
                String damageReport = rs.getString("damageReport");
                boolean confirmedDamage = rs.getBoolean("confirmedDamage");
                boolean deleted = rs.getBoolean("deleted");

                CheckIn checkIn = new CheckIn(checkInId, userId, userName, equipmentName, quantity, campusName, image, checkInDate, confirmedCheckIn, damageReport, deleted, confirmedDamage);
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
                String userName = rs.getString("userName");
                String equipmentName = rs.getString("equipmentName");
                int quantity = rs.getInt("quantity");
                String campusName = rs.getString("campusName");
                String image = rs.getString("image");
                java.sql.Date checkInDate = rs.getDate("checkInDate");
                String damageReport = rs.getString("damageReport");
                boolean confirmedDamage = rs.getBoolean("confirmedDamage");
    

                CheckIn checkIn = new CheckIn(checkInId, userId, userName, equipmentName, quantity, campusName, image, checkInDate, true, damageReport, false, confirmedDamage);
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
                String userName = rs.getString("userName");
                String equipmentName = rs.getString("equipmentName");
                int quantity = rs.getInt("quantity");
                String campusName = rs.getString("campusName");
                String image = rs.getString("image");
                java.sql.Date checkInDate = rs.getDate("checkInDate");
                String damageReport = rs.getString("damageReport");
                boolean confirmedDamage = rs.getBoolean("confirmedDamage");
    

                CheckIn checkIn = new CheckIn(checkInId, userId, userName, equipmentName, quantity , campusName, image, checkInDate, false, damageReport, false, confirmedDamage);
                checkIns.add(checkIn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return checkIns;
    }
    
   

    public List<CheckIn> getCheckInsWithDamageReports() {
        List<CheckIn> checkIns = new ArrayList<>();
        // SQL query to select records where damageReport is not NULL and deleted is FALSE
        String sql = "SELECT * FROM checkin WHERE damageReport IS NOT NULL AND confirmedDamage = FALSE AND deleted = FALSE";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int checkInId = rs.getInt("checkInId");
                int userId = rs.getInt("userId");
                String userName = rs.getString("userName");
                String equipmentName = rs.getString("equipmentName");
                int quantity = rs.getInt("quantity");
                String campusName = rs.getString("campusName");
                String image = rs.getString("image");
                java.sql.Date checkInDate = rs.getDate("checkInDate");
                boolean confirmedCheckIn = rs.getBoolean("confirmedCheckIn");
                String damageReport = rs.getString("damageReport");
                boolean confirmedDamage = rs.getBoolean("confirmedDamage");
                boolean deleted = rs.getBoolean("deleted");

                CheckIn checkIn = new CheckIn(checkInId, userId, userName, equipmentName, quantity, campusName, image, checkInDate, confirmedCheckIn, damageReport, deleted, confirmedDamage);
                checkIns.add(checkIn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return checkIns;
    }

}

