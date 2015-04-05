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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name="PUNTO_RECARGA")
@PrimaryKeyJoinColumn (name = "COD_CLIENTE")
public class PuntoRecarga extends ClienteJuridico implements Serializable{
    @Column(name="SALDO")
    private double saldo;

    public PuntoRecarga() {
       
    }
    
    public PuntoRecarga(double saldo) {
        this.saldo = saldo;
    }

    public PuntoRecarga(double saldo, int nit, String razonSocial, String direccion, String telefono, Persona representante, int idCliente, Date fechaAlta, EstadoCliente estadoCliente, String nickname) {
        super(nit, razonSocial, direccion, telefono, representante, idCliente, fechaAlta, estadoCliente, nickname);
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    
}
