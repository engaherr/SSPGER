/*
* Título del programa: ActividadDAO
* Autor: Omar Dylan Segura Platas
* Fecha: 09/06/2023
* Descripción: Se encarga de la correcta conexión y obtención de datos de la base de datos.
*/


package javafxsspger.modelo.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.ActividadRespuesta;
import javafxsspger.utils.Constantes;


public class ActividadDAO {
    
        public static ActividadRespuesta obtenerActividadesdelEstudiante(int idEstudiante){
        ActividadRespuesta respuesta = new ActividadRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Actividad> actividades = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                 String consulta = "SELECT idActividad, nombre, fechaInicio, fechaFin, "
                        + " idEstudiante, descripcion "
                        + "FROM actividad "
                        + "WHERE idEstudiante = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Actividad actividad = new Actividad();                
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setNombre(resultado.getString("nombre"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFin(resultado.getString("fechaFin"));
                    actividad.setIdEstudiante(resultado.getInt("idEstudiante"));
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividades.add(actividad);
            }
            respuesta.setActividades(actividades);
            conexionBD.close();
        } catch (SQLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
    } else {
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
    }
    return respuesta;
}
    
        public static ActividadRespuesta obtenerDetallesActividad(int idActividad){
        ActividadRespuesta respuesta = new ActividadRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Actividad> actividades = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                 String consulta = "SELECT idActividad, nombre, fechaInicio, fechaFin, fechaCreacion, idAnteproyecto, "
                        + "descripcion, archivo, idEstudiante, idAvance "
                        + "FROM actividad "
                        + "WHERE idActividad = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idActividad);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Actividad actividad = new Actividad();                
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setNombre(resultado.getString("nombre"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFin(resultado.getString("fechaFin"));
                    actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                    actividad.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividad.setIdEstudiante(resultado.getInt("idEstudiante"));
                    actividad.setIdAvance(resultado.getInt("idAvance"));
                    actividades.add(actividad);
                  
            }
            respuesta.setActividades(actividades);
            conexionBD.close();
         
        } catch (SQLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
    } else {
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
    }
    return respuesta;
}
           
        public static ActividadRespuesta obtenerDetallesEntrega(int idActividad){
        ActividadRespuesta respuesta = new ActividadRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Actividad> actividades = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                 String consulta = "SELECT idActividad, fechaCreacion, comentarios\n" +
                    "FROM entrega\n" +
                    "WHERE idActividad = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idActividad);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Actividad actividad = new Actividad();                
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setComentarios(resultado.getString("comentarios"));
                    actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                   
                    actividades.add(actividad);
                  
            }
            respuesta.setActividades(actividades);
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
