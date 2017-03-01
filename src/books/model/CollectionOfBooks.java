package books.model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class CollectionOfBooks{

	private ArrayList<Book> books;
	
	/**
     * This constructor creates an empty list called books for storing book objects.
     */
	public CollectionOfBooks(){
		
		books = new ArrayList<Book>();
	}
	
	/**
     * @Returns the number of elements in this list. 
     */
	public int noOfElements(){
		return books.size();
	}
	
	/**
	 * The method "addBook" appends the specified element to the end of this books list.
	 * @param newBook Represents the book object that is to be added to books list.
	 */
	public boolean addBook(Book newBook){
		
		for(int i = 0; i < books.size(); i++){
			if((books.get(i).getIsbn()).equals(newBook.getIsbn())){
				return false;
			}
		}
		books.add(newBook);
		Collections.sort(books);
		return true;
	}
	
	/**
	 * The method "removeBook" removes the first occurrence of the specified element from this list, if it is present.
	 * If the list does not contain the element, it is unchanged. More formally, removes the element with the lowest index 
	 * @param bookToRemove Represents the book object that is to be deleted from books list.
	 */
	public void removeBook(Book bookToRemove){
		
		for(Book book: books){
			if(book.equals(bookToRemove)){
				books.remove(book);
				return;
			}
		}
	}
	
	/**
	 * The method "removeByTitle" removes the specified element matching the title from this list, if it is present.
	 * If the list does not contain the element, it is unchanged.
	 * @param titleToRemove Represents the title object that is to be deleted from books list.
	 * @return True or false is returned depending on if book object with specified title exists in the books list.
	 */
	public boolean removeByTitle(String titleToRemove){
			
		for(Book b: books){
			if(b.getTitle().equalsIgnoreCase(titleToRemove.trim())){
				books.remove(b);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * The method "searchByTitle" searches the specified element matching the title from this list, if it is present.
	 * If the list does not contain the element, it is unchanged.
	 * @param title Represents the title object that is to be added to temp list.
	 * @return An ArrayList with specified title is returned. 
	 */
	public ArrayList<Book> searchByTitle(String title){
		
		ArrayList<Book> temp = new ArrayList<Book>();
		for(Book b: books){
			if((b.getTitle().indexOf(title)) != -1){
				temp.add(b);
			}
		}		
		return temp;
	}
	
	/**
	 * The method "searchByIsbn" searches the specified element matching the title from this list, if it is present.
	 * @param isbn Represents the isbn that is to be added to temp list.
	 * @return An ArrayList with specified isbn is returned. 
	 */
	public ArrayList<Book> searchByIsbn(String isbn){
		
		ArrayList<Book> temp = new ArrayList<Book>();
		
		for(Book b: books){
			if((b.getIsbn().indexOf(isbn.trim())) != -1){
				temp.add(b);
			}		
		}
		return temp;
	}
	
	/**
	 * The method "searchByAuthor" searches the specified element matching the name of author in this list, if it is present.
	 * @param nameOfAuthor Represents the author name that is to be compared and added to temp list.
	 * @return An ArrayList with specified book object that contains matching author name is returned.  
	 */
	public ArrayList<Book> searchByAuthor(String nameOfAuthor){
		
		ArrayList<Book> temp = new ArrayList<Book>();
		
		for(Book b: books){
			for(Author author: b.getAuthors()){
				if(author.getName().indexOf(nameOfAuthor) != -1){
					temp.add(b);
					continue;
				}
			}
		}
		return temp;
	}
	
	
	public ArrayList<Book> getBooks(){
		return books;
	}
	
	public void clearList(){
		books.clear();
	}

	
	/**
	 * Call this method before the application exits, to store the books, 
	 * in serialized form, on file the specified file.
	 */
	public void closeTheBookRegister(String filename) throws IOException {
		
		ObjectOutputStream out = null;
		
		try {
			FileOutputStream fout = new FileOutputStream(filename);
			out = new ObjectOutputStream(fout);
			out.writeObject(books);
		}finally {
			try {
				if(out != null)	out.close();
				
			} catch(Exception e) {}
		}
	}
	
	/**
	 * Call this method at startup of the application, to deserialize the books
	 * from file the specified file.
	 */
	@SuppressWarnings("unchecked")
	public void openTheBookRegister(String filename) throws IOException, ClassNotFoundException {
		
		ObjectInputStream in = null;
		
		try {
			in = new ObjectInputStream(new FileInputStream(filename));
			// readObject returns a reference of type Object, hence the down-cast
			books = (ArrayList<Book>) in.readObject();
			
			for(Book b: books){
				b.toString();
			}
		}catch (ClassNotFoundException e) {
			 //System.out.println("The class for this type of objects does " + "not exist in this application!");
			 throw new IOException("Invalid file format",e);
		} catch(EOFException ex) {
			 throw new IOException("Input file is empty",ex);
		}finally {
			try {
				if(in != null)	in.close();
			} catch(Exception e) {}			
		}
	}
	
	
}