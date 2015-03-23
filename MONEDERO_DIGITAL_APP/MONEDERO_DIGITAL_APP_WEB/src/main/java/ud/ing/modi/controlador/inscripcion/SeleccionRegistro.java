/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.inscripcion;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Administrador
 */
@ManagedBean(name = "seleccionRegistro")
@RequestScoped
public class SeleccionRegistro {
    private String seleccion;

    public SeleccionRegistro() {
    }

    public String getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(String seleccion) {
        this.seleccion = seleccion;
    }
    
    public String validaSeleccion(){
        System.out.println("INICIO validaSeleccion()");
        String respuesta="";
        if(this.seleccion.equals("Natural")){
            respuesta= "/inscripcion";
        }else if(this.seleccion.equals("Empresa")){
            respuesta= "/inscripcionEmpresa";            
        }
        return respuesta;
    }
}
