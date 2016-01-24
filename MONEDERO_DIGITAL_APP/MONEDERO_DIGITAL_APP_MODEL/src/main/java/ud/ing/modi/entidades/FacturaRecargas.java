/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Administrador
 */

@Entity
@Table (name="FACTURA_RECARGAS")
public class FacturaRecargas {
    
    @Id
    @SequenceGenerator( name = "FACTURA_RECARGAS_SEQ", sequenceName = "FACTURA_RECARGAS_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="FACTURA_RECARGAS_SEQ")
    @Column (name="COD_FACTURA")
    private int codFactura;
    
    @Column (name="FECHA_CORTE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCorte;
    
    @Column (name="FECHA_VENCIMIENTO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaVencimiento;
    
    @Column (name="FECHA_PAGO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPago;
    
    @Column (name="NUM_TOTAL_RECRAGAS")
    private int numTotalRecargas;
    
    @Column (name="VALOR_TOTAL_RECARGAS")
    private double valorTotalRecargas;
    
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_COMISION")
    private Comision comision;
    
    @Column (name="VALOR_CALCULO_COMISION")
    private double valorCalculoComision;
    
    @Column (name="VALOR_TOTAL_PAGO")
    private double valorTotalPago;
    
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_CLIENTE")
    private PuntoRecarga puntoRecarga;
    
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_ESTADO_FACTURA")
    private EstadoFactura estadoFactura;

    @OneToMany
    @JoinColumn(name="COD_FACTURA", referencedColumnName = "")
    
    private List<Recarga> recargas;
    
    public FacturaRecargas() {
    }

    public FacturaRecargas(int codFactura, Date fechaCorte, Date fechaVencimiento, Date fechaPago, int numTotalRecargas, float valorTotalRecargas, Comision comision, float valorCalculoComision, float valorTotalPago, PuntoRecarga puntoRecarga, EstadoFactura estadoFactura) {
        this.codFactura = codFactura;
        this.fechaCorte = fechaCorte;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaPago = fechaPago;
        this.numTotalRecargas = numTotalRecargas;
        this.valorTotalRecargas = valorTotalRecargas;
        this.comision = comision;
        this.valorCalculoComision = valorCalculoComision;
        this.valorTotalPago = valorTotalPago;
        this.puntoRecarga = puntoRecarga;
        this.estadoFactura = estadoFactura;
    }

    public int getCodFactura() {
        return codFactura;
    }

    public void setCodFactura(int codFactura) {
        this.codFactura = codFactura;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getNumTotalRecargas() {
        return numTotalRecargas;
    }

    public void setNumTotalRecargas(int numTotalRecargas) {
        this.numTotalRecargas = numTotalRecargas;
    }

    public double getValorTotalRecargas() {
        return valorTotalRecargas;
    }

    public void setValorTotalRecargas(double valorTotalRecargas) {
        this.valorTotalRecargas = valorTotalRecargas;
    }

    public Comision getComision() {
        return comision;
    }

    public void setComision(Comision comision) {
        this.comision = comision;
    }

    public double getValorCalculoComision() {
        return valorCalculoComision;
    }

    public void setValorCalculoComision(double valorCalculoComision) {
        this.valorCalculoComision = valorCalculoComision;
    }

    public double getValorTotalPago() {
        return valorTotalPago;
    }

    public void setValorTotalPago(double valorTotalPago) {
        this.valorTotalPago = valorTotalPago;
    }

    public PuntoRecarga getPuntoRecarga() {
        return puntoRecarga;
    }

    public void setPuntoRecarga(PuntoRecarga puntoRecarga) {
        this.puntoRecarga = puntoRecarga;
    }

    public EstadoFactura getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(EstadoFactura estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public List<Recarga> getRecargas() {
        return recargas;
    }

    public void setRecargas(List<Recarga> recargas) {
        this.recargas = recargas;
    }
    
    
        
}
