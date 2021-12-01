/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.itextpdf.text.DocumentException;
import dao.UsuarioDAO;
import dao.VacunaDAO;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;
import modelo.Vacuna;
import pdf.PdfManagement;

/**
 *
 * @author Raul
 */
public class Operaciones {
    public Operaciones(){}
    
    public Usuario usuario(String dni){
        return UsuarioDAO.selectUsuario(dni);
    }
    public ArrayList<Vacuna> vacunasDisponibles(){
        return VacunaDAO.seleccionarVacunasConUnidades();
    }
    public static void crearCertificado(ArrayList<Usuario> u) throws FileNotFoundException{
        try {
            PdfManagement.crearDocumento(u);
        } catch (DocumentException | URISyntaxException ex) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
