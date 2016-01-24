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
@Table(name="ESTADO_FACTURA")
@Inheritance(strategy = InheritanceType.JOINED)
public class EstadoFactura implements Serializable{
    
    @Id
    @Column (name="COD_ESTADO_FACTURA")
    private int codEstadoFactura;
    
    @Column (name="DES_ESTADO_FACTURA")
    private String desEstadoFactura;

    public EstadoFactura() {
    }

    public EstadoFactura(int codEstadoFactura, String desEstadoFactura) {
        this.codEstadoFactura = codEstadoFactura;
        this.desEstadoFactura = desEstadoFactura;
    }

    public int getCodEstadoFactura() {
        return codEstadoFactura;
    }

    public void setCodEstadoFactura(int codEstadoFactura) {
        this.codEstadoFactura = codEstadoFactura;
    }

    public String getDesEstadoFactura() {
        return desEstadoFactura;
    }

    public void setDesEstadoFactura(String desEstadoFactura) {
        this.desEstadoFactura = desEstadoFactura;
    }
    
    
    
}
