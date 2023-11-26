<%@ page import="com.example.lab_final.Beans.Usuario" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% String currentPage = request.getParameter("currentPage"); %>
<jsp:useBean id="usuario" type="com.example.lab_final.Beans.Usuario" scope="session"
             class="com.example.lab_final.Beans.Usuario"/>

<nav class="navbar navbar-expand-md navbar-light bg-light">
    <a class="navbar-brand" href="#">CRUD Decano</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
        <ul class="navbar-nav">

            <li class="nav-item bg-light">
                <a class="nav-link <%=currentPage.equals("curs") ? "active text-blue" : ""%>"
                   href="<%=request.getContextPath()%>/DecanoServlet">
                    Cursos
                </a>
            </li>
            <li class="nav-item bg-light">
                <a class="nav-link <%=currentPage.equals("docent") ? "active text-blue" : ""%>"
                   href="<%=request.getContextPath()%>/DecanoServlet?action=listaDocentes">
                    Docentes
                </a>
            </li>

            <li class="nav-item">
                <span class="nav-link text-dark">
                     | Bienvenido <%=usuario.getNombre()%> | (<a
                        href="<%=request.getContextPath()%>/LoginServlet?action=logout">cerrar sesión</a>)
                </span>
            </li>

        </ul>
    </div>
</nav>