/*
* Título del programa: CursoDAO
* Autor: Omar Dylan Segura Platas
* Fecha: 09/06/2023
* Descripción: Se encarga de la correcta conexión y obtención de datos de la base de datos.
*/

package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Curso;
import javafxsspger.modelo.pojo.CursoRespuesta;
import javafxsspger.utils.Constantes;


public class CursoDAO {
    
    
       public static CursoRespuesta obtenerCursos() {
    CursoRespuesta respuesta = new CursoRespuesta();
    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
    ArrayList<Curso> cursos = new ArrayList<>();
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if (conexionBD != null) {
        try {
            String consulta = "SELECT cursoee.idCursoEE, cursoee.NRC, cursoee.idMateria, cursoee.idPeriodoEscolar, cursoee.idProfesor, "
                    + "materia.nombre AS nombreMateria\n" +
                    "FROM cursoee\n" +
                    "JOIN materia ON cursoee.idMateria = materia.idMateria;";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = prepararSentencia.executeQuery();
            while (resultado.next()) {
                Curso curso = new Curso();
                curso.setIdCursoEE(resultado.getInt("idCursoEE"));
                curso.setNRC(resultado.getInt("NRC"));
                curso.setIdMateria(resultado.getInt("idMateria"));
                curso.setIdPeridoEscolar(resultado.getInt("idPeriodoEscolar"));
                curso.setIdProfesor(resultado.getInt("idProfesor"));
                curso.setNombreMateria(resultado.getString("nombreMateria"));
                cursos.add(curso);
            }
            respuesta.setCursos(cursos);
            conexionBD.close();
        } catch (SQLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
    } else {
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
    }
    return respuesta;
}

       
       public static void AgregarEstudianteCurso(int idCursoEE, int idEstudiante) {
    CursoRespuesta respuesta = new CursoRespuesta();
    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if (conexionBD != null) {
        try {
            String consulta = "INSERT INTO estudiantecursoee (estado, idCursoEE, idEstudiante) "
                    + "VALUES ('Activo', ?, ?)";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            prepararSentencia.setInt(1, idCursoEE);
            prepararSentencia.setInt(2, idEstudiante);
            prepararSentencia.executeUpdate();
            conexionBD.close();
        } catch (SQLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
    } else {
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
    }
}

    public static boolean VerificarRegistro(int idCursoEE, int idEstudiante) {
    boolean registroExiste = false;
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if (conexionBD != null) {
        try {
            String consulta = "SELECT EXISTS (SELECT 1 FROM estudiantecursoee WHERE estado = 'Activo' AND idCursoEE = ? AND idEstudiante = ?) AS registroExiste";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            prepararSentencia.setInt(1, idCursoEE);
            prepararSentencia.setInt(2, idEstudiante);
            ResultSet resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                registroExiste = resultado.getBoolean("registroExiste");
            }
            conexionBD.close();
        } catch (SQLException ex) {
   
        }
    } else {
        
    }
    return registroExiste;
}

       
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
                prepararSentencia.setInt(3, cursoNuevo.getIdPeridoEscolar());
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
