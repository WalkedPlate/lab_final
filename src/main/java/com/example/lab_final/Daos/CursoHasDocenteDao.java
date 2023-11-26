package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Curso;
import com.example.lab_final.Beans.CursoHasDocente;
import com.example.lab_final.Beans.Evaluaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CursoHasDocenteDao extends DaoBase{

    public CursoHasDocente obtenerCursoHasDocente(int idCurso, int idDocente) {

        CursoDao cursoDao = new CursoDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        CursoHasDocente cursoHasDocente = new CursoHasDocente();

        String sql = "select * from curso_has_docente where idcurso = ? and iddocente = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCurso);
            pstmt.setInt(2,idDocente);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    cursoHasDocente.setCurso(cursoDao.obtenerCurso(rs.getInt("idcurso")));
                    cursoHasDocente.setDocente(usuarioDao.obtenerUsuario(rs.getInt("iddocente")));
                } else {
                    cursoHasDocente = null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cursoHasDocente;
    }


    public ArrayList<CursoHasDocente> cursosPorDocente(int idDocente) {

        CursoDao cursoDao = new CursoDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        ArrayList<CursoHasDocente> lista = new ArrayList<>();

        String sql = "select * from curso_has_docente where iddocente = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,idDocente);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    CursoHasDocente cursoHasDocente = new CursoHasDocente();
                    cursoHasDocente.setCurso(cursoDao.obtenerCurso(rs.getInt("idcurso")));
                    cursoHasDocente.setDocente(usuarioDao.obtenerUsuario(rs.getInt("iddocente")));
                    lista.add(cursoHasDocente);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public void crear(int idCurso, int idDocente) throws SQLException{

        String sql = "insert into curso_has_docente (idcurso, iddocente) values (?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,idCurso);
            pstmt.setInt(2,idDocente);
            pstmt.executeUpdate();
        }
    }

    public void borrarPorCurso(int idCurso) throws SQLException {
        String sql = "DELETE FROM curso_has_docente WHERE idcurso = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,idCurso);
            pstmt.executeUpdate();
        }
    }


    public ArrayList<CursoHasDocente> listaDocentesConCursoPorFacultad(int idFacultad) {

        CursoDao cursoDao = new CursoDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        ArrayList<CursoHasDocente> lista = new ArrayList<>();

        String sql = "select * from curso_has_docente chd inner join curso c on (chd.idcurso = c.idcurso) inner join facultad f on (c.idfacultad = f.idfacultad) where f.idfacultad = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,idFacultad);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CursoHasDocente cursoHasDocente = new CursoHasDocente();
                    cursoHasDocente.setCurso(cursoDao.obtenerCurso(rs.getInt("idcurso")));
                    cursoHasDocente.setDocente(usuarioDao.obtenerUsuario(rs.getInt("iddocente")));
                    lista.add(cursoHasDocente);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }


}
