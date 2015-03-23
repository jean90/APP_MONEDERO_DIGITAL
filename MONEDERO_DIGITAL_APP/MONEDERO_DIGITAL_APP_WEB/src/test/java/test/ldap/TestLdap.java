/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.ldap;

import java.util.logging.Level;
import java.util.logging.Logger;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.ldap.AccesoLDAP;

/**
 *
 * @author Administrador
 */
public class TestLdap {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Persona persona = new Persona(23124, "123543452", "lorena", "penagos", "345362345", "12342165134", "calle 67 # 45-76", "sejean90@gmail.com", null);
        AccesoLDAP aLdap = new AccesoLDAP();
        try {
            aLdap.InsertarUsuario(persona, "lorena432", "lorena432", "TiendaOnline");
        } catch (Exception ex) {
            Logger.getLogger(TestLdap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
