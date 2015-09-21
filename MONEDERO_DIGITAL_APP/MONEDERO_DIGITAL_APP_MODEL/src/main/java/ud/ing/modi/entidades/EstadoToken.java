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
@Table(name="ESTADO_TOKEN")
@Inheritance(strategy = InheritanceType.JOINED)
public class EstadoToken implements Serializable{
    @Id
    @Column (name="COD_ESTADO_TOKEN")
    private int codEstadoToken;
    @Column (name="DESC_ESTADO_TOKEN")
    private String descEstadoToken;

    public EstadoToken() {
    }

    public EstadoToken(int codEstadoToken, String descEstadoToken) {
        this.codEstadoToken = codEstadoToken;
        this.descEstadoToken = descEstadoToken;
    }
    
    public int getCodEstadoToken() {
        return codEstadoToken;
    }

    public void setCodEstadoToken(int codEstadoToken) {
        this.codEstadoToken = codEstadoToken;
    }

    public String getDescEstadoToken() {
        return descEstadoToken;
    }

    public void setDescEstadoToken(String descEstadoToken) {
        this.descEstadoToken = descEstadoToken;
    }
    
    
}
