/*
* Título del programa: Controlador para Agregar Estudiantes a curso.
* Autor: Omar Dylan Segura Platas
* Fecha: 09/06/2023
* Descripción: Clase controladora de vista FXMLAgregarEstudianteCurso
*/



package javafxsspger.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafxsspger.modelo.dao.CursoDAO;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Curso;
import javafxsspger.modelo.pojo.CursoRespuesta;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;



public class FXMLAgregarEstudianteCursoController implements Initializable {

    @FXML
    private TextField lbMatricula;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbCorreo;
    private Label lbTelefono;
    @FXML
    private Label lbTitulo;
    @FXML
    private ComboBox<Curso> cbCurso;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarCursos();
       configurarComboBox();
    }    


@FXML
private void clicBuscar(ActionEvent event) {
    String matricula = lbMatricula.getText();
    EstudianteRespuesta respuestaBD = EstudianteDAO.obtenerDatosDelEstudiante(matricula);

    if (respuestaBD.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA) {
        ArrayList<Estudiante> estudiantes = respuestaBD.getEstudiantes();
        if (!estudiantes.isEmpty()) {
            Estudiante estudiante = estudiantes.get(0);
            lbNombre.setText(estudiante.getNombre() +" " +estudiante.getApellidoPaterno() +" "+ estudiante.getApellidoMaterno());
            lbCorreo.setText(estudiante.getEmail());
        } else {
            Utilidades.mostrarDialogoSimple("No se encontró", "No se encontró ningún estudiante con esa matricula.",
                    Alert.AlertType.ERROR);
        }
    } else {
          Utilidades.mostrarDialogoSimple("Error", "Ocurrió un error con la base de datos, por favor intentelo más tarde..",
                    Alert.AlertType.ERROR);
    }
}


 @FXML
private void clicAgregar(ActionEvent event) { 
   Estudiante estudiante = obtenerEstudianteActual();
    Curso curso = cbCurso.getValue();
    if (estudiante != null && curso != null) {
        int idEstudiante = estudiante.getIdEstudiante();
        int idCursoEE = curso.getIdCursoEE();
        boolean registroExiste = CursoDAO.VerificarRegistro(idCursoEE, idEstudiante);
        
        if (registroExiste) {
        Utilidades.mostrarDialogoSimple("Atención", "El estudiante ya es parte del curso.", Alert.AlertType.WARNING);
        } else{
        CursoDAO.AgregarEstudianteCurso(idCursoEE, idEstudiante);
        Utilidades.mostrarDialogoSimple("Estudiante agregado", "El estudiante ha sido agregado al curso exitosamente.",
            Alert.AlertType.INFORMATION);
         
        } 
    } else {
        Utilidades.mostrarDialogoSimple("Error", "Seleccione un estudiante y un curso.",
            Alert.AlertType.ERROR);
    }
}


private Estudiante obtenerEstudianteActual() {
    String matricula = lbMatricula.getText();
    EstudianteRespuesta respuestaBD = EstudianteDAO.obtenerDatosDelEstudiante(matricula);

    if (respuestaBD.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA) {
        ArrayList<Estudiante> estudiantes = respuestaBD.getEstudiantes();
        if (!estudiantes.isEmpty()) {
            return estudiantes.get(0);
        }
    }

    return null;
}

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lbCorreo.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
    private void cargarCursos() {
        CursoRespuesta respuestaBD = CursoDAO.obtenerCursos();
        if (respuestaBD.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA) {
            ArrayList<Curso> cursos = respuestaBD.getCursos();
            cbCurso.getItems().addAll(cursos);
        } else {
            System.out.println("Error al obtener los cursos");
        }
    }
    
    private void configurarComboBox() {
        cbCurso.setCellFactory(new Callback<ListView<Curso>, ListCell<Curso>>() {
            @Override
            public ListCell<Curso> call(ListView<Curso> param) {
                return new ListCell<Curso>() {
                    @Override
                    protected void updateItem(Curso curso, boolean empty) {
                        super.updateItem(curso, empty);
                        if (curso != null) {
                            setText(curso.getNombreMateria());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        cbCurso.setButtonCell(new ListCell<Curso>() {
            @Override
            protected void updateItem(Curso curso, boolean empty) {
                super.updateItem(curso, empty);
                if (curso != null) {
                    setText(curso.getNombreMateria());
                } else {
                    setText(null);
                }
            }
        });
    }


    
}
