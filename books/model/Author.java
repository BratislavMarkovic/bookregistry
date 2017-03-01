package books.model;

import java.io.Serializable;

public class Author implements java.io.Serializable{

	private String name;
	
	/**
     * The constructor "Author" creates an author object for a book object.
     * @param name Name of the author.
     */
	public Author(String name){
		this.name = name;
	}
	
	/**
     * The method "getName" returns gets the name of the author object.
     * @return Returns the name of the author object.
     */
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
