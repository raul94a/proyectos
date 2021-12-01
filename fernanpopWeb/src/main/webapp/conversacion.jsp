<%@page session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:if test="${not jo.usuarioPerteneceAConversacion(param['c'],param['v'] ,param['p'] ,sessionScope.usuario)}">
        <c:redirect url="index.jsp" />
    </c:if>
    <header class="barra-superior-conversacion outline-blue">
        <div class="contenedor-volver-mensajes">
            <div class="contenedor-regreso-foto">
                <a href="buzon.jsp" class=""><i class="fas fa-arrow-left"></i></a>
            </div>
            <c:set var="producto" scope="page" value="${jo.getProducto(param['p'])}"/>
            <div class="datos-usuarios">
                <img src="img/${producto.getImagen()}" alt="foto-producto" class="foto-usuario-mensajes">
                <div class="control-flex-row-datos">
                    <p class="precio">${producto.getPrecio()} €</p>
                    <p class="producto">${producto.getNombre()}</p>
                </div>
                
            </div>
            <c:if test="${param['v'] eq sessionScope.usuario}">
                  <img src="img/${jo.datosUsuario(param['c']).getImgPerfil()}" alt="foto-usuario" class="foto-producto-mensajes">
            </c:if>
           <c:if test="${param['c'] eq sessionScope.usuario}">
                  <img src="img/${jo.datosUsuario(param['v']).getImgPerfil()}" alt="foto-usuario" class="foto-producto-mensajes">
            </c:if>  
          

        </div>
    </header>
    <div class="contenedor-principal cnt-ppal-conv">

        <div class="contenedor-boton">
            <!--ESTABLECER FUNCIONALIDAD-->
        <c:if test="${sessionScope.usuario == param['c']}">
            <c:if test="${!jo.existeTratoTemporalRegistrado(param['v'], param['c'], param['p']) and !jo.isProductoVendido(param['p'])}">
            <button class="btn3" style="margin-left:27%"data-open="modal-comprar-conver">Comprar</button>
            </c:if>
        </c:if>
        <c:if test="${sessionScope.usuario != param['c'] and jo.existeTratoTemporalRegistrado(param['v'], param['c'], param['p'])}">
            <button class="btn3" style="margin-left:27%" data-open="modal-vender-conver">Vender</button>
        </c:if>
        </div>
        <div class="contenedor-conversacion" id="conversacion">
            <c:forEach var="msj" items="${jo.getMensajes(jo.getIdConversacion(param['c'],param['v'] ,param['p']))}">
                ${jo.actualizarMsjVistos(msj.id, jo.toInt(sessionScope.usuario))}
            
            <div class="espacio-mensaje">
                <c:if test="${msj.emisor == jo.toInt(sessionScope.usuario)}">
                <div class="control-mensaje mensaje-propio derecha" data-msj="${msj.id}">
                </c:if>
                <c:if test="${msj.receptor == jo.toInt(sessionScope.usuario)}">
                <div class="control-mensaje mensaje-ajeno" data-msj="${msj.id}">
                </c:if>
                    <div class="contenedor-mensaje">
                        <c:if test="${msj.emisor == jo.toInt(sessionScope.usuario)}">
                            <p class="propio" data-msj="${msj.id}">${msj.borradoParaEmisor ? '<i>Has borrado el mensaje</i>':msj.contenido}</p>
                        </c:if>
                        <c:if test="${msj.receptor == jo.toInt(sessionScope.usuario)}">
                            <p class="ajeno" data-msj="${msj.id}">${msj.borradoParaReceptor ? '<i>Has borrado el mensaje</i>' : msj.contenido}</p>
                        </c:if>
                    </div>
                    <span class="hora">${msj.formatFecha()}</span>
                    <c:if test="${msj.emisor == jo.toInt(sessionScope.usuario) and !msj.visto}">
                    <i class="fas fa-check"></i>
                    </c:if>
                    <c:if test="${msj.emisor == jo.toInt(sessionScope.usuario) and msj.visto}">
                       
                    <i class="fas fa-check-double" style="color:${cookie.tema.value eq 'oscuro' ? '#64e8c5':'blue'}"></i>
                    </c:if>
                </div>
              
             
          


             </div>

            </c:forEach>

        
    </div>
    <form action="${pageContext.request.contextPath}/ServletMensajes" method="POST" class="enviar-mensajes">
        <input type="text" name="msj" placeholder="Escribe algo aquí..." name="contenido">
        <c:if test="${jo.toInt(sessionScope.usuario) == param['c']}">
            
        </c:if>
        <input type="hidden" name="emisor" value="${sessionScope.usuario}">
        <input type="hidden" name="receptor" value="${jo.toInt(sessionScope.usuario) == jo.toInt(param['c']) ? param['v'] : param['c']}">
        <input type="hidden" name="conversacion" value="${jo.getIdConversacion(param['c'], param['v'], param['p'])}">
        <input type="hidden" name="producto" value="${param['p']}">
        <input type="submit" name="btnMsj" value="Enviar" id="form-msj-enviar"class="btn1">
    </form>
    <!--MDAL-->
    <div class="modal2" id="modal-vender-conver">
        <div class="control-modal2">
            <c:if test="${jo.existeTratoTemporalRegistrado(param['v'], param['c'], param['p'])}">  
                <h2>El usuario te ha ofrecido ${jo.getPrecioTratoTemporal(param['v'], param['c'], param['p'])} euros</h2>
            </c:if>
            <h3 class="centrar">¿De verdad quieres vender este producto a este usuario?</h3>
            <div class="control-botones-modal">
                <a class="btn1" href="${pageContext.request.contextPath}/ServletVender?retrieve=true&prod=${param['p']}&c=${param['c']}&user=${sessionScope.usuario}">Sí</a>
                <a class="btn1red" href="${pageContext.request.contextPath}/ServletVender?retrieve=false&prod=${param['p']}&c=${param['c']}" data-close>No</a>
                <!--se utilizará pa saber qué hay que borrar-->
                <input type="hidden" value="">
            </div>
        </div>
    </div>
    <div class="modal2" id="modal-comprar-conver">
        <div class="control-modal2">
            <h2>Ofrece un precio por la compra del producto</h2>
            <form action="${pageContext.request.contextPath}/ServletComprar" method="get">
                <input type="number" name="precio"> €
                <input type="hidden" name="producto" value="${param['p']}">
                <input type="hidden" name="comprador" value="${param['c']}">
                <input type ="hidden" name="vendedor" value="${param['v']}">
                <input type="submit" name="btnComprar" class="btn2">
            </form>
            
            <div class="control-botones-modal">
                <a class="btn1">Sí</a>
                <button class="btn1red" data-close>No</button>
                <!--se utilizará pa saber qué hay que borrar-->
                <input type="hidden" value="">
            </div>
        </div>
    </div>
    <div class="modal2" id="modal-borrar-mensaje">
        <div class="control-modal2">
            <h2>¿De verdad quieres borrar este mensaje?</h2>
            <div class="control-botones-modal">
                <button class="btn1" id="btn-aceptar-borrar-mensaje">Sí</button>
                <button class="btn1red" data-close>No</button>
                <!--se utilizará pa saber qué hay que borrar-->
                <form action="${pageContext.request.contextPath}/BorrarMensaje" method="POST" style="display:none">
                    <input type="hidden" name="usuario" value="${sessionScope.usuario}">
                    <input type="hidden" name="mensaje" id="form-msj" value="">
                    <input type="hidden" name="producto" value="${param['p']}">
                    <input type="hidden" name="comprador" value="${param['c']}">
                    <input type ="hidden" name="vendedor" value="${param['v']}">
                    <input type="hidden" id="borrarReceptor" name="borrarReceptor" value="false">
                    <input type="submit" id="btn-borrar-mensaje">

                </form>
            </div>
        </div>
    </div>
  
    <script src="js/chat-control.js"></script>
    <script src="js/modals.js"></script>
    <script src="js/borrarMensaje.js"></script>
    <script src="js/formularios.js"></script>
</body>

</html>