<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css"
        integrity="sha512-xodZBNTC5n17Xt2atTPuE1HxjVMSvLVW9ocqUKLsCC5CXdbqCmblAshOMAS6/keqq/sMZMZ19scR4PsZChSR7A=="
        crossorigin=""/>
        <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"
        integrity="sha512-XQoYMqMTK8LvdxXYG3nZ448hOEQiglfqkJs1NOQV44cWnUrBc8PkAOcXy20w0vlaXaVUearIOBhiXZ5V3ynxwA=="
        crossorigin=""></script>
    </head>
    <body>
        <header>
            <div class="rotulo">
                <img src="img/baviera.jpg" alt="rotulo baviera">
                <h2>Restaurante baviera</h2>
            </div>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/Servlet?a=c">Conocenos</a></li>
                    <li><a href="${pageContext.request.contextPath}/Servlet?a=n">Nuestra carta</a></li>
                    <li><a href="${pageContext.request.contextPath}/Servlet?a=domicilio">Pedir a domicilio</a></li>
                </ul>
            </nav>
        </header>
        <main>
           
            <c:if test="${sessionScope.a eq 'n'}">
                <jsp:include page="componentes/carta.jsp"/>
            </c:if>
            
            <c:if test="${sessionScope.a eq 'c' or sessionScope.a eq null}">  
                <jsp:include page="componentes/conocenos.jsp"/>
            </c:if>
            
            <c:if test="${sessionScope.a eq 'domicilio'}">
                <jsp:include page="componentes/domicilio.jsp"/>
            </c:if>  
            
            <c:if test="${sessionScope.a eq 'factura'}">   
                <jsp:include page="componentes/factura.jsp"/>
            </c:if>
        </main>
        <c:if test="${sessionScope.a eq 'c' or sessionScope.a eq null}">  
            <script src="js/map.js"></script>
        </c:if>
        <footer>
            <p>Proyecto realizado por Raúl Albín Alba</p>
            <p style="font-weight: bolder">Grado Superior en Desarrolo de Aplicaciones Multiplataforma</p>
            <p>IES Fernando III, Martos (Jaén)</p>
        </footer>
            <script>
                
                
                
                
            </script>
    </body>
</html>
