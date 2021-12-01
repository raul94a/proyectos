<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="jo" class="controlador.JsltOperations"/>
<!DOCTYPE html>
<html lang="en">

<head>
       <link rel="stylesheet" href="css/modales.css">
        <jsp:include page="includes/head.jsp"/>  
 
  

</head>
 <c:set var="user" value="${jo.datosUsuario(sessionScope.usuario)}"/>
<body>
     <c:if test="${sessionScope.usuario eq null}">
        <c:redirect url="login.jsp"/>
    </c:if>
    <div class="contenedor-principal">
        <header class="perfil outline-blue">
            <div class="control-perfil">
                <img src="img/${user.imgPerfil}" alt="foto-perfil" class="foto-perfil control-foto-perfil" id="img-perfil">
                <div class="perfil-datos">

                    <p>${user.nombre}</p>
                    <p class="estrellas stars-perfil" id="${jo.valoracionMedia(sessionScope.usuario)}">
                        ${jo.getEstrellas(jo.valoracionMedia(sessionScope.usuario))}
                        <span>(${user.estadisticas.valoracionesRecibidas})</span></p>
                </div>

                <a href="misdatos.jsp"><i class="fas fa-greater-than"></i></a>
            </div>
        </header>
        <main class="contenedor-perfil">
            <div class="catalogo">
                <div class="control-catalogo">
                    <h1>Catalogo</h1>
                    <a href="productos-en-venta.jsp"><i class="fas fa-box-open"></i>  Productos en venta</a>
                    <a href="ventas.jsp"><i class="fas fa-people-carry"></i>  Productos vendidos</a>
                    <a href="compras.jsp"><i class="fas fa-shopping-cart"></i>  Productos comprados</a>
                </div>
            </div>
            <div class="tratos">
                <div class="control-tratos">
                    <h1>Valoraciones</h1>
                    <a href="valoraciones.jsp"> <i class="far fa-gem"></i> Ver las valoraciones de otros usuarios</a>
                    <a href="pendientes-de-valorar.jsp" class="${jo.existeTratoSinValorar(sessionScope.usuario) eq true ? 'rojo':''}"><i class="far fa-star"></i>  Tratos pendientes de valorar</a>
                </div>
            </div>
            <div class="cuenta">
                <div class="control-cuenta">
                    <h1>Cuenta</h1>
                    <a href="cuenta.jsp"><i class="fas fa-cogs"></i>  Configurar mis datos</a>
                    <a href="${pageContext.request.contextPath}/CerrarSesion"><i class="fas fa-sign-out-alt"></i>  Cerrar sesion</a>
                </div>
            </div>
        </main>
                
                
        <div class="modal-editar" id="modal-img">   
            <button class="btnCerrar" id="btn-cerrar-cambio-img">X</button>
            <h1 style="text-align:center">Cambiar imagen de perfil</h1>
            <form action="${pageContext.request.contextPath}/ServletImg" method="POST" enctype="multipart/form-data" class="form-subir-producto" >
                <label>Selecciona la imagen que desees</label>
                <input type="file" id="fotoPerfil" name="foto">
                <input type="hidden" name="usuario" value="${sessionScope.usuario}">
                <input type="submit" name="btnEnviarImg" id="enviar"value="Enviar">
            </form>
        </div>

    </div>
    <nav class="navegacion">
       <jsp:include page="includes/navegacion.jsp"/>
    </nav>
    <script src="js/barra.js"></script>
     <script src="js/background.js"></script>
    <script>
        //CONTROLAR EL CAMBIO DE IMAGEN DE PERFIL ABRIR Y CERRAR MODAL
        const imgPerfil = document.getElementById('img-perfil');
        imgPerfil.addEventListener('click', function(e){
           const modalImg = document.getElementById('modal-img');
           modalImg.classList.add('is-visible');
        });
        document.getElementById('btn-cerrar-cambio-img').addEventListener('click', () => {
           const modalImg = document.getElementById('modal-img');
           modalImg.classList.remove('is-visible');
        });
        
    </script>
    <script src="js/formularios.js"></script>
</body>

</html>