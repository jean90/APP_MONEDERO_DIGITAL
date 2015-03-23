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
@Table(name="TIPO_DOCUMENTO")
public class TipoDocumento implements Serializable{
    @Id
    @Column (name="COD_TIPODOC")
    private int codigotipoDocumento;
    @Column (name="DES_TIPODOC")
    private String desDocumento;

    public TipoDocumento() {
    }

    public TipoDocumento(int codigotipoDocumento, String desDocumento) {
        this.codigotipoDocumento = codigotipoDocumento;
        this.desDocumento = desDocumento;
    }    
    
    public int getCodigotipoDocumento() {
        return codigotipoDocumento;
    }

    public void setCodigotipoDocumento(int codigotipoDocumento) {
        this.codigotipoDocumento = codigotipoDocumento;
    }

    public String getDesDocumento() {
        return desDocumento;
    }

    public void setDesDocumento(String desDocumento) {
        this.desDocumento = desDocumento;
    }
    
    
    
}
