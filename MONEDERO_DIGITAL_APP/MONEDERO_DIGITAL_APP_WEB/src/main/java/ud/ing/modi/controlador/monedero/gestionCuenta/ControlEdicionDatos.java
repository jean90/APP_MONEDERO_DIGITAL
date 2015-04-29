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
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import ud.ing.modi.email.EmailActivacionCuenta;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.PersonMapper;

/**
 *
 * @author Lufe
 */
@ManagedBean(name = "controlEdicionDatos")
@ViewScoped
public class ControlEdicionDatos  implements Serializable{
    private Persona personaAEditar;
    private boolean datosVal;
    
    public ControlEdicionDatos() {
        cargarPersona();
        this.datosVal=true;
    }

    public Persona getPersonaAEditar() {
        return personaAEditar;
    }

    public void setPersonaAEditar(Persona personaAEditar) {
        this.personaAEditar = personaAEditar;
    }

    public boolean isDatosVal() {
        return datosVal;
    }

    public void setDatosVal(boolean datosVal) {
        this.datosVal = datosVal;
    }
    
    public void cargarPersona(){
        FacesContext contexto= FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contexto.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) contextoExterno.getRequest();
        String nick=request.getUserPrincipal().getName();
        this.personaAEditar=((ClienteNatural)new ClienteMapper().buscarPorNick(nick)).getPersona();
    }
    

    public void modifTel(ValueChangeEvent e){
        this.personaAEditar.setNumTelFijo(e.getNewValue().toString());
    }
    
    public void modifCel(ValueChangeEvent e){
        this.personaAEditar.setNumCelular(e.getNewValue().toString());
    }
    
    public void modifDir(ValueChangeEvent e){
        this.personaAEditar.setDireccion(e.getNewValue().toString());
    }
    
    public void modifEmail(ValueChangeEvent e){
        System.out.println("Modificando Email!!!");
        this.personaAEditar.setEmail(e.getNewValue().toString());
    }
    
    public void save(){
        PersonMapper mapeador = new PersonMapper();
        try {
            mapeador.actualizarPersona(personaAEditar);
            FacesMessage msg = new FacesMessage("Actualización exitosa ", personaAEditar.getNombre());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(ControlEdicionDatos.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage("Error", "Ha ocurrido un error en su actualización de datos");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    /**
     * Este método llama la validación de email
     * @param arg0
     * @param arg1
     * @param arg2
     * @throws ValidatorException 
     */
    public void validaEmail(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        System.out.println("Validando email "+arg2.toString());
        EmailActivacionCuenta emailVal=new EmailActivacionCuenta(arg2.toString());
      if (!emailVal.validarEmail()) {
        // throw new ValidatorException(new FacesMessage("Email no es válido"));
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Email no es válido"));
         this.datosVal=false;
      }else{
          this.datosVal=true;
      }
    }
    
    
}
