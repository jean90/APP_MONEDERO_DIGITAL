/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.monedero;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ud.ing.modi.entidades.Monedero;

/**
 *
 * @author Lufe
 */
@ManagedBean(name = "controlExportarHistorico")
@ViewScoped
public class ControlExportarHistorico  implements Serializable {
    
    private Monedero selectedMon;
    
}
