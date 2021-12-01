
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="factura">
    <h2>Restaurante Baviera</h2>
    <c:set var="factura" scope="page" value="${op.factura(param.f)}"/>
    <c:if test="${factura != null}">  
        <p>Factura numero: ${factura.id}</p>
        <p>Direccion: ${factura.direccion}</p>
        <p>Mesa : ${factura.mesa}</p>
        <c:forEach var="p" items="${factura.productos}">    
            <p>${p.getNombre()} x${p.getCantidad()} ${op.calcularPrecio(p.getCantidad(), p.getPrecio())} €</p>
        </c:forEach>
        <p>Total: ...................... ${factura.precio} €</p>
    </c:if>
    <c:if test="${factura eq null}">
        <h3>No se ha podido mostrar ninguna factura porque la mesa ha sido liberada antes de realizar algún pedido</h3>
    </c:if>   
    
    
    
</div>
    <a href=".">Volver</a>