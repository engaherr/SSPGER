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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Utilidades;

public class FXMLPrincipalAdministradorController implements Initializable {

    @FXML
    private ImageView imgMenu;
    @FXML
    private Pane menu;
    private boolean menuAbierto;

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
    private void clicCuerposAcademicos(MouseEvent event) {
        actualizaEstadoMenu(-255, false, "recursos/menu.png");
        Stage escenarioAnteproyectos = new Stage();
        escenarioAnteproyectos.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLConsultarCuerposAcademicos.fxml"));
        escenarioAnteproyectos.setTitle("Cuerpos Académicos");
        escenarioAnteproyectos.initModality(Modality.APPLICATION_MODAL);
        escenarioAnteproyectos.showAndWait();        
    }

    @FXML
    private void clicLGAC(MouseEvent event) {
        actualizaEstadoMenu(-255, false, "recursos/menu.png");
        Stage escenarioAnteproyectos = new Stage();
        escenarioAnteproyectos.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLConsultarLGAC.fxml"));
        escenarioAnteproyectos.setTitle("Líneas de Generación del conocimiento");
        escenarioAnteproyectos.initModality(Modality.APPLICATION_MODAL);
        escenarioAnteproyectos.showAndWait();            
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

}
