package ch.makery.address;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	  private Stage primaryStage;//Escenario que se usa
	  private BorderPane rootLayout;//Objeto vista del layOut
	  @FXML private Button inicio;  
	    //Este metodo es obligatorio ponerlo viene de la clase Application
	    @Override
	    public void start(Stage primaryStage) {
	    	//Se iniciar el escenario de la aplicacion
	        this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("Biblioteca Luman"); //Se cambia titulo de la aplicacion
	        //SE CREAN LOS BORDES DE LA APLICACION
	        initRootLayout();
	        //SE CREA ANCHO QUE VA DENTRO DE LOS BORDES DE LA APLICACION
	        showPersonOverview();
	    }

	   //Se crean bordes de la aplicacion
	    public void initRootLayout() {
	        //Como puede ver excepciones ponemos try y catch
	    	try {
	            // Carga el FXML
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("view/InicioBorde.fxml"));//Se da la direccion del archivo FXML
	            //Se dice que es bordePane y se carga la aplicacion
	            rootLayout = (BorderPane) loader.load();

	            // Se muestra el contenido dentro del escenario
	            Scene scene = new Scene(rootLayout);
	            primaryStage.setScene(scene);
	            primaryStage.show();
	            
	          
	        //EXCEPCIONES POSIBLES............................    
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    //Se crea anchor, el contenido que va adentro de los bordes:
	    public void showPersonOverview() {
	        try {
	        	// Carga el FXML
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("view/PersonOverview.fxml"));//direccion de FXML
	            //Se dice que es AnchorPane y se carga la aplicacion
	            AnchorPane personOverview = (AnchorPane) loader.load();
	            
	         // Se muestra el contenido dentro del escenario dentro de los bordes
	            rootLayout.setCenter(personOverview);
	            
	        //EXCEPCIONES POSIBLES............................    
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void entrar()
	    {
	    	Application app2 = new MainBiblioteca(); 
			
			//Inicia esenario nuevo
            Stage anotherStage = new Stage();
           //Por si hay excepciones se pone try and catch
            try {
            	app2.start(anotherStage);//Se inicia la aplicacion
            	Stage stage = (Stage) inicio.getScene().getWindow();
            	stage.close();
            } catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();//IMPRIME ERROR
            }
	    }
	public static void main(String[] args) {
		launch(args);
	}
}
