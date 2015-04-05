/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.PagoOnline;


/**
 *
 * @author Lufe
 */
public class PagoOnlineMapper {
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

    /**
     * Este método trae la lista de los pagos realizados por el cliente utilizando el monedero seleccionado.
     * @param monedOrigen Es el monedero desde el cual desembolsó el pago.
     * @return Retorna como resultado la lista de pagos realizados.
     * @throws HibernateException 
     */
    public List<PagoOnline> obtenerPagos(Monedero monedOrigen) throws HibernateException {
        List<PagoOnline> pagos = null;
        System.out.println("MONEDERO: "+monedOrigen);
        try {
            iniciaOperacion();
            pagos= sesion.createCriteria(PagoOnline.class).add(Restrictions.eq("monOrigen",monedOrigen)).list();
            System.out.println("Movimientos hallados: "+pagos);
        } finally {
            sesion.close();
        }
        return pagos;
    }
}
