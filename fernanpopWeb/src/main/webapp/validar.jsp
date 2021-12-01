<%-- 
    Document   : validar
    Created on : 19 jun. 2021, 16:46:59
    Author     : Raul
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="https://kit.fontawesome.com/6aaba9f6ff.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="css/general.css">
    <link rel="stylesheet" href="css/login.css">
    <title>Regístrate</title>

</head>

<body>
 
        
        <div class="contenedor-principal">
        <h1>Formulario de validación</h1>
        <c:choose >
            <c:when test="${success}">
                 <h2>Te has registrado con éxito. Introduce el código que ha sido enviado a tu correo electrónico para validar tu cuenta.</h2>
            </c:when>
           
        </c:choose>
        
        <div class="card fix-registro">
            <form action="${pageContext.request.contextPath}/ServletValidacion" method="POST" class="form-login">
                <div class="control-login">
                    <label for="correo">Correo electrónico </label>
                    <input type="email" name="email" id="email" required maxlength="40" value="${correoUsuario}" >
                    <label for="token">Token de validación </label>
                    <input type="text" name="token" id="token" required maxlength="12">
                    <input type="submit" value="validar" name="btnValidar">
                </div>
            </form>
        </div>
       
        <!--AQUI EMPIEZA EL CONTENEDOR DE LAS BARRAS DE MENÚ-->
        <nav class="navegacion registro-margin-top">
            <ul class="barra flex-row-around">
                <li><a href="index.jsp">Inicio</a></li>
                <li><a href="login.jsp">Iniciar sesión</a></li>
            </ul>

        </nav>

        </div>
       
        
  
    


</body>

</html>
