/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.mapper;

import java.util.List;
import org.hibernate.SQLQuery;
import ud.ing.modi.entidades.TipoCuentaBancaria;

/**
 *
 * @author Administrador
 */
public class TipoCuentaMapper extends Mapper{
    
    public List<TipoCuentaBancaria> obtenerTiposCuentasBancarias(){
        List<TipoCuentaBancaria> listaTiposCuentas = null;
        String query="SELECT * FROM TIPO_CUENTA";
        iniciaOperacion();
        SQLQuery sqlquery = getSesion().createSQLQuery(query);
        sqlquery.addEntity(TipoCuentaBancaria.class);
        listaTiposCuentas=sqlquery.list();
        return listaTiposCuentas;    
    }
    
    public TipoCuentaBancaria obtenerTipoCuentaById(String codTipoCuenta){
        TipoCuentaBancaria tipoCuenta=null;
        iniciaOperacion();
        tipoCuenta = (TipoCuentaBancaria)getSesion().get(TipoCuentaBancaria.class, codTipoCuenta);
        return tipoCuenta;
    }
    
}
