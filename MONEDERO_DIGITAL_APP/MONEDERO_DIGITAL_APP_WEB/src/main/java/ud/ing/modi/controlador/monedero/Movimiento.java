/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.controlador.monedero;

import java.util.Date;

/**
 *
 * @author Administrador
 */
public class Movimiento {
    private String tipoMov;
    private String codMov;
    private Date fechaMov;
    private String valorMov;
    private String otrosDatos;

    public Movimiento(String tipoMov, String codMov, Date fechaMov, String valorMov, String otrosDatos) {
        this.tipoMov = tipoMov;
        this.codMov = codMov;
        this.fechaMov = fechaMov;
        this.valorMov = valorMov;
        this.otrosDatos = otrosDatos;
    }

    public Movimiento() {
    }
    
    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    public String getCodMov() {
        return codMov;
    }

    public void setCodMov(String codMov) {
        this.codMov = codMov;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public String getValorMov() {
        return valorMov;
    }

    public void setValorMov(String valorMov) {
        this.valorMov = valorMov;
    }

    public String getOtrosDatos() {
        return otrosDatos;
    }

    public void setOtrosDatos(String otrosDatos) {
        this.otrosDatos = otrosDatos;
    }
    
    
    
}
