/*
* Título del programa: Controlador para añadir miembros al Cuerpo Académico
* Autor: Jasiel Emir Zavaleta García
* Fecha: 11/06/2023
* Descripción: Clase controladora de vista FXMLCuerpoAcademicoAgregarMiembro.fxml
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
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurartabla();
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
                Utilidades.mostrarDialogoSimple("Error de conexión","No fue posible  "
                        + "recuperar a los académicos del sistema, por favor inténtelo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:   
                Utilidades.mostrarDialogoSimple("Error de consulta","Ocurrió "
                        + "un error al recuperar la información de los académicos, "
                        + "inténtelo de nuevo, por favor", Alert.AlertType.WARNING);    
                break;
            case Constantes.OPERACION_EXITOSA:
                academicosBD.addAll(academicos.getAcademicos());
                tvMiembrosCA.setItems(academicosBD);
                break;
        }  
    }    

    @FXML
    private void clicAñadirMiembros(ActionEvent event) {
        if(tvMiembrosCA.getSelectionModel().getSelectedItem() == null){
            Utilidades.mostrarDialogoSimple("No se puede realizar la operación",
                    "Por favor selecciona un académico para añadir al Cuerpo Académico",
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
                Utilidades.mostrarDialogoSimple("Error de conexión","No fue posible agregar  "
                        + "al académico al Cuerpo Académico, por favor inténtalo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case  Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta","Ocurrió un error "
                        + "al añadir al académico al Cuerpo Académico, "
                        + "inténtelo de nuevo por favor", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada","El académico fue "
                        + "añadido al Cuerpo Académico", Alert.AlertType.INFORMATION);
                cargarIformacion();
                break;              
        } 
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        Stage escenarioPrincipal = (Stage) lbNombreFormulario.getScene().getWindow();
        escenarioPrincipal.close();
    }
 
    void enviarCASeleccionado(CuerpoAcademico caSeleccionado) {
        this.caSeleccionado = caSeleccionado;
        lbNombreCA.setText("Cuerpo Académico: "+ caSeleccionado.getNombre());
        
        cargarIformacion();
    }
            
}
