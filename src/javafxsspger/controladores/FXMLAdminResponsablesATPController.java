/*
* Título del programa: Controlador de Administrador de Responsables de anteproyecto
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 12/06/2023
* Descripción: Clase controladora del Administrador de responsables del anteproyecto para agregar
* estudiantes como responsables de un anteproyecto ya aprobado
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafxsspger.modelo.pojo.Estudiante;

public class FXMLAdminResponsablesATPController implements Initializable {

    @FXML
    private TextField tfMatriculaBusqueda;
    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colMatricula;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
    }

    @FXML
    private void clicAgregar(ActionEvent event) {
    }
    
}
