package books.gui;

import java.io.File;

import books.model.CollectionOfBooks;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
/**
*
* @web http://java-buddy.blogspot.com/
*/
public class JavaFX_exMenu extends Application {
 
	 MenuItem open;
	 MenuItem close;
	 MenuItem newBook;
	 MenuItem newRegistry;
	 private CollectionOfBooks registry;
	
	
 /**
  * @param args the command line arguments
  */
 public static void main(String[] args) {
     launch(args);
 }
 
 @Override
 public void start(Stage primaryStage) {
     primaryStage.setTitle("java-buddy.blogspot.com");
     Group root = new Group();
     Scene scene = new Scene(root, 400, 300, Color.WHITE);
   
     //Top Menu Bar
     MenuBar menuBar = new MenuBar();
 
     Menu menu1 = new Menu("File");
   
     
     open = new MenuItem("Open");
     open.setGraphic(new ImageView(new Image("books/gui/open.png")));
     open.setOnAction(new EventHandler<ActionEvent>() {
       
         public void handle(ActionEvent e) {
             System.out.println("Open button Clicked");
             try {
 	    		CollectionOfBooks toOpen = new CollectionOfBooks();
 	    		toOpen.openTheBookRegister("bokregister.ser");
 	    		registry = toOpen;
 	    		setAvailableOperations();
			  }catch (Exception ex) {
					Alert error = new Alert(AlertType.ERROR, "Problem opening registry");
					error.setHeaderText(ex.getMessage());
					error.setContentText("Problem loading registry at location \n"+ new File("bokregister").getAbsolutePath());
					
					error.showAndWait();

			   }
         }
     });
   
     close = new MenuItem("Close");
     close.setDisable(true);
     close.setOnAction(new EventHandler<ActionEvent>() {
       
    	 public void handle(ActionEvent e) {
             System.out.println("Close button Clicked");
             try {
	    			registry.closeTheBookRegister("bokregister.ser");
	    			registry = null;
	    			setAvailableOperations();
				} catch (Exception ex) {
					Alert error = new Alert(AlertType.ERROR, "Problem opening registry");
					error.setHeaderText(ex.getMessage());
					error.setContentText("Problem loading registry at location \n"+ new File("bokregister").getAbsolutePath());
					
					error.showAndWait();

				}
         }
     });
   
     newBook = new MenuItem("New Book");
     newBook.setDisable(true);
     newBook.setOnAction(new EventHandler<ActionEvent>() {
       
         public void handle(ActionEvent e) {
             System.out.println("New Book Clicked");
         }
     });
     
   
     //Sub-Menu
     Menu subMenu_s1 = new Menu("Search");
     MenuItem newTitle = new MenuItem("Search by title");
     MenuItem newIsbn = new MenuItem("Search by ISBN");
     MenuItem newAuthor = new MenuItem("Search by author");
    // subMenu_s1.getItems().addAll(newTitle, newIsbn, newAuthor);
     
     subMenu_s1.setOnAction(new EventHandler<ActionEvent>() {
         
         public void handle(ActionEvent e) {
             System.out.println("Search Clicked");
         }
     });
   
     menu1.getItems().add(open);
     menu1.getItems().add(close);
     menu1.getItems().add(newBook);
     menu1.getItems().add(subMenu_s1);   //Add Sub-Menu
     menuBar.getMenus().add(menu1);
   
     menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
     root.getChildren().add(menuBar);
     primaryStage.setScene(scene);
     primaryStage.show();
 }
 
 	private void setAvailableOperations(){
		newBook.setDisable(registry==null);
		close.setDisable(registry==null);
		open.setDisable(registry!=null);
		newRegistry.setDisable(registry!=null);
 	}
}
