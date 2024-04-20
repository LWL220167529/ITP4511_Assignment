package ict.db;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ict.bean.Equipment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EquipmentDB {

    private String dburl;
    private String dbUser;
    private String dbPassword;

    public EquipmentDB(String dburl, String dbUser, String dbPassword) {
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

    public boolean createEquipmentTable() {
        boolean created = false;
        String sql = "CREATE TABLE IF NOT EXISTS equipment (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255)," +
                "image VARCHAR(45)" +
                ")";
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

    public List<Equipment> getAllEquipments() {
        String sql = "SELECT * FROM equipment";
        List<Equipment> equipments = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String image = rs.getString("image");
                Equipment equipment = new Equipment(id, name, image);
                equipments.add(equipment);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return equipments;
    }

    public int[] getAllEquipmentIds() {
        List<Equipment> equipments = getAllEquipments();
        int[] ids = new int[equipments.size()];
        for (int i = 0; i < equipments.size(); i++) {
            ids[i] = equipments.get(i).getId();
        }
        return ids;
    }

    public Equipment getEquipmentById(int id) {
        Equipment equipment = null;
        String sql = "SELECT * FROM equipment WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int equipmentId = rs.getInt("id");
                    String name = rs.getString("name");
                    String image = rs.getString("image");
                    equipment = new Equipment(equipmentId, name, image);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return equipment;
    }

    public Equipment getEquipmentByName(String name) {
        Equipment equipment = null;
        String sql = "SELECT * FROM equipment WHERE name = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String equipmentName = rs.getString("name");
                    String image = rs.getString("image");
                    equipment = new Equipment(id, equipmentName, image);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return equipment;
    }

    public boolean addEquipment(Equipment equipment) {
        boolean added = false;
        String sql = "INSERT INTO equipment (name, image) VALUES (?,?)";
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, equipment.getName());
            ps.setString(2, equipment.getImage());
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

    public boolean updateEquipmentImageByName(String equipmentName, String newImage) {
        boolean updated = false;
        Equipment equipment = getEquipmentByName(equipmentName);
        int equipmentId = equipment.getId();
        String sql = "UPDATE equipment SET image = ? WHERE id = ?";
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newImage);
            ps.setInt(2, equipmentId);
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
