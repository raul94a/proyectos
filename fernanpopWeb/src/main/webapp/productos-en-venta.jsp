<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="jo" class="controlador.JsltOperations"/>
<!DOCTYPE html>
<html lang="en">

<head>
      
    <link rel="stylesheet" href="css/modales.css">
    <jsp:include page="includes/head.jsp"/> 
    <script src="js/formularios.js"defer></script>

</head>

<body>
  <c:if test="${sessionScope.usuario eq null}">
        <c:redirect url="login.jsp"/>
    </c:if>s
    <div class="contenedor-principal">
        <header class="barra-superior-en-venta flex-row outline-blue">
            <div class="contenedor-volver">
                <a href="perfil.jsp" class=""><i class="fas fa-arrow-left"></i></a>
            </div>
            <h2 class="margin-h2-en-venta">En venta</h2>

        </header>
          <c:if test="${ empty jo.productosEnVenta(sessionScope.usuario)}">
                <h1 class="centrar">No tienes productos a la venta</h1>
            </c:if>
        <!--Aquí empieza el contenedor de los productos que se cargaran por defecto ordenados de más recientes a más antiguos-->
        <c:if test="${sessionScope.mensaje != null}">   
            <h2 class="centrar" style="margin-top:10%">${sessionScope.mensaje}</h2>
            <c:set var="mensaje" scope="session" value="${null}"/>
        </c:if>
        <div class="venta">
            <c:forEach var="prod" items="${jo.productosEnVenta(sessionScope.usuario)}">
            <div class="control-producto">
                <a href="" style="pointer:default">
                    <div class="card-producto">
                        <img src="img/${prod.imagen}" alt="fotodetalle" class="card-foto">
                        <p class="interesados">Numero de interesados: ${jo.contarInteresadosEnProducto(prod.id)}</p>
                        <p class="precio">${prod.precio} €</p>
                        <p class="nombre">${prod.nombre}</p>
                        <p class="estado">${prod.estado}</p>
                        <p class="descripcion">${prod.descripcion}</p>

                    </div>
                </a>
                <div class="contenedor-botones" id="${prod.getId()}">
                    <button class="btn3 edicion" data-open="modal-editar-producto">Editar</button>
                    <button class="btn4 delecion" data-open="modal-eliminar-producto">Eliminar</button>
                </div>
                
            </div>
               
            </c:forEach>
         
        </div>

    </div>
     <nav class="navegacion">
        <jsp:include page="includes/navegacion.jsp"/>
    </nav>


    <!--MODAL EDITAR
            
            -->

    <div class="modal-editar" id="modal-editar-producto">
        <div class="control-modal">
            <button class="btn1red" data-close>X</button>
            <form action="${pageContext.request.contextPath}/ServletGestionProducto" method="POST" class="form-subir-producto">
              
                <label for="nombre">Nombre</label>
                <input type="text" name="nombre" id="nombre" placeholder="Nombre del producto">
                <label for="precio">Precio</label>
                <input type="number" id="precio" name="euros" min="0" step="0.01" placeholder="€">
                <label for="estado">Estado</label>
                <input type="hidden" name="editar" value="true">
                <input type="hidden" name="producto" value="" id="idprod">
                <input type="text" name="estado" id="estado" placeholder="Estado del producto">
                
                <label for="descripcion">Descripcion</label>
                <textarea name="descripcion" id="descripcion" cols="30" rows="10"></textarea>
                <input type="submit" value="Editar producto" class="btn2" id="enviar"data-close>

            </form>
        </div>
    </div>

    <!--MODAL ELIMINA
            
    -->
    <div class="modal2" id="modal-eliminar-producto">
        <div class="control-modal2">
            <h2>¿De verdad quieres borrar este producto?</h2>
            <div class="control-botones-modal">
                <button class="btn1" id="aceptar-borrado">Sí</button>
                <button class="btn1red" data-close>No</button>
                <!--se utilizará pa saber qué hay que borrar-->
                <form action="${pageContext.request.contextPath}/ServletGestionProducto" method="post" style="display:none">
                    <input type="hidden" value="" name="producto" id="idprodborrar">
                    <input type="submit" value="Borrar" id="btnBorradoProducto">
                </form>
            </div>
        </div>
    </div>



    <script src="js/barra.js"></script>
    <script src="js/modals.js"></script>
    <script src="js/background.js"></script>
    <script>
         function limpiarPrecio(precio){
               let str = '';
               for(let i = 0; i < precio.length ; i++){
                   if(precio[i] === ' '){
                       break;
                   }
                 
                   str += precio[i];
               }
               return str;
           }
        
           const venta = document.querySelector('.venta');
           //console.log(venta)
           
           venta.addEventListener('click', function(e){
               //seleccionamos el ID del producto
               e.preventDefault();
               const id = e.target.parentElement.id;
               let tipoDeBoton = "";
               //Determinamos el tipo de boton que se ha pulsado
               if(e.target.classList.contains('delecion')){
                   tipoDeBoton = 'borrar';
             
               } else if (e.target.classList.contains('edicion')){
                   tipoDeBoton = 'editar';
               } else{
                   tipoDeBoton = '';
               }
                
               //GESTIONAMOS LA ACCION DEL LISTENER
               if(tipoDeBoton === ''){
                   return ;
               } 
               else if (tipoDeBoton === 'editar'){
                   
                    //seleccionamos los elementos de el card del producto
                    const elementoPrecio = e.target.parentElement.previousElementSibling.querySelector('a div .precio');
                    const elementoNombre = elementoPrecio.nextElementSibling;
                    const elementoEstado = elementoNombre.nextElementSibling;
                    const elementoDescripcion = elementoEstado.nextElementSibling;
                    
                    // y seleccionamos sus valores
                    const precio = limpiarPrecio(elementoPrecio.textContent);
                    const nombre = elementoNombre.textContent;
                    const estado = elementoEstado.textContent;
                    const descripcion = elementoDescripcion.textContent;
                    
                    //los añadimos al formulario del modal
                    document.getElementById('idprod').value = id;
                    document.getElementById('nombre').value = nombre;
                    document.getElementById('precio').value = precio;
                    document.getElementById('descripcion').value = descripcion;
                    document.getElementById('estado').value = estado;
                    console.log(precio, nombre, estado, descripcion);
                   
               } else if (tipoDeBoton === 'borrar'){
                   
                   //insertamos el id del producto
                    document.getElementById('idprodborrar').value = id;
                    //conectamos el boton de aceptar
                    document.getElementById('aceptar-borrado').addEventListener('click', () => {
                        document.getElementById('btnBorradoProducto').click();
                    });
            
               } else{
                   return;
               }
           });
          
        
    </script>
</body>

</html>