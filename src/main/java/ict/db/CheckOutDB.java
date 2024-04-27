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
                "checkOutId INT PRIMARY KEY AUTO_INCREMENT, " +
                "userId INT, " +
                "userName VARCHAR(255), " +
                "equipmentName VARCHAR(255), " +
                "quantity INT, " +
                "campusName VARCHAR(255), " +
                "image VARCHAR(255), " +
                "checkOutDate DATE, " +
                "returned BOOLEAN DEFAULT FALSE, " +
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


    public boolean insertCheckOutForUser(int userId,  String userName, String equipmentName, int quantity, String campusName, String image, java.util.Date checkOutDate) {
        String sql = "INSERT INTO checkout (userId, userName, equipmentName, quantity, campusName, image, checkOutDate, returned, confirmedCheckOut, deleted) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?,FALSE,  FALSE, FALSE)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, userName);
              ps.setString(3, equipmentName);
            ps.setInt(4, quantity);
            ps.setString(5, campusName);
            ps.setString(6, image);

            // Convert java.util.Date to java.sql.Date
            java.sql.Date checkOutDateSql = new java.sql.Date(checkOutDate.getTime());
            ps.setDate(7, checkOutDateSql);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
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

 public List<CheckOut> getAllConfirmedCheckOuts(int userId) {
    List<CheckOut> checkOuts = new ArrayList<>();
    String sql = "SELECT * FROM checkout WHERE confirmedCheckout = TRUE AND returned = FALSE AND deleted = FALSE AND userId = ?";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, userId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int checkOutid = rs.getInt("checkOutid");
                String userName = rs.getString("userName");
                String equipmentName = rs.getString("equipmentName");
                int quantity = rs.getInt("quantity");
                String campusName = rs.getString("campusName");
                String image = rs.getString("image");
                java.sql.Date checkOutDate = rs.getDate("checkOutDate");

                CheckOut checkOut = new CheckOut(checkOutid, userId, userName, equipmentName, quantity, campusName, image, checkOutDate, false, true, false);
                checkOuts.add(checkOut);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return checkOuts;
}

    
    
    
    
    
    public List<CheckOut> getAllUnconfirmedCheckOuts() {
            List<CheckOut> checkOuts = new ArrayList<>();
            String sql = "SELECT * FROM checkout WHERE confirmedCheckout = FALSE AND returned = FALSE AND deleted = FALSE";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int checkOutId = rs.getInt("checkOutId");
                    int userId = rs.getInt("userId");
                    String userName = rs.getString("userName");
                    String equipmentName = rs.getString("equipmentName");
                    int quantity = rs.getInt("quantity");
                    String campusName = rs.getString("campusName");
                    String image = rs.getString("image");
                    java.sql.Date checkOutDate = rs.getDate("checkOutDate");
 
                    CheckOut checkOut = new CheckOut(checkOutId, userId, userName, equipmentName, quantity, campusName,  image, checkOutDate, false, false, false);
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
