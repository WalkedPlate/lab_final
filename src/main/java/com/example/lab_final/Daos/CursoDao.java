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


    public ArrayList<Curso> obtenerCursosPorFacultad(int idFacultad){

        FacultadDao facultadDao = new FacultadDao();
        ArrayList<Curso> lista = new ArrayList<>();

        String sql = "select * from curso where idfacultad = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,idFacultad);

            try(ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Curso curso = new Curso();
                    curso.setIdCurso(rs.getInt("idcurso"));
                    curso.setCodigo(rs.getString("codigo"));
                    curso.setNombre(rs.getString("nombre"));
                    curso.setFacultad(facultadDao.obtenerFacultad(rs.getInt("idfacultad")));
                    curso.setFechaRegistro(rs.getString("fecha_registro"));
                    curso.setFechaEdicion(rs.getString("fecha_edicion"));
                    lista.add(curso);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public boolean cursoNoTieneEvaluaciones(int idCurso){

        boolean resultado = true;
        String sql = "select * from evaluaciones;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try(ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if(rs.getInt("idcurso")==idCurso){
                        resultado = false;
                        return resultado;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public int obtenerUltimoId(){

        int resultado = 0;
        String sql = "select max(idcurso) from curso;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try(ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public void crearCurso(Curso curso) throws SQLException{

        String sql = "insert into curso (idcurso, codigo, nombre, idfacultad, fecha_registro, fecha_edicion) values (?,?,?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,obtenerUltimoId()+1);
            pstmt.setString(2,curso.getCodigo());
            pstmt.setString(3,curso.getNombre());
            pstmt.setInt(4,curso.getFacultad().getIdFacultad());
            String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());
            pstmt.setString(5,dateTime);
            pstmt.setString(6,dateTime);
            pstmt.executeUpdate();
        }
    }

    public void actualizar(Curso curso) throws SQLException{

        String sql = "update curso set nombre = ?, fecha_edicion = ? where idcurso = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,curso.getNombre());
            String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());
            pstmt.setString(2,dateTime);
            pstmt.setInt(3,curso.getIdCurso());
            pstmt.executeUpdate();
        }
    }

    public void borrarCurso(int idCurso) throws SQLException {
        String sql = "DELETE FROM curso WHERE idcurso = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,idCurso);
            pstmt.executeUpdate();
        }
    }

}
