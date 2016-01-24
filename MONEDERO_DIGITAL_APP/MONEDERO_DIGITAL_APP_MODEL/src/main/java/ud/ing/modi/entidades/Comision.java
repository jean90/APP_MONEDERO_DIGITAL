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
@Table (name="COMISION")
public class Comision implements Serializable{
    
    @Id
    @Column (name="COD_COMISION")
    private int codComision;
    
    @Column (name="DES_COMISION")
    private String desComision;
    
    @Column (name="VALOR_COMISION")
    private double valorComision;

    public Comision() {
    }

    public Comision(int codComision, String desComision, float valorComision) {
        this.codComision = codComision;
        this.desComision = desComision;
        this.valorComision = valorComision;
    }

    public int getCodComision() {
        return codComision;
    }

    public void setCodComision(int codComision) {
        this.codComision = codComision;
    }

    public String getDesComision() {
        return desComision;
    }

    public void setDesComision(String desComision) {
        this.desComision = desComision;
    }

    public double getValorComision() {
        return valorComision;
    }

    public void setValorComision(double valorComision) {
        this.valorComision = valorComision;
    }
    
    
}
