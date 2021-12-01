



function esconder() {
    const loader = document.querySelector('body h1')
    loader.style.visibility='visible';
    loader.style.opacity='1';

    const loaderImg = document.querySelector('.loader');
    loaderImg.style.visibility='visible';
    loaderImg.style.opacity='1';

    const index = document.querySelector('.index');
    index.style.visibility='hidden';
    index.style.opacity='0';

    

}
function mostrar(){
    setTimeout(esconder(), 3000)
        console.log()
        const loader = document.querySelector('body h1')
        loader.style.visibility='hidden';
        loader.style.opacity='0';

        const loaderImg = document.querySelector('.loader');
        loaderImg.style.visibility='hidden';
        loaderImg.style.opacity='0';

        const index = document.querySelector('.index');
        index.style.visibility='visible';
        index.style.opacity='1';
        const divLoading = document.querySelector('.loading').style.display="none";
}


const intervaloCarga = () => {
   
    const promise = new Promise(resolve => {
       
       setTimeout(() => {
           resolve();
       }, 2500);

    });
    return promise;
};

const init = () => {
    esconder();
    intervaloCarga().then(e => {
        mostrar();
    });
};
init();



