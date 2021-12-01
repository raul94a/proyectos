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
 <c:if test="${sessionScope.usuario eq null}">
        <c:redirect url="login.jsp"/>
    </c:if>
<body>
    <c:set var="buscarUsuarios" scope="session" value="${false}"/>
    <header class="barra-superior-conversacion outline-blue">
        <div class="contenedor-volver-mensajes">
            <div class="contenedor-regreso-foto">
                <a href="buzon.jsp" class=""><i class="fas fa-arrow-left"></i></a>
            </div>
           
            
            <c:if test="${param['v'] eq sessionScope.usuario}">
                  <img src="img/${jo.datosUsuario(param['c']).getImgPerfil()}" alt="foto-usuario" class="foto-producto-mensajes">
                  <p>${jo.datosUsuario(param['c']).getNombre()}</p>
            </c:if>
           <c:if test="${param['c'] eq sessionScope.usuario}">
                  <img src="img/${jo.datosUsuario(param['v']).getImgPerfil()}" alt="foto-usuario" class="foto-producto-mensajes">
                    <p>${jo.datosUsuario(param['v']).getNombre()}</p>
           </c:if>  
          

        </div>
    </header>
    <div class="contenedor-principal cnt-ppal-conv">

        <div class="contenedor-boton">
            <!--ESTABLECER FUNCIONALIDAD-->
        
        </div>
        <div class="contenedor-conversacion" id="conversacion">
            <c:forEach var="msj" items="${jo.getMensajes(jo.getIdConversacion(param['c'],param['v'], '0'))}">
                ${jo.actualizarMsjVistos(msj.id, jo.toInt(sessionScope.usuario))}
            
            <div class="espacio-mensaje">
                <c:if test="${msj.emisor == jo.toInt(sessionScope.usuario)}">
                <div class="control-mensaje mensaje-propio derecha" data-msj="${msj.id}">
                </c:if>
                <c:if test="${msj.receptor == jo.toInt(sessionScope.usuario)}">
                <div class="control-mensaje mensaje-ajeno" data-msj="${msj.id}">
                </c:if>
                    <div class="contenedor-mensaje">
                        <p>${msj.emisor == jo.toInt(sessionScope.usuario) and msj.borrado ? '<i>El mensaje ha sido borrado</i>':msj.contenido}</p>
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
    <form action="${pageContext.request.contextPath}/ServletMensajesPrivados" method="POST" class="enviar-mensajes">
        <input type="text" name="msj" placeholder="Escribe algo aquí..." name="contenido">
        <c:if test="${jo.toInt(sessionScope.usuario) == param['c']}">
            
        </c:if>
        <input type="hidden" name="emisor" value="${sessionScope.usuario}">
        <input type="hidden" name="receptor" value="${jo.toInt(sessionScope.usuario) == jo.toInt(param['c']) ? param['v'] : param['c']}">
        <input type="hidden" name="conversacion" value="${jo.getIdConversacion(param['c'], param['v'], '0')}">
        <input type="hidden" name="producto" value="0">
        <input type="hidden" name="vendedor" value="${param['v']}">
        <input type="submit" name="btnMsj" value="Enviar" class="btn1">
    </form>
    <!--MDAL-->
    
    
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
                    <input type="hidden" name="producto" value="0">
                    <input type="hidden" name="comprador" value="${param['c']}">
                    <input type ="hidden" name="vendedor" value="${param['v']}">
                    <input type="submit" id="btn-borrar-mensaje">

                </form>
            </div>
        </div>
    </div>
  
    <script src="js/chat-control.js"></script>
    <script src="js/modals.js"></script>
    <script src="js/borrarMensaje.js"></script>
</body>

</html>