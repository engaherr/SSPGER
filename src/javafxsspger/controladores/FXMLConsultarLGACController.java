/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.modelo.dao.LGACDAO;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.LGACRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


/**
 * FXML Controller class
 *
 * @author jasie
 */
public class FXMLConsultarLGACController implements Initializable, INotificacionOperacion {

    @FXML
    private Label lbNombreFormulario;
    @FXML
    private TableView<LGAC> tvLgac;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn  colCuerpoAcademico;
    private ObservableList<LGAC> lgacs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colCuerpoAcademico.setCellValueFactory(new PropertyValueFactory("nombreCuerpoAcademico"));
    }

    
    private void cargarInformacionTabla(){
        lgacs = FXCollections.observableArrayList();
        LGACRespuesta lgacRegistrados = LGACDAO.obtenerTodosLGACs();
        switch(lgacRegistrados.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","No se pudo "
                        + "recuperar la información relacionada con las LGAC, por favor inténtelo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:   
                Utilidades.mostrarDialogoSimple("Error de consulta","Ocurrió "
                        + "un error al intentar recuperar la información "
                        + "inténtelo de nuevo, por favor", Alert.AlertType.ERROR);    
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operacion exitosa","Las LGAC registradas en el "
                    + "sistema se cargaron de forma correcta", Alert.AlertType.INFORMATION);
                lgacs.addAll(lgacRegistrados.getLgacs());
                tvLgac.setItems(lgacs);
                break;
        }
        
    }

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        Stage escenarioFormulario = new Stage();
        escenarioFormulario.setScene(Utilidades.inicializaEscena("vistas/FXMLRegistrarLGAC.fxml"));
        escenarioFormulario.setTitle("Formulario");
        escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
        escenarioFormulario.showAndWait();
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        LGAC lgacSeleccionada = tvLgac.getSelectionModel().getSelectedItem();
        if(lgacSeleccionada != null){
            irFormulario(true,lgacSeleccionada);
        }else{
            Utilidades.mostrarDialogoSimple("Atención","Por favor selecciona "
                    + "una promoción para poder editar", Alert.AlertType.WARNING);
        }        
    }
    
    
    private void irFormulario(boolean esEdicion, LGAC lgacSeleccionada){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                (JavaFXSSPGER.class.getResource("vistas/FXMLRegistrarLGAC.fxml"));
            Parent vista;
            vista = accesoControlador.load();
            
            FXMLRegistrarLGACController formulario = accesoControlador.getController();
            formulario.inicializarInformacionFormulario(esEdicion,lgacSeleccionada,this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Modificación de LGAC");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();         
            
        }catch(IOException ex){
            Logger.getLogger(FXMLRegistrarLGACController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void notificarOperacionGuardar(String estado) {
        cargarInformacionTabla();
    }

    public void notificarOperacionActualizar(String estado) {
        cargarInformacionTabla();
    }
}
