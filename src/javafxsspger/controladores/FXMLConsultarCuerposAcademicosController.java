/*
* Título del programa: Controlador de listado de Cuerpos Académicos
* Autor: Jasiel Emir Zavaleta García
* Fecha: 08/06/2023
* Descripción: Clase controladora de vista FXMLConsultarCuerposAcademicos.fxml
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.modelo.dao.CuerpoAcademicoDAO;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jasie
 */
public class FXMLConsultarCuerposAcademicosController implements Initializable,INotificacionOperacion {

    @FXML
    private Label lbNombreFormulario;
    @FXML
    private TableView<CuerpoAcademico> tvCuerposAcademicos;
    
    @FXML
    private TableColumn colClave;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colGrado;
    @FXML
    private TableColumn colDes;
    
    private ObservableList<CuerpoAcademico> cuerposAcademicos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }

    private void configurarTabla(){
        colClave.setCellValueFactory(new PropertyValueFactory("clave"));        
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colGrado.setCellValueFactory(new PropertyValueFactory("gradoConsolidacion"));
        colDes.setCellValueFactory(new PropertyValueFactory("dependencia"));
  
}  
    @FXML
    private void clicRegistrar(ActionEvent event) {
        Stage escenarioFormulario = new Stage();
        escenarioFormulario.setScene(Utilidades.inicializaEscena("vistas/FXMLRegistrarCA.fxml"));
        escenarioFormulario.setTitle("Formulario");
        escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
        escenarioFormulario.showAndWait();          
    }



    
    private void cargarInformacionTabla(){
        cuerposAcademicos = FXCollections.observableArrayList();
        CuerpoAcademicoRespuesta caRespuesta = CuerpoAcademicoDAO.obtenerCuerposAcademicos();
        switch(caRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","No se pudo "
                        + "recuperar la información relacionada con las Lgac, por favor inténtelo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:   
                Utilidades.mostrarDialogoSimple("Error de consulta","Ocurrió "
                        + "un error al intentar recuperar la información "
                        + "inténtelo de nuevo, por favor", Alert.AlertType.WARNING);    
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operacion exitosa","Las Lgac registradas en el"
                    + " sistema se cargaron de forma correcta", Alert.AlertType.INFORMATION);
                cuerposAcademicos.addAll(caRespuesta.getCuerposAcademicos());
                tvCuerposAcademicos.setItems(cuerposAcademicos);
                break;
        }
        
    }

    @Override
    public void notificarOperacionGuardar(String estado) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void notificarOperacionActualizar(String estado) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        CuerpoAcademico caSeleccionado = tvCuerposAcademicos.getSelectionModel().getSelectedItem();
        if(caSeleccionado != null){
            mostrarOpcionesModificacion(caSeleccionado);
    
        }else{
            Utilidades.mostrarDialogoSimple("Atención","Por favor selecciona "
                    + "una promoción para poder editar", Alert.AlertType.WARNING);
        }         
    }

    private void mostrarOpcionesModificacion(CuerpoAcademico cuerpoAcademico) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Opciones de Modificación");
        alert.setHeaderText("Seleccione una opción para modificar el CA seleccionado.");
        alert.setContentText("Elige una opción:");

        ButtonType modificarButton = new ButtonType("Modificar");
        ButtonType eliminarButton = new ButtonType("Eliminar");
        ButtonType miembrosButton  = new ButtonType("Administrar miembros");
        ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(modificarButton, eliminarButton,
                miembrosButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType == modificarButton) {
                mostrarVentanaModificacion(cuerpoAcademico);
            } else if (buttonType == eliminarButton) {
                eliminarCuerpoAcademico(cuerpoAcademico);
            }else if(buttonType == miembrosButton){
                mostrarVentanaMiembros(cuerpoAcademico);
            }
        });
    }

    private void mostrarVentanaMiembros(CuerpoAcademico cuerpoAcademico){
        irVentanaMiembros(cuerpoAcademico);
        
    }

    private void mostrarVentanaModificacion(CuerpoAcademico cuerpoAcademico) {
        irFormulario(true, cuerpoAcademico);
    }

    private void eliminarCuerpoAcademico(CuerpoAcademico cuerpoAcademico) {
        // Lógica para eliminar el cuerpoAcademico seleccionado
        // ...
    }


    
    private void irFormulario(boolean esEdicion, CuerpoAcademico caSeleccionado){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                (JavaFXSSPGER.class.getResource("vistas/FXMLRegistrarCA.fxml"));
            Parent vista;
            vista = accesoControlador.load();
            
            FXMLRegistrarCAController formulario = accesoControlador.getController();
            formulario.inicializarInformacionFormulario(esEdicion,caSeleccionado,
                    this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Modificación de Cuerpo Académico");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();         
            
        }catch(IOException ex){
            Logger.getLogger(FXMLRegistrarLGACController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }    
    
    private void irVentanaMiembros(CuerpoAcademico caSeleccionado){
         try{
            FXMLLoader accesoControlador = new FXMLLoader
                (JavaFXSSPGER.class.getResource("vistas/FXMLCuerpoAcademicoConsultarMiembros"
                        + ".fxml"));
            Parent vista;
            vista = accesoControlador.load();
            
            FXMLCuerpoAcademicoConsultarMiembrosController formulario = 
                    accesoControlador.getController();
            formulario.enviarInformacionCA(caSeleccionado);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Miembros de Cuerpo Académico");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();         
            
        }catch(IOException ex){
            Logger.getLogger(FXMLRegistrarLGACController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }       
        
    }
    
    
    
}
