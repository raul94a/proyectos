

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/general.css">
    <link rel="stylesheet" href="css/login.css">

    <script src="https://kit.fontawesome.com/6aaba9f6ff.js" crossorigin="anonymous"></script>
    <script src="js/formularios.js" defer></script>
    <title>Iniciar sesión</title>

</head>

<body>


    <!--Aquí se debe redirigir tras mostrar la pantalla de carga se redirecciona o se cargan los datos de la App
    Hay que ocultar el GIF con javascript, inactivarlo y activar la app

    -->
    <c:if test="${sessionScope.usuario != null}">   
        <c:redirect url="index.jsp"/>
    </c:if>
    <div class="contenedor-principal">
        <h1>Inicio de sesión  - FernanPop</h1>
        <c:if test="${success != null}">
            <h2>${success != true ? "Los datos introducidos no son correctos":""}</h2>
        </c:if>
        <c:if test="${sessionScope.mensaje != null}">
            <h2>${sessionScope.mensaje}</h2>
           <c:set var ="mensaje" scope="session" value="${null}"/>
        </c:if>
        <div class="contenedor-registro">
            <div class="card fix-login">
                <form action="${pageContext.request.contextPath}/ServletLogin" method="POST" class="form-login">
                    <div class="control-login">
                        <label for="correo">Correo </label>
                        <input type="email" name="email" id="correo" required maxlength="40" value="${sessionScope.email != null ? sessionScope.email : ''}">
                        <label for="password">Contraseña </label>
                        <input type="password" name="password" id="password" maxlength="12" required>
                        <input type="submit" name="btnLogin" value="Iniciar sesión" class="btn3-b" id="enviar">
                    </div>
                </form>
    
            </div>
            <button class="btn4-b pass-olvidada" data-open="modal-recuerdo-pass">He olvidado mi contraseña</button>

        </div>

      
        <!--MODAL-->
        <div class="modal1" id="modal-recuerdo-pass">
            <div class="control-modal1">
                <button class="cerrar-modal" data-close>X</button>
                <h2>Recordar contraseña</h2>
            </div>
            <form action="${pageContext.request.contextPath}/RecordarPass" method="post" class="form-modal">
                <label for="recuerdo">Introduce tu correo electrónico</label>
                <input type="email" name="email" id="recuerdo" required maxlength="40">
                <input type="submit" class="btn3-b" name="submit" id="enviar"value="Enviar">
            </form>
        </div>
        <!--FIN MODAL-->
        <!--AQUI EMPIEZA EL CONTENEDOR DE LAS BARRAS DE MENÚ-->
        <nav class="navegacion nav-movil bg-darkblue">
            <ul class="barra flex-row-around">
                
                <li><a href="index.jsp">Inicio</a></li>
                <li><a href="registro.jsp">Registrarse</a></li>
            </ul>
        </nav>
    </div>
    <script src="js/modals.js"></script>
  
    
</body>

</html>