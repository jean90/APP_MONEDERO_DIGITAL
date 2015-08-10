/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.mapper;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.PendienteAltaRegistro;
import ud.ing.modi.entidades.PuntoRecarga;
import ud.ing.modi.entidades.TiendaOnLine;

/**
 *
 * @author Administrador
 */
public class PendienteAltaRegistroMapper extends Mapper{

    public void guardarPendienteAltaRegistro(PendienteAltaRegistro pendiente) throws Exception {
        try {
            iniciaOperacion();
            getSesion().save(pendiente);
            getTx().commit();
        } catch (Exception e) {
            if (getTx() != null) {
                getTx().rollback();
            }
            throw e;
        } finally {
            getSesion().close();
        }
    }
    

    public void borrarPendiente(PendienteAltaRegistro pendiente) throws Exception{
        try {            
            iniciaOperacion();
            System.out.println("Eliminando de Pendientes..");
            getSesion().delete(pendiente);
            getTx().commit();
        } catch (Exception e) {
            if (getTx() != null) {
                getTx().rollback();
            }
            throw e;
        } finally {
            getSesion().close();
        }
    }
    /*
    public int obtenerNumSolicitudesAlta(){
        int respuesta=0;
        iniciaOperacion();
        SQLQuery query = sesion.createSQLQuery("SELECT COUNT(*) AS NUM FROM PENDIENTE_ALTA_REGISTRO");
        query.addScalar("NUM", Hibernate.INTEGER);
        List resustado = query.list();
        
        if(!resustado.isEmpty()){
            respuesta = (Integer)resustado.get(0);
        }
        System.out.println("EL PRIMER QQUERYRESULTO : " + respuesta);
        return respuesta;
    }
    */
    public int obtenerNumSolicitudesAltaTiendaOnline(){
                int respuesta=0;
        iniciaOperacion();
        SQLQuery query = getSesion().createSQLQuery("SELECT COUNT(*) AS NUM FROM PENDIENTE_ALTA_REGISTRO A, TIENDA_ONLINE B WHERE A.COD_CLIENTE = B.COD_CLIENTE");
        query.addScalar("NUM", Hibernate.INTEGER);
        List resustado = query.list();
        
        if(!resustado.isEmpty()){
            respuesta = (Integer)resustado.get(0);
        }
        System.out.println("EL PRIMER QQUERYRESULTO : " + respuesta);
        return respuesta;
    }
    
    public int obtenerNumSolicitudesAltaPuntoRecarga(){
        int respuesta=0;
        iniciaOperacion();
        SQLQuery query = getSesion().createSQLQuery("SELECT COUNT(*) AS NUM FROM PENDIENTE_ALTA_REGISTRO A, PUNTO_RECARGA B WHERE A.COD_CLIENTE = B.COD_CLIENTE");
        query.addScalar("NUM", Hibernate.INTEGER);
        List resustado = query.list();
        
        if(!resustado.isEmpty()){
            respuesta = (Integer)resustado.get(0);
        }
        System.out.println("EL PRIMER QQUERYRESULTO : " + respuesta);
        return respuesta;
    }
    
    
    public List<TiendaOnLine> obtenerTiendasOnlinePendientes(){
        List<TiendaOnLine> resultado = null;
        iniciaOperacion();
        System.out.println("ENTRO A REALIZAR LA CONSULTA DE TIENDAS ONLINE PENDIENTES DE ACTIVACION");
        String queryStr = "SELECT tienda FROM TiendaOnLine tienda "
                + "WHERE tienda.idCliente "
                + "IN (SELECT cliente.idCliente FROM PendienteAltaRegistro pentAlta INNER JOIN pentAlta.cliente cliente)";
        Query query = getSesion().createQuery(queryStr);
        resultado=query.list();
        return resultado;
    }
    
    public List<PuntoRecarga> obtenerPuntosRecargasPendientes(){
        List<PuntoRecarga> resultado = null;
        iniciaOperacion();
        Query query = getSesion().createQuery("SELECT puntoRecarga FROM PuntoRecarga puntoRecarga "
                + "WHERE puntoRecarga.idCliente "
                + "IN (SELECT cliente.idCliente FROM PendienteAltaRegistro pentAlta INNER JOIN pentAlta.cliente cliente)");
        resultado = query.list();
        return resultado;
    }
    
    public PendienteAltaRegistro obtenerPendienteAltaRegistro(ClienteJuridico clienteJuridico){
        PendienteAltaRegistro resultado = null;
        iniciaOperacion();
        String queryStr = "SELECT pentAlta FROM PendienteAltaRegistro pentAlta INNER JOIN pentAlta.cliente cliente "
                + "WHERE cliente.idCliente = :codCLiente";
        Query query = getSesion().createQuery(queryStr).setParameter("codCLiente", clienteJuridico.getIdCliente());
        resultado = (PendienteAltaRegistro) query.uniqueResult();
        return resultado;
    }
    
}