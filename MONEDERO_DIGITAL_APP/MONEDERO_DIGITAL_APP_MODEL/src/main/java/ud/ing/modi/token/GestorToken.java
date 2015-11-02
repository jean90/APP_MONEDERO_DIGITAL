/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.token;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import ud.ing.modi.config.Config;
import ud.ing.modi.constantes.Constantes;
import ud.ing.modi.entidades.EstadoToken;
import ud.ing.modi.entidades.TokenPago;
import ud.ing.modi.mapper.EstadoTokenMapper;
import ud.ing.modi.mapper.TokenMapper;
import ud.ing.modi.utilidades.Cifrado;

/**
 *
 * @author Administrador
 */
public class GestorToken {
    private static GestorToken instancia;
    
    private ArrayList<TimerToken> tokensPendientes;

    private GestorToken() {        
        this.tokensPendientes = new ArrayList<TimerToken>();
    }

    public static GestorToken getInstancia() {
        if(instancia==null){
            instancia = new GestorToken();
        }
        return instancia;
    }
    
    /**
     * Este método se encarga de la creación de un token relacionado a un pago online creado, además,
     * se encarga de persistir en BBDD el token creado en estado EMITIDO.
     * @param codPago código de pago online al cual se le relacionará el token a crear.
     * @return TokenPago relacionado al código de pago en cuestion.
     * @throws Exception error al momento de persistir en BBDD.
     */
    public synchronized TokenPago emitirToken(int codPago) throws Exception{
        TokenPago token = new TokenPago();
        token.setCodPago(codPago);
        token.setEstadoToken(obtenerEstadoTokenEmitido());
        token.setFechaCreacion(new Timestamp(new Date().getTime()));
        token.setToken(generarToken());
        TokenMapper mapeador= new TokenMapper();
        try {
            mapeador.guardarToken(token);
            System.out.println("Token registrado en bd");
            activarTimerToken(token.getCodToken());
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ha ocurrido un error al guardar el registro del token en la bd.");
            throw ex;
        }
        return token;
    }
    
    /**
     * Este método se encarga de la generacion de un token encriptado.
     * @return retorna token encriptado. 
     */    
    private String generarToken(){
        SecureRandom random = new SecureRandom();
        //100/5=20 CARACTERES
        String token=new BigInteger(100, random).toString(32);
        System.out.println("Sin encriptar: "+token);
        Cifrado cifra=new Cifrado();
        cifra.addKey(Config.getConfig().getPropiedad("CLAVE_PRIVADA_MENSAJERIA"));
        //cifra.addKey("123456");
        token=cifra.encriptar(token);
        System.out.println("Encriptado: "+token);
        return token;
    }
    
    /**
     * Este método se encarga de validar el token ingresado por el usuario, comparándolo
     * contra el que se encuentra guardado en BBDD relacionado al pago Online. De ser validado 
     * correctamente el token, este cambiarà de estado a VALIDADO y se actuaizarà en BBDD;
     * en caso contrario, el estado será ANULADO.
     * @param codPago es el código del pago online con el cual se recuperará el token
     * relacionado a dicho pago desde BBDD.
     * @param tokenUsuario es el token ingresado por el usuario, el cual se comparará con el
     * token recuperado desde BBDD.
     * @return retorna true en caso de que la comparacion del token ingresado por el usuario
     * (tokenUsuario) sea igual al token recuperado desde BBDD. En caso contrario la respuesta es false.
     * @throws Exception error al persistir en BBDD.
     */    
    public synchronized boolean validarToken(int codPago, String tokenUsuario) throws Exception{
        boolean isTokenValido = false;
        TokenMapper tm = new TokenMapper();
        TokenPago tokenPago = tm.cargarToken(codPago);
        
        if(tokenPago.getEstadoToken().getCodEstadoToken()==Constantes.TOKEN_EMITIDO){
            tokenUsuario = desencriptarToken(tokenUsuario);
            String token = desencriptarToken(tokenPago.getToken());
            if(tokenUsuario.equals(token)){
                tokenPago.setEstadoToken(obtenerEstadoTokenValidado());
                isTokenValido = true;
            }else {
                tokenPago.setEstadoToken(obtenerEstadoTokenAnulado());
            }
            tm.actualizarToken(tokenPago);
            desactivarTimerToken(tokenPago.getCodToken());
        }
        return isTokenValido;
    }
    
     /**
     * Este método desencripta un token.
     * @param tokenEncriptado Es el token que se va a desencriptar.
     * @return Retorna como resultado el token desencriptado.
     */
    private String desencriptarToken(String tokenEncriptado){
        String respuesta = "";
        Cifrado cifra=new Cifrado();
        cifra.addKey(Config.getConfig().getPropiedad("CLAVE_PRIVADA_MENSAJERIA"));
        //cifra.addKey("123456");
        respuesta=cifra.desencriptar(tokenEncriptado);
        System.out.println("Desencriptado: "+respuesta);
        return respuesta;
    }

    protected EstadoToken obtenerEstadoTokenEmitido(){
        EstadoTokenMapper etm = new EstadoTokenMapper();
        EstadoToken estadoTokenEmitido = etm.obtenerEstadoTokenById(Constantes.TOKEN_EMITIDO);
        return estadoTokenEmitido;
    }
    
    protected EstadoToken obtenerEstadoTokenValidado(){
        EstadoTokenMapper etm = new EstadoTokenMapper();
        EstadoToken estadoTokenValidado = etm.obtenerEstadoTokenById(Constantes.TOKEN_VALIDADO);
        return estadoTokenValidado;
    }
    
    protected EstadoToken obtenerEstadoTokenAnulado(){
        EstadoTokenMapper etm = new EstadoTokenMapper();
        EstadoToken estadoTokenAnulado = etm.obtenerEstadoTokenById(Constantes.TOKEN_ANULADO);
        return estadoTokenAnulado;
    }
    
    protected EstadoToken obtenerEstadoTokenCaducado(){
        EstadoTokenMapper etm = new EstadoTokenMapper();
        EstadoToken estadoTokenAnulado = etm.obtenerEstadoTokenById(Constantes.TOKEN_CADUCADO);
        return estadoTokenAnulado;
    }
    
    private void activarTimerToken(int codToken){
        TimerToken timer = new TimerToken(codToken, this);
        timer.start();
        tokensPendientes.add(timer);
    }
    
    private void desactivarTimerToken(int codTokenPago){
        for (int i = 0; i < this.tokensPendientes.size(); i++) {
            int codTokenAux = this.tokensPendientes.get(i).getCodTokenPago();
            if(codTokenPago==codTokenAux){
                TimerToken timerTokenDesactivar = this.tokensPendientes.get(i);
                timerTokenDesactivar.interrupt();                
            }
        }
    }
    
    protected void eliminarTimerToken(TimerToken timerToken){
        System.out.println("Eliminando timerToken de la lista de pendientes. " + timerToken.getCodTokenPago());
        this.tokensPendientes.remove(timerToken);        
    }
}
