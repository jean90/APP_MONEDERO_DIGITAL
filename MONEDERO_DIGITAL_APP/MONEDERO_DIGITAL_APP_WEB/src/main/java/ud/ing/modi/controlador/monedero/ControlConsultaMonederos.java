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
import ud.ing.modi.entidades.Cliente;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.EstadoMonedero;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.PagoOnline;
import ud.ing.modi.entidades.Recarga;
import ud.ing.modi.entidades.TiendaOnLine;
import ud.ing.modi.ldap.TransaccionalLDAP;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.EstadoMonederoMapper;
import ud.ing.modi.mapper.MonederoMapper;
import ud.ing.modi.mapper.PagoOnlineMapper;
import ud.ing.modi.mapper.RecargaMapper;

/**
 *
 * @author Lufe
 */
@ManagedBean(name = "controlConsultaMoned")
@ViewScoped
public class ControlConsultaMonederos extends OperacionTransaccional implements Serializable{
    
    private List<Monedero> monederos = new ArrayList<Monedero>(); 
    private List<EstadoMonedero> estados = new ArrayList<EstadoMonedero>();
    private List<PagoOnline> pagos = new ArrayList<PagoOnline>();
    private List<Recarga> recargas = new ArrayList<Recarga>();
    private List<Movimiento> movimientos = new ArrayList<Movimiento>();
    
    private Monedero selectedMon;
    private String passTx;

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
        Cliente clienteDueno=null;
        if (request.isUserInRole("Monedero")) {
            clienteDueno=(ClienteNatural)new ClienteMapper().buscarPorNick(nick);
            
        }else if(request.isUserInRole("TiendaOnline")){
            clienteDueno=(ClienteJuridico)new ClienteMapper().buscarPorNick(nick);
            
        }
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

    public List<PagoOnline> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagoOnline> pagos) {
        this.pagos = pagos;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public List<Recarga> getRecargas() {
        return recargas;
    }

    public void setRecargas(List<Recarga> recargas) {
        this.recargas = recargas;
    }
    
    
       
    /**
     * Este método cambia el estado del monedero seleccionado de acuerdo a lo escogido por el cliente
     */
    public void cambioEst(){
        MonederoMapper mapeador=new MonederoMapper();
        TransaccionalLDAP ldap = new TransaccionalLDAP();
        String nick = traerUsu();
        try {
            if (validarEstadoPss(nick)) {
                if (this.validaPss(ldap, nick)) {
                    inicializarIntentosTx(nick);
                    this.asignarEst();

                    mapeador.actualizarMonedero(selectedMon);
                    FacesMessage msg = new FacesMessage("Monedero actualizado con éxito", "Código: " + selectedMon.getCodMonedero()+"Estado: "+selectedMon.getEstado().getDesEstado());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                }else{
                    FacesMessage msg = new FacesMessage("Contraseña transaccional errónea", null);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    validarBloqueoPss(nick);
                }
            }
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
     * Este método carga el histórico de pagos asociados al monedero seleccionado.
     */
    public void cargarHistorico(){
        System.out.println("Cargando histórico ....");
        PagoOnlineMapper mapPagos= new PagoOnlineMapper();
        this.pagos=mapPagos.obtenerPagos(selectedMon);
        
        for (int i = 0; i < pagos.size(); i++) {
            PagoOnline pago=pagos.get(i);
            Movimiento mov=new Movimiento("Pago Compra", Integer.toString(pago.getCodPago()), pago.getFechaPago(), Float.toString(pago.getValorPago()), "Cod Compra: "+pago.getCodCompra()+"\n Tienda: "+((TiendaOnLine)pago.getMonDestino().getClienteDueno()).getRazonSocial());
            this.movimientos.add(mov);
        }
        
        RecargaMapper mapRecargas=new RecargaMapper();
        this.recargas=mapRecargas.buscarRecargaPorMonedero(selectedMon);
        
        for (int i = 0; i < recargas.size(); i++) {
            Recarga recarga=recargas.get(i);
            Movimiento mov=new Movimiento("Recarga ", Integer.toString(recarga.getCodRecarga()), recarga.getFechaRecarga(), Double.toString(recarga.getValorRecarga()), "Punto de recarga: "+recarga.getPuntoRecarga().getRazonSocial());
            this.movimientos.add(mov);
        }
        
        this.ordenarMovs();
    }
    
    /**
     * Este método ordena la lista de movimientos por fecha de ejecución.
     */
    public void ordenarMovs(){
        
         int i, j;
         Movimiento aux;
         for(i=0;i<movimientos.size()-1;i++)
              for(j=0;j<movimientos.size()-i-1;j++)
                   if(movimientos.get(j+1).getFechaMov().after(movimientos.get(j).getFechaMov())){
                      aux=movimientos.get(j+1);
                      movimientos.set(j+1,movimientos.get(j));
                      movimientos.set(j,aux);
                   }

    }
}
