
package ud.ing.modi.pago;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.1.2
 * Sun Aug 30 17:19:49 COT 2015
 * Generated source version: 2.1.2
 * 
 */

public final class PagoCompras_PagoComprasImpPort_Client {

    private static final QName SERVICE_NAME = new QName("http://pago.modi.ing.ud/", "PagoComprasImpService");

    private PagoCompras_PagoComprasImpPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = PagoComprasImpService.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        PagoComprasImpService ss = new PagoComprasImpService(wsdlURL, SERVICE_NAME);
        PagoCompras port = ss.getPagoComprasImpPort();  
        
        {
        System.out.println("Invoking pagarCompra...");
        java.lang.String _pagarCompra_codMonedero = "";
        java.lang.String _pagarCompra_codTienda = "";
        java.lang.String _pagarCompra_codCompra = "";
        java.lang.String _pagarCompra_valorCompra = "";
        java.lang.String _pagarCompra_nickCliente = "";
        java.lang.String _pagarCompra_pssTx = "";
        java.lang.String _pagarCompra__return = port.pagarCompra(_pagarCompra_codMonedero, _pagarCompra_codTienda, _pagarCompra_codCompra, _pagarCompra_valorCompra, _pagarCompra_nickCliente, _pagarCompra_pssTx);
        System.out.println("pagarCompra.result=" + _pagarCompra__return);


        }
        {
        System.out.println("Invoking validarDatosCompra...");
        java.lang.String _validarDatosCompra_codMonedero = "";
        java.lang.String _validarDatosCompra_nickCliente = "";
        java.lang.String _validarDatosCompra__return = port.validarDatosCompra(_validarDatosCompra_codMonedero, _validarDatosCompra_nickCliente);
        System.out.println("validarDatosCompra.result=" + _validarDatosCompra__return);


        }

        System.exit(0);
    }

}