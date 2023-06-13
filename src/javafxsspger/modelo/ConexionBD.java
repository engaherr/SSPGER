/*
* Título del programa: Clase Conexion con Base de Datos
* Autor: Ramón Gómez Romero
* Fecha Creación: 09/05/2023
* Descripción: Clase para abrir la conexión con la base de datos que contiene los datos 
* variables (dependiendo el que corra el sistema) nombreBase, hostname, puerto, usuario y 
* password. Hecho en clase de Principios de Construcción de Software
*/
package javafxsspger.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kikga
 */
public class ConexionBD {
    
    private static String driver = "com.mysql.jdbc.Driver";
    private static String nombreBase = "sspger";
    private static String hostname = "localhost";
    private static String puerto = "3306";
    
    private static String usuario = "root";
    private static String password = "karma";

    private static String urlConexion = "jdbc:mysql://" + hostname + ":" + puerto 
            + "/" + nombreBase +"?allowPublicKeyRetrieval=true&useSSL=false";
    
    public static Connection abrirConexionBD(){
        Connection conexion = null;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(urlConexion,usuario,password);
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Error de conexion con BD: " + ex.getMessage());
            ex.printStackTrace();
        }
        return conexion;
    }
}
