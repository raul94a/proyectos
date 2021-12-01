<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--<c:if test="${param.t eq null}">-->
    <h3 style="text-align:center">GESTIÓN DE LA CARTA</h3>
    <nav class="navegacion">
        <a id="btnAddProducto" class="btnNav">Añadir producto</a>
        <a id="btnEliminarProducto" class="btnNav">Eliminar producto</a>
        <a id="btnModificarProducto" class="btnNav">Modificar producto</a>
        <a id="btnVerCarta" class="btnNav">Ver carta</a>
    </nav>
  
<!--</c:if>-->
<!--<c:if test="${param.t eq '1'}">
    <c:if test="${param.success != null}">
        <h2>El producto ha sido añadido con éxito</h2>
        <p>Si no deseas añadir más productos <a href=".">pulsa aquí</a></p>
    </c:if>-->
<!--</c:if>-->
 <div class="modal ocultar" id="modalAddProd">
     <button class="btnEnviarPedidoDomicilio" id="btnCerrarAddProd">CERRAR</button>
     <h2 class="centrar">Añadir un producto a la carta</h2>
        <form action="${pageContext.request.contextPath}/ServletCarta" method="POST" class="form-app mod-form">
            <input type="hidden" name="mode" value="1">
            <label>Nombre</label>
            <input type="text" name="nombre" required>
            <select name="tipo" required>
                <option value="carne">Carne</option>
                <option value="pescado">Pescado</option>
                <option value="pasta">Pasta</option>
                <option value="bebida">Bebida</option>
            </select>
            <label>Precio</label>

            <input type="number" name="precio" max="200" step="0.01" required>
            <input type="submit" value="Añadir" class="btnVolver">
        
        </form>
        
    </div>
            
    <div class="modal ocultar" id="modalEliminarProd">
        <button class="btnEnviarPedidoDomicilio" id="btnCerrarEliminarProd">CERRAR</button>

        <h2>Eliminar un producto de la carta</h2>
       <div class="productos-modal-mod">
        <div class="carnes">
            <h2 class="centrar">Carnes</h2>
        <c:forEach var="producto" items="${op.productosPorTipo('carne','true')}" varStatus="i">
           <form action="${pageContext.request.contextPath}/ServletCarta" method="POST" class="form-modal">
                <input type="hidden" name="mode" value="2">
                <label>${producto.getNombre()}</label>
                <input type="hidden" name="id" value="${producto.getId()}">
                <input type="submit" value="eliminar">

            </form>
        </c:forEach>
        </div>
        <div class="pescados">
            <h2 class="centrar">Pescados</h2>
        <c:forEach var="producto" items="${op.productosPorTipo('pescado','true')}" varStatus="i">
            <form action="${pageContext.request.contextPath}/ServletCarta" method="POST" class="form-modal">
                <input type="hidden" name="mode" value="2">
               <label>${producto.getNombre()}</label>
                <input type="hidden" name="id" value="${producto.getId()}">
                <input type="submit" value="eliminar">

            </form>
        </c:forEach>
        </div>
          <div class="pastas">
            <h2 class="centrar">Pastas</h2>
        <c:forEach var="producto" items="${op.productosPorTipo('pasta','true')}" varStatus="i">
           <form action="${pageContext.request.contextPath}/ServletCarta" method="POST" class="form-modal">
                <input type="hidden" name="mode" value="2">
                <label>${producto.getNombre()}</label>

                <input type="hidden" name="id" value="${producto.getId()}">
                <input type="submit" value="eliminar">

            </form>
        </c:forEach>
        </div>
          <div class="bebidas">
            <h2 class="centrar">Bebidas</h2>
        <c:forEach var="producto" items="${op.productosPorTipo('bebida','true')}" varStatus="i">
          <form action="${pageContext.request.contextPath}/ServletCarta" method="POST" class="form-modal">
                <input type="hidden" name="mode" value="2">
               <label>${producto.getNombre()}</label>
                <input type="hidden" name="id" value="${producto.getId()}">
                <input type="submit" value="eliminar">

            </form>
        </c:forEach>
        </div>

   </div>
</div>
            
            
    <div class="modal ocultar" id="modalModProd">
        <button class="btnEnviarPedidoDomicilio" id="btnCerrarModProd">CERRAR</button>

        <h2>Modificar productro de la carta</h2>
        <c:forEach var="producto" items="${op.productos()}">
        <form action="${pageContext.request.contextPath}/ServletCarta" method="POST" class="form-modal">
            <input type="hidden" name="mode" value="3">
            <input type="text" name="nombre" value="${producto.getNombre()}">
            <input type="text" name="tipo" value="${producto.getTipo()}">
            <input type="number" name="precio" value="${producto.getPrecio()}">
            <input type="hidden" name="id" value="${producto.getId()}">
            <input type="submit" value="Modificar" class="btnModProd">

        </form>
        </c:forEach>
        
    </div>   
            
    <div class="modal ocultar" id="modalVerCarta">
        <button class="btnEnviarPedidoDomicilio" id="btnCerrarVerCarta">CERRAR</button>

        <div class="productos">
            <div class="carnes">
                <h2>Carnes</h2>
            <c:forEach var="producto" items="${op.productosPorTipo('carne','true')}" varStatus="i">
                <c:if test="${(op.sumarUno(i.index)) % 2 eq 0}">
                    <p class="par">${op.sumarUno(i.index)}. ${producto.getNombre()} ...............    ${producto.getPrecio()} €</p> 
                </c:if>
                <c:if test="${(op.sumarUno(i.index)) % 2 != 0}">
                    <p class="impar">${op.sumarUno(i.index)}. ${producto.getNombre()}...............    ${producto.getPrecio()} €</p> 
                </c:if>
            </c:forEach>
            </div>
            <div class="pescados">
                <h2>Pescados</h2>
            <c:forEach var="producto" items="${op.productosPorTipo('pescado','true')}" varStatus="i">
                <c:if test="${(op.sumarUno(i.index)) % 2 eq 0}">
                    <p class="par">${op.sumarUno(i.index)}. ${producto.getNombre()} ...............    ${producto.getPrecio()} €</p> 
                </c:if>
                <c:if test="${(op.sumarUno(i.index)) % 2 != 0}">
                    <p class="impar">${op.sumarUno(i.index)}. ${producto.getNombre()}...............    ${producto.getPrecio()} €</p> 
                </c:if>
            </c:forEach>
            </div>
              <div class="pastas">
                <h2>Pastas</h2>
            <c:forEach var="producto" items="${op.productosPorTipo('pasta','true')}" varStatus="i">
                <c:if test="${(op.sumarUno(i.index)) % 2 eq 0}">
                    <p class="par">${op.sumarUno(i.index)}. ${producto.getNombre()} ...............    ${producto.getPrecio()} €</p> 
                </c:if>
                <c:if test="${(op.sumarUno(i.index)) % 2 != 0}">
                    <p class="impar">${op.sumarUno(i.index)}. ${producto.getNombre()}...............    ${producto.getPrecio()} €</p> 
                </c:if>
            </c:forEach>
            </div>
              <div class="bebidas">
                <h2>Bebidas</h2>
            <c:forEach var="producto" items="${op.productosPorTipo('bebida','true')}" varStatus="i">
                <c:if test="${(op.sumarUno(i.index)) % 2 eq 0}">
                    <p class="par">${op.sumarUno(i.index)}. ${producto.getNombre()} ...............    ${producto.getPrecio()} €</p> 
                </c:if>
                <c:if test="${(op.sumarUno(i.index)) % 2 != 0}">
                    <p class="impar">${op.sumarUno(i.index)}. ${producto.getNombre()}...............    ${producto.getPrecio()} €</p> 
                </c:if>
            </c:forEach>
            </div>

        </div>
    </div>
    <script src="js/modal.js"></script>
<!--<c:if test="${param.t eq '2'}">-->
   
   
<!--</c:if>-->

<!--<c:if test="${param.t eq '3'}">-->
    
    
<!--</c:if>-->

<!--<c:if test="${param.t eq '4'}">-->
    
       
<!--</c:if>-->