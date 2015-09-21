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
    
    public List<String> validacionCompra(String codMonedero, String nickCliente, String codTienda, String codCompra, String valorCompra){
        try{
            
            System.out.println( "Vamos 1 **" );
            PagoComprasImpService insPago= new PagoComprasImpService();
            System.out.println( "Vamos 2 **" );
            PagoCompras pago= insPago.getPagoComprasImpPort();
            System.out.println( "Listo! Creada la instancia del servicio de pagos del Monedero!!" );
            
            List<String> rta=pago.validarDatosCompra(codMonedero, nickCliente, codTienda, codCompra, valorCompra);
            System.out.println("Respuesta validación: "+rta.get(0));
            
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
    
    public List<String> pagoCompra(String codCompra, String token){
        try{
            System.out.println( "Vamos 1 **" );
            PagoComprasImpService insPago= new PagoComprasImpService();
            System.out.println( "Vamos 2 **" );
            PagoCompras pago= insPago.getPagoComprasImpPort();
            System.out.println( "Listo! Creada la instancia del servicio de pagos del Monedero!!" );


            List<String> rta=pago.pagarCompra(codCompra, token);
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
