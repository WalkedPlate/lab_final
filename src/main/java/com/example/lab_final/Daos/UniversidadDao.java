package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Universidad;
import com.example.lab_final.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UniversidadDao extends DaoBase{


    public Universidad obtenerUniversidad(int idUniversidad){

        UsuarioDao usuarioDao = new UsuarioDao();
        Universidad universidad = new Universidad();

        String sql = "select * from universidad where iduniversidad = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,idUniversidad);

            try(ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    universidad.setIdUniversidad(rs.getInt("iduniversidad"));
                    universidad.setNombre(rs.getString("nombre"));
                    universidad.setLogoUrl(rs.getString("logo_url"));
                    universidad.setAdministrador(usuarioDao.obtenerUsuario(rs.getInt("idadministrador")));
                    universidad.setFechaRegistro(rs.getString("fecha_registro"));
                    universidad.setFechaEdicion(rs.getString("fecha_edicion"));
                }
                else {
                    universidad = null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return universidad;
    }
}
