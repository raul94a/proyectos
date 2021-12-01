<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <header class="barra-superior-cuenta flex-row outline-blue">
            <div class="contenedor-volver ">
                <a href="perfil.jsp" class=""><i class="fas fa-arrow-left"></i></a>
            </div>
            <h2 class="centrar-w85">Mi cuenta</h2>
        </header>
          <c:if test="${sessionScope.mensaje != null}">
         <h2>${sessionScope.mensaje}</h2>
         <c:set var="mensaje" scope="session" value="${null}"/>
        </c:if>   
        <div class="contenedor-cuenta">
            <!--cada boton será controlado por un evento de click. Aparecerá un pop up con la nueva pantalla
            necesaria para cada función-->
            <button class="btn3-b" data-open="modal-editar-perfil">EDITAR PERFIL</button>
            <button class="btn3-b" data-open="modal-cambiar-pass">CAMBIAR CONTRASEÑA</button>
            <button class="btn3-b" data-open="modal-borrar-cuenta">BORRAR CUENTA</button>
            <button class="btn3-b" id="btn-tema-${cookie.tema.value eq 'oscuro' ? 'claro':'oscuro'}">CAMBIAR A TEMA ${cookie.tema.value eq 'oscuro' ? 'CLARO':'OSCURO'}</button>
        </div>

    </div>

    <!--MODAL DE EDITAR PERFIL-->
    <div class="modal-editar" id="modal-editar-perfil">
        <div class="control-modal">
              <button class="btn1red" data-close>X</button>
            <form action="${pageContext.request.contextPath}/ServletGestionUsuario" method="POST" class="form-subir-producto">
                <label for="nombre">Nombre</label>
                <input type="text" name="nombre" id="nombre" maxlength="20">
                <label for="email">Email</label>
                <input type="text" name="email" id="email">
                <input type="hidden" name="usuario" value="${sessionScope.usuario}">
                <input type="hidden" name="tipo" value="editarPerfil"> 
                <input type="submit" value="Editar perfil" class="btn2" data-close>
            </form>
        </div>
    </div>
    <!--MODAL DE EDITAR PASS-->
    <div class="modal-editar" id="modal-cambiar-pass">
        <div class="control-modal">
              <button class="btn1red" data-close>X</button>
            <form action="${pageContext.request.contextPath}/ServletGestionUsuario" method="POST" class="form-subir-producto">
                <label for="pass">Contraseña antigua</label>
                <input type="password" name="pass" id="pass">
                <label for="passNueva">Nueva contraseña</label>
                <input type="password" name="passNueva" id="passNueva">
                <label for="passNuevaRep">Repite la nueva contraseña</label>
                <input type="password" name="passNuevaRep" id="passNuevaRep">
                <input type="hidden" name="tipo" value="editarPass">
                <input type="hidden" name="usuario" value="${sessionScope.usuario}">
                <input type="submit" value="Cambias contraseña" class="btn2" data-close>
            </form>
        </div>
    </div>
  <!--MODAL DE BORRAR CUENTA-->
  <div class="modal2" id="modal-borrar-cuenta">
    <div class="control-modal2">
        <h2>¿De verdad quieres borrar tu cuenta?</h2>
        <div class="control-botones-modal">
            <button class="btn1" id="btn-aceptar-borrar-usuario">Sí</button>
            <button class="btn1red" data-close>No</button>
            <!--se utilizará pa saber qué hay que borrar-->
            <form action="${pageContext.request.contextPath}/ServletGestionUsuario" method="POST" style="display:none">
                <input type="hidden" name="tipo" value="borrar">
                <input type="hidden" name="usuario" value="${sessionScope.usuario}">
                <input type="submit" id="btn-borrar-usuario">

            </form>
        </div>
    </div>
</div>
  
    <script src="js/modals.js"></script>
    <script>
        
        document.getElementById('btn-aceptar-borrar-usuario').addEventListener('click', () => {
           document.getElementById('btn-borrar-usuario').click(); 
        });
    
        const btnCambio = document.querySelector('.contenedor-cuenta button:last-of-type');
        btnCambio.addEventListener('click', function(e){
            
            //vamos a comprobar la id del boton
           let hojaEstilos = document.querySelector('link:last-of-type');
           const tipo = this.id === "btn-tema-oscuro" ? "oscuro" : "claro";
           const ruta = `css/tema-${tipo}.css`;
           hojaEstilos.setAttribute('href',ruta);
           //modificamos la cookie
           document.cookie = 'tema=' + tipo + ';expires=Fri, 31 Dec 9999 23:59:59 GMT';
           location.reload();
            
        }) ;
        
        
    </script>
</body>

</html>
