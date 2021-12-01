<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page session="true"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="srv" class="controlador.Servlet"/>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/main.css">
    </head>
    <body>
       <div class="backdrop ocultar"></div>

        <div class="contenedor-principal">
            <header>
                <h1 class="titulo-principal">Restaurante Baviera</h1>
                <h2 class="subtitulo-principal">Bienvenido al programa de gestión del restaurante</h2>
                <c:if test="${sessionScope.mensaje != null}">
                      <h3 style="text-align:Center">Último movimiento: </h3>
                        <h4 style="text-align:Center; font-size: 1.6rem;">${sessionScope.mensaje}</h4>
                </c:if>
              
              
             
            </header>
            <c:if test="${param['i'] eq null or param['i'] != '0'}">
                <a href="${pageContext.request.contextPath}/Servlet?a=0" class="btnVolver">Volver al menú principal</a>

            </c:if>
            <!--<c:if test="${json != null}">   
                <c:forEach var="d" items="${json}">
                    <p>${d.identificador}  ${d.cantidad}</p>
                </c:forEach>
            </c:if>-->
            
            <c:if test="${param.i eq null || param.i eq 0}">
  
                <p class="pedidos-pendientes">Pedidos pendientes: <span id="pedidos-pendientes">${op.contarPedidosPendientes()}<span></p>
        
            <nav class="navegacion">
                <a href="${pageContext.request.contextPath}/Servlet?a=1" class="btnNav">Reservar mesa</a>
                <a href="${pageContext.request.contextPath}/Servlet?a=2" class="btnNav">Liberar mesa</a>
                <a href="${pageContext.request.contextPath}/Servlet?a=3" class="btnNav">Añadir a mesa</a>
                <a href="${pageContext.request.contextPath}/Servlet?a=10" class="btnNav" id="pedidos-domicilio">Pedidos a domicilio</a>
                <a href="${pageContext.request.contextPath}/Servlet?a=5" class="btnNav">Gestion de Carta</a>
                <a href="${pageContext.request.contextPath}/Servlet?a=6" class="btnNav">Estadísticas</a>
            </nav>
               
            </c:if>
            <c:if test="${param.i eq '1' || param.i eq '2' || param.i eq '3'}">
                <jsp:include page="componentes/mesas.jsp"/>
            </c:if>
            <c:if test="${param.i eq '4'}">
                <jsp:include page="componentes/add.jsp"/>
            </c:if>
            <c:if test="${param.i eq '5'}">
                <jsp:include page="componentes/gestion-carta.jsp" />
            </c:if>
            <c:if test="${param.i eq '6'}">
                <jsp:include page="componentes/gestion-restaurante.jsp"/>
            </c:if>
            <c:if test="${param.i eq '7'}">  
                <jsp:include page="componentes/gestion-carta.jsp" />
            </c:if> 
             <c:if test="${param.i eq '8'}">
                <jsp:include page="componentes/factura.jsp"/>
            </c:if>
            <c:if test="${param.i eq '10'}">
                <jsp:include page="componentes/pedidos.jsp"/>
            </c:if>
           
        </div>
        <footer>
            <p>Proyecto realizado por Raúl Albín Alba</p>
            <p style="font-weight: bolder">Grado Superior en Desarrolo de Aplicaciones Multiplataforma</p>
            <p>IES Fernando III, Martos (Jaén)</p>
        </footer>
              <c:if test="${param.i eq '0' || param.i eq null}">    
                <script src="js/modificarIndex.js"></script>

              </c:if>

     </body>
</html>
