<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page session="true"%>
<jsp:useBean id="jo" class="controlador.JsltOperations"/>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="includes/head.jsp"/>  
       <script src="js/barra.js" defer></script>
</head>

<body>
    <c:if test="${!jo.menuInvitado() and sessionScope.usuario eq null}">
        <c:redirect url = "login.jsp"/>
    </c:if>
      <div class="loading">
            <h1 class="encabezado-principal centrar">FernanPop</h1>
            <h2 id="subtitulo-principal">La aplicación de compra-venta del IES Fernando III</h2>
            <c:if test="${sessionScope.mensaje != null}">
                <h2 class="centrar">${sessionScope.mensaje}</h2>
                <c:set var="mensaje" scope="session" value="${null}"/>
                <c:set var="loading" scope="session" value="${false}"/>
            </c:if>   
             <div class="loader">
                 <div></div><div></div><div></div>
             </div>
       
       </div>
  
    

    <div class="contenedor-principal index">
        <!--Menú de búsqueda de productos-->
        <div class="frm-busqueda">
            <form action="${pageContext.request.contextPath}/ServletBuscador" method="get" class="busqueda">
                <input type="search" name="buscador" placeholder="Buscar productos...">
                <input type="submit" name="btnBuscar" value="Buscar" class="btn2">
            </form>
        </div>
                    
           
        <c:if test="${empty jo.productosApp() and prods == null and sessionScope.usuario == null}">
           <h1 class="centrar" style="width:90%;margin:0 auto;margin-top:5%">No hay ningún producto a la venta.</h1>
       </c:if>
           
       <c:if test="${sessionScope.usuario != null}">
            <c:if test="${empty jo.productosAjenos(sessionScope.usuario) and prods == null}">
                <h1 class="centrar" style="width:90%;margin:0 auto;margin-top:5%">No hay ningún producto a la venta.</h1>
            </c:if>
       </c:if>
       
        <c:if test="${empty prods and prods != null}">
            <h1 class="centrar">La búsqueda no ha arrojado ningún resultado.</h1>
       </c:if>
         
           
        <!--Aquí empieza el contenedor de los productos que se cargaran por defecto ordenados de más recientes a más antiguos-->
        <div class="contenedor-productos">
           
           <c:choose>
              
               <c:when test="${prods == null and sessionScope.usuario != null}">
                   <%System.out.println("En el if de mostrar los productos ajenos");%>
    
                    <c:choose>
                        <c:when test="${not empty jo.productosAjenos(sessionScope.usuario)}">
                           
                            <c:forEach var="p" items="${jo.productosAjenos(sessionScope.usuario)}">
                                <a href="${pageContext.request.contextPath}/ServletProducto?prod=${p.id}">
                                <div class="card-producto" >
                                    <img loading="lazy" src="./img/${p.getImagen()}" alt="" class="card-foto">
                                    <p class="card-precio">${p.precio} €</p>
                                    <p class="card-nombre">${p.nombre}</p>
                                    <input type="hidden" value="${p.id}">
                                    <p class="descripcion" style="display:none;height:7.5rem" id="${p.id}">${p.descripcion}</p>

                                 </div>
                                 </a>
                           </c:forEach>
                        </c:when>
                           
                     </c:choose>
               </c:when>
               
               <c:when test="${prods == null && sessionScope.usuario == null}">
                    <c:choose>
                        <c:when  test="${not empty jo.productosApp()}">
                            <c:forEach var="p" items="${jo.productosApp()}">
                                <a href="${pageContext.request.contextPath}/ServletProducto?prod=${p.id}">
                                <div class="card-producto" >
                                    <img src="./img/${p.getImagen()}" alt="" class="card-foto">
                                    <p class="card-precio">${p.precio} €</p>
                                    <p class="card-nombre">${p.nombre}</p>
                                    <input type="hidden" value="${p.id}">
                                    <p class="descripcion" style="display:none" id="${p.id}">${p.descripcion}</p>

                                 </div>
                                 </a>
                           </c:forEach>
                        </c:when>
                        
                     </c:choose>
               </c:when>
                            
                <c:when test="${prods != null}">
                     <%System.out.println("En el if de mostrar los productos buscados");%>
                     <c:choose>
                        <c:when  test="${not empty prods}">
                            <c:forEach var="p" items="${prods}">
                                <a href="${pageContext.request.contextPath}/ServletProducto?prod=${p.id}">
                                <div class="card-producto" >
                                    <img src="./img/${p.getImagen()}" alt="" class="card-foto">
                                    <p class="card-precio">${p.precio} €</p>
                                    <p class="card-nombre">${p.nombre}</p>
                                    <input type="hidden" value="${p.id}">
                                    <p class="descripcion" style="display:none" id="${p.id}">${p.descripcion}</p>

                                 </div>
                                 </a>
                           </c:forEach>
                        </c:when>
                        
                     </c:choose>
                </c:when>
           </c:choose>
 
        </div>
        

    </div>
    <!--AQUI EMPIEZA EL CONTENEDOR DE LAS BARRAS DE MENÚ-->
    <nav class="navegacion">
        <% if (session.getAttribute("usuario") == null) { %>
        <ul class="barra flex-row-around">
            <c:if test="${cookie.tema.value != 'oscuro' or cookie.tema.value == null}">    
                <li id="btn-oscuro">
                    <i class="far fa-moon"></i>
                </li>
             </c:if>
             <c:if test="${cookie.tema.value eq 'oscuro'}">    
               <li id="btn-claro">
                 <i class="far fa-sun"></i>
               </li>
             </c:if>
            <li><a href="login.jsp" id="inicio-sesion">Iniciar sesión</a></li>
            <li><a href="registro.jsp" id="registro-usuario">Registrarse</a></li>
            <li class="li-menu">
                <i class="fas fa-bars" style="display:none;"></i>
            </li>
            
        </ul>
        <% } else { %>
        <jsp:include page="includes/navegacion.jsp"/>
        <% } %>
    </nav>
 
    <script src="js/loader.js"></script>
    <script src="js/info-producto.js"></script>
    <script src="js/background.js"></script>

</body>

</html>