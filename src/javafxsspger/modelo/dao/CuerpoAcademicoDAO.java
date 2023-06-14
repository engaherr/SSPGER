/*
* Título del programa: DAO para cuerpos académicos
* Autor: Enrique Gamboa Hernández, Jasiel Emir Zavaleta García
* Fecha Creación: 07/06/2023
* Descripción: Clase de acceso a la información para los Cuerpos Académicos los cuales tienen
* una tabla en la persistencia del sistema
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsspger.utils.Constantes;

/**
 *
 * @author kikga
 */
public class CuerpoAcademicoDAO {
    public static CuerpoAcademicoRespuesta obtenerCuerposAcademicos(){
        CuerpoAcademicoRespuesta respuesta = new CuerpoAcademicoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<CuerpoAcademico> cuerposAcademicos = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "select ca.idCuerpoAcademico,ca.nombre,ca.clave,d.nombre as "
                        + "'dependencia',ca.idGradoConsolidacion,ca.idDependencia,ca.idResponsable,"
                        + "gc.nombre as 'nombreGrado', concat(a.nombre,' ',a.apellidoPaterno,' ',a.apellidoMaterno) "
                        + "as 'responsable' "
                        + "from cuerpoacademico ca "
                        + "inner join academico a on a.idAcademico = ca.idResponsable "
                        + "inner join gradoconsolidacion gc on gc.idGradoConsolidacion = "
                        + "ca.idGradoConsolidacion "
                        + "inner join dependencia d on d.idDependencia = ca.idDependencia;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    CuerpoAcademico cuerpoAcademico = new CuerpoAcademico();
                    cuerpoAcademico.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    cuerpoAcademico.setClave(resultado.getString("clave"));
                    cuerpoAcademico.setDependencia(resultado.getString("dependencia"));
                    cuerpoAcademico.setGradoConsolidacion("nombreGrado");
                    cuerpoAcademico.setIdDependencia(resultado.getInt("idDependencia"));
                    cuerpoAcademico.setIdGradoConsolidacion(
                            resultado.getInt("idGradoConsolidacion"));
                    cuerpoAcademico.setIdResponsable(resultado.getInt("idResponsable"));
                    cuerpoAcademico.setNombre(resultado.getString("nombre"));
                    cuerpoAcademico.setResponsable(resultado.getString("responsable"));
                    cuerposAcademicos.add(cuerpoAcademico);
                }
                respuesta.setCuerposAcademicos(cuerposAcademicos);
                conexionBD.close();
            } catch (SQLException ex) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int registrarCuerpoAcademico(CuerpoAcademico caRegistrar){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
              String sentencia = "insert into cuerpoacademico (nombre,clave,idGradoConsolidacion,"
                      + "idDependencia,idResponsable) values (?,?,?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1,caRegistrar.getNombre());
                prepararSentencia.setString(2,caRegistrar.getClave());
                prepararSentencia.setInt(3,caRegistrar.getIdGradoConsolidacion());
                prepararSentencia.setInt(4,caRegistrar.getIdDependencia());
                prepararSentencia.setInt(5,caRegistrar.getIdResponsable());
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
    public static int modificarCuerpoAcademico(CuerpoAcademico caModificar){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
              String sentencia = "update into cuerpoacademico nombre = ?,clave = ?,idGradoConsolidacion = ?,"
                      + "idDependencia = ? ,idResponsable = ? where idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1,caModificar.getNombre());
                prepararSentencia.setString(2,caModificar.getClave());
                prepararSentencia.setInt(3,caModificar.getIdGradoConsolidacion());
                prepararSentencia.setInt(4,caModificar.getIdDependencia());
                prepararSentencia.setInt(5,caModificar.getIdResponsable());
                prepararSentencia.setInt(6,caModificar.getIdCuerpoAcademico());
                
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

    public static int eliminarCuerpoAcademico(int idCuerpoAcademico){
        int respuesta;
        Connection conecionBD = ConexionBD.abrirConexionBD();
        if(conecionBD != null){  
            try{
                String sentencia = "delete from cuerpoacademico where idCuerpoAcademico = ?";
                PreparedStatement prepararSentecia = conecionBD.prepareStatement(sentencia);
                prepararSentecia.setInt(1,idCuerpoAcademico);
                int filasAfectadas = prepararSentecia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            }catch(SQLException ex){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }

    public static int obtenerUltimoCARegistrado() {
    int idCuerpoAcademico = -1;
    Connection conexionBD = ConexionBD.abrirConexionBD(); 
    if (conexionBD != null) {
        try {
            String sentencia = "select idCuerpoAcademico from cuerpoacademico "
                    + "order by idCuerpoAcademico "
                    + "DESC LIMIT 1";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            ResultSet resultado = prepararSentencia.executeQuery(sentencia);
            
            if (resultado.next()) {
                idCuerpoAcademico = resultado.getInt("idCuerpoAcademico");   
            }           
            conexionBD.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
        return idCuerpoAcademico;
    }    
}
