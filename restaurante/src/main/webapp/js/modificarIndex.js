const modificarPedidos= () => {
    
    //extraemos el textContent del span
    const pedidosPendientes = document.getElementById('pedidos-pendientes').textContent;
    if(parseInt(pedidosPendientes) > 0){
        document.getElementById('pedidos-domicilio').classList.add('botonRojo')
    }else{
        document.getElementById('pedidos-domicilio').classList.remove('botonRojo')

    }
    
    
}

modificarPedidos();