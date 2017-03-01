package books.gui;

import books.model.Author;
import books.model.Book;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.event.EventHandler;

public class NewBookDialog extends Dialog<Book> {

	private TextField titleField;
	private TextField isbnField;
	private TextField editionField;
	private TextField authorField;
	private TextField priceField;
	
	
	private ButtonType ok = new ButtonType("OK",ButtonData.OK_DONE);
	private ButtonType cancel = new ButtonType("Cancel",ButtonData.CANCEL_CLOSE);

	public NewBookDialog(){
		
		setTitle("New book");
		setHeaderText("Define properties of new book");
		
		GridPane layout = new GridPane();
		
		Label titleLabel = new Label("Title");
		Label isbnLabel = new Label("ISBN");
		Label editionLanbel = new Label("Edition");
		Label authorLabel = new Label("Author");
		Label priceLabel = new Label("Price");
		
		titleField = new TextField();
		isbnField = new TextField();
		editionField = new TextField();
		authorField = new TextField();
		priceField = new TextField();
		
		layout.setHgap(7D);
		layout.setVgap(8D);
		
		GridPane.setConstraints(titleLabel, 0, 0);
		GridPane.setConstraints(isbnLabel, 0, 1);
		GridPane.setConstraints(editionLanbel, 0, 2);
		GridPane.setConstraints(authorLabel, 0, 3);
		GridPane.setConstraints(priceLabel, 0, 4);
		
		
		GridPane.setConstraints(titleField, 1, 0);
		GridPane.setConstraints(isbnField, 1, 1);
		GridPane.setConstraints(editionField, 1, 2);
		GridPane.setConstraints(authorField, 1, 3);
		GridPane.setConstraints(priceField, 1, 4);
		
		
		
		layout.getChildren().addAll(titleLabel,isbnLabel,editionLanbel,authorLabel,priceLabel,
				titleField,isbnField,editionField,authorField,priceField);
		
		getDialogPane().getButtonTypes().addAll(ok, cancel);
		getDialogPane().setContent(layout);
		
		setResultConverter(new Callback<ButtonType, Book>() {
			
			public Book call(ButtonType option) {
				if(option == ok){
				
					try {
						
						return validateAndCreate();
						
					} catch (Exception e) {
						return null;
					}
					
				} else {
					return null;
				}
			}
		});
		
		getDialogPane().lookupButton(ok).addEventFilter(ActionEvent.ACTION,
		new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				try {
					validateAndCreate();
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING, "Invalid input");
					alert.setHeaderText("Missing or ivalid book data");
					alert.setContentText(e.getMessage());
					
					alert.showAndWait();
					
					event.consume();
				}
			}
		});
		
	}
	
	private Book validateAndCreate() throws Exception{
		String title = titleField.getText();
		if(title == null || title.equals("")){
			throw new Exception("Title is missing");
		}
		String isbn = isbnField.getText();
		if(isbn == null || isbn.equals("")){
			throw new Exception("ISBN is missing");
		}
		String edition = editionField.getText();
		if(edition == null || edition.equals("")){
			throw new Exception("Edition is missing");
		}
		int editionValue = 0;
		try {
			editionValue = Integer.parseInt(edition);
		} catch (Exception e) {
			throw new Exception("Edition is not a valid number",e);
		}
		String author = authorField.getText();
		if(author == null || author.equals("")){
			throw new Exception("Author is missing");
		}
		String price = priceField.getText();
		double priceValue = 0;
		if(price == null || price.equals("")){
			throw new Exception("Price is missing");
		}
		try {
			priceValue = Double.parseDouble(price);
		} catch (Exception e) {
			throw new Exception("Price is not valid number",e);
		}
		
		return new Book(title,isbn,editionValue,priceValue,new Author(author));
		
	}
	
}
