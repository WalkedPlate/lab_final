package com.example.lab_final.Sevlets;

import com.example.lab_final.Beans.Usuario;
import com.example.lab_final.Daos.UsuarioDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = {"/LoginServlet",""})
public class LoginServlet extends HttpServlet {

    UsuarioDao usuarioDao = new UsuarioDao();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "login" : request.getParameter("action");
        HttpSession session = request.getSession();

        switch (action){
            case "login":

                Usuario usuario = (Usuario) session.getAttribute("usuario");
                if (usuario != null && usuario.getIdUsuario() > 0) {
                    response.sendRedirect(request.getContextPath());
                } else {
                    RequestDispatcher view = request.getRequestDispatcher("login/login.jsp");
                    view.forward(request, response);
                }

                break;

            case "logout":
                session.invalidate();
                response.sendRedirect(request.getContextPath());
                break;


        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action){

            case "login":
                String correoInput = request.getParameter("correo");
                String passwordInput = request.getParameter("password");

                System.out.println(correoInput);
                System.out.println(passwordInput);

                if (usuarioDao.login(correoInput,passwordInput)){

                    Usuario usuario = usuarioDao.obtenerUsuarioPorCorreo(correoInput);

                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", usuario);

                    session.setMaxInactiveInterval(1800); // 1800 segundos = 30 minutos

                    switch (usuario.getRol().getIdRol()){
                        case 1:
                            response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                            break;
                        case 2:
                            response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                            break;
                        case 3:
                            response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                            break;
                        case 4:
                            response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                            break;

                    }

                }else{
                    request.setAttribute("err","Usuario o password incorrectos ");
                    request.getRequestDispatcher("login/login.jsp").forward(request,response);
                }

                break;

        }

    }
}

