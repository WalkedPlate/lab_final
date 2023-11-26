<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab_final.Daos.CursoDao" %>
<%@ page import="com.example.lab_final.Beans.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="listaDocentesConCurso" scope="request" type="ArrayList<com.example.lab_final.Beans.CursoHasDocente>" />
<jsp:useBean id="listaDocentesNoAsignados" scope="request" type="ArrayList<com.example.lab_final.Beans.Usuario>" />

<%CursoDao cursoDao = new CursoDao();%>

<!DOCTYPE html>
<html>
<head>
    <title>Lista de Docentes</title>
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
        <jsp:param name="currentPage" value="docent"/>
    </jsp:include>
    <div class="row mb-5 mt-4">
        <div class="col-md-5">
            <h1>Lista de Docentes</h1>
        </div>
        <div class="col-md-5 col-lg-4 ms-auto my-auto text-md-end">
            <a href="<%=request.getContextPath()%>/DecanoServlet?action=crearDocente" class="btn btn-primary">
                Registrar Docente</a>
        </div>

    </div>
    <jsp:include page="../includes/msg.jsp"/>

    <table class="table">
        <tr>
            <th>#</th>
            <th>Nombre</th>
            <th>Correo</th>
            <th>Rol</th>
            <th>Último ingreso</th>
            <th>Cantidad de ingresos</th>
            <th>Curso</th>
        </tr>
        <%if(listaDocentesConCurso.isEmpty() && listaDocentesNoAsignados.isEmpty()){%>
        <tr>NO EXISTEN NI DOCENTES EN SU FACULTAD NI DOCENTES NO ASIGNADOS</tr>
        <%} else {%>
        <%int i=1;%>
        <%for (CursoHasDocente cursoHasDocente: listaDocentesConCurso) {%>
        <tr>
            <td><%=i%>
            </td>
            <td><%=cursoHasDocente.getDocente().getNombre()%>
            </td>
            <td><%=cursoHasDocente.getDocente().getCorreo()%>
            </td>
            <td><%=cursoHasDocente.getDocente().getRol().getNombre()%>
            </td>
            <td><%=cursoHasDocente.getDocente().getUltimoIngreso()%>
            </td>
            <td><%=cursoHasDocente.getDocente().getCantidadIngresos()%>
            </td>
            <td><%=cursoHasDocente.getCurso().getNombre()%>
            </td>

            <td>
                <a href="<%=request.getContextPath()%>/DecanoServlet?action=editarDocente&idDocent=<%=cursoHasDocente.getDocente().getIdUsuario()%>"
                   type="button" class="btn btn-primary">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>

            <td>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/DecanoServlet?action=borrarDocente&idDocent=<%=cursoHasDocente.getDocente().getIdUsuario()%>"
                   type="button" class="btn btn-danger disabled">
                    <i class="bi bi-trash"></i>
                </a>
            </td>

        </tr>
        <%i++;}%>

        <%for (Usuario docenteNoAsignado : listaDocentesNoAsignados) {%>
        <tr>
            <td><%=i%>
            </td>
            <td><%=docenteNoAsignado.getNombre()%>
            </td>
            <td><%=docenteNoAsignado.getCorreo()%>
            </td>
            <td><%=docenteNoAsignado.getRol().getNombre()%>
            </td>
            <td><%=docenteNoAsignado.getUltimoIngreso()%>
            </td>
            <td><%=docenteNoAsignado.getCantidadIngresos()%>
            </td>
            <td>NO ASIGNADO
            </td>

            <td>
                <a href="<%=request.getContextPath()%>/DecanoServlet?action=editarDocente&idDocent=<%=docenteNoAsignado.getIdUsuario()%>"
                   type="button" class="btn btn-primary">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>

            <td>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/DecanoServlet?action=borrarDocente&idDocent=<%=docenteNoAsignado.getIdUsuario()%>"
                   type="button" class="btn btn-danger">
                    <i class="bi bi-trash"></i>
                </a>
            </td>

        </tr>
        <%i++;}%>

        <%}%>
    </table>
</div>

</body>
</html>
