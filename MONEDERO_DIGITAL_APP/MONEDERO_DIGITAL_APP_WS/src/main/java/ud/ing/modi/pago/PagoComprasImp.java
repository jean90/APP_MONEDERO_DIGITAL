/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.pago;

import javax.jws.WebParam;
import javax.jws.WebService;
import ud.ing.modi.entidades.ClienteNatural;
import ud.ing.modi.entidades.Monedero;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.MonederoMapper;

/**
 *
 * @author Administrador
 */
@WebService(endpointInterface = "ud.ing.modi.pago.PagoCompras")
public class PagoComprasImp implements PagoCompras {

    Monedero monederoComprador;
    ClienteNatural clienteComprador;

    public String validarDatosCompra(@WebParam(name = "codMonedero") String codMonedero,
            @WebParam(name = "codTienda") String codTienda, @WebParam(name = "codCompra") String codCompra,
            @WebParam(name = "valorCompra") String valorCompra, @WebParam(name = "usuCliente") String usuCliente) {

        String mensajeRta = "none";
        MonederoMapper mapMonedero = new MonederoMapper();
        this.monederoComprador = mapMonedero.buscarMonedero(codMonedero);
        ClienteMapper mapCliente = new ClienteMapper();
        this.clienteComprador = mapCliente.obtenerClienteNatural(usuCliente);
        if (monederoComprador != null || clienteComprador!=null) {
            if (this.clienteComprador.getIdCliente() == this.monederoComprador.getClienteDueno().getIdCliente()) {
                //Monedero pertenece al cliente comprador
                if (clienteComprador.getEstadoCliente().getCodigoEstado() == 2) {
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

}
