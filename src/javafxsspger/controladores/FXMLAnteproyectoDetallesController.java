/*
* Título del programa: Controlador de Detalles de Anteproyecto
* Autor: Enrique Gamboa Hernández
* Fecha: 05/06/2023
* Descripción: Clase controladora de la vista FXMLAnteproyectoDetalles.fxml
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

public class FXMLAnteproyectoDetallesController implements Initializable, INotificacionOperacion {

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
    
    private INotificacionOperacion interfazNotificacion;
    
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Label lbErrorComentarios;
    @FXML
    private TextArea taComentarios;
    @FXML
    private TableView<Estudiante> tvEstudiantesResponsables;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private Button btnAgregarEstudiante;
    
    private ObservableList<Estudiante> estudiantesResponsables;

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
    
    public void inicializarInformacionDetalles(Anteproyecto anteproyecto,
            INotificacionOperacion interfazNotificacion){
        this.anteproyectoDetalles = anteproyecto;
        this.interfazNotificacion = interfazNotificacion;
        cargarInformacionDetalles();
        
        try{
            if(Academico.getInstanciaSingleton().getIdAcademico() == 
                    anteproyectoDetalles.getIdDirector() && "Disponible".equals(
                            anteproyectoDetalles.getEstado())){
                cargarInformacionResponsables();
                tvEstudiantesResponsables.setVisible(true);
                btnAgregarEstudiante.setVisible(true);
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    
    private void cargarInformacionDetalles() {
        lbAlumnosParticipantes.setText(
                Integer.toString(anteproyectoDetalles.getNumAlumnosParticipantes()));
        lbBibliografiaRecomendada.setText(anteproyectoDetalles.getBibliografiaRecomendada());
        lbCodirectores.setText(anteproyectoDetalles.getCodirectores());
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
        
        if(!"Postulado".equals(anteproyectoDetalles.getEstado())){
            btnRechazar.setVisible(false);
            btnValidar.setVisible(false);
        }
    }

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) taComentarios.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicValidarAnteproyecto(ActionEvent event) {
        boolean publicarAtp = Utilidades.mostrarDialogoConfirmacion("Publicar Anteproyecto", 
                "¿Publicar anteproyecto " + anteproyectoDetalles.getNombreTrabajo() + 
                        " como 'Disponible'?");
        if(publicarAtp){
            anteproyectoDetalles.setIdEstadoATP(4);
            anteproyectoDetalles.setEstado("Disponible");
            int codigoRespuesta = AnteproyectoDAO.modificarAnteproyecto(anteproyectoDetalles);
            switch(codigoRespuesta){
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de Conexión",
                            "El anteproyecto no pudo ser validado debido a un error de conexion...",
                            Alert.AlertType.ERROR);
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al validar", 
                            "El anteproyecto no puede ser validado, por favor intente más tarde", 
                            Alert.AlertType.WARNING);
                    break;
                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Anteproyecto validado", 
                            "El anteproyecto ha sido publicado exitosamente",
                            Alert.AlertType.INFORMATION);
                    cerrarVentana();
                    interfazNotificacion.notificarOperacionActualizar("Postulados");
            }
        }
    }

    @FXML
    private void clicRechazarAnteproyecto(ActionEvent event) {
        taComentarios.setVisible(true);
        taComentarios.setDisable(false);
        btnAceptar.setVisible(true);
        btnAceptar.setDisable(false);
        btnCancelar.setVisible(true);
        btnCancelar.setDisable(false);
        
        btnRechazar.setVisible(false);
        btnValidar.setVisible(false);
        btnValidar.setDisable(true);
        btnRechazar.setDisable(true);
        lbComentarios.setVisible(true);
        lbComentarios.setText("Comentarios y Sugerencias");
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbAlumnosParticipantes.getScene().getWindow();
        escenarioBase.close();
    }

    @FXML
    private void clicAceptarRechazo(ActionEvent event) {
        validarCampoComentarios();
    }

    @FXML
    private void clicCancelarRechazo(ActionEvent event) {
        lbErrorComentarios.setVisible(false);
        
        lbComentarios.setVisible(false);
        lbComentarios.setDisable(true);
        taComentarios.setVisible(false);
        taComentarios.setDisable(true);
        btnAceptar.setVisible(false);
        btnAceptar.setDisable(true);
        btnCancelar.setVisible(false);
        btnCancelar.setDisable(true);
        
        btnRechazar.setVisible(true);
        btnValidar.setVisible(true);
        btnValidar.setDisable(false);
        btnRechazar.setDisable(false);
    }

    private void validarCampoComentarios() {
        taComentarios.setStyle("-fx-border-width: 0;");
        lbErrorComentarios.setVisible(false);
        String comentarios = taComentarios.getText();
        
        if(comentarios.trim().isEmpty()){
            taComentarios.setStyle(
                    "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;");
            lbErrorComentarios.setVisible(true);
        }else{
            anteproyectoDetalles.setIdEstadoATP(3);
            anteproyectoDetalles.setEstado("Rechazado");
            anteproyectoDetalles.setComentarios(comentarios);
            rechazarAnteproyecto(anteproyectoDetalles);
        }
        
    }

    private void rechazarAnteproyecto(Anteproyecto anteproyectoRechazado) {
        int codigoRespuesta = AnteproyectoDAO.modificarAnteproyecto(anteproyectoRechazado);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", 
                        "El Anteproyecto no pudo ser rechazado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información", 
                        "La Información de los comentarios no puede ser guardada, "
                                + "por favor verifique su información e intente más tarde.", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Anteproyecto rechazado", 
                        "Los comentarios sobre el anteproyecto han sido enviados correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionActualizar("Postulado");
                break;
        }
    }

    @FXML
    private void clicAgregarEstudiante(ActionEvent event) {
        if(anteproyectoDetalles.getResponsablesActivos() >= 
                anteproyectoDetalles.getNumAlumnosParticipantes()){
            Utilidades.mostrarDialogoSimple("Anteproyecto lleno", 
                    "El anteproyecto ha llegado a su máximo de alumnos Reponsables",
                    Alert.AlertType.WARNING);
        }else{
            try {
                FXMLLoader accesoControlador = new FXMLLoader(
                        JavaFXSSPGER.class.getResource("vistas/FXMLAdminResponsablesATP.fxml"));
                Parent vista = accesoControlador.load();
                FXMLAdminResponsablesATPController admin = accesoControlador.getController();

                admin.inicializarInformacion(anteproyectoDetalles, this);

                Stage escenarioAdminResponsables = new Stage();
                escenarioAdminResponsables.setScene(new Scene(vista));
                escenarioAdminResponsables.setTitle("Administrador de Responsables de Anteproyecto");
                escenarioAdminResponsables.initModality(Modality.APPLICATION_MODAL);
                escenarioAdminResponsables.showAndWait();
            } catch (IOException ex) {
                ex.getCause();
            }
        }
    }


    private void cargarInformacionResponsables() {
        estudiantesResponsables = FXCollections.observableArrayList();
        EstudianteRespuesta respuestaBD = EstudianteDAO.obtenerEstudiantesPorAnteproyecto(
                anteproyectoDetalles.getIdAnteproyecto());
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión",
                        "Lo sentimos por el momento no hay conexión para poder recuperar la "
                                + "informacion", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intente de nuevo más "
                                + "tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                estudiantesResponsables.addAll(respuestaBD.getEstudiantes());
                configurarTablaResponsables();
                tvEstudiantesResponsables.setItems(estudiantesResponsables);
                break;
        }
    }
    
    private void configurarTablaResponsables(){
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("email"));
        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
    }

    @Override
    public void notificarOperacionGuardar(String estado) {
        cargarInformacionResponsables();
    }

    @Override
    public void notificarOperacionActualizar(String estado) {
    }
}
