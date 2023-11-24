package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Curso;
import com.example.lab_final.Beans.FacultadHasDecano;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultadHasDecanoDao extends DaoBase{

    public FacultadHasDecano obtenerFacultadHasDecano(int idFacultad, int idDecano) {

        FacultadDao facultadDao = new FacultadDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        FacultadHasDecano facultadHasDecano = new FacultadHasDecano();

        String sql = "select * from facultad_has_decano where idfacultad = ? and iddecano = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idFacultad);
            pstmt.setInt(2, idDecano);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    facultadHasDecano.setDecano(usuarioDao.obtenerUsuario(rs.getInt("iddecano")));
                    facultadHasDecano.setFacultad(facultadDao.obtenerFacultad(rs.getInt("idfacultad")));
                } else {
                    facultadHasDecano = null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return facultadHasDecano;
    }
}
