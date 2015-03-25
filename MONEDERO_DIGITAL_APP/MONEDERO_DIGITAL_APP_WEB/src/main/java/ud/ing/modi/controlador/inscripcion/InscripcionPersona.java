/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.inscripcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;   
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FlowEvent;
import ud.ing.modi.config.Config;
import ud.ing.modi.controlador.ControlPersona;
import ud.ing.modi.email.EmailActivacionCuenta;
import ud.ing.modi.entidades.PendienteRegis;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.entidades.TipoDocumento;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.mapper.DocumentoMapper;
import ud.ing.modi.mapper.PendientesMapper;
import ud.ing.modi.mapper.PersonMapper;
import ud.ing.modi.utilidades.Cifrado;

/**
 *
 * @author Lufe
 */
@ManagedBean(name = "inscripcionPersona")
@ViewScoped
public class InscripcionPersona implements Serializable {

    private Persona persona = new Persona();
    private List<TipoDocumento> documentos = new ArrayList<TipoDocumento>(); 
    private PendienteRegis pendiente=new PendienteRegis();
    private String password;
    private String nick;

    public InscripcionPersona() {
        this.traerDocs();        
    }
     
    
    public List<TipoDocumento> getDocumentos() {
        return documentos;
    }
    
    public void setDocumentos(List<TipoDocumento> documentos) {
        this.documentos = documentos;
    }
    
    public Persona getPersona() {
        return persona;
    }
    
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public PendienteRegis getPendiente() {
        return pendiente;
    }

    public void setPendiente(PendienteRegis pendiente) {
        this.pendiente = pendiente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
    
    
    
    public String onFlowProcess(FlowEvent event) {
        String evento=event.getNewStep();
        System.out.println("Evento: " + evento);
        if (evento.equals("confirma")) {
            this.asignarDoc();
        }
        return evento;
    }
    
    /**
     * Este método validaUsuLdap que el nick que el usuario ha elegido para su registro no existe actualmente en el ldap
     * @param arg0
     * @param arg1
     * @param arg2
     * @throws ValidatorException 
     */
    public void validaUsuLdap(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        AccesoLDAP ldap=new AccesoLDAP();
        System.out.println("Buscando usuario "+arg2.toString()+" en el ldap");
        if (ldap.buscarUsuario((String)arg2)) {
         throw new ValidatorException(new FacesMessage("Nickname no disponible"));
      }
    }
    
    /**
     * Este método llama la validación de email
     * @param arg0
     * @param arg1
     * @param arg2
     * @throws ValidatorException 
     */
    public void validaEmail(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        System.out.println("Validando email "+arg2.toString());
        EmailActivacionCuenta emailVal=new EmailActivacionCuenta(arg2.toString());
      if (!emailVal.validarEmail()) {
         throw new ValidatorException(new FacesMessage("Email no es válido"));
      }
    }
    
    /**
     * Este método se encarga de guardar el registro en la base de datos de la entidad persona, así como de la solicitud de registro. De la misma forma se almacena el registro en el LDAP del nuevo usuario.
     */
    public String save() {
        /*if(this.tipoDocumento.getCodigotipoDocumento()=="1"){
         this.tipoDocumento.setDesDocumento(null);
         }*/
        String estado="fallo";
        PersonMapper mapeador = new PersonMapper();
        PendientesMapper mapPend = new PendientesMapper();
        System.out.println("NICK: "+nick+"; PSS: "+password);
        try {
            mapeador.guardarPersona(this.persona);
            //this.pendiente.setIdPersona(this.persona.getIdPersona());
            this.pendiente.setPersona(persona);
            this.pendiente.setFechaSolic(new Date());
            this.pendiente.setNickname(nick);
            mapPend.guardarPendiente(this.pendiente);
            registroLdap();
            /*FacesMessage msg = new FacesMessage("Registro exitoso", persona.getNombre()+", por favor confirma tu registro accediendo a la URL que hemos enviado a tu email");
            FacesContext.getCurrentInstance().addMessage(null, msg);*///Se modificó por lo que está después de generarEmail
            this.generarEmail();
            /*FacesContext contexto = FacesContext.getCurrentInstance();
            ExternalContext contextoExterno = contexto.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) contextoExterno.getRequest();
            HttpServletResponse response = (HttpServletResponse) contextoExterno.getResponse();
            System.out.println("PATH "+request.getContextPath());
            response.sendRedirect(request.getContextPath()+"/faces/Login/LogIn.xhtml?caso=inscrito");*/
            //Si todo lo hace bien:
            estado="inscrito";
        } catch (Exception ex) {
            
            Logger.getLogger(InscripcionPersona.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage("Error", "Ha ocurrido un error en su registro");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
        return estado;
        
    }
    
    public void registroLdap() throws Exception{
        AccesoLDAP ldap= new AccesoLDAP();
        ldap.InsertarUsuario(persona, this.nick, this.password, "Monedero");
    }
    
    public void traerDocs(){
        DocumentoMapper mapDoc=new DocumentoMapper();
        this.documentos=mapDoc.obtenerDocs();
        /*List<TipoDocumento> tipoDoc=mapDoc.obtenerDocs();
       // tipoDoc=mapDoc.obtenerDocs(); 
        //TipoDocumento docs[]=new TipoDocumento[tipoDoc.size()];
        //tipoDoc.toArray(docs);
        for (int i = 0; i < tipoDoc.size(); i++) {
            //System.out.println("LONGITUD: **** "+tipoDoc.size());
            TipoDocumento doc=tipoDoc.get(i);
            //System.out.println("DOC: **** "+tipoDoc.get(i));
            //System.out.println("DOCUMENTO "+i+1+"--"+((TipoDocumento)tipoDoc.get(i)).getDesDocumento());
        }*/
    }
    
    public void asignarDoc(){
        for (int i = 0; i < this.documentos.size(); i++) {
            if (this.documentos.get(i).getCodigotipoDocumento()==this.persona.getTipoDocumento().getCodigotipoDocumento()) {
                //this.persona.getTipoDocumento().setDesDocumento(this.documentos.get(i).getDesDocumento());
                this.persona.setTipoDocumento(this.documentos.get(i));
            }
        }
    }
    
    public void generarEmail(){
        HashMap datos=new HashMap();
        datos.put("nombre", this.persona.getNombre());
        datos.put("apellido", this.persona.getApellido());
        String codSolicit=Integer.toString(this.getPendiente().getCodSolicitud());
        Cifrado cifra=new Cifrado();
        cifra.addKey(Config.getConfig().getPropiedad("CLAVE_PRIVADA_MENSAJERIA"));
        codSolicit=cifra.encriptar(codSolicit);
        datos.put("url", Config.getConfig().getPropiedad("MONEDERO_URL")+"activar?id="+codSolicit);
        EmailActivacionCuenta email= new EmailActivacionCuenta(this.persona.getEmail());
        email.ensamblarMensaje(datos);
        email.enviarMensaje();
        System.out.println("MENSAJE DESCIFRADO: "+cifra.desencriptar(codSolicit));
        //String mensaje = ConstructorEmail.construirMensaje(datos,template);       
       
    }
    
}
