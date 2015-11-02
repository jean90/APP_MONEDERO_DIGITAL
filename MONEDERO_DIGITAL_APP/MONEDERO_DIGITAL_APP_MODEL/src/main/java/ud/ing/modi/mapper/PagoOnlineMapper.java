/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import ud.ing.modi.entidades.EstadoPago;
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
    public List<PagoOnline> obtenerPagos(Monedero monedOrigen, EstadoPago estadoPago) throws HibernateException {
        List<PagoOnline> pagos = null;
        System.out.println("MONEDERO: "+monedOrigen);
        try {
            iniciaOperacion();
            pagos= getSesion().createCriteria(PagoOnline.class).add(Restrictions.eq("monOrigen",monedOrigen)).add(Restrictions.eq("estadoPago",estadoPago)).list();
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
    public List<PagoOnline> obtenerPagosATienda(Monedero monedDestino, EstadoPago estadoPago) throws HibernateException {
        List<PagoOnline> pagos = null;
        System.out.println("MONEDERO: "+monedDestino);
        try {
            iniciaOperacion();
            pagos= getSesion().createCriteria(PagoOnline.class).add(Restrictions.eq("monDestino",monedDestino)).add(Restrictions.eq("estadoPago",estadoPago)).list();
            System.out.println("Movimientos hallados: "+pagos);
        } finally {
            getSesion().close();
        }
        return pagos;
    }
    
    /**
     * Este método registra en la base de datos un pago.
     * @param pago Es el pago a guardar en la base de datos.
     * @throws Exception 
     */
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
    
    /**
     * Este método actualiza en la base de datos un pago.
     * @param pago Es el pago a actualizar en la base de datos.
     * @throws Exception 
     */
    public void actualizarPago(PagoOnline pago) throws Exception{
        try {
            iniciaOperacion();
            getSesion().update(pago);
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
    
    
    public PagoOnline buscarPagoOnlinePendienteById(int codPago){
        PagoOnline pago=null;
        try {
            iniciaOperacion();
            pago= (PagoOnline) getSesion().createCriteria(PagoOnline.class).add(Restrictions.eq("estadoPago",new EstadoPago(1,"PENDIENTE"))).add(Restrictions.eq("codPago",codPago)).uniqueResult();
            System.out.println("Pago hallado: "+pago);
        } finally {
            getSesion().close();
        }
        return pago;
    }
    
    /**
     * Busca la información de un pago online asociado a un código de compra.
     * @param codCompra Es el código de la compra que la tienda asignó en el proceso de pago.
     * @param codPago 
     * @return Retorna como resultado el pago asociado al código de la compra.
     */
    public PagoOnline buscarPagoDeCompra(String codCompra, int codPago){
        PagoOnline pago=null;
        try {
            iniciaOperacion();
            pago= (PagoOnline) getSesion().createCriteria(PagoOnline.class).add(Restrictions.eq("codCompra",codCompra)).add(Restrictions.eq("estadoPago",new EstadoPago(1,"PENDIENTE"))).add(Restrictions.eq("codPago",codPago)).uniqueResult();
            System.out.println("Pago hallado: "+pago);
        } finally {
            getSesion().close();
        }
        return pago;
    }
    
    /**
     * Busca si existen pagos online asociados a un código de compra.
     * @param codCompra Es el código de la compra que la tienda asignó en el proceso de pago.
     * @return Retorna como resultado la lista de pagos asociados al código de compra.
     */
    public List<PagoOnline> listarPagosPtesdeCompra(String codCompra, Monedero monederoTienda){
        
        List<PagoOnline> pagos=null;
        try {
            iniciaOperacion();
            pagos= getSesion().createCriteria(PagoOnline.class).add(Restrictions.eq("codCompra",codCompra)).add(Restrictions.eq("estadoPago",new EstadoPago(1,"PENDIENTE"))).add(Restrictions.eq("monDestino",monederoTienda)).list();
            /*if (!pagos.isEmpty()) {
                existe=true;
            }*/
            System.out.println("Pagos activos hallados: "+pagos);
        } finally {
            getSesion().close();
        }
        return pagos;
    }
    
    /**
     * Busca si existen pagos online asociados a un código de compra que ya estén en estado aprobado.
     * @param codCompra Es el código de la compra que la tienda asignó en el proceso de pago.
     * @return Retorna como resultado la lista de pagos asociados al código de compra.
     */
    public List<PagoOnline> listarPagosFinalizadosdeCompra(String codCompra, Monedero monederoTienda){
        
        List<PagoOnline> pagos=null;
        try {
            iniciaOperacion();
            pagos= getSesion().createCriteria(PagoOnline.class).add(Restrictions.eq("codCompra",codCompra)).add(Restrictions.eq("estadoPago",new EstadoPago(2,"APROBADO"))).add(Restrictions.eq("monDestino",monederoTienda)).list();
            /*if (!pagos.isEmpty()) {
                existe=true;
            }*/
            System.out.println("Pagos finalizados hallados: "+pagos);
        } finally {
            getSesion().close();
        }
        return pagos;
    }
    
    /**
     * Este método cancela los pagos indicados.
     * @param pagos Es la lista de pagos a cancelar
     */
    public void cancelarPagos(List<PagoOnline> pagos){

        try {
            iniciaOperacion();
            for (int i = 0; i < pagos.size(); i++) {
                PagoOnline pago=pagos.get(i);
                pago.setEstadoPago(new EstadoPago(4,"CANCELADO"));
                getSesion().update(pago);
            }
            getTx().commit();
            System.out.println("Pagos borrados");
        } finally {
            getSesion().close();
        }
    }
    
}
