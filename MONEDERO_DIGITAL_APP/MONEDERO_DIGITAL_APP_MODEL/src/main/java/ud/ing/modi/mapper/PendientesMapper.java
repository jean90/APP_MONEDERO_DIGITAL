package ud.ing.modi.mapper;

import org.hibernate.HibernateException;
import ud.ing.modi.entidades.PendienteRegis;


public class PendientesMapper extends Mapper{
    
    public void guardarPendiente(PendienteRegis pendiente) throws Exception {
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
    
    /**
     * Este método busca en la BD en la tabla PENDIENTE_REGIS si el número de la solicitud existe
     * @param codSolic Es el código de la solicitud relacionado en la tabla de pendientes
     * @return Se retorna el objeto de la solicitud pendiente
     * @throws HibernateException 
     */
    public PendienteRegis buscarSolicitud(String codSolic) throws HibernateException {//Se modificó, antes retornaba boolean asociado a return pendiente!=null;
        int codSol = Integer.parseInt(codSolic); 
        //String nick=null;
        PendienteRegis pendiente = null;
        try {
            iniciaOperacion();
            pendiente = (PendienteRegis) getSesion().get(PendienteRegis.class, codSol);
            //nick = pendiente.getNickname();
            //return pendiente!=null;
        } finally {
            getSesion().close();
        }
        return pendiente;
    }
    
    /**
     * Este método carga el registro pendiente de activación
     * @param codSolic Es el código de la solicitud por el cual se filtra la búsqueda
     * @return Retorna el objeto cargado pendiente de registro
     * @throws HibernateException 
     */
    public PendienteRegis cargarPendiente(String codSolic) throws HibernateException {
        int codSol = Integer.parseInt(codSolic); 
        PendienteRegis pendiente = null;
        try {
            iniciaOperacion();
            pendiente = (PendienteRegis) getSesion().get(PendienteRegis.class, codSol);
        } finally {
            getSesion().close();
        }
        return pendiente;
    }
    
    /**
     * Este método borra el registro de la solicitud pendiente de la base de datos
     * @param codPendiente Es el código de la solicitud a borrar de la base de datos
     */
    public void borrarPendiente(String codPendiente) throws Exception{
        try {
            PendienteRegis pendiente=this.cargarPendiente(codPendiente);
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

