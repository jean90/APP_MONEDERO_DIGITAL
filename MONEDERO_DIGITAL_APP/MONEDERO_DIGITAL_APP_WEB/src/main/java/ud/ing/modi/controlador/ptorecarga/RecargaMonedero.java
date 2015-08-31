/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.ptorecarga;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import ud.ing.modi.tx.OperacionTransaccional;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.PuntoRecarga;
import ud.ing.modi.entidades.Recarga;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.MonederoMapper;
import ud.ing.modi.mapper.RecargaMapper;

/**
 *
 * @author Administrador
 */
@ManagedBean (name = "recargaMonedero")
@ViewScoped
public class RecargaMonedero extends OperacionTransaccional implements Serializable{

    /**
     * Creates a new instance of RecargaMonedero
     */
    
    private String codMonedero;
    private double valorRecarga;
    private String nickPtorecarga;
    private PuntoRecarga ptoRecarga;
    private Monedero monedero;
    private Recarga recarga;
    private String claveTx;
    private boolean realizoRecarga;
    
    private static final String RECARGA_OK = "recargaOK";
    
    public RecargaMonedero() {
        inicializar();
    }

    public void confirmarRecarga() throws Exception{
        System.out.println("cod Monedero: " + codMonedero);
        System.out.println("valor Recarga: " + valorRecarga);
        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contexto.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) contextoExterno.getRequest();
        this.nickPtorecarga = request.getUserPrincipal().getName();
        System.out.println("nick pto recarga: " + nickPtorecarga);        
        ClienteMapper cMapper = new ClienteMapper();
        ptoRecarga = cMapper.buscarPtoRecargaPorNick(nickPtorecarga);        
        MonederoMapper mMapper = new MonederoMapper();
        monedero = mMapper.buscarMonedero(codMonedero);    
    }
    
    public void realizarRecarga() throws Exception{        
        String respuesta = "";
        if(claveTx.equals("12345678")){
            MonederoMapper mMapper = new MonederoMapper();
            recarga = new Recarga();
            recarga.setValorRecarga(valorRecarga);
            recarga.setPuntoRecarga(ptoRecarga);
            recarga.setMonedero(monedero);
            recarga.setFechaRecarga(new Date());
            double saldoFinalMonedero = monedero.getSaldo() + valorRecarga;
            monedero.setSaldo((float) saldoFinalMonedero);
            mMapper.actualizarMonedero(monedero);
            RecargaMapper rMapper = new RecargaMapper();
            rMapper.guardarRecarga(recarga);
            realizoRecarga=true;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Recarga realizada satisfactoriamente.");
            FacesContext.getCurrentInstance().addMessage(null, message);             
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "ClaveTransaccional Incorrecta.");
            FacesContext.getCurrentInstance().addMessage("messagesPassTx", message);             
        }       
    }
    
    public void inicializar(){
        System.out.println("inicializando valores para realizacion de recarga");
        codMonedero="";
        valorRecarga=0.0;
        monedero=null;
        recarga=null;
        claveTx="";
        realizoRecarga=false;
    }
    
    public String getCodMonedero() {
        return codMonedero;
    }

    public void setCodMonedero(String codMonedero) {
        this.codMonedero = codMonedero;
    }

    public double getValorRecarga() {
        return valorRecarga;
    }

    public void setValorRecarga(double valorRecarga) {
        this.valorRecarga = valorRecarga;
    }

    public PuntoRecarga getPtoRecarga() {
        return ptoRecarga;
    }

    public void setPtoRecarga(PuntoRecarga ptoRecarga) {
        this.ptoRecarga = ptoRecarga;
    }

    public Monedero getMonedero() {
        return monedero;
    }

    public void setMonedero(Monedero monedero) {
        this.monedero = monedero;
    }

    public String getClaveTx() {
        return claveTx;
    }

    public void setClaveTx(String claveTx) {
        this.claveTx = claveTx;
    }

    public Recarga getRecarga() {
        return recarga;
    }

    public void setRecarga(Recarga recarga) {
        this.recarga = recarga;
    }

    public boolean isRealizoRecarga() {
        return realizoRecarga;
    }

    public void setRealizoRecarga(boolean realizoRecarga) {
        this.realizoRecarga = realizoRecarga;
    }
    
    
    
}
