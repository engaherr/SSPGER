/*
* Titulo del programa: Controlador del Cronograma de Actividades
* Autor: Enrique Gamboa Hernández
* Fecha: 09/06/2023
* Descripción: Clase controladora de la vista FXMLCronogramaActividades.fxml
*/
package javafxsspger.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.utils.Utilidades;

public class FXMLCronogramaActividadesController implements Initializable, INotificacionOperacion{

    private Label lbAnteproyecto;
    private DatePicker dpFechaInicio;
    private DatePicker dpFechaFin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        dpFechaInicio.setValue(LocalDate.now());
        
        dpFechaInicio.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (dpFechaInicio.getValue() != null && dpFechaInicio.getValue().isAfter(
                    dpFechaFin.getValue())) {
                dpFechaInicio.setValue(null);

                Utilidades.mostrarDialogoSimple("Fecha no válida", "Ingrese una fecha de inicio de "
                        + "lapso inferior a la fecha de fin del lapso", Alert.AlertType.WARNING);
            }else{
                actualizarActividades();
            }
        });

        dpFechaFin.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (dpFechaFin.getValue() != null && dpFechaFin.getValue().isBefore(
                    dpFechaInicio.getValue())) {
                dpFechaFin.setValue(null);

                Utilidades.mostrarDialogoSimple("Fecha no válida", "Ingrese una fecha de fin de "
                        + "lapso posterior a la fecha de inicio del lapso", Alert.AlertType.ERROR);
            }else{
                actualizarActividades();
            }
        });
    }

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioPrincipal = (Stage) lbAnteproyecto.getScene().getWindow();
        escenarioPrincipal.close();
    }

    private void actualizarActividades() {
        
    }   

    @Override
    public void notificarOperacionGuardar(String estado) {
        
    }

    @Override
    public void notificarOperacionActualizar(String estado) {
        
    }

    @FXML
    private void clicCrearCurso(ActionEvent event) {
    }
}
