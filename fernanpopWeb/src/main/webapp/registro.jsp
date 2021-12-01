<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="https://kit.fontawesome.com/6aaba9f6ff.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="css/general.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="js/formularios.js" defer></script>
    <title>Regístrate</title>

</head>

<body>
    <c:if test="${sessionScope.usuario != null}">
        <c:redirect url="index.jsp"/>
    </c:if>
    <div class="contenedor-principal">
        <h1>Formulario de registro - FernanPop</h1>
        <h2>${existeEmail?"El email ya está registrado. Por favor, regístrese con uno diferente":""}</h2>
        <h2>${success == 0?"Las contraseñas no coinciden":""}</h2>

        <div class="card fix-registro">
            <form action="${pageContext.request.contextPath}/ServletRegistro" method="POST" class="form-login" id="form-login">
                <div class="control-login">
                    <label for="nombre">Nombre </label>
                    <input type="text" name="nombre" id="nombre" required maxlength="25">
                    <label for="movil">Movil </label>
                    <input type="number" name="movil" id="movil" required min="600000000" max="699999999" >
                    <label for="correo">Correo electrónico </label>
                    <input type="email" name="email" id="email" required maxlength="40">
                    <label for="sexo">Sexo</label>
                    <div class="controlRadio">
                        
                        <input type="radio" name="sexo" value="M" id="sexo" "><label style="color:white; margin-right:15%;">M</label>
                        <input type="radio" name="sexo" value="F""><label  style="color:white">F</label>
                    </div>
                    <label for="password">Contraseña </label>
                    <input type="password" name="password" id="password" required maxlength="12">
                    <label for="password-rep" >Repite la contraseña </label>
                    <input type="password" name="password-rep" id="password-rep" required maxlength="12">
                    
                    <input type="submit" name="btnRegistro" value="Registrarse" id="enviar">
                    
                </div>
            </form>
        </div>
       
        <!--AQUI EMPIEZA EL CONTENEDOR DE LAS BARRAS DE MENÚ-->
        <nav class="navegacion registro-margin-top bg-darkblue">
            <ul class="barra flex-row-around">
                <li><a href="index.jsp">Inicio</a></li>
                <li><a href="login.jsp">Iniciar sesión</a></li>
            </ul>

        </nav>

    </div>


</body>

</html>