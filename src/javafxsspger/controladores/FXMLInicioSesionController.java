/*
* Título: Clase controladora para el Inicio de sesión al sistema
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 11/05/2023
* Descripción: Clase controladora de la vista FXMLInicioSesion que implementa Singleton para 
* distinguir el tipo de usuario y al ser académico le asigna valores booleanos para el rol
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsspger.modelo.dao.SesionDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

public class FXMLInicioSesionController implements Initializable {
    
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfContraseña;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorContraseña;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicIniciarSesion(ActionEvent event) {
        lbErrorContraseña.setText("");
        lbErrorUsuario.setText("");
        validarCampos();
    }

    private void validarCampos() {
        String usuario = tfUsuario.getText();
        String password = tfContraseña.getText();
        boolean sonValidos = true;
        if(usuario.trim().isEmpty()){
            sonValidos = false;
            lbErrorUsuario.setText("El usuario es requerido");
        }
        if(password.trim().isEmpty()){
            sonValidos = false;
            lbErrorContraseña.setText("La contraseña es requerida");
        }
        if(sonValidos){
            validarCredencialesUsuario(usuario,password);
        }
    }

    private void validarCredencialesUsuario(String usuario, String password) {
        Estudiante estudianteRespuesta = null;
        Academico academicoRespuesta = null;
        boolean sonValidos = true;
        int respuesta = Constantes.ERROR_CONSULTA;
        if(usuario.startsWith("s")){
            estudianteRespuesta = SesionDAO.verificarEstudianteSesion(usuario, password);
            respuesta = estudianteRespuesta.getCodigoRespuesta();
        }else if(Character.isDigit(usuario.charAt(0))){
            academicoRespuesta = SesionDAO.verificarAcademicoSesion(usuario, password);
            respuesta = academicoRespuesta.getCodigoRespuesta();
        }else{
            sonValidos = false;
            lbErrorUsuario.setText("El usuario debe comenzar con 's' o un número");
        }
        if(sonValidos){
            switch(respuesta){
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de Conexion", 
                            "Por el momento no hay conexión, por favor intentélo más tarde",
                            Alert.AlertType.ERROR);
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                            "Por el momento no se puede procesar la solicitud de verificación", 
                            Alert.AlertType.ERROR);
                    break;
                case Constantes.OPERACION_EXITOSA:
                    if(estudianteRespuesta != null && estudianteRespuesta.getIdEstudiante() > 0){
                        Utilidades.mostrarDialogoSimple("Estudiante verificado",
                                "Bienvenid@ " + estudianteRespuesta.toString() + " al sistema...", 
                                Alert.AlertType.INFORMATION);
                        Estudiante.setInstanciaSingleton(estudianteRespuesta);
                        irPantallaPrincipal();
                    }else if(academicoRespuesta != null && academicoRespuesta.getIdAcademico() > 0){
                        Utilidades.mostrarDialogoSimple("Academico verificado",
                                "Bienvenid@ " + academicoRespuesta.toString() + " al sistema...", 
                                Alert.AlertType.INFORMATION);
                        Academico.setInstanciaSingleton(academicoRespuesta);
                        irPantallaPrincipal();
                    }else{
                        Utilidades.mostrarDialogoSimple("Credenciales incorrectas", 
                                "El usuario y/o contraseñas son incorrectas, por favor verifique "
                                        + "la información", Alert.AlertType.WARNING);
                    }
                    break;
                default:
                    Utilidades.mostrarDialogoSimple("Credenciales incorrectas", 
                                "El usuario y/o contraseñas son incorrectas, por favor verifique "
                                        + "la información", Alert.AlertType.WARNING);
            }
        }
    }

    private void irPantallaPrincipal() {
        Stage escenarioBase = (Stage) tfContraseña.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLPrincipal.fxml"));
        escenarioBase.setTitle("Menú Principal");
        escenarioBase.show();
    }
}
