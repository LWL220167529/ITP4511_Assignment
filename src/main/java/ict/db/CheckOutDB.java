package ict.db;

import ict.bean.CheckOut;
import ict.db.Database;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class CheckOutDB {
    private Database db;

    public CheckOutDB(Database db) {
        this.db = db;
    }

    private Connection getConnection() throws SQLException, IOException {
        return db.getConnection();
    }

    public boolean createCheckOutTable() throws IOException {
        String sql = "CREATE TABLE IF NOT EXISTS checkout (" +
                "checkOutId INT PRIMARY KEY AUTO_INCREMENT, " +
                "userId INT, " +
                "userName VARCHAR(255), " +
                "equipmentName VARCHAR(255), " +
                "quantity INT, " +
                "campusName VARCHAR(255), " +
                "image VARCHAR(255), " +
                "checkOutDate DATE DEFAULT (CURDATE()), " +
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


    public boolean insertCheckOutForUser(int userId,  String userName, String equipmentName, int quantity, String campusName, String image) throws IOException {
        String sql = "INSERT INTO checkout (userId, userName, equipmentName, quantity, campusName, image) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, userName);
              ps.setString(3, equipmentName);
            ps.setInt(4, quantity);
            ps.setString(5, campusName);
            ps.setString(6, image);



            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
   public boolean returnItem(int checkOutId) throws IOException {
    String sql = "UPDATE checkout SET returned = TRUE WHERE checkOutId = ?";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, checkOutId);

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}

    public boolean confirmCheckOut(int checkOutId) throws IOException {
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

 public List<CheckOut> getAllConfirmedCheckOuts( ) throws IOException {
    List<CheckOut> checkOuts = new ArrayList<>();
    String sql = "SELECT * FROM checkout WHERE confirmedCheckout = TRUE AND returned = FALSE ";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int checkOutid = rs.getInt("checkOutid");
                String userName = rs.getString("userName");
                int userId = rs.getInt("userId");
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

public List<CheckOut> getConfirmedCheckOutsForUser(int userId) throws IOException {
    List<CheckOut> checkOuts = new ArrayList<>();
    String sql = "SELECT * FROM checkout WHERE confirmedCheckout = TRUE AND returned = FALSE AND userId = ?";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, userId); // Set the userID parameter in the query
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
    
    
    
    
    public List<CheckOut> getAllUnconfirmedCheckOuts() throws IOException {
            List<CheckOut> checkOuts = new ArrayList<>();
            String sql = "SELECT * FROM checkout WHERE confirmedCheckout = FALSE AND returned = FALSE AND deleted = false";

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



    public boolean deleteCheckOut(int checkOutId) throws IOException {
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
