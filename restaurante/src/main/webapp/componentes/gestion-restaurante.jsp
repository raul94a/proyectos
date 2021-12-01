<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



    <c:if test="${param.st eq null}">
        <h2 class="centrar">NAVEGACIÓN ESTADÍSTICAS</h2>
        <nav class="navegacion">
            <a href=".?i=6&st=1" class="btnNav">Estadísticas del día</a>
            <a href=".?i=6&st=2" class="btnNav">Estadísticas de la semana</a>
            <a href=".?i=6&st=3" class="btnNav">Estadísticas globales</a>
        <nav>
     </c:if>
     
     <c:if test="${param.st eq '1' or param.st eq '2' or param.st eq '3'}">
         <jsp:include page="estadisticas.jsp"/>
     </c:if>
    


   