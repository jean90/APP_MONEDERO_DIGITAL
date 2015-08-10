/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import ud.ing.modi.mapper.PendienteAltaRegistroMapper;

/**
 *
 * @author Administrador
 */
@ManagedBean
@RequestScoped
public class ControlResumenTareasAdminisrativas {

    private int solicitudesAlta;
    private int solicitudesAltaTiendas;
    private int solicitudesAltaPuntos;
    
    /**
     * Creates a new instance of ControlResumenTareasAdminisrativas
     */
    public ControlResumenTareasAdminisrativas() {
        PendienteAltaRegistroMapper pMapper = new PendienteAltaRegistroMapper();
        solicitudesAltaTiendas = pMapper.obtenerNumSolicitudesAltaTiendaOnline();
        solicitudesAltaPuntos = pMapper.obtenerNumSolicitudesAltaPuntoRecarga();
        solicitudesAlta = solicitudesAltaTiendas + solicitudesAltaPuntos;
    }

    public int getSolicitudesAlta() {
        return solicitudesAlta;
    }

    public void setSolicitudesAlta(int solicitudesAlta) {
        this.solicitudesAlta = solicitudesAlta;
    }

    public int getSolicitudesAltaTiendas() {
        return solicitudesAltaTiendas;
    }

    public void setSolicitudesAltaTiendas(int solicitudesAltaTiendas) {
        this.solicitudesAltaTiendas = solicitudesAltaTiendas;
    }

    public int getSolicitudesAltaPuntos() {
        return solicitudesAltaPuntos;
    }

    public void setSolicitudesAltaPuntos(int solicitudesAltaPuntos) {
        this.solicitudesAltaPuntos = solicitudesAltaPuntos;
    }
    
    
}
