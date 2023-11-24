package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Facultad;
import com.example.lab_final.Beans.Universidad;
import com.example.lab_final.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultadDao extends DaoBase{

    public Facultad obtenerFacultad(int idFacultad){

        UniversidadDao universidadDao = new UniversidadDao();
        Facultad facultad = new Facultad();

        String sql = "select * from facultad where idfacultad = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,idFacultad);

            try(ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    facultad.setIdFacultad(rs.getInt("idfacultad"));
                    facultad.setNombre(rs.getString("nombre"));
                    facultad.setUniversidad(universidadDao.obtenerUniversidad(rs.getInt("iduniversidad")));
                    facultad.setFechaRegistro(rs.getString("fecha_registro"));
                    facultad.setFechaEdicion(rs.getString("fecha_edicion"));
                }
                else {
                    facultad = null;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return facultad;
    }

}
