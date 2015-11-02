/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.ptorecarga;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import ud.ing.modi.entidades.PuntoRecarga;
import ud.ing.modi.entidades.Recarga;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.RecargaMapper;

/**
 *
 * @author Administrador
 */
@ManagedBean ( name = "consultaHistoricoRecargas")
@ViewScoped
public class ConsultaHistoricoRecargas implements Serializable{

    private String nickPtorecarga;
    private PuntoRecarga puntoRecarga;
    private List<Recarga> listaRecargas;
    
    public ConsultaHistoricoRecargas() {
        
    }    
    @PostConstruct
    public void inicializarConsulta(){
        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contexto.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) contextoExterno.getRequest();
        this.nickPtorecarga = request.getUserPrincipal().getName();
        System.out.println("nick pto recarga: " + nickPtorecarga);
        ClienteMapper cMapper = new ClienteMapper();
        puntoRecarga = cMapper.buscarPtoRecargaPorNick(nickPtorecarga);
        RecargaMapper recargaMapper = new RecargaMapper();
        listaRecargas = recargaMapper.buscarRecargaPorPtoRecarga(puntoRecarga);
    }
    
    public PuntoRecarga getPuntoRecarga() {
        return puntoRecarga;
    }

    public void setPuntoRecarga(PuntoRecarga puntoRecarga) {
        this.puntoRecarga = puntoRecarga;
    }

    public String getNickPtorecarga() {
        return nickPtorecarga;
    }

    public void setNickPtorecarga(String nickPtorecarga) {
        this.nickPtorecarga = nickPtorecarga;
    }

    public List<Recarga> getListaRecargas() {
        return listaRecargas;
    }

    public void setListaRecargas(List<Recarga> listaRecargas) {
        this.listaRecargas = listaRecargas;
    }  
    
}
