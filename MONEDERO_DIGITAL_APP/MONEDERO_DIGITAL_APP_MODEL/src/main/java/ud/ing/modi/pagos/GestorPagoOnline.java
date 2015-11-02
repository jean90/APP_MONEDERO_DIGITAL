/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.pagos;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ud.ing.modi.email.EmailTokenPago;
import ud.ing.modi.entidades.EstadoPago;
import ud.ing.modi.entidades.EstadoToken;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.PagoOnline;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.entidades.TokenPago;
import ud.ing.modi.mapper.MonederoMapper;
import ud.ing.modi.mapper.PagoOnlineMapper;
import ud.ing.modi.mapper.TokenMapper;

/**
 *
 * @author Administrador
 */
public class GestorPagoOnline {
    
    
     /**
     * Este método crea el registro del pago iniciado en la operatoria de validación para pagosPtes con el monedero digital.
     * @param codCompra Es el código de la compra que se desea pagar.
     * @param valorPago ES el valor del pago que será cancelado.
     * @param monOrigen Es el monedero del comprador desde el cual se realizará el pago.
     * @param monDestino Es el monedero de la tienda al cual se realizará el pago.
     * @throws Exception 
     */
    public PagoOnline crearPagoOnline(String codCompra, String valorPago, Monedero monOrigen, Monedero monDestino) throws Exception{
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
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ha ocurrido un error al validar o crear el registro del pago en la bd.");
            throw ex;
        }
        return pago;
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
                System.out.println("Hay algún registro de pago con ese código de compra que está APROBADO");
                throw new Exception();
            }
        }catch(Exception ex){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ha ocurrido un error al validar el registro del pago en la bd.");
            throw ex;
        }
        
    }
    
    
     /**
     * Este método envía al correo electrónico del cliente comprador el token encriptado que le ha sido generado para el pago de una compra en específico.
     * @param clienteComprador Es el cliente que está realizando la compra.
     * @param nombreTienda Es el nombre de la tienda a la cual se le está realizando el pago.
     * @param pagoOnline pago online del cual se obtendran los valores necesarios y concernientes para el envio de e-mail.
     * @param token token generado pzra el pago online que se enviará en el e-mail
     */
    public void enviarCorreoToken(Persona clienteComprador, String nombreTienda, PagoOnline pagoOnline, TokenPago token){
        HashMap datos=new HashMap();
        datos.put("nombre", clienteComprador.getNombre());
        datos.put("apellido", clienteComprador.getApellido());
        datos.put("nombreTienda", nombreTienda);
        datos.put("valorCompra", Float.toString(pagoOnline.getValorPago()));
        datos.put("codMonedero", pagoOnline.getMonOrigen().getCodMonedero());
        datos.put("token", token.getToken());
        
        EmailTokenPago email= new EmailTokenPago(clienteComprador.getEmail());
        email.ensamblarMensaje(datos);
        email.enviarMensaje();
        System.out.println("Correo enviado exitosamente con token: " + token.getToken());
    }
    
    /**
    * Este método se encarga de finalizar el pago online, el cual consiste en realizar el respectivo abono
    * al monedero de la tienda online y el débito del monedero del cliente comprador.
    * @param pagoOnline pago online el cual se finalizará
    * @throws Exception
    */
    public void finalizarPagoOnline(PagoOnline pagoOnline) throws Exception{
        
        MonederoMapper mapMonedero = new MonederoMapper();
        
        mapMonedero.abonarPago(pagoOnline.getValorPago(),pagoOnline.getMonDestino());
        System.out.println("Pago abonado");
        
        mapMonedero.debitarPago(pagoOnline.getValorPago(),pagoOnline.getMonOrigen());
        System.out.println("Pago debitado");
        
        PagoOnlineMapper mapPago = new PagoOnlineMapper();
        
        pagoOnline.setFechaPago(new Date());
        pagoOnline.setEstadoPago(new EstadoPago(2,"APROBADO"));
        mapPago.actualizarPago(pagoOnline);
        System.out.println("Pago registrado");        
    }
    
    /**
    * Este método se encarga de rechazar el pago online.
    * @param pagoOnline pago online el cual se rechazará
    * @exception Exception
    */
    public void rechazarPagoOnline(PagoOnline pagoOnline) throws Exception{
        PagoOnlineMapper mapPago = new PagoOnlineMapper();
        
        pagoOnline.setEstadoPago(new EstadoPago(3,"RECHAZADO"));
        
        mapPago.actualizarPago(pagoOnline);
    }
    
}
