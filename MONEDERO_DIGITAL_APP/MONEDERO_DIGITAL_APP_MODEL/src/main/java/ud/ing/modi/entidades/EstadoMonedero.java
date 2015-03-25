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
 * @author Lufe
 */
@Entity
@Table(name="ESTADO_MONEDERO")
public class EstadoMonedero implements Serializable{
    @Id
    @Column (name="COD_ESTADO_MON")
    private int codigoEstado;
    @Column (name="DES_ESTADO_MON")
    private String desEstado;

    public EstadoMonedero() {
    }

    public EstadoMonedero(int codigoEstado, String desEstado) {
        this.codigoEstado = codigoEstado;
        this.desEstado = desEstado;
    }

    public int getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(int codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getDesEstado() {
        return desEstado;
    }

    public void setDesEstado(String desEstado) {
        this.desEstado = desEstado;
    }
    
    
    
}
