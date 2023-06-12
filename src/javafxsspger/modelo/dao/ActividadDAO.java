/*
* Título del programa: DAO para Actividades
* Autor: Jasiel Emir Zavaleta García
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

/**
 *
 * @author jasie
 */
public class ActividadDAO {
    public static ActividadRespuesta obtenerActividadesAnteproyecto(int idAnteproyecto){
    ActividadRespuesta respuesta = new ActividadRespuesta();
    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
    ArrayList<Actividad> actividades = new ArrayList<>();
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if(conexionBD != null){
        try {
            String consulta = "select idActividad, nombre, fechaInicio, fechaFin, fechaCreacion, "
                    + "descripcion, archivo, idEstudiante, idAvance, extensionArchivo, nombreArchivo "
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
                respuesta = (filasAfecadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
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
                String sentencia = "update actividad set nombre = ?, fechaInicio = ?, fechaFin = ?, "
                        + "idAnteproyecto = ?, descripcion = ?, archivo = ?, "
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
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            }catch(SQLException ex){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
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
                 respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                 conexionBD.close();
             }catch(SQLException ex){
                 respuesta = Constantes.ERROR_CONSULTA;
             }
         }else{
             respuesta = Constantes.ERROR_CONEXION;
         }
         return respuesta;
     }
    
}
