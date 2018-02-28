/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author maxni
 */
public class Carrera{
	private IntegerProperty codigoCarrera;
	private StringProperty nombreCarrera;
	private IntegerProperty cantidadAsignaturas;

	public Carrera(int codigoCarrera, String nombreCarrera, int cantidadAsignaturas) { 
		this.codigoCarrera = new SimpleIntegerProperty(codigoCarrera);
		this.nombreCarrera = new SimpleStringProperty(nombreCarrera);
		this.cantidadAsignaturas = new SimpleIntegerProperty(cantidadAsignaturas);
	}

	//Metodos atributo: codigoCarrera
	public int getCodigoCarrera() {
		return codigoCarrera.get();
	}
	public void setCodigoCarrera(int codigoCarrera) {
		this.codigoCarrera = new SimpleIntegerProperty(codigoCarrera);
	}
	public IntegerProperty CodigoCarreraProperty() {
		return codigoCarrera;
	}
	//Metodos atributo: nombreCarrera
	public String getNombreCarrera() {
		return nombreCarrera.get();
	}
	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = new SimpleStringProperty(nombreCarrera);
	}
	public StringProperty NombreCarreraProperty() {
		return nombreCarrera;
	}
	//Metodos atributo: cantidadAsignaturas
	public int getCantidadAsignaturas() {
		return cantidadAsignaturas.get();
	}
	public void setCantidadAsignaturas(int cantidadAsignaturas) {
		this.cantidadAsignaturas = new SimpleIntegerProperty(cantidadAsignaturas);
	}
	public IntegerProperty CantidadAsignaturasProperty() {
		return cantidadAsignaturas;
	}
        
        public static void llenarInformacion(Connection connection, ObservableList<Carrera> lista){
            try {
                Statement statement = connection.createStatement();
                ResultSet resultado = statement.executeQuery("select * from tbl_carreras");
                
                while (resultado.next()) {                    
                    lista.add(new Carrera( resultado.getInt("codigo_carrera"),
                                           resultado.getString("nombre_carrera"),
                                           resultado.getInt("cantidad_asignaturas")
                                         )
                    );
                }
            } catch (SQLException ex) {
                Logger.getLogger(Carrera.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Override
        public String toString(){
            return nombreCarrera.get() + " ("+cantidadAsignaturas.get()+")";
        }
}