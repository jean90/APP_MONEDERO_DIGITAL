/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name="CLIENTE_JURIDICO")
@PrimaryKeyJoinColumn (name = "COD_CLIENTE")
public class ClienteJuridico extends Cliente implements Serializable{
    @Column(name="NUM_NIT")
    private int nit;
    @Column(name="RAZON_SOCIAL")
    private String razonSocial;
    @Column(name="DIRECCION_EMPRESA")
    private String direccion;
    @Column(name="TELEFONO_EMPRESA")
    private String telefono;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="ID_PERSONA_REPRESENTANTE")
    private Persona representante;

    public ClienteJuridico() {
        super();
    }

    public ClienteJuridico(int nit, String razonSocial, String direccion, String telefono, Persona representante, int idCliente, Date fechaAlta, EstadoCliente estadoCliente, String nickname) {
        super(fechaAlta, estadoCliente, nickname);
        this.nit = nit;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.representante = representante;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Persona getRepresentante() {
        return representante;
    }

    public void setRepresentante(Persona representante) {
        this.representante = representante;
    }
    
    
    
}
