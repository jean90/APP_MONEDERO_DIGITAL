/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.sql.ResultSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrador
 */
public class LoginBancoTest {
    
    public LoginBancoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getUserName method, of class LoginBanco.
     */
    @Test
    public void testGetUserName() {
        System.out.println("getUserName");
        LoginBanco instance = new LoginBanco();
        String expResult = "";
        String result = instance.getUserName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUserName method, of class LoginBanco.
     */
    @Test
    public void testSetUserName() {
        System.out.println("setUserName");
        String userName = "";
        LoginBanco instance = new LoginBanco();
        instance.setUserName(userName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPassword method, of class LoginBanco.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        LoginBanco instance = new LoginBanco();
        String expResult = "";
        String result = instance.getPassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPassword method, of class LoginBanco.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        LoginBanco instance = new LoginBanco();
        instance.setPassword(password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class LoginBanco.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        LoginBanco instance = new LoginBanco();
        String expResult = "";
        String result = instance.login();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of conectar method, of class LoginBanco.
     */
    @Test
    public void testConectar() throws Exception {
        System.out.println("conectar");
        LoginBanco instance = new LoginBanco();
        instance.conectar();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consultaBD method, of class LoginBanco.
     */
    @Test
    public void testConsultaBD() throws Exception {
        System.out.println("consultaBD");
        String sql = "";
        LoginBanco instance = new LoginBanco();
        ResultSet expResult = null;
        ResultSet result = instance.consultaBD(sql);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validarUsu method, of class LoginBanco.
     */
    @Test
    public void testValidarUsu() throws Exception {
        System.out.println("validarUsu");
        LoginBanco instance = new LoginBanco();
        boolean expResult = false;
        boolean result = instance.validarUsu();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
