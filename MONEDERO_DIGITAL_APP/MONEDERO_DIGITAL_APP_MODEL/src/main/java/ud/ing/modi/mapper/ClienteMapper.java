/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import ud.ing.modi.entidades.Cliente;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.Persona;

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
    
}
