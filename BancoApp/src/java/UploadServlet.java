
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrador
 */
public class UploadServlet extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;
    private String usuario;
    private Connection conexion;

    public void init() {
        // Get the file location where it would be stored.
        filePath
                = getServletContext().getInitParameter("file-upload");
    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, java.io.IOException {
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        if (!isMultipart) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No file uploaded</p>");
            out.println("</body>");
            out.println("</html>");
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("C:\\temporal"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            this.usuario = request.getSession().getAttribute("usuario").toString();
            System.out.println("VALOOOOOR" + request.getSession().getAttribute("usuario"));
            
            out.println("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
"                xmlns:h=\"http://java.sun.com/jsf/html\"\n" +
"                xmlns:f=\"http://java.sun.com/jsf/core\"\n" +
"                xmlns:ui=\"http://java.sun.com/jsf/facelets\"\n" +
"                xmlns:p=\"http://primefaces.org/ui\">");
            out.println("<f:view contentType=\"text/html\">");
            out.println("<h:head>");
            out.println("<meta charset=\"UTF-8\"/>");
            out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"/>");
            out.println("<title>Portal Banco YYY</title>");
            out.println("<link rel=\"stylesheet\" href=\"css/style.css\"/>");
            out.println("</h:head>");
            out.println("<h:body>");
            out.println("<section class=\"container\">");
            out.println("<div class=\"login\">");
            out.println("<h1>Carga Archivo - Banco YYY Portal</h1>");
            out.println("<form  action=\"UploadServlet\" method=\"post\" enctype=\"multipart/form-data\">");
                    Date hoy = new Date();
                    int mes = hoy.getMonth() + 1;
                    int anio = hoy.getYear() + 1900;
                    String fileName = hoy.getDate() + "-" + mes + "-" + anio + "_"+this.usuario+".txt";//fi.getName();
            
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    // Write the file
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath
                                + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath
                                + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
                    out.println("Uploaded Filename: " + fileName + "<br>");
                }
            }

            try {
                leerArchivo();
                out.println("EXITO: El archivo ha sido procesado con éxito, valide el archivo de respuesta.");
            } catch (StringIndexOutOfBoundsException err) {
                // FacesMessage message = new FacesMessage("ERROR", "Ha ocurrido un error en la lectura del archivo. Verifique su formato.");
                //   FacesContext.getCurrentInstance().addMessage(null, message);
                out.println("ERROR: Ha ocurrido un error en la lectura del archivo. Verifique su formato.");
            } catch (SQLException error) {
                out.println("ERROR: No se encuentra el registro.");
            } catch (Exception e) {
                out.println("ERROR: Ha ocurrido un error en el proceso de pago."+e.getMessage());
                System.out.println(e);
            }

            
            out.println("<a href=\"files\\Confirma_"+file.getName()+"\" download=\"Confirma_"+file.getName()+"\">\n" +
"Descargar Archivo\n" +
"</a>");
            out.println("</form>");
            out.println("</div>\n" +
"          </section>");
            out.println("</h:body>");
            out.println("</f:view>");
            out.println("</ui:composition>");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with "
                + getClass().getName() + ": POST method required.");
    }

    public void leerArchivo() throws FileNotFoundException, IOException, StringIndexOutOfBoundsException, SQLException, Exception {

        //El archivo debe venir cuenta origen(10 caracteres), espacio, cuenta destino(10 caracteres), espacio, valor
        String cadena;
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        File confirma=new File("D:\\REPOSITORIO TESIS2\\REPO\\BancoApp\\web\\files","Confirma_"+file.getName());
        
        FileWriter e=new FileWriter(confirma);
        String tipoDoc = new String();
        String numDoc = new String();
        String nombreBenef = new String();
        String banco = new String();
        String tipoCta = new String();
        String numCta = new String();
        String divisa = new String();
        String valorPago = new String();
        String conceptoPago = new String();
        while ((cadena = b.readLine()) != null) {
            System.out.println(cadena);
            String bco="";
            try{
                
            tipoDoc = cadena.substring(0, 2);
            numDoc = cadena.substring(2, 22);
            nombreBenef = cadena.substring(22, 72);
            banco = cadena.substring(72, 76);
            tipoCta = cadena.substring(76, 78);
            numCta = cadena.substring(78, 98);
            divisa = cadena.substring(98, 101);
            valorPago = cadena.substring(101, 114) + "." + cadena.substring(114, 116);
            conceptoPago = cadena.substring(116, 146);

                conectar();
            if (banco.equals("9999")) {
                //La cuenta de la tienda a la cual se realizará el pago, pertenece a este banco, el mismo donde el sistema mondero tiene la suya.
                //Por esto, se validarán tanto los datos de la cuenta y cliente origen como los de cuenta y cliente destino.
                bco="Banco Propio";
                System.out.println("BCNO IGUAL!!");
                procesaTraspasoMismoBanco(tipoCta, numCta, valorPago);
                
            } else {
                System.out.println("OTRO BCNO!!");
                bco="Otro Banco";
                procesaTraspasoOtroBanco(valorPago);
                
                //La cuenta de la tienda a la cual se realizará el pago, pertenece a un banco diferente al de la presente simulación.
                //Por esto, sólo se validan los datos de la cuenta y cliente origen.
            }
                e.write(cadena+" - Traspaso "+bco+" EXITOSO \n");
            }catch(Exception error){
                e.write(cadena+" - Traspaso "+bco+" ERROR "+error.getMessage()+" \n");
                //throw error;
            }
        }
        e.close();
        b.close();
    }

    public void conectar() throws Exception {

        Class.forName("oracle.jdbc.OracleDriver");
        String BaseDeDatos = "jdbc:oracle:thin:@monedero-server:1521:XE";

        conexion = DriverManager.getConnection(BaseDeDatos, "USER_MONEDERO", "qwerty03");
        if (conexion != null) {
            System.out.println("Conexion exitosa!");
        } else {
            System.out.println("Conexion fallida!");
            Exception e = new Exception();
            throw e;
        }

    }

    public ResultSet consultaBD(String sql) throws SQLException {
        System.out.println("Consultando en BD.... " + sql);

        ResultSet resultado = null;
        try {
            Statement sentencia;
            sentencia = this.conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql);
            this.conexion.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return resultado;

    }

    public void modificaBD(String sql) throws SQLException {
        System.out.println("Modificando en BD.... " + sql);

        
        try {
            Statement sentencia;
            sentencia = this.conexion.createStatement();
            sentencia.executeUpdate(sql);
            this.conexion.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        

    }
    
    private void procesaTraspasoMismoBanco(String tipoCta, String numCta, String valorPago) throws SQLException, Exception {
        String query = "SELECT * FROM CUENTA_BANCO WHERE CLIENTE_DUENO_CTA='" + this.usuario + "'";
        ResultSet cuentaOrig = consultaBD(query);
        if (!cuentaOrig.next()) {
            System.out.println("No existe la cuenta origen");
            throw (new Exception("No existe la cuenta origen"));
        } else {

            query = "SELECT * FROM CUENTA_BANCO WHERE NUM_CUENTA='" + numCta + "'";
            ResultSet cuentasDest = consultaBD(query);
            if (!cuentasDest.next()) {
                //return false;
                System.out.println("No hay resultados");
            } else {
                //System.out.println("Resultado.... "+cuentas.getString("DES_DIVISA")); 
                System.out.println("Resultado.... " + cuentasDest.getString("CLIENTE_DUENO_CTA"));
                 if (Float.parseFloat(cuentaOrig.getString("SALDO_CUENTA"))>= Float.parseFloat(valorPago)){
                     query = "UPDATE CUENTA_BANCO SET SALDO_CUENTA="+(Float.parseFloat(cuentaOrig.getString("SALDO_CUENTA"))-Float.parseFloat(valorPago))+ "where CLIENTE_DUENO_CTA='"+this.usuario+"'";
                     modificaBD(query);
                     query = "UPDATE CUENTA_BANCO SET SALDO_CUENTA="+(Float.parseFloat(cuentasDest.getString("SALDO_CUENTA"))+Float.parseFloat(valorPago))+ "where CLIENTE_DUENO_CTA='"+cuentasDest.getString("CLIENTE_DUENO_CTA")+"'";
                     modificaBD(query);
                     
                    System.out.println("Actualización realizada");
                 }else{
                     throw (new Exception("Saldo insuficiente para el traspaso."));
                 }
            }
        }
    }
    
    private void procesaTraspasoOtroBanco(String valorPago) throws SQLException, Exception {
        String query = "SELECT * FROM CUENTA_BANCO WHERE CLIENTE_DUENO_CTA='" + this.usuario + "'";
        ResultSet cuentaOrig = consultaBD(query);
        if (!cuentaOrig.next()) {
            System.out.println("No existe la cuenta origen");
            throw (new Exception("No existe la cuenta origen"));
        } else {
                //System.out.println("Resultado.... "+cuentas.getString("DES_DIVISA")); 
                 if (Float.parseFloat(cuentaOrig.getString("SALDO_CUENTA"))>= Float.parseFloat(valorPago)){
                     query = "UPDATE CUENTA_BANCO SET SALDO_CUENTA="+(Float.parseFloat(cuentaOrig.getString("SALDO_CUENTA"))-Float.parseFloat(valorPago))+ "where CLIENTE_DUENO_CTA='"+this.usuario+"'";
                     modificaBD(query);
                     
                    System.out.println("Actualización realizada");
                 }else{
                     throw (new Exception("Saldo insuficiente para el traspaso."));
                 }
            
        }
    }
    
}


