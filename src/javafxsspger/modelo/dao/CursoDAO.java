
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Curso;
import javafxsspger.utils.Constantes;

public class CursoDAO {
    public static int guardarCursoEE(Curso cursoNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "insert into cursoee (NRC,idMateria,idPeriodoEscolar,idProfesor)"
                        + "values (?,?,?,?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, cursoNuevo.getNRC());
                prepararSentencia.setInt(2, cursoNuevo.getIdMateria());
                prepararSentencia.setInt(3, cursoNuevo.getIdPeriodoEscolar());
                prepararSentencia.setInt(4, cursoNuevo.getIdProfesor());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA :
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch(SQLException e){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
