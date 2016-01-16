/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.ServletException;

/**
 *
 * @author Administrador
 */
@ManagedBean(name = "loginBanco")
@RequestScoped
public class LoginBanco implements Serializable{
    private String userName;
    private String password;    
    
    private Connection conexion;
    
    public LoginBanco() {
        System.out.println("contruc");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() throws ServletException {
        System.out.println("PRUEBA BANCO!!");
        conectar();
        try {
            validarUsu();
        } catch (SQLException ex) {
            Logger.getLogger(LoginBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "as";
    }
    
    public void conectar(){
        
        try { 
            Class.forName("oracle.jdbc.OracleDriver"); 
            String BaseDeDatos = "jdbc:oracle:thin:@monedero-server:1521:XE";  

            conexion = DriverManager.getConnection(BaseDeDatos, "USER_MONEDERO","qwerty03");             
            if (conexion != null) { 
                System.out.println("Conexion exitosa!"); 
            } else { 
                System.out.println("Conexion fallida!"); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }
    
    public ResultSet consultaBD(String sql){
        System.out.println("Consultando en BD.... "+sql); 
        
        ResultSet resultado = null;
        try {
            Statement sentencia;
            sentencia = this.conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql);
            this.conexion.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }        return resultado;
    
    }
    
    public boolean validarUsu() throws SQLException{
        //String query="SELECT * FROM USER_MONEDERO.DIVISA WHERE COD_DIVISA=1";
        String query="SELECT * FROM USER_MONEDERO.CLIENTE_BANCO WHERE NICK_CLIENTE='"+this.userName+"';";
        ResultSet usuarios=consultaBD(query);
        if (usuarios.equals(null)) {
            return false;
        }else{
            usuarios.next();
            //System.out.println("Resultado.... "+usuarios.getString("DES_DIVISA")); 
            System.out.println("Resultado.... "+usuarios.getString("PSS_CLIENTE")); 
            if (usuarios.getString("PSS_CLIENTE").equals(this.password)) {
                System.out.println("Coincide.... "+true); 
                return true;
            }else{
                System.out.println("Coincide.... "+false);
                return false;
            }
            
        }
    }
}
