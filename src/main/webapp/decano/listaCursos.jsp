<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab_final.Beans.Evaluaciones" %>
<%@ page import="com.example.lab_final.Beans.Semestre" %>
<%@ page import="com.example.lab_final.Daos.CursoDao" %>
<%@ page import="com.example.lab_final.Beans.Curso" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="listaCursos" scope="request" type="ArrayList<com.example.lab_final.Beans.Curso>" />
<%CursoDao cursoDao = new CursoDao();%>

<!DOCTYPE html>
<html>
<head>
    <title>Lista de Cursos</title>
    <!-- Iconos -->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <!-- bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.0/font/bootstrap-icons.css">
</head>
<body>
<div class='container'>
    <jsp:include page="../includes/navbarDecano.jsp">
        <jsp:param name="currentPage" value="curs"/>
    </jsp:include>
    <div class="row mb-5 mt-4">
        <div class="col-md-5">
            <h1>Lista de Cursos</h1>
        </div>
        <div class="col-md-5 col-lg-4 ms-auto my-auto text-md-end">
            <a href="<%=request.getContextPath()%>/DecanoServlet?action=crearCurso" class="btn btn-primary">
                Registrar Curso</a>
        </div>

    </div>
    <jsp:include page="../includes/msg.jsp"/>

    <table class="table">
        <tr>
            <th>#</th>
            <th>Código</th>
            <th>Nombre</th>
            <th>Facultad</th>
        </tr>
        <%if(listaCursos.isEmpty()){%>
        <tr>Su facultad no tiene cursos asignados.</tr>
        <%} else {%>
        <%int i=1;%>
        <%for (Curso curso: listaCursos) {%>
        <tr>
            <td><%=i%>
            </td>
            <td><%=curso.getCodigo()%>
            </td>
            <td><%=curso.getNombre()%>
            </td>
            <td><%=curso.getFacultad().getNombre()%>
            </td>

            <td>
                <a href="<%=request.getContextPath()%>/DecanoServlet?action=editarCurso&idCurs=<%=curso.getIdCurso()%>"
                   type="button" class="btn btn-primary">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>

            <%if(cursoDao.cursoNoTieneEvaluaciones(curso.getIdCurso())){%>
            <td>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/DecanoServlet?action=borrarCurso&idCurs=<%=curso.getIdCurso()%>"
                   type="button" class="btn btn-danger">
                    <i class="bi bi-trash"></i>
                </a>
            </td>
            <%} else{%>
            <td>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/DecanoServlet?action=borrarCurso&idCurs=<%=curso.getIdCurso()%>"
                   type="button" class="btn btn-danger disabled">
                    <i class="bi bi-trash"></i>
                </a>
            </td>
            <%}%>

        </tr>
        <%i++;}%>
        <%}%>
    </table>
</div>

</body>
</html>