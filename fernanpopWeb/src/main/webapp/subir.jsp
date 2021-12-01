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
        

</head>

<body>
     <c:if test="${sessionScope.usuario eq null}">
        <c:redirect url="login.jsp"/>
    </c:if>
    <div class="contenedor-principal subir">
        <header class="barra-superior-subir outline-blue">
            <div class="contenedor-volver">
                <a href="index.jsp" class=""><i class="fas fa-arrow-left"></i></a>
                <h2 class="centrar">Subir producto</h2>

            </div>

        </header>
        <div class="contenedor-subir-producto">
            <form action="${pageContext.request.contextPath}/ServletSubirProducto" enctype="multipart/form-data" method="POST" class="form-subir-producto">
                <label for="foto">Foto</label>
                <input type="file" name="foto" id="foto">
                <label for="nombre">Nombre</label>
                <input type="text" name="nombre" id="nombre" placeholder="Nombre del producto">
                <label for="precio">Precio</label>
                <input type="number" id="precio" name="euros" min="0"step="0.01" placeholder="€">
                <label for="estado">Estado</label>
                <input type="text" name="estado" id="estado" placeholder="Estado del producto">
                <!--<label for="categoria">Categoria</label>
                <input type="text" name="categoria" id="categoria" placeholder="categoria del producto">-->
                <label for="descripcion">Descripcion</label>
                <textarea name="descripcion" id="descripcion" cols="30" rows="10"></textarea>
                <input type="hidden" name="vendedor" value="${sessionScope.usuario}">
                <input type="submit" value="Subir producto" class="btn2" id="enviar">
            </form>
        </div>
       
    

    </div>
    <nav class="navegacion">
       <jsp:include page="includes/navegacion.jsp"/>
    </nav>
    <script src="js/barra.js"></script>
    <script src="js/background.js"></script>
    <script src="js/formularios.js" defer></script>


</body>

</html>