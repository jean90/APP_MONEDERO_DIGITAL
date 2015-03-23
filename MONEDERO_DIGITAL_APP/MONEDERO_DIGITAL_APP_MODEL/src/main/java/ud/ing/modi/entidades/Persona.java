/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PERSONA")
public class Persona implements Serializable{
    @Id
    @SequenceGenerator( name = "PERSONA_SEQ", sequenceName = "PERSONA_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy=SEQUENCE, generator ="PERSONA_SEQ")
    @Column(name="ID_PERSONA")    
    private int idPersona;
    @Column(name="NUM_DOCUMENTO")
    private String numDocumento;
    @Column(name="NOMBRE")
    private String nombre;
    @Column(name="APELLIDO")
    private String apellido;
    @Column(name="NUM_TELFIJO")
    private String numTelFijo;
    @Column(name="NUM_CELULAR")
    private String numCelular;
    @Column(name="DIRECCION")
    private String direccion;
    @Column(name="EMAIL")
    private String email;
    //@Column(name="COD_TIPODOC")
    //private int tipoDocumento;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_TIPODOC")
    private TipoDocumento tipoDocumento;

    public Persona() {
        tipoDocumento=new TipoDocumento();
        //tipoDocumento=0;
    }

    public Persona(int idPersona, String numDocumento, String nombre, String apellido, String numTelFijo, String numCelular, String direccion, String email, TipoDocumento tipoDocumento) {
        this.idPersona = idPersona;
        this.numDocumento = numDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numTelFijo = numTelFijo;
        this.numCelular = numCelular;
        this.direccion = direccion;
        this.email = email;
        this.tipoDocumento = tipoDocumento;
    }

    
    
    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumTelFijo() {
        return numTelFijo;
    }

    public void setNumTelFijo(String numTelFijo) {
        this.numTelFijo = numTelFijo;
    }

    public String getNumCelular() {
        return numCelular;
    }

    public void setNumCelular(String numCelular) {
        this.numCelular = numCelular;
    }

    public TipoDocumento getTipoDocumento() {
    return tipoDocumento;
    }
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
    }
    /*public int getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(int tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }*/

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
