/* Script para el index donde mostramos la info del producto dentro del box de cada producto al hacer un simple hover */


/*function mostrarDescripcion (value) {
    const descripcion = document.getElementById(value);
    descripcion.style.display="block";
 
    
}
function ocultarDescripcion(value){
    const descripcion = document.getElementById(value);
    descripcion.style.display="none";
}*/


//vamos a hacerlo de otra forma mas elegante
/*const cardProducto = document.querySelectorAll('.card-producto');
if(window.innerWidth >= 800){
    for(const card of cardProducto){
        card.addEventListener("mouseover", function () {
            for(const child of this.children){
                child.style.display="none"
            }
                        

            mostrarDescripcion(this.children[3].value)
            
        })
        card.addEventListener("mouseleave", function(){
            for(const child of this.children){
                child.style.display="block"
            }
           
            ocultarDescripcion(this.children[3].value);
        })
    }
}*/

const contenedorProductos = document.querySelector('.contenedor-productos');

contenedorProductos.addEventListener('mouseover', function(e){
   const target = e.target;
   if(target.classList.contains('card-foto')){
      const img = target;
              img.style.display="none";
      const precio = target.nextElementSibling;
              precio.style.display="none";
      const nombre = target.nextElementSibling.nextElementSibling;
              nombre.style.display="none";
      
      const descripcion =  target.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling;
      console.log(descripcion)
      descripcion.style.display="block";
      descripcion.parentElement.addEventListener('mouseleave', function(e){
        
        if(target.classList.contains('card-foto')){
             img.style.display="block";
              precio.style.display="block";
              nombre.style.display="block";
             descripcion.style.display="none";
        }   
      });
     
   }
});
