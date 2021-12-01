<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="factura">
    <h2>Restaurante Baviera</h2>
    <h3>¡Pedido realizado correctamente!</h3>
    
    <c:set var="factura" scope="page" value="${op.factura(sessionScope.factura)}"/>
    <table class="tabla">
        <tr>
            <th colspan = "3">Factura ${factura.id}</th>
        </tr>
        <tr>
            <th colspan = "3">${factura.direccion}</th>
        </tr>
        <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Precio</th>
        </tr>
    <c:forEach var="p" items="${factura.productos}">    
        <tr>
            <td>${p.getNombre()}</td>
            <td>${p.getCantidad()}</td>
            <td>${op.calcularPrecio(p.getCantidad(), p.getPrecio())} €</td>
        </tr>  
    </c:forEach>
        <tr>
            <th colspan="2">Total</th>
            <td>${factura.precio} €</td>
        </tr>
    
    <div class="btnFactura">
        <a href="${pageContext.request.contextPath}/Servlet?a=back" class="btnCartaDomicilio" >Volver</a>
        <a href="file/facturas/${sessionScope.pedido}.pdf" download="factura.pdf" class="btnCartaDomicilio" >Descargar factura</a>
    </div>
    </table>
    
    
</div>
    
