<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="add.jsp"/>

<div class="contenedor-form-resumen">
   <aside>
        <h2>Resumen</h2>
        <!--Imprimir lo que se lleva-->
        <table class="resumen tabla"></table>
    </aside>
    <form action="${pageContext.request.contextPath}/ServletPedidos" method="POST" class="form-app">
        <h2>Introduce tus datos</h2>
        <input class="form-pedidos" type="hidden" name="json" value="">
        <label>Nombre</label>
        <input type="text" name="nombre" required>
        <label>Dirección</label>
        <input type="text" name="direccion" required>
        <label>Telefono</label>
        <input type="number" name="telefono" max="999999999"required>
        <input type="submit" value="Enviar" class="btnCartaDomicilio" id="btnEnviarDomicilio">
    </form>
   
</div>
    <script src="js/pedidos.js"></script>