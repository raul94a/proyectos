
package mensajeria;

import controlador.Gestion;
import modelo.*;
import utils.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class Comunicaciones {
    public Comunicaciones(){}
    public static Boolean enviaMail(String destino, String asunto, String mensaje){
        boolean resultado;
        
        String emisor = "dam6@carlosprofe.es";
        String username = "dam6@carlosprofe.es";
        String password = "Olivo.2021";
        String host = "SSL0.OVH.NET";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress((emisor)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destino));
            message.setSubject(asunto);
            message.setContent(mensaje, "text/html; charset=utf-8");
            Transport.send(message);
            resultado = true;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        return resultado;
    }
    
    public static Boolean enviaMailConArchivo(String destino, String asunto){
        boolean resultado;
    


       String emisor = Fichero.getPropiedadComunicacion("emisor");
        String username = Fichero.getPropiedadComunicacion("username");
        String password = Fichero.getPropiedadComunicacion("password");
        String host = Fichero.getPropiedadComunicacion("host");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", Fichero.getPropiedadComunicacion("port"));
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try{
            BodyPart texto = new MimeBodyPart();
            texto.setText("Lista de usuarios registrados en FernanPop");
            BodyPart adjunto = new MimeBodyPart();
            //AQUI SE DEBE MODIFICAR LA RUTA DEL ARCHIVO, LA CUAL SE DEBE SACAR DE PROPERTIES!!!!!
            adjunto.setDataHandler(new DataHandler
                (new FileDataSource(
                        "C:\\Users\\Raul\\Desktop\\DesarrolloApp\\java\\proyectos\\fernanpopWeb\\src\\main\\resources\\excel\\data.xlsx"
                )));
            adjunto.setFileName("usuarios.xls");
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(texto);
            multipart.addBodyPart(adjunto);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress((emisor)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destino));
            message.setSubject(asunto);
            message.setContent(multipart);
            Transport t = session.getTransport("smtp");
            t.connect(username,password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            resultado = true;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        return resultado;
    }
    public static boolean enviarTelegram(String mensaje){
        /*mensaje = mensaje.replace("\n", "%0A");
        mensaje = mensaje.replace("─", "");
        mensaje = mensaje.replace(" ", "%20");*/
        mensaje = Utils.limpiarTildes(mensaje);
        mensaje = mensaje.replace(" ","%20").replace("\n",
                "%0A").replace("─", "-").replace(":", "");
        String direccion;
        String fijo = "https://api.telegram.org/bot1593922190:AAHMZJ9OHySkpFcr1L3zLI9RJXnTxftTq5E/sendMessage?chat_id=730782451&text=";
        direccion = fijo + mensaje;
        URL url;
        boolean dev = false;
        try {
            url = new URL(direccion);
            URLConnection con = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            dev = true;
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return dev;
    }
    public static boolean enviarToken(Usuario u) {
        String destino = u.getEmail();
        String asunto = "FernanPop. Envío de código de activación de cuenta.";
        String encabezado = "<!DOCTYPE html>\n<html lang=\"en\">\n<body>\n";
        String cola = "\n</body>\n</html>";
        String mensaje =
                "<div class=\"cuerpo\" style=\"margin: 0;\n" +
                        "padding: 0;\">\n" +
                        "        <div class=\"titulo-container\" style=\"text-align: center;width: 50%;margin-left: 25%;font-family:cursive;color: black;font-size: 3em;border-bottom-style: solid;\">\n" +
                        "            <p>FernanPop</p>\n" +
                        "        </div>\n" +
                        "        <div class=\"mensaje-container\" style=\"text-align: center; width: 100%; font-family:cursive;color: black;font-size: 1.2em;padding-top: 20px;\">\n" +
                        "            <p>Se ha generado un nuevo código de activación: <span style=\"font-weight: bold;\">" + u.getToken() + "</span></p>\n" +
                        "        </div>\n" +
                        "</div>";
        String mensajeFinal = encabezado + mensaje + cola;

        return Comunicaciones.enviaMail(destino, asunto, mensajeFinal);
    }
    public static boolean enviarProducto(Producto producto, Usuario u) {
        String destino;
        String asunto;
        String mensaje = "<body>\n" +
                "    <div class='contenedor' style='width:100%;height: 50vh;background-color: #0cbaba;\n" +
                "    background-image: linear-gradient(315deg, #0cbaba 0%, #380036 74%);\n" +
                "    '>\n" +
                "        <h1 class='titulo' style='text-align: center; color:rgb(243, 220, 11); margin-top: 20px; letter-spacing: 0.4rem;'>FernanPop</h1>\n" +
                "        <h3 class='aviso' style='text-align: center; color:white'>Has puesto un producto a la venta</h3>\n" +
                "        <div class='contenedor-datos' style=>\n" +
                "            <p class='data' style='text-align: center;font-size: 1.3rem; color:white;text-decoration: underline;'>Datos del producto:</p>\n" +
                "            <p style='font-family: \"Times New Roman\", Times, serif;font-size: 1.2rem; color:white;text-align: center;'>Nombre: " + producto.getNombre() + "</p>\n" +
                "            <p style='font-family: \"Times New Roman\", Times, serif;font-size: 1.2rem; color:white;text-align: center;'>Precio: " + producto.getPrecio() + " euros</p>\n" +
                "            <p style='font-family: \"Times New Roman\", Times, serif;font-size: 1.2rem; color:white;text-align: center;'>Descripcion: " + producto.getDescripcion() + "</p>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "</body>";
        //String mensaje = "Has puesto a la venta el siguiente producto: " + producto;
        destino = u.getEmail();
        asunto = "NUEVO PRODUCTO A LA VENTA";
        return Comunicaciones.enviaMail(destino, asunto, mensaje);
    }

    public static boolean enviarProductoAComprador(Producto producto, Usuario u) {
        String destino;
        String asunto;
        String mensaje = "<body>\n" +
                "    <div class='contenedor' style='width:100%;height: 50vh;background-color: #0cbaba;\n" +
                "    background-image: linear-gradient(315deg, #0cbaba 0%, #380036 74%);\n" +
                "    '>\n" +
                "        <h1 class='titulo' style='text-align: center; color:rgb(243, 220, 11); margin-top: 20px; letter-spacing: 0.4rem;'>FernanPop</h1>\n" +
                "        <h3 class='aviso' style='text-align: center; color:white'>Has comprado este producto</h3>\n" +
                "        <div class='contenedor-datos' style=>\n" +
                "            <p class='data' style='text-align: center;font-size: 1.3rem; color:white;text-decoration: underline;'>Datos del producto:</p>\n" +
                "            <p style='font-family: \"Times New Roman\", Times, serif;font-size: 1.2rem; color:white;text-align: center;'>Nombre: " + producto.getNombre() + "</p>\n" +
                "            <p style='font-family: \"Times New Roman\", Times, serif;font-size: 1.2rem; color:white;text-align: center;'>Precio: " + producto.getPrecio() + " euros</p>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "</body>";
        //String mensaje = "Has puesto a la venta el siguiente producto: " + producto;
        destino = u.getEmail();
        asunto = "COMPRA DEL PRODUCTO: " + producto.getNombre();
        return Comunicaciones.enviaMail(destino, asunto, mensaje);
    }

    //TODO HAY QUE VER COMO CAPTURAR LO QUE NECESITAMOS EN ESTA FUNCION
    public static boolean enviarCalificacion(Usuario usuarioAValorar, Trato trato) {
        
        String destino = usuarioAValorar.getEmail();
        String asunto = "FERNANPOP - Te han calificado por la " + ((trato.getTipo().equals("compra"))?"compra":"venta") + " de un producto";
        String msj = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<body>\n" +
                "    <div class=\"contenedor\" style='width:90%; margin-top:20px;height:35vh;background: rgba(66,62,61,1);\n" +
                "    background: -moz-linear-gradient(top, rgba(66,62,61,1) 0%, rgba(0,0,0,1) 64%, rgba(33,30,33,1) 92%, rgba(230,12,30,1) 100%);\n" +
                "    background: -webkit-gradient(left top, left bottom, color-stop(0%, rgba(66,62,61,1)), color-stop(64%, rgba(0,0,0,1)), color-stop(92%, rgba(33,30,33,1)), color-stop(100%, rgba(230,12,30,1)));\n" +
                "    background: -webkit-linear-gradient(top, rgba(66,62,61,1) 0%, rgba(0,0,0,1) 64%, rgba(33,30,33,1) 92%, rgba(230,12,30,1) 100%);\n" +
                "    background: -o-linear-gradient(top, rgba(66,62,61,1) 0%, rgba(0,0,0,1) 64%, rgba(33,30,33,1) 92%, rgba(230,12,30,1) 100%);\n" +
                "    background: -ms-linear-gradient(top, rgba(66,62,61,1) 0%, rgba(0,0,0,1) 64%, rgba(33,30,33,1) 92%, rgba(230,12,30,1) 100%);\n" +
                "    background: linear-gradient(to bottom, rgba(66,62,61,1) 0%, rgba(0,0,0,1) 64%, rgba(33,30,33,1) 92%, rgba(230,12,30,1) 100%);\n" +
                "    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr=\"#423e3d\", endColorstr=\"#e60c1e\", GradientType=0 );'>\n" +
                "    <div class=\"contenedor-titulo\">\n" +
                "        <h1 style='text-align: center;color:white;letter-spacing: 0.5rem;'>FernanPop</h1>\n" +
                "    </div>\n" +
                "        <h3 class=\"aviso\" style='color:white;text-align: center;'>Has sido valorado</h3>\n" +
                "        <p style='width:80%;color:white;font-family: cursive;text-align: center;font-size:1.2rem;margin-left:20px'>El "
                + ((trato.getTipo().equals("compra"))?"comprador ":"vendedor ")
                + " te ha valorado con " + trato.getPuntuacion() + " puntos por la " + ((trato.getTipo().equals("compra")) ? "venta":"compra") + " del producto "
                + trato.getProducto().getNombre() +
                "           . Te recordamos que también puedes valorarlo/a a él/ella.</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        //String mensaje = "El " + ((trato.getTipo().equals("compra"))?"vendedor ":"comprador ") + trato.getEmailOtroUser() + " te ha calificado con un " + trato.getPuntuacion() + " por la compra de " + compra.getProducto() +
        //" . También te ha dejado un comentario. Le recordamos que usted también puede valorar al vendedor.";

        return Comunicaciones.enviaMail(destino, asunto, msj);
    }

    static boolean enviarCalificacionVendedor(Gestion gestion, Usuario u) {
        String destino = "MODIFICAR "; // TODO VER COMO CAPTURAR EL EMAIL DEL QUE NOS HA VENEIDO EL PRODUCTO
        String asunto = "FERNANPOP - Te han calificado por la compra de un producto";
        String msj = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<body>\n" +
                "    <div class=\"contenedor\" style='width:90%; margin-top:20px;height:35vh;background: rgba(66,62,61,1);\n" +
                "    background: -moz-linear-gradient(top, rgba(66,62,61,1) 0%, rgba(0,0,0,1) 64%, rgba(33,30,33,1) 92%, rgba(230,12,30,1) 100%);\n" +
                "    background: -webkit-gradient(left top, left bottom, color-stop(0%, rgba(66,62,61,1)), color-stop(64%, rgba(0,0,0,1)), color-stop(92%, rgba(33,30,33,1)), color-stop(100%, rgba(230,12,30,1)));\n" +
                "    background: -webkit-linear-gradient(top, rgba(66,62,61,1) 0%, rgba(0,0,0,1) 64%, rgba(33,30,33,1) 92%, rgba(230,12,30,1) 100%);\n" +
                "    background: -o-linear-gradient(top, rgba(66,62,61,1) 0%, rgba(0,0,0,1) 64%, rgba(33,30,33,1) 92%, rgba(230,12,30,1) 100%);\n" +
                "    background: -ms-linear-gradient(top, rgba(66,62,61,1) 0%, rgba(0,0,0,1) 64%, rgba(33,30,33,1) 92%, rgba(230,12,30,1) 100%);\n" +
                "    background: linear-gradient(to bottom, rgba(66,62,61,1) 0%, rgba(0,0,0,1) 64%, rgba(33,30,33,1) 92%, rgba(230,12,30,1) 100%);\n" +
                "    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr=\"#423e3d\", endColorstr=\"#e60c1e\", GradientType=0 );'>\n" +
                "    <div class=\"contenedor-titulo\">\n" +
                "        <h1 style='text-align: center;color:white;letter-spacing: 0.5rem;'>FernanPop</h1>\n" +
                "    </div>\n" +
                "        <h3 class=\"aviso\" style='color:white;text-align: center;'>Has sido valorado como vendedor</h3>\n" +
                "        <p style='width:80%;color:white;font-family: cursive;text-align: center;font-size:1.2rem;margin-left:20px'>El/la comprador/a " + /*TODO INSERTAR NOMBRE COMPRADOR U.GETNOMBRE*/
                "           te ha valorado con " + /* TODO VALORACION INSERTADA*/" puntos por la compra del producto " +
                /*TODO AQUI HAY QUE INYECTAR EL PRODUCTO QUE HEMOS COMPRADO*/
                "           . Te recordamos que también puedes valorarlo/a a él/ella.</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        //String mensaje = "El comprador " + comprador.getNombre() + " te ha calificado con un " + venta.getCalificacion() + " por la venta de " + venta.getProducto() +
        //" . También te ha dejado un comentario. Le recordamos que usted también puede valorar al vendedor.";

        return Comunicaciones.enviaMail(destino, asunto, msj);
    }

}
