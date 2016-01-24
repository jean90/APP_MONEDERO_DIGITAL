/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import org.hibernate.criterion.Restrictions;

import ud.ing.modi.entidades.Cliente;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.PuntoRecarga;
import ud.ing.modi.entidades.Recarga;
import ud.ing.modi.entidades.TiendaOnLine;

/**
 *
 * @author Administrador
 */
public class ClienteMapper extends Mapper{
    
    public Cliente obtenerCliente(String idCliente){
        int id = Integer.parseInt(idCliente);
        Cliente cliente = null;
        try {
            iniciaOperacion();
            cliente = (Cliente) getSesion().get(Cliente.class, id);
        } finally {
            getSesion().close();
        }
        return cliente;
    }
    
    public ClienteNatural obtenerClienteNatural(String idCliente){
        
        int id = Integer.parseInt(idCliente);
        ClienteNatural cliente = null;
        try {
            iniciaOperacion();
            cliente = (ClienteNatural) getSesion().get(ClienteNatural.class, id);
        } finally {
            getSesion().close();
        }
        return cliente;
    }
    
    /**
     * Este metodo busca un cliente por el nick que usó para loggearse.
     * @param nick Es el nickname con el que se loggeó el cliente y con el cual se encuentra registrado en el sistema.
     * @return Como resultado retorna el objeto cliente que tiene ese nickname asociado.
     */
    public Cliente buscarPorNick(String nick){
        Cliente cliente = null;

        try {
            iniciaOperacion();
            cliente= (Cliente) getSesion().createCriteria(Cliente.class).add(Restrictions.eq("nickname",nick)).uniqueResult();
            System.out.println("Cliente hallado -- "+cliente);

        } finally {
            getSesion().close();
        }
        return cliente;
    }
    
    public PuntoRecarga buscarPtoRecargaPorNick(String nick){
        PuntoRecarga cliente = null;

        try {
            iniciaOperacion();
            cliente= (PuntoRecarga) getSesion().createCriteria(PuntoRecarga.class).add(Restrictions.eq("nickname",nick)).uniqueResult();
            System.out.println("Cliente hallado -- "+cliente);

        } finally {
            getSesion().close();
        }
        return cliente;
    }
    
    public void guardarCliente(Cliente cliente) throws Exception{
         try {
            iniciaOperacion();
            getSesion().save(cliente);
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
    
    public void guardarClienteJuridico(ClienteJuridico cJuridico) throws Exception{
        try {
            iniciaOperacion();
            getSesion().save(cJuridico.getRepresentante());
            getSesion().save(cJuridico);
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
    
    public void guardarClienteNatural(ClienteNatural cNatural) throws Exception{
        try {
            iniciaOperacion();
            getSesion().save(cNatural);
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
    
    public void guardarTiendaOnline(TiendaOnLine tiendaOnline) throws Exception{
        try {
            iniciaOperacion();
            getSesion().save(tiendaOnline.getRepresentante());
            getSesion().save(tiendaOnline);
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
    
    
    public void guardarPuntoRecarga(PuntoRecarga puntoRecarga) throws Exception{
        try {
            iniciaOperacion();
            getSesion().save(puntoRecarga.getRepresentante());
            getSesion().save(puntoRecarga);
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
    
    public void actualizarCliente(Cliente cliente) throws Exception {
        try {
            iniciaOperacion();
            getSesion().update(cliente);
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
    
    public List<PuntoRecarga> obtenerPtosRecargaPorFacturar(Date fechaInicial, Date fechaFinal) throws Exception{
        List<PuntoRecarga> listaResultado = null;
        try {
            iniciaOperacion();
            //String query = "SELECT * FROM PUNTO_RECARGA WHERE PUNTO_RECARGA.COD_CLIENTE IN (SELECT DISTINCT RECARGA.COD_CLI_PTORECARGA FROM RECARGA WHERE RECARGA.FECHA_RECARGA BETWEEN ? AND <= ?)";
            String hqlQueryStr = "from PuntoRecarga as pt "
                    + "where pt.idCliente in "
                    + "(select distinct r.puntoRecarga from Recarga as r where r.fechaRecarga between ? and ?)";
            
            Query hqlQuery = getSesion().createQuery(hqlQueryStr);
            hqlQuery.setDate(0, fechaInicial);
            hqlQuery.setDate(1, fechaFinal);
            //String sqlQueryStr = "SELECT * FROM PUNTO_RECARGA WHERE PUNTO_RECARGA.COD_CLIENTE IN (SELECT DISTINCT RECARGA.COD_CLI_PTORECARGA FROM RECARGA)";
            
            //SQLQuery sqlQuery = getSesion().createSQLQuery(sqlQueryStr);
            //sqlQuery.addEntity("PUNTO_RECARGA", PuntoRecarga.class);
            //sqlQuery.addEntity("RECARGA", Recarga.class);
            System.out.println("Fecha inicial: " +fechaInicial.toString() );
            System.out.println("Fecha final: " +fechaFinal.toString() );
            //sqlQuery.setDate(0, fechaInicial);
            //sqlQuery.setDate(1, fechaFinal);
            //System.out.println("QUERY A EJECUTAR: " + sqlQuery.getQueryString());
            //listaResultado = sqlQuery.list();
            listaResultado = hqlQuery.list();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally{
            getSesion().close();
        }
        return listaResultado;    
    }
}
