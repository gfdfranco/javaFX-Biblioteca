package ch.makery.address;

import javafx.beans.property.SimpleStringProperty;

public class Libros {
	public SimpleStringProperty tnombreL = new SimpleStringProperty();
	public SimpleStringProperty tclaveL = new SimpleStringProperty();
	public SimpleStringProperty tautorL = new SimpleStringProperty();
	public SimpleStringProperty tclasificacionL = new SimpleStringProperty();
	public SimpleStringProperty tdisponibleL = new SimpleStringProperty();
	
	
	
	public String getTnombreL(){
		return tnombreL.get();
	}
	public String getTclaveL(){
		return tclaveL.get();
	}
	public String getTautorL(){
		return tautorL.get();
	}
	public String getTclasificacionL(){
		return tclasificacionL.get();
	}
	public String getTdisponibleL(){
		return tdisponibleL.get();
	}
	
}
