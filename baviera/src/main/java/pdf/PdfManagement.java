
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
    public static void crearDocumento(Pedido p) throws FileNotFoundException, DocumentException, URISyntaxException, BadElementException{
        Factura factura = p.getFac();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Raul\\Desktop\\DesarrolloApp\\java\\proyectos\\baviera\\src\\main\\webapp\\file\\facturas\\" + p.getId() + ".pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 24, BaseColor.BLACK);
        //Encabezado
        Chunk chunk = new Chunk("Restaurante Baviera", font);
        Paragraph parrafo = new Paragraph(chunk);
        parrafo.setSpacingBefore(50);
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(parrafo);
        //Dirección del restaurante
        font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
        chunk = new Chunk("Avenida de la Paz 1, Martos (Jaén)\nFactura número: " + factura.getId() + "\n\nFecha: " + factura.fecha(), font);
        parrafo = new Paragraph(chunk);
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo.setSpacingAfter(50);
        document.add(parrafo);
        //Datos del comprador
        font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);
        String datos = "Datos del comprador:\n\n" + "Nombre: " + p.getNombre() + "\nDireccion: " + p.getDireccion() + "\nTeléfono: " + p.getTelefono() ;
        chunk = new Chunk(datos, font);
        parrafo = new Paragraph(chunk);
        parrafo.setAlignment(Paragraph.ALIGN_MIDDLE);
        parrafo.setSpacingAfter(50);
        document.add(parrafo);
        //Datos de la factura
        font = FontFactory.getFont(FontFactory.COURIER, 11, BaseColor.BLACK);
        chunk = new Chunk(generarFactura(factura.getProductos()), font);
        parrafo = new Paragraph(chunk);
        parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        parrafo.setSpacingAfter(50);
        document.add(parrafo);
        //insertamos la imagen
        Image img = null;
        try {
            img = Image.getInstance("C:\\Users\\Raul\\Desktop\\DesarrolloApp\\java\\proyectos\\baviera\\src\\main\\webapp\\img\\Captura.jpg");
        } catch (IOException ex) {
            Logger.getLogger(PdfManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        //document.add(new Paragraph("Sample 1: This is simple image demo."));
       ;
        img.setAlignment(Image.ALIGN_CENTER);
        document.add(img);

        document.close();
   }
    public static String generarFactura(ArrayList<Producto> productos){
        if(productos == null){
            return null;
        }
        float total = 0;
        HashMap<String, Integer> config = mapaConfiguracion(productos);
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
    }
 
    public static HashMap<String, Integer> mapaConfiguracion(ArrayList<Producto> productos){
        HashMap<String, Integer> configuracion = new HashMap<>();
        configuracion.put("longitudNombreMax", 0);
        configuracion.put("longitudPrecioUnitarioMax", 0);
        configuracion.put("longitudCantidadMax", 0);
        configuracion.put("longitudPrecioMax", 0);
        for(Producto p:productos){
            int longitudNombre = p.getNombre().length();
            int longitudPrecioUnitario = String.valueOf(p.getPrecio()).length();
            int longitudCantidadMax = String.valueOf(p.getCantidad()).length();
            float precioTotal = (float)Math.floor(p.getCantidad() * p.getPrecio() * 100) / 100;
            int longitudPrecio = String.valueOf(precioTotal).length();
            
            if(longitudNombre > configuracion.get("longitudNombreMax")){
                configuracion.put("longitudNombreMax", longitudNombre);
            }
            if(longitudPrecioUnitario > configuracion.get("longitudPrecioUnitarioMax")){
                configuracion.put("longitudPrecioUnitarioMax", longitudPrecioUnitario);
            }
            if(longitudCantidadMax > configuracion.get("longitudCantidadMax")){
                configuracion.put("longitudCantidadMax", longitudCantidadMax);
            }
            if(longitudPrecio > configuracion.get("longitudPrecioMax")){
                configuracion.put("longitudPrecioMax", longitudPrecio);
            }
        }
        return configuracion;
    }
    
}
