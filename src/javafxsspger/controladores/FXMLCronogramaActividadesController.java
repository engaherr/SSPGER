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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        /*if(!tieneAnteproyectoAsignado(Estudiante.getInstanciaSingleton())){
            Utilidades.mostrarDialogoSimple("Sin anteproyecto asignado",
                    "Para acceder al cronograma de actividades debes tener un Anteproyecto asignado"
                            + ". Para esto solicita un Anteproyecto con tu profesor de Proyecto "
                            + "Guiado y/o Experiencia Recepcional", Alert.AlertType.WARNING);
            cerrarVentana();
        }*/
        
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
    /*
    private boolean tieneAnteproyectoAsignado(Estudiante estudianteLogeado){
        AnteproyectoRespuesta anteproyectosBD = AnteproyectoDAO.obtenerInformacionAnteproyectos();
        switch(anteproyectosBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de Conexión",
                            "No se pudo obtener la información del Anteproyecto debido a un error "
                                    + "de conexión. Por favor intentélo más tarde...",
                            Alert.AlertType.ERROR);
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al Consultar",
                            "La información del Cronograma no pudo se",
                            Alert.AlertType.WARNING);
                    break;
                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Producto eliminado",
                            "El artículo fue eliminado exitosamente del inventario",
                            Alert.AlertType.INFORMATION);
                    break;
        }
    }*/
    
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
