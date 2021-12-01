const formatearPrecio = (precio) =>{
    let parse = precio;
    let longitudDecimal = precio.split('.')[1].length;
  
    if(longitudDecimal === 1){
        parse = precio + '0';
    }
    return parse;
}

const formatearTodosLosPrecios = () => {
    const precios = document.querySelectorAll('.info-producto div p');
    precios.forEach(el => {
        el.textContent = formatearPrecio(el.textContent) + ' € ';
    })
}

formatearTodosLosPrecios();

const generarTabla = array => {
    let tabla = document.querySelector('.resumen');
    console.log(tabla);
    let trEncabezado = document.createElement('tr');
    let th2 = document.createElement('th');
    let th = document.createElement('th');
    th.textContent = 'Producto';
    trEncabezado.append(th);
    th2.textContent = 'Cantidad';
    trEncabezado.append(th2);
    tabla.append(trEncabezado);
    for(const el of array){
        let tr = document.createElement('tr');
        let td1 = document.createElement('td');
        td1.textContent = el.nombre;
        let td2 = document.createElement('td');
        td2.textContent = el.cantidad;
        tr.append(td1);
        tr.append(td2);
        tabla.append(tr);
    } 
};


const pedido = [];
let json = "";

const productosAdd = document.getElementById('productos-add');
productosAdd.addEventListener('click', function(e){
   const target = e.target;
   if(target.classList.contains('btnEnviarPedidoMesa')){
        document.querySelector(".resumen").innerHTML = "";
        let producto = {};
        let identificador = target.id;
        let cantidad = target.previousElementSibling.value;
         if(cantidad === ""){
           cantidad = '0';
       }
       producto.identificador = identificador;
       producto.nombre = target.parentElement.previousElementSibling.textContent;
       producto.cantidad = cantidad;
       pedido.push(producto);
       json = JSON.stringify(pedido);
       target.previousElementSibling.value = '';
      /* for(const p of pedido){
           document.querySelector(".resumen table").innerHTML += "<tr>" + "<td>" +p.nombre + "  x " + p.cantidad +"</tr>";
       }*/
      generarTabla(pedido);
   }
});

 
 
 document.getElementById('btnPedidoMesa').addEventListener('click', function(e){
     document.querySelector(".form-pedidos").value = json;
   
 });
