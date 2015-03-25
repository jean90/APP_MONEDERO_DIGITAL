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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Lufe
 */
@Entity
@Table(name="PAGO_ONLINE")
public class PagoOnline implements Serializable{
    @Id
    @SequenceGenerator( name = "PAGO_ONLINE_SEQ", sequenceName = "PAGO_ONLINE_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="PAGO_ONLINE_SEQ")
    @Column (name="COD_PAGO")
    private int codPago;
    @Column (name="VALOR_PAGO")
    private float valorPago;
    @Column (name="FECHA_PAGO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPago;
    @Column (name="COD_COMPRA")
    private String codCompra;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_MON_ORIGEN")
    private Monedero monOrigen;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="COD_MON_DESTINO")
    private Monedero monDestino;
}
