package ch.makery.address;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainBiblioteca extends Application {
   
	//boton salir
	@FXML Button botonCerrar;
	//Registrar Libro
	@FXML TextField claveLibro;
	@FXML TextField nomLibro;
	@FXML TextField autorLibro;
	@FXML TextField clasificacionLibro;
	
	//Registrar Miembro
	@FXML TextField claveMiebro;
	@FXML TextField nombreMiebro;
	@FXML TextField dirMiebro;
	@FXML TextField telMiebro;
	@FXML private RadioButton radioHombre;
	@FXML private RadioButton radioMujer;
	
	//Rentas
	@FXML TextField rclaveRenta;
	@FXML TextField rclaveMiembro;
	@FXML TextField rclaveLibro;
	@FXML TextField rfecha;
	@FXML TextField rhora;
	
	//Entregas
	@FXML TextField eclaveRenta;
		
	//Consultas especificas libros
	@FXML TextField bclaveLibro;
	@FXML TextField bnombreLibro;
	@FXML TextField bautorLibro;
	@FXML TextField bclasificacionLibro;
	@FXML TextField bdisponible;
	
	//Consultas especificas miembros
	@FXML TextField bclaveMiembro;
	@FXML TextField bnombreMiembro;
	@FXML TextField bdirMiembro;
	@FXML TextField btelMiembro;
	@FXML TextField bsexoMiembro;
	
	/* Tabla Libros*/
	@FXML private TableView<Libros> tablaLibros;
    @FXML private TableColumn tnombreL;
    @FXML private TableColumn tclaveL;
    @FXML private TableColumn tautorL;
    @FXML private TableColumn tclasificacionL;
    @FXML private TableColumn tdisponibleL;
    ObservableList<Libros> libros;
    
    /* Tabla Miembros*/
	@FXML private TableView<Miembros> tablaMiembros;
    @FXML private TableColumn tnombreM;
    @FXML private TableColumn tclaveM;
    @FXML private TableColumn tdirM;
    @FXML private TableColumn ttelM;
    @FXML private TableColumn tsexoM;
    ObservableList<Miembros> miembros;
    
    /* Tabla Rentas pendientes*/
   @FXML private TableView<Rentas> tablaRentas;
   @FXML private TableColumn tclaveR;
   @FXML private TableColumn tclaveLibroR;
   @FXML private TableColumn tclaveMiembroR;
   @FXML private TableColumn tfechaR;
   @FXML private TableColumn thoraR;
   ObservableList<Rentas> rentas;
   
   //Eliminar registro
   @FXML TextField eliminarLibro;
   @FXML TextField eliminarMiembro;
   
   private Stage primaryStage;
   private BorderPane rootLayout;
   @Override
   public void start(Stage primaryStage) {
       this.primaryStage = primaryStage;
       this.primaryStage.setTitle("Biblioteca Luman");
       initRootLayout();
       showPersonOverview();
       
   }
   /**
    * Initializes the root layout.
    */
  
   public void initRootLayout() {
       try {
           // Load root layout from fxml file.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MainBiblioteca.class.getResource("view/AdministradorBorde.fxml"));
           rootLayout = (BorderPane) loader.load();
           // Show the scene containing the root layout.
           Scene scene = new Scene(rootLayout);
           primaryStage.setScene(scene);
           primaryStage.show();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

  
   public void showPersonOverview() {
       try {
           // Load person overview.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MainBiblioteca.class.getResource("view/Administrador.fxml"));
           AnchorPane personOverview = (AnchorPane) loader.load();
           // Set person overview into the center of root layout.
           rootLayout.setCenter(personOverview);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   public void registrarLibro(){
   	
   	DataBaseSQL	db = new DataBaseSQL();
   	String[] data = new String[5];
   		data[0] = claveLibro.getText();
   		if(!Validaciones.isPositiveInt(data[0], "Clave Libro", 4))
   			return;
   		data[1] = nomLibro.getText();
		if(!Validaciones.isCorrectSize(data[1], 20, "Nombre"))
			return;
		data[2] = autorLibro.getText();
		if(!Validaciones.isCorrectSize(data[2], 25, "Autor"))
			return;
		data[3] = clasificacionLibro.getText();
		if(!Validaciones.isCorrectSize(data[3], 15, "clasificacion"))
			return;
		data[4]="SI";
   	
   	
		if(db.insert("libro", data))
   		error("Libro agregado con Exito!");
		else
   		error("Error al ingresar el libro");
   }
   public void registrarMiembro(){
	   	
	   	DataBaseSQL	db = new DataBaseSQL();
	   	String[] data = new String[5];
	   		data[0] = claveMiebro.getText();
	   		if(!Validaciones.isPositiveInt(data[0], "Clave Miembro", 4))
	   			return;
	   		data[1] = nombreMiebro.getText();
			if(!Validaciones.isName(data[1], 35, "Nombre"))
				return;
			data[2] = dirMiebro.getText();
			if(!Validaciones.isCorrectSize(data[2], 30, "Direccion"))
				return;
			data[3] = telMiebro.getText();
			if(!Validaciones.isPhone(data[3]))
				return;
			if(radioHombre.isSelected()){
				data[4]="MASCULINO";
			}
			if(radioMujer.isSelected()){
				data[4]="FEMENINO";
			}
	   	
			if(db.insert("miembro", data))
	   		error("Miembro agregado con Exito!");
			else
	   		error("Error al ingresar el miembro");
	   }
   
   public void buscarLibro(){
   	DataBaseSQL	db = new DataBaseSQL();
		HashMap<String, String> data;
		
		data = db.fetchArray("LIBRO", "CLAVE_LIBRO", bclaveLibro.getText());
		
		if(data == null){
			error("El libro no existe");
			return;
		}
		
		bnombreLibro.setText(data.get("NOMBRE"));
		bautorLibro.setText(data.get("AUTOR"));
		bclasificacionLibro.setText(data.get("CLASIFICACION"));
		bdisponible.setText(data.get("DISPONIBLE"));
		  		
   }
   public void buscarMiembro(){
	   	DataBaseSQL	db = new DataBaseSQL();
			HashMap<String, String> data;
			
			data = db.fetchArray("MIEMBRO", "CLAVE_MIEMBRO", bclaveMiembro.getText());
			
			if(data == null){
				error("El miembro no existe");
				return;
			}
			
			bnombreMiembro.setText(data.get("NOMBRE"));
			bdirMiembro.setText(data.get("DIRECCION"));
			btelMiembro.setText(data.get("TELEFONO"));
			bsexoMiembro.setText(data.get("SEXO"));
			  		
	   }
   public void entregasLibros(){
	   	
	   	DataBaseSQL	db = new DataBaseSQL();
		HashMap<String, String> dataS;
		String claveRenta=eclaveRenta.getText();
		String claveLibro;
		if((dataS = db.fetchArray("renta", "CLAVE_RENTA", claveRenta)) != null)
	   	{
			claveLibro=dataS.get("CLAVE_LIBRO");
			if(db.free("DELETE FROM RENTA WHERE CLAVE_RENTA = " + claveRenta))
			{
				error("Entrega realizada con exito");
				db.libroDisponible(claveLibro);
				error("El libro ya  se encuentra disponible!");
			}	
			else
				error("Problema con la devolucion");
	   	}
		else
			error("La renta no existe");
	   }
   
   public void deleteLibro(){
	   	
	   	DataBaseSQL	db = new DataBaseSQL();
		HashMap<String, String> dataS;
		String eliminar=eliminarLibro.getText();
		if((dataS = db.fetchArray("libro", "CLAVE_LIBRO", eliminar)) != null)
	   	{
			
			if(db.free("DELETE FROM LIBRO WHERE CLAVE_LIBRO = " + eliminar))
			{
				error("El libro ha sido eliminado con exito!");
			}	
			else
				error("Problema al eliminar el libro, compruebe los campos");
	   	}
		else
			error("El libro no existe");
	   }
   
   public void deleteMiembro(){
	   	
	   	DataBaseSQL	db = new DataBaseSQL();
		HashMap<String, String> dataS;
		String eliminar=eliminarMiembro.getText();
		if((dataS = db.fetchArray("MIEMBRO", "CLAVE_MIEMBRO", eliminar)) != null)
	   	{
			
			if(db.free("DELETE FROM MIEMBRO WHERE CLAVE_MIEMBRO = " + eliminar))
			{
				error("El miembro ha sido eliminado con exito!");
			}	
			else
				error("Problema al eliminar el miembro, compruebe los campos");
	   	}
		else
			error("El miembro no existe");
	   }
  
   public void salir()
   {
   	Stage stage = (Stage) botonCerrar.getScene().getWindow();
   	stage.close();
   }
   public void registrarRenta(){
	   	
	   	DataBaseSQL	db = new DataBaseSQL();
	   	HashMap<String, String> dataS;
	   	String[] data = new String[5];
	   	String claveLibro=rclaveLibro.getText();
	   	String claveMiembro=rclaveMiembro.getText();
	   	if((dataS = db.fetchArray("miembro", "CLAVE_MIEMBRO", claveMiembro)) != null)
	   	{
	   		if((dataS = db.fetchArray("libro", "CLAVE_LIBRO", claveLibro)) != null)
		   	{
	   			if(dataS.get("DISPONIBLE").equals("SI"))
	   			{
			   		data[0] = rclaveRenta.getText();
			   		if(!Validaciones.isPositiveInt(data[0], "Clave Renta", 4))
			   			return;
			   		data[1] = rclaveMiembro.getText();
			   		if(!Validaciones.isPositiveInt(data[1], "Clave Miembro", 4))
			   			return;
			   		data[2] = rclaveLibro.getText();
			   		if(!Validaciones.isPositiveInt(data[2], "Clave Libro", 4))
			   			return;
			   		data[3] = rfecha.getText();
					if(!Validaciones.isCorrectSize(data[3], 20, "Fecha"))
						return;
			   		data[4] = rhora.getText();
					if(!Validaciones.isCorrectSize(data[4], 10, "Hora"))
						return;
					
			   	   
					if(db.insert("renta", data))
					{
						error("Renta agregada con Exito!");
						db.libroNODisponible(data[2]);
						error("El libro ya no se encuentra disponible hasta su devolucion!");
					}
					else
						error("Error al ingresar la renta");
	   			}
	   			else
	   				error("El libro no esta disponible, se encuentra rentado");
		   	}
	   		else
	   			error("El libro no existe");
	   	}
	   	else
	   		error("El miembro no existe");
	   		
	   }
   
   public void initTableLibros(){
	    tnombreL.setCellValueFactory(new PropertyValueFactory<Libros, String>("tnombreL"));
	    tclaveL.setCellValueFactory(new PropertyValueFactory<Libros, String>("tclaveL"));
	    tautorL.setCellValueFactory(new PropertyValueFactory<Libros, String>("tautorL"));
	    tclasificacionL.setCellValueFactory(new PropertyValueFactory<Libros, String>("tclasificacionL"));
	    tdisponibleL.setCellValueFactory(new PropertyValueFactory<Libros, String>("tdisponibleL"));
	    libros = FXCollections.observableArrayList();
	   	tablaLibros.setItems(libros);
	    
	    fillTableLibros();
   }
   @FXML
   public void fillTableLibros(){
   	DataBaseSQL db = new DataBaseSQL();
		List<HashMap<String, String>> data1;
		data1 = db.getAll("LIBRO");
		
		for(int i = 0; i < data1.size(); i++){
			
			Libros dtA = new Libros();
			
			dtA.tnombreL.set(data1.get(i).get("NOMBRE"));
			dtA.tclaveL.set(data1.get(i).get("CLAVE_LIBRO"));
			dtA.tautorL.set(data1.get(i).get("AUTOR"));
			dtA.tclasificacionL.set(data1.get(i).get("CLASIFICACION"));
			dtA.tdisponibleL.set(data1.get(i).get("DISPONIBLE"));
									
			libros.add(dtA);
		}
   }
   
   public void initTableMiembros(){
	   tnombreM.setCellValueFactory(new PropertyValueFactory<Miembros, String>("tnombreM"));
	   tclaveM.setCellValueFactory(new PropertyValueFactory<Miembros, String>("tclaveM"));
	   tdirM.setCellValueFactory(new PropertyValueFactory<Miembros, String>("tdirM"));
	   ttelM.setCellValueFactory(new PropertyValueFactory<Miembros, String>("ttelM"));
	   tsexoM.setCellValueFactory(new PropertyValueFactory<Miembros, String>("tsexoM"));
	    miembros = FXCollections.observableArrayList();
	   	tablaMiembros.setItems(miembros);
	    
	    fillTableMiembros();
  }
  @FXML
  public void fillTableMiembros(){
  	DataBaseSQL db = new DataBaseSQL();
		List<HashMap<String, String>> data1;
		data1 = db.getAll("MIEMBRO");
		
		for(int i = 0; i < data1.size(); i++){
			
			Miembros dtA = new Miembros();
			
			dtA.tnombreM.set(data1.get(i).get("NOMBRE"));
			dtA.tclaveM.set(data1.get(i).get("CLAVE_MIEMBRO"));
			dtA.tdirM.set(data1.get(i).get("DIRECCION"));
			dtA.ttelM.set(data1.get(i).get("TELEFONO"));
			dtA.tsexoM.set(data1.get(i).get("SEXO"));
									
			miembros.add(dtA);
		}
  }
  public void initTableRentas(){
	  tclaveR.setCellValueFactory(new PropertyValueFactory<Rentas, String>("tclaveR"));
	  tclaveLibroR.setCellValueFactory(new PropertyValueFactory<Rentas, String>("tclaveLibroR"));
	  tclaveMiembroR.setCellValueFactory(new PropertyValueFactory<Rentas, String>("tclaveMiembroR"));
	  tfechaR.setCellValueFactory(new PropertyValueFactory<Rentas, String>("tfechaR"));
	  thoraR.setCellValueFactory(new PropertyValueFactory<Rentas, String>("thoraR"));
	    rentas = FXCollections.observableArrayList();
	   	tablaRentas.setItems(rentas);
	    
	    fillTableRentas();
 }
 @FXML
 public void fillTableRentas(){
 	DataBaseSQL db = new DataBaseSQL();
		List<HashMap<String, String>> data1;
		data1 = db.getAll("RENTA");
		
		for(int i = 0; i < data1.size(); i++){
			
			Rentas dtA = new Rentas();
			
			dtA.tclaveR.set(data1.get(i).get("CLAVE_RENTA"));
			dtA.tclaveLibroR.set(data1.get(i).get("CLAVE_LIBRO"));
			dtA.tclaveMiembroR.set(data1.get(i).get("CLAVE_MIEMBRO"));
			dtA.tfechaR.set(data1.get(i).get("FECHA"));
			dtA.thoraR.set(data1.get(i).get("HORA"));
									
			rentas.add(dtA);
		}
 }
   public void error(String txt){
   	JOptionPane.showMessageDialog(null, txt);
   }
   
}
