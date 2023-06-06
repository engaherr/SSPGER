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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.Estudiante;

/**
 * FXML Controller class
 *
 * @author kikga
 */
public class FXMLAnteproyectoDetallesController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbDescripcionTrabajoInvestigacion;
    @FXML
    private Label lbDescripcionTrabajoRecepcional;
    @FXML
    private Label lbResultadosEsperados;
    @FXML
    private Label lbBibliografiaRecomendada;
    @FXML
    private Label lbComentarios;
    @FXML
    private TextField tfComentarios;
    @FXML
    private Label lbCuerpoAcademico;
    @FXML
    private Label lbProyectoInvestigacion;
    @FXML
    private Label lbLGAC;
    @FXML
    private Label lbLineaInvestigacion;
    @FXML
    private Label lbDuracionAproximada;
    @FXML
    private Label lbModalidad;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbRequisitos;
    @FXML
    private Label lbDirector;
    @FXML
    private Label lbCodirectores;
    @FXML
    private Label lbAlumnosParticipantes;
    
    private Anteproyecto anteproyectoDetalles;
    @FXML
    private Button btnValidar;
    @FXML
    private Button btnRechazar;
    @FXML
    private ScrollPane spEscenarioBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Estudiante.getInstanciaSingleton() != null){
            btnRechazar.setVisible(false);
            btnValidar.setVisible(false);
        }else if(Academico.getInstanciaSingleton() != null && 
                !Academico.getInstanciaSingleton().isEsResponsableCA()){
            btnRechazar.setVisible(false);
            btnValidar.setVisible(false);
        }
    }
    
    public void inicializarInformacionDetalles(Anteproyecto anteproyecto){
        this.anteproyectoDetalles = anteproyecto;
        cargarInformacionDetalles();
    }
    
    private void cargarInformacionDetalles() {
        String codirectores = null;
        lbAlumnosParticipantes.setText(
                Integer.toString(anteproyectoDetalles.getNumAlumnosParticipantes()));
        lbBibliografiaRecomendada.setText(anteproyectoDetalles.getBibliografiaRecomendada());
        /*if(anteproyectoDetalles.getCodirectores().length != 0){
            for(String codirector : anteproyectoDetalles.getCodirectores()){
                codirectores = codirector + " ";
            }
            lbCodirectores.setText(codirectores);
        }*/
        lbComentarios.setText(anteproyectoDetalles.getComentarios());
        lbCuerpoAcademico.setText(anteproyectoDetalles.getNombreCA());
        lbDescripcionTrabajoInvestigacion.setText(
                anteproyectoDetalles.getDescripcionProyectoInvestigacion());
        lbDescripcionTrabajoRecepcional.setText(
                anteproyectoDetalles.getDescripcionTrabajoRecepcional());
        lbDirector.setText(anteproyectoDetalles.getNombreDirector());
        lbDuracionAproximada.setText(anteproyectoDetalles.getMesesDuracionAproximada() + " meses");
        lbLGAC.setText(anteproyectoDetalles.getNombreLgac());
        lbLineaInvestigacion.setText(anteproyectoDetalles.getLineaInvestigacion());
        lbModalidad.setText(anteproyectoDetalles.getModalidad());
        lbNombre.setText(anteproyectoDetalles.getNombreTrabajo());
        lbProyectoInvestigacion.setText(anteproyectoDetalles.getProyectoInvestigacion());
        lbRequisitos.setText(anteproyectoDetalles.getRequisitos());
        lbResultadosEsperados.setText(anteproyectoDetalles.getResultadosEsperados());
    }

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tfComentarios.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicValidarAnteproyecto(ActionEvent event) {
    }

    @FXML
    private void clicRechazarAnteproyecto(ActionEvent event) {
    }
    
}
