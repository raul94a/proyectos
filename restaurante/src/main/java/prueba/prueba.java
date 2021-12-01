/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import controlador.Operaciones;
import dao.FacturaDAO;
import java.util.ArrayList;
import modelo.Factura;
import modelo.Producto;

/**
 *
 * @author Raul
 */
public class prueba {
    public static void main(String[] args) {
       
        ArrayList<Producto> ps = new Operaciones().productosPorTipo("carne", "true");
        for(Producto p:ps){
            System.out.println(p.getNombre());
        }
    }
}
