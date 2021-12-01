<%-- 
    Document   : recuperacion
    Created on : 16 jun. 2021, 23:56:54
    Author     : Raul
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="https://kit.fontawesome.com/6aaba9f6ff.js" crossorigin="anonymous"></script>
    <title>Recordar contraseña</title>

</head>
<body>
    
  
    <!--Aquí se debe redirigir tras mostrar la pantalla de carga se redirecciona o se cargan los datos de la App
    Hay que ocultar el GIF con javascript, inactivarlo y activar la app

    -->
    <div class="contenedor-principal">
        <h1>Recordar contraseña</h1>
        <form action="login.jsp" method="POST" class="form-login">
             <div class="form-control">
                 <label for="correo">Introduce tu correo electrónico </label>
                 <input type="email" name="email" id="correo">
                 <input type="submit" value="Recuperar contraseña">
             </div>
        </form>
        <!--AQUI EMPIEZA EL CONTENEDOR DE LAS BARRAS DE MENÚ-->
        <nav class="navegacion">
            <ul class="barra-invitado">
                <li><a href="index.jsp">Inicio</a></li>
            </ul>
            
        </nav>

    </div>
    

</body>
</html>