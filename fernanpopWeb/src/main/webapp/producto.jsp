<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>s
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<jsp:useBean id="jo" class="controlador.JsltOperations" />
<%
   // GestionApp controlador = new GestionApp();
    //int idProducto = Integer.parseInt(request.getParameter("prod"));
   // producto = controlador.buscarProductoPorId(idProducto);
  
%>
<!DOCTYPE html>
<html lang="en">

<head>
       <jsp:include page="includes/head.jsp"/>  

</head>

<body>


    <div class="contenedor-principal-producto">
        <header class="barra-superior">
            <div class="contenedor-volver">
                <a href="index.jsp" class="esquina-sup-izquierda"><i class="fas fa-arrow-left blanco"></i></a>
                <!--AÑADIR WISHLIST icon-->
            </div>

        </header>

        <!--Aquí empieza el contenedor de los productos que se cargaran por defecto ordenados de más recientes a más antiguos-->
        <div class="contenedor-detalle-producto">
            <img src="img/${producto.imagen}" alt="fotodetalle" class="foto-detalle-producto">
            <div class="control-contenedor-detalle-productos">
                <p class="precio"> ${producto.precio} €</p>
                <p class="nombre">Nombre: ${producto.nombre}</p>
                <p class="estado">Estado: ${producto.estado}</p>
                <p class="descripcion"> ${producto.descripcion}
                </p>
                <div class="contenedor-datos-vendedor">
                    <img src="img/${producto.imagen}" alt="imagen-del-producto">
                    <p>${jo.datosUsuario(producto.vendedor).nombre}</p>
                    <p class="estrellas stars-producto">
                        ${jo.getEstrellas(jo.valoracionMedia(producto.vendedor))}
                    </p>
                </div>
                    <c:if test="${sessionScope.usuario != null}">
                        <a href="${pageContext.request.contextPath}/ServletConversacion?c=${sessionScope.usuario}&v=${producto.vendedor}&p=${producto.id}" class="chat">chat</a>

                    </c:if>

            </div>
           
        </div>
      

    </div>
  
    

</body>

</html>