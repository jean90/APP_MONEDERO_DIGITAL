package ud.ing.modi.pago;

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
 
@WebService(targetNamespace = "http://pago.modi.ing.ud/", name = "PagoCompras")
@XmlSeeAlso({ObjectFactory.class})
public interface PagoCompras {

    @WebMethod
    @RequestWrapper(localName = "pagarCompra", targetNamespace = "http://pago.modi.ing.ud/", className = "ud.ing.modi.pago.PagarCompra")
    @ResponseWrapper(localName = "pagarCompraResponse", targetNamespace = "http://pago.modi.ing.ud/", className = "ud.ing.modi.pago.PagarCompraResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String pagarCompra(
        @WebParam(name = "codMonedero", targetNamespace = "")
        java.lang.String codMonedero,
        @WebParam(name = "codTienda", targetNamespace = "")
        java.lang.String codTienda,
        @WebParam(name = "codCompra", targetNamespace = "")
        java.lang.String codCompra,
        @WebParam(name = "valorCompra", targetNamespace = "")
        java.lang.String valorCompra,
        @WebParam(name = "nickCliente", targetNamespace = "")
        java.lang.String nickCliente,
        @WebParam(name = "pssTx", targetNamespace = "")
        java.lang.String pssTx
    );

    @WebMethod
    @RequestWrapper(localName = "validarDatosCompra", targetNamespace = "http://pago.modi.ing.ud/", className = "ud.ing.modi.pago.ValidarDatosCompra")
    @ResponseWrapper(localName = "validarDatosCompraResponse", targetNamespace = "http://pago.modi.ing.ud/", className = "ud.ing.modi.pago.ValidarDatosCompraResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String validarDatosCompra(
        @WebParam(name = "codMonedero", targetNamespace = "")
        java.lang.String codMonedero,
        @WebParam(name = "nickCliente", targetNamespace = "")
        java.lang.String nickCliente
    );
}