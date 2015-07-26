/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.utilidades;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Se tomó de http://www.jc-mouse.net/java/encriptacion-simetrica-en-java
 * @author Lufe
 */
public class Cifrado {
    private SecretKey key;       
    private Cipher cipher;  
    private String algoritmo= "AES";
    private int keysize=16;
    
    public void addKey( String value ){
        byte[] valuebytes = value.getBytes();            
        key = new SecretKeySpec( Arrays.copyOf( valuebytes, keysize ) , algoritmo );      
    }
    
    public String encriptar( String texto ){
        String value="";
        try {
            cipher = Cipher.getInstance( algoritmo );             
            cipher.init( Cipher.ENCRYPT_MODE, key );             
            byte[] textobytes = texto.getBytes();
            byte[] cipherbytes = cipher.doFinal( textobytes );
            //A continuación se modifica para que se cambie el caracter + por otro ya que al ingresar a la url lo toma como un espacio
            value = new BASE64Encoder().encode( cipherbytes );
            System.out.println("CÓDIGO ENCRIPTADO ENVÍO: "+value);
            value = value.replace('+', '-').replace('/', '_');
            System.out.println("CÓDIGO ENCRIPTADO ENVÍO AJUSTADO: "+value);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println( ex.getMessage() );
        } catch (NoSuchPaddingException ex) {
            System.err.println( ex.getMessage() );
        } catch (InvalidKeyException ex) {
            System.err.println( ex.getMessage() );
        } catch (IllegalBlockSizeException ex) {
            System.err.println( ex.getMessage() );
        } catch (BadPaddingException ex) {
            System.err.println( ex.getMessage() );
        }
        return value;
    }
    
    public String desencriptar( String texto ){
        String str="";        
        System.out.println("CÓDIGO ENCRIPTADO RECEPCIÓN: "+texto);
        texto=texto.replace('-', '+').replace('_', '/');
        System.out.println("CÓDIGO ENCRIPTADO RECEPCIÓN AJUSTADO: "+texto);
        try {
            byte[] value = new BASE64Decoder().decodeBuffer(texto);                 
            cipher = Cipher.getInstance( algoritmo );            
            cipher.init( Cipher.DECRYPT_MODE, key );
            byte[] cipherbytes = cipher.doFinal( value );
            str = new String( cipherbytes ); 
        } catch (InvalidKeyException ex) {
            System.err.println( ex.getMessage() );
        }  catch (IllegalBlockSizeException ex) {
            System.err.println( ex.getMessage() );
        } catch (BadPaddingException ex) {
            System.err.println( ex.getMessage() );            
        }   catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }catch (NoSuchAlgorithmException ex) {
            System.err.println( ex.getMessage() );
        } catch (NoSuchPaddingException ex) {
            System.err.println( ex.getMessage() );
        }
        return str;
    }
    
}
