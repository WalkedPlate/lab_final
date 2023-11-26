package com.example.lab_final.Sevlets;

import com.example.lab_final.Beans.Curso;
import com.example.lab_final.Beans.Evaluaciones;
import com.example.lab_final.Beans.FacultadHasDecano;
import com.example.lab_final.Beans.Usuario;
import com.example.lab_final.Daos.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "DecanoServlet", value = "/DecanoServlet")
public class DecanoServlet extends HttpServlet {

    CursoDao cursoDao = new CursoDao();
    FacultadHasDecanoDao facultadHasDecanoDao = new FacultadHasDecanoDao();
    UsuarioDao usuarioDao = new UsuarioDao();
    CursoHasDocenteDao cursoHasDocenteDao = new CursoHasDocenteDao();
    RolDao rolDao = new RolDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario= (Usuario) session.getAttribute("usuario");


        String action = request.getParameter("action") == null ? "listaCursos" : request.getParameter("action");

        switch (action) {
            case "listaCursos":

                ArrayList<FacultadHasDecano> facultades = facultadHasDecanoDao.obtenerFacultadPorDecano(usuario.getIdUsuario());
                if(!facultades.isEmpty()){
                    request.setAttribute("listaCursos",cursoDao.obtenerCursosPorFacultad(facultades.get(0).getFacultad().getIdFacultad()));
                    request.setAttribute("facultades",facultades);
                }
                else{request.setAttribute("listaCursos",new ArrayList<>());}

                request.getRequestDispatcher("decano/listaCursos.jsp").forward(request,response);
                break;
            case "crearCurso":

                request.setAttribute("listaDocentesPosibles",usuarioDao.listaDocentesSinCurso());
                request.setAttribute("facultades",facultadHasDecanoDao.obtenerFacultadPorDecano(usuario.getIdUsuario()));
                request.getRequestDispatcher("decano/newCursos.jsp").forward(request,response);
                break;
            case "editarCurso":

                if (request.getParameter("idCurs") != null) {
                    String cursoIdString = request.getParameter("idCurs");
                    int cursoId = 0;
                    try {
                        cursoId = Integer.parseInt(cursoIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                    }
                    Curso curso = cursoDao.obtenerCurso(cursoId);
                    if (curso != null) {
                        request.setAttribute("curso",curso);
                        request.setAttribute("listaDocentesPosibles",usuarioDao.listaDocentesSinCurso());
                        request.setAttribute("facultades",facultadHasDecanoDao.obtenerFacultadPorDecano(usuario.getIdUsuario()));
                        request.getRequestDispatcher("decano/editCursos.jsp").forward(request,response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                    }

                } else {
                    response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                }


                break;
            case "borrarCurso":
                if (request.getParameter("idCurs") != null) {
                    String cursoIdString = request.getParameter("idCurs");
                    int cursoId = 0;
                    try {
                        cursoId = Integer.parseInt(cursoIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                    }
                    Curso curso = cursoDao.obtenerCurso(cursoId);
                    if (curso != null) {
                        try {
                            cursoHasDocenteDao.borrarPorCurso(cursoId);
                            cursoDao.borrarCurso(cursoId);
                            request.getSession().setAttribute("err", "Curso borrado exitosamente");
                        } catch (SQLException e) {
                            request.getSession().setAttribute("err", "Error al borrar el curso");
                            e.printStackTrace();
                        }
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                    }
                }

                break;

            case "listaDocentes":

                ArrayList<FacultadHasDecano> facultadesD = facultadHasDecanoDao.obtenerFacultadPorDecano(usuario.getIdUsuario());
                request.setAttribute("listaDocentesConCurso",cursoHasDocenteDao.listaDocentesConCursoPorFacultad(facultadesD.get(0).getFacultad().getIdFacultad()));
                request.setAttribute("listaDocentesNoAsignados",usuarioDao.listaDocentesSinCurso());

                request.getRequestDispatcher("decano/listaDocentes.jsp").forward(request,response);
                break;
            case "crearDocente":
                request.getRequestDispatcher("decano/newDocentes.jsp").forward(request,response);
                break;
            case "editarDocente":

                if (request.getParameter("idDocent") != null) {
                    String docenteIdString = request.getParameter("idDocent");
                    int docenteId = 0;
                    try {
                        docenteId = Integer.parseInt(docenteIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=listaDocentes");
                    }
                    Usuario docente = usuarioDao.obtenerUsuario(docenteId);
                    if (docente != null) {
                        request.setAttribute("docente",docente);
                        request.getRequestDispatcher("decano/editDocentes.jsp").forward(request,response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=listaDocentes");
                    }

                } else {
                    response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=listaDocentes");
                }


                break;
            case "borrarDocente":
                if (request.getParameter("idDocent") != null) {
                    String docenteIdString = request.getParameter("idDocent");
                    int docenteId = 0;
                    try {
                        docenteId = Integer.parseInt(docenteIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=listaDocentes");
                    }
                    Usuario docente = usuarioDao.obtenerUsuario(docenteId);
                    if (docente != null) {
                        try {
                            usuarioDao.borrarDocente(docenteId);
                            request.getSession().setAttribute("err", "Docente borrado exitosamente");
                        } catch (SQLException e) {
                            request.getSession().setAttribute("err", "Error al borrar al docente");
                            e.printStackTrace();
                        }
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=listaDocentes");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=listaDocentes");
                    }

                } else {
                    response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=listaDocentes");
                }

                break;

        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario= (Usuario) session.getAttribute("usuario");

        String action = request.getParameter("action") == null ? "guardarCurso" : request.getParameter("action");

        switch (action) {

            case "guardarCurso":

                Curso curso = new Curso();
                curso.setCodigo(request.getParameter("codigoCurso"));
                curso.setNombre(request.getParameter("nombreCurso"));
                curso.setFacultad(facultadHasDecanoDao.obtenerFacultadPorDecano(usuario.getIdUsuario()).get(0).getFacultad());


                if (request.getParameter("idCurso") == null) {
                    try {
                        cursoDao.crearCurso(curso);
                        cursoHasDocenteDao.crear(cursoDao.obtenerUltimoId(),Integer.parseInt(request.getParameter("idDocente")));
                        session.setAttribute("msg", "Curso registrado exitosamente");
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                    } catch (SQLException exc) {
                        session.setAttribute("err", "Error al registrar el curso");
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=crearCurso");
                    }
                } else {
                    curso.setIdCurso(Integer.parseInt(request.getParameter("idCurso")));
                    try {
                        cursoDao.actualizar(curso);
                        session.setAttribute("msg", "Curso actualizado exitosamente");
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                    } catch (SQLException ex) {
                        session.setAttribute("err", "Error al actualizar el curso");
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=editarCurso");
                    }
                }


                break;

            case "guardarDocente":

                Usuario docenteRegistrar = new Usuario();
                docenteRegistrar.setNombre(request.getParameter("nombreDocente"));
                docenteRegistrar.setCorreo(request.getParameter("correoDocente"));
                docenteRegistrar.setRol(rolDao.obtenerRol(4));

                if (request.getParameter("idDocente") == null) {
                    try {
                        docenteRegistrar.setPassword(request.getParameter("passwordDocente"));
                        usuarioDao.crearUsuario(docenteRegistrar);
                        session.setAttribute("msg", "Docente registrado exitosamente");
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=listaDocentes");
                    } catch (SQLException exc) {
                        System.out.println("primer");
                        session.setAttribute("err", "Error al registrar al docente");
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=crearDocente");
                    }
                } else {
                    docenteRegistrar.setIdUsuario(Integer.parseInt(request.getParameter("idDocente")));
                    try {
                        usuarioDao.actualizar(docenteRegistrar);
                        session.setAttribute("msg", "Docente actualizado exitosamente");
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=listaDocentes");
                    } catch (SQLException ex) {
                        System.out.println("segundo");
                        session.setAttribute("err", "Error al actualizar el curso");
                        response.sendRedirect(request.getContextPath() + "/DecanoServlet?action=editarDocente");
                    }
                }


                break;
        }

    }
}

