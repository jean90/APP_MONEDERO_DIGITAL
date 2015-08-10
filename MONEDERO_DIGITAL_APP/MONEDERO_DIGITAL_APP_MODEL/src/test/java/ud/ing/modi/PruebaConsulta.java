/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi;

import java.util.List;
import ud.ing.modi.entidades.PendienteAltaRegistro;
import ud.ing.modi.entidades.PuntoRecarga;
import ud.ing.modi.entidades.TiendaOnLine;
import ud.ing.modi.mapper.PendienteAltaRegistroMapper;

/**
 *
 * @author Administrador
 */
public class PruebaConsulta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PendienteAltaRegistroMapper pResgistro = new PendienteAltaRegistroMapper();
        List<TiendaOnLine> tiendasOnline = pResgistro.obtenerTiendasOnlinePendientes();
        for(int i = 0; i < tiendasOnline.size() ; i ++){
            TiendaOnLine tOnline = tiendasOnline.get(i);
            System.out.println("nit:" + tOnline.getNit());
            
        }
        
        List<PuntoRecarga> puntosRecarga = pResgistro.obtenerPuntosRecargasPendientes();
        for(int i = 0; i < puntosRecarga.size(); i++){
            PuntoRecarga punto = puntosRecarga.get(i);
            System.err.println("jojojoj" + punto.getRepresentante().getNombre());
        }
    }
    
}
