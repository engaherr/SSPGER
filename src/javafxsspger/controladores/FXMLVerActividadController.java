/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
    }

    @FXML
    private void clicCalificar(ActionEvent event) {
    }
    
}
