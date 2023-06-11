/*
* Título: Controlador del Formulario de registro de curso
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 10/06/2023
* Descripción: Clase Controladora de la vista del formulario de registro para los cursos
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafxsspger.modelo.dao.CursoDAO;
import javafxsspger.modelo.pojo.Curso;
import javafxsspger.modelo.dao.MateriaDAO;
import javafxsspger.modelo.dao.PeriodoEscolarDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Materia;
import javafxsspger.modelo.pojo.MateriaRespuesta;
import javafxsspger.modelo.pojo.PeriodoEscolar;
import javafxsspger.modelo.pojo.PeriodoEscolarRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

public class FXMLCursoFormularioController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfNRC;
    @FXML
    private ComboBox<PeriodoEscolar> cbPeriodoEscolar;
    @FXML
    private TextField tfNombreDelCurso;
    @FXML
    private ComboBox<Materia> cbEE;
    
    private static final int MAX_LENGTH = 5;
    private ObservableList<PeriodoEscolar> periodosEscolares;
    private ObservableList<Materia> experienciasEducativas;
    
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionPeriodoEscolar();
        cargarInformacionExperienciasEducativas();
        
        tfNRC.setOnKeyTyped(event -> {
            if(tfNRC.getText().length() >= MAX_LENGTH){
                event.consume();
            }
        });
        
        TextFormatter<Integer> textFormatter = new TextFormatter<>(
                new IntegerStringConverter(),
                null,
                change -> {
                    String newText = change.getControlNewText();
                    if(newText.matches("\\d*")){
                        return change;
                    }
                    return null;
                }
        );
        
        tfNRC.setTextFormatter(textFormatter);
        
        cbPeriodoEscolar.setOnAction(event -> actualizarNombreCurso());
        cbEE.setOnAction(event -> actualizarNombreCurso());
        tfNRC.textProperty().addListener((observable, oldValue, newValue) -> 
                actualizarNombreCurso());
    }    
    
    
    @FXML
    private void clicCerrarVentana(MouseEvent event){
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
        escenarioPrincipal.close();
    }

    private void cargarInformacionExperienciasEducativas() {
        experienciasEducativas = FXCollections.observableArrayList();
        MateriaRespuesta eesBD = MateriaDAO.obtenerMaterias();
        switch(eesBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                  Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos.", 
                            Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                  Utilidades.mostrarDialogoSimple("Error de consulta", 
                          "Por el momento no se puede obtener la información", 
                          Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                  experienciasEducativas.addAll(eesBD.getMaterias());
                  cbEE.setItems(experienciasEducativas);
                break;
        }
    }

    private void cargarInformacionPeriodoEscolar() {
        periodosEscolares = FXCollections.observableArrayList();
        PeriodoEscolarRespuesta periodosEscolaresBD = PeriodoEscolarDAO.obtenerPeriodosEscolares();
        switch(periodosEscolaresBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                  Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos.", 
                            Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                  Utilidades.mostrarDialogoSimple("Error de consulta", 
                          "Por el momento no se puede obtener la información", 
                          Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                  periodosEscolares.addAll(periodosEscolaresBD.getPeriodosEscolares());
                  cbPeriodoEscolar.setItems(periodosEscolares);
                break;
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        validarCamposRegistro();
    }

    private void validarCamposRegistro() {
        establecerEstiloNormal();
        boolean datosValidos = true;
        
        int posicionEE = cbEE.getSelectionModel().getSelectedIndex();
        int posicionPeriodoEscolar = cbPeriodoEscolar.getSelectionModel().getSelectedIndex();
        String nrc = tfNRC.getText();
        
        if(posicionEE == -1){
            cbEE.setStyle(estiloError);
            datosValidos = false;
        }
        if(posicionPeriodoEscolar == -1){
            cbPeriodoEscolar.setStyle(estiloError);
            datosValidos = false;
        }
        if(nrc.trim().isEmpty()){
            tfNRC.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(datosValidos){
            Curso cursoValidado = new Curso();
            cursoValidado.setIdMateria(cbEE.getValue().getIdMateria());
            cursoValidado.setIdPeriodoEscolar(cbPeriodoEscolar.getValue().getIdPeriodoEscolar());
            cursoValidado.setIdProfesor(Academico.getInstanciaSingleton().getIdAcademico());
            cursoValidado.setNRC(Integer.parseInt(nrc));
            
            registrarCurso(cursoValidado);
        }
    }

    private void establecerEstiloNormal() {
        tfNRC.setStyle(estiloNormal);
        cbEE.setStyle(estiloNormal);
        cbPeriodoEscolar.setStyle(estiloNormal);
    }

    private void registrarCurso(Curso cursoValidado) {
        int codigoRespuesta = CursoDAO.guardarCursoEE(cursoValidado);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", 
                        "El Curso no pudo ser guardado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información", 
                        "La información del Curso no puede ser guardada, por favor verifique su "
                                + "información e intente más tarde.", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Curso registrado", 
                        "La información del Curso fue guardada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                break;
        }
    }
    
    private void actualizarNombreCurso() {
        PeriodoEscolar periodoEscolar = cbPeriodoEscolar.getValue();
        Materia materia = cbEE.getValue();
        String nrc = tfNRC.getText();

        String nombreCurso = "";
        if (materia != null || periodoEscolar != null || !nrc.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();

            if (materia != null) {
                stringBuilder.append(materia.getNombre());
            }

            if (periodoEscolar != null) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(periodoEscolar.getNombre());
            }

            if (!nrc.isEmpty()) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append("(").append(nrc).append(")");
            }

            nombreCurso = stringBuilder.toString();
        }

        tfNombreDelCurso.setText(nombreCurso);
    }

}
