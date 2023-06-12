/*
* Título del programa: Controlador de Evaluar Actividad
* Autor: Omar Dylan Segura Platas
* Fecha: 10/06/2023
* Descripción: Clase controladora de vista FXMLEvaluarActividad
*/

package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.modelo.dao.ActividadDAO;
import javafxsspger.utils.Utilidades;


public class FXMLEvaluarActividadController implements Initializable {

    @FXML
    private TextField tfEvaluacion;
    @FXML
    private Label lbTitulo;
    private int idActividadSeleccionada;


  public void setActividadEvaluar(int idActividad) {
    this.idActividadSeleccionada = idActividad;
}

    
    @Override
     public void initialize(URL url, ResourceBundle rb) {
        // Configurar eventos para el TextField
        tfEvaluacion.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("\\d")) { 
                event.consume();
            }
        });

        tfEvaluacion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 2) { 
                tfEvaluacion.setText(newValue.substring(0, 2));
            }
        });
    }

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
        escenarioPrincipal.close();
     }

    @FXML
    private void clicGuardar(ActionEvent event) {
        String evaluacionStr = tfEvaluacion.getText();
        if (!evaluacionStr.isEmpty()) {
            int evaluacion = Integer.parseInt(evaluacionStr);
            if (evaluacion >= 0 && evaluacion <= 10) { 
                ActividadDAO.EvaluarEntrega(evaluacion, idActividadSeleccionada);
                Utilidades.mostrarDialogoSimple("Entraga Evaluada", "La evaluación se ha registrado correctamente",
                        Alert.AlertType.INFORMATION);
                Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
                escenarioPrincipal.close();
            } else {
                Utilidades.mostrarDialogoSimple("Error", "La evaluación debe estar entre 0 y 10",
                        Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarDialogoSimple("Error", "Ingrese una evaluación",
                    Alert.AlertType.ERROR);
        }
    }
    
}
