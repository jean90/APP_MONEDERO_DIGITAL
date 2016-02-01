package ud.ing.modi.controlador.login;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import ud.ing.modi.email.EmailActivacionCuenta;
import ud.ing.modi.entidades.PendienteRegis;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.mapper.PendientesMapper;

/**
 *
 * @author Administrador
 */
@ManagedBean
@RequestScoped
public class MonederoLogIn {

    private String userName;
    private String password;
    private String estadoCuenta;

    /**
     * Creates a new instance of MonederoLogIn
     */
    public MonederoLogIn() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Este método realiza las validaciones necesarias para que un usuario se pueda loggear.
     * @return Se retorna como resultado el rol asociado al usuario validado.
     * @throws ServletException 
     */
    public String login() throws ServletException {
        String rol = "error";
        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contexto.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) contextoExterno.getRequest();
        try {
            if (validarEstadoCuenta(userName)) {
                request.login(userName, password);
                inicializarIntentosConexion(userName);
                //System.out.println("ENTRA A LOGIN validada la cuenta");
                //System.out.println("pinto .. "+request.getRemoteUser()+" - "+request.getUserPrincipal());
                /*String[] roles=((GenericPrincipal)request.getUserPrincipal()).getRoles();
                for (int i = 0; i < roles.length; i++) {
                    System.out.println(roles[i]+" + ");
                }*/
                if (request.isUserInRole("Admin")) {
                    rol = "Admin";
                } else if (request.isUserInRole("Monedero")) {
                    rol = "Monedero";
                } else if (request.isUserInRole("PuntoRecarga")) {
                    rol = "PuntoRecarga";
                } else if (request.isUserInRole("TiendaOnline")) {
                    rol = "TiendaOnline";
                } else if (request.isUserInRole("") || rol.equals("error")) {
                    System.out.println("SIN ROL -------------");
                    request.logout();
                }
                String nombreFact="D:\\REPOSITORIO TESIS2\\REPO\\MONEDERO_DIGITAL_APP\\MONEDERO_DIGITAL_APP_WEB\\src\\main\\webapp\\docs\\FACTURA_"+this.userName+".pdf";
                File file=new File(nombreFact);
                file.delete();
            } 
        } catch (ServletException ex) {
            System.out.println("ERROR DE LOGGEO ");
            AccesoLDAP ldap = new AccesoLDAP();
            if (ldap.usuarioExiste(userName)) {
                /*if (!ldap.validarPassword(userName, password)) {
                }*/
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña Incorrecta", "Verifique sus datos"));
                validarBloqueoCuenta(userName);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario No Existe!", "Verifique sus datos"));
            }
            //Logger.getLogger(MonederoLogIn.class.getName()).log(Level.WARNING, null, ex);
            request.logout();
        }
        System.out.println("ROL:---" + rol);
        return rol;
    }

    public void pruebaLdap() {
        /*ConexionLdap lc = new ConexionLdap();
         lc.ConexionManager("admin", "123456");
         lc.CerrarConLDAP(lc.getLc());*/
        Persona per = new Persona();
        per.setApellido("Gualtero");
        per.setNombre("David");
        AccesoLDAP ldap = new AccesoLDAP();
        //ldap.InsertarUsuario(per, "10003", "123456");
        ldap.buscarUsuario(this.userName);

    }

   /* public void pruebaPendientes() {
        PendienteRegis pendiente = new PendienteRegis();
        pendiente.setIdPersona(1);
        pendiente.setFechaSolic(new Date());
        PendientesMapper mapper = new PendientesMapper();
        try {
            mapper.guardarPendiente(pendiente);
        } catch (Exception ex) {
            Logger.getLogger(MonederoLogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    public String logOut() throws ServletException {
        System.out.println("CERRAR SESION");
        
        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contexto.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) contextoExterno.getRequest();
        request.logout();
        return "/LogIn/LogIn.xhtml";
    }

    public void generarEmail() {
        HashMap datos = new HashMap();
        datos.put("nombre", "Alejandro");
        datos.put("apellido", "Silva");
        datos.put("url", "http://localhost:8080/MONEDERO_DIGITAL_APP_WEB/activar?id=123456");
        EmailActivacionCuenta email = new EmailActivacionCuenta("nxtreo@gmail.com");
        email.ensamblarMensaje(datos);
        email.enviarMensaje();
        //String mensaje = ConstructorEmail.construirMensaje(datos,template);             
    }

    private boolean validarEstadoCuenta(String usuario) {
        boolean estadoCuentaActivo = false;
        String estadoCuenta;
        AccesoLDAP ldap = new AccesoLDAP();
       // System.out.println("GRUPOS**************** "+ldap.getGrupos());
        estadoCuenta = ldap.getEstadoCuenta(usuario);
       // System.out.println("estado Cuenta = " + estadoCuenta);
        if (estadoCuenta.equals(AccesoLDAP.CUENTA_ACTIVA)) {
            estadoCuentaActivo = true;
        } else if (estadoCuenta.equals(AccesoLDAP.CUENTA_BLOQUEDA)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "CUENTA BLOQUEADA", "Verifique sus datos"));
        } else if (estadoCuenta.equals(AccesoLDAP.CUENTA_PENDIENTE_ACTIVACION)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "CUENTA DE USUARIO SE ENCUENTRA PENDIENTE DE ACTIVACION", "Verifique sus datos"));
        } else if (estadoCuenta.equals(AccesoLDAP.CUENTA_CANCELADA)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "CUENTA DE USUARIO SE ENCUENTRA CANCELADA", "Ya no puede acceder con esta cuenta"));
        }
        return estadoCuentaActivo;
    }

    private void validarBloqueoCuenta(String usuario) {
        int numIntentosConexion;
        AccesoLDAP ldap = new AccesoLDAP();
        numIntentosConexion = Integer.parseInt(ldap.getNumIntentosConexion(usuario));
        numIntentosConexion++;
        ldap.modificarIntentosConexion(usuario, Integer.toString(numIntentosConexion));
        if (numIntentosConexion > 2) {
            //BLOQUEAR CUENTA
            System.out.println("BLOQUEANDO CUENTA");
            ldap.modificarEstadoCuenta(usuario, AccesoLDAP.CUENTA_BLOQUEDA);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "CUENTA BLOQUEADA", null));
        }
    }

    private void inicializarIntentosConexion(String usuario) {
        int numIntentosConexion = 0;
        AccesoLDAP ldap = new AccesoLDAP();
        ldap.modificarIntentosConexion(usuario, Integer.toString(numIntentosConexion));
    }
}
