/*
* Título del programa: DAO para las LGACs
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 07/06/2023
* Descripción: Clase de acceso a la información para las LGACs lass cuales tienen
* una tabla en la persistencia del sistema
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.LGACRespuesta;
import javafxsspger.utils.Constantes;

public class LGACDAO {
    public static LGACRespuesta obtenerLGACsPorCA(int idCuerpoAcademico){
        LGACRespuesta respuesta = new LGACRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<LGAC> lgacs = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "select idLgac,nombre,numero "
                        + "from lgac where idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    LGAC lgac = new LGAC();
                    lgac.setIdCuerpoAcademico(idCuerpoAcademico);
                    lgac.setIdLgac(resultado.getInt("idLgac"));
                    lgac.setNombre(resultado.getString("nombre"));
                    lgac.setNumero(resultado.getInt("numero"));
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
       
    public static LGACRespuesta obtenerTodosLGACs(){
        LGACRespuesta respuesta = new LGACRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<LGAC> lgacs = new ArrayList<>();
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
                    LGAC lgac = new LGAC();
                    
                    lgac.setIdLgac(resultado.getInt("idLgac"));
                    lgac.setNombre(resultado.getString("nombre"));
                    lgac.setDescripcion(resultado.getString("descripcion"));
                    lgac.setNumero(resultado.getInt("numero"));
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
    
    public static int RegistarLGAC(LGAC nuevoLGAC){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "insert into lgac (nombre,descripcion) values (?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, nuevoLGAC.getNombre());
                prepararSentencia.setString(2,nuevoLGAC.getDescripcion());
                
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
    
    public static int modificarLGAC(LGAC edicionLGAC, boolean tieneCuerpoAcademico){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            if(tieneCuerpoAcademico){
                try{
                    String sentencia = "update lgac set nombre = ? descripcion = ?, "
                            + "idCuerpoAcademico = ?, numero = ? where idLgac = ?";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                    prepararSentencia.setString(1, edicionLGAC.getNombre());
                    prepararSentencia.setString(2, edicionLGAC.getDescripcion());
                    prepararSentencia.setInt(3, edicionLGAC.getIdCuerpoAcademico());    
                    prepararSentencia.setInt(4, edicionLGAC.getNumero());
                    prepararSentencia.setInt(3, edicionLGAC.getIdLgac());

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
                    prepararSentencia.setString(1, edicionLGAC.getNombre());
                    prepararSentencia.setString(2, edicionLGAC.getDescripcion());
                    //prepararSentencia.setInt(3, edicionLGAC.getIdCuerpoAcademico());    
                    //prepararSentencia.setInt(4, edicionLGAC.getNumero());
                    prepararSentencia.setInt(3, edicionLGAC.getIdLgac());

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

}
