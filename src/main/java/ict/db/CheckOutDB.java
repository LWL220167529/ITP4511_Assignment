package ict.db;

import ict.bean.CheckOut;
import java.sql.*;
import java.util.*;

public class CheckOutDB {
    private String dburl;
    private String dbUser;
    private String dbPassword;

    public CheckOutDB(String dburl, String dbUser, String dbPassword) {
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

    public boolean createCheckOutTable() {
        String sql = "CREATE TABLE IF NOT EXISTS checkout (" +
                "checkOutid INT PRIMARY KEY AUTO_INCREMENT, " +
                "userId INT, " +
                "reserveID INT, " +
                "equipmentId INT, " +
                "campusId VARCHAR(255), " +
                "image VARCHAR(255), " +
                "checkOutDate DATE, " +
                "confirmedCheckOut BOOLEAN DEFAULT FALSE, " +
                "deleted BOOLEAN DEFAULT FALSE)";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean insertCheckOut(CheckOut checkOut) {
        String sql = "INSERT INTO checkout (userId, reserveID, equipmentId, campusId, image, checkOutDate, confirmedCheckOut, deleted) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, checkOut.getUserId());
            ps.setInt(2, checkOut.getReserveID());
            ps.setInt(3, checkOut.getEquipmentId());
            ps.setString(4, checkOut.getCampusId());
            ps.setString(5, checkOut.getImage());
            ps.setDate(6, new java.sql.Date(checkOut.getCheckOutDate().getTime()));
 
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean confirmCheckOut(int checkOutId) {
        String sql = "UPDATE checkout SET confirmedCheckout = TRUE WHERE checkOutid = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, checkOutId);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;  // True if the row is updated, false otherwise
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<CheckOut> getAllConfirmedAndNotDeletedCheckOuts() {
    List<CheckOut> checkOuts = new ArrayList<>();
    String sql = "SELECT * FROM checkout WHERE confirmedCheckout = TRUE AND deleted = FALSE";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int checkOutid = rs.getInt("checkOutid");
            int userId = rs.getInt("userId");
            int reserveID = rs.getInt("reserveID");
            int equipmentId = rs.getInt("equipmentId");
            String campusId = rs.getString("campusId");
            String image = rs.getString("image");
            java.sql.Date checkOutDate = rs.getDate("checkOutDate");
            boolean deleted = rs.getBoolean("deleted");

            // Assuming a constructor or setters to handle the data appropriately
            CheckOut checkOut = new CheckOut(checkOutid, userId, equipmentId, reserveID, campusId, image, checkOutDate, true, deleted);
            checkOuts.add(checkOut);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return checkOuts;
}

    
    
    
    
    
    public List<CheckOut> getAllUnconfirmedCheckOuts() {
            List<CheckOut> checkOuts = new ArrayList<>();
            String sql = "SELECT * FROM checkout WHERE confirmedCheckout = FALSE AND deleted = FALSE";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int checkOutid = rs.getInt("checkOutid");
                    int userId = rs.getInt("userId");
                    int reserveID = rs.getInt("reserveID");
                    int equipmentId = rs.getInt("equipmentId");
                    String campusId = rs.getString("campusId");
                    String image = rs.getString("image");
                    java.sql.Date checkOutDate = rs.getDate("checkOutDate");
                    boolean deleted = rs.getBoolean("deleted");

                    CheckOut checkOut = new CheckOut(checkOutid, userId, equipmentId, reserveID, campusId, image, checkOutDate, false, deleted);
                    checkOuts.add(checkOut);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return checkOuts;
        }



    public boolean deleteCheckOut(int checkOutId) {
        String sql = "UPDATE checkout SET deleted = TRUE WHERE checkOutid = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, checkOutId);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
