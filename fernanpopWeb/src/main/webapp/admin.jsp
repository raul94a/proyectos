<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page session="true"%>
<jsp:useBean id="jo" class="controlador.JsltOperations"/>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<!DOCTYPE html>
<html style="height:100vh;">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" href="css/general.css">
         <link rel="stylesheet" href="css/login.css">
         <link rel="stylesheet" href="css/admin.css">
         <script src="https://kit.fontawesome.com/6aaba9f6ff.js" crossorigin="anonymous"></script>
        
        <title>Fernanpop admin</title>
    </head>
    <body>
         <div class="contenedor-principal">
        <h1>Fernanpop - admin</h1>
      

        <c:if test="${sessionScope.admin eq null}">    
            <div class="contenedor-registro">
                <div class="card fix-login">
                    <form action="${pageContext.request.contextPath}/Admin" method="POST" class="form-login">
                        <div class="control-login">
                            <label for="usuario">Usuario </label>
                            <input type="text" name="usuario" id="correo" required maxlength="40">
                            <label for="password">Contraseña </label>
                            <input type="password" name="password" id="password" maxlength="20" required>
                            <input type="submit" name="btnLogin" value="Iniciar sesión" class="btn3-b" id="enviar">
                        </div>
                    </form>

                </div>

            </div>
        </c:if>
        
        <c:if test="${sessionScope.admin eq true}">  
            <h2>Iniciada la sesión en modo administrador</h2>
            <main>
                <nav>
                    <a href="${pageContext.request.contextPath}/ServletAdmin?invitado=${jo.menuInvitado() ? 'false' : 'true'}&modo=menuInvitado">
                        ${jo.menuInvitado() ? 'Bloquear menú invitado' : 'Activar menú invitado'}
                    </a>
                    <a href="${pageContext.request.contextPath}/ServletAdmin?modo=pass">
                        Cambiar password
                    </a>
                    <a href="ServletAdmin?modo=excel">
                        Enviar excel con datos de usuarios
                    </a>
                    
                    <a href="${pageContext.request.contextPath}/ServletAdmin?modo=cerrar">
                        Cerrar sesión
                    </a>
                    <a href="diagrama.jsp">Diagrama de la App</a>
                </nav>
                <c:if test="${sessionScope.data eq true}">
                    <h2>Datos de usuario</h2>
                </c:if>
            </main>
        </c:if>
    </body>
</html>
