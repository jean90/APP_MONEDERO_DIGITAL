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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    @Column (name="COD_MONEDERO")
    private String codMonedero;
    //@Column (name="COD_DIVISA")
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_DIVISA")
    private Divisa divisa;
    @Column (name="FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Column (name="SALDO")
    private float saldo;
    @Column (name="COD_ESTADO")
    private int codEstado;//este debe cambiar para apuntar al objeto Estado
    //@Column (name="COD_CLIENTE_DUENO")
    //private int codCliente;//este debe cambiar para apuntar al objeto cliente
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_CLIENTE_DUENO")
    private ClienteNatural clienteDueno;

    public Monedero() {
        divisa=new Divisa();
        clienteDueno=new ClienteNatural();
    }

    public Monedero(String codMonedero, Divisa divisa, Date fechaCreacion, float saldo, int codEstado, ClienteNatural clienteDueno) {
        this.codMonedero = codMonedero;
        this.divisa = divisa;
        this.fechaCreacion = fechaCreacion;
        this.saldo = saldo;
        this.codEstado = codEstado;
        //this.codCliente = codCliente;
        this.clienteDueno=clienteDueno;
    }

    public String getCodMonedero() {
        return codMonedero;
    }

    public void setCodMonedero(String codMonedero) {
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

    public int getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(int codEstado) {
        this.codEstado = codEstado;
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
