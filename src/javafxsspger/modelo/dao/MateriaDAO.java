/*
* Título del programa: DAO para Materias
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 10/06/2023
* Descripción: Clase DAO para establecer comunicación con la persistencia del sistema y recuperar 
* las materias registradas en la base de datos
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Materia;
import javafxsspger.modelo.pojo.MateriaRespuesta;
import javafxsspger.utils.Constantes;

public class MateriaDAO {
    public static MateriaRespuesta obtenerMaterias(){
        MateriaRespuesta respuesta = new MateriaRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Materia> materias = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "select * from materia";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Materia materia = new Materia();
                    materia.setIdMateria(resultado.getInt("idMateria"));
                    materia.setNombre(resultado.getString("nombre"));
                    materias.add(materia);
                }
                conexionBD.close();
                respuesta.setMaterias(materias);
            }catch(SQLException e){
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
