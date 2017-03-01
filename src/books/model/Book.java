package books.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Comparable<Book>, java.io.Serializable {

	private String title;
	private String isbn;
	private int edition;
	private double price;
	private ArrayList<Author> authors;
	
	 /**
     * The constructor "Book" creates a new book by adding all information about
     * it.
     * 
     * @param title Title of the book.
     * @param ISBN ISBN of the book.
     * @param edition Edition of the book.
     * @param price  Price of the book.
     * @param a Author of the book.
     */
	public Book(String title, String isbn, int edition, double price, Author a){
		
		this.title = title;
		this.isbn = isbn;
		this.edition = edition;
		this.price = price;
		this.authors = new ArrayList<Author>();
		authors.add(a);
	}
	
	 /**
     * The constructor "Book" creates a new book by adding all information about
     * it.
     * 
     * @param title Title of the book.
     * @param isbn Isbn of the book.
     * @param edition Edition of the book.
     * @param price  Price of the book.
     * @param authors Authors of the book.
     */
	public Book(String title, String isbn, int edition, double price, ArrayList<Author> aut){
		
		this.title = title;
		this.isbn = isbn;
		this.edition = edition;
		this.price = price;
		this.authors = new ArrayList<Author>();
		for(Author a: aut){
			authors.add(a);
		}
		
	}
	
	/**
     * The method "getTitle" gets the title of a book object.
     * 
     * @return Returns the title of a book.
     */  
	public String getTitle(){
		return this.title;
	}
	
	/**
     * The method "getIsbn" gets the isbn of a book object.
     * 
     * @return Returns the isbn of a book.
     */  
	public String getIsbn(){
		return this.isbn;
	}
	
	/**
     * The method "getEdition" gets the edition of a book object.
     * 
     * @return Returns the edition of a book.
     */  
	public int getEdition(){
		return this.edition;
	}
	
	/**
     * The method "getPrice" gets the price of a book object.
     * 
     * @return Returns the price of a book.
     */  
	public double getPrice(){
		return this.price;
	}
	
	
	/**
     * The method "addAuthor" adds an author to a book object.
     * 
     * @param author Name of the author to be added.
     */
	public boolean addAuthor(Author a){
		
		for(Author author: authors){
			if((author.getName().trim()).equals((a.getName().trim()))){
				return false;
			}
		}
		return authors.add(a);
	}
	
	/**
     * The method "getAuthors" gets the authors of a book object.
     * 
     * @return Returns a list of authors for a book object.
     */
	public ArrayList<Author> getAuthors(){
		
		return new ArrayList<Author>(authors);
		
	}
	
	 /**
     * The method "compareTo" compares the title of
     * different books by title (it does this by their alphabetical order).
     * 
     * @param otehrBook The other book that is to be compared with.
     * @return Returns the books so that they are listed in alphabetical order
     * by theirs title.
     */
	public int compareTo(Book otherBook) {
		
		int ret = (this.getTitle().compareTo(otherBook.getTitle()));
		return ret;
	}
	
	/**
     * The method "toString" gets a list of existing books in book
     * register.
     * 
     * @return Returns a list of existing books, containing all the necessary information.
     */
	public String toString(){
		
		String info = "Title: " + this.title + ", ISBN: " + this.isbn + ", Authors: " + 
		this.getAuthors().toString() + ", Edition: " + this.edition + ", Price: " + price + "\n";  
		
		return info;
	}
	
	
}
