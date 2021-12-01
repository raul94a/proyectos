
import dao.UsuarioDAO;
import modelo.Usuario;
import seguridad.Cifrado;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Raul
 */
public class pruebaa {
    public static void main(String[] args) {
        int id = UsuarioDAO.buscarIdUsuario("madrid671@hotmail.com");
        
        Usuario u = UsuarioDAO.getUsuario(id);
        String psw = u.getPassword();
        System.out.println(psw);
        System.out.println(Cifrado.descifrar("z%@hIcrP2#t;#eM5ksÑNEs2McI'¿f%Xp /\\K[D!.2FpAv¿_20N)+oJEWB&Fu.QS*Hi|m2GT-L/BºZSPt0TL9wn&tOhh6KW|@uVs5jzRVM¿4¿3pe3)evPXRL¡¡|E{?8j*ñO7Gqfn6$Rf8ktQz]¿$4_t(ñt)=iYx;{@UV?k215u]fy4vId@RScD@hF)SJ-VpMELn58qÑFxE7NV}S%ao&c)n;_|5UJzlº6$N?Y;9W0n[aqºn]WutysbthC*sKY5&NR"));
    }
}
