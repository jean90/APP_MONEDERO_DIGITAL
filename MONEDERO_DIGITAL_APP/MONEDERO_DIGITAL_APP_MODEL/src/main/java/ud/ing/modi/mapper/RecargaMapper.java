/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.PuntoRecarga;
import ud.ing.modi.entidades.Recarga;

/**
 *
 * @author Administrador
 */
public class RecargaMapper extends Mapper{
    
    public void guardarRecarga(Recarga recarga) throws Exception{
        try {
            iniciaOperacion();
            getSesion().save(recarga);
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
     * Este método carga la lista de recargas realizadas a un monedero en específico.
     * @param monedDestino Es el monedero al cual se le realizaron las recargas.
     * @return Retorna como resultado la lista de recargas hechas al monedero.
     */
    public List<Recarga> buscarRecargaPorMonedero(Monedero monedDestino){
        List<Recarga> recargas = null;
        System.out.println("MONEDERO: "+monedDestino);
        try {
            iniciaOperacion();
            recargas= getSesion().createCriteria(Recarga.class).add(Restrictions.eq("monedero",monedDestino)).list();
            System.out.println("Movimientos hallados: "+recargas);
        } finally {
            getSesion().close();
        }
        return recargas;
    }
    
    public List<Recarga> buscarRecargaPorPtoRecarga (PuntoRecarga puntoRecarga){
        List<Recarga> recargas = null;
        System.out.println("Punto de recarga: "+puntoRecarga);
        try {
            iniciaOperacion();
            recargas= getSesion().createCriteria(Recarga.class).add(Restrictions.eq("puntoRecarga",puntoRecarga)).list();
            System.out.println("Movimientos hallados: "+recargas);
        } finally {
            getSesion().close();
        }
        return recargas;
    }
    
}
