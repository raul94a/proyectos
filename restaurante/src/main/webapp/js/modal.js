
class GestionCarta {
    
 
    static generarMapaConexiones() {
        const map = new Map();
        map.set('btnEliminarProducto', 'modalEliminarProd');
        map.set('btnAddProducto','modalAddProd'); 
        map.set('btnModificarProducto','modalModProd');
        map.set('btnVerCarta','modalVerCarta');
        return map;
    }
    static generarMapaConexionesBotonesCerrarModal(){
        const map = new Map();
        map.set('btnCerrarVerCarta', 'modalVerCarta');
        map.set('btnCerrarAddProd','modalAddProd'); 
        map.set('btnCerrarModProd','modalModProd');
        map.set('btnCerrarEliminarProd','modalEliminarProd');
        return map;
    }
    static conectarModales(){
        const map = this.generarMapaConexiones();
        for(let [key, value] of map){
          
            document.getElementById(key).addEventListener('click', function (e){
               const element = document.getElementById(value);
               element.classList.toggle('ocultar');
               const backdrop = document.querySelector('.backdrop');
               backdrop.classList.toggle('ocultar');
            });
        }
    }
    static conectarBotonesDeCierre(){
        const map = this.generarMapaConexionesBotonesCerrarModal();
        for(let [key, value] of map){
             document.getElementById(key).addEventListener('click', function (e){
               const element = document.getElementById(value);
               element.classList.toggle('ocultar');
               const backdrop = document.querySelector('.backdrop');
               backdrop.classList.toggle('ocultar');
            });
        }
    }
    
    static init(){
        this.conectarModales();
        this.conectarBotonesDeCierre();
    };
}

GestionCarta.init();