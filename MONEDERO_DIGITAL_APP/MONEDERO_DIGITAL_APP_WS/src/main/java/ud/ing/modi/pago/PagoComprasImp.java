/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.pago;

import java.util.Date;
import javax.jws.WebParam;
import javax.jws.WebService;
import ud.ing.modi.entidades.Cliente;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.entidades.PagoOnline;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.MonederoMapper;
import ud.ing.modi.mapper.PagoOnlineMapper;

/**
 *
 * @author Administrador
 */
@WebService(endpointInterface = "ud.ing.modi.pago.PagoCompras")
public class PagoComprasImp implements PagoCompras {

    Monedero monederoComprador;
    ClienteNatural clienteComprador;

    public String validarDatosCompra(@WebParam(name = "codMonedero") String codMonedero, @WebParam(name = "nickCliente") String nickCliente) {

        String mensajeRta = "none";
        
        AccesoLDAP ldap=new AccesoLDAP();
        MonederoMapper mapMonedero = new MonederoMapper();
        this.clienteComprador = (ClienteNatural)new ClienteMapper().buscarPorNick(nickCliente);
        this.monederoComprador = mapMonedero.buscarMonedero(codMonedero);
        
        if (monederoComprador != null && clienteComprador!=null && ldap.usuarioExiste(nickCliente)) { //Se busca tanto en la BD como en el ldap
            if (this.clienteComprador.getIdCliente() == this.monederoComprador.getClienteDueno().getIdCliente()) {
                //Monedero pertenece al cliente comprador
                if (clienteComprador.getEstadoCliente().getCodigoEstado() == 2 && ldap.getEstadoCuenta(nickCliente).equals(AccesoLDAP.CUENTA_ACTIVA)) {
                    //Cuenta del cliente ACTIVA
                    if (monederoComprador.getEstado().getCodigoEstado() == 1) {
                        //Monedero del cliente ACTIVO
                        mensajeRta = "OK: Datos para pago válidos.";
                    } else {
                        mensajeRta = "ERROR: Su monedero no se encuentra activo.";
                    }
                } else {
                    mensajeRta = "ERROR: Su cuenta no se encuentra activa.";
                }
            } else {
                mensajeRta = "ERROR: Datos para pago son inconsistentes. Valida la información.";
            }
        }else{
            mensajeRta = "ERROR: Monedero o Cliente no existentes.";
        }

        return mensajeRta;

    }

    public String pagarCompra(@WebParam(name = "codMonedero") String codMonedero,
            @WebParam(name = "codTienda") String codTienda, @WebParam(name = "codCompra") String codCompra,
            @WebParam(name = "valorCompra") String valorCompra, @WebParam(name = "nickCliente") String nickCliente){
        String mensajeRta="none";
        
        Monedero monederoTienda=null;
        MonederoMapper mapMonedero=new MonederoMapper();
        ClienteMapper mapCliente=new ClienteMapper();
        Cliente tienda=mapCliente.obtenerCliente(codTienda);
        PagoOnlineMapper mapPago=new PagoOnlineMapper();
        
        try{
            
        
        this.monederoComprador = mapMonedero.buscarMonedero(codMonedero);
        
        //1. Validar saldo vs valor compra
        if (monederoComprador.getSaldo()>=Float.parseFloat(valorCompra)) {
        //2. Insertar en la tabla el pago
            PagoOnline pago=new PagoOnline();
            pago.setCodCompra(codCompra);
            pago.setFechaPago(new Date());
            pago.setValorPago(Float.parseFloat(valorCompra));
            pago.setMonOrigen(monederoComprador);
            monederoTienda=mapMonedero.buscarPorDueno(tienda);
            pago.setMonDestino(monederoTienda);
            
            if (monederoTienda!=null) {
                System.out.println("Monedero hallado: "+monederoTienda.getCodMonedero());
        //3. Realizar abono en el monedero de la tienda
                mapMonedero.abonarPago(Float.parseFloat(valorCompra),monederoTienda);
                System.out.println("Pago abonado");
        //4. Debitar el monedero del cliente comprador
                mapMonedero.debitarPago(Float.parseFloat(valorCompra),monederoComprador);
                System.out.println("Pago debitado");
        //5. Registrar pago en tabla
                mapPago.registrarPago(pago);
                System.out.println("Pago registrado");
                mensajeRta="OK: Vamos bien.";
            }else{
                mensajeRta="ERROR: Código de tienda inválido.";
            }
        }else{
            mensajeRta="ERROR: Saldo insuficiente en monedero para realizar esta operación.";
        }
        }catch(Exception e)
        {
            mensajeRta="ERROR: Ha ocurrido un error durante el proceso de pago - "+e.getMessage();
        }
        //6. Notificar vía email
        return mensajeRta;
    }
    
}
