/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ud.ing.modi.entidades.Cliente;
import ud.ing.modi.entidades.EstadoCliente;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.PendienteAltaRegistro;
import ud.ing.modi.entidades.TiendaOnLine;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.EstadoClienteMapper;
import ud.ing.modi.mapper.EstadoMonederoMapper;
import ud.ing.modi.mapper.MonederoMapper;
import ud.ing.modi.mapper.PendienteAltaRegistroMapper;

/**
 *
 * @author Administrador
 */
@ManagedBean
@ViewScoped
public class ConsultaRegistrosPendientesTiendaOnline implements Serializable{
    private List<TiendaOnLine> tiendaPendienteList;
    private TiendaOnLine tiendaPendienteSeleccionada;
    
    private String pasoActivacion;

    public String getPasoActivacion() {
        return pasoActivacion;
    }

    public void setPasoActivacion(String pasoActivacion) {
        this.pasoActivacion = pasoActivacion;
    }
    /**
     * Creates a new instance of ConsultaRegistrosPendientesTiendaOnline
     */
    public ConsultaRegistrosPendientesTiendaOnline() {
        consultarTiendasOnlinePendientesAlta();
        this.pasoActivacion = "";
    }
    
    private void consultarTiendasOnlinePendientesAlta(){
        PendienteAltaRegistroMapper pMapper = new PendienteAltaRegistroMapper();
        this.tiendaPendienteList = pMapper.obtenerTiendasOnlinePendientes();
    }

    public List<TiendaOnLine> getTiendaPendienteList() {
        return tiendaPendienteList;
    }

    public void setTiendaPendienteList(List<TiendaOnLine> tiendaPendienteList) {
        this.tiendaPendienteList = tiendaPendienteList;
    }

    public TiendaOnLine getTiendaPendienteSeleccionada() {
        return tiendaPendienteSeleccionada;
    }

    public void setTiendaPendienteSeleccionada(TiendaOnLine tiendaPendienteSeleccionada) {
        this.tiendaPendienteSeleccionada = tiendaPendienteSeleccionada;
    }
    
    public void activarCuenta() throws Exception{
        AccesoLDAP aLDAP = new AccesoLDAP();
        PendienteAltaRegistroMapper pMapper = new PendienteAltaRegistroMapper();
        PendienteAltaRegistro pendiente = pMapper.obtenerPendienteAltaRegistro(tiendaPendienteSeleccionada);
        if(pendiente!=null){
            aLDAP.modificarEstadoCuenta(tiendaPendienteSeleccionada.getNickname(), AccesoLDAP.CUENTA_ACTIVA);
            pMapper.borrarPendiente(pendiente);
            EstadoClienteMapper eClienteMapper = new EstadoClienteMapper();
            EstadoCliente eCliente = eClienteMapper.obtenerEstadoCliente("2");
            tiendaPendienteSeleccionada.setEstadoCliente(eCliente);
            ClienteMapper cMapper = new ClienteMapper();
            cMapper.actualizarCliente(tiendaPendienteSeleccionada);
            MonederoMapper mMapper = new MonederoMapper();
            mMapper.guardarMonedero(crearMonedero(tiendaPendienteSeleccionada));
            this.tiendaPendienteList.remove(tiendaPendienteSeleccionada);
        }        
    }
    
    public Monedero crearMonedero(TiendaOnLine tienda){
        Monedero monedero = new Monedero();
        Date fechaACtual = new Date();
        monedero.setFechaCreacion(fechaACtual);
        monedero.setClienteDueno(tienda);
        monedero.setDivisa(tienda.getDivisa());
        float saldo = 0;
        monedero.setSaldo(saldo);
        EstadoMonederoMapper eMonederoMapper = new EstadoMonederoMapper();
        monedero.setEstado(eMonederoMapper.obtenerEstadoMonederById("1"));
        return monedero;
    }
    
    public void denegarCuenta(){
        System.out.println("Entro a denegar");
        this.tiendaPendienteList.remove(this.tiendaPendienteSeleccionada);
    }
}
