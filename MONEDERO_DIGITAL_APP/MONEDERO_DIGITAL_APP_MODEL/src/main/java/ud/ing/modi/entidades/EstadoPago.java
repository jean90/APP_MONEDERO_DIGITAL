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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name="ESTADO_PAGO")
@Inheritance(strategy = InheritanceType.JOINED)
public class EstadoPago implements Serializable{
    @Id
    @Column (name="COD_ESTADO_PAGO")
    private int codEstadoPago;
    @Column (name="DESC_ESTADO_PAGO")
    private String descEstadoPago;

    public EstadoPago() {
    }

    public EstadoPago(int codEstadoPago, String descEstadoPago) {
        this.codEstadoPago = codEstadoPago;
        this.descEstadoPago = descEstadoPago;
    }

    public int getCodEstadoPago() {
        return codEstadoPago;
    }

    public void setCodEstadoPago(int codEstadoPago) {
        this.codEstadoPago = codEstadoPago;
    }

    public String getDescEstadoPago() {
        return descEstadoPago;
    }

    public void setDescEstadoPago(String descEstadoPago) {
        this.descEstadoPago = descEstadoPago;
    }
    
    
}
