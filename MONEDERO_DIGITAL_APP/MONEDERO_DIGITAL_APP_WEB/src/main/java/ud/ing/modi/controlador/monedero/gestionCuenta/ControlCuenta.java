/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.monedero.gestionCuenta;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ud.ing.modi.tx.OperacionTransaccional;
import ud.ing.modi.entidades.Cliente;
import ud.ing.modi.entidades.EstadoCliente;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.ldap.TransaccionalLDAP;
import ud.ing.modi.mapper.ClienteMapper;

/**
 *
 * @author Lufe
 */
@ManagedBean(name = "controlCuenta")
@ViewScoped
public class ControlCuenta extends OperacionTransaccional implements Serializable{
    private Cliente cliente;

    public ControlCuenta() {
        cargarCliente();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void cargarCliente(){
        String nick=this.traerUsu();
        this.cliente=new ClienteMapper().buscarPorNick(nick);
    }
    
    /**
     * Este método cancela la cuenta del cliente
     */
    public String cancelarCuenta(){
        String estado="fallo";
        ClienteMapper mapeador=new ClienteMapper();
        EstadoCliente estadoCliente= new EstadoCliente(3, "CANCELADO");
        TransaccionalLDAP ldap = new TransaccionalLDAP();
        String nick = traerUsu();
        try {
            if (this.validarEstadoPss(nick).equals(TransaccionalLDAP.PSS_ACTIVA)) {
                if (this.validaPss(ldap, nick)) {
                    inicializarIntentosTx(nick);
                    this.cliente.setEstadoCliente(estadoCliente);
                    mapeador.actualizarCliente(cliente);
                    modifLdap();
                    FacesMessage msg = new FacesMessage("Cambio realizado", "Su cuenta ha sido cancelada correctamente");
                    //Agregar mensaje de error en el html***
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    estado="cancelada";

                    System.out.println("CERRANDO SESION");
                    FacesContext contexto = FacesContext.getCurrentInstance();
                    ExternalContext contextoExterno = contexto.getExternalContext();
                    HttpServletRequest request = (HttpServletRequest) contextoExterno.getRequest();
                    request.logout();
                    /*HttpServletResponse response = (HttpServletResponse) contextoExterno.getResponse();
                    System.out.println("PATH "+request.getContextPath());
                    response.sendRedirect(request.getContextPath()+"/faces/LogIn/LogIn.xhtml?caso=cancelada");*/
                    return "/LogIn/LogIn.xhtml?caso=cancelada";
                }else{
                    FacesMessage msg = new FacesMessage("ERROR", "Contraseña transaccional errónea");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    if (validarBloqueoPss(nick)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "PASSWORD BLOQUEADA", "Ha superado el número de intentos erróneos de clave transaccional"));
                    }
                }
            }else if (validarEstadoPss(nick).equals(TransaccionalLDAP.PSS_BLOQUEADA)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "PASSWORD BLOQUEADA", "No puede realizar esta operación en tanto no sea desbloqueada"));
            }else if (validarEstadoPss(nick).equals(TransaccionalLDAP.PSS_SIN_ASIGNAR)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "PASSWORD TRANSACCIONAL SE ENCUENTRA PENDIENTE DE ASIGNACION", "Asigne un password desde el submenú Gestionar cuenta - Crear contraseña transaccional"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ControlCuenta.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage("Error", "Ha ocurrido un error en la cancelación de su cuenta");
            //Agregar mensaje de error en el html***
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        //return estado;
        return null;
    }
    
    /**
     * Este método realiza la modificación del estado del cliente a cancelado en el LDAP
     * @throws Exception 
     */
    public void modifLdap() throws Exception{
        AccesoLDAP ldap= new AccesoLDAP();
        ldap.modificarEstadoCuenta(this.cliente.getNickname(),AccesoLDAP.CUENTA_CANCELADA);
    }
    
    
}
