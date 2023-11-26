<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab_final.Beans.Usuario" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="listaDocentesPosibles" scope="request" type="ArrayList<com.example.lab_final.Beans.Usuario>" />
<jsp:useBean id="facultades" scope="request" type="ArrayList<com.example.lab_final.Beans.FacultadHasDecano>" />


<!DOCTYPE html>
<html>
<head>
    <title>Registrar Curso</title>
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
        <jsp:param name="currentPage" value="eval"/>
    </jsp:include>
    <div class="row mb-4">
        <div class="col"></div>
        <div class="col-md-6">
            <h1 class='mb-3'>Registrar Curso</h1>
            <hr>

            <form method="post" action="<%=request.getContextPath()%>/DecanoServlet?action=guardarCurso">
                <div class="mb-3">
                    <label class="form-label" for="codigo">Código</label>
                    <input type="text" class="form-control form-control-sm" id="codigo" name="codigoCurso" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="nombre">Nombre</label>
                    <input type="text" class="form-control form-control-sm" id="nombre" name="nombreCurso" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="idDocente">Docente</label>
                    <select name="idDocente" id="idDocente" class="form-select form-select-sm">
                        <% for (Usuario docente: listaDocentesPosibles) {%>
                        <option value="<%=docente.getIdUsuario()%>"><%=docente.getNombre()%>
                        </option>
                        <% }%>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="curso">Facultad</label>
                    <p class="bg-light" id="curso"><%=facultades.get(0).getFacultad().getNombre()%></p>
                </div>

                <a href="<%= request.getContextPath()%>/DecanoServlet" class="btn btn-danger">Cancelar</a>
                <input type="submit" value="Guardar" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col"></div>
    </div>
</div>

</body>
</html>