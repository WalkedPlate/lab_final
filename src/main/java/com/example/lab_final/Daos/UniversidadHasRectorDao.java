package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Curso;
import com.example.lab_final.Beans.UniversidadHasRector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UniversidadHasRectorDao extends DaoBase{

    public UniversidadHasRector obtenerUniversidadHasRector(int idUniversidad, int idRector) {

        UniversidadDao universidadDao = new UniversidadDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        UniversidadHasRector universidadHasRector = new UniversidadHasRector();

        String sql = "select * from universidad_has_rector where iduniversidad = ? and idrector = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUniversidad);
            pstmt.setInt(2, idRector);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    universidadHasRector.setRector(usuarioDao.obtenerUsuario(rs.getInt("idrector")));
                    universidadHasRector.setUniversidad(universidadDao.obtenerUniversidad(rs.getInt("iduniversidad")));
                } else {
                    universidadHasRector = null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return universidadHasRector;
    }

}
