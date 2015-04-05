/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;
import ud.ing.modi.entidades.Cliente;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.entidades.PuntoRecarga;
import ud.ing.modi.entidades.TiendaOnLine;

/**
 *
 * @author Administrador
 */
public class ClienteMapper {
    private static final SessionFactory sessionFactory;
    private Session sesion;
    private Transaction tx;

    static {
        try {
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (HibernateException he) {
            System.err.println("Ocurrió un error en la inicialización de la SessionFactory: " + he);
            throw new ExceptionInInitializerError(he);
        }
    }

    private void iniciaOperacion() throws HibernateException {
        sesion = this.sessionFactory.openSession();
        tx = sesion.beginTransaction();
    }
    
    public Cliente obtenerCliente(String idCliente){
        int id = Integer.parseInt(idCliente);
        Cliente cliente = null;
        try {
            iniciaOperacion();
            cliente = (Cliente) sesion.get(Cliente.class, id);
        } finally {
            sesion.close();
        }
        return cliente;
    }
    
    public ClienteNatural obtenerClienteNatural(String idCliente){
        
        int id = Integer.parseInt(idCliente);
        ClienteNatural cliente = null;
        try {
            iniciaOperacion();
            cliente = (ClienteNatural) sesion.get(ClienteNatural.class, id);
        } finally {
            sesion.close();
        }
        return cliente;
    }
    
    /**
     * Este metodo busca un cliente por el nick que usó para loggearse.
     * @param nick Es el nickname con el que se loggeó el cliente y con el cual se encuentra registrado en el sistema.
     * @return Como resultado retorna el objeto cliente que tiene ese nickname asociado.
     */
    public Cliente buscarPorNick(String nick){
        ClienteNatural cliente = null;

        try {
            iniciaOperacion();
            cliente= (ClienteNatural) sesion.createCriteria(ClienteNatural.class).add(Restrictions.eq("nickname",nick)).uniqueResult();
            System.out.println("Cliente hallado -- "+cliente);

        } finally {
            sesion.close();
        }
        return cliente;
    }
    
    public void guardarCliente(Cliente cliente) throws Exception{
         try {
            iniciaOperacion();
            sesion.save(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sesion.close();
        }
    }
    
    public void guardarClienteJuridico(ClienteJuridico cJuridico) throws Exception{
        try {
            iniciaOperacion();
            sesion.save(cJuridico.getRepresentante());
            sesion.save(cJuridico);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sesion.close();
        }
    }
    
    public void guardarClienteNatural(ClienteNatural cNatural) throws Exception{
        try {
            iniciaOperacion();
            sesion.save(cNatural);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sesion.close();
        }
    }
    
    public void guardarTiendaOnline(TiendaOnLine tiendaOnline) throws Exception{
        try {
            iniciaOperacion();
            sesion.save(tiendaOnline.getRepresentante());
            sesion.save(tiendaOnline);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sesion.close();
        }
    }
    
    
    public void guardarPuntoRecarga(PuntoRecarga puntoRecarga) throws Exception{
        try {
            iniciaOperacion();
            sesion.save(puntoRecarga.getRepresentante());
            sesion.save(puntoRecarga);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sesion.close();
        }
    }
}
