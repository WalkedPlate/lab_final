
<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab_final.Beans.Evaluaciones" %>
<%@ page import="com.example.lab_final.Beans.Semestre" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="lista" scope="request" type="ArrayList<com.example.lab_final.Beans.Evaluaciones>" />
<jsp:useBean id="listaSemestres" scope="request" type="ArrayList<com.example.lab_final.Beans.Semestre>" />
<jsp:useBean id="semestreMostrado" scope="request" type="com.example.lab_final.Beans.Semestre" />
<jsp:useBean id="listaCursos" scope="request" type="ArrayList<com.example.lab_final.Beans.CursoHasDocente>" />


<!DOCTYPE html>
<html>
<head>
    <title>Lista de Evaluaciones</title>
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
    <jsp:include page="../includes/navbar.jsp">
        <jsp:param name="currentPage" value="eval"/>
    </jsp:include>
    <div class="row mb-5 mt-4">
        <div class="col-md-5">
            <h1>Lista de Evaluaciones</h1>
        </div>
        <div class="col-md-3">
            <form method="post" action="<%=request.getContextPath()%>/DocenteServlet?action=lista">
            <label for="semestre">Semestre</label>
            <select name="semestre" id="semestre" class="form-select form-select">
                <%for(Semestre semestre: listaSemestres){%>
                <option name="semestre" value="<%=semestre.getIdSemestre()%>" <%= semestre.getIdSemestre() == semestreMostrado.getIdSemestre() ? "selected" : ""%>> <%= semestre.getNombre() %> <%=semestre.isHabilitado() ? "(habilitado)" : "(no habilitado)"%>
                </option>
                <%}%>
            </select>
                <button class="submit">Buscar</button>
            </form>
        </div>
        <%if(semestreMostrado.isHabilitado()){%>
        <div class="col-md-5 col-lg-4 ms-auto my-auto text-md-end">
            <a href="<%=request.getContextPath()%>/DocenteServlet?action=crear" class="btn btn-primary">
                Registrar Evaluación</a>
        </div>
        <%} else{%>
        <div class="col-md-5 col-lg-4 ms-auto my-auto text-md-end">
            <a href="<%=request.getContextPath()%>/DocenteServlet?action=crear" class="btn btn-primary disabled">
                Registrar Evaluación</a>
        </div>
        <%}%>
    </div>
    <jsp:include page="../includes/msg.jsp"/>

    <table class="table">
        <tr>
            <th>#</th>
            <th>Nombre</th>
            <th>Código</th>
            <th>Correo</th>
            <th>Nota</th>
            <th>Curso</th>
            <th>Semestre</th>

        </tr>
        <%if(listaCursos.isEmpty()){%>
        <tr>Usted no tiene cursos asignados</tr>
        <%} else {%>
        <%if(lista.isEmpty()){%>
        <tr>No existen evaluaciones registradas</tr>
        <%}%>
        <%int i=1;%>
        <%for (Evaluaciones evaluaciones : lista) {%>
        <tr>
            <td><%=i%>
            </td>
            <td><%=evaluaciones.getNombreEstudiante()%>
            </td>
            <td><%=evaluaciones.getCodigoEstudiante()%>
            </td>
            <td><%=evaluaciones.getCorreoEstudiante()%>
            </td>
            <td><%=evaluaciones.getNota()%>
            </td>
            <td><%=evaluaciones.getCurso().getNombre()%>
            </td>
            <td><%=evaluaciones.getSemestre().getNombre()%>
            </td>
            <td>
                <a href="<%=request.getContextPath()%>/DocenteServlet?action=editar&idEval=<%=evaluaciones.getIdEvaluacion()%>"
                   type="button" class="btn btn-primary">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>
            <%if(semestreMostrado.isHabilitado()){%>
            <td>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/DocenteServlet?action=borrar&idEval=<%=evaluaciones.getIdEvaluacion()%>"
                   type="button" class="btn btn-danger">
                    <i class="bi bi-trash"></i>
                </a>
            </td>
            <%} else{%>
            <td>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/DocenteServlet?action=borrar&idEval=<%=evaluaciones.getIdEvaluacion()%>"
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