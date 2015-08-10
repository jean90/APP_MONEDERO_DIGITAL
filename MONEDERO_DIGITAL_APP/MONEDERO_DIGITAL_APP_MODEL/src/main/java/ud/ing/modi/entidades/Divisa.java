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
@Table(name = "DIVISA")
public class Divisa implements Serializable {

    @Id
    @Column(name = "COD_DIVISA")
    private int codigoDivisa;
    @Column(name = "DES_DIVISA")
    private String desDivisa;

    public Divisa() {
    }

    public Divisa(int codigoDivisa, String desDivisa) {
        this.codigoDivisa = codigoDivisa;
        this.desDivisa = desDivisa;
    }

    public int getCodigoDivisa() {
        return codigoDivisa;
    }

    public void setCodigoDivisa(int codigoDivisa) {
        this.codigoDivisa = codigoDivisa;
    }

    public String getDesDivisa() {
        return desDivisa;
    }

    public void setDesDivisa(String desDivisa) {
        this.desDivisa = desDivisa;
    }

    public boolean equals(Object object) {

        if (object == this) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Divisa other = (Divisa) object;
        if (this.codigoDivisa != other.codigoDivisa) {
            return false;
        }

        if (this.desDivisa == null ? other.desDivisa != null : !this.desDivisa.equals(other.desDivisa)) {
            return false;
        }

        return true;

    }

}
