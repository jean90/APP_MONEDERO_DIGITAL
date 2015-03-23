/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.monedero;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.Divisa;
import ud.ing.modi.entidades.EstadoCliente;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.entidades.TipoDocumento;
import ud.ing.modi.mapper.DivisaMapper;
import ud.ing.modi.mapper.MonederoMapper;

/**
 *
 * @author Lufe
 */
@ManagedBean(name = "controlCreacionMoned")
@ViewScoped
public class ControlCreacionMonedero implements Serializable {
    
    private List<Divisa> divisas = new ArrayList<Divisa>();
    private Monedero monedero= new Monedero();

    public ControlCreacionMonedero() {
        this.traerDivisas();
    }

    public List<Divisa> getDivisas() {
        return divisas;
    }

    public void setDivisas(List<Divisa> divisas) {
        this.divisas = divisas;
    }
    
    public void traerDivisas(){
        DivisaMapper mapDivs= new DivisaMapper();
        this.divisas=mapDivs.obtenerDivisas();
    }

    public Monedero getMonedero() {
        return monedero;
    }

    public void setMonedero(Monedero monedero) {
        this.monedero = monedero;
    } 
    
    public void save(){
        MonederoMapper mapeador=new MonederoMapper();
        try {
            this.asignarDiv();
            
            FacesContext contexto= FacesContext.getCurrentInstance();
            ExternalContext contextoExterno = contexto.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) contextoExterno.getRequest();
            //monedero.setCodCliente(6);
            //ClienteNatural cliente=request.getUserPrincipal().getName();
            System.out.println("USUARIO: "+request.getUserPrincipal().getName());
            monedero.setClienteDueno(new ClienteNatural(new Persona(1, "1014211498", "JEAN", "PENAGOS", "4906771", "3102002149", "CALLE 69 # 96 - 96", null, new TipoDocumento(1, "Cedula de ciudadania")), 6, new Date(2014,11,18), new EstadoCliente(01, "VALIDACION")));
            monedero.setCodEstado(01);
            monedero.setCodMonedero("23428");
            monedero.setFechaCreacion(new Date());
            monedero.setSaldo(0);
            mapeador.guardarMonedero(monedero);
            FacesMessage msg = new FacesMessage("Monedero creado con éxito", "Código: " + monedero.getCodMonedero()+"\n Divisa: "+monedero.getDivisa().getDesDivisa()+"\n Fecha creación: "+monedero.getFechaCreacion());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(ControlCreacionMonedero.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage("Error", "Ha ocurrido un error en la creación del monedero");
            //Agregar mensaje de error en el html***
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        }
    
    public void asignarDiv(){
        for (int i = 0; i < this.divisas.size(); i++) {
            if (this.divisas.get(i).getCodigoDivisa()==this.monedero.getDivisa().getCodigoDivisa()) {
                //this.persona.getTipoDocumento().setDesDocumento(this.documentos.get(i).getDesDocumento());
                this.monedero.setDivisa(this.divisas.get(i));
            }
        }
    }
    
}
