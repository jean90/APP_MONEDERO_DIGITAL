/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.ldap;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ud.ing.modi.config.Config;

/**
 *
 * @author Administrador
 */
public class ConexionLdap {
     private int ldapPort;    
     private LDAPConnection lc;
	 private String ldapHost;

    public ConexionLdap() {
        this.ldapHost=Config.getConfig().getPropiedad("LDAP_HOST");
        this.ldapPort=Integer.parseInt(Config.getConfig().getPropiedad("LDAP_PORT"));
       
    }
     
      
     public LDAPConnection ConexionManager(String strManager, String strPassword) {
          String login="uid="+strManager+",ou=system"; 
         //login = "cn=" + strManager + ",o=utpl,c=ec";
          System.out.println("" + login);          
          System.out.println("puerto: " + ldapPort);          
          System.out.println("Vesion: " + LDAPConnection.LDAP_V3);
          System.out.println("HOST: " + ldapHost);
          try {
               lc = new LDAPConnection();
               lc.connect(ldapHost, ldapPort);
               System.out.println("====Conectado al Servidor LDAP====");
               lc.bind(LDAPConnection.LDAP_V3, login, strPassword.getBytes("UTF8"));
               System.out.println("Autenticado en el servidor....");
          } catch (UnsupportedEncodingException ex) {
               Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE,null, ex);
          } catch (LDAPException ex) {
               Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE,null, ex);
          }
          return lc;
     }
 
     /**
     * Este metodo permite realizar la conexion al servidor de LDAP
     * Para el usuario manager
     * @param strUser
     * @param strPassword
     */
 
     public LDAPConnection ConexionUser(String strUser, String strPassword) {
          String login = "uid=" + strUser + ",ou=People,o=utpl,c=ec";
          System.out.println("" + login);
          ldapPort = LDAPConnection.DEFAULT_PORT;
          System.out.println("puerto: " + ldapPort);
         
          System.out.println("Vesion: " + LDAPConnection.LDAP_V3);
          System.out.println("HOST: " + ldapHost);
          try {
               lc = new LDAPConnection();
               lc.connect(ldapHost, ldapPort);
               System.out.println("====Conectado al Servidor LDAP====");
               lc.bind(LDAPConnection.LDAP_V3, login, strPassword.getBytes("UTF8"));
          } catch (UnsupportedEncodingException ex) {
               Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE,null, ex);
          } catch (LDAPException ex) {
               Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE,null, ex);
          }
          return lc;
     }
 
 	public void abrirConexionLdap() {
        String user=Config.getConfig().getPropiedad("LDAP_USER");
        String passWord=Config.getConfig().getPropiedad("LDAP_USER_PASSWORD");
        String login = "uid=" + user + ",ou=system";
        try {            
            this.lc = new LDAPConnection();
            if(this.lc.isConnectionAlive()){
                System.out.println("conexion abierta");
            }else{
                System.out.println("conexion cerrada");            
                this.lc.connect(ldapHost, ldapPort);
                System.out.println("====Conectado al Servidor LDAP====");
                System.out.println("Autenticado en el servidor....");
                this.lc.bind(LDAPConnection.LDAP_V3, login, passWord.getBytes("UTF8"));
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LDAPException ex) {
            Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
    public void cerrarConexionLdap() {
        try {
            this.lc.disconnect();            
            System.out.println("Conexion Cerrada Correctamente...");
        } catch (LDAPException ex) {
            Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public LDAPConnection getLc() {
        return lc;
    }
      
}
