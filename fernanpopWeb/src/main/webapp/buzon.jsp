<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="jo" class="controlador.JsltOperations"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <link rel="stylesheet" href="css/modales.css">
    <jsp:include page="includes/head.jsp"/>  
    
</head>

<body>
     <c:if test="${sessionScope.usuario eq null}">
        <c:redirect url="login.jsp"/>
    </c:if>
    <div class="contenedor-principal">
        <header class="barra-superior-buzon flex-row outline-blue">
            <div class="contenedor-volver">
                <a href="index.jsp" class=""><i class="fas fa-arrow-left"></i></a>
            </div>
            <h2 class="margin-h2-buzon">Conversaciones</h2>

        </header>

        <!--MODAL BORRAR-->
        
        <div class="modal2" id="modal-borrar-conver">
            <div class="control-modal2">
                <h2>¿De verdad quieres borrar esta conversación?</h2>
                <div class="control-botones-modal">
                    <a href="${pageContext.request.contextPath}/ServletBuzon?id="><button class="btn1">Sí</button></a>
                    <button class="btn1red" data-close>No</button>
                    <!--se utilizará pa saber qué hay que borrar-->
                    <input type="hidden" value="">
                </div>
            </div>
        </div>
        
        <!--FIN MODAL-->
        <div class="frm-busqueda">
            <form action="${pageContext.request.contextPath}/BuscarUsuarios" method="post" class="busqueda">
                <input type="search" name="buscador" placeholder="Buscar usuarios">
                <input type="hidden" name="usuario" value="${sessionScope.usuario}">
                <input type="submit" name="btnBuscar" value="Buscar" class="btn2">
            </form>
        </div>
            <c:if test="${buscarUsuarios eq null or !buscarUsuarios}"> 
                
        <div class="contenedor-mensajes">
            <c:forEach var="conver" items="${jo.getConversacionesUsuario(sessionScope.usuario)}">
            <div class="control-conversaciones" id="uno">
                <a href="conversacion${conver.producto eq 0 ? 'Privada':''}.jsp?c=${conver.comprador}&v=${conver.vendedor}&p=${conver.producto}">
                    <div class="buzon-conversacion">
                        <div class="datos-usuario-conversacion">
                            <!--CON UNA ETIQUETA IF PARA DIFERENCIAR VENDEDOR DE COMPRADOR-->
                            <c:if test="${jo.isUsuarioComprador(conver.id, conver.producto)}">
                                <p class="usuario">${jo.datosUsuario(conver.vendedor).nombre}</p>
                            </c:if>
                           <c:if test="${jo.isUsuarioComprador(conver.id, conver.producto) != true}">
                                <p class="usuario">${jo.datosUsuario(conver.comprador).nombre}</p>
                            </c:if>
                            <c:if test="${jo.getProducto(conver.producto).id != 0}">
                                 <p class="producto">${jo.getProducto(conver.producto).nombre}</p>
                                 <p class="fecha">${jo.getProducto(conver.producto).fecha}</p>
                            </c:if>
                           
                            <c:if test="${jo.hayMensajesSinLeer(conver.id, sessionScope.usuario)}">
                            <p class="aviso" style="color:red">Tienes mensajes sin leer</p>
                            </c:if>
                        </div>
                        <c:if test="${jo.getProducto(conver.producto).id != 0}">
                                 <img src="img/${jo.getProducto(conver.producto).imagen}" alt="foto prod" class="foto-producto-conversacion">
                            </c:if>
                       
                    </div>
                </a>
                <button class="btn4" data-open="modal-borrar-conver" id="${conver.id}">Borrar</button>
            </div>
            </c:forEach>


          

        </div>
                
        </c:if>   
                
        <c:if test="${buscarUsuarios eq true}">
            <c:forEach var="u" items="${sessionScope.usuarios}"> 
                <section style="width:90%;margin: 0 auto;display:flex; justify-content: space-between;margin-bottom:1%;margin-top:2%;">
                    <p style="padding:0.5rem">${u.email} </p>
                    <a href="${pageContext.request.contextPath}/ConversacionPrivada?user=${u.id}&own=${sessionScope.usuario}" class="btn3" style="text-align:center">Enviar mensaje</a>
                </section>
            </c:forEach>
        </c:if>

        <!--AQUI EMPIEZA EL CONTENEDOR DE LAS BARRAS DE MENÚ-->
       

    </div>
    <nav class="navegacion">
         <jsp:include page="includes/navegacion.jsp"/>
    </nav>
    <script src="js/barra.js"></script>
    <script src="js/modals.js"></script>
    <script src="js/select-bzn-del.js"></script>
     <script src="js/background.js"></script>
    

</body>

</html>