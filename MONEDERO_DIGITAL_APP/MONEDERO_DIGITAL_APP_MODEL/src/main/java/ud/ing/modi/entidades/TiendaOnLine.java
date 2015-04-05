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



@Entity
@Table(name="TIENDA_ONLINE")
@PrimaryKeyJoinColumn (name = "COD_CLIENTE")
public class TiendaOnLine extends ClienteJuridico implements Serializable{
    
    @Column(name="NUM_CTA_BANCARIA")
    private String numCuentaBancaria;
    @Column(name="TIPO_CTA_BANCARIA")
    private String tipoCuentaBancaria;
    @Column(name="BANCO_CTA")
    private String Banco;

    public TiendaOnLine() {
        
    }  
    
    public TiendaOnLine(String numCuentaBancaria, String tipoCuentaBancaria, String Banco) {
        this.numCuentaBancaria = numCuentaBancaria;
        this.tipoCuentaBancaria = tipoCuentaBancaria;
        this.Banco = Banco;
    }

    public TiendaOnLine(String numCuentaBancaria, String tipoCuentaBancaria, String Banco, int nit, String razonSocial, String direccion, String telefono, Persona representante, int idCliente, Date fechaAlta, EstadoCliente estadoCliente, String nickname) {
        super(nit, razonSocial, direccion, telefono, representante, idCliente, fechaAlta, estadoCliente, nickname);
        this.numCuentaBancaria = numCuentaBancaria;
        this.tipoCuentaBancaria = tipoCuentaBancaria;
        this.Banco = Banco;
    }

    public String getNumCuentaBancaria() {
        return numCuentaBancaria;
    }

    public void setNumCuentaBancaria(String numCuentaBancaria) {
        this.numCuentaBancaria = numCuentaBancaria;
    }

    public String getTipoCuentaBancaria() {
        return tipoCuentaBancaria;
    }

    public void setTipoCuentaBancaria(String tipoCuentaBancaria) {
        this.tipoCuentaBancaria = tipoCuentaBancaria;
    }

    public String getBanco() {
        return Banco;
    }

    public void setBanco(String Banco) {
        this.Banco = Banco;
    }
    
    
    
}
