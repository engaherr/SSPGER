/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.modelo.pojo.AvanceRespuesta;
import javafxsspger.utils.Constantes;

/**
 *
 * @author jasie
 */
public class AvanceDAO {
    public static AvanceRespuesta obtenerAvancesAnteproyecto(int idAnteproyecto){
        AvanceRespuesta respuesta = new AvanceRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        ArrayList<Avance> avances = new ArrayList<>();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if(conexionBD != null){
            try{
                String sentencia = "select idAvance,nombre,porcentaje from avance "
                        + "where idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1,idAnteproyecto);  
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Avance avance = new Avance();
                    avance.setIdAvance(resultado.getInt("idAvance"));
                    avance.setNombre(resultado.getString("nombre"));
                    avance.setPorcentaje(resultado.getInt("porcentaje"));
                    avance.setIdAnteproyecto(idAnteproyecto);
                    avances.add(avance);
                }
                conexionBD.close();
                respuesta.setAvances(avances);
            }catch(SQLException ex){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
}
