/*
* Título del programa: DAO para Actividades
* Autor: Jasiel Emir Zavaleta García, Dylan Omar Segura Platas
* Fecha Creación: 10/06/2023
* Descripción: Clase de Acceso a la información de la base de datos correspondiente a las 
* actividades los cuales tienen una tabla en la persistencia del sistema
*/
package javafxsspger.modelo.dao;

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
    public static ActividadRespuesta obtenerActividadesAnteproyecto(int idAnteproyecto){
    ActividadRespuesta respuesta = new ActividadRespuesta();
    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
    ArrayList<Actividad> actividades = new ArrayList<>();
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if(conexionBD != null){
        try {
            String consulta = "select idActividad, nombre, fechaInicio, fechaFin, fechaCreacion, "
                    + "descripcion, archivo, idEstudiante, idAvance, extensionArchivo, "
                    + "nombreArchivo "
                    + "from "
                    + "lgac where idAnteproyecto = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            prepararSentencia.setInt(1, idAnteproyecto);
            ResultSet resultado = prepararSentencia.executeQuery();
            while(resultado.next()){
                Actividad actividad = new Actividad();
                actividad.setIdAnteproyecto(idAnteproyecto);
                actividad.setIdActividad(resultado.getInt("idActividad"));
                actividad.setNombre(resultado.getString("nombre"));
                actividad.setFechaInicio(resultado.getString("fechaInicio"));
                actividad.setFechaFin(resultado.getString("fechaFin"));
                actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                actividad.setDescripcion(resultado.getString("descripcion"));
                actividad.setArchivo(resultado.getBytes("archivo"));
                actividad.setIdEstudiante(resultado.getInt("idEstudiante"));
                actividad.setIdAvance(resultado.getInt("idAvance"));
                actividad.setExtensionArchivo(resultado.getString("extensionArchivo"));
                actividad.setNombreArchivo(resultado.getString("nombreArchivo"));
                actividades.add(actividad);
            }
            conexionBD.close();           
            respuesta.setActividades(actividades);
        } catch (SQLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
    }else{
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
    }
    return respuesta;               
    }

    public static int registrarActividad(Actividad actividadNueva){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "insert into actividad (nombre,fechaInicio,fechaFin,"
                        + "fechaCreacion,idAnteproyecto,descripcion,archivo,idEstudiante,idAvance,"
                        + "extensionArchivo,nombreArchivo) values (?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1,actividadNueva.getNombre());
                prepararSentencia.setString(2,actividadNueva.getFechaInicio());
                prepararSentencia.setString(3,actividadNueva.getFechaFin());
                prepararSentencia.setString(4,actividadNueva.getFechaCreacion());
                prepararSentencia.setInt(5, actividadNueva.getIdAnteproyecto());
                prepararSentencia.setString(6,actividadNueva.getDescripcion());
                prepararSentencia.setBytes(7,actividadNueva.getArchivo());
                prepararSentencia.setInt(8,actividadNueva.getIdEstudiante());
                prepararSentencia.setInt(9,actividadNueva.getIdAvance());
                prepararSentencia.setString(10,actividadNueva.getExtensionArchivo());
                prepararSentencia.setString(11,actividadNueva.getNombreArchivo());
                
                int filasAfecadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfecadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch(SQLException ex){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int modificarActividad(Actividad edicionActividad){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "update actividad set nombre = ?, fechaInicio = ?, fechaFin = ?,"
                        + " idAnteproyecto = ?, descripcion = ?, archivo = ?, "
                        + "idEstudiante = ?, idAvance = ?, extensionArchivo = ?,nombreArchivo = ? "
                        + "where idActividad = ?";
                PreparedStatement prepararSentencia  = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1,edicionActividad.getNombre());
                prepararSentencia.setString(2,edicionActividad.getFechaInicio());
                prepararSentencia.setString(3,edicionActividad.getFechaFin());
                prepararSentencia.setInt(4,edicionActividad.getIdAnteproyecto());
                prepararSentencia.setString(5,edicionActividad.getDescripcion());
                prepararSentencia.setBytes(6,edicionActividad.getArchivo());
                prepararSentencia.setInt(7,edicionActividad.getIdEstudiante());
                prepararSentencia.setInt(8,edicionActividad.getIdAvance());
                prepararSentencia.setString(9,edicionActividad.getExtensionArchivo());
                prepararSentencia.setString(10,edicionActividad.getNombreArchivo());                
                prepararSentencia.setInt(11,edicionActividad.getIdActividad());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA :
                        Constantes.ERROR_CONSULTA;
            }catch(SQLException ex){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }

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
                 String consulta = "SELECT idActividad, nombre, fechaInicio, fechaFin, "
                         + "fechaCreacion, idAnteproyecto, "
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
                 String consulta = "SELECT idActividad, fechaCreacion, comentarios,archivo,"
                         + "nombreArchivo\n" +
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
                String sentencia = "INSERT INTO entrega (idActividad, fechaCreacion, archivo, "
                        + "comentarios, nombreArchivo)\n" +
                        "VALUES (?, ?, ?, ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, actividadEntrega.getIdActividad());
                prepararSentencia.setString(2, actividadEntrega.getFechaCreacion());
                prepararSentencia.setBytes(3, actividadEntrega.getArchivo());
                prepararSentencia.setString(4, actividadEntrega.getComentarios());
                prepararSentencia.setString(5, actividadEntrega.getNombreArchivo());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
            } catch (SQLException ex) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }

     public static int eliminarActividad(int idActividad){
         int respuesta;
         Connection conexionBD = ConexionBD.abrirConexionBD();
         if(conexionBD != null){
             try{
                 String sentencia = "delete from actividad where idActividad = ?";
                 PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                 int filasAfectadas = prepararSentencia.executeUpdate();
                 respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                         Constantes.ERROR_CONSULTA;
                 conexionBD.close();
             }catch(SQLException ex){
                 respuesta = Constantes.ERROR_CONSULTA;
             }
         }else{
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
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
            } catch (SQLException ex) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
        
        
    public static boolean verificarEsModificar(int idActividad) {
    boolean existeRegistro = false;
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if (conexionBD != null) {
        try {
            String consulta = "SELECT EXISTS(SELECT 1 FROM entrega WHERE idActividad = ?) AS existeRegistro;";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            prepararSentencia.setInt(1, idActividad);
            ResultSet resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                existeRegistro = resultado.getBoolean("existeRegistro");
            }
            conexionBD.close();
        } catch (SQLException ex) {
     
        }
    }
    return existeRegistro;
}

       
}
