package ud.ing.modi;

import ud.ing.modi.pago.PagoCompras;
import  ud.ing.modi.pago.PagoComprasImpService;

/**
 * 
 *
 */
public class ClientePagosMonedero 
{
    
    public String pagoCompra(String codMonedero, String nickCliente, String codTienda, String codCompra, String valorCompra, String pssTx){
        try{
            
            System.out.println( "Vamos 1 **" );
            PagoComprasImpService insPago= new PagoComprasImpService();
            System.out.println( "Vamos 2 **" );
            PagoCompras pago= insPago.getPagoComprasImpPort();
            System.out.println( "Listo! Creada la instancia del servicio de pagos del Monedero!!" );

            String rta=pago.validarDatosCompra(codMonedero, nickCliente);
            System.out.println("Respuesta validación: "+rta);
            System.out.println("Respuesta corta: "+rta.substring(0,2));

            if (rta.substring(0,2).equals("OK")) {
                rta=pago.pagarCompra(codMonedero, codTienda, codCompra, valorCompra, nickCliente, pssTx);
                System.out.println("Respuesta Compra: "+rta);
            }
            return rta;

        
    }catch(Exception e){
        e.printStackTrace();
        System.out.println("Error: "+e);
        return "Error gral";
    }
    }   
}
