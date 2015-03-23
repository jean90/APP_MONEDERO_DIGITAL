package ud.ing.modi.ldap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPSearchResults;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import ud.ing.modi.entidades.Persona;

/**
 *
 * @author Administrador
 */
public class AccesoLDAP {

    private final String baseBusqueda = "ou=Users,dc=monederodigital,dc=com,dc=co";
    public static final String  CUENTA_ACTIVA= "ACTIVA";
    public static final String  CUENTA_BLOQUEDA= "BLOQUEADA";
    public static final String  CUENTA_PENDIENTE_ACTIVACION= "PENDIENTE_ACTIVACION";

    public AccesoLDAP() {

    }

    public void InsertarUsuario(Persona persona, String uid, String password, String role) throws Exception {
        try {
            String grupo = role;//MODIFICAR ESTO*********
            LDAPEntry usuarioLdap = cargarDatos(persona, uid, password);
            ConexionLdap conn = new ConexionLdap();
            conn.abrirConexionLdap();
            conn.getLc().add(usuarioLdap);
            cargarMemberUid(grupo, uid, conn);
            conn.cerrarConexionLdap();
        } catch (LDAPException e) {
            System.out.println("fallo al insertar");
            e.printStackTrace();
            throw e;
        }
    }

    private LDAPEntry cargarDatos(Persona persona, String uid, String password) {
        LDAPAttributeSet setAtr = new LDAPAttributeSet();
        setAtr.add(new LDAPAttribute("objectclass", "PersonMonederoDigital"));
        setAtr.add(new LDAPAttribute("objectclass", "inetOrgPerson"));
        setAtr.add(new LDAPAttribute("objectclass", "organizationalPerson"));
        setAtr.add(new LDAPAttribute("objectclass", "person"));        
        setAtr.add(new LDAPAttribute("objectclass", "top"));
        setAtr.add(new LDAPAttribute("cn", persona.getNombre()));
        setAtr.add(new LDAPAttribute("estadoCuenta", AccesoLDAP.CUENTA_PENDIENTE_ACTIVACION));
        setAtr.add(new LDAPAttribute("intentosConexion", "0"));
        setAtr.add(new LDAPAttribute("sn", persona.getApellido()));
        setAtr.add(new LDAPAttribute("givenName", persona.getNombre()));
        setAtr.add(new LDAPAttribute("userPassword", password));
        String dn = "uid=" + uid + ",ou=Users,dc=monederodigital,dc=com,dc=co";
        LDAPEntry entrada = new LDAPEntry(dn, setAtr);
        return entrada;
    }

    private void cargarMemberUid(String grupo, String uid, ConexionLdap conn) throws LDAPException {
        //LDAPAttributeSet setAtr = new LDAPAttributeSet();
        //setAtr.add(new LDAPAttribute("memberUid",uid));
        String dn = "cn=" + grupo + ",ou=Group,dc=monederodigital,dc=com,dc=co";
        LDAPModification entrada = new LDAPModification(LDAPModification.ADD, new LDAPAttribute("memberUid", uid));
        conn.getLc().modify(dn, entrada);
    }

    public boolean buscarUsuario(String usuario) {
        LDAPSearchResults resultado = null;
        int salida = LDAPConnection.SCOPE_SUB;
        String filtro = "(uid=" + usuario + ")";
        try {
            ConexionLdap conn = new ConexionLdap();
            conn.abrirConexionLdap();
            resultado = conn.getLc().search(this.baseBusqueda, salida, filtro, null, false);
            System.out.println("RTADO **" + resultado.hasMore());
            if (resultado.getCount() > 0) {
                return true;
            }
            conn.cerrarConexionLdap();

           //Aqu� se pintan los atributos del resultado
          /* while(resultado.hasMore()){
             LDAPEntry entrada=null;
             try{
             entrada=resultado.next();
             }catch(LDAPException e){
             System.out.println("Error: "+e.toString());
             continue;
             }
             LDAPAttributeSet attributeSet = entrada.getAttributeSet();
             Iterator atributos = attributeSet.iterator();
             while(atributos.hasNext()){
             LDAPAttribute atributo=(LDAPAttribute)atributos.next();
             String nombreAtributo = atributo.getName();
             Enumeration valores = atributo.getStringValues();
             if(valores!=null){
             while(valores.hasMoreElements()){
             String valor=(String)valores.nextElement();
             System.out.println(nombreAtributo+": "+valor);
             }
             }
             }
             }*/
        } catch (LDAPException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
            System.out.println("ERROR AC�AAAAA" + ex.getMessage());
        }
        return false;
    }

    public boolean usuarioExiste(String usuario) {
        boolean existeUsuario = false;
        LDAPSearchResults resultado;        
        int salida = LDAPConnection.SCOPE_SUB;
        String filtro = "(uid=" + usuario + ")";
        System.out.println("USUARIO AL BUSCAR:" + filtro);
        try {
            ConexionLdap conn = new ConexionLdap();
            conn.abrirConexionLdap();
            resultado = conn.getLc().search(this.baseBusqueda, salida, filtro, null, false);
            if (resultado.hasMore()) {
                existeUsuario = true;
            }
            conn.cerrarConexionLdap();
        } catch (LDAPException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }
        System.out.println("USU EXISTE: " + existeUsuario);
        return existeUsuario;
    }

    public boolean validarPassword(String usuario, String password) {
        boolean passOK = false;
        String dn = "uid=" + usuario + "," + baseBusqueda;
        try {

            LDAPConnection conLdap = new LDAPConnection();
            conLdap.bind(LDAPConnection.LDAP_V3, dn, password.getBytes("UTF8"));
            LDAPAttribute atributo = new LDAPAttribute("userPassword", password);
            passOK = conLdap.compare(dn, atributo);

        } catch (LDAPException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        } catch (UnsupportedEncodingException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }
        return passOK;
    }

    public String getEstadoCuenta(String usuario) {
        String estadoCuenta = "ERROR";
        LDAPSearchResults resultado;
        int salida = LDAPConnection.SCOPE_SUB;
        String filtro = "(uid=" + usuario + ")";
        System.out.println("USUARIO AL BUSCAR:" + filtro);
        try {
            ConexionLdap conn = new ConexionLdap();
            conn.abrirConexionLdap();
            resultado = conn.getLc().search(this.baseBusqueda, salida, filtro, null, false);
            while (resultado.hasMore()) {
                LDAPEntry entradaLdap = resultado.next();
                estadoCuenta = entradaLdap.getAttribute("estadoCuenta").getStringValue();
            }
            conn.cerrarConexionLdap();
        } catch (LDAPException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }
        return estadoCuenta;
    }

    public String getNumIntentosConexion(String usuario) {
        String numIntentosConexion = "3";
        LDAPSearchResults resultado;
        int salida = LDAPConnection.SCOPE_SUB;
        String filtro = "(uid=" + usuario + ")";
        System.out.println("USUARIO AL BUSCAR:" + filtro);
        try {
            ConexionLdap conn = new ConexionLdap();
            conn.abrirConexionLdap();
            resultado = conn.getLc().search(this.baseBusqueda, salida, filtro, null, false);
            while (resultado.hasMore()) {
                LDAPEntry entradaLdap = resultado.next();
                numIntentosConexion = entradaLdap.getAttribute("intentosConexion").getStringValue();
            }
            conn.cerrarConexionLdap();
        } catch (LDAPException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }
        return numIntentosConexion;
    }

    public void modificarEstadoCuenta(String usuario, String estadoCuenta) {
        String dn = "uid=" + usuario + "," + this.baseBusqueda;
        LDAPModification entrada = new LDAPModification(LDAPModification.REPLACE, new LDAPAttribute("estadoCuenta", estadoCuenta));
        try {
            ConexionLdap conn = new ConexionLdap();
            conn.abrirConexionLdap();
            conn.getLc().modify(dn, entrada);
            conn.cerrarConexionLdap();
        } catch (LDAPException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }
    }

    public void modificarIntentosConexion(String usuario, String intentosConexion) {
        String dn = "uid=" + usuario + "," + this.baseBusqueda;
        LDAPModification entrada = new LDAPModification(LDAPModification.REPLACE, new LDAPAttribute("intentosConexion", intentosConexion));
        try {
            ConexionLdap conn = new ConexionLdap();
            conn.abrirConexionLdap();
            conn.getLc().modify(dn, entrada);
            conn.getLc().modify(dn, entrada);
            conn.cerrarConexionLdap();
        } catch (LDAPException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }
    }
}
