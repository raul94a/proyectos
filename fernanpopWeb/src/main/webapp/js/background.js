 function getCookie(cookie) {
    let nombre = cookie + "=";
    let cookies = decodeURIComponent(document.cookie); 
    //separa las cookies por ; es decir, crea un array usando como separador ;
    let ca = cookies.split(';');
    console.log(ca)
    for(let i = 0; i <ca.length; i++) {
      let c = ca[i];
      
      while (c.charAt(0) === ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(nombre) === 0) {
          console.log(c.indexOf(nombre), nombre);
        return c.substring(nombre.length, c.length);
      }
    }
    return "";
  }
let tipoTema = getCookie('tema') === 'oscuro' ? 'claro' : 'oscuro';
let tipo = "btn-"+tipoTema;

document.getElementById(tipo).addEventListener('click', function(){
   // console.log(tipoTema);
    document.cookie = "tema=" + tipoTema + ";expires=Fri, 31 Dec 9999 23:59:59 GMT";
    location.reload();
});