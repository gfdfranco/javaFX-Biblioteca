package ch.makery.address;

import javafx.beans.property.SimpleStringProperty;

public class Miembros {
	public SimpleStringProperty tnombreM = new SimpleStringProperty();
	public SimpleStringProperty tclaveM = new SimpleStringProperty();
	public SimpleStringProperty tdirM = new SimpleStringProperty();
	public SimpleStringProperty ttelM = new SimpleStringProperty();
	public SimpleStringProperty tsexoM = new SimpleStringProperty();
	
	
	
	public String getTnombreM(){
		return tnombreM.get();
	}
	public String getTclaveM(){
		return tclaveM.get();
	}
	public String getTdirM(){
		return tdirM.get();
	}
	public String getTtelM(){
		return ttelM.get();
	}
	public String getTsexoM(){
		return tsexoM.get();
	}
	
}
