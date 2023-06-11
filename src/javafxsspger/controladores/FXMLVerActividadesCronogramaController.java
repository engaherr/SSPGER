/*
* Título del programa: Controlador de Ver actividades del cronograma
* Autor: Omar Dylan Segura Platas
* Fecha: 10/06/2023
* Descripción: Clase controladora de vista FXMLVerActividadesCronograma
*/


package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.ActividadDAO;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.ActividadRespuesta;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

public class FXMLVerActividadesCronogramaController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Actividad> tvActividades;
    @FXML
    private TableColumn<Actividad, String> tcActividades;
    @FXML
    private TableColumn<Actividad, LocalDate> tvFechaInicio;
    @FXML
    private TableColumn<Actividad, LocalDate> tvFechaFin;
    @FXML
    private DatePicker dpFin;
    @FXML
    private DatePicker dpInicio;
    private ObservableList<Actividad> actividades;
    private Actividad actividadSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        Estudiante estudiante = Estudiante.getInstanciaSingleton();
        int idEstudiante = estudiante.getIdEstudiante();
        cargarInformacionTabla(idEstudiante);
        String nombreTrabajo = AnteproyectoDAO.obtenerNombreTrabajoAnteproyecto(idEstudiante); 
        lbTitulo.setText(nombreTrabajo);
        dpInicio.valueProperty().addListener((observable, oldValue, newValue) -> actualizarTabla());
        dpFin.valueProperty().addListener((observable, oldValue, newValue) -> actualizarTabla());
    }

    private void configurarTabla() {
        tcActividades.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tvFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        tvFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        tvActividades.setRowFactory(tv -> {
            TableRow<Actividad> fila = new TableRow<>();
            fila.setOnMouseClicked(event -> {
                if (!fila.isEmpty() && event.getClickCount() == 2) {
                    Actividad actividad = fila.getItem();
                    try {
                        verActividad(actividad);
                    } catch (IOException ex) {
                        Utilidades.mostrarDialogoSimple("Error", "Error al mostrar los detalles de la actividad", Alert.AlertType.ERROR);
                    }
                }
            });
            return fila;
        });
    }

    private void cargarInformacionTabla(int idEstudiante) {
        actividades = FXCollections.observableArrayList();
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerActividadesdelEstudiante(idEstudiante);
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión",
                        "Lo sentimos por el momento no hay conexión para poder cargar la "
                                + "información", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentélo más tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                actividades.addAll(respuestaBD.getActividades());
                tvActividades.setItems(actividades);
        }
    }

    private void verActividad(Actividad actividad) throws IOException {
        actividadSeleccionada = tvActividades.getSelectionModel().getSelectedItem();
        FXMLLoader accesoControlador = new FXMLLoader(
                JavaFXSSPGER.class.getResource("vistas/FXMLVerActividad.fxml"));
        Parent vista = accesoControlador.load();
        FXMLVerActividadController detalles = accesoControlador.getController();
        detalles.setActividadSeleccionada(actividadSeleccionada);
        Stage stage = new Stage();
        stage.setScene(new Scene(vista));
        stage.showAndWait();
    }

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tvActividades.getScene().getWindow();
        escenarioPrincipal.close();
    }


    private void actualizarTabla() {
    LocalDate fechaInicio = dpInicio.getValue();
    LocalDate fechaFin = dpFin.getValue();

    if (fechaInicio != null && fechaFin != null) {
        ObservableList<Actividad> actividadesFiltradas = FXCollections.observableArrayList();

        for (Actividad actividad : actividades) {
           LocalDate fechaActividadInicio = LocalDate.parse(actividad.getFechaInicio(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
            LocalDate fechaActividadFin = LocalDate.parse(actividad.getFechaFin(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));

            if ((fechaActividadInicio.isEqual(fechaInicio) || fechaActividadInicio.isAfter(fechaInicio))
                    && (fechaActividadFin.isEqual(fechaFin) || fechaActividadFin.isBefore(fechaFin))) {
                actividadesFiltradas.add(actividad);
            }
        }

        tvActividades.setItems(actividadesFiltradas);
    } else {
        tvActividades.setItems(actividades);
    }
}

}





