/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
public class FXMLCuerpoAcademicoAgregarMiembroController implements Initializable {
    private CuerpoAcademico caSeleccionado;
    @FXML
    private Label lbNombreFormulario;
    @FXML
    private TableView<Academico> tvMiembrosCA;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colPaterno;
    @FXML
    private TableColumn colMaterno;
    @FXML
    private Label lbNombreCA;
    private ObservableList<Academico> academicosBD;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurartabla();
    }    

    @FXML
    private void clicAñadirMiembros(ActionEvent event) {
        if(tvMiembrosCA.getSelectionModel().getSelectedItem() == null){
            Utilidades.mostrarDialogoSimple("No se puede realizar la operación",
                    "Por favor selecciona un registro de la tabla para realizar el proceso",
                    Alert.AlertType.WARNING);
        }else{
            registrarAcademicoCA(tvMiembrosCA.getSelectionModel().getSelectedItem().
                    getIdAcademico());
        }
    }
    
    private void registrarAcademicoCA(int idAcademico){
        int respuesta = AcademicoDAO.agregarAcademicoCA(idAcademico,caSeleccionado.
                getIdCuerpoAcademico());
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","Por el momento no "
                        + "podemos establecer conexión con la base de datos, por favor inténtalo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case  Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta","Ocurrió un error "
                        + "durante la eliminación del académico del Cuerpo Académico, "
                        + "inténtelo de nuevo por favor", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada","El académico fue eliminado"
                        + "del Cuerpo Académico", Alert.AlertType.INFORMATION);
                cargarIformacion();
                break;              
        } 
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        Stage escenarioPrincipal = (Stage) lbNombreFormulario.getScene().getWindow();
        escenarioPrincipal.close();
    }
 
    
    private void configurartabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));       
        
    }
    
    private void cargarIformacion(){
        academicosBD = FXCollections.observableArrayList();
        AcademicoRespuesta academicos = AcademicoDAO.recuperarAcademicosSinCA();
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
                academicosBD.addAll(academicos.getAcademicos());
                tvMiembrosCA.setItems(academicosBD);
                break;
        }  
    }

    void enviarCASeleccionado(CuerpoAcademico caSeleccionado) {
        this.caSeleccionado = caSeleccionado;
        lbNombreCA.setText("Cuerpo Académico: "+ caSeleccionado.getNombre());
        
        cargarIformacion();
    }
            
}
