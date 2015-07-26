/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.ldap;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPSearchResults;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lufe
 */
public class TransaccionalLDAP {
    private final String baseBusqueda = "ou=Users,dc=monederodigital,dc=com,dc=co";
    public static final String  CUENTA_ACTIVA= "ACTIVA";
    public static final String  CUENTA_BLOQUEDA= "BLOQUEADA";
    public static final String  CUENTA_SIN_ASIGNAR= "SIN_ASIGNAR";
    public static final int  NUM_INTENTOS_TX_MAX= 3;//Validar si finalmente quedan 3***********

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
    
    
    /**
     * Este método trae el estado de la contraseña transaccional del cliente
     * @param usuario Es el cliente del cual se desea validar el password
     * @return Retorna como resultado el estado del password
     */
    public String getEstadoPssTx(String usuario) {
        String estadoPssTx = "ERROR";
        LDAPSearchResults resultado;
        int salida = LDAPConnection.SCOPE_SUB;
        String filtro = "(uid=" + usuario + ")";
        System.out.println("USUARIO AL BUSCAR:" + filtro);
            ConexionLdap conn = null;
        try {
            conn = new ConexionLdap();
            conn.abrirConexionLdap();
            resultado = conn.getLc().search(this.baseBusqueda, salida, filtro, null, false);
            while (resultado.hasMore()) {
                LDAPEntry entradaLdap = resultado.next();
                estadoPssTx = entradaLdap.getAttribute("estadoPassTx").getStringValue();
            }
        } catch (LDAPException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }finally{
            //Para evitar que si conn no se pudo abrir caiga en error al intentar cerrar la conexión
            if (conn!=null&&conn.getLc().isConnectionAlive()) {
                conn.cerrarConexionLdap();     
            }
        }
        return estadoPssTx;
    }
    
    /**
     * Este método trae el número de intentos erróneos de uso del password transaccional.
     * @param usuario Es el usuario del cual se validará el password.
     * @return Retorna como resultado el número de intentos erróneos de uso del password.
     */
    public String getNumIntentosTx(String usuario) {
        String numIntentosTx = "";
        LDAPSearchResults resultado;
        int salida = LDAPConnection.SCOPE_SUB;
        String filtro = "(uid=" + usuario + ")";
        System.out.println("USUARIO AL BUSCAR:" + filtro);
            ConexionLdap conn = null;
        try {
            conn = new ConexionLdap();
            conn.abrirConexionLdap();
            resultado = conn.getLc().search(this.baseBusqueda, salida, filtro, null, false);
            while (resultado.hasMore()) {
                LDAPEntry entradaLdap = resultado.next();
                numIntentosTx = entradaLdap.getAttribute("numFallosPassTx").getStringValue();
            }
        } catch (LDAPException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }finally{
            //Para evitar que si conn no se pudo abrir caiga en error al intentar cerrar la conexión
            if (conn!=null&&conn.getLc().isConnectionAlive()) {
                conn.cerrarConexionLdap();     
            }   
        }
        return numIntentosTx;
    }
    
    /**
     * Este método modifica el número de intentos de uso erróneo de password transaccional
     * @param usuario Este es el usuario del cual se modificará el número de intentos
     * @param numIntentosTx Es el número de intentos que se almacenará
     */
    public void modificarIntentosTx(String usuario, String numIntentosTx) {
        String dn = "uid=" + usuario + "," + this.baseBusqueda;
        LDAPModification entrada = new LDAPModification(LDAPModification.REPLACE, new LDAPAttribute("numFallosPassTx", numIntentosTx));
            ConexionLdap conn = null;
        try {
            conn = new ConexionLdap();
            conn.abrirConexionLdap();
            conn.getLc().modify(dn, entrada);
            conn.getLc().modify(dn, entrada);
        } catch (LDAPException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }finally{
            //Para evitar que si conn no se pudo abrir caiga en error al intentar cerrar la conexión
            if (conn!=null&&conn.getLc().isConnectionAlive()) {
                conn.cerrarConexionLdap();     
            }  
        }
    }
    
    /**
     * Este método modifica el estado del password transaccional
     * @param usuario Es el usuario al cual se le modificará el estado del password
     * @param estadoTx Es el nuevo estado para el password transaccional
     */
    public void modificarEstadoTx(String usuario, String estadoTx) {
        String dn = "uid=" + usuario + "," + this.baseBusqueda;
        LDAPModification entrada = new LDAPModification(LDAPModification.REPLACE, new LDAPAttribute("estadoPassTx", estadoTx));
            ConexionLdap conn = null;
        try {
            conn = new ConexionLdap();
            conn.abrirConexionLdap();
            conn.getLc().modify(dn, entrada);
        } catch (LDAPException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }finally{
            //Para evitar que si conn no se pudo abrir caiga en error al intentar cerrar la conexión
            if (conn!=null&&conn.getLc().isConnectionAlive()) {
                conn.cerrarConexionLdap();     
            }  
        }
     }
}
