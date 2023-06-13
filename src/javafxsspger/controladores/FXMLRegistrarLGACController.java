/*
* Título del programa: Controlador de Lgac
* Autor: Jasiel Emir Zavaleta García
* Fecha: 08/06/2023
* Descripción: Clase controladora de vista FXMLRegistrarLgac.fxml
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.modelo.dao.LgacDAO;
import javafxsspger.modelo.pojo.Lgac;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLRegistrarLGACController implements Initializable {

    private INotificacionOperacion interfazNotificacion;
    private boolean esEdicion;
    private Lgac lgacEdicion;
    
    @FXML
    private Label lbNombreFormulario;
    @FXML
    private TextField tfNombreLgac;
    @FXML
    private TextArea taDescripcion;
    
    private String estiloError = 
            "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    private String estiloNormal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estiloNormal = tfNombreLgac.getStyle();
    }    

    @FXML
    private void clicGuardar(ActionEvent event) {
        validarInformacion();
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        Stage escenarioBase = (Stage) tfNombreLgac.getScene().getWindow();
        escenarioBase.close();
    }
    
    private void validarInformacion(){
        tfNombreLgac.setStyle(estiloNormal);
        taDescripcion.setStyle(estiloNormal);
        
        String nombre = null;
        String descripcion = null;
        
        boolean esValido = true;
        if(! tfNombreLgac.getText().isEmpty()){
            nombre = tfNombreLgac.getText();            
        }else{
            esValido = false;
            tfNombreLgac.setStyle(estiloError);
        }
        
        if(! taDescripcion.getText().isEmpty()){
            descripcion = taDescripcion.getText();
        }else{
            esValido = false;
            taDescripcion.setStyle(estiloError);
        }
        
        if(esValido){
            Lgac nuevoLgac = new Lgac();
            nuevoLgac.setNombre(nombre);
            nuevoLgac.setDescripcion(descripcion);

            if(esEdicion){
                nuevoLgac.setIdLgac(lgacEdicion.getIdLgac());
                modificarLgac(nuevoLgac);               
            }else{
                registrarLgac(nuevoLgac);
            }
            
            
        }else if(esEdicion){
            Utilidades.mostrarDialogoSimple("Guardar con información anterior","No ha "
                    + "ingresado información en todas las secciones. La información se completará "
                    + "con los últimos datos almacenados",Alert.AlertType.INFORMATION);
            cerrarVentana();
        }
        else{
            Utilidades.mostrarDialogoSimple("Campos Vacíos",
                "Por favor ingrese información o valores en todas las casillas",
                Alert.AlertType.WARNING);
            
        }
    }
    
    private void registrarLgac(Lgac nuevoLgac){
        int codigoRespuesta = LgacDAO.RegistarLgac(nuevoLgac);
        switch (codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","Por el momento no "
                        + "podemos establecer conexión con la base de datos, por favor inténtalo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case  Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Erro de consulta","Ocurrió un error "
                        + "durante la consulta, inténtelo de nuevo por favor", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada","El Lgac que "
                        + "ingresaste ha sido registrado en el sistema", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                String exito = "Operacion exitosa";
                interfazNotificacion.notificarOperacionGuardar(exito);
                break;
                
        }
                
    }
    
    private void modificarLgac(Lgac edicionLgac){
        boolean tieneCuerpoAcademico = false;
        int codigoRespuesta = LgacDAO.modificarLgac(edicionLgac, tieneCuerpoAcademico);
        switch (codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","Por el momento no "
                        + "podemos establecer conexión con la base de datos, por favor inténtalo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case  Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Erro de consulta","Ocurrió un error "
                        + "durante la actualización, inténtelo de nuevo por favor", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada","El Lgac ha "
                        + "sido actualizado", Alert.AlertType.INFORMATION);
                cerrarVentana();
                String exito = "Operacion exitosa";
                interfazNotificacion.notificarOperacionActualizar(exito);
                break;
                
        }
                
    }    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) tfNombreLgac.getScene().getWindow();
        escenarioBase.close();
       
    }

    public void inicializarInformacionFormulario(boolean esEdicion, Lgac lgacEdicion,
            INotificacionOperacion interfazNotificacion){
        this.esEdicion = esEdicion;
        this.lgacEdicion = lgacEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            cargarInformacionLgac();
            lbNombreFormulario.setText("Modificación de Lgac");
        }
    }
    
    private void cargarInformacionLgac(){
        tfNombreLgac.setText(lgacEdicion.getNombre());
        taDescripcion.setText(lgacEdicion.getDescripcion());        
    }
}
