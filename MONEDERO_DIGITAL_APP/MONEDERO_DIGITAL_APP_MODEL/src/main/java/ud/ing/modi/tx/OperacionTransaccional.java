/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.tx;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import ud.ing.modi.ldap.TransaccionalLDAP;

/**
 *
 * @author Lufe
 */
public class OperacionTransaccional {
    
    private String passTx;

    public OperacionTransaccional() {
    }

    public String getPassTx() {
        return passTx;
    }

    public void setPassTx(String passTx) {
        this.passTx = passTx;
    }
    
    
    
    /**
     * Este método devuelve el usuario cuya sesión ha sido iniciada
     *
     * @return Retorna como resultado el nick del usuario loggeado
     */
    public String traerUsu() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contexto.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) contextoExterno.getRequest();
        return request.getUserPrincipal().getName();
    }
    
    /**
     * Este método valida que la contraseña transaccional corresponda con la
     * comparada
     *
     * @param password Es la contraseña con la cual se comparará la que el
     * usuario tenga registrado en el ldap
     * @return Retorna como resultado el booleano que indica si las contraseñas
     * correspondieron
     */
    public boolean validaPss(TransaccionalLDAP ldap, String usuario) {
        System.out.println("Validando password " + this.passTx);
        return ldap.validarPssTx(usuario, this.passTx);
    }

    /**
     * Este método valida el estado del password transaccional del usuario.
     * @param usuario Es el usuario del cual se validará el password transaccional
     * @return Retorna como resultado el estado del password transaccional
     */
    public String validarEstadoPss(String usuario) {
        boolean estadoPssActivo = false;
        String estadoPssTx;
        TransaccionalLDAP ldap = new TransaccionalLDAP();
        System.out.println("estadoooooooo");
        estadoPssTx = ldap.getEstadoPssTx(usuario);
        System.out.println("estado Cuenta = " + estadoPssTx);
        /*if (estadoPssTx.equals(TransaccionalLDAP.PSS_ACTIVA)) {
            estadoPssActivo = true;
        } else if (estadoPssTx.equals(TransaccionalLDAP.PSS_BLOQUEADA)) {
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "PASSWORD BLOQUEADA", "No puede realizar esta operación en tanto no sea desbloqueada"));
        } else if (estadoPssTx.equals(TransaccionalLDAP.PSS_SIN_ASIGNAR)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "PASSWORD TRANSACCIONAL SE ENCUENTRA PENDIENTE DE ASIGNACION", "Asigne un password desde el submenú Gestionar cuenta - Crear contraseña transaccional"));
        }*/
        //return estadoPssActivo;
        return estadoPssTx;
    }

    /**
     * Este método valida si el password transaccional se debe bloquear.
     * @param usuario Es el usuario cuyo password será validado.
     * @return Retorna true si se bloquea la contraseña, de otro modo, false.
     */
    public boolean validarBloqueoPss(String usuario) {
        int numIntentosTx;
        TransaccionalLDAP ldap = new TransaccionalLDAP();
        numIntentosTx = Integer.parseInt(ldap.getNumIntentosTx(usuario));
        numIntentosTx++;
        ldap.modificarIntentosTx(usuario, Integer.toString(numIntentosTx));
        if (numIntentosTx >= TransaccionalLDAP.NUM_INTENTOS_TX_MAX) {
            //BLOQUEAR PASSWORD
            System.out.println("BLOQUEANDO PASSWORD TX");
            ldap.modificarEstadoTx(usuario, TransaccionalLDAP.PSS_BLOQUEADA);
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "PASSWORD BLOQUEADA", "Ha superado el número de intentos erróneos de clave transaccional"));
            return true;
        }
        return false;
    }

    /**
     * Este método se encarga de reiniciar el número de intentos de operación transaccional errónea
     * @param usuario Es el usuario del cual se reiniciará el campo
     */
    public void inicializarIntentosTx(String usuario) {
        int numIntentosConexion = 0;
        TransaccionalLDAP ldap = new TransaccionalLDAP();
        ldap.modificarIntentosTx(usuario, Integer.toString(numIntentosConexion));
    }
}
