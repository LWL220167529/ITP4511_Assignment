package ict.db;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ict.bean.Device;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeviceDB {

    private String dburl;
    private String dbUser;
    private String dbPassword;

    public DeviceDB(String dburl, String dbUser, String dbPassword) {
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

    public boolean createDeviceTable() {
        boolean created = false;
        String sql = "CREATE TABLE IF NOT EXISTS device (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255)," +
                "image VARCHAR(45))";
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

    public List<Device> getAllDevices() {
        String sql = "SELECT * FROM device";
        List<Device> devices = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String image = rs.getString("image");
                Device device = new Device(id, name, image);
                devices.add(device);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return devices;
    }

    public int[] getAllDeviceIds() {
        List<Device> devices = getAllDevices();
        int[] ids = new int[devices.size()];
        for (int i = 0; i < devices.size(); i++) {
            ids[i] = devices.get(i).getId();
        }
        return ids;
    }

    public Device getDeviceById(int id) {
        Device device = null;
        String sql = "SELECT * FROM device WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int deviceId = rs.getInt("id");
                    String name = rs.getString("name");
                    String image = rs.getString("image");
                    device = new Device(deviceId, name, image);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return device;
    }

    public Device getDeviceByName(String name) {
        Device device = null;
        String sql = "SELECT * FROM device WHERE name = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String deviceName = rs.getString("name");
                    String image = rs.getString("image");
                    device = new Device(id, deviceName, image);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return device;
    }

    public boolean addDevice(Device device) {
        boolean added = false;
        String sql = "INSERT INTO device (name, image) VALUES (?,?)";
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, device.getName());
            ps.setString(2, device.getImage());
            ps.executeUpdate();
            if (ps.getUpdateCount() > 0) {
                added = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return added;
    }

    public boolean updateDeviceImageByName(String deviceName, String newImage) {
        boolean updated = false;
        Device device = getDeviceByName(deviceName);
        int deviceId = device.getId();
        String sql = "UPDATE device SET image = ? WHERE id = ?";
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newImage);
            ps.setInt(2, deviceId);
            ps.executeUpdate();
            if (ps.getUpdateCount() > 0) {
                updated = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return updated;
    }
}
