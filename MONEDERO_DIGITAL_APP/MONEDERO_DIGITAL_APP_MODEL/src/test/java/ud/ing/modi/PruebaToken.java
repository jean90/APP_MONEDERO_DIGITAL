/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi;

import ud.ing.modi.entidades.TokenPago;
import ud.ing.modi.token.GestorToken;

/**
 *
 * @author Administrador
 */
public class PruebaToken {

    
   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        GestorToken gt = GestorToken.getInstancia();
        TokenPago tp = gt.emitirToken(1);
        //gt.validarToken(1, tp.getToken());
    }
    
}
