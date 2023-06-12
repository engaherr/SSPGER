/*
* Título del programa: AvanceDAO
* Autor: Omar Dylan Segura Platas
* Fecha: 09/06/2023
* Descripción: Se encarga de la correcta conexión y obtención de datos de la base de datos.
*/

package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.modelo.pojo.AvanceRespuesta;
import javafxsspger.utils.Constantes;


public class AvanceDAO {
    
    public static AvanceRespuesta consultarAvances() {
    AvanceRespuesta respuesta = new AvanceRespuesta();
    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
    ArrayList<Avance> avances = new ArrayList<>();
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if (conexionBD != null) {
        try {
            String consulta = "SELECT a.idAnteproyecto, a.nombreTrabajo, COUNT(DISTINCT act.idActividad) AS totalActividades, COUNT(ent.idEntrega) AS totalEntregas\n"
                    + "FROM anteproyecto a\n"
                    + "LEFT JOIN actividad act ON a.idAnteproyecto = act.idAnteproyecto\n"
                    + "LEFT JOIN entrega ent ON act.idActividad = ent.idActividad\n"
                    + "GROUP BY a.idAnteproyecto, a.nombreTrabajo;";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = prepararSentencia.executeQuery();
            while (resultado.next()) {
                Avance avance = new Avance();
                avance.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                avance.setNombreTrabajo(resultado.getString("nombreTrabajo"));
                avance.setCantidadActividades(resultado.getInt("totalActividades"));
                avance.setCantidadRegistros(resultado.getInt("totalEntregas"));
                avances.add(avance);
            }
            respuesta.setAvances(avances);
            conexionBD.close();
        } catch (SQLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
    } else {
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
    }
    return respuesta;
}

}
