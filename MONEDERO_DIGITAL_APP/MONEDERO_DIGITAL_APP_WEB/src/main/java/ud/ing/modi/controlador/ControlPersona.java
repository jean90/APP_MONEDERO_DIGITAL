/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.mapper.PersonMapper;

/**
 *
 * @author Administrador
 */
@ManagedBean (name = "controlPersona")
@ApplicationScoped
public class ControlPersona implements Serializable{

    /**
     * Creates a new instance of ControlPersona
     */
    private Persona persona;  
    private String documento;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
    private String nombre;
    
    public ControlPersona() {
        this.nombre = "qqq";
        PersonMapper aMapper=new PersonMapper();
        this.persona=aMapper.obtenerUsuario("00001");
        this.nombre=persona.getNombre();
        //this.documento=persona.getTipoDocumento().getDesDocumento();
    }
    
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
