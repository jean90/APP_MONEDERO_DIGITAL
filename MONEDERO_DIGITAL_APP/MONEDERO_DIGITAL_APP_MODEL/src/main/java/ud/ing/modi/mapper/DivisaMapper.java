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
import ud.ing.modi.entidades.Divisa;


/**
 *
 * @author Lufe
 */
public class DivisaMapper extends Mapper{

    public List<Divisa> obtenerDivisas() throws HibernateException {
        List<Divisa> tipoDivs = null;
        String query="SELECT * FROM DIVISA";
        System.out.println("QUERY: "+query);
        try {
            iniciaOperacion();
            SQLQuery sqlquery=getSesion().createSQLQuery(query);
         //   System.out.println("QUERY: "+sesion.createSQLQuery(query).getQueryString());
            sqlquery.addEntity(Divisa.class);
            tipoDivs= sqlquery.list();
        } finally {
            getSesion().close();
        }
        return tipoDivs;
    }
    public Divisa obtenerDivisaById(int codDivisa){
        Divisa divisa=null;
        iniciaOperacion();
        divisa = (Divisa)getSesion().get(Divisa.class, codDivisa);
        return divisa;
    }
}