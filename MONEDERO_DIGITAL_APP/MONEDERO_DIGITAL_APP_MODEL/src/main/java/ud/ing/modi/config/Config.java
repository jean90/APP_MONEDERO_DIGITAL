/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Administrador
 */
public class Config {    
    private Properties propiedades;
    private static Config config;

    public Config(){
        
    }

    public static Config getConfig(){
        if (config==null){
            config=new Config();
        }
        return config;
    }
    
    public void inicializarConfiguracion(String path) throws FileNotFoundException, IOException{                
        System.out.println("Iniciando la lectura del fichero de configuracion: "+path);        
        propiedades=new Properties();
        InputStream in = new FileInputStream(path);
        propiedades.load(in);   
        System.out.println("Archivo de configuracion "+path+" cargado correctamente.");  
    }
    
    public String getPropiedad(String propiedad){
        return this.propiedades.getProperty(propiedad);
    }
}
