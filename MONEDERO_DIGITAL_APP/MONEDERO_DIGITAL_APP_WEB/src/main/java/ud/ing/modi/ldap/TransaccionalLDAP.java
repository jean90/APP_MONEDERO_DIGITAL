/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.ldap;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lufe
 */
public class TransaccionalLDAP {
    private final String baseBusqueda = "ou=Users,dc=monederodigital,dc=com,dc=co";

    public TransaccionalLDAP() {
    }
    
    /**
     * Este método permite modificar la constraseña transaccional del usuario en el ldap
     * @param usuario Es el usuario al cual se asignará la contraseña
     * @param pssTx Es la contraseña que se asignará al usuario
     */
    public void modificarPssTx(String usuario, String pssTx) {
        String dn = "uid=" + usuario + "," + this.baseBusqueda;
        //Para que lo siguiente funciona debemos crear el campo en el ldap*******
        LDAPModification modifPss = new LDAPModification(LDAPModification.REPLACE, new LDAPAttribute("pssTx", pssTx));
        LDAPModification modifEst = new LDAPModification(LDAPModification.REPLACE, new LDAPAttribute("estadoPassTx", "ACTIVA"));
        try {
            ConexionLdap conn = new ConexionLdap();
            conn.abrirConexionLdap();
            conn.getLc().modify(dn, modifPss);
            conn.getLc().modify(dn, modifEst);
            conn.cerrarConexionLdap();
        } catch (LDAPException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }
    }
    
    /**
     * Este método valida la contraseña transaccional del cliente
     * @param usuario Es el usuario del cual se validará la contraseña
     * @param password Es la contraseña transaccional a validar
     * @return Retorna como resultado un booleano que indica si la contraseña es correcta
     */
    public boolean validarPssTx(String usuario, String password) {
        boolean passOK = false;
        String dn = "uid=" + usuario + "," + baseBusqueda;
        try {
            LDAPAttribute atributo = new LDAPAttribute("pssTx", password);
            ConexionLdap conn = new ConexionLdap();
            conn.abrirConexionLdap();
            passOK=conn.getLc().compare(dn, atributo);
            conn.cerrarConexionLdap();
        } catch (LDAPException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error 1 al validar el pss en el ldap "+e);
        }
        return passOK;
    }
    
    public boolean pssTxAsignado(String usuario) {
        boolean passOK = false;
        String dn = "uid=" + usuario + "," + baseBusqueda;
        try {
            LDAPAttribute atributo = new LDAPAttribute("estadoPassTx", "ACTIVA");
            ConexionLdap conn = new ConexionLdap();
            conn.abrirConexionLdap();
            passOK=conn.getLc().compare(dn, atributo);
            conn.cerrarConexionLdap();
        } catch (LDAPException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error 1 al validar el pss en el ldap "+e);
        }
        return passOK;
    }
}
