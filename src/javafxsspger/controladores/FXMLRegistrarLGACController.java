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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jasie
 */
public class FXMLRegistrarLGACController implements Initializable {

    @FXML
    private Label lbNombreFormulario;
    @FXML
    private TextField tfNombreLgac;
    @FXML
    private TextField lbDescripcionLgac;

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
    
}
