/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.dao;

import java.sql.Connection;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.AnteproyectoRespuesta;
import javafxsspger.utils.Constantes;

/**
 *
 * @author kikga
 */
public class AnteproyectoDAO {
    public static void obtenerAnteproyectosDisponibles(){
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "select anteproyecto.idAnteproyecto, nombre_de_trabajo, "
                        + "usuario.nombre, usuario.apellidoPaterno, modalidad, estado from anteproyecto"
                        + "inner join director_de_trabajo_has_anteproyecto on anteproyecto.idAnteproyecto = "
                        + "director_de_trabajo_has_anteproyecto.idAnteproyecto "
                        + "inner join director_de_trabajo on director_de_trabajo.idDirector_de_trabajo = "
                        + "director_de_trabajo_has_anteproyecto.idDirector_de_trabajo"
                        + "inner join usuario on usuario.idUsuario = director_de_trabajo.idUsuario";
            } catch (Exception e) {
            }
        }
    }
}
