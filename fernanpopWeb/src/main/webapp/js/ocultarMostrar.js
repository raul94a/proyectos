const enVenta = document.querySelector('.en-venta')
const opiniones = document.querySelector('.opiniones');

enVenta.addEventListener("click", function(){
    //se puede hacer de otra forma mas elegante que llamando a childNodes, pero esto lo hice cuando no sabia apenas JS
    enVenta.childNodes[3].classList.add("active-option")
    opiniones.childNodes[3].classList.remove("active-option")

    const contOpiniones = document.querySelector('.contenedor-opiniones');
    contOpiniones.style.display="none";
    const contenedorVenta = document.querySelector('.contenedor-productos-en-venta');
    contenedorVenta.style.display="block";
})
enVenta.addEventListener("focus", function(){
    
})

opiniones.addEventListener("click", function() {
    opiniones.childNodes[3].classList.add("active-option")
    enVenta.childNodes[3].classList.remove("active-option")
    const contEnVenta = document.querySelector('.contenedor-productos-en-venta');
    contEnVenta.style.display="none";
    const contenedorOpiniones = document.querySelector('.contenedor-opiniones');
    contenedorOpiniones.style.display="";

    if(window.innerWidth >= 800){
        contenedorOpiniones.classList.add('grid-navegador-pc')
    }

})

