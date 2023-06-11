/*
* Título del programa: Controlador de Actividad
* Autor: Jasiel Emir Zavaleta García
* Fecha: 10/06/2023
* Descripción: Clase controladora de vista FXMLRegistrarActividad.fxml
*/
package javafxsspger.controladores;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.modelo.dao.ActividadDAO;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;
import javax.imageio.ImageIO;


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
    
    private LocalDate fechaAnteriorInicio;
    private LocalDate fechaAnteriorFin;

    boolean cambioEnNombre = false;
    boolean cambioEnDescripcion = false;
    @FXML
    private Button btGuardar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estiloNormal = tfNombre.getStyle();
        dpFechaFin.setEditable(false);
        dpFechaInicio.setEditable(false);
        
        configurarDatePickerInicio();
        configurarDatePickerFin();
        
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
       FileChooser.ExtensionFilter filtroPDF = new FileChooser.ExtensionFilter
        ("Archivos PDF (*.pdf)", "*.pdf");
       FileChooser.ExtensionFilter filtroZIP = new FileChooser.ExtensionFilter
        ("Archivos ZIP (*.zip)", "*.zip");
       dialogoSeleccionArchivo.getExtensionFilters().addAll(filtroPDF, filtroZIP);

       Stage escenarioBase = (Stage) tfNombre.getScene().getWindow();
       archivoAtividad = dialogoSeleccionArchivo.showOpenDialog(escenarioBase);
       visualizarArchivo(archivoAtividad);   
        Utilidades.mostrarDialogoSimple(extensionArchivo, archivoAtividad.getAbsolutePath(),
                Alert.AlertType.INFORMATION);

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
        
        if(dpFechaInicio.getValue() != null){
           fechaInicio = dpFechaInicio.getValue().format(DateTimeFormatter.ISO_DATE);
        }else{
            dpFechaInicio.setStyle(estiloError);
            esValido = false;
        }
        
        
        if(dpFechaFin.getValue() != null){
            fechaFin = dpFechaFin.getValue().format(DateTimeFormatter.ISO_DATE);
        }else{
            dpFechaFin.setStyle(estiloError);
            esValido = false;
        }
        
        
        if(esValido){
            Actividad actividadValida = new Actividad();
            actividadValida.setNombre(nombre);
            actividadValida.setDescripcion(descripcion);
            actividadValida.setFechaInicio(fechaInicio);
            actividadValida.setFechaFin(fechaFin);
            
            //Asignar el id de Anteproyecto, de estudiante y de avance;
            
            try{
                if(esEdicion){
                    if(archivoAtividad != null){
                        actividadValida.setArchivo(Files.readAllBytes(archivoAtividad.toPath()));
                        actividadValida.setExtensionArchivo(extensionArchivo);
                    }else{
                        actividadValida.setIdActividad(actividadEdicion.getIdActividad());
                        actividadValida.setExtensionArchivo(actividadEdicion.getExtensionArchivo());
                    }
                    actividadValida.setIdActividad(actividadEdicion.getIdActividad());
                    modificarActividad(actividadValida);               
                }else{
                    if(archivoAtividad != null){
                        actividadValida.setArchivo(Files.readAllBytes(archivoAtividad.toPath()));
                        actividadValida.setExtensionArchivo(extensionArchivo);
                    }
                    LocalDate fechaActual = LocalDate.now();
                    String fechaHoy = fechaActual.format(DateTimeFormatter.ISO_DATE);
                    actividadValida.setFechaCreacion(fechaHoy);
                    registrarActividad(actividadValida);
                }
            }catch(IOException ex){
                Utilidades.mostrarDialogoSimple("Error con el archivo","Hubo un error al "
                        + "guardar el archivo seleccionado, por favor inténtelo de nuevo",
                        Alert.AlertType.ERROR);
            }         
        }else{
            Utilidades.mostrarDialogoSimple("Campos Vacíos",
                "Por favor ingrese información o valores en todas las casillas",
                Alert.AlertType.WARNING);
            
        }
    }
 
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) tfNombre.getScene().getWindow();
        escenarioBase.close();
    }

    private void registrarActividad(Actividad nuevaActividad){
        int codigoRespuesta = ActividadDAO.registrarActividad(nuevaActividad);
        switch (codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","Por el momento no "
                        + "podemos establecer conexión con la base de datos, por favor inténtalo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case  Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Erro de consulta","Ocurrió un error "
                        + "durante la consulta, inténtelo de nuevo por favor", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada","El LGAC que "
                        + "ingresaste ha sido registrado en el sistema", Alert.AlertType.INFORMATION);
                cerrarVentana();
                String exito = "Operacion exitosa";
                interfazNotificacion.notificarOperacionGuardar(exito);
                break;
                
        }
                
    }
    
    private void modificarActividad(Actividad nuevaActividad){
        int codigoRespuesta = ActividadDAO.modificarActividad(nuevaActividad);
        switch (codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","Por el momento no "
                        + "podemos establecer conexión con la base de datos, por favor inténtalo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case  Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Erro de consulta","Ocurrió un error "
                        + "durante la actualización, inténtelo de nuevo por favor", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada","El LGAC ha "
                        + "sido actualizado", Alert.AlertType.INFORMATION);
                cerrarVentana();
                String exito = "Operacion exitosa";
                interfazNotificacion.notificarOperacionActualizar(exito);
                break;
                
        }
                
    }
    
    public void inicializarInformacionFormulario(boolean esEdicion, Actividad actividad, 
            INotificacionOperacion interfazNotificacion){
        this.esEdicion = esEdicion;
        this.actividadEdicion = actividad;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            cargarInformacionActividad();
            
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == tfNombre) {
                    cambioEnNombre = true;
                } 
                if (cambioEnNombre) {
                    btGuardar.setDisable(false);  
                }
            }
        };
        tfNombre.setOnAction(eventHandler);
        
        taDescripcion.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                cambioEnDescripcion = true;
                
                if (cambioEnDescripcion || cambioEnNombre ) {
                    btGuardar.setDisable(false);
                }
            }
        });
        }
    }
    
    private void cargarInformacionActividad(){
        
        tfNombre.setText(actividadEdicion.getNombre());
        taDescripcion.setText(actividadEdicion.getDescripcion());
        dpFechaInicio.setValue(LocalDate.parse(actividadEdicion.getFechaInicio()));
        dpFechaFin.setValue(LocalDate.parse(actividadEdicion.getFechaFin()));
        
        
        fechaAnteriorInicio = dpFechaInicio.getValue();
        fechaAnteriorFin = dpFechaFin.getValue();
        
        /*try{
            ByteArrayInputStream inputFoto = new ByteArrayInputStream(promocion.getImagen());
            Image imgPromocion = new Image(inputFoto);
            imagenPromocion.setImage(imgPromocion);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        */        
    }

    private void configurarDatePickerInicio(){
        dpFechaInicio.setOnAction(event -> {
            LocalDate dateSeleccionada = dpFechaInicio.getValue();
            LocalDate dateActual = LocalDate.now();
            
            if(dateSeleccionada != null && dateSeleccionada.isBefore(dateActual)){
                Utilidades.mostrarDialogoSimple("Fecha no válida",
                        "Seleccione una fecha posterior a la actual"
                        , Alert.AlertType.WARNING);
                if(fechaAnteriorInicio == null){
                   dpFechaInicio.setValue(null);
                }else{
                    dpFechaInicio.setValue(fechaAnteriorInicio);
                }
            }
            
            if (dateSeleccionada != null && dateSeleccionada.getYear() > LocalDate.now().getYear()) {
                Utilidades.mostrarDialogoSimple("Año inválido", "Solo se aceptan "
                        + "promociones con el año en curso", Alert.AlertType.WARNING);
                if(fechaAnteriorInicio == null){
                    dpFechaInicio.setValue(null);
                }else{
                    dpFechaInicio.setValue(fechaAnteriorInicio);
                }                
            }
        });        
    }
    
    private void configurarDatePickerFin(){
        dpFechaFin.setOnAction(event -> {
            LocalDate dateSeleccionada = dpFechaFin.getValue();
            LocalDate dateInicio = dpFechaInicio.getValue();
            
            if(dateSeleccionada != null && dateSeleccionada.isBefore(dateInicio)){
                Utilidades.mostrarDialogoSimple("Fecha no válida",
                        "Seleccione una fecha posterior a la del inicio de la promoción"
                        , Alert.AlertType.WARNING);
                if(fechaAnteriorInicio == null){
                    dpFechaFin.setValue(null);
                }else{
                    dpFechaFin.setValue(fechaAnteriorFin);

                }
            }
            
            if(dateSeleccionada != null && dateSeleccionada.getMonth() != dateInicio.getMonth()){
                Utilidades.mostrarDialogoSimple("Fecha no válida",
                        "Seleccione una fecha posterior a la del inicio de la promoción"
                        , Alert.AlertType.WARNING);
                if(fechaAnteriorInicio == null){
                    dpFechaFin.setValue(null);
                }else{
                    dpFechaFin.setValue(fechaAnteriorFin);

                }                
            }
        });        
    }
    
}
