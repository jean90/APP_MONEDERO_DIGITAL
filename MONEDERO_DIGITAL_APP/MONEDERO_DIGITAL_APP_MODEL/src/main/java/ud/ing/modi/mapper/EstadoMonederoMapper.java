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
import ud.ing.modi.entidades.EstadoMonedero;

/**
 *
 * @author Lufe
 */
public class EstadoMonederoMapper extends Mapper{

    public List<EstadoMonedero> obtenerEstados() throws HibernateException {
        List<EstadoMonedero> tipoMons = null;
        String query="SELECT * FROM ESTADO_MONEDERO";
        System.out.println("QUERY: "+query);
        try {
            iniciaOperacion();
            SQLQuery sqlquery=getSesion().createSQLQuery(query);
         //   System.out.println("QUERY: "+sesion.createSQLQuery(query).getQueryString());
            sqlquery.addEntity(EstadoMonedero.class);
            tipoMons= sqlquery.list();
        } finally {
            getSesion().close();
        }
        return tipoMons;
    }
}
