/*
* Título del programa: Controlador de Anteproyecto
* Autor: Enrique Gamboa Hernández
* Fecha: 05/06/2023
* Descripción: Clase controladora de vista FXMLAnteproyecto.fxml
*/
package javafxsspger.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.AnteproyectoRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

public class FXMLAnteproyectoController implements Initializable, INotificacionOperacion {

    @FXML
    private TableView<Anteproyecto> tvAnteproyectos;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colDirector;
    @FXML
    private TableColumn colAlumnosMaximos;
    @FXML
    private TableColumn colDuracion;
    @FXML
    private TableColumn colModalidad;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TextField tfBusqueda;
    
    private ObservableList<Anteproyecto> anteproyectos;
    private FilteredList<Anteproyecto> filtradoAnteproyectos;
    
    @FXML
    private Label lbTitulo;
    @FXML
    private Button btnVerPostulados;
    @FXML
    private Button btnVerPublicados;
    @FXML
    private Button btnVerMisAnteproyectos;
    @FXML
    private Button btnCrearAnteproyecto;
    @FXML
    private Button btnModificarAnteproyecto;
    @FXML
    private Button btnVerAvances;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvAnteproyectos.setRowFactory(tableView -> {
            TableRow<Anteproyecto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                        Anteproyecto anteproyectoSeleccionado = row.getItem();
                        mostrarDetallesAnteproyecto(anteproyectoSeleccionado);
                    }
            });
            return row;
        });
        configurarTabla();
        cargarInformacionTabla("Disponible");
        configurarBusquedaTabla();
        
        if(Academico.getInstanciaSingleton() == null || 
                !Academico.getInstanciaSingleton().isEsResponsableCA())
            btnVerPostulados.setVisible(false);
        
        if(Academico.getInstanciaSingleton() == null){
            btnVerMisAnteproyectos.setVisible(false);
            btnVerPublicados.setVisible(false);
            btnVerAvances.setVisible(false);
        }
    }

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tfBusqueda.getScene().getWindow();
        escenarioPrincipal.close();
    }

    private void configurarTabla() {
        colAlumnosMaximos.setCellValueFactory(new PropertyValueFactory("numAlumnosParticipantes"));
        colDirector.setCellValueFactory(new PropertyValueFactory("nombreDirector"));
        colDuracion.setCellValueFactory(new PropertyValueFactory("mesesDuracionAproximada"));
        
        colDuracion.setCellFactory(column -> {
            return new TableCell<Anteproyecto, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.toString() + " meses");
                    }
                }
            };
        });
        
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        colModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreTrabajo"));
        
        tvAnteproyectos.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, 
                    Number newValue) {
                TableHeaderRow header = (TableHeaderRow) tvAnteproyectos.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, 
                            Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
    }

    private void cargarInformacionTabla(String estado) {
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoRespuesta respuestaBD = AnteproyectoDAO.obtenerInformacionAnteproyectos();
        switch(respuestaBD.getCodigoRespuesta()){
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
                anteproyectos.addAll(respuestaBD.getAnteproyectos());
                filtradoAnteproyectos = new FilteredList<>(anteproyectos);
            switch (estado) {
                case "Postulado":
                    filtradoAnteproyectos.setPredicate(anteproyecto -> {
                        boolean estadoMatch = anteproyecto.getEstado().equals(estado);
                        int idResponsableCA = 
                                Academico.getInstanciaSingleton().getIdCAResponsable();
                        boolean mismoCuerpoAcademico = anteproyecto.getIdCuerpoAcademico() == 
                                idResponsableCA;
                        return estadoMatch && mismoCuerpoAcademico;
                    });
                    break;
                case "Borrador":
                    filtradoAnteproyectos.setPredicate(anteproyecto -> {
                        boolean estadoMatch = (anteproyecto.getEstado().equals("Rechazado"))
                                || (anteproyecto.getEstado().equals("Borrador")) ||
                                       (anteproyecto.getEstado().equals("Disponible"));
                        int idDirector = anteproyecto.getIdDirector();
                        int idAcademico = Academico.getInstanciaSingleton().getIdAcademico();
                        return estadoMatch && idDirector == idAcademico;
                    });
                    break;
                default:
                    filtradoAnteproyectos.setPredicate(anteproyecto ->
                            anteproyecto.getEstado().equals(estado));
                    break;
            }
                tvAnteproyectos.setItems(filtradoAnteproyectos);
                break;

        }
    }
    
    private void configurarBusquedaTabla() {
        filtradoAnteproyectos = new FilteredList<>(anteproyectos, anteproyecto -> 
                anteproyecto.getEstado().equals("Disponible"));

        tfBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
            filtradoAnteproyectos.setPredicate(anteproyecto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return anteproyecto.getEstado().equals("Disponible");
                }
                String lowerNewValue = newValue.toLowerCase();
                return (anteproyecto.getEstado().equals("Disponible") || 
                        anteproyecto.getEstado().equals("Asignado"))
                        && (anteproyecto.getNombreTrabajo().toLowerCase().contains(lowerNewValue)
                        || anteproyecto.getNombreDirector().toLowerCase().contains(lowerNewValue));
            });
        });

        SortedList<Anteproyecto> sortedListAnteproyectos = new SortedList<>(filtradoAnteproyectos);
        sortedListAnteproyectos.comparatorProperty().bind(tvAnteproyectos.comparatorProperty());
        tvAnteproyectos.setItems(sortedListAnteproyectos);
    }


    private void mostrarDetallesAnteproyecto(Anteproyecto anteproyecto){
        try {
            
            FXMLLoader accesoControlador = new FXMLLoader(
                    JavaFXSSPGER.class.getResource("vistas/FXMLAnteproyectoDetalles.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAnteproyectoDetallesController detalles = accesoControlador.getController();
            detalles.inicializarInformacionDetalles(anteproyecto, this);
            
            Stage escenarioDetalles = new Stage();
            escenarioDetalles.setScene(new Scene(vista));
            escenarioDetalles.setTitle("Detalles de Anteproyecto");
            escenarioDetalles.initModality(Modality.APPLICATION_MODAL);
            escenarioDetalles.showAndWait();
        } catch (IOException ex) {
        }
    }

    @Override
    public void notificarOperacionGuardar(String estado) {
        cargarInformacionTabla(estado);
    }

    @Override
    public void notificarOperacionActualizar(String estado) {
        cargarInformacionTabla(estado);
    }

    @FXML
    private void clicVerAnteproyectosPostulados(ActionEvent event) {
        btnModificarAnteproyecto.setVisible(false);
        btnCrearAnteproyecto.setDisable(true);
        btnCrearAnteproyecto.setVisible(false);
        btnVerMisAnteproyectos.setDisable(false);
        btnVerPublicados.setDisable(false);
        cargarInformacionTabla("Postulado");
        btnVerPostulados.setDisable(true);
        tfBusqueda.setVisible(false);
        lbTitulo.setText("Anteproyectos Postulados");
    }

    @FXML
    private void clicVerAnteproyectosPublicados(ActionEvent event) {
        btnModificarAnteproyecto.setVisible(false);
        btnCrearAnteproyecto.setVisible(false);
        btnCrearAnteproyecto.setDisable(true);
        btnVerPostulados.setDisable(false);
        btnVerMisAnteproyectos.setDisable(false);
        cargarInformacionTabla("Disponible");
        btnVerPublicados.setDisable(true);
        tfBusqueda.setVisible(true);
        lbTitulo.setText("Anteproyectos Publicados");
    }

    @FXML
    private void clicVerMisAnteproyectos(ActionEvent event) {
        btnModificarAnteproyecto.setVisible(true);
        btnVerMisAnteproyectos.setDisable(false);
        btnVerPublicados.setDisable(false);
        btnVerMisAnteproyectos.setDisable(true);
        cargarInformacionTabla("Borrador");
        tfBusqueda.setVisible(false);
        btnCrearAnteproyecto.setVisible(true);
        btnCrearAnteproyecto.setDisable(false);
        lbTitulo.setText("Mis Anteproyectos");
    }

    @FXML
    private void clicCrearAnteproyecto(ActionEvent event) {
        irFormulario(false, null);
    }

    private void irFormulario(boolean esEdicion, Anteproyecto anteproyectoEdicion) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource(
                    "vistas/FXMLAnteproyectoFormulario.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAnteproyectoFormularioController formulario = accesoControlador.getController();
            
            formulario.inicializarInformacionFormulario(esEdicion, anteproyectoEdicion, this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicModificarAnteproyecto(ActionEvent event) {
        Anteproyecto anteproyectoSeleccionado = 
                tvAnteproyectos.getSelectionModel().getSelectedItem();
        if(anteproyectoSeleccionado != null ){
            if(anteproyectoSeleccionado.getEstado().equals("Disponible")){
                Utilidades.mostrarDialogoSimple("Anteproyecto ya Publicado",
                        "No se puede modificar un Anteproyecto ya publicado y/o asignado, "
                                + "seleccione uno en estado 'Borrador' o 'Rechazado' e intentélo de"
                                + " nuevo. Si desea modificar un anteproyecto de su autoría ya "
                                + "publicado comuniquese con el Cuerpo Académico al que pertenezca "
                                + "el anteproyecto", Alert.AlertType.WARNING);
            }else 
                irFormulario(true, anteproyectoSeleccionado);
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un Anteproyecto", 
                    "Selecciona el borrador o rechazado"
                            + " en la tabla del Anteproyecto para su edición", 
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicVerAvances(ActionEvent event) {
                      Stage escenarioEstudiantes = new Stage();
        escenarioEstudiantes.setScene(Utilidades.inicializaEscena(
                "vistas/FXMLConsultarAvancesAnteproyectos.fxml"));
        escenarioEstudiantes.setTitle("Avances");
        escenarioEstudiantes.initModality(Modality.APPLICATION_MODAL);
        escenarioEstudiantes.showAndWait();
    }
}

