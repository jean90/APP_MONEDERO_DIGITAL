/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import ud.ing.modi.entidades.EstadoToken;

/**
 *
 * @author Administrador
 */
public class EstadoTokenMapper extends Mapper{
    
    public EstadoToken obtenerEstadoTokenById(int idEstadoToken){
        int id = idEstadoToken;
        EstadoToken estadoToken = null;
        try {
            iniciaOperacion();
            estadoToken = (EstadoToken) getSesion().get(EstadoToken.class, id);
        } finally {
            getSesion().close();
        }
        return estadoToken;
    }
    
}
