<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="jo" class="controlador.JsltOperations"/>
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
        <header class="barra-superior-ventas outline-blue">
            <div class="contenedor-volver">
                <a href="perfil.jsp" class=""><i class="fas fa-arrow-left"></i></a>
                <h2 class="centrar-w60">Ventas</h2>
            </div>


        </header>
          <c:if test="${empty jo.getVentasUsuario(sessionScope.usuario)}">
                <h1 class="centrar" style="margin-top:10%">No has realizado ninguna venta aún.</h1>
            </c:if>

        <!--Aquí empieza el contenedor de los productos que se cargaran por defecto ordenados de más recientes a más antiguos-->
        <div class="contenedor-productos">
            <c:forEach var="venta" items="${jo.getVentasUsuario(sessionScope.usuario)}">
            <div class="card-producto contenedor-ventas">
                <img src="img/${jo.getProducto(venta.producto.id).imagen}" alt="fotodetalle" class="card-foto">
                <p class="fecha">${venta.fecha}</p>
                <p class="precio">Precio de venta: ${venta.precio} €</p>
                <p class="nombre">Producto: ${jo.getProducto(venta.producto.id).nombre}</p>
                <p class="usuario">Comprador: ${jo.datosUsuario(venta.idOtroUser).nombre}</p>
                ${jo.getEstrellas(venta.puntuacion)}
                <p class="comentario">${venta.comentario}</p>
                <c:if test="${not jo.isTratoCalificado(jo.getIdCompra(venta.idOtroUser, venta.producto.vendedor, venta.producto.id))}">
                <button class="btn3-b v" id="${jo.getIdCompra(venta.idOtroUser, venta.producto.vendedor, venta.producto.id)}/${venta.idOtroUser}" data-open="modal-valorar">Valorar comprador</button>
                </c:if>
                <!--<c:if test="${jo.isTratoCalificado(jo.getIdCompra(venta.idOtroUser, venta.producto.vendedor, venta.producto.id))}">
                <button class="btn4-b e" id="${jo.getIdCompra(venta.idOtroUser, venta.producto.vendedor, venta.producto.id)}" data-open="modal-editar-valoracion">Editar valoracion</button>
                </c:if>-->
            </div>
            </c:forEach>
         
           
           

        </div>

    </div>

    <nav class="navegacion">
       <jsp:include page="includes/navegacion.jsp"/>
    </nav>
<!--MODAL VALORAR COMPRAOR-->

<div class="modal-editar" id="modal-valorar">
    <div class="control-modal">
        <button class="btn1red" data-close>X</button>

        <form action="${pageContext.request.contextPath}/ServletValorar" method="GET" class="form-subir-producto">
            <label for="Valoracion">Valoracion</label>
            <input type="number" name="val" id="valoracion" min="0" max="5" step="0.1">
            <label for="comentario">Comentario</label>
            <textarea name="com" id="comentario" cols="30" rows="10"></textarea>
            <input class="a" type="hidden" name="trato" value="">
            <input class="b" type="hidden" name="usuarioValorado" value="">
            <input type="submit" name="btnVal"value="Valorar usuario" id="enviar" class="btn2" data-close>

        </form>
    </div>
</div>

<div class="modal-editar" id="modal-editar-valoracion">
    <div class="control-modal">
        <button class="btn1red" data-close>X</button>
        <form action="#" method="GET" class="form-subir-producto">
            <label for="Valoracion">Valoracion</label>
            <input type="number" name="valoracion" id="valoracion" min="0" max="5" step="0.1">
            <label for="comentario">Comentario</label>
            <textarea name="comentario" id="comentario" cols="30" rows="10"></textarea>
            <input type="submit" value="Editar valoracion" class="btn2" data-close>

        </form>
    </div>
</div>

<script src="js/modals.js"></script>
<script src="js/barra.js"></script>
<script src="js/compra-venta-val.js"></script>
    <script src="js/background.js"></script>

</body>

</html>