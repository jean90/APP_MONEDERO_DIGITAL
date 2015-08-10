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
public class EmailCreacionCuentaEmpresa extends Email{

    public EmailCreacionCuentaEmpresa(){
        super("MONEDERO DIGITAL Creación de Cuenta Empresarial","peticionCuentaEmpresarial.vm");
    }

    public EmailCreacionCuentaEmpresa(String destinatario) {
        super("MONEDERO DIGITAL Creación de Cuenta Empresarial", "peticionCuentaEmpresarial.vm",destinatario);
    }

    public void ensamblarMensaje(HashMap datos) {
        this.setDatos(datos);
        this.ensamblarMensaje();
    }
    
    
}
