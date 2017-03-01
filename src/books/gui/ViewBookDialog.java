package books.gui;

import books.model.Author;
import books.model.Book;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ViewBookDialog extends Dialog<ButtonType> {

	public ViewBookDialog(Book book){
		
		super();
		
		setTitle("View book");
		setHeaderText("View details of book "+book.getTitle());
		
		GridPane layout = new GridPane();
		
		Label titleLabel = new Label("Title");
		Label isbnLabel = new Label("ISBN");
		Label editionLanbel = new Label("Edition");
		Label priceLabel = new Label("Price");
		
		Label titleField = new Label();
		titleField.setText(book.getTitle());
		Label isbnField = new Label();
		isbnField.setText(book.getIsbn());
		Label editionField = new Label();
		editionField.setText(book.getEdition()+"");
		Label priceField = new Label();
		priceField.setText(""+book.getPrice());

		layout.setHgap(7D);
		layout.setVgap(8D);
		
		GridPane.setConstraints(titleLabel, 0, 0);
		GridPane.setConstraints(isbnLabel, 0, 1);
		GridPane.setConstraints(editionLanbel, 0, 2);
		GridPane.setConstraints(priceLabel, 0, 3);
		
		
		GridPane.setConstraints(titleField, 1, 0);
		GridPane.setConstraints(isbnField, 1, 1);
		GridPane.setConstraints(editionField, 1, 2);
		GridPane.setConstraints(priceField, 1, 3);
		
		
		
		layout.getChildren().addAll(titleLabel,isbnLabel,editionLanbel,priceLabel,
				titleField,isbnField,editionField,priceField);
		
		int i = 4;
		
		for(Author author: book.getAuthors()){
			
			Label authorLabel = new Label("Author "+(i-3));
			Label authorField = new Label();
			authorField.setText(author.getName());
			GridPane.setConstraints(authorLabel, 0, i);
			GridPane.setConstraints(authorField, 1, i);
			i++;
			layout.getChildren().addAll(authorLabel,authorField);
		}
		
		getDialogPane().getButtonTypes().add(ButtonType.OK);

		getDialogPane().setContent(layout);
		
	}
	
	
	
}
