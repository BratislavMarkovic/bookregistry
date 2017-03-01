package books.gui;

import java.io.File;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import books.model.Author;
import books.model.Book;
import books.model.CollectionOfBooks;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class BookRegistry extends Application{
	
	
	private Stage primaryStage;
	private MenuItem close;
	private MenuItem open;
	private MenuItem newRegistry;
	private MenuItem save;
	private MenuItem saveAs;
	private BorderPane mainLayout;
	private TextField isbn;
	private TextField title;
	private TextField author;
	private Label searchStatus;
	private VBox searchResults;
	private GridPane searchCriteria;
	private TableView<Book> table;
	private FileChooser chooser ;
	private CollectionOfBooks registry;
	private String fileName;
	private boolean changeExist;
	
	public BookRegistry() throws ClassNotFoundException, IOException {
		
	}
	
	
    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage stage) {
    	
    	primaryStage = stage;

    	primaryStage.setTitle("Book Registry");
    	mainLayout = new BorderPane();
    	Scene scene = new Scene(mainLayout, 800, 530);
        scene.setFill(Color.OLDLACE);
 
        
    	chooser = new FileChooser();
    	chooser.setTitle("Choose book repository file");
    	chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Repository", "*.ser")
            );
      
    	MenuBar menuBar = new MenuBar();
    	Menu menuFile = new Menu("File");
    	menuBar.getMenus().addAll(menuFile);
    	
    	
    	open = new MenuItem("Open");
    	open.setGraphic(new ImageView(new Image("books/gui/open.png")));
    	open.setOnAction(new EventHandler<ActionEvent>() {
    	    public void handle(ActionEvent e) {
    	    	
    	    	openRegistry();
    	    	
    	    }
    	});
    	
    	menuFile.getItems().add(open);
    	
    	close = new MenuItem("Close");
    	close.setDisable(true);
    	close.setOnAction(new EventHandler<ActionEvent>() {
    	    public void handle(ActionEvent e) {
    	    	
    	    	closeRegistry();
    	    	
    	    }
    	});
    	
    	menuFile.getItems().add(close);
    	
    	
    	save = new MenuItem("Save");
    	save.setDisable(true);
    	save.setOnAction(new EventHandler<ActionEvent>() {
    	    public void handle(ActionEvent e) {
    	    	
    	    	saveRegistry();
    	    	
    	    }
    	});
    	
    	menuFile.getItems().add(save);

    	saveAs = new MenuItem("Save As");
    	saveAs.setDisable(true);
    	saveAs.setOnAction(new EventHandler<ActionEvent>() {
    	    public void handle(ActionEvent e) {
    	    	
    	    	saveAsRegistry();
    	    	
    	    }
    	});
    	
    	menuFile.getItems().add(saveAs);    	
    	
    	
    	newRegistry = new MenuItem("New registry");
    	newRegistry.setOnAction(new EventHandler<ActionEvent>(){
    		 public void handle(ActionEvent e) {
    			
    			 newRegistry();
    			 
    		 }
    	});
    	
    	menuFile.getItems().add(newRegistry);
	    
    	//Creating a GridPane container
    	searchCriteria = new GridPane();
    	searchCriteria.setAlignment(Pos.CENTER);
    	searchCriteria.setPadding(new Insets(20, 20, 20, 20));
    	searchCriteria.setVgap(10);
    	searchCriteria.setHgap(10);

    	//Defining the Name text field
    	
    	Label isbnLabel = new Label("ISBN");
    	GridPane.setConstraints(isbnLabel, 0, 0);
    	
    	isbn = new TextField();
    	isbn.setPrefColumnCount(10);
    	GridPane.setConstraints(isbn, 1, 0);
    	
    	Label titleLabel = new Label("Title");
    	GridPane.setConstraints(titleLabel, 0, 1);
    	
    	title = new TextField();
    	isbn.setPrefColumnCount(20);
    	GridPane.setConstraints(title, 1, 1); 
    	
    	Label authorLabel = new Label("Author");
    	GridPane.setConstraints(authorLabel, 0, 2);
    	
    	author = new TextField();
    	isbn.setPrefColumnCount(20);
    	GridPane.setConstraints(author,1,2);
    	
    	Button search = new Button("Search");
    	GridPane.setConstraints(search, 0, 3);
    	
    	search.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent e) {
    			
    			searchRegistry();
    			
		    }
		}); 
    	
    	search.setDefaultButton(true);
    	
    	Button newBook = new Button("New book");
    	GridPane.setConstraints(newBook, 1, 3);
    	newBook.setOnAction(new EventHandler<ActionEvent>(){
   		 public void handle(ActionEvent e) {
   			 
   			 addNewBook();
   			 
		 }
		});
    	
    	searchCriteria.getChildren().addAll(isbnLabel,isbn,titleLabel,title,authorLabel,author,search,newBook);
    	
    	// define table
    	table = new TableView<Book>();
    	table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	table.setPrefHeight(10 * 25 + 25);   

    	table.setEditable(false);
        TableColumn<Book,String> isbnCol = new TableColumn<Book,String>("ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory<Book,String>("isbn"));
        isbnCol.setPrefWidth(200);

        TableColumn<Book,String> titleCol = new TableColumn<Book,String>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        titleCol.setPrefWidth(600);

        table.getColumns().addAll(titleCol,isbnCol);
        
		ObservableList<Book> foundBooks = FXCollections.observableArrayList();
	    foundBooks.addListener(new ListChangeListener<Book> ()  {

	        public void onChanged(ListChangeListener.Change<? extends Book> c) {

	           table.setPrefHeight(table.getItems().size() > 10 ? table.getItems().size() : 10  * 25 + 25);
	           
	        }
	    });
	    table.setItems(foundBooks);	

    	table.setRowFactory(new Callback<TableView<Book>, TableRow<Book>>() {
		
			public TableRow<Book> call(TableView<Book> tableView) {

                final TableRow<Book> row = new TableRow<Book>();  
                ContextMenu contextMenu = new ContextMenu();  
                MenuItem removeMenuItem = new MenuItem("Remove");  
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {  
                    public void handle(ActionEvent event) {
                    	
                    	removeBook(row.getItem());
                    	
                    }  
                });
                MenuItem addAuthorMenuItem = new MenuItem("Add author");
                addAuthorMenuItem.setOnAction(new EventHandler<ActionEvent>() {  
                    public void handle(ActionEvent event) {
                    	
                    	addAuthor(row.getItem());
                    	
                    }
                });
                contextMenu.getItems().add(removeMenuItem);
                contextMenu.getItems().add(addAuthorMenuItem);
               // Set context menu on row, but use a binding to make it only show for non-empty rows:  
                row.contextMenuProperty().bind(  
                        Bindings.when(row.emptyProperty())  
                        .then((ContextMenu)null)  
                        .otherwise(contextMenu)  
                );
                
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 2) {
                        	Dialog<ButtonType> viewBookDialog = new ViewBookDialog(table.getSelectionModel().getSelectedItem());
                        	viewBookDialog.showAndWait();
                        }
                    }
                });
                
                return row ;  
			
			}
		});
    	
    	searchStatus = new Label("");
    	searchResults = new VBox();
        searchResults.setSpacing(5);
        searchResults.setPadding(new Insets(10, 10, 10, 10));
        searchResults.getChildren().addAll(searchStatus,table);
    	
    	mainLayout.setTop(menuBar);
    	
    	
    	primaryStage.setScene(scene);
    	primaryStage.show();
      
    }
    
    private void setAvailableOperations(){
    	
		close.setDisable(!(registry!=null && fileName!=null));
		open.setDisable(registry!=null);
		newRegistry.setDisable(registry!=null);
		save.setDisable(!(registry!=null&&changeExist));
		saveAs.setDisable(!(registry != null && fileName!=null));
		if(registry != null){
			mainLayout.setCenter(searchCriteria);
			mainLayout.setBottom(searchResults);
			if(fileName != null){
				primaryStage.setTitle("Book Registry - "+fileName);
			} else {
				primaryStage.setTitle("Book Registry - New ");
			}
			if(changeExist){
				primaryStage.setTitle(primaryStage.getTitle()+" * ");
			}
		} else {
			table.getItems().clear();
			searchStatus.setText("");
			isbn.setText("");
			title.setText("");
			author.setText("");
			mainLayout.setCenter(null);
			mainLayout.setBottom(null);
			primaryStage.setTitle("Book Registry");
		}
    }

    
    public void saveRegistry(){
		File file = null;
		if(fileName == null){
	    	file = chooser.showSaveDialog(primaryStage);
	    	if(file == null){
	    		return;
	    	}
		}
    	try {
			registry.closeTheBookRegister(fileName!=null?fileName:file.getAbsolutePath());
			changeExist = false;
			if(fileName == null){
				fileName = file.getAbsolutePath();
			}
			setAvailableOperations();
		} catch (Exception ex) {
			Alert error = new Alert(AlertType.ERROR, "Problem saving registry");
			error.setHeaderText(ex.getMessage());
			error.setContentText("Problem storing registry at location \n"+ new File("bokregister").getAbsolutePath());
			
			error.showAndWait();

		}

    }
    
    
    
    public void showSuccess(String title,String message){
		Alert info = new Alert(AlertType.INFORMATION);
		info.setTitle(title);
		info.setHeaderText(message);
		
		info.showAndWait();
    }

    public void showError(String title,String message){
		Alert info = new Alert(AlertType.ERROR);
		info.setTitle(title);
		info.setHeaderText(message);
		
		info.showAndWait();
    }
    
    public void openRegistry(){
    	File file = chooser.showOpenDialog(primaryStage);
    	if(file == null){
    		return;
    	}
    	try {
    		CollectionOfBooks toOpen = new CollectionOfBooks();
    		toOpen.openTheBookRegister(file.getAbsolutePath());
    		registry = toOpen;
    		fileName = file.getAbsolutePath();
    		changeExist = false;
    		setAvailableOperations();
		} catch (Exception ex) {
			Alert error = new Alert(AlertType.ERROR, "Problem opening registry");
			error.setHeaderText(ex.getMessage());
			error.setContentText("Problem loading registry at location \n"+ new File("bokregister").getAbsolutePath());
			
			error.showAndWait();

		}
    }
    
    public void closeRegistry(){
    	try {
			registry.closeTheBookRegister(fileName);
			registry = null;
			fileName = null;
			changeExist = false;
			setAvailableOperations();
		} catch (Exception ex) {
			Alert error = new Alert(AlertType.ERROR, "Problem closing registry");
			error.setHeaderText(ex.getMessage());
			error.setContentText("Problem storing registry at location \n"+ new File("bokregister").getAbsolutePath());
			
			error.showAndWait();

		}
    }
    
    public void saveAsRegistry(){
    	File file = chooser.showSaveDialog(primaryStage);
    	if(file == null){
    		return;
    	}
    	
    	try {
			registry.closeTheBookRegister(file.getAbsolutePath());
			fileName = file.getAbsolutePath();
			changeExist = false;
			setAvailableOperations();
		} catch (Exception ex) {
			Alert error = new Alert(AlertType.ERROR, "Problem saving registry");
			error.setHeaderText(ex.getMessage());
			error.setContentText("Problem storing registry at location \n"+ new File("bokregister").getAbsolutePath());
			
			error.showAndWait();

		}
    }
    
    public void newRegistry(){
		 registry = new CollectionOfBooks();
		 fileName = null;
		 setAvailableOperations();
    }
    
    public void searchRegistry(){
    	
		boolean isbnProvided = false;
		boolean authorProvided = false;
		boolean titleProvided = false;
		
		List<Book> results = new ArrayList<Book>();
		
		if(isbn.getText() != null && !isbn.getText().equals("")){
			
			results.addAll(registry.searchByIsbn(isbn.getText()));
			isbnProvided = true;
		}
		
		if(title.getText() != null && !title.getText().equals("")){
			
			if(isbnProvided){
				results.retainAll(registry.searchByTitle(title.getText()));
			} else {
				results.addAll(registry.searchByTitle(title.getText()));
			}
			titleProvided = true;
		}
		
		if(author.getText() != null && !author.getText().equals("")){
			
			if(isbnProvided || titleProvided){
				results.retainAll(registry.searchByAuthor(author.getText()));
			} else {
				results.addAll(registry.searchByAuthor(author.getText()));
			}
			authorProvided = true;
		}
		
		if(!isbnProvided && !titleProvided && !authorProvided ){
			
			results.addAll(registry.getBooks());
			
		}
		
		if(results.isEmpty()){
			searchStatus.setText("No books found");
		} else if(results.size() > 10){
			searchStatus.setText("Found "+results.size()+" books. Displaying only first 10.");
			results = results.subList(0, 10);
		} else{
			searchStatus.setText("Found "+results.size()+" books.");
		}
		
		table.getItems().clear();
		table.getItems().addAll(results);
    }
    
    public void addNewBook(){
    	
		 // construct new add book dialog with all necessary fields
		 
		 NewBookDialog newBookDialog = new NewBookDialog();
		 
		 Optional<Book> result = newBookDialog.showAndWait();
		 if(result.isPresent()){
			 Book newBook = result.get();
			 
			 if(registry.addBook(newBook)){
				 
				 changeExist = true;
				 showSuccess("Add book", "Book "+newBook.getTitle()+" successfully added.");
				 setAvailableOperations();
				 
			 } else {
				 
				 showError("Add book","Book with isbn "+newBook.getIsbn()+" alredy exists in the registry.");
				 
			 }
		 }
    	
    }
    
    public void addAuthor(Book book){
    	
    	TextInputDialog enterAuthorName = new TextInputDialog("Name of author");
    	enterAuthorName.setTitle("New author for book "+book.getIsbn());
    	enterAuthorName.setHeaderText("Author name must be unique");
    	enterAuthorName.setContentText("Please enter author name:");

    	// Traditional way to get the response value.
    	Optional<String> result = enterAuthorName.showAndWait();
    	if (result.isPresent()){
    		if(book.addAuthor(new Author(result.get()))){
    			
    			changeExist = true;
    			showSuccess("Add author","Author "+result.get()+" successfully added");
    			setAvailableOperations();
    			
    		} else {
    			
    			showError("Add author","Author "+result.get()+" already defined for "+book.getTitle());
    			
    		}
    		;
    	}

    	
    }
    
    public void removeBook(Book book){
    	
		Alert warning = new Alert(AlertType.CONFIRMATION, "Remove book "+book.getIsbn());
		warning.setHeaderText("We are about to remove book"+book.getIsbn()+" from registry!");
		warning.setContentText("Are you ok with this?");
		((Button)warning.getDialogPane().lookupButton(ButtonType.OK)).setDefaultButton(false);
		((Button)warning.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);
		
		Optional<ButtonType> result = warning.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK){
        	table.getItems().remove(book);
        	registry.removeBook(book);
        	changeExist = true;
        	showSuccess("Remove book","Successfully removed book "+book.getTitle()+" from registry");
        	setAvailableOperations();
		} 
    	
    }
    
    @Override
    public void stop(){
    	
    	if(changeExist){
    		
			Alert warning = new Alert(AlertType.CONFIRMATION);
			warning.setHeaderText("Application is about to exit.");
			warning.setContentText("Do you want to save the changes made to the current book registry?");

			Optional<ButtonType> result = warning.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK){
				
				saveRegistry();
				
			}
    		
    	}
    	
    }

    public static void main(String[] args) {
        launch(args);
    }

}

