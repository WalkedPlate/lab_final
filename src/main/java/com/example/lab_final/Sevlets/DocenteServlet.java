package com.example.lab_final.Sevlets;

import com.example.lab_final.Daos.EvaluacionesDao;
import com.example.lab_final.Daos.FacultadHasDecanoDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DocenteServlet", value = "/DocenteServlet")
public class DocenteServlet extends HttpServlet {

    EvaluacionesDao evaluacionesDao = new EvaluacionesDao();
    FacultadHasDecanoDao facultadHasDecanoDao = new FacultadHasDecanoDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");



        switch (action) {
            case "lista":

                request.getRequestDispatcher("docente/listaEvaluaciones.jsp").forward(request,response);
                break;
            case "agregar":

                break;
            case "editar":

                break;
            case "borrar":

                break;
            case "est":

                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

