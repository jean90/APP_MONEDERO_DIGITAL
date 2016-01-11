/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.controlador.monedero.gestionCuenta;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import ud.ing.modi.ldap.TransaccionalLDAP;
import ud.ing.modi.tx.OperacionTransaccional;

/**
 *
 * @author Lufe
 */
@ManagedBean(name = "operaPssTx")
@ViewScoped
public class ControlOperaPssTx extends OperacionTransaccional implements Serializable {

    private String passNueva;
    private String passActual = "none";

    public ControlOperaPssTx() {
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
     * Este método permite la creación de la contraseña transaccional
     */
    public void crear() {
        TransaccionalLDAP ldap = new TransaccionalLDAP();
        String usuario = traerUsu();
        if (/*validaPss(ldap,usuario)*/!pssAsignado(ldap, usuario)) {//el validaPss es en cso que se vaya a comparar directo con el none
            almacenaLdap(ldap, usuario);
            FacesMessage msg = new FacesMessage("La contraseña transaccional ha sido creada correctamente");
            FacesContext.getCurrentInstance().addMessage("msg", msg);
        } else {
            FacesMessage msg = new FacesMessage("Error", "Usted ya tiene asignada una contraseña transaccional");
            FacesContext.getCurrentInstance().addMessage("msg", msg);
        }
    }

    /**
     * Ese método permite la modificación de la contraseña transaccional
     */
    public void modificar() {
        TransaccionalLDAP ldap = new TransaccionalLDAP();
        String usuario = traerUsu();
        if (pssAsignado(ldap, usuario)) {
            if (validaPss(ldap, usuario)) {

                almacenaLdap(ldap, usuario);
                FacesMessage msg = new FacesMessage("La contraseña transaccional ha sido actualizada correctamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Error", "La contraseña transaccional actual ingresada es incorrecta");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage("Error", "La contraseña transaccional no ha sido creada por primera vez. Ingrese por la opción de creación de contraseña.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    /**
     * Este método almacena en el ldap la nueva contraseña ingresada por el
     * usuario
     */
    public void almacenaLdap(TransaccionalLDAP ldap, String usuario) {

        ldap.modificarPssTx(usuario, this.passNueva);
    }


    /**
     * Este método valida que el cliente tenga un password transaccional asignado
     * @param ldap Es la conexión ldap desde la cual se realizarán los cambios
     * @param usuario Es el usuario del cual se verificará el usuario
     * @return 
     */
    public boolean pssAsignado(TransaccionalLDAP ldap, String usuario) {
        System.out.println("Validando password " + passActual);
        return ldap.pssTxAsignado(usuario);
    }
}
