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
@Table(name="ENTIDAD_FINANCIERA")
public class EntidadFinanciera implements Serializable {
    @Id
    @Column (name="COD_ENTIDAD")
    private String codEntidad;
    @Column (name="DES_ENTIDAD")
    private String desEntidad;

    public EntidadFinanciera() {
    
    }
    
    public EntidadFinanciera(String codEntidad, String desEntidad) {
        this.codEntidad = codEntidad;
        this.desEntidad = desEntidad;
    }   
    
    public String getCodEntidad() {
        return codEntidad;
    }

    public void setCodEntidad(String codEntidad) {
        this.codEntidad = codEntidad;
    }

    public String getDesEntidad() {
        return desEntidad;
    }

    public void setDesEntidad(String desEntidad) {
        this.desEntidad = desEntidad;
    }

     @Override
    public boolean equals(Object object){
                
        if (object == this) return true;
        if (object == null || getClass() != object.getClass()) return false;
        
        EntidadFinanciera other = (EntidadFinanciera) object;
        if (this.codEntidad == null ? other.codEntidad != null : !this.codEntidad.equals(other.codEntidad)) return false;
        
        if (this.desEntidad == null ? other.desEntidad != null : !this.desEntidad.equals(other.desEntidad)) return false;
                
        return  true;
        
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.codEntidad != null ? this.codEntidad.hashCode() : 0);
        hash = 67 * hash + (this.desEntidad != null ? this.desEntidad.hashCode() : 0);
        return hash;
    }
}
