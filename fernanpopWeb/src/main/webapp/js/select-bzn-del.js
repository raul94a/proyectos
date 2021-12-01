

let buttons = document.querySelectorAll('.control-conversaciones button');
let ids = [];
let modificador = document.querySelector(".control-botones-modal a");
        console.log(modificador.href)
const URL = modificador.href;
for(const button of buttons){
    button.addEventListener("click", function(){
        modificador.href = URL;
        let codigo = this.id;
        modificador.href = modificador.href + codigo;
        console.log(modificador.href)
    })
}
