/*
* Título del programa: Controlador de Administrador de Responsables de anteproyecto
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 12/06/2023
* Descripción: Clase controladora del Administrador de responsables del anteproyecto para agregar
* estudiantes como responsables de un anteproyecto ya aprobado
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

public class FXMLAdminResponsablesATPController implements Initializable {

    @FXML
    private TextField tfMatriculaBusqueda;
    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colMatricula;
    
    private ObservableList<Estudiante> estudiantes;
    private FilteredList<Estudiante> filtradoEstudiantes;
    private Anteproyecto anteproyectoAdmin;
    private INotificacionOperacion interfazNotificacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        
        estudiantes = FXCollections.observableArrayList();
        EstudianteRespuesta respuestaBD = EstudianteDAO.obtenerEstudiantes();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión",
                        "Por el momento no hay conexión para poder cargar la página",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta",
                        "Hubo un error al cargar la información, por favor intentélo de nuevo más "
                                + "tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                estudiantes.addAll(respuestaBD.getEstudiantes());
        }
    }    

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicAgregar(ActionEvent event) {
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        if(estudianteSeleccionado != null && anteproyectoAdmin.getResponsablesActivos() < 
                anteproyectoAdmin.getNumAlumnosParticipantes()){
            estudianteSeleccionado.setIdAnteproyecto(anteproyectoAdmin.getIdAnteproyecto());
            int respuesta = EstudianteDAO.modificarInformacionEstudiante(estudianteSeleccionado);
            switch(respuesta){
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de Conexión",
                            "El sistema no pudo hacer la asignación debido a un error de conexión",
                            Alert.AlertType.ERROR);
                    estudianteSeleccionado.setIdAnteproyecto(0);
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta",
                            "La información no pudo ser procesada, por favor intentélo de nuevo",
                            Alert.AlertType.WARNING);
                    estudianteSeleccionado.setIdAnteproyecto(0);
                    break;
                case Constantes.OPERACION_EXITOSA:
                    anteproyectoAdmin.setResponsablesActivos(
                            anteproyectoAdmin.getResponsablesActivos() + 1);
                    int respuestaATP = AnteproyectoDAO.modificarAnteproyecto(anteproyectoAdmin);
                    switch(respuestaATP){
                        case Constantes.ERROR_CONEXION:
                            Utilidades.mostrarDialogoSimple("Error de Conexión",
                                "El sistema no pudo hacer la asignación debido a un error de "
                                        + "conexión",
                                Alert.AlertType.ERROR);
                            break;
                        case Constantes.ERROR_CONSULTA:
                            Utilidades.mostrarDialogoSimple("Error de consulta",
                                    "La información no pudo ser procesada, por favor intentélo de "
                                            + "nuevo",
                                    Alert.AlertType.WARNING);
                            break;
                        case Constantes.OPERACION_EXITOSA:
                            Utilidades.mostrarDialogoSimple("Operación exitosa",
                                    "El estudiante ha sido asignado al anteproyecto correctamente",
                                    Alert.AlertType.INFORMATION);
                            cerrarVentana();
                            interfazNotificacion.notificarOperacionGuardar("Responsable Nuevo");
                            break;
                    }
            }
        }else if(anteproyectoAdmin.getResponsablesActivos() >= 
                anteproyectoAdmin.getNumAlumnosParticipantes()){
            Utilidades.mostrarDialogoSimple("Anteproyecto lleno", "El anteproyecto ha llegado a "
                    + "su límite de participantes", Alert.AlertType.WARNING);
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un Estudiante", 
                    "Selecciona el registro en la tabla de estudiantes para su asignación", 
                    Alert.AlertType.WARNING);
        }
    }
    
    public void inicializarInformacion(Anteproyecto anteproyectoAdmin, INotificacionOperacion
            interfazNotificacion){
        this.anteproyectoAdmin = anteproyectoAdmin;
        this.interfazNotificacion = interfazNotificacion;
    }

    @FXML
    private void clicBuscar(ActionEvent event) {
        String matricula = tfMatriculaBusqueda.getText();
        cargarInformacionTabla(matricula);
    }

    private void configurarTabla() {
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));        
    }

    private void cargarInformacionTabla(String matriculaBusqueda) {
        filtradoEstudiantes = new FilteredList<>(estudiantes);
        filtradoEstudiantes.setPredicate(estudiante -> estudiante.getIdAnteproyecto() == 0
                  && estudiante.getMatricula().equals(matriculaBusqueda));
        tvEstudiantes.setItems(filtradoEstudiantes);
    }

    private void cerrarVentana() {
        Stage escenarioBase = (Stage) tfMatriculaBusqueda.getScene().getWindow();
        escenarioBase.close();
    }
    
}
