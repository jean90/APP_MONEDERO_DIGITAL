/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.mapper;

import java.util.List;
import ud.ing.modi.entidades.FacturaRecargas;

/**
 *
 * @author Administrador
 */
public class FacturaRecargasMapper extends Mapper {

    public void guardarListaFacturas(List<FacturaRecargas> listaFacturas) {
        iniciaOperacion();
        int tamListaFacturas = listaFacturas.size();
        for (int i = 0; i < tamListaFacturas; i++) {
            getSesion().save(listaFacturas.get(i));
            if (i % 20 == 0) { //20, same as the JDBC batch size
                //flush a batch of inserts and release memory:
                getSesion().flush();
                getSesion().clear();
            }
        }
        getTx().commit();
        getSesion().close();
    }

}
