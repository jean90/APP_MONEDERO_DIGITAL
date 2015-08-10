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
import ud.ing.modi.email.EmailCreacionCuentaEmpresa;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.Divisa;
import ud.ing.modi.entidades.EntidadFinanciera;
import ud.ing.modi.entidades.EstadoCliente;
import ud.ing.modi.entidades.PendienteAltaRegistro;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.entidades.PuntoRecarga;
import ud.ing.modi.entidades.TiendaOnLine;
import ud.ing.modi.entidades.TipoCuentaBancaria;
import ud.ing.modi.entidades.TipoDocumento;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.DivisaMapper;
import ud.ing.modi.mapper.DocumentoMapper;
import ud.ing.modi.mapper.EntidadFinancieraMapper;
import ud.ing.modi.mapper.EstadoClienteMapper;
import ud.ing.modi.mapper.PendienteAltaRegistroMapper;
import ud.ing.modi.mapper.TipoCuentaMapper;

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
    private List<EntidadFinanciera> listaEntidades;
    private EntidadFinanciera entidadFinanciera;
    private List<TipoCuentaBancaria> listaTiposCuentas;
    private TipoCuentaBancaria tipoCuentaBancaria;
    private List<Divisa> listaDivisas;
    private Divisa divisa;
    private String numCuentaBancaria;
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
        obtenerEntidadesFinancieras();
        obtenerTiposCuentasBancarias();
        obtenerDivisas();
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
    
    public List<EntidadFinanciera> getListaEntidades() {
        return listaEntidades;
    }

    public void setListaEntidades(List<EntidadFinanciera> listaEstidades) {
        this.listaEntidades = listaEstidades;
    }

    public EntidadFinanciera getEntidadFinanciera() {
        return entidadFinanciera;
    }

    public void setEntidadFinanciera(EntidadFinanciera entidadFinanciera) {
        this.entidadFinanciera = entidadFinanciera;
    }

    public List<TipoCuentaBancaria> getListaTiposCuentas() {
        return listaTiposCuentas;
    }

    public void setListaTiposCuentas(List<TipoCuentaBancaria> listaTiposCuentas) {
        this.listaTiposCuentas = listaTiposCuentas;
    }

    public TipoCuentaBancaria getTipoCuentaBancaria() {
        return tipoCuentaBancaria;
    }

    public void setTipoCuentaBancaria(TipoCuentaBancaria tipoCuentaBancaria) {
        this.tipoCuentaBancaria = tipoCuentaBancaria;
    }

    public String getNumCuentaBancaria() {
        return numCuentaBancaria;
    }

    public void setNumCuentaBancaria(String numCuentaBancaria) {
        this.numCuentaBancaria = numCuentaBancaria;
    }

    public List<Divisa> getListaDivisas() {
        return listaDivisas;
    }

    public void setListaDivisas(List<Divisa> listaDivisas) {
        this.listaDivisas = listaDivisas;
    }

    public Divisa getDivisa() {
        return divisa;
    }

    public void setDivisa(Divisa divisa) {
        this.divisa = divisa;
    }
    
    
    
    public String onFlowProcess(FlowEvent event) {
        
        String eventoViejo = event.getOldStep();
        String eventoNuevo = event.getNewStep();    

        if(eventoViejo.equals("empresarial")&&eventoNuevo.equals("infBanco")){            
            if (this.tipoCliente.equals(PUNTO_RECARGA)){
                eventoNuevo = "personal";
            }            
        }else if(eventoViejo.equals("personal")&&eventoNuevo.equals("infBanco")){
            if (this.tipoCliente.equals(PUNTO_RECARGA)){
                eventoNuevo = "empresarial";
            }
        }else if (eventoNuevo.equals("confirma")) {
            this.asignarDoc();
        }
        System.out.println(eventoViejo);
        System.out.println(eventoNuevo);
        return eventoNuevo;
    }

    private void traerDocs() {
        DocumentoMapper mapDoc = new DocumentoMapper();
        this.listaDocumentos = mapDoc.obtenerDocs();
    }

    public void obtenerEntidadesFinancieras(){
        EntidadFinancieraMapper eMapper = new EntidadFinancieraMapper();
        listaEntidades = eMapper.obtenerEntidadesFinaciera();
    }
    
    public void obtenerTiposCuentasBancarias(){
        TipoCuentaMapper tMapper = new TipoCuentaMapper();
        listaTiposCuentas = tMapper.obtenerTiposCuentasBancarias();
    }
    
    public void obtenerDivisas(){
        DivisaMapper dMapper = new DivisaMapper();
        this.listaDivisas = dMapper.obtenerDivisas();
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
            generarEmail();
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
        tiendaOnline.setNumCuentaBancaria(numCuentaBancaria);
        tiendaOnline.setBanco(entidadFinanciera);
        tiendaOnline.setTipoCuentaBancaria(tipoCuentaBancaria);
        tiendaOnline.setDivisa(divisa);
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
        HashMap datos=new HashMap();
        datos.put("nombre", this.representante.getNombre());
        datos.put("apellido", this.representante.getApellido());
        datos.put("razonSocial", this.cJuridico.getRazonSocial());       
        EmailCreacionCuentaEmpresa email = new EmailCreacionCuentaEmpresa(this.representante.getEmail());
        email.ensamblarMensaje(datos);
        email.enviarMensaje();
    }
    
    public boolean validaTipoEmpresa(){
        System.out.println("INICIO validaTipoEmpresa()");
        boolean respuesta = false;
        if(this.tipoCliente!=null&&!this.tipoCliente.equals("")){
            System.out.println("valor tipoCliente" + this.tipoCliente);
            if(tipoCliente.equals(TIENDA_ONLINE)){
                respuesta=true;
            }
        }
        System.out.println("FIN validaTipoEmpresa()");
        return respuesta;
    }
    


    
    
}
