
package ud.ing.modi.pago;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pagarCompra complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pagarCompra">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codCompra" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codMonTienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pagarCompra", propOrder = {
    "codCompra",
    "token",
    "codMonTienda"
})
public class PagarCompra {

    protected String codCompra;
    protected String token;
    protected String codMonTienda;

    /**
     * Gets the value of the codCompra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCompra() {
        return codCompra;
    }

    /**
     * Sets the value of the codCompra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCompra(String value) {
        this.codCompra = value;
    }

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the codMonTienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodMonTienda() {
        return codMonTienda;
    }

    /**
     * Sets the value of the codMonTienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodMonTienda(String value) {
        this.codMonTienda = value;
    }

}
