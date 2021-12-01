<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="includes/head.jsp"/>  

</head>
<body>

    <div class="contenedor-principal">
       <header class="barra-superior">
           <div class="contenedor-volver">
                <a href="productos-en-venta.jsp" class=""><i class="fas fa-arrow-left"></i></a>
                 <h2 class="centrar">Detalle del producto</h2>
           </div>
          
       </header>
        <!--Aquí empieza el contenedor de los productos que se cargaran por defecto ordenados de más recientes a más antiguos-->
        <div class="contenedor-detalle-producto">
            <img src="img/car.jpg" alt="fotodetalle" class="foto-detalle-producto">
            <p class="precio">12500 €</p>
            <p class="nombre">Audi</p>
            <p class="interesados">Se han interesado n personas por este producto</p>
            <div class="lista-interesados">
                <div class="interesado">
                    <p class="nombre">nombre</p>
                    <p class="cantidad-ofrecida">Cantidad ofrecida</p>
                    <button>Aceptar</button>
                    <button>Rechazar</button>
                </div>
                <div class="interesado">
                    <p class="nombre">nombre</p>
                    <p class="cantidad-ofrecida">Cantidad ofrecida</p>
                    <button>Aceptar</button>
                    <button>Rechazar</button>
                </div>
                <div class="interesado">
                    <p class="nombre">nombre</p>
                    <p class="cantidad-ofrecida">Cantidad ofrecida</p>
                    <button>Aceptar</button>
                    <button>Rechazar</button>
                </div>
              
            </div>
        </div>
        

    </div>
    <nav class="navegacion">
            <jsp:include page="includes/navegacion.jsp"/>
        </nav>
    <script src="js/barra.js"></script>


</body>
</html>