package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Curso;
import com.example.lab_final.Beans.Evaluaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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


    public ArrayList<Evaluaciones> listaEvaluaciones(int idCurso, int idSemestre) {

        CursoDao cursoDao = new CursoDao();
        SemestreDao semestreDao = new SemestreDao();

        ArrayList<Evaluaciones> lista = new ArrayList<>();


        String sql = "select * from evaluaciones where idcurso = ? and idsemestre = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCurso);
            pstmt.setInt(2, idSemestre);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Evaluaciones evaluaciones = new Evaluaciones();
                    evaluaciones.setIdEvaluacion(rs.getInt("idevaluaciones"));
                    evaluaciones.setNombreEstudiante(rs.getString("nombre_estudiante"));
                    evaluaciones.setCodigoEstudiante(rs.getString("codigo_estudiante"));
                    evaluaciones.setCorreoEstudiante(rs.getString("correo_estudiante"));
                    evaluaciones.setNota(rs.getInt("nota"));
                    evaluaciones.setCurso(cursoDao.obtenerCurso(rs.getInt("idcurso")));
                    evaluaciones.setSemestre(semestreDao.obtenerSemestre(rs.getInt("idsemestre")));
                    evaluaciones.setFechaRegistro(rs.getString("fecha_registro"));
                    evaluaciones.setFechaEdicion(rs.getString("fecha_edicion"));
                    lista.add(evaluaciones);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }


    public void crearEvaluacion(Evaluaciones evaluaciones) throws SQLException{

        String sql = "insert into evaluaciones (nombre_estudiante, codigo_estudiante, correo_estudiante, nota, idcurso, idsemestre, fecha_registro, fecha_edicion) values (?,?,?,?,?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,evaluaciones.getNombreEstudiante());
            pstmt.setString(2,evaluaciones.getCodigoEstudiante());
            pstmt.setString(3,evaluaciones.getCorreoEstudiante());
            pstmt.setInt(4,evaluaciones.getNota());
            pstmt.setInt(5,evaluaciones.getCurso().getIdCurso());
            pstmt.setInt(6,evaluaciones.getSemestre().getIdSemestre());
            String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());
            pstmt.setString(7,dateTime);
            pstmt.setString(8,dateTime);
            pstmt.executeUpdate();
        }
    }


    public void actualizar(Evaluaciones evaluaciones) throws SQLException{

        String sql = "update evaluaciones set nombre_estudiante = ?,  codigo_estudiante = ?, correo_estudiante = ?, nota = ?, fecha_edicion = ? where idevaluaciones = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,evaluaciones.getNombreEstudiante());
            pstmt.setString(2,evaluaciones.getCodigoEstudiante());
            pstmt.setString(3,evaluaciones.getCorreoEstudiante());
            pstmt.setInt(4,evaluaciones.getNota());
            String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());
            pstmt.setString(5,dateTime);
            pstmt.setInt(6,evaluaciones.getIdEvaluacion());

            pstmt.executeUpdate();
        }
    }

    public void borrarEvaluacion(int idEvaluaciones) throws SQLException {
        String sql = "DELETE FROM evaluaciones WHERE idevaluaciones = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,idEvaluaciones);
            pstmt.executeUpdate();
        }
    }

}