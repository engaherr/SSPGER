/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
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

/**
 * FXML Controller class
 *
 * @author kikga
 */
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
    
    private boolean verPostulados;
    @FXML
    private Button btnVerPostulados;
    @FXML
    private Button btnVerPublicados;
    @FXML
    private Button btnVerMisAnteproyectos;
    @FXML
    private Button btnCrearAnteproyecto;
    /**
     * Initializes the controller class.
     */
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
                if(estado.equals("Postulado")){
                    filtradoAnteproyectos.setPredicate(anteproyecto -> {
                        boolean estadoMatch = anteproyecto.getEstado().equals(estado);
                        int idResponsableCA = 
                                Academico.getInstanciaSingleton().getIdCAResponsable();
                        boolean mismoCuerpoAcademico = anteproyecto.getIdCuerpoAcademico() == 
                                idResponsableCA;
                        return estadoMatch && mismoCuerpoAcademico;
                    });
                }else{
                    filtradoAnteproyectos.setPredicate(anteproyecto -> 
                        anteproyecto.getEstado().equals(estado));
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
    public void notificarOperacionGuardar() {
        cargarInformacionTabla("Disponible");
    }

    @Override
    public void notificarOperacionActualizar() {
        cargarInformacionTabla("Postulado");
    }

    @FXML
    private void clicVerAnteproyectosPostulados(ActionEvent event) {
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
    }
}

