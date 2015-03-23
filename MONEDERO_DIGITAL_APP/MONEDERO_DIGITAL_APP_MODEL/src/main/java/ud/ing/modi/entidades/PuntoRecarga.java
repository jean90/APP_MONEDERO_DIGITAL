/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name="PUNTO_RECARGA")
@PrimaryKeyJoinColumn (name = "COD_CLIENTE")
public class PuntoRecarga extends ClienteJuridico implements Serializable{
    private float saldo;
}
