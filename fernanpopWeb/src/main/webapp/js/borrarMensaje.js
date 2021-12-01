const conversacion = document.getElementById('conversacion');

conversacion.addEventListener('click', function(e){
   const target = e.target;
   const borrarParaReceptor = document.getElementById('borrarReceptor');

   if(target.classList.contains('propio')){
      borrarParaReceptor.value = "false";
      console.log('MENSAJE PROPIO EN EL PRIMER IF')
   } else if(target.classList.contains('ajeno')){
        console.log('MENSAJE AJENO')

       borrarParaReceptor.value = "true";
       
   }
   if(target.classList.contains('ajeno') || target.classList.contains('propio')){
       console.log('MENSAJE PROPIO O AJENO EN EL ULTIMO ELSE IF')
       const id = target.dataset.msj;
       console.log(id, target.dataset)
       const modal = document.getElementById('modal-borrar-mensaje');
       modal.classList.toggle('is-visible');
       const aceptar = document.getElementById('btn-aceptar-borrar-mensaje');
       aceptar.addEventListener('click', function(ev){
           document.getElementById('form-msj').value = id;
           document.getElementById('btn-borrar-mensaje').click();
       });
   }
});