/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import ud.ing.modi.config.Config;



public class InicioMonederoDigital extends HttpServlet{

    
    @Override
    public void init(){
        try {
            System.out.println("Iniciando Aplicación---cargando archivo de configuracion");
            Config config;
            config = Config.getConfig();
            config.inicializarConfiguracion(getServletContext().getInitParameter("ficheroConfig"));  
        } catch (IOException ex) {
            Logger.getLogger(InicioMonederoDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
