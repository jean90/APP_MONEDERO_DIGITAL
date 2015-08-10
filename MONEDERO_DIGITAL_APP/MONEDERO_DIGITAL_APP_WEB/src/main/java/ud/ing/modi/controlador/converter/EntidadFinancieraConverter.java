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
import ud.ing.modi.entidades.EntidadFinanciera;
import ud.ing.modi.mapper.EntidadFinancieraMapper;

/**
 *
 * @author Administrador
 */
@FacesConverter("EntidadFinancieraConverter")
public class EntidadFinancieraConverter implements Converter{

    private EntidadFinancieraMapper eMapper;
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        System.out.println("valor a validar de Entidad Financiera" + value);
        if (value == null || value.equals("")) {
            return null;
        } else {
            eMapper = new EntidadFinancieraMapper();
            EntidadFinanciera entidadFinanciera = eMapper.obtenerEntidadFinancieraById(value);
            System.out.println("descripcion de Entidad Financiera" + entidadFinanciera.getDesEntidad());
            return entidadFinanciera;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if(value==null||value.equals("")){
            return "";
        }else{
            String codEntidadFinaciera=null;
            if(!(value instanceof EntidadFinanciera)){
                throw new ConverterException("The value is not a valid EntidadFinanciera: " + value);     
            }else{
                codEntidadFinaciera = ((EntidadFinanciera)value).getCodEntidad();                
            }
            return codEntidadFinaciera;
        }
    }
    
}
