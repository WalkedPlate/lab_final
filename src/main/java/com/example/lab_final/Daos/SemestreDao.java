package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Curso;
import com.example.lab_final.Beans.Semestre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SemestreDao extends DaoBase {

    public Semestre obtenerSemestre(int idSemestre) {

        UsuarioDao usuarioDao = new UsuarioDao();
        Semestre semestre = new Semestre();

        String sql = "select * from semestre where idsemestre = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idSemestre);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    semestre.setIdSemestre(rs.getInt("idsemestre"));
                    semestre.setNombre(rs.getString("nombre"));
                    semestre.setAdministrador(usuarioDao.obtenerUsuario(rs.getInt("idadmistrador")));
                    semestre.setHabilitado(rs.getBoolean("habilitado"));
                    semestre.setFechaRegistro(rs.getString("fecha_registro"));
                    semestre.setFechaEdicion(rs.getString("fecha_edicion"));
                } else {
                    semestre = null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return semestre;
    }


    public ArrayList<Semestre> listaSemestres() {

        UsuarioDao usuarioDao = new UsuarioDao();
        ArrayList<Semestre> lista = new ArrayList<>();

        String sql = "select * from semestre;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Semestre semestre = new Semestre();
                    semestre.setIdSemestre(rs.getInt("idsemestre"));
                    semestre.setNombre(rs.getString("nombre"));
                    semestre.setAdministrador(usuarioDao.obtenerUsuario(rs.getInt("idadmistrador")));
                    semestre.setHabilitado(rs.getBoolean("habilitado"));
                    semestre.setFechaRegistro(rs.getString("fecha_registro"));
                    semestre.setFechaEdicion(rs.getString("fecha_edicion"));
                    lista.add(semestre);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}