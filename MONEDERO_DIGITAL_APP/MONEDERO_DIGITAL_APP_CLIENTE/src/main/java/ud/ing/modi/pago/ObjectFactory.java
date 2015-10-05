
package ud.ing.modi.pago;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ud.ing.modi.pago package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ValidarDatosCompra_QNAME = new QName("http://pago.modi.ing.ud/", "validarDatosCompra");
    private final static QName _PagarCompra_QNAME = new QName("http://pago.modi.ing.ud/", "pagarCompra");
    private final static QName _ValidarDatosCompraResponse_QNAME = new QName("http://pago.modi.ing.ud/", "validarDatosCompraResponse");
    private final static QName _PagarCompraResponse_QNAME = new QName("http://pago.modi.ing.ud/", "pagarCompraResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ud.ing.modi.pago
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PagarCompraResponse }
     * 
     */
    public PagarCompraResponse createPagarCompraResponse() {
        return new PagarCompraResponse();
    }

    /**
     * Create an instance of {@link ValidarDatosCompra }
     * 
     */
    public ValidarDatosCompra createValidarDatosCompra() {
        return new ValidarDatosCompra();
    }

    /**
     * Create an instance of {@link PagarCompra }
     * 
     */
    public PagarCompra createPagarCompra() {
        return new PagarCompra();
    }

    /**
     * Create an instance of {@link ValidarDatosCompraResponse }
     * 
     */
    public ValidarDatosCompraResponse createValidarDatosCompraResponse() {
        return new ValidarDatosCompraResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarDatosCompra }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pago.modi.ing.ud/", name = "validarDatosCompra")
    public JAXBElement<ValidarDatosCompra> createValidarDatosCompra(ValidarDatosCompra value) {
        return new JAXBElement<ValidarDatosCompra>(_ValidarDatosCompra_QNAME, ValidarDatosCompra.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PagarCompra }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pago.modi.ing.ud/", name = "pagarCompra")
    public JAXBElement<PagarCompra> createPagarCompra(PagarCompra value) {
        return new JAXBElement<PagarCompra>(_PagarCompra_QNAME, PagarCompra.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarDatosCompraResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pago.modi.ing.ud/", name = "validarDatosCompraResponse")
    public JAXBElement<ValidarDatosCompraResponse> createValidarDatosCompraResponse(ValidarDatosCompraResponse value) {
        return new JAXBElement<ValidarDatosCompraResponse>(_ValidarDatosCompraResponse_QNAME, ValidarDatosCompraResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PagarCompraResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pago.modi.ing.ud/", name = "pagarCompraResponse")
    public JAXBElement<PagarCompraResponse> createPagarCompraResponse(PagarCompraResponse value) {
        return new JAXBElement<PagarCompraResponse>(_PagarCompraResponse_QNAME, PagarCompraResponse.class, null, value);
    }

}
