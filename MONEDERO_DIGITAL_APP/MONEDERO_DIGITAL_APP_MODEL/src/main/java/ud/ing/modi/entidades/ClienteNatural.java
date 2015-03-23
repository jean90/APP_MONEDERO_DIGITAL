/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Lufe
 */
@Entity
@Table(name="CLIENTE_NATURAL")
@PrimaryKeyJoinColumn (name = "COD_CLIENTE")
public class ClienteNatural extends Cliente implements Serializable {
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="ID_PERSONA")
    private Persona persona;

    public ClienteNatural() {
        this.persona = new Persona();
    }

    public ClienteNatural(Persona persona, int idCliente, Date fechaAlta, EstadoCliente estadoCliente) {
        super(idCliente, fechaAlta, estadoCliente);
        this.persona = persona;
    }
    
    public ClienteNatural(Persona persona, Date fechaAlta, EstadoCliente estadoCliente) {
        super(fechaAlta, estadoCliente);
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    
    
}
