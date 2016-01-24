/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import ud.ing.modi.entidades.Cliente;
import ud.ing.modi.entidades.Monedero;


/**
 *
 * @author Lufe
 */
public class MonederoMapper extends Mapper{
    
    public void guardarMonedero(Monedero monedero) throws Exception {
        try {
            iniciaOperacion();
            getSesion().save(monedero);
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
    
    public void actualizarMonedero(Monedero monedero) throws Exception {
        try {
            iniciaOperacion();
            getSesion().update(monedero);
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
    
    /**
     * Este método hace la búsqueda en la base de datos de los monederos pertenecientes a un cliente.
     * @param clienteDueno Es el cliente del cual se buscarán los monederos.
     * @return Como respuesta se retorna la lista de monederos del cliente
     * @throws HibernateException 
     */
    public List<Monedero> listarMonederos(Cliente clienteDueno) throws HibernateException {
        List<Monedero> monederos = null;
       // Query consulta=null;
        //String query="SELECT * FROM MONEDERO";
        //System.out.println("QUERY: "+query);
        try {
            iniciaOperacion();
           // consulta = sesion.createQuery("FROM MONEDERO");
    //        SQLQuery sqlquery=sesion.createSQLQuery(query);
            
                monederos= getSesion().createCriteria(Monedero.class).add(Restrictions.eq("clienteDueno",clienteDueno)).list();
            
         //   System.out.println("QUERY: "+sesion.createSQLQuery(query).getQueryString());
  //          sqlquery.addEntity(Monedero.class);
            //monederos= consulta.list();
  //          monederos= sqlquery.list();
            System.out.println("Monederos hallados: "+monederos);
        } finally {
            getSesion().close();
        }
        return monederos;
    }
    
    /**
     * Este método busca un monedero desde el criterio indicado.
     * @param codMonedero Es el código del monedero que se desea encontrar.
     * @return Retorna como resultado el objeto del monedero que cumple el criterio.
     */
    public Monedero buscarMonedero(String codMonedero){
        int codMon = Integer.parseInt(codMonedero);
        Monedero monedero=null;
        try {
            iniciaOperacion();
            monedero= (Monedero) getSesion().get(Monedero.class, codMon);
            
            System.out.println("Monedero hallado: "+monedero);
        } finally {
            getSesion().close();
        }
        return monedero;
    }
    
    /**
     * Este método halla el monedero que le pertenece al cliente indicado.
     * @param clienteDueno Es el cliente dueño del monedero a buscar.
     * @return Retorna como resultado el monedero del cliente indicado.
     */
    public Monedero buscarPorDueno(Cliente clienteDueno){
        
        Monedero monedero=null;
        try {
            iniciaOperacion();
            monedero= (Monedero) getSesion().createCriteria(Monedero.class).add(Restrictions.eq("clienteDueno",clienteDueno)).uniqueResult();
            
            System.out.println("Monedero hallado: "+monedero);
        } finally {
            getSesion().close();
        }
        return monedero;
    }

    /**
     * Este método permite abonar el valor pago de una compra realizado por un cliente monedero a una tienda.
     * @param valorAbono Es el valor de la compra que está siendo pagada.
     * @param monedero Es el monedero de la tienda a la cual se le abonará el pago.
     * @throws Exception 
     */
    public void abonarPago(float valorAbono, Monedero monedero) throws Exception{
        float newSaldo=monedero.getSaldo()+valorAbono;
        monedero.setSaldo(newSaldo);
        try {
            iniciaOperacion();
            getSesion().update(monedero);
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
    
    /**
     * Este método permite abonar el valor pago de una compra realizado por un cliente monedero a una tienda.
     * @param valorDebito Es el valor de la compra que está siendo pagada.
     * @param monedero Es el monedero del cliente comprador del cual se le debitará el pago.
     * @throws Exception 
     */
    public void debitarPago(float valorDebito, Monedero monedero) throws Exception{
        float newSaldo=monedero.getSaldo()-valorDebito;
        monedero.setSaldo(newSaldo);
        try {
            iniciaOperacion();
            getSesion().update(monedero);
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
    
    public List<Monedero> obtenerMonederosPorAbonar(){
        List<Monedero> listaMonederos = null;
        iniciaOperacion();
        String hqlQueryStr = "select mo"
                + " from Monedero as mo, TiendaOnLine as td"
                + " where mo.saldo > 0"
                + " and mo.clienteDueno = td.idCliente";
        Query hqlQuery = getSesion().createQuery(hqlQueryStr);
        listaMonederos = hqlQuery.list();
        return listaMonederos;
    }
}
