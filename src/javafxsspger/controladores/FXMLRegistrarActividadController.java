/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.utils.Utilidades;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author jasie
 */
public class FXMLRegistrarActividadController implements Initializable {

    private INotificacionOperacion interfazNotificacion;
    private boolean esEdicion;
    private Actividad actividadEdicion;
    
    @FXML
    private TextField tfNombre;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TextArea taDescripcion;
    
    private String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    private String estiloNormal;
    @FXML
    private ImageView ivArchivo;
    
    private File archivoAtividad;
    private String extensionArchivo;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estiloNormal = tfNombre.getStyle();
        dpFechaFin.setEditable(false);
        dpFechaInicio.setEditable(false);
    }    

    @FXML
    private void clicGuardar(ActionEvent event) {
        validarInformacion();
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicSeleccionarArchivo(ActionEvent event) {
       FileChooser dialogoSeleccionArchivo = new FileChooser();
       dialogoSeleccionArchivo.setTitle("Selecciona un archivo");
       FileChooser.ExtensionFilter filtroPDF = new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
       FileChooser.ExtensionFilter filtroZIP = new FileChooser.ExtensionFilter("Archivos ZIP (*.zip)", "*.zip");
       dialogoSeleccionArchivo.getExtensionFilters().addAll(filtroPDF, filtroZIP);

       Stage escenarioBase = (Stage) tfNombre.getScene().getWindow();
       archivoAtividad = dialogoSeleccionArchivo.showOpenDialog(escenarioBase);
       visualizarArchivo(archivoAtividad);   
        Utilidades.mostrarDialogoSimple(extensionArchivo, archivoAtividad.getAbsolutePath(), Alert.AlertType.INFORMATION);

    }
    
private void visualizarArchivo(File archivoSeleccionado) {
    if (archivoSeleccionado != null) {
        String extension = getFileExtension(archivoSeleccionado);
        extensionArchivo = extension;
        if (extension.equalsIgnoreCase("pdf")) {
            
        } else if (extension.equalsIgnoreCase("zip")) {
        
        }
        else {
            System.out.println("Formato de archivo no válido.");
        }
    }
}



private String getFileExtension(File archivo) {
    String nombreArchivo = archivo.getName();
    int lastDotIndex = nombreArchivo.lastIndexOf(".");
    if (lastDotIndex > 0 && lastDotIndex < nombreArchivo.length() - 1) {
        return nombreArchivo.substring(lastDotIndex + 1).toLowerCase();
    }
    return "";
}

 
    private void validarInformacion(){
        tfNombre.setStyle(estiloNormal);
        taDescripcion.setStyle(estiloNormal);
        dpFechaInicio.setStyle(estiloNormal);
        dpFechaFin.setStyle(estiloNormal);
               
        String nombre = null;
        String descripcion = null;
        String fechaInicio = null;
        String fechaFin = null;
        boolean esValido = true;
 
        if(! tfNombre.getText().isEmpty()){
            nombre = tfNombre.getText();            
        }else{
            esValido = false;
            tfNombre.setStyle(estiloError);
        }
        
        if(! taDescripcion.getText().isEmpty()){
            descripcion = taDescripcion.getText();
        }else{
            esValido = false;
            taDescripcion.setStyle(estiloError);
        }
        
        
        
        if(esValido){
            Actividad nuevaActividad = new Actividad();
            nuevaActividad.setNombre(nombre);
            nuevaActividad.setDescripcion(descripcion);

            if(esEdicion){
                nuevaActividad.setIdActividad(actividadEdicion.getIdActividad());
                modificarActividad(nuevaActividad);               
            }else{
                registrarActividad(nuevaActividad);
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
 
    private void registrarActividad(Actividad nuevaActividad){
        validarInformacion();
        
    }
    private void modificarActividad(Actividad nuevaActividad){
        validarInformacion();
        
    }
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) tfNombre.getScene().getWindow();
        escenarioBase.close();
    }    
}
