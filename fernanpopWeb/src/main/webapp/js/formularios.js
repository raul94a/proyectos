const mapaUrl = () => {
    const map = new Map();
    map.set('/fernanpopWeb/registro.jsp', 'registro');
    map.set('/fernanpopWeb/login.jsp', 'login');
    map.set('/fernanpopWeb/subir.jsp', 'subir');
    map.set('/fernanpopWeb/productos-en-venta.jsp', 'enventa');
    map.set('/fernanpopWeb/ventas.jsp', 'ventas');
    map.set('/fernanpopWeb/compras.jsp', 'compras');
    map.set('/fernanpopWeb/pendientes-de-valorar.jsp','pendientesValorar');
    map.set('/fernanpopWeb/perfil.jsp','perfil');
    map.set('/fernanpopWeb/conversacion.jsp','conversacion');
    return map;
}



const getElemento = (id) => {
    return document.getElementById(id);
};

const validarPassword = (id) => {
    const elemento = getElemento(id);
    const valor = elemento.value;
    let isValid = true;
    if(valor.length < 6 || valor.length > 12){
        isValid = false;
    }
    return isValid;
};

const validarNombreUsuario = (id) => {
    const caracteresInvalidos = ['<','>', '"',';','/','\\','(',')','='];
    const elemento = getElemento(id);
    const valor = elemento.value;
    let isValid = true;
    //recorremos el string
    caracteresInvalidos.forEach(e => {
        console.log(e);
       if(valor.includes(e)){
           isValid = false;
       } 
    });
    
    if(valor.length > 25 || valor.length < 4){
        isValid = false;
    };
    
    return isValid;

};

const validarDescripcion = (id) => {
    const caracteresInvalidos = ['<','>', '"','/','\\','=', '{','}'];
    const elemento = getElemento(id);
    const valor = elemento.value;
    let isValid = true;
    //recorremos el string
    caracteresInvalidos.forEach(e => {
       console.log(e);
       if(valor.includes(e)){
           isValid = false;
       } 
    });
    return isValid;
}

const validarMensaje = id => {
      const caracteresInvalidos = ['<','>','{','}'];
      const mensajeElemento = getElemento(id);
      let mensaje = mensajeElemento.value;
      let isValid = true;
      
      caracteresInvalidos.forEach(e =>{
         if(mensaje.includes(e)){
             isValid = false;
            
         } 
         
      });
      return isValid;
  
};

const validarSubirImagen = (id) => {
    const formatosValidos = ['image/jpeg', 'image/png'];
    const foto = getElemento(id);
    const file = foto.files[0];
    let isValid = false;
    if(file){
        let tipo = file.type;
        if(tipo === formatosValidos[0] || tipo === formatosValidos[1]){
            isValid = true;
        }
    }
    return isValid;
    
};

const validarMovil = (id) => {
    const elementosValidos = '0123456789';
    const elemento = getElemento(id);
    const valor = elemento.value;
    let isValid = true;
    //comprobamos longitud
    if(valor.length !== 9){
        isValid = false;
    }
    //comprobamos que solo tenga numeros
    for(let i = 0; i < valor.length;i++){
        if(!elementosValidos.includes(valor[i])){
            isValid = false;
        }
    }
    //comporobamos que empiece por seis, aunque tb hay numeros que empiezan x 7
    if(!valor.startsWith('6')){
        isValid = false;
    }
    return isValid;
    
}
//HACER EN UN FUTURO EL RESTO DE COMPROBACIOENS

const validarRepeticionPassword = (pass, passRep) => {
    const pw = getElemento(pass);
    const pwr = getElemento(passRep);
    
    return pw.value === pwr.value;
};

const addParrafo = (texto) => {
    const p = document.createElement('p');
    p.classList.add('informacion-error');
    
    p.style.color = 'red';
    p.style.marginBottom = '1%';
    p.textContent = texto;
    return p;
};

//vamos a dividir la funcionalidad del script dependiendo de la url donde estemos
const diccionarioUrl = mapaUrl();
const url = window.location.pathname;
const lugar = diccionarioUrl.get(url);


switch(lugar){
    case "registro":
        let btn = document.getElementById('enviar');
        btn.addEventListener('click', function(e){
           const infoError = document.querySelectorAll('.informacion-error');
           infoError.forEach(e => {
               e.remove();
           });
           const nombreValido = validarNombreUsuario('nombre');
           const movilValido = validarMovil('movil');
           const passValida = validarPassword('password');
           const passValidaRep = validarPassword('password-rep');
           if(!nombreValido){
               e.preventDefault();
               const p = addParrafo('El nombre introducido no es correcto. Debe ser menor de 25 caracteres y no poseer caracteres especiales.');
               btn.before(p);
           }
           if(!movilValido){
               e.preventDefault();
               const p = addParrafo('El movil introducido no es correcto. comenzar por el número seis y contener 9 cifras en total.');
               btn.before(p);
           }
           if(!passValida || !passValidaRep){
               e.preventDefault();
               const p = addParrafo('La contraseña debe tener entre 6 y 12 caracteres.');
               btn.before(p);
           }
           if(!validarRepeticionPassword('password', 'password-rep')){
                e.preventDefault();
               const p = addParrafo('Las contraseñas no coinciden.');
               btn.before(p);
           }
        });
        break;
        
    case "login":
        let inicio = document.getElementById('enviar');
        inicio.addEventListener('click', function(e){
            let info = document.querySelector('.informacion-error');
           if(info !== null){
                info.remove();
            }
            const passValida = validarPassword('password');
            if(!passValida){
               e.preventDefault();
               const p = addParrafo('La contraseña debe tener entre 6 y 12 caracteres.');
              inicio.before(p);
           }
        });
        break;
        
    case "subir":

        let subir = document.getElementById('enviar');
        subir.addEventListener('click', function (e) {
           const infoError = document.querySelectorAll('.informacion-error');
           infoError.forEach(e => {
               e.remove();
           });
           const formatoFotoValido = validarSubirImagen('foto');
           const descripcionValida = validarDescripcion('descripcion');
           if(!formatoFotoValido){
               e.preventDefault();
               const p = addParrafo('El formato de la foto no es válido o no se ha subido ningún archivo. La aplicación solo acepta imágenes .jpg o png')
               subir.before(p);   
           }
           if(!descripcionValida){
               e.preventDefault();
               const p = addParrafo('La descripción contiene caracteres inválidos. Recuerda que no se permite el uso de <, >, ", /, \, =, {, }');
               subir.before(p);   
           }
        });
        break;
    case "enventa":
        let subirr = document.getElementById('enviar');
        subirr.addEventListener('click', function (e) {
           const infoError = document.querySelectorAll('.informacion-error');
           infoError.forEach(e => {
               e.remove();
           });
           const descripcionValida = validarDescripcion('descripcion');
           if(!descripcionValida){
               e.preventDefault();
               alert('La descripción contiene caracteres inválidos. Recuerda que no se permite el uso de <, >, ", /, \, =, {, }');
            //   subirr.before(p);   
           }
        });
        break;
    
    case "ventas":
        
        let valorarUsuarioVentas = document.getElementById('enviar');
        
        valorarUsuarioVentas.addEventListener('click', function(e){
            const comentarioValido = validarDescripcion('comentario');
            if(!comentarioValido){
                e.preventDefault();
                alert('El comentario no puede contener caracteres especiales.')
            }
            
        });
        
        break;
    case "compras":
        let valorarUsuarioCompras = document.getElementById('enviar');
        
        valorarUsuarioCompras.addEventListener('click', function(e){
            const comentarioValido = validarDescripcion('comentario');
            if(!comentarioValido){
                e.preventDefault();
                alert('El comentario no puede contener caracteres especiales.')
            }
            
        });
        
        break;
    case "pendientesValorar":
        let pendientesValorar = document.getElementById('enviar');
        
        pendientesValorar.addEventListener('click', function(e){
            const comentarioValido = validarDescripcion('comentario');
            if(!comentarioValido){
                e.preventDefault();
                alert('El comentario no puede contener caracteres especiales.')
            }
            
        });
        break;
    case "perfil":
        let subirFotoPerfil = document.getElementById('enviar');
        subirFotoPerfil.addEventListener('click', function(e){
           const fotoValida = validarSubirImagen('fotoPerfil'); 
           if(!fotoValida){
               e.preventDefault();
               alert('El formato de la foto no es válido o no se ha subido ningún archivo. La aplicación solo acepta imágenes .jpg o png')
               subir.before(p);   
           }
        });
        break;
    case "conversacion":
        let enviarMensaje = document.getElementById('form-msj-enviar');
        enviarMensaje.addEventListener('click', function(e){
            let mensajeValido = validarMensaje('form-msj');
            //console.log(mensajeValido);
            if(!mensajeValido){
                e.preventDefault();
                alert('Parece ser que quieres insertar codigo malicioso');
                
            }
        });
        break;
}

