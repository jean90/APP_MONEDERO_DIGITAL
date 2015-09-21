/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.mapper;

import org.hibernate.HibernateException;
import ud.ing.modi.entidades.Persona;

/**
 *
 * @author Administrador
 */
public class PersonMapper extends Mapper{

    public Persona obtenerUsuario(String idUsuario) throws HibernateException {
        int id = Integer.parseInt(idUsuario);
        Persona persona = null;
        try {
            iniciaOperacion();
            persona = (Persona) getSesion().get(Persona.class, id);
        } finally {
            getSesion().close();
        }
        return persona;
    }

    public void guardarPersona(Persona persona) throws Exception {
        try {
            iniciaOperacion();
            getSesion().save(persona);
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
     * Este método actualiza una persona en el momento en que el cliente natural decidió actualizar sus datos.
     * @param persona Es la persona que se va a actualizar.
     * @throws Exception 
     */
    public void actualizarPersona(Persona persona) throws Exception {
        try {
            iniciaOperacion();
            getSesion().update(persona);
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
