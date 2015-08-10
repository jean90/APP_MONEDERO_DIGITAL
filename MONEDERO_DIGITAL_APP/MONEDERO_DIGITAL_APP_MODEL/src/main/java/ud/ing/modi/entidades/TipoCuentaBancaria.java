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
@Table(name="TIPO_CUENTA")
public class TipoCuentaBancaria implements Serializable {
    
    @Id
    @Column (name="COD_TIPO_CUENTA")    
    private String codTipoCuenta;
    @Column (name="DES_TIPO_CUENTA")  
    private String desTipoCuenta;

    public TipoCuentaBancaria() {
    
    }

    public TipoCuentaBancaria(String codTipoCuenta, String desTipoCuenta) {
        this.codTipoCuenta = codTipoCuenta;
        this.desTipoCuenta = desTipoCuenta;
    }

    public String getCodTipoCuenta() {
        return codTipoCuenta;
    }

    public void setCodTipoCuenta(String codTipoCuenta) {
        this.codTipoCuenta = codTipoCuenta;
    }

    public String getDesTipoCuenta() {
        return desTipoCuenta;
    }

    public void setDesTipoCuenta(String desTipoCuenta) {
        this.desTipoCuenta = desTipoCuenta;
    }    
    
    @Override
    public boolean equals(Object object){
                
        if (object == this) return true;
        if (object == null || getClass() != object.getClass()) return false;
        
        TipoCuentaBancaria other = (TipoCuentaBancaria) object;
        if (this.codTipoCuenta == null ? other.codTipoCuenta != null : !this.codTipoCuenta.equals(other.codTipoCuenta)) return false;
        
        if (this.desTipoCuenta == null ? other.desTipoCuenta != null : !this.desTipoCuenta.equals(other.desTipoCuenta)) return false;
                
        return  true;
        
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.codTipoCuenta != null ? this.codTipoCuenta.hashCode() : 0);
        hash = 67 * hash + (this.desTipoCuenta != null ? this.desTipoCuenta.hashCode() : 0);
        return hash;
    }
    
}
