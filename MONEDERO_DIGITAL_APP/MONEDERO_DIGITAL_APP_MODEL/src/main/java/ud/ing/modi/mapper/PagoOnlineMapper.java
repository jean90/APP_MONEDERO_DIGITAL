/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.PagoOnline;


/**
 *
 * @author Lufe
 */
public class PagoOnlineMapper extends Mapper{

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
            pagos= getSesion().createCriteria(PagoOnline.class).add(Restrictions.eq("monOrigen",monedOrigen)).list();
            System.out.println("Movimientos hallados: "+pagos);
        } finally {
            getSesion().close();
        }
        return pagos;
    }
    
    /**
     * Este método trae la lista de los pagos realizados al monedero de la tienda.
     * @param monedDestino Es el monedero al cual desembolsó el/los pago(s).
     * @return Retorna como resultado la lista de pagos realizados.
     * @throws HibernateException 
     */
    public List<PagoOnline> obtenerPagosATienda(Monedero monedDestino) throws HibernateException {
        List<PagoOnline> pagos = null;
        System.out.println("MONEDERO: "+monedDestino);
        try {
            iniciaOperacion();
            pagos= getSesion().createCriteria(PagoOnline.class).add(Restrictions.eq("monDestino",monedDestino)).list();
            System.out.println("Movimientos hallados: "+pagos);
        } finally {
            getSesion().close();
        }
        return pagos;
    }
    
    public void registrarPago(PagoOnline pago) throws Exception{
        try {
            iniciaOperacion();
            getSesion().save(pago);
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
