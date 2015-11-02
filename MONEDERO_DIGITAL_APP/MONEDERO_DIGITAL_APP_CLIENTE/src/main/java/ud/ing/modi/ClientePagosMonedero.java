package ud.ing.modi;

import java.util.List;
import ud.ing.modi.pago.PagoCompras;
import  ud.ing.modi.pago.PagoComprasImpService;

/**
 * 
 *
 */
public class ClientePagosMonedero 
{
    /**
    * Este método se encarga de realizar el llamado a la operacion validarDatosCompra del web service PagosCompra
    * del sistema de pagos online Monedero Digital, el cual se encarga de validar los datos requeridos para la
    * realización de un pago online.
    * @param codMonedero código de monedero digital de cliente comprador quien desea realizar el pago online.
    * @param nickCliente Nickname del cliente complador quien desea realizar el pagon online.
    * @param codTienda Código de la tienda online desde la cual se desea realizar el pago online.
    * @param codCompra Código o consecutivo dado por la tienda online que identifica la compra que se desea pagar
    * por medio del sistema de pagos Monedero Digital.
    * @param valorCompra Valor de compra que se desea pagar por medio del sistema de pagos Monedero Digital.
    * @return objeto de tipo List<String>, el cual contiene la respuesta de la operación validarDatosCompra, en la 
    * posición (0) codigo de respuesta, (1) descripción de respuesta, (2) código de pago online generado por el sistema
    * de pagos Monedero Digital de haber aprobado la validaciones de los datos de compra.
    */
    public List<String> validacionCompra(String codMonedero, String nickCliente, String codTienda, String codCompra, String valorCompra){
        try{            
            System.out.println( "Vamos 1 **" );
            PagoComprasImpService insPago= new PagoComprasImpService();
            System.out.println( "Vamos 2 **" );
            PagoCompras pago= insPago.getPagoComprasImpPort();
            System.out.println( "Listo! Creada la instancia del servicio de pagos del Monedero!!" );
            
            List<String> rta=pago.validarDatosCompra(codMonedero, nickCliente, codTienda, codCompra, valorCompra);
            System.out.println("Respuesta validaciÃ³n: "+rta.get(0));
            
            return rta;       
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error: "+e);
            List<String> rta=null;
            rta.add("00");
            rta.add("Error general");
            return rta;
        }
    }   
    
    
    /**
     * Este método se encarga de realizar el llamado de la operación pagoCompra del web servicw pagoCompras del
     * sistema de pagos online Monedero Digital, el cual se encarga de realizar el pago online.
     * @param codCompra Código o consecutivo dado por la tienda online que identifica la compra que se desea pagar
     * por medio del sistema de pagos Monedero Digital.
     * @param token valor del token ingresado por el cliente comprador para la autorización del pago online.
     * @param codPago Código del pago online generado previamente por el sistema de pagos online Monedero Digital.
     * @return objeto de tipo List<String>, el cual contiene la respuesta de la operación pagoCompra, en la 
     * posición (0) codigo de respuesta, (1) descripción de respuesta, (2) código de pago online generado por el sistema
     * de pagos Monedero Digital de haber aprobado la validaciones de los datos de compra.
     */
    public List<String> pagoCompra(String codCompra, String token, String codPago){
        try{
            System.out.println( "Vamos 1 **" );
            PagoComprasImpService insPago= new PagoComprasImpService();
            System.out.println( "Vamos 2 **" );
            PagoCompras pago= insPago.getPagoComprasImpPort();
            System.out.println( "Listo! Creada la instancia del servicio de pagos del Monedero!!" );
            List<String> rta=pago.pagarCompra(codCompra, token, codPago);
            System.out.println("Respuesta Compra: "+rta);
            return rta;            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error: "+e);
            List<String> rta=null;
            rta.add("00");
            rta.add("Error general");
            return rta;
        }
    }
}
