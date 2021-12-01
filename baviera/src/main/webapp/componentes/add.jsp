<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<div class="contenedor-productos">
    <div class="productos-add" id="productos-add">
        <div class="carnes">
            <h2>CARNES</h2>
            <c:forEach var="producto" items="${op.productosPorTipo('carne', 'true')}">
            <div class="info-producto">
                <p>${producto.getNombre()}</p>
             
                <div>
                      <p>${producto.getPrecio()}</p>
                    <input type="number" name="qty">
                    <button id="${producto.getId()}" class="btnCartaDomicilio">Añadir</button>
                </div>
                
            </div>
            </c:forEach>
      
        </div>
        <div class="pescados">
            <h2>PESCADOS</h2>

            <c:forEach var="producto" items="${op.productosPorTipo('pescado', 'true')}">
            <div class="info-producto">
                <p>${producto.getNombre()}</p>
              

                <div>
                     <p>${producto.getPrecio()}</p>
                    <input type="number" name="qty">
                    <button id="${producto.getId()}" class="btnCartaDomicilio">Añadir</button>
                </div>
            </div>
            </c:forEach>
        </div>
        <div class="pastas">
            <h2>PASTAS</h2>

            <c:forEach var="producto" items="${op.productosPorTipo('pasta', 'true')}">
            <div class="info-producto">
                <p>${producto.getNombre()}</p>
                <div>
                    <p>${producto.getPrecio()}</p>
                    <input type="number" name="qty">
                    <button id="${producto.getId()}" class="btnCartaDomicilio">Añadir</button>
                </div>
            </div>
            </c:forEach>
        </div>
        <div class="bebidas">
            <h2>BEBIDAS</h2>

            <c:forEach var="producto" items="${op.productosPorTipo('bebida', 'true')}">
            <div class="info-producto">
                <p>${producto.getNombre()}</p>
     
                <div>
                     <p>${producto.getPrecio()}</p>
                    <input type="number" name="qty">
                    <button id="${producto.getId()}" class="btnCartaDomicilio">Añadir</button>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>
    
</div>