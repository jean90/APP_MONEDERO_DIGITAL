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
import ud.ing.modi.entidades.PendienteAltaRegistro;
import ud.ing.modi.entidades.PendienteRegis;

/**
 *
 * @author Administrador
 */
public class PendienteAltaRegistroMapper {

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

    public void guardarPendienteAltaRegistro(PendienteAltaRegistro pendiente) throws Exception {
        try {
            iniciaOperacion();
            sesion.save(pendiente);
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
    

    public void borrarPendiente(PendienteAltaRegistro pendiente) throws Exception{
        try {            
            iniciaOperacion();
            System.out.println("Eliminando de Pendientes..");
            sesion.delete(pendiente);
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
