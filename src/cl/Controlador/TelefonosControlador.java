/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Controlador;

import cl.Entity.Telefonos;
import cl.Modelo.TelefonosJpaController;

/**
 *
 * @author Leonardo
 */
public class TelefonosControlador {
    
    private TelefonosJpaController tjpa = new TelefonosJpaController();
    private Telefonos t = new Telefonos();
    
    public int insertarTelefono(String telefono){
        
        try {
            
            t.setTelefono(telefono);
            tjpa.create(t);
            return t.getIdtelefono();
        } catch (Exception e) {
            return 0;
        }
        
    }
    
}
