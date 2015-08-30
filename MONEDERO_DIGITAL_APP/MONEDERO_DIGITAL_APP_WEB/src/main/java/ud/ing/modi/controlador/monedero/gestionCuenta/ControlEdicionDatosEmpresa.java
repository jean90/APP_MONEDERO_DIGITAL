/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.monedero.gestionCuenta;

import java.io.Serializable;
import java.util.List;
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
import ud.ing.modi.controlador.monedero.OperacionTransaccional;
import ud.ing.modi.email.EmailActivacionCuenta;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.EntidadFinanciera;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.entidades.TiendaOnLine;
import ud.ing.modi.entidades.TipoCuentaBancaria;
import ud.ing.modi.ldap.TransaccionalLDAP;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.EntidadFinancieraMapper;
import ud.ing.modi.mapper.PersonMapper;
import ud.ing.modi.mapper.TiendaMapper;
import ud.ing.modi.mapper.TipoCuentaMapper;

/**
 *
 * @author Lufe
 */
@ManagedBean(name = "controlEdicionDatosEmp")
@ViewScoped
public class ControlEdicionDatosEmpresa extends OperacionTransaccional implements Serializable{
    private TiendaOnLine tiendaAEditar;
    private boolean datosVal;
    private List<EntidadFinanciera> listaEntidades;
    private List<TipoCuentaBancaria> listaTiposCuentas;
    
    public ControlEdicionDatosEmpresa() {
        cargarEmpresa();
        this.datosVal=true;
        obtenerEntidadesFinancieras();
        obtenerTiposCuentasBancarias();
    }

    public TiendaOnLine getTiendaAEditar() {
        return tiendaAEditar;
    }

    public void setTiendaAEditar(TiendaOnLine tiendaAEditar) {
        this.tiendaAEditar = tiendaAEditar;
    }

    public boolean isDatosVal() {
        return datosVal;
    }

    public void setDatosVal(boolean datosVal) {
        this.datosVal = datosVal;
    }

    public List<EntidadFinanciera> getListaEntidades() {
        return listaEntidades;
    }

    public void setListaEntidades(List<EntidadFinanciera> listaEntidades) {
        this.listaEntidades = listaEntidades;
    }

    public List<TipoCuentaBancaria> getListaTiposCuentas() {
        return listaTiposCuentas;
    }

    public void setListaTiposCuentas(List<TipoCuentaBancaria> listaTiposCuentas) {
        this.listaTiposCuentas = listaTiposCuentas;
    }
    
    
    public void cargarEmpresa(){
        String nick=this.traerUsu();
        this.tiendaAEditar=((TiendaOnLine)new ClienteMapper().buscarPorNick(nick));
    }
    

    public void modifTel(ValueChangeEvent e){
        this.tiendaAEditar.setTelefono(e.getNewValue().toString());
    }
        
    public void modifDir(ValueChangeEvent e){
        this.tiendaAEditar.setDireccion(e.getNewValue().toString());
    }
    
    public void modifBanco(ValueChangeEvent e){
        System.out.println("Modificando banco!!!"+ ((EntidadFinanciera)e.getNewValue()).getDesEntidad());
        this.tiendaAEditar.setBanco((EntidadFinanciera)e.getNewValue());
        //this.tiendaAEditar.setBanco(e.getNewValue().toString());
    }
    
    public void modifTipoCta(ValueChangeEvent e){
        System.out.println("Modificando tipo cuenta!!!"+ ((TipoCuentaBancaria)e.getNewValue()).getDesTipoCuenta());
        this.tiendaAEditar.setTipoCuentaBancaria((TipoCuentaBancaria)e.getNewValue());
        //this.tiendaAEditar.setTipoCuentaBancaria(e.getNewValue().toString());
    }
    
    public void modifNumCta(ValueChangeEvent e){
        this.tiendaAEditar.setNumCuentaBancaria(e.getNewValue().toString());
    }
    
    public void modifDirRepr(ValueChangeEvent e){
        this.tiendaAEditar.getRepresentante().setDireccion(e.getNewValue().toString());
    }
    
    public void modifTelRepr(ValueChangeEvent e){
        this.tiendaAEditar.getRepresentante().setNumTelFijo(e.getNewValue().toString());
    }
    
    public void modifCelRepr(ValueChangeEvent e){
        this.tiendaAEditar.getRepresentante().setNumCelular(e.getNewValue().toString());
    }
    
    public void modifEmailRepr(ValueChangeEvent e){
        this.tiendaAEditar.getRepresentante().setEmail(e.getNewValue().toString());
    }
    
    public void save(){
        TiendaMapper mapeador = new TiendaMapper();
        TransaccionalLDAP ldap = new TransaccionalLDAP();
        String nick = traerUsu();
        PersonMapper mapPerson=new PersonMapper();
        try {
            if (this.validarEstadoPss(nick)) {
                if (this.validaPss(ldap, nick)) {
                    inicializarIntentosTx(nick);
                    mapeador.actualizarTienda(this.tiendaAEditar);
                    mapPerson.actualizarPersona(this.tiendaAEditar.getRepresentante());
                    FacesMessage msg = new FacesMessage("Actualización exitosa ", tiendaAEditar.getRazonSocial());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }else{
                    FacesMessage msg = new FacesMessage("Contraseña transaccional errónea", null);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    validarBloqueoPss(nick);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ControlEdicionDatosEmpresa.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void obtenerEntidadesFinancieras(){
        EntidadFinancieraMapper eMapper = new EntidadFinancieraMapper();
        listaEntidades = eMapper.obtenerEntidadesFinaciera();
    }
    
    public void obtenerTiposCuentasBancarias(){
        TipoCuentaMapper tMapper = new TipoCuentaMapper();
        listaTiposCuentas = tMapper.obtenerTiposCuentasBancarias();
    }
    
}
