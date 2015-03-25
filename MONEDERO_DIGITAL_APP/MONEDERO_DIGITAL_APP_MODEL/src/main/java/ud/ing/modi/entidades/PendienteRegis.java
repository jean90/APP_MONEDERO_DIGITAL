package ud.ing.modi.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="PENDIENTE_REGIS")
public class PendienteRegis implements Serializable{
    @Id
    @SequenceGenerator( name = "PENDIENTE_REGIS_SEQ", sequenceName = "PENDIENTE_REGIS_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="PENDIENTE_REGIS_SEQ")
    @Column(name="COD_SOLICITUD")    
    private int codSolicitud;
    //@Column(name="ID_PERSONA")
    //private int idPersona;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="ID_PERSONA")
    private Persona persona;
    @Column(name="FECHA_SOLIC")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaSolic;
    @Column(name="NICKNAME")    
    private String nickname;

    public PendienteRegis() {
        persona=new Persona();
    }

    public int getCodSolicitud() {
        return codSolicitud;
    }

    public void setCodSolicitud(int codSolicitud) {
        this.codSolicitud = codSolicitud;
    }

    /*public int getIdPersona() {
    return idPersona;
    }
    public void setIdPersona(int idPersona) {
    this.idPersona = idPersona;
    }*/
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Date getFechaSolic() {
        return fechaSolic;
    }

    public void setFechaSolic(Date fechaSolic) {
        this.fechaSolic = fechaSolic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    
    
}
