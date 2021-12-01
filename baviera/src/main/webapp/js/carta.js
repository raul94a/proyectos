class Carta {
    static idsTipoComida = ['carnes','pescados','pastas','bebidas'];
    static idPaginaIzquierda = 'carnes';
    static idPaginaDerecha = 'pescados';
    static indiceElementoPaginaIzquierda = 0;
    static indiceElementoPaginaDerecha = 1;
    
    static init(){
        //clases skew
        //this.skewIzquierda(this.idsTipoComida[0]);
       // this.skewDerecha(this.idsTipoComida[this.indiceElementoPaginaDerecha]);
        //OCULTAR PASTAS Y BEBIDAS
        for(let i = 2; i < this.idsTipoComida.length; i++){
            this.toggleContenedor(this.idsTipoComida[i]);
        }
       // this.crearSeparador();
       this.inicializarBotones();
       this.conectarBotonDerecho();
       this.conectarBotonIzquierdo();
    }
    static toggleContenedor(id){
        document.getElementById(id).classList.toggle('ocultar');
    }
    static skewIzquierda(id){
        document.getElementById(id).classList.add('skewIzquierda');
    }
    static skewDerecha(id){
        document.getElementById(id).classList.add('skewDerecha');
    }
    static normalizarAlturaContenedor(){
        const elementoIzquierda = document.getElementById(this.idPaginaIzquierda);
        const elementoDerecha = document.getElementById(this.idPaginaDerecha);
        let mayorAltura = 
                elementoIzquierda.offsetHeight > elementoDerecha.offsetHeight
                ? elementoIzquierda.offsetHeight
                : elementoDerecha.offsetHeight;
        const a2e = elementoDerecha.offsetHeight;
        let d = mayorAltura - a2e;
       // elementoIzquierda.style.height = mayorAltura + 'px';
        //elementoDerecha.style.height = mayorAltura + 'px';

    }
    static crearSeparador(){
        const div = document.createElement('div');
        div.classList.add('separador');
        document.getElementById(this.idPaginaDerecha).before(div);
    }
    static inicializarBotones() {
        const izquierdo = document.getElementById('controlador-izq');
        izquierdo.setAttribute('disabled','');
        document.getElementById('controlador-dcha').textContent = `Ver ${this.idsTipoComida[this.indiceElementoPaginaDerecha + 1]} >>`;
    }
    static pasarPaginaDerecha() {
        //Primero ocultamos la pagina izquierda ACTUAL. 
        const el = document.getElementById(Carta.idPaginaIzquierda)
        el.classList.toggle('ocultar');
   
        //ACTUALIZAMOS EL INDICE DELA PAGINA DERECHA e IZQUIERDA
        Carta.indiceElementoPaginaIzquierda++;
        Carta.indiceElementoPaginaDerecha++;
        Carta.idPaginaIzquierda = Carta.idsTipoComida[Carta.indiceElementoPaginaIzquierda];
        Carta.idPaginaDerecha = Carta.idsTipoComida[Carta.indiceElementoPaginaDerecha];
        //mostramos la paginna de la derecha actualizada!
        document.getElementById(Carta.idPaginaDerecha).classList.toggle('ocultar');
        //Modificanos el texto de los botones
        //BOTONES
        Carta.modificarTextoBotones();
        //comprobamos qué indice llevamos en la derecha
        if(Carta.indiceElementoPaginaDerecha === (Carta.idsTipoComida.length - 1)){
            document.getElementById('controlador-dcha').setAttribute('disabled','');
        }
         if(Carta.indiceElementoPaginaIzquierda !== 0){
            document.getElementById('controlador-izq').removeAttribute('disabled');
        }
        
    }
    static conectarBotonDerecho(){
        const derecho = document.getElementById('controlador-dcha');
        derecho.addEventListener('click', this.pasarPaginaDerecha);
        
    }
     static pasarPaginaIzquierda() {
        //Primero ocultamos la pagina derecha ACTUAL. 
        document.getElementById(Carta.idPaginaDerecha).classList.toggle('ocultar');
   
        //ACTUALIZAMOS EL INDICE DELA PAGINA DERECHA e IZQUIERDA
        Carta.indiceElementoPaginaIzquierda--;
        Carta.indiceElementoPaginaDerecha--;
        Carta.idPaginaIzquierda = Carta.idsTipoComida[Carta.indiceElementoPaginaIzquierda];
        Carta.idPaginaDerecha = Carta.idsTipoComida[Carta.indiceElementoPaginaDerecha];
        //mostramos la paginna de la derecha actualizada!
        document.getElementById(Carta.idPaginaIzquierda).classList.toggle('ocultar');
        //BOTONES
         Carta.modificarTextoBotones();

        //comprobamos qué indice llevamos en la derecha
        if(Carta.indiceElementoPaginaIzquierda === 0){
            document.getElementById('controlador-izq').setAttribute('disabled','');
        }
        //comprobamos tb el boton derecho
        if(Carta.indiceElementoPaginaDerecha !== Carta.idsTipoComida.length  - 1){
            document.getElementById('controlador-dcha').removeAttribute('disabled');
        }
        
    }
    
     static conectarBotonIzquierdo(){
        const izquierdo = document.getElementById('controlador-izq');
        izquierdo.addEventListener('click', this.pasarPaginaIzquierda);
        
    }
    static modificarTextoBotones(){
          if(Carta.indiceElementoPaginaDerecha !== Carta.idsTipoComida.length - 1){
            document.getElementById('controlador-dcha').textContent = `Ver ${Carta.idsTipoComida[Carta.indiceElementoPaginaDerecha + 1]} >>`;
        }else{
            document.getElementById('controlador-dcha').textContent = '—';
        }
        if(Carta.indiceElementoPaginaIzquierda !== 0){
            document.getElementById('controlador-izq').textContent = `<< Ver ${Carta.idsTipoComida[Carta.indiceElementoPaginaIzquierda - 1]}`;
        }else{
            document.getElementById('controlador-izq').textContent = '—';
        }
    }
    
}

Carta.init();
