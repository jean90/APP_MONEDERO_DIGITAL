/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;


import org.hibernate.criterion.Restrictions;
import ud.ing.modi.entidades.Cliente;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.PuntoRecarga;
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
}
