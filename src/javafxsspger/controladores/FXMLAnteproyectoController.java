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
public class FXMLAnteproyectoController implements Initializable {

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
    private Button btnVerAnteproyectos; 
    @FXML
    private Label lbTitulo;
    
    private boolean verPostulados;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvAnteproyectos.setPlaceholder(
                new Label("Por el momento no hay Anteproyectos disponibles..."
                        + " Intente realizando una búsqueda"));
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
            btnVerAnteproyectos.setVisible(false);
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
            detalles.inicializarInformacionDetalles(anteproyecto);
            
            Stage escenarioDetalles = new Stage();
            escenarioDetalles.setScene(new Scene(vista));
            escenarioDetalles.setTitle("Detalles de Anteproyecto");
            escenarioDetalles.initModality(Modality.APPLICATION_MODAL);
            escenarioDetalles.showAndWait();
        } catch (IOException ex) {
        }
    }

    @FXML
    private void clicSwitchAnteproyectos(ActionEvent event) {
        if(verPostulados){
            cargarInformacionTabla("Disponible");
            btnVerAnteproyectos.setText("Ver Anteproyectos Postulados");
            tfBusqueda.setVisible(true);
            verPostulados = false;
            lbTitulo.setText("Anteproyectos Publicados");
        }else{
            cargarInformacionTabla("Postulado");
            btnVerAnteproyectos.setText("Ver Anteproyectos Publicados");
            tfBusqueda.setVisible(false);
            verPostulados = true;
            lbTitulo.setText("Anteproyectos Postulados");
        }
    }
}

