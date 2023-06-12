/*
* Título del programa: Controlador de Cuerpo Académico
* Autor: Jasiel Emir Zavaleta García
* Fecha: 08/06/2023
* Descripción: Clase controladora de vista FXMLRegistrarCA.fxml
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.Lgac;


public class FXMLRegistrarCAController implements Initializable {

    @FXML
    private Label lbNombreFormulario;
    @FXML
    private ComboBox<?> cbGrado;
    @FXML
    private ComboBox<?> cbDependencias;
    @FXML
    private TextField tfClave;
    @FXML
    private TextField tfNombreCA;
    @FXML
    private TableColumn colNombreLgac;
    @FXML
    private TableColumn colSeleccionarLgac;
    @FXML
    private TableColumn colNumeroDePersonal;
    @FXML
    private TableColumn colNombreAcademico;
    @FXML
    private TableColumn colPaternoAcademico;
    @FXML
    private TableView<Lgac> tvLgac;
    @FXML
    private TableView<CuerpoAcademico> tvAcademicos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicGuardarLgac(ActionEvent event) {
    }

    @FXML
    private void clicCancelarGuardado(ActionEvent event) {
    }
 
    private void configurarTablaAcademicos(){
        
    }    
}
