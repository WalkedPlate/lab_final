package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UsuarioDao extends DaoBase{

    public Usuario obtenerUsuario(int idUsuario){

        RolDao rolDao = new RolDao();
        Usuario usuario = new Usuario();

        String sql = "select * from usuario where idusuario = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,idUsuario);

            try(ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt("idusuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setRol(rolDao.obtenerRol(rs.getInt("idrol")));
                    usuario.setUltimoIngreso(rs.getString("ultimo_ingreso"));
                    usuario.setCantidadIngresos(rs.getInt("cantidad_ingresos"));
                    usuario.setFechaRegistro(rs.getString("fecha_registro"));
                    usuario.setFechaEdicion(rs.getString("fecha_edicion"));
                }
                else {
                    usuario = null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    public Usuario obtenerUsuarioPorCorreo(String correo){

        RolDao rolDao = new RolDao();
        Usuario usuario = new Usuario();

        String sql = "select * from usuario where correo = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,correo);

            try(ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt("idusuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setRol(rolDao.obtenerRol(rs.getInt("idrol")));
                    usuario.setUltimoIngreso(rs.getString("ultimo_ingreso"));
                    usuario.setCantidadIngresos(rs.getInt("cantidad_ingresos"));
                    usuario.setFechaRegistro(rs.getString("fecha_registro"));
                    usuario.setFechaEdicion(rs.getString("fecha_edicion"));
                }
                else {
                    usuario = null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    public boolean login(String correo, String password){

        boolean valido = false;

        String sql = "SELECT correo, password FROM usuario WHERE correo = ? AND password = SHA2(?,256);";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, correo);
            pstmt.setString(2, password);

            try(ResultSet rs = pstmt.executeQuery()){

                if(rs.next()){
                    valido = true;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return valido;
    }

}
