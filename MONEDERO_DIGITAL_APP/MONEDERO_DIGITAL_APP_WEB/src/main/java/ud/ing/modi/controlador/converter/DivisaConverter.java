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
import ud.ing.modi.entidades.Divisa;
import ud.ing.modi.mapper.DivisaMapper;

/**
 *
 * @author Administrador
 */
@FacesConverter("DivisaConverter")
public class DivisaConverter implements Converter{

    private DivisaMapper dMapper;
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.equals("")) {
            return null;
        } else {
            dMapper = new DivisaMapper();
            int idDivisa=Integer.parseInt(value);
            System.out.println("Validando la divisa Seleccionada: " + idDivisa);
            Divisa divisa = dMapper.obtenerDivisaById(idDivisa);   
            System.out.println("El valor de la Divisa seleccionada es: " + divisa.getDesDivisa());
            return divisa;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if(value==null||value.equals("")){
            return "";
        }else{
            String codDivisa=null;
            if(!(value instanceof Divisa)){
                throw new ConverterException("The value is not a valid Divisa: " + value);
            }else{
                codDivisa = Integer.toString(((Divisa)value).getCodigoDivisa());
            }
            return codDivisa;
        }
    }    
}
