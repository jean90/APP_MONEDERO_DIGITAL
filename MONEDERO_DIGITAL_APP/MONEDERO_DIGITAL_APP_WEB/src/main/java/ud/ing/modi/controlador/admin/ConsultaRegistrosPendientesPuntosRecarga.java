/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.admin;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ud.ing.modi.entidades.EstadoCliente;
import ud.ing.modi.entidades.PendienteAltaRegistro;
import ud.ing.modi.entidades.PuntoRecarga;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.EstadoClienteMapper;
import ud.ing.modi.mapper.PendienteAltaRegistroMapper;

/**
 *
 * @author Administrador
 */
@ManagedBean
@ViewScoped
public class ConsultaRegistrosPendientesPuntosRecarga {
    private List<PuntoRecarga> puntoRecargaList;
    private PuntoRecarga ptoRecargaSeleccionada;

    /**
     * Creates a new instance of ConsultaRegistrosPendientesPuntosRecarga
     */
    public ConsultaRegistrosPendientesPuntosRecarga() {
        consultarPuntosRecargaPendientesAlta();
    }

    public List<PuntoRecarga> getPuntoRecargaList() {
        return puntoRecargaList;
    }

    public void setPuntoRecargaList(List<PuntoRecarga> puntoRecargaList) {
        this.puntoRecargaList = puntoRecargaList;
    }
    
    public void consultarPuntosRecargaPendientesAlta(){
        PendienteAltaRegistroMapper pMapper = new PendienteAltaRegistroMapper();
        this.puntoRecargaList = pMapper.obtenerPuntosRecargasPendientes();
    }

    public PuntoRecarga getPtoRecargaSeleccionada() {
        return ptoRecargaSeleccionada;
    }

    public void setPtoRecargaSeleccionada(PuntoRecarga ptoRecargaSeleccionada) {
        this.ptoRecargaSeleccionada = ptoRecargaSeleccionada;
    }  
    
        
    public void activarCuenta() throws Exception{
        AccesoLDAP aLDAP = new AccesoLDAP();
        PendienteAltaRegistroMapper pMapper = new PendienteAltaRegistroMapper();
        PendienteAltaRegistro pendiente = pMapper.obtenerPendienteAltaRegistro(ptoRecargaSeleccionada);
        if(pendiente!=null){
            aLDAP.modificarEstadoCuenta(ptoRecargaSeleccionada.getNickname(), AccesoLDAP.CUENTA_ACTIVA);
            pMapper.borrarPendiente(pendiente);
            EstadoClienteMapper eClienteMapper = new EstadoClienteMapper();
            EstadoCliente eCliente = eClienteMapper.obtenerEstadoCliente("2");
            ptoRecargaSeleccionada.setEstadoCliente(eCliente);
            ClienteMapper cMapper = new ClienteMapper();
            cMapper.actualizarCliente(ptoRecargaSeleccionada);
            this.puntoRecargaList.remove(ptoRecargaSeleccionada);
        }
        
    }
    
    public void denegarCuenta(){
        System.out.println("Entro a denegar");
        this.puntoRecargaList.remove(this.ptoRecargaSeleccionada);
    }
}
