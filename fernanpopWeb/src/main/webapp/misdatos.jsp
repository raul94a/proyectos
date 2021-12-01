<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="jo" class="controlador.JsltOperations"/>
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
        <header class="header-datos-usuario">
            <!--FLEX BOX FILA (3 FILA))-->
            <div class="control-header-datos-usuario">
                <a href="perfil.jsp" class=""><i class="fas fa-arrow-left"></i></a>
                <!--FLEX BOX COLUMNA (2 COLUMNAs)-->
                <div class="contenedor-datos">
                    <c:set var="user" value="${jo.datosUsuario(sessionScope.usuario)}"/>
                    <p>${user.nombre}</p>
                    <p class="estrellas">
                        ${jo.getEstrellas(jo.valoracionMedia(sessionScope.usuario))}
                        <span>(${user.estadisticas.valoracionesRecibidas})</span>
                    </p>

                </div>
                <img src="img/${user.imgPerfil}" alt="foto-perfil" class="foto-perfil">
            </div>
                <div class="contenedor-estadisticas">
                    <!-- flex box en columnas -->
                    <i class="far fa-chart-bar"></i>
                    <p>ventas <span style="display:block; font-weight: 200;">${user.estadisticas.ventas}</span></p>
                    <p>compras <span style="display:block;font-weight: 200;">${user.estadisticas.compras}</span></p>
                </div>
            
                <div class="contenedor-menu-productos-opiniones">
                    <!--flexbox en columnas-->
                    <div class="en-venta">
                        <p>${jo.contarArrayList(jo.productosEnVenta(sessionScope.usuario))}</p>
                        <p class="active-option">en venta</p>
                    </div>
                    <div class="opiniones">
                        <p>${jo.contarArrayList(jo.getTratosCalificadosUsuario(sessionScope.usuario))}</p>
                        <p>opiniones</p>
                    </div>
                </div>
    
        </header>
        <div class="contenedor-productos-en-venta">
            <!--Mostrar porudctos en venta. Necesitaria una clase de activo / inactivo
                En movil mostramos de dos en dos los productos

                ya tengo hecho los estilos contenedor-producto 
                card-producto etc
            -->

            <div class="venta">
                <c:forEach var="pr" items="${jo.productosEnVenta(sessionScope.usuario)}">
                <div class="control-producto">
                    
                        <div class="card-producto">
                            <img src="img/${pr.imagen}" alt="fotodetalle" class="card-foto">
                            <p class="interesados">Numero de interesados: ${jo.contarInteresadosEnProducto(pr.id)}</p>
                            <p class="precio">${pr.precio} €</p>
                            <p class="nombre">${pr.nombre}</p>
                            <p class="estado">Estado: ${pr.estado}</p>
                            <p class="descripcion">${pr.descripcion}</p>
    
                        </div>
                   
                </div>
                </c:forEach>
            </div>
        </div>
        <div class="contenedor-opiniones" style="display:none;">
            <!--Mostrar toas las opiniones / valoraciones necesitaria clase de activo inactivo. Se encarga javascript-->
            <c:forEach var="val" items="${jo.getTratosCalificadosUsuario(sessionScope.usuario)}">
            <div class="card-opinion">
                <div class="contenedor-estadisticas">
                    
                    <p>${jo.datosUsuario(val.idOtroUser).nombre} te ha valorado con:</p>
                    
                    <p class="estrellas">${jo.getEstrellas(val.puntuacion)}</p>
                </div>
               
                <p>${val.comentario}</p>
            </div>
            </c:forEach>
          
        </div>

    </div>
    <script src="js/ocultarMostrar.js"></script>

</body>

</html>