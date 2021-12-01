/*const buttons = document.querySelectorAll("button");
totalProducts = 0;
counter = 0;
for(const button of buttons){
    button.addEventListener("click", function(){
        if(totalProducts === 0){
            totalProducts = button.parentElement.parentElement.childElementCount;
        }
        counter += 1;
        if(counter === totalProducts){
            var enlace = this.parentElement.parentElement.parentElement.childNodes[5];
            enlace.classList.remove("disable");
        }

       
    });
}*/

const a = document.querySelectorAll("a");
for(const anchor of a){
    anchor.addEventListener("click", function(){
       totalProductos = 0;
       counter = 0;
    });
}

