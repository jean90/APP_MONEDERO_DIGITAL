/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.email;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import ud.ing.modi.config.Config;

/**
 *
 * @author Administrador
 */
public class ConstructorEmail {

    public ConstructorEmail() {
    }
        
    public static String construirMensaje(HashMap datos, String template){       
        String mensaje="";
        VelocityEngine ve= new VelocityEngine();
        Properties p = new Properties();  
        p.setProperty("resource.loader","file");  
        p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");  
        p.setProperty("file.resource.loader.path", Config.getConfig().getPropiedad("EMAIL_PATH_TEMPLATE"));             
        p.setProperty("file.resource.loader.cache", "false");  
        p.setProperty("file.resource.loader.modificationCheckInterval", "0");        
        ve.init(p);          
        
        VelocityContext context = new VelocityContext(datos);
        Template t = ve.getTemplate(template);
        
        StringWriter writer = new StringWriter();
        
        
        System.out.println((String)datos.get("nombre"));
        t.merge(context, writer);
        
        mensaje = writer.toString();
        System.out.println(mensaje);
        return mensaje;
    }

}
