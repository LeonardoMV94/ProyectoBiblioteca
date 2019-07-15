
package cl.Controlador;

import cl.Modelo.TrabajadoresJpaController;
import cl.Entity.Trabajadores;
import cl.Vista.Menu;
import java.util.Date;

/**
 *
 * @author Leonardo
 */
public class TrabajadoresControlador {

    private TrabajadoresJpaController tjpa = new TrabajadoresJpaController();
    private Trabajadores trabajador = new Trabajadores();
    String mensaje="";
    
    public String insertarTrabajador(String rutTrabajador,String nombre, 
            String apellidoPaterno,String apellidoMaterno, Date fechaContrato) throws Exception{
        try {
            trabajador.setRutTrabajador(rutTrabajador);
            trabajador.setNombre(nombre);
            trabajador.setApellidoPaterno(apellidoPaterno);
            trabajador.setApellidoMaterno(apellidoMaterno);
            trabajador.setFechaContrato(fechaContrato);
       
        tjpa.create(trabajador);
        
        return mensaje = "insertado correctamente";
        } catch (Exception e) {
          return mensaje = "ERROR: " + e.getMessage();
        }
        
        
        
    }
    
    public boolean passwordTrabajador(String rut , String clave){
        
        trabajador = tjpa.findTrabajadores(rut);
        
        if (trabajador.getPassword().equals(clave)) {
            Menu m = new Menu( trabajador);
            m.setVisible(true);
            
            return true;
        }else{
             return false;
        }
        
        
       
    }
    
   public Trabajadores buscarTrabajador(String rut){
       
      
       try {
         Trabajadores tb=   tjpa.findTrabajadores(rut);
          return tb;
       } catch (Exception e) {
           return null;
       }
       
   }
   
    
}
