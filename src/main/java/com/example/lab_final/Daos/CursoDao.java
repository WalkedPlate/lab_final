package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CursoDao extends DaoBase{

    public Curso obtenerCurso(int idCurso){

        FacultadDao facultadDao = new FacultadDao();
        Curso curso = new Curso();

        String sql = "select * from curso where idcurso = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,idCurso);

            try(ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    curso.setIdCurso(rs.getInt("idcurso"));
                    curso.setCodigo(rs.getString("codigo"));
                    curso.setNombre(rs.getString("nombre"));
                    curso.setFacultad(facultadDao.obtenerFacultad(rs.getInt("idfacultad")));
                    curso.setFechaRegistro(rs.getString("fecha_registro"));
                    curso.setFechaEdicion(rs.getString("fecha_edicion"));
                }
                else {
                    curso = null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return curso;
    }
}
