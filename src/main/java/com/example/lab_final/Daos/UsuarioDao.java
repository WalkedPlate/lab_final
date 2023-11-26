package com.example.lab_final.Daos;

import com.example.lab_final.Beans.Curso;
import com.example.lab_final.Beans.Evaluaciones;
import com.example.lab_final.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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

    public void actualizarIngresos(int idUsuario){

        String sql = "update usuario set cantidad_ingresos = cantidad_ingresos + 1 , ultimo_ingreso = ? where idusuario = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());
            pstmt.setString(1,dateTime);
            pstmt.setInt(2,idUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Usuario> listaDocentesSinCurso(){

        RolDao rolDao = new RolDao();
        CursoHasDocenteDao cursoHasDocenteDao = new CursoHasDocenteDao();
        ArrayList<Usuario> lista = new ArrayList<>();

        String sql = "select * from usuario where idrol = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,4);

            try(ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("idusuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setRol(rolDao.obtenerRol(rs.getInt("idrol")));
                    usuario.setUltimoIngreso(rs.getString("ultimo_ingreso"));
                    usuario.setCantidadIngresos(rs.getInt("cantidad_ingresos"));
                    usuario.setFechaRegistro(rs.getString("fecha_registro"));
                    usuario.setFechaEdicion(rs.getString("fecha_edicion"));
                    if(cursoHasDocenteDao.cursosPorDocente(usuario.getIdUsuario()).isEmpty()){
                        lista.add(usuario);
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public int obtenerUltimoId(){

        int resultado = 0;
        String sql = "select max(idusuario) from usuario;";
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


    public void crearUsuario(Usuario usuario) throws SQLException{

        String sql = "insert into usuario (idusuario, nombre, correo, password, idrol, cantidad_ingresos, fecha_registro, fecha_edicion) values (?,?,?,SHA2(?,256),?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,obtenerUltimoId()+1);
            pstmt.setString(2,usuario.getNombre());
            pstmt.setString(3,usuario.getCorreo());
            pstmt.setString(4,usuario.getPassword());
            pstmt.setInt(5,usuario.getRol().getIdRol());
            pstmt.setInt(6,0);
            String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());
            pstmt.setString(7,dateTime);
            pstmt.setString(8,dateTime);
            pstmt.executeUpdate();
        }
    }

    public void actualizar(Usuario usuario) throws SQLException{

        String sql = "update usuario set nombre = ?, fecha_edicion = ? where idusuario = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,usuario.getNombre());
            String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());
            pstmt.setString(2,dateTime);
            pstmt.setInt(3,usuario.getIdUsuario());
            pstmt.executeUpdate();
        }
    }

    public void borrarDocente(int idDocente) throws SQLException {
        String sql = "DELETE FROM usuario WHERE idusuario = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,idDocente);
            pstmt.executeUpdate();
        }
    }

}
