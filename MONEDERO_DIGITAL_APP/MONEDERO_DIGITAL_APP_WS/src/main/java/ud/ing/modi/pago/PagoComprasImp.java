/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.pago;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebParam;
import javax.jws.WebService;
import ud.ing.modi.entidades.Cliente;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.EstadoPago;
import ud.ing.modi.entidades.EstadoToken;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.PagoOnline;
import ud.ing.modi.entidades.TokenPago;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.ldap.TransaccionalLDAP;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.MonederoMapper;
import ud.ing.modi.mapper.PagoOnlineMapper;
import ud.ing.modi.mapper.TokenMapper;
import ud.ing.modi.pagos.GestorPagoOnline;
import ud.ing.modi.seguridad.GeneradorTokenPagos;
import ud.ing.modi.token.GestorToken;


/**
 *
 * @author Administrador
 */
@WebService(endpointInterface = "ud.ing.modi.pago.PagoCompras")
public class PagoComprasImp implements PagoCompras {

    Monedero monederoComprador;
    Monedero monederoTienda;
    ClienteNatural clienteComprador;
    GeneradorTokenPagos generadorToken= new GeneradorTokenPagos();
    GestorPagoOnline gestorPagoOnline;
    GestorToken gestorToken;

    public String[] validarDatosCompra(@WebParam(name = "codMonedero") String codMonedero, @WebParam(name = "nickCliente") String nickCliente,
            @WebParam(name = "codTienda") String codTienda, @WebParam(name = "codCompra") String codCompra,
            @WebParam(name = "valorCompra") String valorCompra) {
        String rta[]=new String [3];
        String mensajeRta = "none";
        String codRta = "000";
        String codPago = "none";
        
        AccesoLDAP ldap=new AccesoLDAP();
        MonederoMapper mapMonedero = new MonederoMapper();
        ClienteMapper mapCliente=new ClienteMapper();

        try{
        
            Cliente tienda=mapCliente.obtenerCliente(codTienda);
            this.clienteComprador = (ClienteNatural)mapCliente.buscarPorNick(nickCliente);
            this.monederoComprador = mapMonedero.buscarMonedero(codMonedero);
            this.monederoTienda=mapMonedero.buscarPorDueno(tienda);

            //1. Se busca tanto en la BD como en el ldap
            if (monederoComprador != null && clienteComprador!=null && ldap.usuarioExiste(nickCliente)) { 
                //2. SE valida que el monedero pertenezca al comprador
                if (this.clienteComprador.getIdCliente() == this.monederoComprador.getClienteDueno().getIdCliente()) {
                    //Monedero pertenece al cliente comprador
                    //3. Se valida el estado de la cuenta del cliente
                    if (clienteComprador.getEstadoCliente().getCodigoEstado() == 2 && ldap.getEstadoCuenta(nickCliente).equals(AccesoLDAP.CUENTA_ACTIVA)) {
                        //Cuenta del cliente ACTIVA
                        //4. Se valida el estado del monedero del comprador
                        if (monederoComprador.getEstado().getCodigoEstado() == 1) {
                            //Monedero del cliente ACTIVO
                            //5. Validar saldo vs valor compra
                            if (monederoComprador.getSaldo()>=Float.parseFloat(valorCompra)) {
                                if (monederoTienda!=null) {
                                System.out.println("Monedero hallado: "+monederoTienda.getCodMonedero());
                                    //6. Validar si la divisa del monedero del cliente coincide con la divisa manejada por la tienda
                                    if (monederoTienda.getDivisa().equals(monederoComprador.getDivisa())) {
                                        //7. Crear token para pago

                                        gestorPagoOnline = new GestorPagoOnline();
                                        PagoOnline pagoOnline = gestorPagoOnline.crearPagoOnline(codCompra, valorCompra, monederoComprador, monederoTienda);
                                        
                                        
                                        //8.crear token para pago online
                                        gestorToken = GestorToken.getInstancia();
                                        TokenPago tokenPago = gestorToken.emitirToken(pagoOnline);
                                        
                                        //9. Enviar token a correo
                                        gestorPagoOnline.enviarCorreoToken(clienteComprador.getPersona(), ((ClienteJuridico)tienda).getRazonSocial(), pagoOnline, tokenPago);
                                        
                                        /*generadorToken.generarToken();
                                        generadorToken.encriptarToken();

                                        //8. Guardar datos token en tabla
                                        generadorToken.guardarRegistroToken(codCompra, valorCompra, monederoComprador, monederoTienda);

                                        //9. Enviar token a correo
                                        generadorToken.enviarCorreoToken(clienteComprador.getPersona(), ((ClienteJuridico)tienda).getRazonSocial(),valorCompra, codMonedero);*/
                                        
                                        codRta = "OK01";
                                        mensajeRta = "Datos para pago válidos.";
                                        codPago=Integer.toString(pagoOnline.getCodPago());
                                        System.out.println("TODO BIEEEN");
                                    }else{
                                        codRta= "EP06";
                                        mensajeRta="Divisa del monedero del comprador no coincide con la divisa manejada por la tienda.";
                                        System.out.println("Divisas no coinciden");
                                    }
                                }else{
                                    codRta = "EP05";
                                    mensajeRta="Código de tienda inválido.";
                                }
                            }else{
                                codRta = "EP04";
                                mensajeRta="Saldo insuficiente en monedero para realizar esta operación.";
                            }
                        } else {
                            codRta = "EV04";
                            mensajeRta = "Su monedero no se encuentra activo.";
                        }
                    } else {
                        codRta = "EV03";
                        mensajeRta = "Su cuenta no se encuentra activa.";
                    }
                } else {
                    codRta = "EV02";
                    mensajeRta= "Datos para pago son inconsistentes. Valida la información.";
                }
            }else{
                codRta = "EV01";
                mensajeRta = "Monedero o Cliente no existentes.";
            }

        }catch(Exception e){
            codRta="EP00";
            mensajeRta="Datos para pago inválidos";
            System.out.println("Ha ocurrido un error de comunicación durante el proceso de pago - "+e.toString());
        }
        
        rta[0]=codRta;
        rta[1]=mensajeRta;
        rta[2]=codPago;
        return rta;

    }

    public String[] pagarCompra(@WebParam(name = "codCompra") String codCompra,@WebParam(name = "token") String token,@WebParam(name = "codPago") String codPago){
        String mensajeRta="none";
        String codRta="000";
        
        String rta[]=new String [3];
        
        PagoOnlineMapper mapPago=new PagoOnlineMapper();
        
        //Monedero monTienda=mapMonedero.buscarMonedero(codMonTienda);
        PagoOnline pago=mapPago.buscarPagoDeCompra(codCompra,Integer.parseInt(codPago));        
        gestorPagoOnline = new GestorPagoOnline();
        
        if (pago!=null) {            
            gestorToken = GestorToken.getInstancia();
            try{                
                boolean isTokenValido = gestorToken.validarToken(pago.getCodPago(), token);
                if(isTokenValido){
                    gestorPagoOnline.finalizarPagoOnline(pago);
                    codPago = Integer.toString(pago.getCodPago());
                    codRta="OK02";
                    mensajeRta="Pago realizado correctamente.";
                }else{
                    gestorPagoOnline.rechazarPagoOnline(pago);
                    codRta="EP01";
                    mensajeRta="Token incorrecto o ha caducado.";
                }
            }catch(Exception e)
            {
                try {
                    gestorPagoOnline.rechazarPagoOnline(pago);
                    codRta="EP00";
                    mensajeRta="Ha ocurrido un error durante el proceso de pago - "+e.toString();
                } catch (Exception ex) {
                    Logger.getLogger(PagoComprasImp.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    codRta="EP00";
                    mensajeRta="Ha ocurrido un error durante el proceso de pago - "+e.toString();
                }
            }
        }else{            
            codRta="EP07";
            mensajeRta="No se ha registrado intento de pago con el código de compra indicado.";
        }
        
        rta[0]=codRta;
        rta[1]=mensajeRta;
        rta[2]=codPago;                
        return rta;
    }
    
}
