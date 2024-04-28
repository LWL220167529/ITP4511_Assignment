package ict.db;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ict.bean.CampusEquipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CampusEquipmentDB {

    private Database db;

    public CampusEquipmentDB(Database db) {
        this.db = db;
    }

    public boolean createCampusEquipmentTable() {
        boolean created = false;
        String sql = "CREATE TABLE IF NOT EXISTS campus_equipment (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "equipment_id INT, " +
                "campus VARCHAR(25), " +
                "quantity INT, " +
                "status VARCHAR(255), " +
                "FOREIGN KEY (campus) REFERENCES campus(Id), " +
                "FOREIGN KEY (equipment_id) REFERENCES equipment(id))";
        try (Connection con = db.getConnection();
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

    public List<CampusEquipment> getCampusEquipments() {
        List<CampusEquipment> campusEquipments = new ArrayList<>();
        String sql = "SELECT ad.id, ad.equipment_id, d.name, d.image, ad.campus, ad.quantity, ad.status " +
                "FROM campus_equipment ad " +
                "JOIN equipment d ON ad.equipment_id = d.id";
        try (Connection con = db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CampusEquipment campusEquipment = new CampusEquipment();
                campusEquipment.setId(rs.getInt("id"));
                campusEquipment.setEquipmentId(rs.getInt("equipment_id"));
                campusEquipment.setEquipmentName(rs.getString("name"));
                campusEquipment.setEquipmentImage(rs.getString("image"));
                campusEquipment.setCampus(rs.getString("campus"));
                campusEquipment.setQuantity(rs.getInt("quantity"));
                campusEquipment.setStatus(rs.getString("status"));
                campusEquipments.add(campusEquipment);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return campusEquipments;
    }

    public CampusEquipment getCampusEquipmentById(int id) {
        CampusEquipment campusEquipment = null;
        String sql = "SELECT ad.id, ad.equipment_id, d.name, d.image, ad.campus, ad.quantity, ad.status " +
                "FROM campus_equipment ad " +
                "JOIN equipment d ON ad.equipment_id = d.id " +
                "WHERE ad.id = ?";
        try (Connection con = db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    campusEquipment = new CampusEquipment();
                    campusEquipment.setId(rs.getInt("id"));
                    campusEquipment.setEquipmentId(rs.getInt("equipment_id"));
                    campusEquipment.setEquipmentName(rs.getString("name"));
                    campusEquipment.setEquipmentImage(rs.getString("image"));
                    campusEquipment.setCampus(rs.getString("campus"));
                    campusEquipment.setQuantity(rs.getInt("quantity"));
                    campusEquipment.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return campusEquipment;
    }

    public List<CampusEquipment> getCampusEquipmentsByCampus(String campus) {
        List<CampusEquipment> campusEquipments = new ArrayList<>();
        String sql = "SELECT ad.id, ad.equipment_id, d.name, d.image, ad.campus, ad.quantity, ad.status " +
                "FROM campus_equipment ad " +
                "JOIN equipment d ON ad.equipment_id = d.id " +
                "WHERE ad.campus = ?";
        try (Connection con = db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, campus);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CampusEquipment campusEquipment = new CampusEquipment();
                    campusEquipment.setId(rs.getInt("id"));
                    campusEquipment.setEquipmentId(rs.getInt("equipment_id"));
                    campusEquipment.setEquipmentName(rs.getString("name"));
                    campusEquipment.setEquipmentImage(rs.getString("image"));
                    campusEquipment.setCampus(rs.getString("campus"));
                    campusEquipment.setQuantity(rs.getInt("quantity"));
                    campusEquipment.setStatus(rs.getString("status"));
                    campusEquipments.add(campusEquipment);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return campusEquipments;
    }

    public boolean addCampusEquipment(CampusEquipment campusEquipment) {
        boolean added = false;
        String sql = "INSERT INTO campus_equipment (equipment_id, campus, quantity, status) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection con = db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, campusEquipment.getEquipmentId());
            ps.setString(2, campusEquipment.getCampus());
            ps.setInt(3, campusEquipment.getQuantity());
            ps.setString(4, campusEquipment.getStatus());

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
        String sql = "SELECT id, quantity FROM campus_equipment WHERE quantity < 1";
        try (Connection con = db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String updateSql = "UPDATE campus_equipment SET status = 'unavailable' WHERE id = ?";
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

    public boolean updateCampusEquipmentQuantity(int id, int quantity) {
        boolean updated = false;
        String sql = "UPDATE campus_equipment SET quantity = ? WHERE id = ?";
        try (Connection con = db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
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
