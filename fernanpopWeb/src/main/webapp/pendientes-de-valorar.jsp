<%-- 
    Document   : pendientes-de-valorar
    Created on : 16 jun. 2021, 17:16:33
    Author     : Raul
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page session="true"%>
<jsp:useBean id="jo" class="controlador.JsltOperations"/>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
      <link rel="stylesheet" href="css/modales.css">
    <jsp:include page="includes/head.jsp"/>  
      <script src="js/formularios.js" defer></script>



</head>

<body>
 <c:if test="${sessionScope.usuario eq null}">
        <c:redirect url="login.jsp"/>
    </c:if>
    <div class="contenedor-principal">
        <header class="barra-superior-valoraciones outline-blue">
            <div class="contenedor-volver">
                <a href="perfil.jsp" class=""><i class="fas fa-arrow-left"></i></a>
            </div>
            <h2 class="margin-h2-valoracion-pendiente">Pendientes de valorar</h2>
          
        </header>
        <c:if test="${empty jo.seleccionarTratosValorables(sessionScope.usuario)}">
            <h1 class="centrar" style="margin-top:10%">No tienes tratos pendientes de valorar</h1> 
        </c:if>
        <!--Aquí empieza el contenedor de los productos que se cargaran por defecto ordenados de más recientes a más antiguos-->
       <c:if test="${success}">
        <h2 class="centrar">El trato ha sido valorado correctamente</h2>
        </c:if>
        <div class="contenedor-valoraciones">
            <c:forEach var="trato" items="${jo.seleccionarTratosValorables(sessionScope.usuario)}">
            <div class="card-valoracion val-selector">
                <p class="datos-valoracion">
                    <c:if test="${trato.idOtroUser == jo.toInt(sessionScope.usuario)}">
                    Compraste el producto ${trato.producto.nombre} el <span>${trato.fecha} </span>al usuario
                    ${jo.datosUsuario(trato.producto.vendedor).nombre}
                    </c:if>
                    <c:if test="${trato.producto.vendedor == jo.toInt(sessionScope.usuario)}">
                        Vendiste el producto ${trato.producto.nombre} el <span>${trato.fecha} </span> al usuario
                        ${jo.datosUsuario(trato.idOtroUser).nombre}
                    </c:if>
                </p>
             
                <button class="btn4-b cntrl-btn" id="${trato.id}/${jo.toInt(sessionScope.usuario) == trato.idOtroUser ? trato.producto.vendedor : trato.idOtroUser}" data-open="modal-valorar">Valorar usuario</button>
               
            </div>
            </c:forEach>
             
        </div>
       
    </div>

    <nav class="navegacion">
        <jsp:include page="includes/navegacion.jsp"/>
    </nav>


    <div class="modal-editar" id="modal-valorar">
        <div class="control-modal">
            <button class="btn1red" data-close>X</button>
            <form action="${pageContext.request.contextPath}/ServletValorar" method="GET" class="form-subir-producto">
                <label for="Valoracion">Valoracion</label>
                <input type="number" name="val" id="valoracion" min="0" max="5" step="0.1">
                <label for="comentario">Comentario</label>
                <textarea name="com" id="comentario" cols="30" rows="10"></textarea>
                <input class="id-trato"type="hidden" name="trato" value="">
                <input class="id-usuarioValorado" type="hidden" name="usuarioValorado" value=""}"> 
                <input type="submit" name="btnVal"value="Valorar usuario" id="enviar" class="btn2">
    
            </form>
        </div>
    </div>
    <script src="js/barra.js"></script>
    <script src="js/modals.js"></script>
    <script src="js/val-trato.js"></script>
     <script src="js/background.js"></script>
</body>

</html>