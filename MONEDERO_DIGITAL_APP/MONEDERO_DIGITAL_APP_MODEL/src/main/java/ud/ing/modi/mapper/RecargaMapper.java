/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

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
    
    
    
}
