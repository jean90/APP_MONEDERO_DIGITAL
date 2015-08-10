/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name="PENDIENTE_ALTA_REGISTRO")
public class PendienteAltaRegistro implements Serializable{
    
    @Id
    @SequenceGenerator( name = "PENDIENTE_ALTA_REGISTRO_SEQ", sequenceName = "PENDIENTE_ALTA_REGISTRO_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy=SEQUENCE, generator ="PENDIENTE_ALTA_REGISTRO_SEQ")
    @Column(name="COD_SOLICITUD")    
    private int codSolicitud;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_CLIENTE")
    private ClienteJuridico cliente;
    @Column(name="FECHA_SOLICITUD")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaSolicitud;

    
    public PendienteAltaRegistro() {
    }
    
    public PendienteAltaRegistro(int cod_solicitud, ClienteJuridico cliente, Date fecha_solicitud) {
        this.codSolicitud = cod_solicitud;
        this.cliente = cliente;
        this.fechaSolicitud = fecha_solicitud;
    }

    public int getCodSolicitud() {
        return codSolicitud;
    }

    public void setCodSolicitud(int cod_solicitud) {
        this.codSolicitud = cod_solicitud;
    }

    public ClienteJuridico getCliente() {
        return cliente;
    }

    public void setCliente(ClienteJuridico cliente) {
        this.cliente = cliente;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fecha_solicitud) {
        this.fechaSolicitud = fecha_solicitud;
    }
    
    /*
    Metodo encargado de la activacion de la cuenta del cliente seleccionado,
    tanto del boorado del registro en la tabla pendiente como la creacion del 
    registro en el LDAP.
    */
    public void activarCliente(){
        
    }
    
    
    /*
    Metodo encargado de el rechazo de la cuenta del cliente.
    */
    public void rechazarActivacion(){
    
    }
}
