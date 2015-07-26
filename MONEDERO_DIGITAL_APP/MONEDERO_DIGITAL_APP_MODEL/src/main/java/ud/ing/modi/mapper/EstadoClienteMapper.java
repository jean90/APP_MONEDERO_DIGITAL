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
import ud.ing.modi.entidades.EstadoCliente;

/**
 *
 * @author Administrador
 */
public class EstadoClienteMapper extends Mapper{
    
    public EstadoCliente obtenerEstadoCliente(String codigoEstado){
        int id = Integer.parseInt(codigoEstado);
        EstadoCliente estadoCliente = null;
        try {
            iniciaOperacion();
            estadoCliente = (EstadoCliente) getSesion().get(EstadoCliente.class, id);
        } finally {
            getSesion().close();
        }
        return estadoCliente;
    }
   
    
}
