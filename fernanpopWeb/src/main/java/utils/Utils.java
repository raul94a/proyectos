package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Utils {
    public Utils(){}
    public static String lector(){
        return new Scanner(System.in).nextLine();
    }
    public static int lectorInt(){
        return Integer.parseInt(lector());
    }
    public static float lectorFloat(){
        return Float.parseFloat(lector());
    }
    public static void pulsaContinuar(){
        System.out.print("\nPulsa enter para continuar: ");
        lector();
    }

    public static String limpiarTildes(String str){
        HashMap<String,String> diccionario = new HashMap<>();
        diccionario.put("á","a");
        diccionario.put("é","e");
        diccionario.put("í","i");
        diccionario.put("ó","o");
        diccionario.put("ú","u");
        diccionario.put("ü", "u");
        String cadenaLimpia = "";
        for(int i = 0; i < str.length();i++){
            String letra = String.valueOf(str.charAt(i));
            if(diccionario.get(letra) == null){
                cadenaLimpia += letra;
            }else{
                letra = diccionario.get(letra);
                cadenaLimpia += letra;
            }
        }
        return cadenaLimpia;
    }
   /* public static boolean menuInvitado(){
        return Boolean.parseBoolean(Fichero.getPropiedad("menu_invitado",1));
    }*/
    public static Boolean leerArchivoMenuInvitado () throws IOException {

        FileReader lectura = new FileReader("menuinvitado.txt");
        BufferedReader lector = new BufferedReader(lectura);

        String resultado = lector.readLine();
        int posicion = 0;
        for (int i = 0; i < resultado.length(); i++) {
            if (resultado.charAt(i) == ':') posicion = i;
        }
        resultado = resultado.substring(posicion + 1);
        return Boolean.parseBoolean(resultado);
    }
    public static boolean validarEmail(String email){
        int longitud = email.length();
        for (int i = 0; i < longitud; i++) {
            String aux = email.substring(i, (i + 1));
            if (aux.equals("@")) return true;
        }
        return false;
    }
    public static boolean invitado(){
        boolean invitado = false;
        try {
            invitado = Utils.leerArchivoMenuInvitado();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return invitado;
    }
    public static void loading(){
        System.out.print("\n\nCargando");
        for (int i = 0; i < 3; i++){
            System.out.print(".");
            try{
                TimeUnit.MILLISECONDS.sleep(355);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("\n\n");
    }
    public static String generarCaracter() {
        int aleatorio = ((int) (Math.random() * 22)) ;
        String[] caracteres = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","K","R","L"};
        return caracteres[aleatorio];

    }
    public static String getFechaHora(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
    }
    public static String formatDateSQL(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

}
