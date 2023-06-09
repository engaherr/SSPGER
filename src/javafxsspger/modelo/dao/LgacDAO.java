/*
* Título del programa: DAO para las LGACs
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 07/06/2023
* Descripción: Clase de acceso a la información para las LGACs lass cuales tienen
* una tabla en la persistencia del sistema
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.LGACRespuesta;
import javafxsspger.utils.Constantes;

public class LGACDAO {
    public static LGACRespuesta obtenerLGACsPorCA(int idCuerpoAcademico){
        LGACRespuesta respuesta = new LGACRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<LGAC> lgacs = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "select idLgac,nombre,numero "
                        + "from lgac where idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    LGAC lgac = new LGAC();
                    lgac.setIdCuerpoAcademico(idCuerpoAcademico);
                    lgac.setIdLgac(resultado.getInt("idLgac"));
                    lgac.setNombre(resultado.getString("nombre"));
                    lgac.setNumero(resultado.getInt("numero"));
                    lgacs.add(lgac);
                }
                conexionBD.close();
                respuesta.setLgacs(lgacs);
            } catch (SQLException ex) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
