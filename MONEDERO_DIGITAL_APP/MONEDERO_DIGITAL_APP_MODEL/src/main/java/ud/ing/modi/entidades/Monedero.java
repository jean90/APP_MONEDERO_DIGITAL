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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;


/**
 *
 * @author user
 */
@Entity
@Table(name="MONEDERO")
public class Monedero implements Serializable{
    //OJO: este antes no era serializable, lo puse para poder utilizar la clase al crear un nuevo monedero
    @Id
    @SequenceGenerator( name = "MONEDERO_SEQ", sequenceName = "MONEDERO_SEQ", allocationSize = 1, initialValue = 10001 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="MONEDERO_SEQ")
    @Column (name="COD_MONEDERO")
    private int codMonedero;
    //@Column (name="COD_DIVISA")
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_DIVISA")
    private Divisa divisa;
    @Column (name="FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Column (name="SALDO")
    private float saldo;
    //@Column (name="COD_ESTADO")
    //private int codEstado;//este debe cambiar para apuntar al objeto Estado
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_ESTADO")
    private EstadoMonedero estado;
    //@Column (name="COD_CLIENTE_DUENO")
    //private int codCliente;//este debe cambiar para apuntar al objeto cliente
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_CLIENTE_DUENO")
    private ClienteNatural clienteDueno;

    public Monedero() {
        divisa=new Divisa();
        estado=new EstadoMonedero();
        clienteDueno=new ClienteNatural();
    }

    public Monedero(int codMonedero, Divisa divisa, Date fechaCreacion, float saldo, EstadoMonedero estado, ClienteNatural clienteDueno) {
        this.codMonedero = codMonedero;
        this.divisa = divisa;
        this.fechaCreacion = fechaCreacion;
        this.saldo = saldo;
        //this.codEstado = codEstado;
        this.estado=estado;
        //this.codCliente = codCliente;
        this.clienteDueno=clienteDueno;
    }

    public int getCodMonedero() {
        return codMonedero;
    }

    public void setCodMonedero(int codMonedero) {
        this.codMonedero = codMonedero;
    }

    public Divisa getDivisa() {
        return divisa;
    }

    public void setDivisa(Divisa divisa) {
        this.divisa = divisa;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    /*public int getCodEstado() {
    return codEstado;
    }
    public void setCodEstado(int codEstado) {
    this.codEstado = codEstado;
    }*/
    public EstadoMonedero getEstado() {
        return estado;
    }

    public void setEstado(EstadoMonedero estado) {
        this.estado = estado;
    }
    
    

    /* public int getCodCliente() {
    return codCliente;
    }
    public void setCodCliente(int codCliente) {
    this.codCliente = codCliente;
    }*/
    public ClienteNatural getClienteDueno() {
        return clienteDueno;
    }

    public void setClienteDueno(ClienteNatural clienteDueno) {
        this.clienteDueno = clienteDueno;
    }

    
    
}
