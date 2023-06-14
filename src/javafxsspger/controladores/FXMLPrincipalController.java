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
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Utilidades;

public class FXMLPrincipalController implements Initializable {

    @FXML
    private Pane menu;
    @FXML
    private ImageView imgMenu;
    private boolean menuAbierto;
    @FXML
    private Pane paneCronograma;
    @FXML
    private Pane paneEstudiantes;
    @FXML
    private Pane paneCuerposAcademicos;
    @FXML
    private Pane paneLgac;
    @FXML
    private Pane paneAdminCursos;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuAbierto = false;
        if(Academico.getInstanciaSingleton() != null){
            paneCronograma.setVisible(false);
            if(!Academico.getInstanciaSingleton().isEsAdmin()){
                paneCuerposAcademicos.setVisible(false);
                paneLgac.setVisible(false);
            }
        }
        
        if(Estudiante.getInstanciaSingleton() != null){
           paneAdminCursos.setVisible(false);
           paneEstudiantes.setVisible(false);
           paneCuerposAcademicos.setVisible(false);
           paneLgac.setVisible(false);
        }
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
        
        Estudiante estudiante = Estudiante.getInstanciaSingleton();
        int idEstudiante = estudiante.getIdEstudiante();
        boolean tieneAnteproyecto = EstudianteDAO.verificarTieneAnteproyecto(idEstudiante);
           if (!tieneAnteproyecto) {
                    Utilidades.mostrarDialogoSimple("El estudiante no tiene anteproyecto", 
                            "El estudiante no tienen ningún anteproyecto asignado.",Alert.AlertType.WARNING);
           }else{
                    Stage escenarioEstudiantes = new Stage();
                    escenarioEstudiantes.setScene(Utilidades.inicializaEscena(
                            "vistas/FXMLVerActividadesCronograma.fxml"));
                    escenarioEstudiantes.setTitle("Estudiantes");
                    escenarioEstudiantes.initModality(Modality.APPLICATION_MODAL);
                    escenarioEstudiantes.showAndWait();
           }
    }



    private void clicVerAvances(ActionEvent event) {
              Stage escenarioEstudiantes = new Stage();
        escenarioEstudiantes.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLConsultarAvancesAnteproyectos.fxml"));
        escenarioEstudiantes.setTitle("Avances");
        escenarioEstudiantes.initModality(Modality.APPLICATION_MODAL);
        escenarioEstudiantes.showAndWait();
    }

    @FXML
    private void clicIrAdminCursos(MouseEvent event) {
        Stage escenarioEstudiantes = new Stage();
        escenarioEstudiantes.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLAdminCursos.fxml"));
        escenarioEstudiantes.setTitle("Administrador de Cursos");
        escenarioEstudiantes.initModality(Modality.APPLICATION_MODAL);
        escenarioEstudiantes.showAndWait();
    }

    @FXML
    private void clicIrVerEstudiantes(MouseEvent event) {
                  Stage escenarioEstudiantes = new Stage();
        escenarioEstudiantes.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLVerEstudiantes.fxml"));
        escenarioEstudiantes.setTitle("Estudiantes");
        escenarioEstudiantes.initModality(Modality.APPLICATION_MODAL);
        escenarioEstudiantes.showAndWait();
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
        escenarioAnteproyectos.setTitle("Líneas de Generación y Aplicación del Conocimiento");
        escenarioAnteproyectos.initModality(Modality.APPLICATION_MODAL);
        escenarioAnteproyectos.showAndWait();         
    }
    
    
    
}
