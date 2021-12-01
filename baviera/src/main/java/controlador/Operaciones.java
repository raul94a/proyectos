/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import dao.EstadisticaDAO;
import dao.FacturaDAO;
import dao.MesaDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Estadistica;
import modelo.Factura;
import modelo.Pedido;
import modelo.Producto;
import pdf.PdfManagement;

/**
 *
 * @author Raul
 */
public class Operaciones {
    public Operaciones(){}
    
    //metodos de utilidad
    public int sumarUno(String value){
        return (Integer.parseInt(value) + 1);
    }
    
    public ArrayList<Integer> mesas(String libres){
        return MesaDAO.seleccionarMesas(Boolean.parseBoolean(libres));
    }
    public Factura factura(String factura){
        int fac = Integer.parseInt(factura);
        Factura f =  FacturaDAO.seleccionarFactura(fac);
        Pedido p = PedidoDAO.seleccionarPedidoPorFactura(fac);
        p.setFac(f);
        try {
            crearPdfFactura(p);
        } catch (DocumentException | FileNotFoundException | URISyntaxException ex) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }
    public void crearPdfFactura(Pedido pedido) throws DocumentException, FileNotFoundException, BadElementException, URISyntaxException{
        PdfManagement.crearDocumento(pedido);
    }
    public float calcularPrecio(String cantidad, String precio){
        int c = Integer.parseInt(cantidad);
        float p = Float.parseFloat(precio);
        return c * p;
    }
    public ArrayList<Producto> productos(){
        return ProductoDAO.seleccionarTodos();
    }
    public ArrayList<Producto> productosPorTipo(String tipo, String buscarSoloActivos){
        return ProductoDAO.seleccionarPorTipo(tipo, Boolean.parseBoolean(buscarSoloActivos));
    }
    
    public Estadistica estadistica(String tipo){
        int t = Integer.parseInt(tipo);
        float gananciasTotales = EstadisticaDAO.gananciasTotales(t);
        int numeroFacturas = EstadisticaDAO.numeroFacturas(t);
        ArrayList<Producto> productosMasVendidos = EstadisticaDAO.productosMasVendidos(t);
        ArrayList<Producto> productosMasDineroGenerado = EstadisticaDAO.productosMasDineroGenerado(t);
        return new Estadistica(gananciasTotales, numeroFacturas, productosMasVendidos, productosMasDineroGenerado);
    }
    
 
}
