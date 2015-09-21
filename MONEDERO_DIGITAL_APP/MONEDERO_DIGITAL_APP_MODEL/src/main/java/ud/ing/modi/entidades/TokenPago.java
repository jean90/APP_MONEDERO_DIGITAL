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
@Table(name="TOKEN_PAGO")
@Inheritance(strategy = InheritanceType.JOINED)
public class TokenPago implements Serializable {
    @Id
    @SequenceGenerator( name = "TOKEN_SEQ", sequenceName = "TOKEN_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="TOKEN_SEQ")
    @Column(name="COD_TOKEN")
    private int codToken;
    @Column(name="COD_PAGO")
    private int codPago;
    @Column(name="TIME_CREACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name="TOKEN")
    private String token;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_ESTADO_TOKEN")
    private EstadoToken estadoToken;

    public TokenPago() {
    }

    public TokenPago(int codToken, int codPago, Date fechaCreacion, String token, EstadoToken estadoToken) {
        this.codToken = codToken;
        this.codPago = codPago;
        this.fechaCreacion = fechaCreacion;
        this.token = token;
        this.estadoToken = estadoToken;
    }

    public int getCodToken() {
        return codToken;
    }

    public void setCodToken(int codToken) {
        this.codToken = codToken;
    }

    public int getCodPago() {
        return codPago;
    }

    public void setCodPago(int codPago) {
        this.codPago = codPago;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public EstadoToken getEstadoToken() {
        return estadoToken;
    }

    public void setEstadoToken(EstadoToken codEstadoToken) {
        this.estadoToken = codEstadoToken;
    }
    
    
}
