<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="mantel">
    <div class="productos">
        <div class="carnes" id="carnes">
          <h2 class="titulo">Carnes</h2>
        <c:forEach var="producto" items="${op.productosPorTipo('carne','true')}" varStatus="i">
            
            <c:if test="${(op.sumarUno(i.index)) % 2 eq 0}">
                 <div class="producto par">
               <p>${op.sumarUno(i.index)}. ${producto.getNombre()}</p>
               <p>${producto.getPrecio()}</p>
            </c:if>
            <c:if test="${(op.sumarUno(i.index)) % 2 != 0}">
                <div class="producto">
               <p class="impar">${op.sumarUno(i.index)}. ${producto.getNombre()}</p>
               <p class="impar">${producto.getPrecio()}</p>
            </c:if>
            </div>
        </c:forEach>
             </div>
            <div class="pescados" id="pescados">
                <h2 class="titulo">Pescados</h2>
            <c:forEach var="producto" items="${op.productosPorTipo('pescado','true')}" varStatus="i">
                <c:if test="${(op.sumarUno(i.index)) % 2 eq 0}">
                    <div class="producto par">

                   <p class="par">${op.sumarUno(i.index)}. ${producto.getNombre()}</p>
                   <p class="par">${producto.getPrecio()}</p>
                </c:if>
                <c:if test="${(op.sumarUno(i.index)) % 2 != 0}">
                    <div class="producto">

                   <p class="impar">${op.sumarUno(i.index)}. ${producto.getNombre()}</p>
                   <p class="impar">${producto.getPrecio()}</p>
                </c:if>
                </div>
            </c:forEach>
            </div>
              <div class="pastas" id="pastas">
                <h2 class="titulo">Pastas</h2>
            <c:forEach var="producto" items="${op.productosPorTipo('pasta','true')}" varStatus="i">
                 <c:if test="${(op.sumarUno(i.index)) % 2 eq 0}">
                    <div class="producto par">

                    <p class="par">${op.sumarUno(i.index)}. ${producto.getNombre()}</p>
                    <p class="par">${producto.getPrecio()}</p>
                 </c:if>
                 <c:if test="${(op.sumarUno(i.index)) % 2 != 0}">
                    <div class="producto">
                    <p class="impar">${op.sumarUno(i.index)}. ${producto.getNombre()}</p>
                    <p class="impar">${producto.getPrecio()}</p>
                 </c:if>
                 </div>
            </c:forEach>
            </div>
              <div class="bebidas" id="bebidas">
                <h2 class="titulo">Bebidas</h2>
            <c:forEach var="producto" items="${op.productosPorTipo('bebida','true')}" varStatus="i">
                
                <c:if test="${(op.sumarUno(i.index)) % 2 eq 0}">
                   <div class="producto par">
                   <p >${op.sumarUno(i.index)}. ${producto.getNombre()}</p>
                   <p>${producto.getPrecio()}</p>
                </c:if>
                <c:if test="${(op.sumarUno(i.index)) % 2 != 0}">
                     <div class="producto">
                   <p class="impar">${op.sumarUno(i.index)}. ${producto.getNombre()}</p>
                   <p class="impar">${producto.getPrecio()}</p>
                </c:if>
                </div>
            </c:forEach>
            </div>

    </div>
    <div class="contenedor-botones">
        <button class="btnCarta" id="controlador-izq"><</button>
        <button class="btnCarta" id="controlador-dcha">></button>

    </div>
   
    
</div>
<script src="js/carta.js"></script>
<script>
    const formatearPrecio = (precio) =>{
    let parse = precio;
    let longitudDecimal = precio.split('.')[1].length;
  
    if(longitudDecimal === 1){
        parse = precio + '0';
    }
    return parse;
}

const formatearTodosLosPrecios = () => {
    const precios = document.querySelectorAll('.productos div div p:last-of-type');
    precios.forEach(el => {
        el.textContent = formatearPrecio(el.textContent) + ' € ';
    })
}

formatearTodosLosPrecios();

</script>
          