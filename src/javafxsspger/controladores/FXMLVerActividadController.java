/*
* Título del programa: Controlador de Ver Actividad
* Autor: Omar Dylan Segura Platas
* Fecha: 09/06/2023
* Descripción: Clase controladora de vista FXMLVerActividadController
*/


package javafxsspger.controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.ActividadDAO;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.ActividadRespuesta;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author dplat
 */
public class FXMLVerActividadController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbFechaComienzo;
    @FXML
    private Label lbFechaEntrega;
    @FXML
    private TextArea taCuerpo;
    @FXML
    private Label lbfechaCreacion;
    private int idActividadSeleccionada;
    private Actividad actividadSeleccionada;

    
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       taCuerpo.setWrapText(true);

    }    

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lbDescripcion.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicCalificar(ActionEvent event) throws IOException {
           FXMLLoader accesoControlador = new FXMLLoader(
                    JavaFXSSPGER.class.getResource("vistas/FXMLEvaluarActividad.fxml"));
            Parent vista = accesoControlador.load();
             FXMLEvaluarActividadController evaluarActividadController = accesoControlador.getController();
             evaluarActividadController.setActividadEvaluar(idActividadSeleccionada);
            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.showAndWait();
    }
    
    
    public void setActividadSeleccionada(Actividad actividad) {
    lbTitulo.setText(actividad.getNombre());
    lbDescripcion.setText(actividad.getDescripcion());
    lbFechaComienzo.setText(actividad.getFechaInicio());
    lbFechaEntrega.setText(actividad.getFechaFin());
     cargarInformacionEntrega(actividad.getIdActividad());
     idActividadSeleccionada = actividad.getIdActividad();
     actividadSeleccionada = actividad;
}
    
    
     public void cargarInformacionEntrega(int idActividad) {
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerDetallesEntrega(idActividad);
        ArrayList<Actividad> actividades = respuestaBD.getActividades();
        if (!actividades.isEmpty()) {
            Actividad actividad = actividades.get(0);
            taCuerpo.setText(actividad.getComentarios());
            lbfechaCreacion.setText(actividad.getFechaCreacion());
        }
    }

  @FXML
private void clicDescargarDocumento(ActionEvent event) {
    System.out.println("Id de la actividad: " + idActividadSeleccionada);
}

}


