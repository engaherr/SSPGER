/*
* Título: Controlador del Formulario de registro de curso
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 10/06/2023
* Descripción: Clase Controladora de la vista del formulario de registro para los cursos
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLCursoFormularioController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfNRC;
    @FXML
    private ComboBox<?> cbPeriodoEscolar;
    @FXML
    private TextField tfNombreDelCurso;
    @FXML
    private ComboBox<?> cbEE;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    private void clicCerrarVentana(MouseEvent event){
        Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
        escenarioPrincipal.close();
    }

}
