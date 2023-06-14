/*
* Título del programa: Controlador de Actividad
* Autor: Jasiel Emir Zavaleta García
* Fecha: 10/06/2023
* Descripción: Clase controladora de vista FXMLRegistrarActividad.fxml
*/
package javafxsspger.controladores;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.modelo.dao.ActividadDAO;
import javafxsspger.modelo.dao.AnteproyectoEstudianteDAO;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.AnteproyectoEstudiante;
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


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
    
    private String estiloError = 
            "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    private String estiloNormal;
    
    private File archivoAtividad;
    private String extensionArchivo;
    private String nombreArchivo;
    
    private LocalDate fechaAnteriorInicio;
    private LocalDate fechaAnteriorFin;

    boolean cambiosRealizados = false;
    @FXML
    private Button btGuardar;
    
    
    private Estudiante estudiante;
    private AnteproyectoEstudiante atpAsignado;
    private ObservableList<Avance> avances;
    
    @FXML
    private Label lbRutaArchivo;
    private String HORA_INICIO = "00:00:00";
    private String HORA_TERMINO = "23:45:00";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estiloNormal = tfNombre.getStyle();
        dpFechaFin.setEditable(false);
        dpFechaInicio.setEditable(false);
        
        configurarDatePickerInicio();
        configurarDatePickerFin();
        
        estudiante = Estudiante.getInstanciaSingleton();
        atpAsignado = AnteproyectoEstudianteDAO.obtenerAnteproyectoEstudiante
            (estudiante.getIdEstudiante());
    }    

    @FXML
    private void clicGuardar(ActionEvent event) {
        validarInformacion();
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void clicSeleccionarArchivo(ActionEvent event) {
       FileChooser dialogoSeleccionArchivo = new FileChooser();
       dialogoSeleccionArchivo.setTitle("Selecciona un archivo");
       FileChooser.ExtensionFilter filtroPDF = new FileChooser.ExtensionFilter
            ("Archivos PDF (*.pdf)", "*.pdf");
        FileChooser.ExtensionFilter filtroWord = new FileChooser.ExtensionFilter
            ("Archivos Word (*.docx)", "*.docx");
        dialogoSeleccionArchivo.getExtensionFilters().addAll(filtroPDF, filtroWord);

       Stage escenarioBase = (Stage) tfNombre.getScene().getWindow();
       archivoAtividad = dialogoSeleccionArchivo.showOpenDialog(escenarioBase);
       visualizarArchivo(archivoAtividad);   
        Utilidades.mostrarDialogoSimple(extensionArchivo, archivoAtividad.
                getAbsolutePath(),
                Alert.AlertType.INFORMATION);

    }

    
    private void visualizarArchivo(File archivoSeleccionado) {
        if (archivoSeleccionado != null) {
            nombreArchivo = archivoSeleccionado.getName();
            lbRutaArchivo.setText(nombreArchivo);

            String extension = getFileExtension(archivoSeleccionado);
            extensionArchivo = extension;
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
        int idAvance = -1;
        boolean esValido = true;
        
        String fechaHoraInicio = null;
        String fechaHoraTermino = null;
 
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
           fechaHoraInicio = fechaInicio+" "+HORA_TERMINO;
           
        }else{
            dpFechaInicio.setStyle(estiloError);
            esValido = false;
        }
        
        
        if(dpFechaFin.getValue() != null){
            fechaFin = dpFechaFin.getValue().format(DateTimeFormatter.ISO_DATE);
            fechaHoraTermino = fechaFin+" "+HORA_TERMINO;
        }else{
            dpFechaFin.setStyle(estiloError);
            esValido = false;
        }
                    
        if(esValido){
            Actividad actividadValida = new Actividad();
            actividadValida.setNombre(nombre);
            actividadValida.setDescripcion(descripcion);
            actividadValida.setFechaFin(fechaHoraTermino);
            actividadValida.setFechaInicio(fechaHoraInicio);

         
            
            try{
                if(esEdicion){
                    actividadValida.setIdEstudiante(actividadEdicion.getIdEstudiante());
                    actividadValida.setIdAnteproyecto(actividadEdicion.
                            getIdAnteproyecto());
                    if(archivoAtividad != null){
                        actividadValida.setArchivo(Files.readAllBytes
                            (archivoAtividad.toPath()));
                        actividadValida.setExtensionArchivo(extensionArchivo);
                        actividadValida.setNombreArchivo(nombreArchivo);
                    }else{
                        actividadValida.setIdActividad(actividadEdicion.getIdActividad());
                        actividadValida.setExtensionArchivo(actividadEdicion.
                                getExtensionArchivo());
                        actividadValida.setNombreArchivo(actividadEdicion.
                                getNombreArchivo());
                    }
                    actividadValida.setIdActividad(actividadEdicion.getIdActividad());
                    modificarActividad(actividadValida);               
                }else{
                    if(archivoAtividad != null){
                        actividadValida.setArchivo(Files.readAllBytes
                                (archivoAtividad.toPath()));
                        actividadValida.setExtensionArchivo(extensionArchivo);
                        actividadValida.setNombreArchivo(nombreArchivo);
                    }

                    actividadValida.setIdEstudiante(estudiante.getIdEstudiante());
                    actividadValida.setIdAnteproyecto(atpAsignado.getIdAnteproyecto());
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

    private void registrarActividad(Actividad actividadValida){
        int codigoRespuesta = ActividadDAO.registrarActividad(actividadValida);
        switch (codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","No fue posible registrar "
                        + "la actividad, por favor inténtelo más tarde "
                        , Alert.AlertType.ERROR);
                break;
            case  Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta","Ocurrió un error "
                        + "al registrar la actividad, inténtelo de nuevo por favor",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada","La actividad fue "
                        + "registrada",
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                String exito = "Operacion exitosa";
                interfazNotificacion.notificarOperacionGuardar(exito);
                break;
                
        }
                
    }
    
    private void modificarActividad(Actividad actividadValida){
        int codigoRespuesta = ActividadDAO.modificarActividad(actividadValida);
        switch (codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","No fue posible modificar "
                        + "la actividad, por favor inténtelo más tarde "
                        , Alert.AlertType.ERROR);
                break;
            case  Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Erro de consulta","Ocurrió un error "
                        + "al modificar la actividad, inténtelo de nuevo por favor",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada","La actividad "
                        + "ha sido actualizada", Alert.AlertType.INFORMATION);
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
                    cambiosRealizados = true;
                }else if(event.getSource() == dpFechaFin){
                    cambiosRealizados = true;
                }else if(event.getSource() == dpFechaInicio){
                    cambiosRealizados = true;
                } 
                
                if (cambiosRealizados) {
                    btGuardar.setDisable(false);  
                }
            }
        };
        tfNombre.setOnAction(eventHandler);
        dpFechaInicio.setOnAction(eventHandler);
        dpFechaFin.setOnAction(eventHandler);
        
        
        taDescripcion.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                cambiosRealizados = true;
                
                if (cambiosRealizados) {
                    btGuardar.setDisable(false);
                }
            }
        });
        }
    }
    
    private void cargarInformacionActividad(){
        
        tfNombre.setText(actividadEdicion.getNombre());
        taDescripcion.setText(actividadEdicion.getDescripcion());
        
        String fechaHoraInicio = actividadEdicion.getFechaInicio();
        LocalDate fechaI = LocalDate.parse(fechaHoraInicio, DateTimeFormatter.ofPattern
        ("dd/MM/yyyy")); 
        dpFechaInicio.setValue(fechaI);
        
        String fechaHoraFin = actividadEdicion.getFechaFin();
        LocalDate fechaF = LocalDate.parse(fechaHoraFin, DateTimeFormatter.ofPattern
        ("dd/MM/yyyy")); 
        dpFechaFin.setValue(fechaF);
        
        if(actividadEdicion.getNombreArchivo() != null){
            lbRutaArchivo.setText(actividadEdicion.getNombreArchivo()+"."+actividadEdicion.
                getExtensionArchivo());
        }

       
    }
    
    private int obtenerPosicionComboAvance(int idAvance){
        for (int i = 0; i < avances.size(); i++) {
            if(avances.get(i).getIdAvance() == idAvance)
                return i;
        }
        return 0;
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
            
            if (dateSeleccionada != null && dateSeleccionada.getYear() > LocalDate.now().getYear()){
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
