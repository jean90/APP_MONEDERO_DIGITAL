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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrador
 */
@ManagedBean(name = "loginBanco")
@SessionScoped
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

    public String login()  {
        System.out.println("PRUEBA BANCO!!");
        try{
            conectar();
            if (validarUsu()) {
                //FacesMessage message = new FacesMessage("OK", "Usuario existe.");
                //FacesContext.getCurrentInstance().addMessage(null, message);
                System.out.println("ASignar: "+userName);
                HttpServletRequest req=
                (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
                req.getSession().setAttribute("usuario", userName);
                this.conexion.close();
                return "ok";
            }else{
                FacesMessage message = new FacesMessage("ERROR", "Los datos ingresados son incorrectos.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                this.conexion.close();
                return "nok";
            }

            
        }catch(SQLException ex){
            FacesMessage message = new FacesMessage("ERROR", "Error en la comunicación con la base de datos.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }catch(Exception e){
            FacesMessage message = new FacesMessage("ERROR", "Error en el proceso.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
            return null;
    }
    
    public void conectar() throws Exception{
        
            Class.forName("oracle.jdbc.OracleDriver"); 
            String BaseDeDatos = "jdbc:oracle:thin:@monedero-server:1521:XE";  

            conexion = DriverManager.getConnection(BaseDeDatos, "USER_MONEDERO","qwerty03");             
            if (conexion != null) { 
                System.out.println("Conexion exitosa!"); 
            } else { 
                System.out.println("Conexion fallida!"); 
                Exception e=new Exception();
                throw e;
            } 

    }
    
    public ResultSet consultaBD(String sql) throws SQLException{
        System.out.println("Consultando en BD.... "+sql); 
        
        ResultSet resultado = null;
        try {
            Statement sentencia;
            sentencia = this.conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY); 
            resultado = sentencia.executeQuery(sql); 
            this.conexion.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }        return resultado;
    
    }
    
    public boolean validarUsu() throws SQLException{
        //String query="SELECT * FROM USER_MONEDERO.DIVISA WHERE COD_DIVISA=1";
        String query="SELECT * FROM CLIENTE_BANCO WHERE NICK_CLIENTE='"+this.userName+"'";
        ResultSet usuarios=consultaBD(query);
        if (!usuarios.next()) {
            return false;
        }else{
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
