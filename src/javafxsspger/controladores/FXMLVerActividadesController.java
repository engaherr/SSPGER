/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.modelo.dao.ActividadDAO;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.ActividadRespuesta;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author dplat
 */
public class FXMLVerActividadesController implements Initializable {

    @FXML
    private TableView<Actividad> tvActividades;
    @FXML
    private TableColumn tcActividad;
    @FXML
    private TableColumn tcFechaInicio;
    @FXML
    private TableColumn tcFechaFinal;
    @FXML
    private Label lbTitulo;
    private ObservableList<Actividad> actividades;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    
    
    
    private void configurarTabla(){
          tcActividad.setCellValueFactory(new PropertyValueFactory("nombre"));
          tcFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
          tcFechaFinal.setCellValueFactory(new PropertyValueFactory("fechaFin"));
    }
    
public void setEstudianteSeleccionado(Estudiante estudiante) {
    cargarInformaciónTabla(estudiante.getIdEstudiante());
    
}


    private void cargarInformaciónTabla(int idEstudiante){
          actividades = FXCollections.observableArrayList();
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerActividadesdelEstudiante(idEstudiante);
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
                actividades.addAll(respuestaBD.getActividades());
                tvActividades.setItems(actividades);
        }   
    }
    
    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tvActividades.getScene().getWindow();
        escenarioPrincipal.close();
    }

}
