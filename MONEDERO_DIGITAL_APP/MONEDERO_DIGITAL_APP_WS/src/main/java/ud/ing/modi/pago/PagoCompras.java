/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.pago;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Administrador
 */
@WebService
public interface PagoCompras {

    String[] validarDatosCompra(@WebParam(name = "codMonedero") String codMonedero,@WebParam(name = "nickCliente") String nickCliente,
    @WebParam(name = "codTienda") String codTienda, @WebParam(name = "codCompra") String codCompra,
            @WebParam(name = "valorCompra") String valorCompra);
    
    
    String[] pagarCompra(@WebParam(name = "codCompra") String codCompra,@WebParam(name = "token") String token, @WebParam(name = "codPago") String codPago);
    
}
