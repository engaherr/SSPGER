/*
* Título del programa: Controlador de listado de Cuerpos Académicos
* Autor: Jasiel Emir Zavaleta García
* Fecha: 08/06/2023
* Descripción: Clase controladora de vista FXMLConsultarCuerposAcademicos.fxml
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.utils.Utilidades;

public class FXMLConsultarCuerposAcademicosController implements Initializable {

    @FXML
    private Label lbNombreFormulario;
    @FXML
    private TableView<CuerpoAcademico> tvCuerposAcademicos;
    
    @FXML
    private TableColumn colClave;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colGrado;
    @FXML
    private TableColumn colDes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    private void configurarTabla(){
        colClave.setCellValueFactory(new PropertyValueFactory("clave"));        
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colGrado.setCellValueFactory(new PropertyValueFactory("gradoConsolidacion"));
        colDes.setCellValueFactory(new PropertyValueFactory("dependencia"));
  
}  
    @FXML
    private void clicRegistrar(ActionEvent event) {
        Stage escenarioFormulario = new Stage();
        escenarioFormulario.setScene(Utilidades.inicializaEscena("vistas/FXMLRegistrarCA.fxml"));
        escenarioFormulario.setTitle("Formulario");
        escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
        escenarioFormulario.showAndWait();          
    }

    @FXML
    private void clicVerDetalles(ActionEvent event) {
        CuerpoAcademico caSeleccionado = tvCuerposAcademicos.getSelectionModel().getSelectedItem();
        if(caSeleccionado != null){
            //irFormulario(true,lgacSeleccionada);
        }else{
            Utilidades.mostrarDialogoSimple("Atención","Por favor selecciona "
                    + "una promoción para poder editar", Alert.AlertType.WARNING);
        }   
    }
    
}
