/*let btnVals = document.querySelectorAll(".v");
console.log(btnVals);

for(const btnVal of btnVals){
    btnVal.addEventListener("click", function(){
        console.log("funciona el event listener")
        let identificador = this.id;
        console.log(this.id);
         let trato = 0;
        let user = 0;
        var position = 0;
        for(var i = 0; i < identificador.length;i++){
           let letra = identificador[i];
           if(letra === '/'){
               position = i;
               console.log(position)
               
           }
        }
        trato = identificador.substring(0,position);
        user = identificador.substring(position + 1)
        console.log(trato, user);
        
        let usuario = document.querySelector('.b');
        usuario.value="";
        console.log(usuario);
        usuario.value = user;
        console.log(usuario.value)
        let t = document.querySelector('.a');
        t.value ="";
        t.value = trato;
        console.log(t.value);
    })
}*/

const contenedorProductos = document.querySelector('.contenedor-productos');

contenedorProductos.addEventListener('click', function(e) {
    const target = e.target;
    console.log(target)
    if(e.target.classList.contains('v')){
      //  alert('btn pursao')
        const identificador = target.id;
        let trato = 0;
        let user = 0;
        var position = 0;
        for(var i = 0; i < identificador.length;i++){
           let letra = identificador[i];
           if(letra === '/'){
               position = i;
             
               
           }
        }
        //alert(position)
        trato = identificador.substring(0,position);
        user = identificador.substring(position + 1);
        
        let usuario = document.querySelector('.b');
        usuario.value = user;
      
        let t = document.querySelector('.a');
        t.value = trato;
   
     
    }
});