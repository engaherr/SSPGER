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
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.AcademicoDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.AcademicoRespuesta;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jasie
 */
public class FXMLCuerpoAcademicoConsultarMiembrosController implements Initializable {

    @FXML
    private Label lbNombreFormulario;
    @FXML
    private TableView<Academico> tvMiembrosCA;
    @FXML
    private TableColumn<Academico,String> colNombre;
    @FXML
    private TableColumn<Academico, String> colPaterno;
    @FXML
    private TableColumn<Academico,String> colMaterno;
    
    private ObservableList<Academico> miembrosCA;

    private CuerpoAcademico caSeleccionado;
    @FXML
    private Label lbNombreCA;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();

        
    }    

    @FXML
    private void clicCerrarVentana(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicEliminarMiembros(ActionEvent event) {
                  try{
            FXMLLoader accesoControlador = new FXMLLoader
                (JavaFXSSPGER.class.getResource("vistas/FXMLCuerpoAcademicoEliminarMiembro"
                        + ".fxml"));
            Parent vista;
            vista = accesoControlador.load();
            
            FXMLCuerpoAcademicoEliminarMiembroController formulario = 
                    accesoControlador.getController();
            formulario.enviarMiembrosCA(caSeleccionado,miembrosCA);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Eliminar integrantes de Cuerpo Académico");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();         
            
        }catch(IOException ex){
            Logger.getLogger(FXMLRegistrarLGACController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }    
    }

    @FXML
    private void clicAñadirMiembros(ActionEvent event) {
          try{
            FXMLLoader accesoControlador = new FXMLLoader
                (JavaFXSSPGER.class.getResource("vistas/FXMLCuerpoAcademicoAgregarMiembro"
                        + ".fxml"));
            Parent vista;
            vista = accesoControlador.load();
            
            FXMLCuerpoAcademicoAgregarMiembroController formulario = 
                    accesoControlador.getController();
            formulario.enviarCASeleccionado(caSeleccionado);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Agregar miembros a Cuerpo Académico");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();         
            
        }catch(IOException ex){
            Logger.getLogger(FXMLRegistrarLGACController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }       
    }
    
    private void cerrarVentana(){
        Stage escenarioPrincipal = (Stage) lbNombreFormulario.getScene().getWindow();
        escenarioPrincipal.close();      
    }

    public void enviarInformacionCA(CuerpoAcademico caSeleccionado) {
        this.caSeleccionado = caSeleccionado;
        cargarInformacionMiembros();
        
        lbNombreCA.setText("Cuerpo Académico: "+caSeleccionado.getNombre());
    }
    
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
    }
    
    private void cargarInformacionMiembros(){
         miembrosCA = FXCollections.observableArrayList();
        AcademicoRespuesta academicos = AcademicoDAO.recuperarAcademicosEnCA
                (caSeleccionado.getIdCuerpoAcademico());
        switch(academicos.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","No se pudo "
                        + "recuperar la información, por favor inténtelo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:   
                Utilidades.mostrarDialogoSimple("Error de consulta","Ocurrió "
                        + "un error al intentar recuperar la información de los académicos, "
                        + "inténtelo de nuevo, por favor", Alert.AlertType.WARNING);    
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Exito","Se cargaron los miembros",
                        Alert.AlertType.INFORMATION);
                miembrosCA.addAll(academicos.getAcademicos());
                tvMiembrosCA.setItems(miembrosCA);
                break;       
        }
    }
}
