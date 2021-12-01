
<%@page import="controlador.Operaciones"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:set var="st" value="${op.estadistica(param.st)}"/>

<div class="stats">

    <table class="tabla">
      <tr>
          <th colspan="2">Estadística general</th>   
      </tr>
      <tr>
          <td>Numero de facturas </td>
          <td>${st.getNumeroFacturas()}</td>
      </tr>
      <tr>
          <td>Ganancias totales (euros)</td>
          <td class="st-precio">${st.getGananciasTotales()}</td>
      </tr>
      
      <tr>
          <td>Media (euros / factura) </td>
          <td class="st-precio"> ${st.media()} </td>
      </tr>
    </table>


    <table class="tabla">
        <tr>
            <th colspan="2">Productos más vendidos</th>
        </tr>
        <tr>
            <th>Productos</th>
            <th>Unidades</th>
        </tr>


        <c:forEach var="prod" items="${st.getProductosMasVendidos()}">
            <tr>
                <td>${prod.getNombre()}</td>
                <td>${prod.getCantidad()}</td>
            </tr>
        </c:forEach>
    </table>    

    <div class="productos-mas-vendidos w75 cnt-chart" id='${st.productosMasVendidosToJSON()}'>
       <div class="plot" id="myChart"></div>
    </div>


    <table class="tabla">
        <tr>
            <th colspan="2">Productos que más dinero generan</th>
        </tr>
        <tr>
            <th>Producto</th>
            <th>Precio (€)</th>
        </tr>


        <c:forEach var="prod" items="${st.getProductosQueMasDineroHanGenerado()}">
        <tr>
            <td>${prod.getNombre()}</td>
            <td class="st-precio">${prod.getPrecio()}</td>
        </tr>
        </c:forEach>
    </table>    





    <div class="productos-mas-dinero productos-mas-vendidos w75 cnt-chart" id='${st.productosQueMasDineroHanGeneradoToJSON()}'>
         <div class="plot" id="myChart2"></div>
    </div>  
</div>

         <script src="js/modificarStats.js"></script>
<script type="text/javascript" src="https://cdn.zingchart.com/zingchart.min.js"></script>  
<script src="js/plotting.js"> </script>
    