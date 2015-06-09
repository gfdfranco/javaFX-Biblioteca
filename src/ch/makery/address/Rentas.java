package ch.makery.address;

import javafx.beans.property.SimpleStringProperty;

public class Rentas {
	public SimpleStringProperty tclaveR = new SimpleStringProperty();
	public SimpleStringProperty tclaveLibroR = new SimpleStringProperty();
	public SimpleStringProperty tclaveMiembroR = new SimpleStringProperty();
	public SimpleStringProperty tfechaR = new SimpleStringProperty();
	public SimpleStringProperty thoraR = new SimpleStringProperty();
	
	
	
	public String getTclaveR(){
		return tclaveR.get();
	}
	public String getTclaveLibroR(){
		return tclaveLibroR.get();
	}
	public String getTclaveMiembroR(){
		return tclaveMiembroR.get();
	}
	public String getTfechaR(){
		return tfechaR.get();
	}
	public String getThoraR(){
		return thoraR.get();
	}
	
}
