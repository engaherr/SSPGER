/*
* Título del programa: Controlador de Cuerpo Académico
* Autor: Jasiel Emir Zavaleta García
* Fecha: 08/06/2023
* Descripción: Clase controladora de vista FXMLRegistrarCA.fxml
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxsspger.interfaz.INotificacionOperacion;
import javafxsspger.modelo.dao.AcademicoDAO;
import javafxsspger.modelo.dao.CuerpoAcademicoDAO;
import javafxsspger.modelo.dao.DependenciaDAO;
import javafxsspger.modelo.dao.GradoConsolidacionDAO;
import javafxsspger.modelo.dao.LgacDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.AcademicoRespuesta;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.Dependencia;
import javafxsspger.modelo.pojo.DependenciaRespuesta;
import javafxsspger.modelo.pojo.GradoConsolidacion;
import javafxsspger.modelo.pojo.GradoConsolidacionRespuesta;
import javafxsspger.modelo.pojo.Lgac;
import javafxsspger.modelo.pojo.LgacRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLRegistrarCAController implements Initializable {
    private INotificacionOperacion interfazNotificacion;
    private boolean esEdicion;
    private CuerpoAcademico caEdicion;
   
    
    @FXML
    private Label lbNombreFormulario;
    @FXML
    private ComboBox<GradoConsolidacion> cbGrado;
    @FXML
    private ComboBox<Dependencia> cbDependencias;
    @FXML
    private ComboBox<Lgac> cbLgac;    
    @FXML
    private TextField tfClave;
    @FXML
    private TextField tfNombreCA;
    
    @FXML
    private TableColumn colNombreAcademico;
    @FXML
    private TableColumn colPaternoAcademico;
        @FXML
    private TableColumn colMaternoAcademico;

    @FXML
    private TableView<Academico> tvAcademicos;
    
    private ObservableList<Dependencia> dependenciasBD;
    private ObservableList<GradoConsolidacion> gradosBD;
    private ObservableList<Academico> academicosBD;
    private ObservableList<Lgac> lgacBD;

    private String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    private String estiloNormal;
    private String inicioClave = "UV";
    
    private Lgac lgacDeCA;
    private int idLgac;
    @FXML
    private Label lbElegirResponsable;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionDependencias();
        cargarInformacionGrados();
        cargarInformacionLgac();
        configurarTablaAcademicos();
        cargarInformacionAcademicos();
        
        estiloNormal = tfClave.getStyle();

    }    

    @FXML
    private void clicGuardarLgac(ActionEvent event) {
       validarInformacion();
    }

    private void validarInformacion(){
        tfClave.setStyle(estiloNormal);
        tfNombreCA.setStyle(estiloNormal);
        cbDependencias.setStyle(estiloNormal);
        cbGrado.setStyle(estiloNormal);
        cbLgac.setStyle(estiloNormal);
        tvAcademicos.setStyle(estiloNormal);
        
        boolean esValido = true;
        String nombreCA = null;
        String claveCA = null;
        int idGrado = -1;
        int idDependencia = -1;
        
        
        if(! tfClave.getText().isEmpty()){
            claveCA = tfClave.getText();
            if(! claveCA.startsWith(inicioClave)){
               esValido = false;
               Utilidades.mostrarDialogoSimple("Error con la clave del CA",
                       "Es necesario que las claves comiencen con las letras 'UV'",
                       Alert.AlertType.WARNING);
            }
        }else{
            esValido = false;
            tfClave.setStyle(estiloError);
        }
        
        if(! tfNombreCA.getText().isEmpty()){
            nombreCA = tfNombreCA.getText();
        }else{
            tfNombreCA.setStyle(estiloError);
            esValido = false;
        }
        
       if(cbDependencias.getSelectionModel().getSelectedItem() != null){
            idDependencia = cbDependencias.getSelectionModel().getSelectedItem().getIdDependencia();
        }else{
            cbDependencias.setStyle(estiloError);
            esValido = false;
        }
       
       if(cbGrado.getSelectionModel().getSelectedItem() != null){
            idGrado = cbGrado.getSelectionModel().getSelectedItem().getIdGradoConsolidacion();
        }else{
            cbGrado.setStyle(estiloError);
            esValido = false;
        }

       if(cbLgac.getSelectionModel().getSelectedItem() != null){
           lgacDeCA= cbLgac.getSelectionModel().getSelectedItem();
            this.idLgac = cbLgac.getSelectionModel().getSelectedItem().getIdLgac(); 
        }else{
            cbLgac.setStyle(estiloError);
            esValido = false;
        }
       
        Academico academicoSeleccionado = tvAcademicos.getSelectionModel().getSelectedItem(); 
        if(academicoSeleccionado == null){
            esValido = false;
            tvAcademicos.setStyle(estiloError);  
        }
            

        
        CuerpoAcademico caValido = new CuerpoAcademico();
        if (esEdicion) {
            if (! tfNombreCA.getText().isEmpty()) {
                
                caValido.setClave(claveCA);
                caValido.setNombre(nombreCA);
                caValido.setIdDependencia(idDependencia);
                caValido.setIdGradoConsolidacion(idGrado);
                caValido.setIdResponsable(caEdicion.getIdResponsable());
                caValido.setIdCuerpoAcademico(caEdicion.getIdCuerpoAcademico());
                if((cbLgac.getSelectionModel().getSelectedItem() == null)){
                    cbLgac.setStyle(estiloNormal);
                    modificarCuerpoAcademico(caValido);
                }else{
                    modificarCuerpoAcademico(caValido);
                }
            }else{
                tfNombreCA.setStyle(estiloError);
                Utilidades.mostrarDialogoSimple("Campos Vacíos",
                    "Por favor ingrese información o  seleccione valores en todas las casillas",
                    Alert.AlertType.WARNING);
            }  
        }else  {
            if (esValido) {
                caValido.setClave(claveCA);
                caValido.setNombre(nombreCA);
                caValido.setIdDependencia(idDependencia);
                caValido.setIdGradoConsolidacion(idGrado);
                caValido.setIdResponsable(academicoSeleccionado.getIdAcademico());
                registrarCuerpoAcademico(caValido);
            }else{
                Utilidades.mostrarDialogoSimple("Campos Vacíos",
                    "Por favor ingrese información o  seleccione valores en todas las casillas",
                    Alert.AlertType.WARNING);
            }

        }       
    }
        
     private void modificarCuerpoAcademico(CuerpoAcademico caValido){
        int codigoRespuesta = CuerpoAcademicoDAO.modificarCuerpoAcademico(caValido);
        switch (codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","Por el momento no "
                        + "podemos establecer conexión con la base de datos, por favor inténtalo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case  Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta","Ocurrió un error "
                        + "durante la modificacion del Cuerpo Académico, "
                        + "inténtelo de nuevo por favor", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada","El Cuerpo Academico "
                        + "ha sido modificado en el sistema", Alert.AlertType.INFORMATION);
                registrarElementosCA(caEdicion.getIdCuerpoAcademico()
                        ,caValido.getIdResponsable() );
                cerrarVentana();
                String exito = "Operacion exitosa";
                interfazNotificacion.notificarOperacionActualizar(exito);
                break;               
        }
    }   
     
    private void registrarCuerpoAcademico(CuerpoAcademico caValido){
        int codigoRespuesta = CuerpoAcademicoDAO.registrarCuerpoAcademico(caValido);
        switch (codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","No fue posible  "
                        + "registrar al Cuerpo Académico, por favor inténtelo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case  Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta","Ocurrió un error "
                        + "al registrar el Cuerpo Académico, "
                        + "inténtelo de nuevo por favor", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada","El Cuerpo Academico "
                        + "ha sido registrado",Alert.AlertType.INFORMATION);
                int idCuerpoAcademico = CuerpoAcademicoDAO.obtenerUltimoCARegistrado();
                registrarElementosCA(idCuerpoAcademico,caValido.getIdResponsable() );
                cerrarVentana();
                String exito = "Operacion exitosa";
                interfazNotificacion.notificarOperacionGuardar(exito);
                break;               
        }
    }
    
    private void registrarElementosCA(int idCuerpoAcademico, int idAcademico){
        if(esEdicion && (cbLgac.getSelectionModel().getSelectedItem() != null) || !esEdicion){
            int respuestaLgac = LgacDAO.agregarLgacCA(idLgac, idCuerpoAcademico);
            switch(respuestaLgac){
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexion LGAC", "Por el momento no "
                            + "hay conexion, "
                            + "por favor inténtalos más tarde", Alert.AlertType.ERROR);
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", "Ocurrió un error al"
                            + " actualizar el LGAC del CA,"
                            + " por favor inténtelo más tarde", Alert.AlertType.WARNING);
                    break;
                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("EXITO AL ACTUALIZAR EL LGAC", inicioClave, Alert.AlertType.INFORMATION);
                    break;
            }           
        }
        
        int respuestaAcademico = AcademicoDAO.agregarAcademicoCA(idAcademico, idCuerpoAcademico);
          switch(respuestaAcademico){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion ACADE", "POr el momento no "
                        + "hay conexion, "
                        + "por favor inténtalos más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Ocurrió un error al "
                        + "actualizar al Academico del CA,"
                        + " por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("EXITO AL ACTUALIZAR EL ACADEMICO", inicioClave, Alert.AlertType.INFORMATION);

                break;    
        }       
    }
    
    @FXML
    private void clicCancelarGuardado(ActionEvent event) {
        cerrarVentana();
        
    }
 
    private void configurarTablaAcademicos(){
        colNombreAcademico.setCellValueFactory(new PropertyValueFactory("nombre"));
        colPaternoAcademico.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colMaternoAcademico.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));

    }
     
    private void cargarInformacionDependencias(){
        dependenciasBD = FXCollections.observableArrayList();
        DependenciaRespuesta dependencias = DependenciaDAO.recuperarDependencias();
         switch(dependencias.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "POr el momento no hay "
                        + "conexion, "
                        + "por favor inténtalos más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Ocurrió un error al "
                        + "cargar la información de las dependencias,"
                        + " por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                dependenciasBD.addAll(dependencias.getDependencias());
                cbDependencias.setItems(dependenciasBD);
                break;    
         }        
    }
    private void cargarInformacionGrados(){
        gradosBD = FXCollections.observableArrayList();
        GradoConsolidacionRespuesta grados = GradoConsolidacionDAO.recuperarGradosConsolidacion();
         switch(grados.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "POr el momento no hay "
                        + "conexion, "
                        + "por favor inténtalos más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Ocurrió un error al "
                        + "cargar la información de los grados de consolidación,"
                        + " por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                gradosBD.addAll(grados.getGrados());
                cbGrado.setItems(gradosBD);
                break;    
         }        
    } 

    private void cargarInformacionAcademicos(){
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
                tvAcademicos.setItems(academicosBD);
                break;
        }       
    }
    private void cargarInformacionLgac(){
        lgacBD = FXCollections.observableArrayList();
        LgacRespuesta lgacs = LgacDAO.obtenerLgacsSinCA();
        switch(lgacs.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión","No se pudo "
                        + "recuperar la información, por favor inténtelo "
                        + "más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:   
                Utilidades.mostrarDialogoSimple("Error de consulta","Ocurrió "
                        + "un error al intentar recuperar la información de las lgac, "
                        + "inténtelo de nuevo, por favor", Alert.AlertType.WARNING);    
                break;
            case Constantes.OPERACION_EXITOSA:
                lgacBD.addAll(lgacs.getLgacs());
                cbLgac.setItems(lgacBD);
                break;
        }       
    }
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) tfClave.getScene().getWindow();
        escenarioBase.close();
    }
    public void inicializarInformacionFormulario(boolean esEdicion, CuerpoAcademico caEdicion, 
            INotificacionOperacion interfazNotificacion){
        this.esEdicion = esEdicion;
        this.caEdicion = caEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            cargarInformacionCuerpoAcademico();
            lbNombreFormulario.setText("Modificación de Lgac");
        }
    }
    
    private void cargarInformacionCuerpoAcademico(){
        tfClave.setText(caEdicion.getClave());
        tfClave.setEditable(false);
        tfNombreCA.setText(caEdicion.getNombre());
        
       
        int posicionDependencia = obtenerPosicionComboDependencia(caEdicion.
                getIdDependencia());
        cbDependencias.getSelectionModel().select(posicionDependencia);
        int posicionGrado = obtenerPosicionComboGrado(caEdicion.getIdGradoConsolidacion());
        cbGrado.getSelectionModel().select(posicionGrado);
        tvAcademicos.setVisible(false);
        lbElegirResponsable.setVisible(false);
    }
    
    private int obtenerPosicionComboDependencia(int idDependencia){
        for (int i = 0; i < dependenciasBD.size(); i++) {
            if(dependenciasBD.get(i).getIdDependencia() == idDependencia)
                return i;
        }
        return 0;
    }    
    
      private int obtenerPosicionComboGrado(int idGrado){
        for (int i = 0; i < gradosBD.size(); i++) {
            if(gradosBD.get(i).getIdGradoConsolidacion() == idGrado)
                return i;
        }
        return 0;
    }    
    
}
