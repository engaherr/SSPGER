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
                 String consulta = "SELECT idActividad, fechaCreacion, comentarios,archivo,nombreArchivo\n" +
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
                    actividad.setArchivo(resultado.getBytes("archivo"));
                    actividad.setNombreArchivo(resultado.getString("nombreArchivo"));
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
        
        
      public static void EvaluarEntrega(int evaluacion, int idActividad) {
        ActividadRespuesta respuesta = new ActividadRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);   
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
        try {
            String consulta = "UPDATE entrega SET evaluacion = ? WHERE idActividad = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            prepararSentencia.setInt(1, evaluacion);
            prepararSentencia.setInt(2, idActividad);
            prepararSentencia.executeUpdate();
            conexionBD.close();
        } catch (SQLException ex) {
         respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
    } else {
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
    }
}

      
       public static int enviarEntrega(Actividad actividadEntrega) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO entrega (idActividad, fechaCreacion, archivo, comentarios, nombreArchivo)\n" +
                        "VALUES (?, ?, ?, ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, actividadEntrega.getIdActividad());
                prepararSentencia.setString(2, actividadEntrega.getFechaCreacion());
                prepararSentencia.setBytes(3, actividadEntrega.getArchivo());
                prepararSentencia.setString(4, actividadEntrega.getComentarios());
                prepararSentencia.setString(5, actividadEntrega.getNombreArchivo());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            } catch (SQLException ex) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
        public static int modificarEntrega(Actividad actividadEntrega) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE entrega\n" +
                "SET fechaCreacion = ?, archivo = ?, comentarios = ?, nombreArchivo = ?\n" +
                "WHERE idActividad = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, actividadEntrega.getFechaCreacion());
                prepararSentencia.setBytes(2, actividadEntrega.getArchivo());
                prepararSentencia.setString(3, actividadEntrega.getComentarios());
                prepararSentencia.setString(4, actividadEntrega.getNombreArchivo());
                prepararSentencia.setInt(5, actividadEntrega.getIdActividad());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            } catch (SQLException ex) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
       
       
}
