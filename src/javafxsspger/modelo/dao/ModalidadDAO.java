/*
* Título del programa: DAO para las modalidades
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 07/06/2023
* Descripción: Clase de acceso a la información para las Modalidades de los anteproyectos
* las cuales tienen una tabla en la persistencia del sistema
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Modalidad;
import javafxsspger.modelo.pojo.ModalidadRespuesta;
import javafxsspger.utils.Constantes;

public class ModalidadDAO {
    public static ModalidadRespuesta obtenerModalidades(){
        ModalidadRespuesta respuesta = new ModalidadRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Modalidad> modalidades = new ArrayList();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "select * from modalidad";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Modalidad modalidad = new Modalidad();
                    modalidad.setIdModalidad(resultado.getInt("idModalidad"));
                    modalidad.setModalidad(resultado.getString("modalidad"));
                    modalidades.add(modalidad);
                }
                conexionBD.close();
                respuesta.setModalidades(modalidades);
            } catch (SQLException ex) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
