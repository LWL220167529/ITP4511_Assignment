package ict.db;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ict.bean.CampusDevice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CampusDeviceDB {

    private String dburl;
    private String dbUser;
    private String dbPassword;

    public CampusDeviceDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public boolean createCampusDeviceTable() {
        boolean created = false;
        String sql = "CREATE TABLE IF NOT EXISTS campus_device (" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "device_id INT, " +
            "campus VARCHAR(255), " +
            "quantity INT, " +
            "status VARCHAR(255), " +
            "FOREIGN KEY (device_id) REFERENCES device(id))";
        try (Connection con = getConnection();
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
            created = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return created;
    }

    public List<CampusDevice> getCampusDevices() {
        List<CampusDevice> campusDevices = new ArrayList<>();
        String sql = "SELECT ad.id, ad.device_id, d.name, d.image, ad.campus, ad.quantity, ad.status " +
                 "FROM campus_device ad " +
                 "JOIN device d ON ad.device_id = d.id";
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CampusDevice campusDevice = new CampusDevice();
                campusDevice.setId(rs.getInt("id"));
                campusDevice.setDeviceId(rs.getInt("device_id"));
                campusDevice.setDeviceName(rs.getString("name"));
                campusDevice.setDeviceImage(rs.getString("image"));
                campusDevice.setCampus(rs.getString("campus"));
                campusDevice.setQuantity(rs.getInt("quantity"));
                campusDevice.setStatus(rs.getString("status"));
                campusDevices.add(campusDevice);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return campusDevices;
    }

    public CampusDevice getCampusDeviceById(int id) {
        CampusDevice campusDevice = null;
        String sql = "SELECT ad.id, ad.device_id, d.name, d.image, ad.campus, ad.quantity, ad.status " +
                     "FROM campus_device ad " +
                     "JOIN device d ON ad.device_id = d.id " +
                     "WHERE ad.id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    campusDevice = new CampusDevice();
                    campusDevice.setId(rs.getInt("id"));
                    campusDevice.setDeviceId(rs.getInt("device_id"));
                    campusDevice.setDeviceName(rs.getString("name"));
                    campusDevice.setDeviceImage(rs.getString("image"));
                    campusDevice.setCampus(rs.getString("campus"));
                    campusDevice.setQuantity(rs.getInt("quantity"));
                    campusDevice.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return campusDevice;
    }

    public List<CampusDevice> getCampusDevicesByCampus(String campus) {
        List<CampusDevice> campusDevices = new ArrayList<>();
        String sql = "SELECT ad.id, ad.device_id, d.name, d.image, ad.campus, ad.quantity, ad.status " +
                 "FROM campus_device ad " +
                 "JOIN device d ON ad.device_id = d.id " +
                 "WHERE ad.campus = ?";
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, campus);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CampusDevice campusDevice = new CampusDevice();
                    campusDevice.setId(rs.getInt("id"));
                    campusDevice.setDeviceId(rs.getInt("device_id"));
                    campusDevice.setDeviceName(rs.getString("name"));
                    campusDevice.setDeviceImage(rs.getString("image"));
                    campusDevice.setCampus(rs.getString("campus"));
                    campusDevice.setQuantity(rs.getInt("quantity"));
                    campusDevice.setStatus(rs.getString("status"));
                    campusDevices.add(campusDevice);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return campusDevices;
    }

    public boolean addCampusDevice(CampusDevice campusDevice) {
        boolean added = false;
        String sql = "INSERT INTO campus_device (device_id, campus, quantity, status) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, campusDevice.getDeviceId());
            ps.setString(2, campusDevice.getCampus());
            ps.setInt(3, campusDevice.getQuantity());
            ps.setString(4, campusDevice.getStatus());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                added = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return added;
    }

    public boolean updateStatusForQuantityLessThanOne() {
        boolean updated = false;
        String sql = "SELECT id, quantity FROM campus_device WHERE quantity < 1";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String updateSql = "UPDATE campus_device SET status = 'unavailable' WHERE id = ?";
                try (PreparedStatement updatePs = con.prepareStatement(updateSql)) {
                    updatePs.setInt(1, id);
                    int rowsAffected = updatePs.executeUpdate();
                    if (rowsAffected > 0) {
                        updated = true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return updated;
    }
}
