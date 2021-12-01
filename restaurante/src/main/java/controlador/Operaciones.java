/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.EstadisticaDAO;
import dao.FacturaDAO;
import dao.MesaDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import java.util.ArrayList;
import modelo.Estadistica;
import modelo.Factura;
import modelo.Pedido;
import modelo.Producto;

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
        Factura f = FacturaDAO.seleccionarFactura(Integer.parseInt(factura));
        return f == null ? null : f;
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
    
    public ArrayList<Pedido> pedidoNoFinalizado(){
        return PedidoDAO.seleccionarPedidosNoFinalizados();
    }
    public int contarPedidosPendientes(){
        return PedidoDAO.contarPedidosNoFinalizados();
    }
}
