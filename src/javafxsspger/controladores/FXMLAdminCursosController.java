/*
* Título del programa: Controlador del Administrador de Cursos
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 10/06/2023
* Descripción: Clase controladora para el administrador de cursos
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.utils.Utilidades;

public class FXMLAdminCursosController implements Initializable {

    @FXML
    private Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clicCrearCurso(ActionEvent event) {
        Stage escenarioFormulario = new Stage();
        escenarioFormulario.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLCursoFormulario.fxml"));
        escenarioFormulario.setTitle("Formulario de Curso");
        escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
        escenarioFormulario.showAndWait();
    }

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }

    @FXML
    private void clicAgregarEstudiante(ActionEvent event) {
           Stage escenarioEstudiantes = new Stage();
        escenarioEstudiantes.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLAgregarEstudianteCurso.fxml"));
        escenarioEstudiantes.setTitle("Agregar estudiantes a curso");
        escenarioEstudiantes.initModality(Modality.APPLICATION_MODAL);
        escenarioEstudiantes.showAndWait();
    }
    
    
    
}
