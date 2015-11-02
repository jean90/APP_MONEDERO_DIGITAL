/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.mapper;

import org.hibernate.criterion.Restrictions;
import ud.ing.modi.entidades.EstadoToken;
import ud.ing.modi.entidades.TokenPago;

/**
 *
 * @author Administrador
 */
public class TokenMapper extends Mapper{
    
    /**
     * Este método guarda un registro de token para pagos.
     * @param token Es el token que será almacenado en la base de datos.
     * @throws Exception 
     */
    public void guardarToken(TokenPago token) throws Exception{
         try {
            iniciaOperacion();
            getSesion().save(token);
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
     * Este método actualiza un registro de token para pagos.
     * @param token Es el token que será almacenado en la base de datos.
     * @throws Exception 
     */
    public void actualizarToken(TokenPago token) throws Exception{
         try {
            iniciaOperacion();
            getSesion().update(token);
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
     * Este método buscar y trae el token asociado a un pago.
     * @param codPago Es el código del pago del cual se buscará el token.
     * @return Retorna como resultado el token asociado al pago buscado.
     */
    public TokenPago cargarToken(int codPago){
        TokenPago token=null;
        try{
            iniciaOperacion();
            token= (TokenPago) getSesion().createCriteria(TokenPago.class).add(Restrictions.eq("codPago",codPago)).uniqueResult();
            System.out.println("Token hallado: "+token);
        } finally {
            getSesion().close();
        }
        return token;
    }  
    
    public TokenPago obtenerTokenPagoById(int codToken){
        TokenPago token=null;
        try{
            iniciaOperacion();
            token= (TokenPago) getSesion().createCriteria(TokenPago.class).add(Restrictions.eq("codToken",codToken)).uniqueResult();
            System.out.println("Token hallado: "+token);
        } finally {
            getSesion().close();
        }
        return token;
    }
    
}
