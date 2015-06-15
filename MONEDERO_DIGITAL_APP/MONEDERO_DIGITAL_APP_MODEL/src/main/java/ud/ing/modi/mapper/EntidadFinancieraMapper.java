/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import java.util.List;
import org.hibernate.SQLQuery;
import ud.ing.modi.entidades.EntidadFinanciera;

/**
 *
 * @author Administrador
 */
public class EntidadFinancieraMapper extends Mapper{
    
    public List<EntidadFinanciera> obtenerEntidadesFinaciera(){
        List<EntidadFinanciera> listaEntidades = null;
        String query="SELECT * FROM ENTIDAD_FINANCIERA";
        iniciaOperacion();
        SQLQuery sqlquery = getSesion().createSQLQuery(query);
        sqlquery.addEntity(EntidadFinanciera.class);
        listaEntidades=sqlquery.list();
        return listaEntidades;    
    }
    
    public EntidadFinanciera obtenerEntidadFinancieraById(String codEntidad){
        EntidadFinanciera entidadFinanciera=null;
        iniciaOperacion();
        entidadFinanciera = (EntidadFinanciera)getSesion().get(EntidadFinanciera.class, codEntidad);
        return entidadFinanciera;
    }
    
}
