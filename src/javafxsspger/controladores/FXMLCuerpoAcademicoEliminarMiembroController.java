/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxsspger.modelo.dao.AcademicoDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jasie
 */
public class FXMLCuerpoAcademicoEliminarMiembroController implements Initializable {
    private CuerpoAcademico caSeleccionado;
    private ObservableList<Academico> miembrosCA;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    

    @FXML
    private void clicCerrarVentana(ActionEvent event) {
       Stage escenarioBase = (Stage) lbNombreFormulario.getScene().getWindow();
        escenarioBase.close();
    }
    @FXML
    private void clicEliminarMiembro(ActionEvent event) {
        if(tvMiembrosCA.getSelectionModel().getSelectedItem() == null){
            Utilidades.mostrarDialogoSimple("No se puede realizar la operación","Si el Cuerpo"
                    + " Académico aun tiene integrantes, por favor seleccione uno",
                    Alert.AlertType.WARNING);
        }else{
            eliminarAcademico(tvMiembrosCA.getSelectionModel().getSelectedItem().
                    getIdAcademico());
        }

    }

    @FXML
    private void clicEliminarTodos(ActionEvent event) {
        if(miembrosCA.isEmpty()){
            Utilidades.mostrarDialogoSimple("No se puede realizar la operación","El Cuerpo "
                    + "Académico ya no cuenta con miembros que se puedan remover",
                    Alert.AlertType.WARNING);
        }else{
            eliminarTodosAcademicos();
        }        
    }
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
    }    

    public void enviarMiembrosCA(CuerpoAcademico caSeleccionado, ObservableList<Academico> miembrosCA){
        this.caSeleccionado = caSeleccionado;
        this.miembrosCA = miembrosCA;
        lbNombreCA.setText("Cuerpo Académico: "+caSeleccionado.getNombre());
               
        for (Academico academico : this.miembrosCA) {
            if (academico.getIdAcademico() == this.caSeleccionado.getIdResponsable()) {
                this.miembrosCA.remove(academico);
            }
        }
        actualizarTabla();
    }
    
    private void actualizarTabla(){
        tvMiembrosCA.setItems(miembrosCA);
    }

    private void eliminarAcademico(int idAcademico){
        int respuesta = AcademicoDAO.removerAcademicoCA(idAcademico);
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
                eliminarAcademicoTabla(idAcademico);
                break;              
        }        
    }

    private void eliminarAcademicoTabla(int idAcademico){
         for (Academico academico : this.miembrosCA) {
            if (academico.getIdAcademico() == idAcademico) {
                this.miembrosCA.remove(academico);
                actualizarTabla();
            }
        }

    }
 
    private void eliminarTodosAcademicos(){
        int respuesta = AcademicoDAO.removerMuchosAcademicosDeCA(this.caSeleccionado.
                getIdCuerpoAcademico(),this.caSeleccionado.getIdResponsable());
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","Por el momento no "
                        + "podemos establecer conexión con la base de datos, por favor inténtalo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case  Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta","Ocurrió un error "
                        + "durante la eliminación de los académicos del Cuerpo Académico, "
                        + "inténtelo de nuevo por favor", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada","Los académicos fueron"
                        + "eliminados del Cuerpo Académico", Alert.AlertType.INFORMATION);
                eliminarTodosTabla();
                break;              
        }         
    }
    
    private void eliminarTodosTabla(){
        this.miembrosCA.clear();
        actualizarTabla();
    }
}
