/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.ActividadDAO;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.ActividadRespuesta;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;
import javafxsspger.controladores.FXMLVerActividadesController;


/**
 * FXML Controller class
 *
 * @author dplat
 */
public class FXMLVerEstudiantesController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> colNombre;
    private ObservableList<Estudiante> estudiantes;
    private Estudiante estudianteSeleccionado;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        
    }    

private void configurarTabla() {
    colNombre.setCellValueFactory(cellData -> {
        Estudiante estudiante = cellData.getValue();
        String nombreCompleto = estudiante.getNombre() + " " +
                                estudiante.getApellidoPaterno() + " " +
                                estudiante.getApellidoMaterno();
        return new SimpleStringProperty(nombreCompleto);
    });
}
    
    private void cargarInformacionTabla(){
     estudiantes = FXCollections.observableArrayList();
        EstudianteRespuesta respuestaBD = EstudianteDAO.obtenerEstudiantes();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión",
                        "Lo sentimos por el momento no hay conexión para poder cargar la "
                                + "información", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentélo más tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                estudiantes.addAll(respuestaBD.getEstudiantes());
                         
                tvEstudiantes.setItems(estudiantes);
        }
    }
    
    public void setEstudianteSeleccionado(Estudiante estudiante) {
    this.estudianteSeleccionado = estudiante;
}

    
    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tvEstudiantes.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicVerActividades(ActionEvent event) throws IOException {
       estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        if (estudianteSeleccionado != null) {
      FXMLLoader accesoControlador = new FXMLLoader(
                    JavaFXSSPGER.class.getResource("vistas/FXMLVerActividades.fxml"));
            Parent vista = accesoControlador.load();
            FXMLVerActividadesController detalles = accesoControlador.getController();
            detalles.setEstudianteSeleccionado(estudianteSeleccionado);
            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.showAndWait();
        } else {
            Utilidades.mostrarDialogoSimple("Seleccione un estudiante", 
                    "Por favor seleccione un estudiante.", Alert.AlertType.INFORMATION);
        }
    }


}
