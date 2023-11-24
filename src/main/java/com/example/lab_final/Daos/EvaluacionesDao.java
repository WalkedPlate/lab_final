package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Curso;
import com.example.lab_final.Beans.Evaluaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EvaluacionesDao extends DaoBase {

    public Evaluaciones obtenerEvaluaciones(int idevaluaciones) {

        CursoDao cursoDao = new CursoDao();
        SemestreDao semestreDao = new SemestreDao();

        Evaluaciones evaluaciones = new Evaluaciones();

        String sql = "select * from evaluaciones where idevaluaciones = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idevaluaciones);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    evaluaciones.setIdEvaluacion(rs.getInt("idevaluaciones"));
                    evaluaciones.setNombreEstudiante(rs.getString("nombre_estudiante"));
                    evaluaciones.setCodigoEstudiante(rs.getString("codigo_estudiante"));
                    evaluaciones.setCorreoEstudiante(rs.getString("correo_estudiante"));
                    evaluaciones.setNota(rs.getInt("nota"));
                    evaluaciones.setCurso(cursoDao.obtenerCurso(rs.getInt("idcurso")));
                    evaluaciones.setSemestre(semestreDao.obtenerSemestre(rs.getInt("idsemestre")));
                    evaluaciones.setFechaRegistro(rs.getString("fecha_registro"));
                    evaluaciones.setFechaEdicion(rs.getString("fecha_edicion"));
                } else {
                    evaluaciones = null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return evaluaciones;
    }
}