
package seguridad;



import java.util.HashMap;
import java.util.Map;


public class Cifrado {

    private static final String[] list = {"a","b","c","d","e","f","g","h","i","j"
            ,"k","l","m","n","ñ","o","p","q","r","s","t","u",
            "v","w","x","y","z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J","K","L","M","N","Ñ","O","P","Q","R","S","T","U", "V", "W", "X", "Y", "Z","1","2","3","4","5","6","7","8","9","0"," ",
            "@",":","=","?","¿","¡","!","|","#","(",")","{","}","[","]","'","$","%","&","/","\\","º","*","-","+",".",";", "_"};
    //HAY QUE MODIFICAR EL ALGORITMO DE CIFRADO!!!!!! PARA LO QUE QUIERO HACER
    public static String cifrar(String cadena){
        //SE CREA PRIMERO LA CADENA DE ALEATORIAS CUYA FORMULA ES 255 - cadena.lenght - 3 -5 -1
        int longitud = 255 - cadena.length() - 5 - 3 - 1 - 1;
        int size = list.length;

        String cifradoAleatorio = "";

        for(int i = 0; i < longitud; i++){
            //String letra = list[(int) (Math.random() * size)];
            cifradoAleatorio += list[(int) (Math.random() * size)];
        }
       // System.out.println(cifradoAleatorio);
        String cola = "";
        for(int i = 0; i < 5; i++){
            cola += list[(int) (Math.random() * size)];
        }
       // System.out.println(cola);
        //creo el mapa de valores
        HashMap<Integer, String> diccionario = diccionario();

        int insertarPos = 0;
        String cadena2 = "";
        for(int i = 0; i < 3; i++){
            int numero = (int)(Math.random() * size);
            if(numero == 0){
                numero += 1;
            }

            String letter = diccionario.get(numero);
            insertarPos += numero;
            cadena2 += letter;
        }

       // System.out.println(insertarPos + "\n" + cadena2);
        String caracterLongitud = diccionario.get(cadena.length());

        //System.out.println(insertarPos);

        //generamos el número aleatorio que nos dará la pista de la clave para resolver el cifrado.
        //este se desplazará x posiciones a la derecha
        //ejemplo: si 'a' está en la posición 1 en el diccionario normal, y en la posición 1 del cifrado
        //hay una 'c', el cifrado se desplaza dos posiciones a la derecha (a = c, b=d, c=e etc...)
        int selectorDic = (int) (Math.random() * size);
        //lo copiamos en otra variable porque la original a lo mejor nos interesa tenerla no modificada
        //esta variable copia se utilizará para rellenar el diccionario cifrado
        String semilla = diccionario.get(selectorDic);

        //declaracion del cifrado
        HashMap<Integer, String> cifrado = enigma(selectorDic);
        //System.out.println("Numero aleatorio: " + selectorDic);
        //rellenamos el cifrado


        //AQUI GENERAMOS LOS PRIMEROS 100 CHAR ALEATORIOS!!!!!!!!!!!!!!!!!!!!!! (USANDO EL HASMAP)
        String cadenaCifrada = "";
        for(int i = 0; i < cadena.length();i++){
            int clave = -1;
            //seleccionamos cada letra de la cadena introducida
            String letra = String.valueOf(cadena.charAt(i));
            for(Map.Entry dic: diccionario.entrySet()){
                //sacamos cada valor del mapa diccionario
                String valor = (String) dic.getValue();
                //si el valor coincide con la letra, sacamos la posicion (clave) donde se encuentra
                //la letra dentro del mapa diccionario
                if(valor.equals(letra)){
                    clave = (int) dic.getKey();
                }
            }
            //ahora recorremos el mapa de cifrado
            for(Map.Entry cif:cifrado.entrySet()){
                //sacamos cada clave del mapa cifrado
                int keyCifrado = (int) cif.getKey();
                //y la comparamos con la clave del cifrado. Cuando coincida,
                //le añadimos el caracter a la cadenaCifrada
                if(keyCifrado == clave){
                    cadenaCifrada += (String)cif.getValue();
                }
            }
        }
        //tenemos cifraedoAleatorio - cadena cifrada - longitud - semilla - cola. INSERTAMOS
        String encriptacion = cifradoAleatorio.substring(0,insertarPos) + cadenaCifrada + cifradoAleatorio.substring(insertarPos) + cadena2 + caracterLongitud + semilla + cola;
     //   System.out.println(cadena2 + caracterLongitud + semilla + cola);
        //System.out.println("CADJENA CIFRA " + cadenaCifrada);
       
       // System.out.println(cifradoAleatorio+cadenaCifrada+cadena2+semilla+cola);
       // System.out.println(cadenaCifrada);
        return encriptacion;
    }


    //MODIFICAR EL ALGORITMO DE DESCIFRAMIENTO
    public static String descifrar(String cadena){
        //generamos el diccionario
        HashMap<Integer, String> diccionario = diccionario();
        //HAY QUE DESCUBRIR LA ENCRIPTACION PRIMERO NOS QUEDAMOS CON LOS ÚLTIMOS 10 CHAR
        String secreto = cadena.substring(cadena.length() -  10);
        System.out.println(secreto);
        //Vemos la POSICION DE INSERCION DEL CIFRADO
        String cadenaInsercion = secreto.substring(0,3);
        int posicion = 0;
        for(int i = 0; i < cadenaInsercion.length();i++){
            for( Map.Entry val:diccionario().entrySet() ){
                if(String.valueOf(cadenaInsercion.charAt(i)).equals(val.getValue())){
                    posicion += (int) val.getKey();
                }
            }
        }
        //AHORA LIMPIAMOS LA CADENA CIFRADA
        secreto = secreto.substring(3);
        String longitudClaveValor = secreto.substring(0,1);
        int longitudClave = 0;
        //SACAMOS LA LONGITUD DE LA CLAVE
        for( Map.Entry val:diccionario().entrySet() ){
            if(longitudClaveValor.equals(val.getValue())){
                longitudClave = (int) val.getKey();
                break;
            }
        }
        //limpiamos
        secreto = secreto.substring(1);
        //sacamos el caracter que nos dara la clave del cifrado
        String caracter = secreto.substring(0,1);
        //limpiamos
        secreto = secreto.substring(1);
        //generamos el cifrado
        HashMap<Integer, String> cifrado = enigma(caracter);
        //NOS QUEDAMOS CON LA CLAVE CIFRADA
        cadena = cadena.substring(posicion, posicion + longitudClave);
       // System.out.println("cadena aun cifrada "+ cadena);
        String descifrada = "";
        for(int i = 0; i < cadena.length();i++){
            int clave = -1;
            //seleccionamos cada letra de la cadena cifrada
            String letra = String.valueOf(cadena.charAt(i));
            for(Map.Entry cif: cifrado.entrySet()){
                //sacamos cada valor del mapa cifrado
                String valor = (String) cif.getValue();
                //si el valor coincide con la letra, sacamos la posicion (clave) donde se encuentra
                //la letra dentro del mapa cifrado
                if(valor.equals(letra)){
                    clave = (int) cif.getKey();
                }
            }
            //ahora recorremos el mapa de diccionario
            for(Map.Entry dic:diccionario.entrySet()){
                //sacamos cada clave del mapa cifrado
                int keyCifrado = (int) dic.getKey();
                //y la comparamos con la clave del cifrado. Cuando coincida,
                //le añadimos el caracter a la cadenaCifrada
                if(keyCifrado == clave){
                    descifrada += (String)dic.getValue();
                }
            }
        }
        //System.out.println("CADENA DESCIFERADA: " + descifrada);

        return descifrada;
    }

    private static int key(String term){
        HashMap<Integer, String> diccionario = diccionario();
        for(Map.Entry m:diccionario.entrySet()){
            if(m.getValue().equals(term)) return (Integer)m.getKey();
        }
        return -1;
    }

    private static HashMap<Integer, String> diccionario(){
        HashMap<Integer, String> diccionario = new HashMap<>();
        for(int i = 0; i < list.length;i++){
            diccionario.put(i + 1,list[i]);
        }
        return diccionario;
    }
    private static HashMap<Integer, String> enigma(int key){
        int contador = 0;
        HashMap<Integer, String> diccionario = diccionario();
        HashMap<Integer, String> cifrado = new HashMap<>();
        //int key = (int) (Math.random() * list.length);
        while(contador < list.length){
            cifrado.put(++contador, diccionario.get(key));
            key++;
            //si la variable es mayor que el tamaño, habrá que reinicializarla para que rellene el
            //resto de posiciones
            if(key > list.length){
                key = 1;
            }
        }
        return cifrado;
    }
    private static HashMap<Integer, String> enigma(String term){
        int contador = 0;
        HashMap<Integer, String> diccionario = diccionario();
        HashMap<Integer, String> cifrado = new HashMap<>();
        int key = key(term);
        while(contador < list.length){
            cifrado.put(++contador, diccionario.get(key));
            key++;
            //si la variable es mayor que el tamaño, habrá que reinicializarla para que rellene el
            //resto de posiciones
            if(key > list.length){
                key = 1;
            }
        }
        return cifrado;
    }
    
    public static String generarPassword(int longitudMaxima){
        String str = "";
        for(int i = 0; i < longitudMaxima; i++){
            String letter = list[(int)(Math.random() * list.length)];
            if(letter.equals(" ")){
                i--;
            }else{
               str += list[(int)(Math.random() * list.length)];

            }
        }
        return str;
        
    }
  

}
