/*
* Título del programa: Controlador de Menú Principal 
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 05/06/2023
* Descripción: Clase controladora para la vista FXMLPrincipal.fxml que dirige a los módulos
* distintos del sistema dependiendo del tipo de usuario y sus permisos
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Utilidades;

public class FXMLPrincipalController implements Initializable {

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
        Academico.setInstanciaSingleton(null);
        Estudiante.setInstanciaSingleton(null);
    }

    @FXML
    private void clicIrAnteproyectos(MouseEvent event) {
        actualizaEstadoMenu(-255, false, "recursos/menu.png");
        Stage escenarioAnteproyectos = new Stage();
        escenarioAnteproyectos.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLAnteproyectos.fxml"));
        escenarioAnteproyectos.setTitle("Anteproyectos");
        escenarioAnteproyectos.initModality(Modality.APPLICATION_MODAL);
        escenarioAnteproyectos.showAndWait();
    }

    @FXML
    private void clicIrCronograma(MouseEvent event) {
          Stage escenarioEstudiantes = new Stage();
        escenarioEstudiantes.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLVerActividadesCronograma.fxml"));
        escenarioEstudiantes.setTitle("Estudiantes");
        escenarioEstudiantes.initModality(Modality.APPLICATION_MODAL);
        escenarioEstudiantes.showAndWait();
    }

    @FXML
    private void clicIrVerEstudiantes(ActionEvent event) {
        
          Stage escenarioEstudiantes = new Stage();
        escenarioEstudiantes.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLVerEstudiantes.fxml"));
        escenarioEstudiantes.setTitle("Estudiantes");
        escenarioEstudiantes.initModality(Modality.APPLICATION_MODAL);
        escenarioEstudiantes.showAndWait();
    }

    @FXML
    private void clicAgregarEstudianteCurso(ActionEvent event) {
          Stage escenarioEstudiantes = new Stage();
        escenarioEstudiantes.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLAgregarEstudianteCurso.fxml"));
        escenarioEstudiantes.setTitle("Agregar estudiantes a curso");
        escenarioEstudiantes.initModality(Modality.APPLICATION_MODAL);
        escenarioEstudiantes.showAndWait();
    }
    
    
    
}
