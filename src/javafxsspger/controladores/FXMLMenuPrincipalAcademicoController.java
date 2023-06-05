/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author kikga
 */
public class FXMLMenuPrincipalAcademicoController implements Initializable {

    @FXML
    private Pane menu;
    @FXML
    private ImageView imgMenu;
    private boolean menuAbierto;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuAbierto = false;
    }    

    @FXML
    private void clicOpenMenu(MouseEvent event) {
        if(menuAbierto)
            actualizaEstadoMenu(-255, false, "recursos/menu.png");
        else
            actualizaEstadoMenu(255, true, "recursos/close.png");
    }

    @FXML
    private void clicIrAdminAlumnos(MouseEvent event) {
    }
    
    private void actualizaEstadoMenu(int posicion, boolean abierto, String icono){
        animacionMenu(posicion);
        menuAbierto = abierto;
        imgMenu.setImage(new Image(JavaFXSSPGER.class.getResource(icono).toString()));
    }
    
    private void animacionMenu(int posicion){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(menu);
        translate.setDuration(Duration.millis(300));
        translate.setByX(posicion);
        translate.setAutoReverse(true);
        translate.play();
    }

    @FXML
    private void clicCerrarSesion(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) imgMenu.getScene().getWindow();
        escenarioPrincipal.setScene(Utilidades.inicializaEscena("vistas/FXMLInicioSesion.fxml"));
        escenarioPrincipal.setTitle("Iniciar sesión");
        escenarioPrincipal.show();
    }
}
