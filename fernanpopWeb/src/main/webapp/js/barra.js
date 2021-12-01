/*window.scroll(0, 0);

function website (){
    var pos = 0;
    var punto = 0;
    var url = window.location.pathname;
    var urlLength = url.length;
    for(var i = 0; i < urlLength;i++){
        if(url[i] == '/'){
            pos = i;
        }
        if(url[i] == '.'){
            punto = i;
            pos++;
        }
    }
    return url.substring(pos, punto)
}

const urls = ["/src/vista/index.html", "/src/vista/subir.html",
 "/src/vista/buzon.html", "/src/vista/perfil.html"];

const url = window.location.pathname;
if(urls.includes(url)){
    var dir = '.' +  website() + '-nav';
    var els = document.querySelectorAll(dir)

    for(const el of els){
        el.style.color='#12195A';
        el.style.fontSize='1.25rem';  
        el.style.fontWeight="bold";
     }
    
}*/
const width = window.innerWidth;
if(window.innerWidth >= 800){
    let clicks = 0;
    const barraNavegacion = document.querySelector('.navegacion');
    barraNavegacion.style.width="3%";
    const contenedorPrincipal = document.querySelector('.contenedor-principal');
    contenedorPrincipal.style.marginLeft="7%";
    const els = document.querySelectorAll('.barra li');
    for(const el of els){
        el.style.display="none";
    }
    const menuBarLi = document.querySelector('.li-menu');
    menuBarLi.style.display="block";
    const menuBar = document.querySelector('.fa-bars');
    menuBar.style.display="block"

    menuBarLi.addEventListener('click', function(){
        if(clicks === 0){
            for(const el of els){
                el.style.display="block";
            }
            barraNavegacion.style.width="10%";
            barraNavegacion.style.transition="0.35s ease"
            contenedorPrincipal.style.marginLeft="10%";
            contenedorPrincipal.style.transition="0.35s ease";
            clicks++;
        }else{
            for(const el of els){
                el.style.display="none";
                const menuBarLi = document.querySelector('.li-menu');
                menuBarLi.style.display="block";
            }
            barraNavegacion.style.width="3%";
            contenedorPrincipal.style.marginLeft="7%";
            clicks = 0;
        }
        
    })

}else{
    let barra = document.querySelector('.barra');
    barra.style.paddingTop = '1rem';
}



