/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularioalumno;

import Modelo.Alumno;
import Modelo.Carrera;
import Modelo.CentroEstudio;
import Modelo.Conexion;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 *
 * @author maxni
 */
public class FXMLDocumentController implements Initializable {
    //Columnas
    @FXML private TableColumn<Alumno, String> columnaNombre;
    @FXML private TableColumn<Alumno, String> columnaApellido;
    @FXML private TableColumn<Alumno, Number> columnaEdad;
    @FXML private TableColumn<Alumno, String> columnaGenero;
    @FXML private TableColumn<Alumno, Date> columnaFecha;
    @FXML private TableColumn<Alumno, CentroEstudio> columnaCentroEstudio;
    @FXML private TableColumn<Alumno, Carrera> columnaCarrera;
    
    //Componentes GUI
    @FXML private TextField textCodigo;
    @FXML private TextField textNombre;
    @FXML private TextField textApellido;
    @FXML private TextField textEdad;
    @FXML private RadioButton radioMasculino;
    @FXML private RadioButton radioFemenino;
    @FXML private DatePicker fechaIngreso;
    @FXML private ComboBox<Carrera> cmbCarrera;
    @FXML private ComboBox<CentroEstudio> cmbCentroEstudio;
    @FXML private TableView<Alumno> tablaAlumnos;
    @FXML private Button btnGuardar;
    @FXML private Button btnEliminar;
    @FXML private Button btnActualizar;
    
    //Colecciones
    private ObservableList<Carrera> listaCarreras;
    private ObservableList<CentroEstudio> listaCentroEstudios;
    private ObservableList <Alumno> listaAlumnos;
    
    private Conexion conexion;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = new Conexion();
        conexion.establecerConexion();
        // Inicializar Listas 
        listaCarreras = FXCollections.observableArrayList();
        listaCentroEstudios = FXCollections.observableArrayList();
        listaAlumnos = FXCollections.observableArrayList();
        
        // Llenar Listas
        Carrera.llenarInformacion(conexion.getConnection(), listaCarreras);
        CentroEstudio.llenarInformacion(conexion.getConnection(), listaCentroEstudios);
        Alumno.llenarInformacion(conexion.getConnection(), listaAlumnos);
       
        // Enlazar Listas con ComboBox 
        cmbCarrera.setItems(listaCarreras);
        cmbCentroEstudio.setItems(listaCentroEstudios);
        tablaAlumnos.setItems(listaAlumnos);
        
        //Enlazar columnas con atribustos
        columnaNombre.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        columnaApellido.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido") );
        columnaEdad.setCellValueFactory(new PropertyValueFactory<Alumno, Number>("edad") );
        columnaGenero.setCellValueFactory(new PropertyValueFactory<Alumno, String>("genero") );
        columnaFecha.setCellValueFactory(new PropertyValueFactory<Alumno, Date>("fecha") );
        columnaCentroEstudio.setCellValueFactory(new PropertyValueFactory<Alumno, CentroEstudio>("centroEstudio") );
        columnaCarrera.setCellValueFactory(new PropertyValueFactory<Alumno, Carrera>("carrera") );
        
        gestionarEventos();
        conexion.cerrarConexion();
    }

    public void gestionarEventos(){
        tablaAlumnos.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Alumno>(){
                    @Override
                    public void changed(ObservableValue<? extends Alumno> observable, Alumno valorAnterior, Alumno valorSeleccionado) {
                         //To change body of generated methods, choose Tools | Templates.
                         //System.out.println("Nombre de alumno selecionado: " +valorSeleccionado.getNombre());
                         
                         if (valorSeleccionado!=null) {
                            textCodigo.setText(String.valueOf(valorSeleccionado.getCodigoAlumno()));
                            textNombre.setText(valorSeleccionado.getNombre());
                            textApellido.setText(valorSeleccionado.getApellido());
                            textEdad.setText(String.valueOf(valorSeleccionado.getEdad()));
                            if (valorSeleccionado.getGenero().equals("M")) {
                               radioMasculino.setSelected(true);
                               radioFemenino.setSelected(false);
                           } else {
                               radioMasculino.setSelected(false);
                               radioFemenino.setSelected(true);
                           }

                           fechaIngreso.setValue(valorSeleccionado.getFecha().toLocalDate());
                           cmbCarrera.setValue(valorSeleccionado.getCarrera());
                           cmbCentroEstudio.setValue(valorSeleccionado.getCentroEstudio());

                           btnGuardar.setDisable(true);
                           btnActualizar.setDisable(false);
                           btnEliminar.setDisable(false);
                        }
                         
                    }

                }
        );
    } 
    
    @FXML
    public void guardarRegistro(){
        //Crear una nueva instancia del tipo Alumno
        Alumno a = new Alumno(0, 
                textNombre.getText(),
                textApellido.getText(),
                Integer.valueOf(textEdad.getText()), 
                radioMasculino.isSelected()?"M":"F",
                Date.valueOf(fechaIngreso.getValue()),
                cmbCentroEstudio.getSelectionModel().getSelectedItem(),
                cmbCarrera.getSelectionModel().getSelectedItem());
        //llamar al metodo guardarRegistro de la clase Alumno
        conexion.establecerConexion();
        int resultado = a.guardarRegistro(conexion.getConnection());
        conexion.cerrarConexion();
        if (resultado == 1) {
            listaAlumnos.add(a);
            //JDK 8u>40
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("REGISTRO AGREGADO");
            mensaje.setContentText("El registro ha sido guardado exitosamente");
            mensaje.setHeaderText("Resultados");
            mensaje.show();
        }
    }
    
    @FXML
    public void actualizarRegistro(){
         //Crear una nueva instancia del tipo Alumno
        Alumno a = new Alumno(
                Integer.valueOf(textCodigo.getText()), 
                textNombre.getText(),
                textApellido.getText(),
                Integer.valueOf(textEdad.getText()), 
                radioMasculino.isSelected()?"M":"F",
                Date.valueOf(fechaIngreso.getValue()),
                cmbCentroEstudio.getSelectionModel().getSelectedItem(),
                cmbCarrera.getSelectionModel().getSelectedItem());
        conexion.establecerConexion();
        int resultado = a.actualizarRegistro(conexion.getConnection());
        conexion.cerrarConexion();
        
        if (resultado == 1) {
            listaAlumnos.set(tablaAlumnos.getSelectionModel().getSelectedIndex(),a);
            //JDK 8u>40
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("REGISTRO ACTUALIZADO");
            mensaje.setContentText("El registro ha sido actualizado exitosamente");
            mensaje.setHeaderText("Resultados");
            mensaje.show();
        }
    }
    
    @FXML
    public void eliminarRegistro(){
        conexion.establecerConexion();
        int resultado = tablaAlumnos.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexion();
        
        if (resultado == 1) {
            listaAlumnos.remove(tablaAlumnos.getSelectionModel().getSelectedIndex());
            //JDK 8u>40
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("REGISTRO ELIMINADO");
            mensaje.setContentText("El registro ha sido eliminado exitosamente");
            mensaje.setHeaderText("Resultados");
            mensaje.show();
        }
    }
    
    @FXML
    public void limpiarComponentes(){
        textCodigo.setText(null);
        textNombre.setText(null);
        textApellido.setText(null);
        textEdad.setText(null);
        radioMasculino.setSelected(false);
        radioFemenino.setSelected(false);
        fechaIngreso.setValue(null);
        cmbCarrera.setValue(null);
        cmbCentroEstudio.setValue(null);
        
        btnGuardar.setDisable(false);
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
    }
    
}
