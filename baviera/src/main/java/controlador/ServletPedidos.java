
package controlador;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import dao.DataProductoDAO;
import dao.FacturaDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.DataProducto;
import modelo.Pedido;
/**
 *
 * @author Raul
 */
@WebServlet("/ServletPedidos")
public class ServletPedidos extends HttpServlet {
   
    
    public String getParam (HttpServletRequest req, String param){
        return req.getParameter(param);
    }
    public int getParamInt(HttpServletRequest req, String param){
        return Integer.parseInt(getParam(req, param));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        //recogemos los parametros
        String nombre = getParam(req, "nombre");
        String direccion = getParam(req, "direccion");
        int telefono = getParamInt(req, "telefono");
        //El primer paso es crear la puta factura
        FacturaDAO.insertar(direccion);
        //pillamso ahora la ID de la factura que ha sido insertada
        int idFactura = FacturaDAO.seleccionarId(direccion);
        //creamos el objeto pedido para insertarlo en la base de datos
        /**
         SOLUCIÓN AL PROBLEMA: CREAR UNA FUNCIÓN DENTRO DE UNA CLASE DE UTILIDADES QUE GENERE UN BIGINT DE 12 DIGITOS ALEATORIAMENTE, BUSCAR SI EXISTE EN LA TABLA DE PEDIDOS Y SI NO EXISTE PARAR EL BUCLE
         * ESE NUMERO SE PASARÁ AL CONSTRUCTOR DE PEDIDO
         * FLUJO DE TRABAJO: 
         *      1. CREAR LA FUNCIÓN QUE GENERE EL ALEATORIO
         *      2. CREAR LA FUNCIÓN QUE COMPRUEBE LA EXISTENCIA DEL ALEATORIO
         *      3. MODIFICAR EL CONSTRUCTOR DE PEDIDO Y LO SMETODOS GET Y SET
         *      4. MODIFICAR PEDIDODAO EN EL METODO INSERTAR Y EN SU STRING SQL AÑADIENDOLE LA ID
         
         
         **/
        int idPedido = generarAleatorio();
        Pedido pedido = new Pedido(idPedido,nombre, direccion, telefono, idFactura);
        //insertamos el pedido
        PedidoDAO.insertar(pedido);
        //nos devuelve un JSON con el pedido, que convertiremos en arrayList de productos
       String json = req.getParameter("json");
       Type DataProductoListType = new TypeToken<ArrayList<DataProducto>>(){}.getType();
       ArrayList<DataProducto> dp = new Gson().fromJson(json, DataProductoListType);
       float precioFactura = 0;
       //ahora se inserta cada producto en la base de datos, en la tabla factura_producto (factura, producto, cantidad)
       //aprovecho para calcular cuanto vale cada producto y sumarlo para actualizar la factura
       for(DataProducto d:dp){
           //¿Existe la id del producto en la intertabla?
            if(DataProductoDAO.selectProducto(d.getIdentificador(), idFactura) != null){
                DataProductoDAO.updateCantidad(d.getCantidad(), idFactura, d.getIdentificador());
            }else{
                DataProductoDAO.insertar(idFactura, d);
            }
            precioFactura += ProductoDAO.seleccionarPrecio(d.getIdentificador()) * d.getCantidad();
       }
       //se inserta el precio en la factura!
       FacturaDAO.actualizarPrecio(precioFactura, idFactura);
       //en una app real esta función se llamaría únicamente tras realizar el pago! al igual que la inserción en la base de datos de la factura, los productos y el pedido!
       FacturaDAO.cerrarFactura(idFactura);
       //seteamos los atributos de session utilizados para redirigir a la página adecuada
       HttpSession session = req.getSession();
       session.setAttribute("factura", idFactura);
       session.setAttribute("a", "factura");
       session.setAttribute("pedido", idPedido);
       try {
           //¿A donde redirigimos? a index.jsp con simple .
           res.sendRedirect(".");
       } catch (IOException ex) {
           Logger.getLogger(ServletPedidos.class.getName()).log(Level.SEVERE, null, ex);
       }



    }
    
   private int generarAleatorio(){
      Random rand = new Random();
      return rand.nextInt(99999999 - 10000000 + 1) + 10000000;
   }
    
    
}
