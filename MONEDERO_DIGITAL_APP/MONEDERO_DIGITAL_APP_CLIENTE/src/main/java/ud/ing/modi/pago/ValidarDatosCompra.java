
package ud.ing.modi.pago;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validarDatosCompra complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validarDatosCompra">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codMonedero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nickCliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validarDatosCompra", propOrder = {
    "codMonedero",
    "nickCliente"
})
public class ValidarDatosCompra {

    protected String codMonedero;
    protected String nickCliente;

    /**
     * Gets the value of the codMonedero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodMonedero() {
        return codMonedero;
    }

    /**
     * Sets the value of the codMonedero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodMonedero(String value) {
        this.codMonedero = value;
    }

    /**
     * Gets the value of the nickCliente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickCliente() {
        return nickCliente;
    }

    /**
     * Sets the value of the nickCliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickCliente(String value) {
        this.nickCliente = value;
    }

}
