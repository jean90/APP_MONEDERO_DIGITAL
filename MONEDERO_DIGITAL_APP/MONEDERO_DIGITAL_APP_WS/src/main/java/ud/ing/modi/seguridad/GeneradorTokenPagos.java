/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.seguridad;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ud.ing.modi.config.Config;
import ud.ing.modi.email.EmailTokenPago;
import ud.ing.modi.entidades.EstadoPago;
import ud.ing.modi.entidades.EstadoToken;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.PagoOnline;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.entidades.TokenPago;
import ud.ing.modi.mapper.PagoOnlineMapper;
import ud.ing.modi.mapper.TokenMapper;
import ud.ing.modi.utilidades.Cifrado;

/**
 *
 * @author Administrador
 */
public class GeneradorTokenPagos {
    String token;

    /**
     * Este método genera un token aleatorio para el pago de una compra.
     */
    public void generarToken(){
        SecureRandom random = new SecureRandom();
        //100/5=20 CARACTERES
        this.token=new BigInteger(100, random).toString(32);
        System.out.println("Sin encriptar: "+token);
    }
    
    /**
     * Este método encripta el token generado para un pago en línea.
     */
    public void encriptarToken(){
        Cifrado cifra=new Cifrado();
        cifra.addKey(Config.getConfig().getPropiedad("CLAVE_PRIVADA_MENSAJERIA"));
        this.token=cifra.encriptar(this.token);
        System.out.println("Encriptado: "+token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    /**
     * Este método envía al correo electrónico del cliente comprador el token encriptado que le ha sido generado para el pago de una compra en específico.
     * @param clienteComprador Es el cliente que está realizando la compra.
     * @param nombreTienda Es el nombre de la tienda a la cual se le está realizando el pago.
     * @param valorCompra Es el valor de la compra que se está pagando.
     * @param codMonedero Es el código del monedero del cual se está debitando el pago.
     */
    public void enviarCorreoToken(Persona clienteComprador, String nombreTienda, String valorCompra, String codMonedero){
        HashMap datos=new HashMap();
        datos.put("nombre", clienteComprador.getNombre());
        datos.put("apellido", clienteComprador.getApellido());
        datos.put("nombreTienda", nombreTienda);
        datos.put("valorCompra", valorCompra);
        datos.put("codMonedero", codMonedero);
        datos.put("token", this.token);
        
        EmailTokenPago email= new EmailTokenPago(clienteComprador.getEmail());
        email.ensamblarMensaje(datos);
        email.enviarMensaje();
        System.out.println("Correo enviado exitosamente con token: "+this.token);
    }
    
    /**
     * Este método guarda en la base de datos el registro con el token que se ha generado para el pago.
     * @param pagoOnline Es el código del pago asociado al token generado.
     * @throws Exception 
     */
    public void guardarToken(PagoOnline pagoOnline) throws Exception{
        TokenPago token = new TokenPago();
        token.setPagoOnline(pagoOnline);
        token.setEstadoToken(new EstadoToken(1, "EMITIDO"));
        token.setFechaCreacion(new Timestamp(new Date().getTime()));
        token.setToken(this.token);
        TokenMapper mapeador= new TokenMapper();
        try {
            mapeador.guardarToken(token);
            System.out.println("Token registrado en bd");
        } catch (Exception ex) {
            Logger.getLogger(GeneradorTokenPagos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ha ocurrido un error al guardar el registro del token en la bd.");
            throw ex;
        }
    }
    
    /**
     * Este método crea el registro del pago iniciado en la operatoria de validación para pagosPtes con el monedero digital.
     * @param codCompra Es el código de la compra que se desea pagar.
     * @param valorPago ES el valor del pago que será cancelado.
     * @param monOrigen Es el monedero del comprador desde el cual se realizará el pago.
     * @param monDestino Es el monedero de la tienda al cual se realizará el pago.
     * @throws Exception 
     */
    public void guardarRegistroToken(String codCompra, String valorPago, Monedero monOrigen, Monedero monDestino) throws Exception{
        PagoOnline pago= new PagoOnline();
        pago.setCodCompra(codCompra);
        pago.setMonDestino(monDestino);
        pago.setMonOrigen(monOrigen);
        pago.setValorPago(Float.parseFloat(valorPago));
        pago.setEstadoPago(new EstadoPago(1, "PENDIENTE"));
        PagoOnlineMapper mapeador = new PagoOnlineMapper();
        try {
            this.validarExistenciaPago(codCompra, monDestino);
            mapeador.registrarPago(pago);
            System.out.println("Pago registrado en la bd.");
            this.guardarToken(pago);
        } catch (Exception ex) {
            Logger.getLogger(GeneradorTokenPagos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ha ocurrido un error al validar o crear el registro del pago en la bd.");
            throw ex;
        }
    }
    
    /**
     * Este método valida la existencia de un pago que se va a ingresar de nuevo. Si ya existe lo cancela, así como cancela el token previamente generado.
     * @param codCompra Es el código de la compra de la cual se validará la existencia del pago.
     * @param monTienda Es el monedero de la tienda a la cual se realizará el pago a validar.
     * @throws Exception 
     */
    public void validarExistenciaPago(String codCompra, Monedero monTienda) throws Exception{
        PagoOnlineMapper mapPagos = new PagoOnlineMapper();
        TokenMapper mapToken = new TokenMapper();
        try{
            List<PagoOnline> pagosFinal=mapPagos.listarPagosFinalizadosdeCompra(codCompra,monTienda);
            List<PagoOnline> pagosPtes=mapPagos.listarPagosPtesdeCompra(codCompra,monTienda);
            if (pagosPtes!=null && !pagosPtes.isEmpty()) {
                mapPagos.cancelarPagos(pagosPtes);
                for (int i = 0; i < pagosPtes.size(); i++) {
                    TokenPago token = mapToken.cargarToken(pagosPtes.get(i).getCodPago());
                    token.setEstadoToken(new EstadoToken(4,"ANULADO"));
                    mapToken.actualizarToken(token);
                }
            }
            if (pagosFinal!=null && !pagosFinal.isEmpty()) {
                //Hay algún registro de pago con ese código de compra que está RECHAZADO o APROBADO
                System.out.println("Hay algún registro de pago con ese código de compra que está RECHAZADO o APROBADO");
                throw new Exception();
            }
        }catch(Exception ex){
            Logger.getLogger(GeneradorTokenPagos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ha ocurrido un error al validar el registro del pago en la bd.");
            throw ex;
        }
        
    }
    
    /**
     * ESte método desencripta un token.
     * @param tokenEncriptado Es el token que se va a desencriptar.
     * @return Retorna como resultado el token desencriptado.
     */
    public String desencriptarToken(String tokenEncriptado){
        
        Cifrado cifra=new Cifrado();
        cifra.addKey(Config.getConfig().getPropiedad("CLAVE_PRIVADA_MENSAJERIA"));
        this.token=cifra.desencriptar(tokenEncriptado);
        System.out.println("Desencriptado: "+token);
        return token;
    }
    
}
