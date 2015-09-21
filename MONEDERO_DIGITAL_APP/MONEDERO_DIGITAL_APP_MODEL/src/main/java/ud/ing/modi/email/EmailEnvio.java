/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.email;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import ud.ing.modi.config.Config;


/**
 *
 * @author Lufe
 */
public class EmailEnvio implements Serializable{


    
    public EmailEnvio() {        
    }
    
    public static void enviarMail(String destinatario, String mensaje,String asunto){
        System.out.println("Método! yuhuuuu "+destinatario);
        Session sesion=Session.getInstance(inicializarPropiedades(), new javax.mail.Authenticator(){
        protected PasswordAuthentication getPasswordAuthentication(){
           return new PasswordAuthentication(Config.getConfig().getPropiedad("EMAIL_CUENTA"),Config.getConfig().getPropiedad("EMAIL_PASSWORD")); 
           }
        });
        
        try{
            Message mes=new MimeMessage(sesion);
            mes.setFrom(new InternetAddress(Config.getConfig().getPropiedad("EMAIL_CUENTA")));
            mes.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mes.setSubject(asunto);
            mes.setText(mensaje);                
            Transport.send(mes);                
        }catch(MessagingException e){
            System.out.println("Error "+e);
        }
    }
    
    private static Properties inicializarPropiedades(){
        Properties props;
        props=new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", Config.getConfig().getPropiedad("EMAIL_HOST"));
        props.put("mail.smtp.port", Config.getConfig().getPropiedad("EMAIL_PORT"));
        return props;
    }
    
}
