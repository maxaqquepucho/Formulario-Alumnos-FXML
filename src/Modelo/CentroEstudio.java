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

public class CentroEstudio{
	private IntegerProperty codigoCentro;
	private StringProperty nombreCentro;

	public CentroEstudio(int codigoCentro, String nombreCentro) { 
		this.codigoCentro = new SimpleIntegerProperty(codigoCentro);
		this.nombreCentro = new SimpleStringProperty(nombreCentro);
	}

	//Metodos atributo: codigoCentro
	public int getCodigoCentro() {
		return codigoCentro.get();
	}
	public void setCodigoCentro(int codigoCentro) {
		this.codigoCentro = new SimpleIntegerProperty(codigoCentro);
	}
	public IntegerProperty CodigoCentroProperty() {
		return codigoCentro;
	}
	//Metodos atributo: nombreCentro
	public String getNombreCentro() {
		return nombreCentro.get();
	}
	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = new SimpleStringProperty(nombreCentro);
	}
	public StringProperty NombreCentroProperty() {
		return nombreCentro;
	}
        
        public static void llenarInformacion(Connection connection, ObservableList<CentroEstudio> lista){
            try {
                Statement statement = connection.createStatement();
                ResultSet resultado = statement.executeQuery("select * from tbl_centro_estudio");
                
                while (resultado.next()) {                    
                    lista.add(new CentroEstudio( resultado.getInt("codigo_centro"),
                                                 resultado.getString("nombre_centro_estudio")
                                               )
                    );
                }
            } catch (SQLException ex) {
                Logger.getLogger(Carrera.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Override
        public String toString(){
            return nombreCentro.get();
        }
}