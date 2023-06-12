/*
* Titulo del programa: Estudiante DAO
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 11/06/2023
* Descripción: Clase DAO para establecer comunicación con la BD y recuperar, dar de alta, modificar 
* y eliminar estudiantes del sistema
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.utils.Constantes;

public class EstudianteDAO {
    public EstudianteRespuesta obtenerInformacionEstudiantes(){
        EstudianteRespuesta respuesta = new EstudianteRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "select idEstudiante, email, password, createTime, nombre, "
                        + "apellidoPaterno, apellidoMaterno, foto, matricula, idAnteproyecto from"
                        + " estudiante";
            }
        }
    }
}
