package books.gui;

/** För inläsning vid val av meny-alternativ använder jag
 *	genomgående scan.nextLine(). När jag vill se ett 
 *	enskilt tecken använder jag charAt(int index) från 
 *	String-klassen.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import books.model.Author;
import books.model.Book;
import books.model.CollectionOfBooks;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class UserInterface {

	private Scanner scan;
	private CollectionOfBooks list;
	private ArrayList<Author> listOfAuthors;
	private Author a;

    public UserInterface() throws ClassNotFoundException, IOException{
    	scan = new Scanner(System.in);
    	list = new CollectionOfBooks();
    	listOfAuthors = new ArrayList<Author>();
    	list.openTheBookRegister("bokregister.ser");
    }
    
    public CollectionOfBooks getCollection(){
    	return list;
    }
    
    // Huvudloopen för menyn
    public void run() throws IOException, ClassNotFoundException {
    	
  	
    	char choice = ' ';
    	String answer;
    	
    	do {
    		printMenu();
    		answer = scan.nextLine();
    		answer = answer.toUpperCase();
    		
    		choice = answer.charAt(0); // Första tecknet i svaret
    		
    		switch(choice) {
    			case 'A':	läggTillBöcker(); break;
    			case 'B':	taBortBöcker(); break;
    			case 'C':	sökEfterTitel(); break;
    			case 'D':	sökEfterFörfattare(); break;
    			case 'E':	läggTillFörfattare(); break;
    			case 'F':	sökEfterISBN(); break;
    			case 'G':	listaAllaBöcker(); break;
    			case 'H':	raderaListan();break;
    			case 'X':	avsluta();break;
    			default: 	System.out.println("Ogiltigt kommando!");
    		}
    		
    	} while(choice != 'X');
    }
    
    public void läggTillBöcker() throws IOException {
    	
    	char end = '\n';
    	String str;
    	String isbn;
    	int edition;
    	double price;
    	String author;
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	System.out.println("Ange titel till boken som ska registreras: "); 
    	System.out.println("Tryck på enter för att avsluta.");
    	do { 
			str = br.readLine(); 
		} while(str.equals(end));
    	
    	System.out.println("En bok kan ha flera författare.");
    	System.out.println("Ange antal författare till boken som ska registreras: ");
    	Scanner scan = new Scanner(System.in);
    	int scanInt = 0;
    	scanInt = scan.nextInt();
    	while(scanInt < 1){
    		System.out.println("Antal författare måste vara minst ett.");
    		System.out.println("Ange nytt antal författare.");
        	scanInt = scan.nextInt();
    	}
    	     	
        for(int i = 0; i < scanInt; i++){
        	System.out.println("Ange namn till författare nr." + (i + 1) + ": ");
        	System.out.println("Tryck sedan enter för att avsluta.");
        	do { 
        		author = br.readLine(); 
        	} while(author.equals(end));
        	author.toLowerCase();
        	a = new Author(author.trim());
        	listOfAuthors.add(a);
    	}
    	
    	//////matning av ISBN/////////////	    		
    	System.out.println("Ange bokens ISBN-nummer."); 
        System.out.println("Tryck sedan enter för att avsluta."); 
        	do { 
        		isbn = br.readLine(); 
        	} while(isbn.equals(end));
        	isbn.trim();
        	
        while(!(list.searchByIsbn(isbn.trim()).isEmpty())){
        		System.out.println("Registret innehåller redan böcker med den angivna ISBN.");
        		System.out.println("Ange ett annat ISBN..");
        		do { 
        			isbn = br.readLine(); 
        		} while(str.equals(end));
        	}	
        
        System.out.println("Ange bokens upplaga."); 
        System.out.println("Tryck sedan enter för att avsluta."); 
        edition = scan.nextInt();
        while(edition < 1){
            System.out.println("Upplaga måste vara större än 0.");
            System.out.println("Ange annat upplaganummer: ");
            edition = scan.nextInt();
        }
        
        System.out.println("Ange bokens pris."); 
        System.out.println("Tryck sedan enter för att avsluta."); 
        price = scan.nextDouble();
        while(price < 1){
            System.out.println("Priset måste ha ett positivt värde.");
            System.out.println("Ange annat pris: ");
            price = scan.nextDouble();
        }
        Book b = new Book(str,isbn,edition,price,listOfAuthors);  
    	/*Book b = new Book(str,isbn,edition,price,listOfAuthors.get(0));
    	for(int i = 1; i < listOfAuthors.size(); i++){
    		b.addAuthor(listOfAuthors.get(i));
    	}*/
    	if(list.addBook(b)){
    		System.out.println("Boken har nu lagts in i registret!");
    	}else{
    		System.out.println("There already exist book with same title!");
			System.out.println("Type some other book titel!");
    	}

    	listOfAuthors.clear();
    	 
    }
    
    // Definierar ett annat "uppdrag" //  //  //  //  //  //  //  //  //  //  //  //  //  // 
    public void taBortBöcker() throws IOException, ClassNotFoundException {
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	String str;
    	char end = '\n';
    	
    	System.out.println("-----Nuvarande status i bokregistret-----");
    	if(list.getBooks().isEmpty()){
    		System.out.println("The register is empty. Add some books.");
    	}
    	else{
    		for(Book b: list.getBooks()){
    			System.out.println(b.toString());
    		}
    		System.out.println("\n" + "Ange titel som ska tas bort:" + "\n");
    		do { 
    			str = br.readLine(); 
    		} while(str.equals(end));
    		
    		if(list.removeByTitle(str.trim())){
    			System.out.println("\n" + "The book is succesfully removed.");
    			System.out.println("-----Nuvarande status i bokregistret-----");
    			for(Book b: list.getBooks()){
        			System.out.println(b.toString());
        		}
    		}
    		else{
    			System.out.println("Det finns ingen bok med den angivna titeln.");
    			System.out.println("Ange en annan titel.");
    		}
    			
    	}
    	 	
    }
    
    public void sökEfterTitel() throws IOException {
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	ArrayList<Book> temp;
    	String title;
    	char end = '\n';
    	System.out.println("\n" + "Ange titel som ska sökas efter:" + "\n");
    	do { 
			title = br.readLine(); 
		} while(title.equals(end));
    	title = title.trim();
    	temp = list.searchByTitle(title.trim());
    	
    	if(!(temp.isEmpty())){
    		for(Book b: temp){
    			System.out.println("Följande bok matchar den angivna titeln: ");
    			System.out.println(b.toString());
    		}
    	}
    	else{
    		System.out.println("Det finns inga böcker som matchar den angivna titeln.");
    	}
    }
    
    public void läggTillFörfattare() throws IOException{
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	String bokName;
    	String author;
    	char end = '\n';
    	ArrayList<Book> temp;
    	System.out.println("-----Nuvarande status i bokregistret-----");
		for(Book b: list.getBooks()){
			System.out.println(b.toString());
		}
		System.out.println("Ange titel vars författare ska uppdateras: ");
    	do { 
			bokName = br.readLine(); 
		} while(bokName.equals(end));
    	
    	temp = list.searchByTitle(bokName.trim());
    	
    	if(!(temp.isEmpty())){
    		System.out.println( "Ange författarens namn: ");
    		do { 
    			author = br.readLine(); 
    		} while(author.equals(end));
    		for(Book b: temp){
    			if(b.addAuthor(new Author(author.trim()))){
    				System.out.println("Den angivna författaren finns redan registrerad för denna bok.");
    				System.out.println("Var vänlig och prova med ett annat författarnamn.");
    			}
    		}
    	}
    	else{
    		System.out.println("-------------------Registret saknar böcker med det angivna författarnamnet-------------");
    	}
    	for(Book b: list.getBooks()){
			System.out.println(b.toString());
		}		
    } 
    
    
    public void sökEfterFörfattare() throws IOException {
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	String str;
    	char end = '\n';
    	System.out.println("\n" + "Ange namn som ska sökas efter: " + "\n");
    	do { 
			str = br.readLine(); 
		} while(str.equals(end));
    	str.trim();
    	
    	if(list.searchByAuthor(str).isEmpty()){
    		System.out.println("--------------Det finns inga böcker som är skrivna av den angivna författaren--------------");
    		return;
    	}
    	else{
    		System.out.println("------------------Nedanstående böcker är skrivna av den angivna författaren-----------------" + "\n");
    		for(Book b: list.searchByAuthor(str)){
    		System.out.println(b.toString());
    		}
    		return;
    	}
    }

    public void sökEfterISBN() throws IOException{
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	ArrayList<Book> temp;
    	String isbn;
    	char end = '\n';
    	System.out.println("\n" + "Ange ISBN som ska sökas efter:" + "\n");
    	do { 
			isbn = br.readLine(); 
		} while(isbn.equals(end));
    	isbn = isbn.trim();
    	temp = list.searchByIsbn(isbn);
    	
    	if(!(temp.isEmpty())){
    		for(Book b: temp){
    			System.out.println("-------------------Boken med det angivna ISBN är----------------------");
    			System.out.println("");
    			System.out.println(b.toString());
    		}
    	}
    	else{
    		System.out.println("-------------Det finns inga böcker med det angivna ISBN-----------------");
    	}
    }
    
    public void listaAllaBöcker(){
    	if(list.getBooks().isEmpty()){
    		System.out.println("---------------------Registret är tomt---------------------");
    		System.out.println("-------------------Lägg till nya böcker--------------------");
    	}
    	else{
    		System.out.println("");
    		System.out.println("---------------------Bokregistret innehåller nedanstående böcker---------------------");
    		System.out.println("");
    		for(Book b: list.getBooks()){
    			System.out.println(b.toString());
    		}
    	}
    }
    
    public void raderaListan(){
    	Scanner scan = new Scanner(System.in);
    	int delete;
    	System.out.println("Är du säker på att du vill tömma registret?");
    	System.out.println("För att ångra tryck '0' därefter tryck enter.");
    	System.out.println("För att tömma registret tryck '1' därefter tryck enter.");
    	
    	delete = scan.nextInt();
    	if(delete == 0){
    		return;
    	}else if(delete == 1){
    		list.clearList();
    		System.out.println("Listan har tömts.");
    		return;
    	}else{
    		System.out.println("Ogilltigt kommando.");
    		return;
    	}
    }
    
    public void avsluta() throws IOException{
   
    	list.closeTheBookRegister("bokregister.ser");    
    	System.out.println("Bye, bye.");
    	System.out.println("Bokregistret är nu stängt.");
    }
    
    
    public void printMenu() {
    	System.out.println("---Menu---");
    	System.out.println("A Lägg till böcker.");
    	System.out.println("B Ta bort böcker.");
    	System.out.println("C Sök efter titel.");
    	System.out.println("D Sök efter författare.");
    	System.out.println("E Lägg till författare.");
    	System.out.println("F Sök efter ISBN.");
    	System.out.println("G Lista alla böcker.");
    	System.out.println("H Töm registret.");
    	System.out.println("X Exit");
    	System.out.println("----------");
    }
    

	
    
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
    	
    	UserInterface menu = new UserInterface();
    	//launch(args);
    	menu.run();
    	
    }

    /*@Override
	public void start(Stage primaryStage) throws Exception {

        BorderPane border = new BorderPane();
       
        
        Label topLabel = new Label("Menu");
        topLabel.setAlignment(Pos.TOP_CENTER);
        
        topLabel.setPadding(new Insets(5,5,5,5));
    
        
        border.setTop(topLabel);
        
        FlowPane bottomPane = new FlowPane();
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setHgap(10);
        bottomPane.setPadding(new Insets(5,5,5,5));
        bottomPane.getChildren().add(new Button("Hit me!"));
        bottomPane.getChildren().add(new Button("No, hit me!"));
        border.setBottom(bottomPane);
        
        TextArea textArea = new TextArea("Bokregister\n");
        textArea.setPrefRowCount(10);
        textArea.setPrefColumnCount(40);
        textArea.setWrapText(true);
        border.setCenter(textArea);

        Scene scene = new Scene(border);

        primaryStage.setTitle("Book Register");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //for(int i = 0; i < 5; i++) {
            textArea.appendText(list.toString());
        //}
	}*/
}