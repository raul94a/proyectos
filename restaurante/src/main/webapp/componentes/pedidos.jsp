<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<h2 style="text-align:center;">Gestión de pedidos</h2>
<p style="text-align:center;" class="pedidos-pendientes">Pedidos pendientes: <span>${op.contarPedidosPendientes()}</span></p>
<div class="pedidos">
    <c:forEach var="pedido" items="${op.pedidoNoFinalizado()}">
    <div class="pedido" id="${pedido.getId()}">
        <div class="datos-usuario">
            <h2>Datos del usuario:</h2>
            <p>${pedido.getNombre()}</p>
            <p>${pedido.getDireccion()}</p>
            <p>${pedido.getTelefono()}</p>
        </div>
        <h2 class="titulo-lista-productos">Lista de productos:</h2> 
        <div class="productos">
               
            <c:forEach var="prod" items="${pedido.getProductos()}">
            <div class="producto">
                <p>${prod.getNombre()} <span> x ${prod.getCantidad()}</span></p>
                <!--<button>Listo!</button>-->
            </div>
            </c:forEach>
        </div>
        <a href="${pageContext.request.contextPath}/ServletDespacho?pedido=${pedido.getId()}" class="btnEnviarPedidoDomicilio">Finalizar Pedido</a>
    </div>
    </c:forEach>
</div>

<script src="js/pedidosDispatcher.js"></script>