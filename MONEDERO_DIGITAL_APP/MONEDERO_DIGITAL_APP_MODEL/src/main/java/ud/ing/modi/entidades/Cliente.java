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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name="CLIENTE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente implements Serializable {
    @Id
    @SequenceGenerator( name = "CLIENTE_SEQ", sequenceName = "CLIENTE_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="CLIENTE_SEQ")
    @Column(name="COD_CLIENTE")
    private int idCliente;
    @Column(name="FECHA_ALTA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAlta;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_ESTADO")
    private EstadoCliente estadoCliente;

    public Cliente() {
    }

    public Cliente(int idCliente, Date fechaAlta, EstadoCliente estadoCliente) {
        this.idCliente = idCliente;
        this.fechaAlta = fechaAlta;
        this.estadoCliente = estadoCliente;
    }

    public Cliente(Date fechaAlta, EstadoCliente estadoCliente) {
        this.idCliente = idCliente;
        this.fechaAlta = fechaAlta;
        this.estadoCliente = estadoCliente;
    }
    
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public EstadoCliente getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(EstadoCliente estadoCliente) {
        this.estadoCliente = estadoCliente;
    }
    
    
    
}
