<!--Seleccionar la mesa LIBRE que se quiera reservar-->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contenedor-mesas">
        <c:if test="${param.i eq '1'}">
            <h3 style="text-align:center">OCUPAR MESA</h3>
        <nav class="navegacion" style="margin-top:2%;">
        <c:forEach var="m" items="${op.mesas('true')}">
            
            <a href="${pageContext.request.contextPath}/ServletGestion?mesa=${m}&tipo=reserva" class="btnNav">Mesa ${m}</a>
       
        </c:forEach>
            </nav>
            <c:if test="${ empty op.mesas('true')}">
                <h2>No hay mesas disponibles en este momento</h2>
                  <a href="./?i=0">Volver</a>
            </c:if>
        </c:if>
    
     <c:if test="${param.i eq '2'}">
         <h3 style="text-align:center">Liberar Mesa</h3>
         <nav class="navegacion" style="margin-top:2%;">
        <c:forEach var="m" items="${op.mesas('false')}">
         <a href="${pageContext.request.contextPath}/ServletGestion?mesa=${m}&tipo=pago" class="btnNav">Mesa ${m}</a>
        </c:forEach>
        </nav>
         <c:if test="${empty op.mesas('false')}">
                <h2>No hay mesas reservadas</h2>
                  <a href="./?i=0">Volver</a>
            </c:if>
    </c:if>
    
    <c:if test="${param.i eq '3'}">
        <h3 style="text-align:center">Pedido de Mesa</h3>
        <nav class="navegacion"style="margin-top:2%;">

        <c:forEach var="m" items="${op.mesas('false')}">
            
        <a href="${pageContext.request.contextPath}/ServletGestion?mesa=${m}&tipo=pedido" class="btnNav">Mesa ${m}</a>

        </c:forEach>
            </nav>
         <c:if test="${empty op.mesas('false')}">
                <h2>No hay mesas reservadas</h2>
                <a href="./?i=0">Volver</a>
        </c:if>
    </c:if>
    
  
</div>