/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.email;

import java.util.HashMap;

/**
 *
 * @author Administrador
 */
public class EmailTokenPago extends Email{
    
    public EmailTokenPago(){
        super("MONEDERO DIGITAL Token para Pago en línea","envioTokenPago.vm");
    }

    public EmailTokenPago(String destinatario) {
        super("MONEDERO DIGITAL Token para Pago en línea", "envioTokenPago.vm",destinatario);
    }
    
    /*
    Se tiene que definir acorde a la funcionalidad del mensaje
    */
    public void ensamblarMensaje(HashMap datos) {
        this.setDatos(datos);
        this.ensamblarMensaje();
    }
}
