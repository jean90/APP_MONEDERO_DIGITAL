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
import ud.ing.modi.entidades.TiendaOnLine;

/**
 *
 * @author Lufe
 */
public class TiendaMapper extends Mapper{
    
    
    /**
     * Este método actualiza una persona en el momento en que el cliente natural decidió actualizar sus datos.
     * @param persona Es la persona que se va a actualizar.
     * @throws Exception 
     */
    public void actualizarTienda(TiendaOnLine tienda) throws Exception {
        try {
            iniciaOperacion();
            getSesion().update(tienda);
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
