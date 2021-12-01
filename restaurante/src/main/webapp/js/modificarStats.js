const formatearPrecio = (precio) =>{
    let parse = precio;
    let longitudDecimal = precio.split('.')[1].length;
  
    if(longitudDecimal === 1){
        parse = precio + '0';
    }
    return parse;
};

const formatearTodosLosPrecios = () => {
    const precios = document.querySelectorAll('.st-precio');
    let contador = 0;
    precios.forEach(el => {
        contador++;
        el.textContent = formatearPrecio(el.textContent) + ' € ';
        if(contador === 2){
            el.textContent += ' / factura';
        }
    });
};

formatearTodosLosPrecios();