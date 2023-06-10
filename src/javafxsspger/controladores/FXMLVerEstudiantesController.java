/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

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
    
    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tvEstudiantes.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicVerActividades(ActionEvent event) {
    }
    
}
