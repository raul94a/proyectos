const contenedorValoraciones = document.querySelector('.contenedor-valoraciones');
contenedorValoraciones.addEventListener('click', function(e) {
    
    const target = e.target;
    
    if(target.classList.contains('cntrl-btn')){
        //
       let trato = 0;
       let user = 0;
       let position = 0;
       
       const id = target.id;
       for(let i = 0; i < id.length;i++){
            let letra = id[i];
            if(letra === '/'){
                position = i;
            }
        }
        
        trato = id.substring(0,position);
        user = id.substring(position + 1);
        let inputTrato = document.querySelector('.id-trato');
        let usuario = document.querySelector('.id-usuarioValorado');
       
        usuario.value= user;
      
       inputTrato.value = trato;
    }
});