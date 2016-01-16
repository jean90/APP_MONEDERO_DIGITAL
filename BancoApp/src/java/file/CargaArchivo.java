/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;



import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;


/**
 *
 * @author Administrador
 */
@ManagedBean(name = "cargaArchivo")
@SessionScoped
public class CargaArchivo implements Serializable{
    //private Part file;
    private UploadedFile file;
    private String as;

    public CargaArchivo() {
        System.out.println("Carga");
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        System.out.println("gaurdado");
        this.file = file;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        System.out.println("ajaaaaaaa");
        this.as = as;
    }

    public void modifTel(ValueChangeEvent e){
        System.out.println("modif");
        this.setFile((UploadedFile)e.getNewValue());
    }
    
   public void cargar()throws ServletException {
       
       if(file == null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName()+" is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
       System.out.println("er ---");
       
   }
   
   public void handleFileUpload(FileUploadEvent event){
       UploadedFile archivo=event.getFile();
       System.out.println(archivo.getFileName());
   }
}
