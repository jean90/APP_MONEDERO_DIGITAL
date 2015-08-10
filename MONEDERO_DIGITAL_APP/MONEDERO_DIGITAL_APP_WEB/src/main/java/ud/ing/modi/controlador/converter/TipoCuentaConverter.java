/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.controlador.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import ud.ing.modi.entidades.TipoCuentaBancaria;
import ud.ing.modi.mapper.TipoCuentaMapper;

/**
 *
 * @author Administrador
 */
@FacesConverter("TipoCuentaConverter")
public class TipoCuentaConverter implements Converter {

    private TipoCuentaMapper tMapper;

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.equals("")) {
            System.out.println("El valor a validar es nullo" + value);
            return null;
        } else {
            System.out.println("entro a validad el tipo de cuentacon el codigo: " + value);
            tMapper = new TipoCuentaMapper();
            TipoCuentaBancaria tipoCuenta = tMapper.obtenerTipoCuentaById(value);
            System.out.println("entro a validad el tipo de cuentacon el codigo: " + tipoCuenta.getDesTipoCuenta());
            return tipoCuenta;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if(value==null||value.equals("")){
            return "";
        }else{
            String codTipoCuenta=null;
            if(!(value instanceof TipoCuentaBancaria)){
                throw new ConverterException("The value is not a valid TipoCuentaBancaria: " + value);     
            }else{
                codTipoCuenta = ((TipoCuentaBancaria)value).getCodTipoCuenta();                
            }
            return codTipoCuenta ;
        }
    }

}
