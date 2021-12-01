package utils;

import dao.UsuarioDAO;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import mensajeria.Comunicaciones;
import modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fichero {
    
    static final String RUTA_PROPERTIES = "C:\\Users\\Raul\\Desktop\\DesarrolloApp\\java\\proyectos\\fernanpopWeb\\src\\main\\resources\\";
    //vamos a guardar en disco los datos del programa
   /* public boolean salvarDatos(ArrayList<Usuario> users) {
        try {
            FileOutputStream fos = new FileOutputStream(Fichero.getPropiedad("path_datos",1) + "/appData.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Usuario> cargarDatos() {
        ArrayList<Usuario> usuarios;
        try {
            FileInputStream fis = new FileInputStream(Fichero.getPropiedad("path_AppData",1));
            ObjectInputStream ois = new ObjectInputStream(fis);
            usuarios = (ArrayList<Usuario>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return usuarios;
    }*/
   
    public static void registroLog(String funcion, String term, boolean error) {
        try {
            String ruta  = Fichero.getPropiedadApp(error ? "error_log_path":"app_log_path");

            BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true));
            bw.write("\"" + funcion + "\"" + ";" + term + ";" + Utils.getFechaHora() + "\n");
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean existeProperties() {
        File file = new File("config.properties");
        return file.exists();
    }

    public static void generarProperties() {
        if (!existeProperties()) {
            try {
                File f = new File("config.properties");
                f.createNewFile();
                Properties prop = new Properties();
                prop.load(new FileReader(f));
                prop.setProperty("admin_mail", "david.cueto@fernando3martos.com");
                prop.setProperty("host_mail", "SSL0.OVH.NET");
                prop.setProperty("emisor_mail", "dam6@carlosprofe.es");
                prop.setProperty("path_files", "./files/");
                prop.setProperty("username_mail", "dam6@carlosprofe.es");
                prop.setProperty("path_datos", "./datos");
                prop.setProperty("path_AppData", "./datos/appData.dat");
                prop.setProperty("password_mail", "Olivo.2021");
                prop.setProperty("menu_invitado", "true");
                prop.setProperty("excel_usuarios", "./files/usuarios.xls");
                prop.store(new BufferedWriter(new FileWriter("config.properties")), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static String prueba(){
        Properties p = new Properties();
        String str = "";
        try {
            p.load(new FileReader(RUTA_PROPERTIES + "prueba.properties"));
           str = p.getProperty("prueba");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
    public static String getPropiedadDb(String term) {
        Properties prop = new Properties();
        String propiedad = "";
        try {
            prop.load(new FileReader(RUTA_PROPERTIES + "db.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        propiedad = prop.getProperty(term);
        
        return propiedad;
    }
     public static String getPropiedadComunicacion(String term) {
        Properties prop = new Properties();
        String propiedad = "";
        try {
      
            prop.load(new FileReader(RUTA_PROPERTIES + "comunicacion.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        propiedad = prop.getProperty(term);
        
        return propiedad;
    }
      public static String getPropiedadUser(String term) {
        Properties prop = new Properties();
        String propiedad = "";
        try {
            prop.load(new FileReader(RUTA_PROPERTIES + "user.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        propiedad = prop.getProperty(term);
        
        return propiedad;
    }
       public static String getPropiedadApp(String term) {
         Properties prop = new Properties();
        String propiedad = "";
        try {
            prop.load(new FileReader(RUTA_PROPERTIES + "app.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        propiedad = prop.getProperty(term);
        
        return propiedad;
    }

    public static boolean hayUltimoInicioSesion(String email) {
        String usuario = getPropiedadUser(email);
        return usuario != null;
    }

    public static void cambiarPropiedad(String key, String value, int type) {
        Properties prop = new Properties();
        String filename = "";
        try {
            switch(type){
                case 1:
                    prop.load(new FileReader(RUTA_PROPERTIES + "db.properties"));
                    filename = "db.properties";
                    break;
                case 2:
                    prop.load(new FileReader(RUTA_PROPERTIES + "comunicacion.properties"));
                    filename = "comunicacion.properties";
                    break;
                case 3:
                    prop.load(new FileReader(RUTA_PROPERTIES + "user.properties"));
                    filename = "user.properties";
                    break;
                case 4:
                    prop.load(new FileReader(RUTA_PROPERTIES + "app.properties"));
                    filename = "app.properties";
                    break;
            }
            
            prop.setProperty(key, value);
            prop.store(new BufferedWriter(new FileWriter(RUTA_PROPERTIES + filename)), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void borrarPropiedad(String key) {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader("config.properties"));
            prop.remove(key);
            prop.store(new FileWriter("config.properties"), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> listarPropiedades() {
        Properties prop = new Properties();
        ArrayList<String> lista = new ArrayList<>();
        try {
            prop.load(new FileReader("config.properties"));
            for (Enumeration keys = prop.keys(), ele = prop.elements(); keys.hasMoreElements(); ) {
                String key = keys.nextElement().toString();
                String el = ele.nextElement().toString();

                if (!key.contains("@")) {
                    lista.add(key + " => " + el);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return lista;
    }

    public static void generarExcelUsuarios() {
        //crear archivo excel
        WritableWorkbook myFirstWbook = null;
        try {


            ArrayList<Usuario> usuarios = UsuarioDAO.getUsuarios();
            myFirstWbook = Workbook.createWorkbook(new File("C:\\Users\\Raul\\Desktop\\DesarrolloApp\\java\\proyectos\\fernanpopWeb\\src\\main\\resources\\excel\\data.xlsx"));

            // crear hoja excel
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

            // cabecero de la primera columna de la primera fila
            Label label = new Label(0, 0, "Correos de usuarios");
            excelSheet.addCell(label);

            for (int i = 0, j = 1; i < usuarios.size(); i++, j++) {
                label = new Label(0, j, usuarios.get(i).getEmail());
                excelSheet.addCell(label);
            }
            
            label = new Label(1 , 0, "nombre de usuario");
            excelSheet.addCell(label);
            
             for (int i = 0, j = 1; i < usuarios.size(); i++, j++) {
                label = new Label(1, j, usuarios.get(i).getNombre());
                excelSheet.addCell(label);
            }
             
              label = new Label(2 , 0, "Telefono");
               excelSheet.addCell(label);
            
             for (int i = 0, j = 1; i < usuarios.size(); i++, j++) {
                label = new Label(2, j, String.valueOf(usuarios.get(i).getTelefono()));
                excelSheet.addCell(label);
            }

              label = new Label(3 , 0, "Sexo");
               excelSheet.addCell(label);
            
             for (int i = 0, j = 1; i < usuarios.size(); i++, j++) {
                label = new Label(3, j, String.valueOf(usuarios.get(i).getSexo()));
                excelSheet.addCell(label);
            }
             
               label = new Label(4 , 0, "Fecha de registro");
               excelSheet.addCell(label);
            
             for (int i = 0, j = 1; i < usuarios.size(); i++, j++) {
                label = new Label(4, j, String.valueOf(usuarios.get(i).getFechaRegistro()));
                excelSheet.addCell(label);
            }
             
             label = new Label(5 , 0, "Usuario activo");
               excelSheet.addCell(label);
            
             for (int i = 0, j = 1; i < usuarios.size(); i++, j++) {
                label = new Label(5, j, String.valueOf(usuarios.get(i).isActivo()));
                excelSheet.addCell(label);
            }



            myFirstWbook.write();


        } catch (IOException | WriteException e) {
            e.printStackTrace(System.out);
        } finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                } catch (WriteException ex) {
                    Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


        }
    }

    public static boolean enviarExcel(String correo) {
        generarExcelUsuarios();
        return Comunicaciones.enviaMailConArchivo(correo, "EXCEL USUARIOS");

    }
}
