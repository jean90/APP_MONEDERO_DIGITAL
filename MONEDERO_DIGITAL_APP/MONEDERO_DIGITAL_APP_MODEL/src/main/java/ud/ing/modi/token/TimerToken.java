/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.token;

import java.util.logging.Level;
import java.util.logging.Logger;
import ud.ing.modi.constantes.Constantes;
import ud.ing.modi.entidades.EstadoPago;
import ud.ing.modi.entidades.PagoOnline;
import ud.ing.modi.entidades.TokenPago;
import ud.ing.modi.mapper.PagoOnlineMapper;
import ud.ing.modi.mapper.TokenMapper;

/**
 *
 * @author Administrador
 */
public class TimerToken extends Thread{
    public static final int MEDIO_MINUTO = 30000;
    public static final int UN_MINUTO = 60000;
    public static final int DOS_MINUTOS = 120000;
    public static final int TRES_MINUTOS = 180000;
    public static final int CINCO_MINUTOS = 300000;
    
    private final int codTokenPago;
    
    private GestorToken gestorToken;

    public TimerToken(int codTokenPago, GestorToken gestorToken) {
        super("token_" + codTokenPago);
        this.codTokenPago = codTokenPago;
        this.gestorToken = gestorToken;
        
    }

    public int getCodTokenPago() {
        return codTokenPago;
    }
    
    public void run(){
        try {
            System.out.println("Iniciando el tiempo de vida del token " + this.codTokenPago);
            sleep(DOS_MINUTOS);
            TokenMapper tm = new TokenMapper();
            TokenPago tokenPago= tm.obtenerTokenPagoById(codTokenPago);
            if(tokenPago.getEstadoToken().getCodEstadoToken()==Constantes.TOKEN_EMITIDO){
                tokenPago.setEstadoToken(this.gestorToken.obtenerEstadoTokenCaducado());
                tm.actualizarToken(tokenPago);
                cancelarPagoOnline(tokenPago.getCodPago());
                System.out.println("tiempo de vida del token se agotó, el token será invalidado.");
                this.gestorToken.eliminarTimerToken(this);
            }
        } catch (InterruptedException ex) {            
            //Logger.getLogger(TimerToken.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("El hilo ha sido detenido satisfactoriamente");
        } catch (Exception ex) {
            Logger.getLogger(TimerToken.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cancelarPagoOnline(int codPago) throws Exception{
        PagoOnlineMapper mapPago = new PagoOnlineMapper();        
        PagoOnline pagoOnline = mapPago.buscarPagoOnlinePendienteById(codPago);        
        pagoOnline.setEstadoPago(new EstadoPago(4,"CANCELADO"));        
        mapPago.actualizarPago(pagoOnline);
    }
}
