/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import ud.ing.modi.entidades.TipoDocumento;


/**
 *
 * @author Lufe
 */
public class DocumentoMapper {
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
    
    public List<TipoDocumento> obtenerDocs() throws HibernateException {
        List<TipoDocumento> tipoDoc = null;
        String query="SELECT * FROM TIPO_DOCUMENTO";
        System.out.println("QUERY: "+query);
        try {
            iniciaOperacion();
            SQLQuery sqlquery=sesion.createSQLQuery(query);
         //   System.out.println("QUERY: "+sesion.createSQLQuery(query).getQueryString());
            sqlquery.addEntity(TipoDocumento.class);
            tipoDoc= sqlquery.list();
        } finally {
            sesion.close();
        }
        return tipoDoc;
    }
    
    
}
