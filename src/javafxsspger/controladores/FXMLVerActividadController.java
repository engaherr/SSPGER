/*
* Título del programa: Controlador de Ver Actividad
* Autor: Omar Dylan Segura Platas
* Fecha: 09/06/2023
* Descripción: Clase controladora de vista FXMLVerActividadController
*/


package javafxsspger.controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.ActividadDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.ActividadRespuesta;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author dplat
 */
public class FXMLVerActividadController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbFechaComienzo;
    @FXML
    private Label lbFechaEntrega;
    @FXML
    private TextArea taCuerpo;
    @FXML
    private Label lbfechaCreacion;
    private int idActividadSeleccionada;
    private Actividad actividadSeleccionada;
    @FXML
    private ImageView btnDescargar;
    @FXML
    private Button btnCalificar;
    @FXML
    private Label lbEntregado;
    @FXML
    private Label lbnombreDelArchivo;
    @FXML
    private Button btnEnviar;
    @FXML
    private ImageView btnAdjuntar;
    
    File archivoElegido;

    
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       taCuerpo.setWrapText(true);
       mostrarElementosSegunRol();
    }    

    public void mostrarElementosSegunRol() {
    Estudiante estudiante = Estudiante.getInstanciaSingleton();
    Academico academico = Academico.getInstanciaSingleton();

    if (estudiante != null) {
        btnCalificar.setVisible(false);
        btnDescargar.setVisible(false);
    } else if (academico != null) {
        taCuerpo.setEditable(false);
        btnEnviar.setVisible(false);
        btnAdjuntar.setVisible(false);
    }
}
    
    
    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lbDescripcion.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicCalificar(ActionEvent event) throws IOException {
           FXMLLoader accesoControlador = new FXMLLoader(
                    JavaFXSSPGER.class.getResource("vistas/FXMLEvaluarActividad.fxml"));
            Parent vista = accesoControlador.load();
             FXMLEvaluarActividadController evaluarActividadController = accesoControlador.getController();
             evaluarActividadController.setActividadEvaluar(idActividadSeleccionada);
            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.showAndWait();
    }
    
    
    public void setActividadSeleccionada(Actividad actividad) {
    lbTitulo.setText(actividad.getNombre());
    lbDescripcion.setText(actividad.getDescripcion());
    lbFechaComienzo.setText(actividad.getFechaInicio());
    lbFechaEntrega.setText(actividad.getFechaFin());
     cargarInformacionEntrega(actividad.getIdActividad());
     idActividadSeleccionada = actividad.getIdActividad();
     actividadSeleccionada = actividad;
}
    
    
     public void cargarInformacionEntrega(int idActividad) {
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerDetallesEntrega(idActividad);
        ArrayList<Actividad> actividades = respuestaBD.getActividades();
        if (!actividades.isEmpty()) {
            Actividad actividad = actividades.get(0);
            taCuerpo.setText(actividad.getComentarios());
            lbfechaCreacion.setText(actividad.getFechaCreacion());
            lbnombreDelArchivo.setText(actividad.getNombreArchivo());
            if(actividad.getNombreArchivo() == null){
                btnDescargar.setVisible(false);
            }
             btnDescargar.setOnMouseClicked(event -> {
            String directorio = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
            String directorioFinal = directorio + "/" + actividad.getNombreArchivo();
            File archivo = new File(directorioFinal);
            try {
                OutputStream output = new FileOutputStream(archivo);
                output.write(actividad.getArchivo());
                output.close();
                Utilidades.mostrarDialogoSimple("Documento descargado",
                        "Se ha descargado el documento en la dirección: " + directorio, Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                Utilidades.mostrarDialogoSimple("No hay archivo", "No hay ningún archivo para descargar.", 
                        Alert.AlertType.WARNING);
            }
               });
            
        }       
}

    @FXML
private void clicEnviar(ActionEvent event) throws IOException {
    try {
        Actividad actividadEntrega = crearActividadDesdeFormulario();
        int resultado = ActividadDAO.enviarEntrega(actividadEntrega);
        if (resultado == Constantes.OPERACION_EXITOSA) {
            Utilidades.mostrarDialogoSimple("Entrega enviada", "La entrega se ha enviado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarDialogoSimple("Error", "No se pudo enviar la entrega.", Alert.AlertType.ERROR);
        }
    } catch (Exception e) {
        e.printStackTrace();
        // Mostrar el mensaje de error o manejar la excepción de alguna otra manera
    }
}




    @FXML
    private void clicAdjuntarArchivo(MouseEvent event) {
            FileChooser dialogoSeleccionImg = new FileChooser();
            dialogoSeleccionImg.setTitle("Selecciona un documento");
            Stage escenarioBase = (Stage)lbDescripcion.getScene().getWindow();
            archivoElegido = dialogoSeleccionImg.showOpenDialog(escenarioBase);
                    if(archivoElegido != null){
                    lbnombreDelArchivo.setText(archivoElegido.getName());
            }
       
        }

    private Actividad crearActividadDesdeFormulario() throws IOException {
    Actividad actividadEntrega = new Actividad();
    actividadEntrega.setIdActividad(idActividadSeleccionada);
    actividadEntrega.setFechaCreacion(Utilidades.obtenerFechaActual());
    actividadEntrega.setArchivo(Files.readAllBytes(archivoElegido.toPath()));
    actividadEntrega.setComentarios(taCuerpo.getText());
    actividadEntrega.setNombreArchivo(lbnombreDelArchivo.getText());
    return actividadEntrega;
}

    


    }
     
   



