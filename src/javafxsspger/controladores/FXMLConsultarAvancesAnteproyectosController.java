/*
* Título del programa: Controlador de Consultar avances anteproyectos
* Autor: Omar Dylan Segura Platas
* Fecha: 09/06/2023
* Descripción: Clase controladora de vista FXMLConsultarAvancesAnteproyectos
*/



package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.modelo.dao.AvanceDAO;
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.modelo.pojo.AvanceRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLConsultarAvancesAnteproyectosController implements Initializable {

    @FXML
    private TableView<Avance> tvAnteproyectos;
    @FXML
    private TableColumn tcNombreAnteproyecto;
    @FXML
    private TableColumn tcEstablecidos;
    @FXML
    private TableColumn tcEntregados;
    @FXML
    private TableColumn tcPorcentaje;
    @FXML
    private Label lbTitulo;

    private ObservableList<Avance> avances;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    

     private void configurarTabla(){
    tcNombreAnteproyecto.setCellValueFactory(new PropertyValueFactory<>("nombreTrabajo"));
    tcEstablecidos.setCellValueFactory(new PropertyValueFactory<>("cantidadActividades")); // Asociar con la propiedad "cantidadActividades"
    tcEntregados.setCellValueFactory(new PropertyValueFactory<>("cantidadRegistros")); // Asociar con la propiedad "cantidadRegistros"
      tcPorcentaje.setCellValueFactory(new PropertyValueFactory<>("porcentaje"));
    tcPorcentaje.setCellFactory(column -> {
    return new TableCell<Avance, Double>() {
        @Override
        protected void updateItem(Double porcentaje, boolean empty) {
            super.updateItem(porcentaje, empty);
            if (empty) {
                setText(null);
            } else {
                setText(String.format("%.2f%%", porcentaje));
            }
        }
    };
});
}

     
     private void cargarInformacionTabla() {
    avances = FXCollections.observableArrayList();
    AvanceRespuesta respuestaBD = AvanceDAO.consultarAvances();
    switch (respuestaBD.getCodigoRespuesta()) {
        case Constantes.ERROR_CONEXION:
            // ...
            break;
        case Constantes.ERROR_CONSULTA:
            // ...
            break;
        case Constantes.OPERACION_EXITOSA:
            for (Avance avance : respuestaBD.getAvances()) {
                int totalActividades = avance.getCantidadActividades();
                int totalEntregas = avance.getCantidadRegistros();
                double porcentaje = (totalEntregas / (double) totalActividades) * 100;
                avance.setPorcentaje(porcentaje);
            }
            avances.addAll(respuestaBD.getAvances());
            tvAnteproyectos.setItems(avances);
    }
}

     
    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tvAnteproyectos.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
    
    
}
