/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi;

import java.util.List;
import ud.ing.modi.entidades.EntidadFinanciera;
import ud.ing.modi.mapper.EntidadFinancieraMapper;

/**
 *
 * @author Administrador
 */
public class PruebaEntidades {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntidadFinancieraMapper mapper = new EntidadFinancieraMapper();
        List<EntidadFinanciera> lista = mapper.obtenerEntidadesFinaciera();
        for(int i=0; i<lista.size();i++){
            EntidadFinanciera aux = lista.get(i);
            System.out.println("cod--" + aux.getCodEntidad()+"  descripcion--" + aux.getDesEntidad());
        }
    }
    
}
