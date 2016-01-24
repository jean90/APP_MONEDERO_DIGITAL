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
import static javax.persistence.GenerationType.SEQUENCE;
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
@Table(name="PERSONA")
public class AbonoCuenta implements Serializable{
    
    @Id
    @SequenceGenerator( name = "ABONO_CUENTA_SEQ", sequenceName = "ABONO_CUENTA_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy=SEQUENCE, generator ="ABONO_CUENTA_SEQ")
    @Column(name="COD_ABONO_CTA")
    private int codAbonoCta;
    
    @Column(name="FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    
    @Column(name="FECHA_PROCESO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaProceso;

    @Column (name="VALOR_NETO_MONEDERO")
    private double valorNetoMonedero;
    
    @Column (name="VALOR_CALCULO_COMISION")
    private double valorCalculoComision;
    
    @Column (name="VALOR_TOTAL_ABONO")
    private double valorTotalAbono;
    
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_MONEDERO")
    private Monedero monedero;
    
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_ESTADO_ABONO")
    private EstadoAbono estadoAbono;
    
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_COMISION")
    private Comision comision;

    public AbonoCuenta() {
    }

    public AbonoCuenta(int codAbonoCta, Date fechaCreacion, Date fechaProceso, double valorNetoMonedero, double valorCalculoComision, double valorTotalAbono, Monedero monedero, EstadoAbono estadoAbono, Comision comision) {
        this.codAbonoCta = codAbonoCta;
        this.fechaCreacion = fechaCreacion;
        this.fechaProceso = fechaProceso;
        this.valorNetoMonedero = valorNetoMonedero;
        this.valorCalculoComision = valorCalculoComision;
        this.valorTotalAbono = valorTotalAbono;
        this.monedero = monedero;
        this.estadoAbono = estadoAbono;
        this.comision = comision;
    }

    public int getCodAbonoCta() {
        return codAbonoCta;
    }

    public void setCodAbonoCta(int codAbonoCta) {
        this.codAbonoCta = codAbonoCta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public double getValorNetoMonedero() {
        return valorNetoMonedero;
    }

    public void setValorNetoMonedero(double valorNetoMonedero) {
        this.valorNetoMonedero = valorNetoMonedero;
    }

    public double getValorCalculoComision() {
        return valorCalculoComision;
    }

    public void setValorCalculoComision(double valorCalculoComision) {
        this.valorCalculoComision = valorCalculoComision;
    }

    public double getValorTotalAbono() {
        return valorTotalAbono;
    }

    public void setValorTotalAbono(double valorTotalAbono) {
        this.valorTotalAbono = valorTotalAbono;
    }

    public Monedero getMonedero() {
        return monedero;
    }

    public void setMonedero(Monedero monedero) {
        this.monedero = monedero;
    }

    public EstadoAbono getEstadoAbono() {
        return estadoAbono;
    }

    public void setEstadoAbono(EstadoAbono estadoAbono) {
        this.estadoAbono = estadoAbono;
    }

    public Comision getComision() {
        return comision;
    }

    public void setComision(Comision comision) {
        this.comision = comision;
    }
    
    
}
