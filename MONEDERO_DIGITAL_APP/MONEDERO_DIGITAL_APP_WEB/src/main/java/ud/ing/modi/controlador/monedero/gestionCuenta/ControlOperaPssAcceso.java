/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.controlador.monedero.gestionCuenta;

import com.novell.ldap.LDAPException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import ud.ing.modi.ldap.AccesoLDAP;

/**
 *
 * @author Lufe
 */
@ManagedBean(name = "operaPssAcceso")
@ViewScoped
public class ControlOperaPssAcceso implements Serializable {

    private String passNueva;
    private String passActual;

    public ControlOperaPssAcceso() {
    }

    public String getPassNueva() {
        return passNueva;
    }

    public void setPassNueva(String passNueva) {
        this.passNueva = passNueva;
    }

    public String getPassActual() {
        return passActual;
    }

    public void setPassActual(String passActual) {
        this.passActual = passActual;
    }

    /**
     * Ese método permite la modificación de la contraseña de acceso
     */
    public void modificar() {
        AccesoLDAP ldap = new AccesoLDAP();
        String usuario = traerUsu();
        FacesMessage msg=null;
        try{
            if (validaPss(ldap, usuario)) {

                almacenaLdap(ldap, usuario);
                msg = new FacesMessage("La contraseña de acceso ha sido actualizada correctamente");
                
            } else {
                msg = new FacesMessage("Error", "La contraseña de acceso actual ingresada es incorrecta");
            }
        }catch(LDAPException e){
            if (e.getResultCode()==19) {
                //La contraseña ya ha sido asignada previamente y no se puede repetir.
                msg = new FacesMessage("Error", "La contraseña nueva ingresada no es permitida. Intente con un valor diferente.");
            }else{
                msg = new FacesMessage("Error", "Ha ocurrido un error en la operación. Intente nuevamente.");
            }
        }finally{
                FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }
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
     * Este método almacena en el ldap la nueva contraseña ingresada por el
     * usuario
     */
    public void almacenaLdap(AccesoLDAP ldap, String usuario) throws LDAPException {

        ldap.modificarPssAcceso(usuario, this.passNueva);
    }

    /**
     * Este método valida que la contraseña de acceso corresponda con la
     * comparada
     *
     * @param password Es la contraseña con la cual se comparará la que el
     * usuario tenga registrado en el ldap
     * @return Retorna como resultado el booleano que indica si las contraseñas
     * correspondieron
     */
    public boolean validaPss(AccesoLDAP ldap, String usuario) {
        System.out.println("Validando password " + passActual);
        return ldap.validarPassword(usuario, passActual);
    }

}
