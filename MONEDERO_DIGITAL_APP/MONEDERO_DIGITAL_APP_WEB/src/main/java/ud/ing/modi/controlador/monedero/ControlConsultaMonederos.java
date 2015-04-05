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
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.EstadoMonedero;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.PagoOnline;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.EstadoMonederoMapper;
import ud.ing.modi.mapper.MonederoMapper;
import ud.ing.modi.mapper.PagoOnlineMapper;

/**
 *
 * @author Lufe
 */
@ManagedBean(name = "controlConsultaMoned")
@ViewScoped
public class ControlConsultaMonederos implements Serializable{
    
    private List<Monedero> monederos = new ArrayList<Monedero>(); 
    private List<EstadoMonedero> estados = new ArrayList<EstadoMonedero>();
    private List<PagoOnline> movimientos = new ArrayList<PagoOnline>();
    private Monedero selectedMon;

    public ControlConsultaMonederos() {
        this.traerEstados();
        this.listarMonederos();
    }

    public List<EstadoMonedero> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadoMonedero> estados) {
        this.estados = estados;
    }
    
    public void traerEstados(){
        EstadoMonederoMapper mapEstados= new EstadoMonederoMapper();
        this.estados=mapEstados.obtenerEstados();
    }
    
    /**
     * Este método lista los monederos propios del cliente loggeado.
     */
    public void listarMonederos(){
        MonederoMapper monMap=new MonederoMapper();
        FacesContext contexto= FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contexto.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) contextoExterno.getRequest();
        String nick=request.getUserPrincipal().getName();
        System.out.println("USUARIO: "+nick);
        ClienteNatural clienteDueno=(ClienteNatural)new ClienteMapper().buscarPorNick(nick);
        this.monederos=monMap.listarMonederos(clienteDueno);
    }

    public List<Monedero> getMonederos() {
        return monederos;
    }

    public void setMonederos(List<Monedero> monederos) {
        this.monederos = monederos;
    }

    public Monedero getSelectedMon() {
        return selectedMon;
    }

    public void setSelectedMon(Monedero selectedMon) {
        this.selectedMon = selectedMon;
    }

    public List<PagoOnline> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<PagoOnline> movimientos) {
        this.movimientos = movimientos;
    }
       
    /**
     * Este método cambia el estado del monedero seleccionado de acuerdo a lo escogido por el cliente
     */
    public void cambioEst(){
        MonederoMapper mapeador=new MonederoMapper();
        try {
            this.asignarEst();
            
            mapeador.actualizarMonedero(selectedMon);
            FacesMessage msg = new FacesMessage("Monedero actualizado con éxito", "Código: " + selectedMon.getCodMonedero()+"Estado: "+selectedMon.getEstado().getDesEstado());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(ControlCreacionMonedero.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage("Error", "Ha ocurrido un error en la actualización del monedero");
            //Agregar mensaje de error en el html***
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        }
    
    public void asignarEst(){
        for (int i = 0; i < this.estados.size(); i++) {
            if (this.estados.get(i).getCodigoEstado()==this.selectedMon.getEstado().getCodigoEstado()) {
                //this.persona.getTipoDocumento().setDesDocumento(this.documentos.get(i).getDesDocumento());
                this.selectedMon.setEstado(this.estados.get(i));
            }
        }
    }
    
    /**
     * Este método carga el histórico de movimientos asociados al monedero seleccionado.
     */
    public void cargarHistorico(){
        System.out.println("Cargando histórico ....");
        PagoOnlineMapper mapPagos= new PagoOnlineMapper();
        this.movimientos=mapPagos.obtenerPagos(selectedMon);
    }
    
}
