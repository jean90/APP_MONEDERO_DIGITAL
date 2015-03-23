package ud.ing.modi.mapper;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import ud.ing.modi.entidades.PendienteRegis;


public class PendientesMapper {
    
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
    
    public void guardarPendiente(PendienteRegis pendiente) throws Exception {
        try {
            iniciaOperacion();
            sesion.save(pendiente);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sesion.close();
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
            pendiente = (PendienteRegis) sesion.get(PendienteRegis.class, codSol);
            //nick = pendiente.getNickname();
            //return pendiente!=null;
        } finally {
            sesion.close();
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
            pendiente = (PendienteRegis) sesion.get(PendienteRegis.class, codSol);
        } finally {
            sesion.close();
        }
        return pendiente;
    }
    
    /**
     * Este método borra el registro de la solicitud pendiente de la base de datos
     * @param pendiente Es el código de la solicitud a borrar de la base de datos
     */
    public void borrarPendiente(String codPendiente) throws Exception{
        try {
            PendienteRegis pendiente=this.cargarPendiente(codPendiente);
            iniciaOperacion();
            System.out.println("Eliminando de Pendientes..");
            sesion.delete(pendiente);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sesion.close();
        }
    }
    
}

