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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name="RECARGA")
public class Recarga implements Serializable{
    
    @Id
    @SequenceGenerator( name = "RECARGA_SEQ", sequenceName = "RECARGA_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="RECARGA_SEQ")
    @Column (name="COD_RECARGA")
    private int codRecarga;
    @Column (name="VALOR")
    private double valorRecarga;
    @Column (name="FECHA_RECARGA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRecarga;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_CLI_PTORECARGA")
    private PuntoRecarga puntoRecarga;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_MON_REC")
    private Monedero monedero;

    public Recarga() {
    }
    
    public int getCodRecarga() {
        return codRecarga;
    }

    public void setCodRecarga(int codRecarga) {
        this.codRecarga = codRecarga;
    }

    public double getValorRecarga() {
        return valorRecarga;
    }

    public void setValorRecarga(double valorRecarga) {
        this.valorRecarga = valorRecarga;
    }

    public Date getFechaRecarga() {
        return fechaRecarga;
    }

    public void setFechaRecarga(Date fechaRecarga) {
        this.fechaRecarga = fechaRecarga;
    }

    public PuntoRecarga getPuntoRecarga() {
        return puntoRecarga;
    }

    public void setPuntoRecarga(PuntoRecarga puntoRecarga) {
        this.puntoRecarga = puntoRecarga;
    }

    public Monedero getMonedero() {
        return monedero;
    }

    public void setMonedero(Monedero monedero) {
        this.monedero = monedero;
    }
}
