
//vamos a recorrer el JSON de productos más vendidos
let productosMasVendidos = JSON.parse(document.querySelector(".productos-mas-vendidos").id);
//creamos nuestro array de cantidades
let cantidades = [];
//array de productos
let productos = [];
for(var i = 0; i < productosMasVendidos.length; i++) {
    var obj = productosMasVendidos[i];
    cantidades.push(obj.cantidad);
    productos.push(obj.nombre);
}

window.onload = function() {
  zingchart.render({
    id: "myChart",
    width: "100%",
    height: 400,
    data: {
      "type": "bar",
      "title": {
        "text": "Productos más vendidos"
      },
      "scale-x": {
        "labels": productos
      },
      "plot": {
        "line-width": 1
      },
      "series": [{
        "values": cantidades
      }]
    }
  });
};

//hacemos lo mismo con el JSON de productos más dinero generado

let productosMasDinero = JSON.parse(document.querySelector(".productos-mas-dinero").id);
console.log(productosMasDinero)
let precios = [];
//array de productos
let prods = [];
for(var i = 0; i < productosMasDinero.length; i++) {
    var obj = productosMasDinero[i];
   precios.push(obj.precio);
    prods.push(obj.nombre);
}
console.log(cantidades)

  zingchart.render({
    id: "myChart2",
    width: "100%",
    height: 400,
    data: {
      "type": "bar",
      "title": {
        "text": "Productos que más dinero generan"
      },
      "scale-x": {
        "labels": prods
      },
      "plot": {
        "line-width": 1
      },
      "series": [{
        "values": precios
      }]
    }
  });
  
  
document.querySelector(".productos-mas-vendidos").id = "";
document.querySelector(".productos-mas-dinero").id = "";