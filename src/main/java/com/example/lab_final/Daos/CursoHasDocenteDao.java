package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Curso;
import com.example.lab_final.Beans.CursoHasDocente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
