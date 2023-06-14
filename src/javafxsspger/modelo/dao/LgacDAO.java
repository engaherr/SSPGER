/*
* Título del programa: DAO para las Líneas de Generación y Aplicación del Conocimiento (LGAC)
* Autor: Enrique Gamboa Hernández, Jasiel Emir Zavaleta García
* Fecha Creación: 07/06/2023
* Descripción: Clase de acceso a la información para las Lgacs lass cuales tienen
* una tabla en la persistencia del sistema
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Lgac;
import javafxsspger.modelo.pojo.LgacRespuesta;
import javafxsspger.utils.Constantes;

public class LgacDAO {
    public static LgacRespuesta obtenerLgacsPorCA(int idCuerpoAcademico){
        LgacRespuesta respuesta = new LgacRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Lgac> lgacs = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "select idLgac,nombre from lgac where idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Lgac lgac = new Lgac();
                    lgac.setIdCuerpoAcademico(idCuerpoAcademico);
                    lgac.setIdLgac(resultado.getInt("idLgac"));
                    lgac.setNombre(resultado.getString("nombre"));
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

    public static LgacRespuesta obtenerLgacsSinCA(){
        LgacRespuesta respuesta = new LgacRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Lgac> lgacs = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "select idLgac,nombre from lgac where idCuerpoAcademico is null";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Lgac lgac = new Lgac();
                    lgac.setIdLgac(resultado.getInt("idLgac"));
                    lgac.setNombre(resultado.getString("nombre"));
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
       
    public static LgacRespuesta obtenerTodosLgacs(){
        LgacRespuesta respuesta = new LgacRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Lgac> lgacs = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT lgac.*, IFNULL(cuerpoAcademico.nombre, 'No asignado') AS "
                        + "nombreCuerpoAcademico\n" +
                        "FROM lgac\n" +
                        "left JOIN cuerpoAcademico ON lgac.idCuerpoAcademico = "
                        + "cuerpoacademico.idCuerpoAcademico;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Lgac lgac = new Lgac();
                    
                    lgac.setIdLgac(resultado.getInt("idLgac"));
                    lgac.setNombre(resultado.getString("nombre"));
                    lgac.setDescripcion(resultado.getString("descripcion"));
                    lgac.setIdCuerpoAcademico(resultado.
                            getInt("idCuerpoAcademico"));
                    lgac.setNombreCuerpoAcademico(resultado.
                            getString("nombreCuerpoAcademico"));
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
    
    public static int RegistarLgac(Lgac nuevoLgac){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "insert into lgac (nombre,descripcion) values (?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, nuevoLgac.getNombre());
                prepararSentencia.setString(2,nuevoLgac.getDescripcion());
                
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
    
    public static int modificarLgac(Lgac edicionLgac, boolean tieneCuerpoAcademico){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            if(tieneCuerpoAcademico){
                try{
                    String sentencia = "update lgac set nombre = ? descripcion = ?, "
                            + "idCuerpoAcademico = ? where idLgac = ?";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                    prepararSentencia.setString(1, edicionLgac.getNombre());
                    prepararSentencia.setString(2, edicionLgac.getDescripcion());
                    prepararSentencia.setInt(3, edicionLgac.getIdCuerpoAcademico());    
                    prepararSentencia.setInt(4, edicionLgac.getIdLgac());

                    int filasAfectadas = prepararSentencia.executeUpdate();
                    respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                            Constantes.ERROR_CONSULTA;
                    conexionBD.close();
                }catch(SQLException ex){
                    respuesta = Constantes.ERROR_CONSULTA;
                }
                
            }else{
                try{
                    String sentencia = "update lgac set nombre = ?, "
                            + "descripcion = ?  where idLgac = ?";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                    prepararSentencia.setString(1, edicionLgac.getNombre());
                    prepararSentencia.setString(2, edicionLgac.getDescripcion());
                    prepararSentencia.setInt(3, edicionLgac.getIdLgac());

                    int filasAfectadas = prepararSentencia.executeUpdate();
                    respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                            Constantes.ERROR_CONSULTA;
                    conexionBD.close();
                }catch(SQLException ex){
                    respuesta = Constantes.ERROR_CONSULTA;
                }                 
            }
       
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }

    public static int agregarLgacCA(int idLgac,int idCuerpoAcademico){
       int respuesta;
       Connection conexionBD = ConexionBD.abrirConexionBD();
       if(conexionBD != null){
           try{
               String sentencia = "update lgac set idCuerpoAcademico = ? where idLgac = ?";
               PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
               prepararSentencia.setInt(1, idCuerpoAcademico);
               prepararSentencia.setInt(2, idLgac);
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
    
    public static int eliminarLgacCA(int idCuerpoAcademico){
        int respuesta;
       Connection conexionBD = ConexionBD.abrirConexionBD();
       if(conexionBD != null){
           try{
               String sentencia = "update lgac set idCuerpoAcademico = null "
                       + "where idCuerpoAcademico = ?";
               PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
               prepararSentencia.setInt(1, idCuerpoAcademico);
               int filasAfectadas = prepararSentencia.executeUpdate(); 
               respuesta = (filasAfectadas >= 1) ? Constantes.OPERACION_EXITOSA : 
                       Constantes.ERROR_CONSULTA;
           }catch(SQLException ex){
               respuesta = Constantes.ERROR_CONSULTA;
           }
       }else{
            respuesta = Constantes.ERROR_CONEXION;

       }
       return respuesta;       
    }
}
