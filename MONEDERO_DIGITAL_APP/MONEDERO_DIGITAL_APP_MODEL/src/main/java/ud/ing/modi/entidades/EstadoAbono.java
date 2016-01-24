/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name="ESTADO_CLIENTE")
public class EstadoAbono implements Serializable{
    @Id
    @Column (name="COD_ESTADO_ABONO")
    private int codigoEstadoAbono;
    @Column (name="DES_ESTADO_ABONO")
    private String desEstadoAbono;

    public EstadoAbono() {
    }   
    
    public EstadoAbono(int codigoEstadoAbono, String desEstadoAbono) {
        this.codigoEstadoAbono = codigoEstadoAbono;
        this.desEstadoAbono = desEstadoAbono;
    }

    public int getCodigoEstadoAbono() {
        return codigoEstadoAbono;
    }

    public void setCodigoEstadoAbono(int codigoEstadoAbono) {
        this.codigoEstadoAbono = codigoEstadoAbono;
    }

    public String getDesEstadoAbono() {
        return desEstadoAbono;
    }

    public void setDesEstadoAbono(String desEstadoAbono) {
        this.desEstadoAbono = desEstadoAbono;
    }
    
    
    
}
