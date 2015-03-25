/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.Monedero;


/**
 *
 * @author Lufe
 */
public class MonederoMapper {
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
    
    public void guardarMonedero(Monedero monedero) throws Exception {
        try {
            iniciaOperacion();
            sesion.save(monedero);
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
    
    public void actualizarMonedero(Monedero monedero) throws Exception {
        try {
            iniciaOperacion();
            sesion.update(monedero);
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
    
    /**
     * Este método hace la búsqueda en la base de datos de los monederos pertenecientes a un cliente.
     * @param clienteDueno Es el cliente del cual se buscarán los monederos.
     * @return Como respuesta se retorna la lista de monederos del cliente
     * @throws HibernateException 
     */
    public List<Monedero> listarMonederos(ClienteNatural clienteDueno) throws HibernateException {
        List<Monedero> monederos = null;
       // Query consulta=null;
        //String query="SELECT * FROM MONEDERO";
        //System.out.println("QUERY: "+query);
        try {
            iniciaOperacion();
           // consulta = sesion.createQuery("FROM MONEDERO");
    //        SQLQuery sqlquery=sesion.createSQLQuery(query);
            monederos= sesion.createCriteria(Monedero.class).add(Restrictions.eq("clienteDueno",clienteDueno)).list();
         //   System.out.println("QUERY: "+sesion.createSQLQuery(query).getQueryString());
  //          sqlquery.addEntity(Monedero.class);
            //monederos= consulta.list();
  //          monederos= sqlquery.list();
            System.out.println("Monederos hallados: "+monederos);
        } finally {
            sesion.close();
        }
        return monederos;
    }
    
    
}
