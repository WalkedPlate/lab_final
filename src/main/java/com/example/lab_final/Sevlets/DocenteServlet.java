package com.example.lab_final.Sevlets;

import com.example.lab_final.Beans.CursoHasDocente;
import com.example.lab_final.Beans.Evaluaciones;
import com.example.lab_final.Beans.Usuario;
import com.example.lab_final.Daos.CursoHasDocenteDao;
import com.example.lab_final.Daos.EvaluacionesDao;
import com.example.lab_final.Daos.FacultadHasDecanoDao;
import com.example.lab_final.Daos.SemestreDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "DocenteServlet", value = "/DocenteServlet")
public class DocenteServlet extends HttpServlet {

    EvaluacionesDao evaluacionesDao = new EvaluacionesDao();
    FacultadHasDecanoDao facultadHasDecanoDao = new FacultadHasDecanoDao();
    SemestreDao semestreDao = new SemestreDao();
    CursoHasDocenteDao cursoHasDocenteDao = new CursoHasDocenteDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario= (Usuario) session.getAttribute("usuario");


        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        switch (action) {
            case "lista":
                int idSemestre = Integer.parseInt(request.getParameter("idSemestre") == null ? "4" : request.getParameter("idSemestre"));

                ArrayList<CursoHasDocente> listaCursos = cursoHasDocenteDao.cursosPorDocente(usuario.getIdUsuario());

                request.setAttribute("listaCursos",listaCursos);
                request.setAttribute("semestreMostrado",semestreDao.obtenerSemestre(idSemestre));
                request.setAttribute("listaSemestres",semestreDao.listaSemestres());

                if(!listaCursos.isEmpty()){
                    request.setAttribute("lista",evaluacionesDao.listaEvaluaciones(listaCursos.get(0).getCurso().getIdCurso(),idSemestre));
                }
                else {request.setAttribute("lista",new ArrayList<>());}
                request.getRequestDispatcher("docente/listaEvaluaciones.jsp").forward(request,response);
                break;
            case "crear":

                request.setAttribute("listaCursos",cursoHasDocenteDao.cursosPorDocente(usuario.getIdUsuario()));
                request.setAttribute("semestreMostrado",semestreDao.obtenerSemestre(4));
                request.getRequestDispatcher("docente/newEvaluaciones.jsp").forward(request,response);
                break;
            case "editar":

                if (request.getParameter("idEval") != null) {
                    String evaluacionIdString = request.getParameter("idEval");
                    int evaluacionId = 0;
                    try {
                        evaluacionId = Integer.parseInt(evaluacionIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                    }
                    Evaluaciones evaluaciones = evaluacionesDao.obtenerEvaluaciones(evaluacionId);
                    if (evaluaciones != null) {
                        request.setAttribute("evaluacion",evaluaciones);
                        request.setAttribute("listaCursos",cursoHasDocenteDao.cursosPorDocente(usuario.getIdUsuario()));
                        request.setAttribute("semestreMostrado",semestreDao.obtenerSemestre(4));
                        request.getRequestDispatcher("docente/editEvaluaciones.jsp").forward(request,response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                    }

                } else {
                    response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                }

                break;
            case "borrar":
                if (request.getParameter("idEval") != null) {
                    String evaluacionIdString = request.getParameter("idEval");
                    int evaluacionId = 0;
                    try {
                        evaluacionId = Integer.parseInt(evaluacionIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                    }

                    Evaluaciones evaluaciones = evaluacionesDao.obtenerEvaluaciones(evaluacionId);

                    if (evaluaciones != null) {
                        try {
                            evaluacionesDao.borrarEvaluacion(evaluacionId);
                            request.getSession().setAttribute("err", "Empleado borrado exitosamente");
                        } catch (SQLException e) {
                            request.getSession().setAttribute("err", "Error al borrar el empleado");
                            e.printStackTrace();
                        }
                        response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                }

                break;

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario= (Usuario) session.getAttribute("usuario");

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        switch (action) {
            case "lista":
                int idSemestre = Integer.parseInt(request.getParameter("semestre"));
                response.sendRedirect(request.getContextPath() + "/DocenteServlet?action=lista&idSemestre="+idSemestre);
                break;
            case "guardar":

                Evaluaciones evaluaciones = new Evaluaciones();
                evaluaciones.setSemestre(semestreDao.obtenerSemestre(4));
                evaluaciones.setCurso(cursoHasDocenteDao.cursosPorDocente(usuario.getIdUsuario()).get(0).getCurso());
                evaluaciones.setNombreEstudiante(request.getParameter("nombre"));
                evaluaciones.setCorreoEstudiante(request.getParameter("correo"));
                evaluaciones.setCodigoEstudiante(request.getParameter("codigo"));
                evaluaciones.setNota(Integer.parseInt(request.getParameter("nota")));


                if (request.getParameter("idEvaluacion") == null) {
                    try {
                        evaluacionesDao.crearEvaluacion(evaluaciones);
                        session.setAttribute("msg", "Evaluación registrada exitosamente");
                        response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                    } catch (SQLException exc) {
                        session.setAttribute("err", "Error al crear el empleado");
                        response.sendRedirect(request.getContextPath() + "/DocenteServlet?action=crear");
                    }
                } else {
                    evaluaciones.setIdEvaluacion(Integer.parseInt(request.getParameter("idEvaluacion")));
                    try {
                        evaluacionesDao.actualizar(evaluaciones);
                        session.setAttribute("msg", "Evalaución actualizada exitosamente");
                        response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                    } catch (SQLException ex) {
                        session.setAttribute("err", "Error al actualizar la evaluación");
                        response.sendRedirect(request.getContextPath() + "/DocenteServlet?action=editar");
                    }
                }


                break;
        }

    }
}

