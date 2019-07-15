
package cl.Vista;

import cl.Controlador.TrabajadoresControlador;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Leonardo
 */
public class prueba {
    
    public static void main(String[] args) throws Exception {
        
        TrabajadoresControlador t= new TrabajadoresControlador();
        
        Calendar date = Calendar.getInstance();
        Date d = date.getTime();
        
        System.out.println(t.insertarTrabajador("1-1", "asd", "asdasd", "asdasd", d));
        
        
    }
    
    
}
