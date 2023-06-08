/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
