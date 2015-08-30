
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
 *         &lt;element name="codMonedero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codTienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codCompra" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valorCompra" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "pagarCompra", propOrder = {
    "codMonedero",
    "codTienda",
    "codCompra",
    "valorCompra",
    "nickCliente"
})
public class PagarCompra {

    protected String codMonedero;
    protected String codTienda;
    protected String codCompra;
    protected String valorCompra;
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
     * Gets the value of the codTienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTienda() {
        return codTienda;
    }

    /**
     * Sets the value of the codTienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTienda(String value) {
        this.codTienda = value;
    }

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
     * Gets the value of the valorCompra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValorCompra() {
        return valorCompra;
    }

    /**
     * Sets the value of the valorCompra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValorCompra(String value) {
        this.valorCompra = value;
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
