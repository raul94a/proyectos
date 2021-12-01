
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.*;

public class PdfManagement {
    public static void crearDocumento(ArrayList<Usuario> vacunacionesUsuario) throws FileNotFoundException, DocumentException, URISyntaxException, BadElementException{
        Vacuna v = vacunacionesUsuario.get(0).getVacuna();
        HashMap<String, String> mapaVac = new HashMap<>();
        mapaVac.put("Pfizer", "SARS-CoV-2 mRNA vaccine/\nSARS-CoV-2 vacuna ARNm");
        mapaVac.put("Moderna","SARS-CoV-2 mRNA vaccine/\nSARS-CoV-2 vacuna ARNm");
        mapaVac.put("Janssen", "SARS-CoV-2 Adenovirus vaccine / \nSARS-CoV-2 vacuna Adenovirus");
        mapaVac.put("AstraZeneca", "SARS-CoV-2 Adenovirus vaccine / \nSARS-CoV-2 vacuna Adenovirus");

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Raul\\Desktop\\DesarrolloApp\\java\\proyectos\\vacunacion\\src\\main\\webapp\\covidCertificates" + vacunacionesUsuario.get(0).getDni() + ".pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLUE);
       
       
        Chunk chunk = new Chunk("EU DIGITAL COVID CERTIFICATE\nCERTIFICADO COVID DIGITAL DE LA UE", font);
        Paragraph parrafo = new Paragraph(chunk);
        parrafo.setSpacingBefore(2);
        parrafo.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(parrafo);
          Image img = null;
        //Encabezado
           try {
            img = Image.getInstance("C:\\Users\\Raul\\Desktop\\DesarrolloApp\\java\\proyectos\\vacunacion\\src\\main\\webapp\\assets\\img\\header.jpg");
        } catch (IOException ex) {
            Logger.getLogger(PdfManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        //document.add(new Paragraph("Sample 1: This is simple image demo."));
        img.setAlignment(Image.ALIGN_RIGHT);
        img.setSpacingAfter(5);
        document.add(img);
        //Dirección del restaurante
        font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, BaseColor.BLACK);
        String header = "<!DOCTYPE html><html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><link rel='stylesheet' href='assets/styles/main.css'><title>AndaVac</title></head><body>";
        chunk = new Chunk(header + "<h1 style='color:red'>" + "Surname and forename / Apellidos y nombre\n" + vacunacionesUsuario.get(0).getNombre() +
                " " + vacunacionesUsuario.get(0).getApellidos() +
                        "\n\nDate of birth / Fecha de nacimiento\n1994-07-20</h1>");
        parrafo = new Paragraph(chunk);
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo.setSpacingAfter(5);
        document.add(parrafo);
        //qr
         try {
            img = Image.getInstance("C:\\Users\\Raul\\Desktop\\DesarrolloApp\\java\\proyectos\\vacunacion\\src\\main\\webapp\\assets\\img\\emisor.jpg");
        } catch (IOException ex) {
            Logger.getLogger(PdfManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        //document.add(new Paragraph("Sample 1: This is simple image demo."));
        img.setAlignment(Image.ALIGN_RIGHT);
        img.setSpacingAfter(5);
        document.add(img);
        
          // Creating a table object 

        font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
        String datos = "Vaccine/prophylaxis/Tipo de vacuna \tNumber in a series of vaccionations and number of doses "
                + "/ Número en una serie de vacunaciones y número de dosis\n" + mapaVac.get(v.getNombre()) +
                "\t" + vacunacionesUsuario.size() + "/" + vacunacionesUsuario.size() + 
                "\nVaccine medicial product / Vacuna administrada \t Date of Vaccination / Fecha de vacunación\n"
                + v.getNombre() + "\t" + vacunacionesUsuario.get(0).getFecha();
        chunk = new Chunk(datos, font);
        parrafo = new Paragraph(chunk);
        parrafo.setAlignment(Paragraph.ALIGN_LEFT);
        parrafo.setSpacingAfter(2);
        document.add(parrafo);
        //Datos de la factura
        font = FontFactory.getFont(FontFactory.COURIER, 1, BaseColor.BLACK);
        chunk = new Chunk("This certificate is not a travel document. The scientific evidence on COVID-19 vaccination, testing and recovery continues to evolve,\n" +
"also in view of new variants of concern of the virus. Before travelling, please check the applicable public health measures and related\n" +
"restrictions applied at the point of destination. / El presente certificado no es un documento de viaje. Los datos científicos sobre la\n" +
"vacunación, el test y la recuperación de la COVID-19 siguen evolucionando, también a la vista de las nuevas variantes preocupantes\n" +
"del virus. Antes de viajar, sírvase comprobar las medidas de salud pública aplicables y las restricciones correspondientes que se\n" +
"apliquen en el punto de destino.</body></html>", font);
        parrafo = new Paragraph(chunk);
        parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        parrafo.setSpacingAfter(50);
        document.add(parrafo);
        //insertamos la imagen
       
        
       
       

        document.close();
   }
   /* public static String generarFactura(ArrayList<Producto> productos){
        if(productos == null){
            return null;
        }
        float total = 0;
        String str = "";
        //bucle sobre los productos
        for(int i = 0;i < productos.size();i++){
            Producto p = productos.get(i);
            
            str += (i + 1) + ". " + p.getNombre();
            int longitudNombre = p.getNombre().length();
            int puntos = 30 - longitudNombre;
            //COMPLETAMOS CON PUNTOS HASTA LLEGAR AL PRECIO
            for(int j = 0; j < puntos;j++){
                str += " ";
            }
            //PONEMOS EL PRECIO DEL PRODUCTO ¿NECESITAMOS DARLE ESPACIO? PARA ELLO USAMOS EL HASHMAP CONFIG 
            String precioString = String.valueOf(p.getPrecio());
            int longitudPrecio = precioString.length();
            int cantidadDecimales = precioString.substring(precioString.indexOf(".") + 1).length();
            if(cantidadDecimales < 2){
               if(cantidadDecimales == 1){
                   precioString += "0";
               }else{
                   precioString += "00";

               }
            }
            for(int j = 0; j < (config.get("longitudPrecioUnitarioMax") - longitudPrecio);j++){
                str += " ";
            }
            //ahora ponemos el precio
            
            str += precioString + " €     Cantidad: ";
            //ahora tenemos que disponer de la cantidad de producto
            int cantidad = String.valueOf(p.getCantidad()).length();
            for(int j = 0; j < (config.get("longitudCantidadMax") - cantidad);j++){
                str += "0";
            }
            str += p.getCantidad() + " uds     ";
            //Ahora tenemos que hacer el calculo del precio de cada producto x su cantidad
            float precioTotalProducto = (float)Math.floor(p.getCantidad() * p.getPrecio() * 100) / 100;
            total += precioTotalProducto;
            String precioTotalProductoString = String.valueOf(precioTotalProducto);
            cantidadDecimales = precioTotalProductoString.substring(precioTotalProductoString.indexOf(".") + 1).length();
            if(cantidadDecimales < 2){
                if(cantidadDecimales == 1){
                    precioTotalProductoString += "0";
                }else{
                    precioTotalProductoString += "00";

                }
            }
            int longitudPrecioTotalProducto = precioTotalProductoString.length();
            //ponemos los espacios correspondientes
            for(int j = 0; j < (config.get("longitudPrecioMax") - longitudPrecioTotalProducto);j++){
                str += " ";
            }
            str += precioTotalProductoString + " €\n";
        }
        str +="\n\n\t\t\tTotal: " + (float)Math.floor(total * 100)/100 + " €";
        
        return str;
    }*/
    
}
