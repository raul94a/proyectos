
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page session="true"%>
<jsp:useBean id="jo" class="controlador.JsltOperations"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
      <jsp:include page="includes/head.jsp"/>  

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
            <h2 class="margin-h2-valoraciones">Valoraciones recibidas</h2>


        </header>
        <!--Aquí empieza el contenedor de los productos que se cargaran por defecto ordenados de más recientes a más antiguos-->
        <c:if test="${jo.numeroValoraciones(sessionScope.usuario) eq 0}">  
            <h1 class="centrar" style="margin-top:10%">No has recibido ninguna valoración</h1>
        </c:if>
        <div class="contenedor-valoraciones">
            <c:forEach var="trato" items="${jo.seleccionarTratos(sessionScope.usuario)}">
            <div class="card-valoracion">
                <div class="usuario-valoracion">
                    <p class="fecha">${trato.fecha}</p>
                    <p>${jo.datosUsuario(trato.idOtroUser).nombre}</p>
                </div>
                ${jo.getEstrellas(trato.puntuacion)}
                <p class="comentario">${trato.comentario}</p>
            </div>
                <c:if test="${empty jo.seleccionarTratos(sessionScope.usuario)}">
                    <h1>No tienes tratos pendientes de valorar</h1> 
                </c:if>
            </c:forEach>

            


        </div>
        
      
    </div>
    <nav class="navegacion">
        <jsp:include page="includes/navegacion.jsp"/>
    </nav>
    <script src="js/barra.js"></script>
        <script src="js/background.js"></script>


</html>