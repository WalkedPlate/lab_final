<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form </title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- Enlazamos archivo css -->
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="container">
    <div class="wrapper">

        <div class="title"><span>INICIA SESIÓN</span></div>
        <%if (request.getAttribute("err")!=null) {%>
        <div class="alert alert-danger" role="alert"><%=request.getAttribute("err")%>
        </div>
        <%request.removeAttribute("err");%>
        <% } %>
        <form method="post" action="<%=request.getContextPath()%>/LoginServlet?action=login">
            <div class="row">
                <i class="fas fa-user"></i>
                <input type="text" id="correo" name="correo" class="form-control" placeholder="Email" required="">
            </div>
            <div class="row">
                <i class="fas fa-lock"></i>
                <input type="password" id="password" name="password" class="form-control" placeholder="Contraseña" required="">
            </div>
            <div class="row button">
                <button type="submit" class="btn btn-outline-danger">Acceder</button>
            </div>

        </form>

    </div>
</div>
</body>
</html>