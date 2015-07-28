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
public class PendienteAltaRegistroMapper extends Mapper{

    public void guardarPendienteAltaRegistro(PendienteAltaRegistro pendiente) throws Exception {
        try {
            iniciaOperacion();
            getSesion().save(pendiente);
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
    

    public void borrarPendiente(PendienteAltaRegistro pendiente) throws Exception{
        try {            
            iniciaOperacion();
            System.out.println("Eliminando de Pendientes..");
            getSesion().delete(pendiente);
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
