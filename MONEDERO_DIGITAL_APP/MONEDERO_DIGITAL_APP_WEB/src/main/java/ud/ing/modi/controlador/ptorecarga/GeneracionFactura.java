/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.controlador.ptorecarga;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.FacturaRecargas;
import ud.ing.modi.entidades.PuntoRecarga;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.FacturaRecargasMapper;
import ud.ing.modi.tx.OperacionTransaccional;

/**
 *
 * @author Administrador
 */
@ManagedBean (name = "generacionFactura")
@ViewScoped
public class GeneracionFactura extends OperacionTransaccional implements Serializable{

    FacturaRecargas factura;
    
    public void generacionFacturaPDF() {
        try {
            System.out.println("Generaa");
            cargarDatosFactura();
            
            if (this.factura!=null) {
                
                PdfReader reader=new PdfReader("C:\\Users\\Administrador\\Dropbox\\Tesis\\Monedero Digital\\Factura_PtoRecarga.pdf");
                String nombreFact="D:\\REPOSITORIO TESIS2\\REPO\\MONEDERO_DIGITAL_APP\\MONEDERO_DIGITAL_APP_WEB\\src\\main\\webapp\\docs\\FACTURA_"+traerUsu()+".pdf";
                PdfStamper stamp=new PdfStamper(reader, new FileOutputStream(nombreFact));
                AcroFields formulario=stamp.getAcroFields();

                formulario.setField("NUM_FACTURA", Integer.toString(this.factura.getCodFactura()));
                
                int mes = this.factura.getFechaCorte().getMonth() + 1;
                int anio = this.factura.getFechaCorte().getYear() + 1900;
                    String fechaC = this.factura.getFechaCorte().getDate() + "-" + mes + "-" + anio ;
                formulario.setField("FECHA_CORTE", fechaC);
                mes=this.factura.getFechaVencimiento().getMonth() + 1;
                anio = this.factura.getFechaVencimiento().getYear() + 1900;
                String fechaL = this.factura.getFechaVencimiento().getDate() + "-" + mes + "-" + anio ;
                formulario.setField("FECHA_LIMITE", fechaL);
                formulario.setField("VALOR_PAGO", "$ "+Double.toString(this.factura.getValorTotalPago()));
                formulario.setField("COD_CLIENTE", Integer.toString(this.factura.getPuntoRecarga().getIdCliente()));
                formulario.setField("NOMBRE_CLIENTE", this.factura.getPuntoRecarga().getRazonSocial());
                formulario.setField("NUM_RECARGAS", Integer.toString(this.factura.getNumTotalRecargas()));
                formulario.setField("VALOR_RECARGAS", "$ "+Double.toString(this.factura.getValorTotalRecargas()));
                formulario.setField("VALOR_COMISION", "$ "+Double.toString(this.factura.getValorCalculoComision()));

                stamp.setFormFlattening(true);
                stamp.close();
                
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(GeneracionFactura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GeneracionFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FacturaRecargas getFactura() {
        return factura;
    }

    public void setFactura(FacturaRecargas factura) {
        this.factura = factura;
    }
    
    public String generarPDF(){
        System.out.println("PDFFFFF");
        
            
           /* if (this.factura==null) {
                FacesMessage msg = new FacesMessage("No tiene facturas generadas.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                
            }else{*/
        String nombreFact="D:\\REPOSITORIO TESIS2\\REPO\\MONEDERO_DIGITAL_APP\\MONEDERO_DIGITAL_APP_WEB\\src\\main\\webapp\\docs\\FACTURA_"+traerUsu()+".pdf";
        File fact=new File(nombreFact);
        System.out.println("archivo"+ fact.exists());
        if (fact.exists()) {
            return "/docs/FACTURA_"+traerUsu()+".pdf";
            
        }else{
            FacesMessage msg = new FacesMessage("No tiene facturas generadas.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
    }
    
    public void cargarDatosFactura(){
        String nick = traerUsu();
        PuntoRecarga punto= new ClienteMapper().buscarPtoRecargaPorNick(nick);
        
        FacturaRecargasMapper mapFacturas=new FacturaRecargasMapper();
        this.factura=mapFacturas.buscarFactura(punto);
    }
}
