package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Rol;
import com.example.lab_final.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RolDao extends DaoBase{


    public Rol obtenerRol(int idRol){

        Rol rol = new Rol();

        String sql = "select * from rol where idrol = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,idRol);

            try(ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    rol.setIdRol(rs.getInt("idrol"));
                    rol.setNombre(rs.getString("nombre"));
                }
                else{
                    rol = null;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rol;
    }

}
