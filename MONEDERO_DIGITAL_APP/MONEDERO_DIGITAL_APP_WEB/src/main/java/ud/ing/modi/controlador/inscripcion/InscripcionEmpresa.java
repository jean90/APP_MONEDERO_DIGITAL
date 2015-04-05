/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.controlador.inscripcion;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.event.FlowEvent;
import ud.ing.modi.config.Config;
import ud.ing.modi.email.EmailActivacionCuenta;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.EstadoCliente;
import ud.ing.modi.entidades.PendienteAltaRegistro;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.entidades.PuntoRecarga;
import ud.ing.modi.entidades.TiendaOnLine;
import ud.ing.modi.entidades.TipoDocumento;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.DocumentoMapper;
import ud.ing.modi.mapper.EstadoClienteMapper;
import ud.ing.modi.mapper.PendienteAltaRegistroMapper;
import ud.ing.modi.utilidades.Cifrado;

/**
 *
 * @author Administrador
 */
@ManagedBean(name = "inscripcionEmpresa")
@ViewScoped
public class InscripcionEmpresa implements Serializable {

    
    private ClienteJuridico cJuridico;
    private Persona representante;
    private List<TipoDocumento> listaDocumentos;
    private TipoDocumento documento;
    private EstadoCliente eCliente;
    private String tipoCliente;
    private String password;
    private String nick;

    public static final String TIENDA_ONLINE = "1";
    public static final String DES_TIENDA_ONLINE = "TiendaOnline";
    public static final String PUNTO_RECARGA = "2";
    public static final String DES_PUNTO_RECARGA = "PuntoRecarga";
    
    public static final double SALDO_INICIAL = 0.0;

    /**
     * Creates a new instance of InscripcionEmpresa
     */
    public InscripcionEmpresa() {
        cJuridico=new ClienteJuridico();
        representante=new Persona();
        traerDocs();
    }    

    public ClienteJuridico getcJuridico() {
        return cJuridico;
    }

    public void setcJuridico(ClienteJuridico cJuridico) {
        this.cJuridico = cJuridico;
    }

    public Persona getRepresentante() {
        return representante;
    }

    public void setRepresentante(Persona representante) {
        this.representante = representante;
    }

    public List<TipoDocumento> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<TipoDocumento> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public TipoDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(TipoDocumento documento) {
        this.documento = documento;
    }

    public EstadoCliente geteCliente() {
        return eCliente;
    }

    public void seteCliente(EstadoCliente eCliente) {
        this.eCliente = eCliente;
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

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    
    
    
    public String onFlowProcess(FlowEvent event) {
        String evento = event.getNewStep();
        System.out.println("Evento: " + evento);
        if (evento.equals("confirma")) {
            this.asignarDoc();
        }
        return evento;
    }

    private void traerDocs() {
        DocumentoMapper mapDoc = new DocumentoMapper();
        this.listaDocumentos = mapDoc.obtenerDocs();
    }

    public void asignarDoc() {
        for (int i = 0; i < this.listaDocumentos.size(); i++) {
            if (this.listaDocumentos.get(i).getCodigotipoDocumento() == this.representante.getTipoDocumento().getCodigotipoDocumento()) {
                //this.persona.getTipoDocumento().setDesDocumento(this.documentos.get(i).getDesDocumento());
                this.representante.setTipoDocumento(this.listaDocumentos.get(i));
            }
        }
    }
    
    public String save(){        
        String estado="fallo";
        try {
            ClienteJuridico cliente = null;
            if(this.tipoCliente.equals(TIENDA_ONLINE)){
                cliente = crearTiendaOnline();
            }else if(this.tipoCliente.equals(PUNTO_RECARGA)){
                cliente = crearPuntoRecarga();
            }        
            PendienteAltaRegistroMapper penAltaMapper = new PendienteAltaRegistroMapper();
            PendienteAltaRegistro penAlta = new PendienteAltaRegistro();
            penAlta.setCliente(cliente);
            penAlta.setFechaSolicitud(new Date()); 
            penAltaMapper.guardarPendienteAltaRegistro(penAlta);  
            estado="inscritoEmpresa";
        }catch (Exception ex) {            
            Logger.getLogger(InscripcionPersona.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage("Error", "Ha ocurrido un error en su registro");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        System.out.println("estado de salida despues de la inscripcion: " + estado);
        return estado;
    }
    
    public PuntoRecarga crearPuntoRecarga() throws Exception{
        EstadoClienteMapper eClienteMapper = new EstadoClienteMapper();
        EstadoCliente eCliente = eClienteMapper.obtenerEstadoCliente("1");
        PuntoRecarga puntoRecarga = new PuntoRecarga();
        puntoRecarga.setEstadoCliente(eCliente);
        puntoRecarga.setRepresentante(this.representante);
        puntoRecarga.setDireccion(this.cJuridico.getDireccion());
        puntoRecarga.setFechaAlta(new Date());
        puntoRecarga.setNickname(nick);
        puntoRecarga.setNit(this.cJuridico.getNit());
        puntoRecarga.setRazonSocial(this.cJuridico.getRazonSocial());
        puntoRecarga.setSaldo(SALDO_INICIAL);
        puntoRecarga.setTelefono(this.cJuridico.getTelefono());
        ClienteMapper cMapper = new ClienteMapper();
        cMapper.guardarPuntoRecarga(puntoRecarga);
        registroLdap(PUNTO_RECARGA);
        return puntoRecarga;
    }
    
    public TiendaOnLine crearTiendaOnline() throws Exception{
        EstadoClienteMapper eClienteMapper = new EstadoClienteMapper();
        EstadoCliente eCliente = eClienteMapper.obtenerEstadoCliente("1");
        TiendaOnLine tiendaOnline = new TiendaOnLine();
        tiendaOnline.setEstadoCliente(eCliente);
        tiendaOnline.setRepresentante(this.representante);
        tiendaOnline.setDireccion(this.cJuridico.getDireccion());
        tiendaOnline.setFechaAlta(new Date());
        tiendaOnline.setNickname(nick);
        tiendaOnline.setNit(this.cJuridico.getNit());
        tiendaOnline.setRazonSocial(this.cJuridico.getRazonSocial());
        tiendaOnline.setTelefono(this.cJuridico.getTelefono());
        ClienteMapper cMapper = new ClienteMapper();
        cMapper.guardarTiendaOnline(tiendaOnline);
        registroLdap(TIENDA_ONLINE);
        return tiendaOnline;
    }
    /*
    Metodo que validala existencia del nickName deseado por el usuario que se encuentra realizando el registro.
    */
    public void validaUsuLdap(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        AccesoLDAP ldap=new AccesoLDAP();
        System.out.println("Buscando usuario "+arg2.toString()+" en el ldap");
        if (ldap.buscarUsuario((String)arg2)) {
         throw new ValidatorException(new FacesMessage("Nickname no disponible"));
      }
    }
    
    
    public void registroLdap(String tipoCliente) throws Exception{
        AccesoLDAP ldap= new AccesoLDAP();
        if(tipoCliente.equals(PUNTO_RECARGA)){
            ldap.InsertarUsuario(representante, this.nick, this.password, DES_PUNTO_RECARGA);
        }else if(tipoCliente.equals(TIENDA_ONLINE)){
            ldap.InsertarUsuario(representante, this.nick, this.password, DES_TIENDA_ONLINE);
        }        
    }
    
    /*
    metodo encargado de enviar el email al representante legal 
    de la empresa que se registrò enel sistema de monedero digital.
    */    
    public void generarEmail(){
        //HashMap datos=new HashMap();
        //datos.put("nombre", this.persona.getNombre());
        //datos.put("apellido", this.persona.getApellido());
        //String codSolicit=Integer.toString(this.getPendiente().getCodSolicitud());
        //Cifrado cifra=new Cifrado();
        //cifra.addKey(Config.getConfig().getPropiedad("CLAVE_PRIVADA_MENSAJERIA"));
        //codSolicit=cifra.encriptar(codSolicit);
        //datos.put("url", Config.getConfig().getPropiedad("MONEDERO_URL")+"activar?id="+codSolicit);
        //EmailActivacionCuenta email= new EmailActivacionCuenta(this.persona.getEmail());
        //email.ensamblarMensaje(datos);
        //email.enviarMensaje();
        //System.out.println("MENSAJE DESCIFRADO: "+cifra.desencriptar(codSolicit));
        //String mensaje = ConstructorEmail.construirMensaje(datos,template);       
       
    }
    
}
