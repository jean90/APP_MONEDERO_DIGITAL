/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import org.hibernate.criterion.Restrictions;
import ud.ing.modi.entidades.Comision;

/**
 *
 * @author Administrador
 */
public class ComisionMapper extends Mapper{
    
    public Comision obtenerComisionbyId(int codComision){
        Comision comision = null;
        try{
        iniciaOperacion();
        comision = (Comision) getSesion().createCriteria(Comision.class).add(Restrictions.eq("codComision", codComision)).uniqueResult();
        }finally{
            getSesion().close();
        }
        return comision;
    }
    
}
