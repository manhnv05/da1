/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package da.service;

import da.model.VaiTro;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class VaiTroService {
    private Connection conn;

    public VaiTroService() {
        conn = connectDB.getConnection();
    }
    
    public ArrayList<VaiTro> getAllVaiTro() {
    String SQL = "SELECT id, tenVaiTro FROM VaiTro";
    ArrayList<VaiTro> vaiTroList = new ArrayList<>();

    try (PreparedStatement ps = conn.prepareStatement(SQL);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            vaiTroList.add(new VaiTro(rs.getInt("id"), rs.getString("tenVaiTro")));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return vaiTroList;
}

}
