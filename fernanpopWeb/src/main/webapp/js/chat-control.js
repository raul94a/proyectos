function generarBox(longitud){
    let width = 0;
    if(window.innerWidth > 800){
        width = 25 + longitud;
        if (width > 80){
            width = 80;
        }
    }else{
        width = 40 + longitud * 1.5;
        if (width > 80){
            width = 80;
        }
    }
   
    return width;
}

const mensajes = document.querySelectorAll('.control-mensaje');

for(const msj of mensajes){
    const width = generarBox(msj.children[0].children[0].outerText.length);
    msj.style.width = width + "%";
}

//esconder el overflow del body
const body = document.querySelector('body');
if(window.innerWidth > 800){
    body.style.overflow="hidden";
}
let count = 0;
const anchura = window.innerWidth;
const altura = window.innerHeight;
if(anchura >= 800){
    scrollHaciaAbajoAutomatico();
}else{
    window.scrollTo(0,altura);
   
}

function scrollHaciaAbajoAutomatico() {
    const contenedorConversacion = document.querySelector('.contenedor-conversacion');
    contenedorConversacion.scrollTop += 100;
    count++;
    if(count < 10){
        scrollHaciaAbajoAutomatico();
    }
  
}


