package javafxsspger.controladores;

import com.mysql.jdbc.Util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.dao.CuerpoAcademicoDAO;
import javafxsspger.modelo.dao.LGACDAO;
import javafxsspger.modelo.dao.ModalidadDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.LGACRespuesta;
import javafxsspger.modelo.pojo.Modalidad;
import javafxsspger.modelo.pojo.ModalidadRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author kikga
 */
public class FXMLAnteproyectoFormularioController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextArea taProyectoInvestigacion;
    @FXML
    private ComboBox<Integer> cbAlumnosMax;
    @FXML
    private ComboBox<LGAC> cbLgac;
    @FXML
    private TextArea taLineaInvestigacion;
    @FXML
    private ComboBox<Modalidad> cbModalidad;
    @FXML
    private TextArea taNombreTrabajo;
    @FXML
    private TextArea taRequisitos;
    @FXML
    private TextArea taCodirectores;
    @FXML
    private TextArea taDescripcionProyecto;
    @FXML
    private TextArea taDescripcionTrabajo;
    @FXML
    private TextArea taResultadosEsperados;
    @FXML
    private TextArea taBibliografiaRecomendada;
    @FXML
    private TextArea taComentarios;
    @FXML
    private ComboBox<CuerpoAcademico> cbCuerpoAcademico;
    
    private ObservableList<CuerpoAcademico> cuerposAcademicos;
    private ObservableList<LGAC> lgacs;
    private ObservableList<Modalidad> modalidades;
    private Anteproyecto anteproyectoEdicion;
    private boolean esEdicion;
    private boolean esPostulacion;
    private INotificacionOperacion interfazNotificacion;
    private String estiloError = 
            "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    private String estiloNormal = "-fx-border-width: 0;";
    private Label lbComentairos;
    @FXML
    private Label lbComentarios;
    @FXML
    private TextField tfDirector;
    @FXML
    private TextField tfDuracion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtenerInformacionModalidades();
        obtenerInformacionCuerpoAcademico();
        cbCuerpoAcademico.valueProperty().addListener(new ChangeListener<CuerpoAcademico>(){
            
            @Override
            public void changed(ObservableValue<? extends CuerpoAcademico> observable, 
                    CuerpoAcademico oldValue, CuerpoAcademico newValue) {
                if(newValue != null){
                    cargarInformacionLgacs(newValue.getIdCuerpoAcademico());
                }
            }
            
        });
        
        cbAlumnosMax.getItems().addAll(1,2);
    }
    
    public void inicializarInformacionFormulario(boolean esEdicion, Anteproyecto atpEdicion,
            INotificacionOperacion interfazNotificacion){
        this.anteproyectoEdicion = atpEdicion;
        this.esEdicion = esEdicion;
        this.interfazNotificacion = interfazNotificacion;
        if(esEdicion){
            lbTitulo.setText("Editar Informacion del Anteproyecto " + 
                    anteproyectoEdicion.getNombreTrabajo());
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Crear Anteproyecto");
            tfDirector.setText(Academico.getInstanciaSingleton().toString());
        }
    }
    
    private void cargarInformacionEdicion() {
        taBibliografiaRecomendada.setText(anteproyectoEdicion.getBibliografiaRecomendada());
        taCodirectores.setText(anteproyectoEdicion.getCodirectores());
        taDescripcionProyecto.setText(anteproyectoEdicion.getDescripcionProyectoInvestigacion());
        taDescripcionTrabajo.setText(anteproyectoEdicion.getDescripcionTrabajoRecepcional());
        tfDirector.setText(anteproyectoEdicion.getNombreDirector());
        tfDuracion.setText(Integer.toString(anteproyectoEdicion.getMesesDuracionAproximada()));
        taLineaInvestigacion.setText(anteproyectoEdicion.getLineaInvestigacion());
        taNombreTrabajo.setText(anteproyectoEdicion.getNombreTrabajo());
        taProyectoInvestigacion.setText(anteproyectoEdicion.getProyectoInvestigacion());
        taRequisitos.setText(anteproyectoEdicion.getRequisitos());
        taResultadosEsperados.setText(anteproyectoEdicion.getResultadosEsperados());
        cbAlumnosMax.getSelectionModel().select(anteproyectoEdicion.getNumAlumnosParticipantes());
        int posicionCuerpoAcademico = obtenerPosicionComboCA(
                anteproyectoEdicion.getIdCuerpoAcademico());
        cbCuerpoAcademico.getSelectionModel().select(posicionCuerpoAcademico);
        int posicionLgac = obtenerPosicionComboLGAC(anteproyectoEdicion.getIdLgac());
        cbLgac.getSelectionModel().select(posicionLgac);
        int posicionModalidad = obtenerPosicionModalidad(anteproyectoEdicion.getIdModalidad());
        cbModalidad.getSelectionModel().select(posicionModalidad);
        
        if(anteproyectoEdicion.getComentarios() != null){
            taComentarios.setText(anteproyectoEdicion.getComentarios());
            taComentarios.setVisible(true);
            lbComentarios.setVisible(true);
        }
    }

    private void obtenerInformacionCuerpoAcademico() {
        cuerposAcademicos = FXCollections.observableArrayList();
        CuerpoAcademicoRespuesta cuerposAcademicosBD = 
                CuerpoAcademicoDAO.obtenerCuerposAcademicos();
        switch(cuerposAcademicosBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de Conexion",
                        "Error en la conexion con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de Consulta CA", 
                        "Por el momento no se puede obtener la información",
                        Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                cuerposAcademicos.addAll(cuerposAcademicosBD.getCuerposAcademicos());
                cbCuerpoAcademico.setItems(cuerposAcademicos);
                break;
        }
    }
    
    private void cargarInformacionLgacs(int idCuerpoAcademico) {
        lgacs = FXCollections.observableArrayList();
        LGACRespuesta lgacsBD = LGACDAO.obtenerLGACsPorCA(idCuerpoAcademico);
        switch(lgacsBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de Conexion",
                        "Error en la conexion con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de Consulta LGAC", 
                        "Por el momento no se puede obtener la información",
                        Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                lgacs.addAll(lgacsBD.getLgacs());
                cbLgac.setItems(lgacs);
                break;
        }
    }
    
    private void validarCamposPostulacion(){
        establecerEstiloNormal();
        boolean datosValidos = true;
        
        String bibliografiaRecomendada = taBibliografiaRecomendada.getText();
        String descripcionProyecto = taDescripcionProyecto.getText();
        String proyectoInvestigacion = taProyectoInvestigacion.getText();
        String lineaInvestigacion = taLineaInvestigacion.getText();
        String codirectores = taCodirectores.getText();
        String descripcionTrabajo = taDescripcionTrabajo.getText();
        int duracionAproximada = 0;
        String nombreTrabajo = taNombreTrabajo.getText();
        String requisitos = taRequisitos.getText();
        String resultadosEsperados = taResultadosEsperados.getText();
        int posicionCA = cbCuerpoAcademico.getSelectionModel().getSelectedIndex();
        int posicionLGAC = cbLgac.getSelectionModel().getSelectedIndex();
        int posicionModalidad = cbModalidad.getSelectionModel().getSelectedIndex();
        int alumnosMaximos = cbAlumnosMax.getSelectionModel().getSelectedIndex();
        
        if(bibliografiaRecomendada.trim().isEmpty()){
            taBibliografiaRecomendada.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(descripcionTrabajo.trim().isEmpty()){
            taDescripcionTrabajo.setStyle(estiloError);
            datosValidos = false;
        }
        
        try{
            duracionAproximada = Integer.parseInt(tfDuracion.getText());
        }catch(NumberFormatException nfe){
            tfDuracion.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(nombreTrabajo.trim().isEmpty()){
            taNombreTrabajo.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(requisitos.trim().isEmpty()){
            taRequisitos.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(resultadosEsperados.trim().isEmpty()){
            taResultadosEsperados.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(posicionCA == -1){
            cbCuerpoAcademico.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(posicionLGAC == -1){
            cbLgac.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(posicionModalidad == -1){
            cbModalidad.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(alumnosMaximos == -1){
            cbAlumnosMax.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(datosValidos){
            Anteproyecto anteproyectoValidado = new Anteproyecto();
            anteproyectoValidado.setBibliografiaRecomendada(bibliografiaRecomendada);
            anteproyectoValidado.setCodirectores(codirectores);
            anteproyectoValidado.setDescripcionProyectoInvestigacion(descripcionProyecto);
            anteproyectoValidado.setDescripcionTrabajoRecepcional(descripcionTrabajo);
            anteproyectoValidado.setEstado("Postulado");
            anteproyectoValidado.setIdCuerpoAcademico(cuerposAcademicos.
                    get(posicionCA).getIdCuerpoAcademico());
            anteproyectoValidado.setIdDirector(Academico.getInstanciaSingleton().getIdAcademico());
            anteproyectoValidado.setIdEstadoATP(2);
            anteproyectoValidado.setIdLgac(lgacs.get(posicionLGAC).getIdLgac());
            anteproyectoValidado.setIdModalidad(
                    modalidades.get(posicionModalidad).getIdModalidad());
            anteproyectoValidado.setLineaInvestigacion(lineaInvestigacion);
            anteproyectoValidado.setMesesDuracionAproximada(duracionAproximada);
            anteproyectoValidado.setModalidad(modalidades.get(posicionModalidad).toString());
            anteproyectoValidado.setNombreCA(cuerposAcademicos.get(posicionCA).getNombre());
            anteproyectoValidado.setNombreDirector(Academico.getInstanciaSingleton().toString());
            anteproyectoValidado.setNombreLgac(lgacs.get(posicionLGAC).getNombre());
            anteproyectoValidado.setNombreTrabajo(nombreTrabajo);
            anteproyectoValidado.setNumAlumnosParticipantes(cbAlumnosMax.getValue());
            anteproyectoValidado.setProyectoInvestigacion(proyectoInvestigacion);
            anteproyectoValidado.setRequisitos(requisitos);
            anteproyectoValidado.setResultadosEsperados(resultadosEsperados);
            esPostulacion = true;
            
            if(esEdicion){
                anteproyectoValidado.setIdAnteproyecto(anteproyectoEdicion.getIdAnteproyecto());
                actualizarAnteproyecto(anteproyectoValidado);
            }else{
                registrarPostularAnteproyecto(anteproyectoValidado);
            }
        }else{
            Utilidades.mostrarDialogoSimple("Campos vacíos o no válidos", 
                    "Campos vacíos o no válidos, por favor ingrese la información correcta "
                            + "e inténtelo de nuevo", Alert.AlertType.WARNING);
        }
    }

    private void obtenerInformacionModalidades() {
        modalidades = FXCollections.observableArrayList();
        ModalidadRespuesta modalidadesBD = ModalidadDAO.obtenerModalidades();
        switch(modalidadesBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de Conexion",
                        "Error en la conexion con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de Consulta Modalidad", 
                        "Por el momento no se puede obtener la información",
                        Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                modalidades.addAll(modalidadesBD.getModalidades());
                cbModalidad.setItems(modalidades);
                break;
        }
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }

    private int obtenerPosicionComboCA(int idCuerpoAcademico) {
        for(int i = 0; i < cuerposAcademicos.size(); i++){
            if(cuerposAcademicos.get(i).getIdCuerpoAcademico() == idCuerpoAcademico)
                return i;
        }
        return 0;
    }

    private int obtenerPosicionComboLGAC(int idLgac) {
        for(int i = 0; i < lgacs.size(); i++){
            if(lgacs.get(i).getIdLgac() == idLgac)
                return i;
        }
        return 0;
    }

    private int obtenerPosicionModalidad(int idModalidad) {
        for(int i = 0; i < modalidades.size(); i++){
            if(modalidades.get(i).getIdModalidad() == idModalidad)
                return i;
        }
        return 0;
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        validarCamposGuardar();
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cerrar Ventana",
                "¿Está seguro de cerrar la ventana sin guardar cambios?"))
            cerrarVentana();
    }

    @FXML
    private void clicPostular(ActionEvent event) {
        validarCamposPostulacion();
    }
    
    private void establecerEstiloNormal(){
        taBibliografiaRecomendada.setStyle(estiloNormal);
        taCodirectores.setStyle(estiloNormal);
        taComentarios.setStyle(estiloNormal);
        taDescripcionProyecto.setStyle(estiloNormal);
        taDescripcionTrabajo.setStyle(estiloNormal);
        tfDirector.setStyle(estiloNormal);
        tfDuracion.setStyle(estiloNormal);
        taLineaInvestigacion.setStyle(estiloNormal);
        taNombreTrabajo.setStyle(estiloNormal);
        taProyectoInvestigacion.setStyle(estiloNormal);
        taRequisitos.setStyle(estiloNormal);
        taResultadosEsperados.setStyle(estiloNormal);
        cbAlumnosMax.setStyle(estiloNormal);
        cbCuerpoAcademico.setStyle(estiloNormal);
        cbLgac.setStyle(estiloNormal);
        cbModalidad.setStyle(estiloNormal);
    }

    private void actualizarAnteproyecto(Anteproyecto anteproyectoActualizar) {
        int codigoRespuesta = AnteproyectoDAO.modificarAnteproyecto(anteproyectoActualizar);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                if(esPostulacion)
                    Utilidades.mostrarDialogoSimple("Error de Conexión",
                            "El anteproyecto no pudo ser postulado debido a un error de conexion..."
                            , Alert.AlertType.ERROR);
                else
                    Utilidades.mostrarDialogoSimple("Error de Conexión",
                            "La información del anteproyecto no pudo ser actualizada debido a "
                                    + "un error de conexión...", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de Consulta",
                        "La información del anteproyecto no pudo ser procesada, por favor "
                                + "verifique su información e intente más tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                if(esPostulacion)
                    Utilidades.mostrarDialogoSimple("Anteproyecto Postulado",
                            "El anteproyecto fue enviado al Cuerpo Academico " +
                            anteproyectoActualizar.getNombreCA() + " correctamente",
                            Alert.AlertType.INFORMATION);
                else
                    Utilidades.mostrarDialogoSimple("Anteproyecto Actualizado", 
                            "La información del anteproyecto fue modificada correctamente", 
                            Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionActualizar("Borrador");
                break;
        }
    }

    private void registrarPostularAnteproyecto(Anteproyecto anteproyectoEnvio) {
        int codigoRespuesta = AnteproyectoDAO.guardarAnteproyecto(anteproyectoEnvio);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                if(esPostulacion)
                    Utilidades.mostrarDialogoSimple("Error de Conexión", 
                            "El anteproyecto no pudo ser postulado debido a un error de conexión..."
                            , Alert.AlertType.ERROR);
                else
                    Utilidades.mostrarDialogoSimple("Error de Conexión",
                            "El anteproyecto no pudo ser guardado debido a un error de conexion...",
                            Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta ATP",
                        "La información del anteproyecto no pudo ser procesada, por favor verifique"
                        + " su informacion e intente más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                if(esPostulacion)
                    Utilidades.mostrarDialogoSimple("Anteproyecto Postulado",
                            "El anteproyecto fue enviado al Cuerpo Academico " +
                            anteproyectoEnvio.getNombreCA() + " correctamente",
                            Alert.AlertType.INFORMATION);
                else
                    Utilidades.mostrarDialogoSimple("Anteproyecto Guardado", 
                            "El anteproyecto fue guardado como borrador correctamente", 
                            Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardar("Borrador");
                break;
        }
    }

    private void validarCamposGuardar() {
        establecerEstiloNormal();
        boolean datosValidos = true;
        
        String bibliografiaRecomendada = taBibliografiaRecomendada.getText();
        String descripcionProyecto = taDescripcionProyecto.getText();
        String proyectoInvestigacion = taProyectoInvestigacion.getText();
        String lineaInvestigacion = taLineaInvestigacion.getText();
        String codirectores = taCodirectores.getText();
        String descripcionTrabajo = taDescripcionTrabajo.getText();
        Integer duracionAproximada = null;
        String nombreTrabajo = taNombreTrabajo.getText();
        String requisitos = taRequisitos.getText();
        String resultadosEsperados = taResultadosEsperados.getText();
        int posicionCA = cbCuerpoAcademico.getSelectionModel().getSelectedIndex();
        int posicionLGAC = cbLgac.getSelectionModel().getSelectedIndex();
        int posicionModalidad = cbModalidad.getSelectionModel().getSelectedIndex();
        int posicionAlumnosMax = cbAlumnosMax.getSelectionModel().getSelectedIndex();
        
        String duracion = tfDuracion.getText();
        if(!duracion.trim().isEmpty()){
            try{
                duracionAproximada = Integer.valueOf(tfDuracion.getText());
            }catch(NumberFormatException nfe){
                tfDuracion.setStyle(estiloError);
                tfDuracion.requestFocus();
                datosValidos = false;
            }
        }
        
        if(nombreTrabajo.trim().isEmpty()){
            taNombreTrabajo.setStyle(estiloError);
            taNombreTrabajo.requestFocus();
            taNombreTrabajo.requestLayout();
            datosValidos = false;
        }
        
         if(posicionCA == -1){
            cbCuerpoAcademico.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(posicionLGAC == -1){
            cbLgac.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(posicionModalidad == -1){
            cbModalidad.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(datosValidos){
            Anteproyecto anteproyectoValidado = new Anteproyecto();
            anteproyectoValidado.setBibliografiaRecomendada(bibliografiaRecomendada);
            anteproyectoValidado.setCodirectores(codirectores);
            anteproyectoValidado.setDescripcionProyectoInvestigacion(descripcionProyecto);
            anteproyectoValidado.setDescripcionTrabajoRecepcional(descripcionTrabajo);
            anteproyectoValidado.setEstado("Borrador");
            if(posicionCA != -1){
                anteproyectoValidado.setIdCuerpoAcademico(cuerposAcademicos.
                        get(posicionCA).getIdCuerpoAcademico());
                anteproyectoValidado.setNombreCA(cuerposAcademicos.get(posicionCA).getNombre());
            }
            anteproyectoValidado.setIdDirector(Academico.getInstanciaSingleton().getIdAcademico());
            anteproyectoValidado.setIdEstadoATP(1);
            if(posicionLGAC != -1){
                anteproyectoValidado.setIdLgac(lgacs.get(posicionLGAC).getIdLgac());
                anteproyectoValidado.setNombreLgac(lgacs.get(posicionLGAC).getNombre());
            }
            if(posicionModalidad != -1){
                anteproyectoValidado.setIdModalidad(
                    modalidades.get(posicionModalidad).getIdModalidad());
                anteproyectoValidado.setModalidad(modalidades.get(posicionModalidad).toString());
            }
            anteproyectoValidado.setLineaInvestigacion(lineaInvestigacion);
            if(duracionAproximada != null)
                anteproyectoValidado.setMesesDuracionAproximada(duracionAproximada);
            anteproyectoValidado.setNombreTrabajo(nombreTrabajo);
            if(posicionAlumnosMax != -1)
                anteproyectoValidado.setNumAlumnosParticipantes(cbAlumnosMax.getValue());
            anteproyectoValidado.setProyectoInvestigacion(proyectoInvestigacion);
            anteproyectoValidado.setRequisitos(requisitos);
            anteproyectoValidado.setResultadosEsperados(resultadosEsperados);
            
            if(esEdicion){
                anteproyectoValidado.setIdAnteproyecto(anteproyectoEdicion.getIdAnteproyecto());
                actualizarAnteproyecto(anteproyectoValidado);
            }else{
                registrarPostularAnteproyecto(anteproyectoValidado);
            }
        }else{
            Utilidades.mostrarDialogoSimple("Campos vacíos o no válidos", 
                    "Campos vacíos o no válidos, por favor ingrese la información correcta "
                            + "e inténtelo de nuevo", Alert.AlertType.WARNING);
        }
    }
}
