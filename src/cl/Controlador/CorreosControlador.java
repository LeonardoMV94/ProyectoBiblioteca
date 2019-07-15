/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Controlador;

import cl.Entity.Clientes;
import cl.Entity.Correos;
import cl.Modelo.CorreosJpaController;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public class CorreosControlador {
    
    private CorreosJpaController cjpa = new CorreosJpaController();
    private Correos c = new Correos();
    
    public int insertarCorreo(String rut, String correo){
        
        try {
          
          c.setCorreo(correo);
          cjpa.create(c);
          return c.getIdcorreo();
        } catch (Exception e) {
            return 0;
        }
        
        
    }
    
    
    
}
