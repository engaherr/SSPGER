/*
* Título del programa: DAO para académicos
* Autor: Jasiel Emir Zavaleta García
* Fecha Creación: 10/06/2023
* Descripción: Clase de Acceso a la información de la base de datos correspondiente a los académicos
* los cuales tienen una tabla en la persistencia del sistema
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.AcademicoRespuesta;
import javafxsspger.utils.Constantes;

/**
 *
 * @author jasie
 */
public class AcademicoDAO {
    public static AcademicoRespuesta recuperarAcademicosSinCA(){
       AcademicoRespuesta respuesta = new AcademicoRespuesta();
       respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
       ArrayList<Academico> academicos = new ArrayList<>();       
       Connection conexionBD = ConexionBD.abrirConexionBD();
       if(conexionBD != null){
           try{
                String sentencia = "select idAcademico,nombre,apellidoPaterno,apellidoMaterno,"
                        + "noPersonal\n" +
                        "from academico\n" +
                        "where idCuerpoAcademico is null";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Academico academico = new Academico();
                    academico.setIdAcademico(resultado.getInt("idAcademico"));
                    academico.setNombre(resultado.getString("nombre"));
                    academico.setApellidoPaterno(resultado.getString
                        ("apellidoPaterno"));
                    academico.setApellidoMaterno(resultado.
                            getString("apellidoMaterno"));
                    academico.setNoPersonal(resultado.getInt("noPersonal"));
                    academicos.add(academico);
                   //System.out.println("Academico"+academico.getNombre()+" añadido a la tabla");
                }
                respuesta.setAcademicos(academicos);
                conexionBD.close();
           }catch(SQLException ex){
               respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
           }
       }else{
           respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
       }
       return respuesta;
    }
    
    public static int agregarAcademicoCA(int idAcademico, int idCuerpoAcademico){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "update academico set idCuerpoAcademico = ? where idAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1,idCuerpoAcademico);
                prepararSentencia.setInt(2,idAcademico);
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
    public static AcademicoRespuesta recuperarAcademicosEnCA(int idCuerpoAcademico){
       AcademicoRespuesta respuesta = new AcademicoRespuesta();
       respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
       ArrayList<Academico> academicos = new ArrayList<>();       
       Connection conexionBD = ConexionBD.abrirConexionBD();
       if(conexionBD != null){
           try{
                String sentencia = "select idAcademico,nombre,apellidoPaterno,apellidoMaterno,"
                        + "noPersonal\n" +
                        "from academico\n" +
                        "where idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Academico academico = new Academico();
                    academico.setIdAcademico(resultado.getInt("idAcademico"));
                    academico.setNombre(resultado.getString("nombre"));
                    academico.setApellidoPaterno(resultado.getString
                        ("apellidoPaterno"));
                    academico.setApellidoMaterno(resultado.
                            getString("apellidoMaterno"));
                    academico.setNoPersonal(resultado.getInt("noPersonal"));
                    academicos.add(academico);
                   //System.out.println("Academico"+academico.getNombre()+" añadido a la tabla");
                }
                respuesta.setAcademicos(academicos);
                conexionBD.close();
           }catch(SQLException ex){
               respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
           }
       }else{
           respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
       }
       return respuesta;
    }    
   
    public static int removerAcademicoCA(int idAcademico){
        int repuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "update academico set idCuerpoAcademico = null where "
                        + "idAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idAcademico);  
                int filasAfectadas = prepararSentencia.executeUpdate();
                repuesta  = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                         Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch(SQLException ex){
                repuesta = Constantes.ERROR_CONSULTA;
            }        
        }else{
            repuesta = Constantes.ERROR_CONEXION;
        }
        return repuesta;
  
    }  
    
    public static int removerMuchosAcademicosDeCA(int idCuerpoAcademico,int idAcademico){
        int repuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "update academico set idCuerpoAcademico = null where "
                        + "idCuerpoAcademico = ? and idAcademico <> ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                prepararSentencia.setInt(2, idAcademico);  
                int filasAfectadas = prepararSentencia.executeUpdate();
                repuesta  = (filasAfectadas >= 1) ? Constantes.OPERACION_EXITOSA : 
                         Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch(SQLException ex){
                repuesta = Constantes.ERROR_CONSULTA;
            }        
        }else{
            repuesta = Constantes.ERROR_CONEXION;
        }
        return repuesta; 
    }
    
    public static int removerTodosAcademicosDeCA(int idCuerpoAcademico){
        int repuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "update academico set idCuerpoAcademico = null where "
                        + "idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                int filasAfectadas = prepararSentencia.executeUpdate();
                repuesta  = (filasAfectadas >= 1) ? Constantes.OPERACION_EXITOSA : 
                         Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch(SQLException ex){
                repuesta = Constantes.ERROR_CONSULTA;
            }        
        }else{
            repuesta = Constantes.ERROR_CONEXION;
        }
        return repuesta; 
    }    
    
}
