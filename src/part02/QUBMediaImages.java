package part02;

import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.time.LocalDate;

import part01.ImageAlbum;
import part01.ImageManager;
import part01.ImageRecord;
import part01.ImageType;

import console.Console;

public class QUBMediaImages {
	// Define Colours
	private static final Color COLOUR_BG = new Color(28, 28, 42);
	private static final Color COLOUR_TITLE = new Color(255, 193, 7);
	private static final Color COLOUR_HEADING = new Color(160, 160, 180);
	private static final Color COLOUR_SUBHEADING = new Color(100, 100, 120);
	private static final Color COLOUR_BODY = new Color(240, 240, 250);
	private static final Color COLOUR_ERROR = new Color(220, 80, 80);
	
	// Define Fonts
	private static final Font FONT_TITLE = new Font("Courier", Font.BOLD, 50);
	private static final Font FONT_HEADING = new Font("Courier", Font.BOLD, 30);
	private static final Font FONT_SUBHEADING = new Font("Courier", Font.BOLD, 25);
	private static final Font FONT_BODY = new Font("Courier", Font.PLAIN, 20);
	private static final Font FONT_BODY_BOLD = new Font("Courier", Font.BOLD, 20);
	private static final Font FONT_ERROR = new Font("Courier", Font.PLAIN, 16);
	
	// Define a constant PROMPT
	private static final String PROMPT = "----> ";
	
	// Define Console instance
	private static Console console;
		
	// Define ImageManager
	private static ImageManager manager = new ImageManager();
	
	public static void main(String[] args) {
		// Load Example Images to console
		loadExampleImages();
		
		// Initialise the menu
		String options[] = {"Add Image", "Search", "Display All", "Exit"};
		
		// Initiate consoles
		console = new Console(true);
		
		console.setSize(780, 440);
		console.setVisible(true);
		console.setBgColour(COLOUR_BG);
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
		
		int choice; // The user's choice
		boolean finished = false; // controls main console loop
		do {
			// Use the menu to get the users' choice
			console.setColour(COLOUR_TITLE);
			console.setFont(FONT_TITLE);
			choice = menu("QUBImages", options);
			
			// Checks if user wants to quit
			if (choice == 4) {
				finished = true;
			} else {
				// Deal with the users' choice
				processChoice(choice);
			}
		} while(finished == false);
		
		console.println("\nConsole Closed. Goodbye!");
	}

	/**
	 * Displays a menu of options to console and allows the user to choose one.
	 * @param title	name of menu as string
	 * @param items	array of options to choose in the menu
	 * @return
	 */
	private static int menu(String title, String items[]) {
		// Display menu title
		for(int count=0;count<title.length();count++) {
			console.print("--");
		}
		console.println("\n" + title);
		for(int count=0;count<title.length();count++) {
			console.print("--");
		}
		console.println();
		
		// Display options
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
		for(int option=1; option<=items.length; option++) {
			console.println(option + ". " + items[option-1] );
		}
		console.println();
		
		// Prompt and get user selection
		boolean valid = false;
		int value = -1;
		do {
			console.setColour(COLOUR_BODY);
			console.setFont(FONT_BODY);
            console.print(PROMPT + "Enter Selection: ");
			try {
				value = Integer.parseInt(console.readLn().trim());
				valid = true;
			} catch (Exception e){
				console.setColour(COLOUR_ERROR);
    			console.setFont(FONT_ERROR);
                console.println("Invalid selection.");
			}
		} while (valid == false);
		return value;
	}

	/**
	 * Processes the user's menu choice
	 * @param choice - the selection made by the console user
	 */
	private static void processChoice(int choice) {
		switch(choice) {
			case 1 : addImage();
			break;
			case 2 : searchMenu();
			break;
			case 3 : displayAll();
			break;
			default: 
				console.setColour(COLOUR_ERROR);
				console.setFont(FONT_ERROR);
				console.println("Option \"" + choice + "\" is invalid.");
		}
		console.println();
	}

	/**
	 * Attempts to add a new image to the system
	 */
	private static void addImage() {
		console.setColour(COLOUR_HEADING);
		console.setFont(FONT_HEADING);
		console.println("\n--- Add a new Image ---");
		
		// Get info for new image from user
		int id = manager.getNextId();
		
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
		console.print(PROMPT + "Enter title: ");
		String title = console.readLn().trim();
		
		console.print(PROMPT + "Enter description: ");
		String description = console.readLn().trim();
		
		ImageType genre = selectGenre();
        if (genre == null) return;
		
        LocalDate dateTaken = enterDate(PROMPT + "Enter date taken (YYYY-MM-DD): ");
        if (dateTaken == null) return;
		
        boolean validThumb = false;
        String thumbnail = "";
        do {
        	console.setColour(COLOUR_BODY);
    		console.setFont(FONT_BODY);
    		console.print(PROMPT + "Enter thumbnail filename: ");
			thumbnail = console.readLn().trim();
			if (thumbnail.contains(".")) {
				validThumb = true;
			} else {
				console.setColour(COLOUR_ERROR);
    			console.setFont(FONT_ERROR);
                console.println("Invalid filename. (must contain extension)");
			}
        } while (validThumb == false);
		
		// Create image record and display new image in console
		ImageRecord record = new ImageRecord(id, title, description, genre, dateTaken, thumbnail);
		manager.addImage(record);
		console.println("\nImage added successfully:");
		printRecord(record);
	}

	/**
	 * Prints an individual record of an image including all data and thumbnail to console
	 * @param record	image to be printed
	 */
	private static void printRecord(ImageRecord record) {
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
		console.println("  ID: " + record.getId());
		
		ImageIcon thumb = new ImageIcon("Images/" + record.getThumbnail());
		if (thumb.getIconWidth() > 0) {
			console.print("  ");
			console.print(thumb);
		} else {
			console.setColour(COLOUR_ERROR);
			console.setFont(FONT_ERROR);
			console.print("No file found in folder with filename: " + record.getThumbnail());
		}
	
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY_BOLD);
		console.print("\n  Title: ");
		console.setFont(FONT_BODY);
		console.println(record.getTitle());
		
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY_BOLD);
		console.print("  Description: ");
		console.setFont(FONT_BODY);
		console.println(record.getDescription());
		
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY_BOLD);
		console.print("  Genre: ");
		console.setFont(FONT_BODY);
		console.println(record.getGenre().name() + " - " + record.getGenre().getDescription());
		
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY_BOLD);
		console.print("  Date Taken: ");
		console.setFont(FONT_BODY);
		console.println(record.getDateTaken());
		
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY_BOLD);
		console.print("  Thumbnail: ");
		console.setFont(FONT_BODY);
		console.println(record.getThumbnail());
	}

	/**
	 * Displays Genre selection options and lets user choose
	 * @return Selected genre
	 */
	private static ImageType selectGenre() {
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
        ImageType[] types = ImageType.values();
		String[] genreOptions = new String[types.length];
		for (int i = 0; i < types.length; i++) {
			genreOptions[i] = types[i].name() + " - " + types[i].getDescription();
		}
		boolean valid = false;
		ImageType genre = null;
		int choice = menu("Genre Menu", genreOptions);
		do {
			try {
				genre = types[choice - 1];
				valid = true;
				return genre;
			} catch (Exception e) {
            	console.setColour(COLOUR_ERROR);
    			console.setFont(FONT_ERROR);
                console.println("Invalid selection.");
                console.setColour(COLOUR_BODY);
    			console.setFont(FONT_BODY);
                console.print(PROMPT + "Enter Selection: ");
                choice = Integer.parseInt(console.readLn().trim());
            }
		} while (valid == false);
		return genre;
    }
	
	/**
	 * Allows the user to input a valid date into the system
	 * @param prompt	the prompt string to the user before entering date (include formatting)
	 * @return the user input date
	 */
	private static LocalDate enterDate(String prompt) {
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
		console.print(prompt);
		LocalDate dateTaken = null;
        while (true) {
            String inputDate = console.readLn().trim();
            try {
            	dateTaken = LocalDate.parse(inputDate);
                return dateTaken;
            } catch (Exception e) {
            	console.setColour(COLOUR_ERROR);
    			console.setFont(FONT_ERROR);
                console.println("Invalid date format.");
                console.setColour(COLOUR_BODY);
        		console.setFont(FONT_BODY);
                console.print(PROMPT + "Enter date taken (YYYY-MM-DD): ");
            }
        }
	}
	
	/**
	 * Prints the Search Menu to console
	 */
	private static void searchMenu() {
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
		String[] searchOptions = {"Search by ID", "Search by Title", "Search by Description", "Search by Genre", "Search by Date Range", "Back to Main Menu"};
		
        int choice; // The user's choice
		choice = menu("Search Menu", searchOptions);
		switch (choice) {
		case 1: searchById(); 
		break;
        case 2: searchByTitle(); 
        break;
        case 3: searchByDescription(); 
        break;
        case 4: searchByGenre(); 
        break;
        case 5: searchByDateRange(); 
        break;
        default: console.println("Option \"" + choice + "\" is invalid.");
        break;
		}
	}

	/**
	 * Searches and displays record of image matching user input ID
	 */
	private static void searchById() {
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
		console.print("\nEnter image ID to search: ");
		try {
            int id = Integer.parseInt(console.readLn().trim());
            ImageRecord record = manager.searchId(id);
            if (record == null) {
            	console.setColour(COLOUR_ERROR);
    			console.setFont(FONT_ERROR);
                console.println("No image found with ID: " + id);
            } else {
            	console.setColour(COLOUR_HEADING);
        		console.setFont(FONT_HEADING);
                console.println("\n--- Search Result ---");
                console.setColour(COLOUR_BODY);
        		console.setFont(FONT_BODY);
                printRecord(record);
            }
        } catch (Exception e) {
        	console.setColour(COLOUR_ERROR);
			console.setFont(FONT_ERROR);
            console.println("Invalid ID entered.");
        }
	}

	/**
	 * Searches and displays records of images containing user input string in title
	 */
	private static void searchByTitle() {
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
		console.print("\nEnter title keyword to search: ");
        String keyword = console.readLn().trim();
        ImageAlbum album = manager.searchTitle(keyword);
        displayAlbum(album, "--- Title search: " + keyword + "\" ---");
	}

	/**
	 * Searches and displays records of images containing user input string in description
	 */
	private static void searchByDescription() {
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
		console.print("\nEnter description keyword to search: ");
        String keyword = console.readLn().trim();
        ImageAlbum album = manager.searchDescription(keyword);
        displayAlbum(album, "--- Description search: \"" + keyword + "\" ---");
	}

	/**
	 * Searches and displays records of images matching user input genre
	 */
	private static void searchByGenre() {
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
		ImageType genre = selectGenre();
        if (genre == null) return;
        ImageAlbum album = manager.searchGenre(genre);
        displayAlbum(album, "Genre search: " + genre.name());
	}

	/**
	 * Searches and displays records of images within user input date range
	 */
	private static void searchByDateRange() {
		console.setColour(COLOUR_BODY);
		console.setFont(FONT_BODY);
		console.println("\n--- Search by Date Range ---");
		console.println();
        LocalDate start = enterDate(PROMPT + "Enter start date (YYYY-MM-DD): ");
        LocalDate end = enterDate(PROMPT + "Enter end date (YYYY-MM-DD): ");

        // Display error if start date is after end date
        if (start.isAfter(end)) {
        	console.setColour(COLOUR_ERROR);
			console.setFont(FONT_ERROR);
            console.println("Start date must not be after end date.");
            return;
        }

        ImageAlbum album = manager.searchDates(start, end);
        displayAlbum(album, "Date range: " + start + " to " + end);
		
	}

	/**
	 * Displays records of all images in the system
	 */
	private static void displayAll() {
		ImageAlbum album = manager.getAllImages();
		displayAlbum(album, "All Images");
		
	}
	
	/**
	 * Displays a referenced Images Album and its Images
	 * @param album	referenced Image Album
	 * @param albumName	the name of the album
	 */
	private static void displayAlbum(ImageAlbum album, String albumName) {
		console.setColour(COLOUR_HEADING);
		console.setFont(FONT_HEADING);
		console.println("\n--- " + albumName + " ---");
		
		// Check if album is empty
		ImageRecord current = album.getFirst();
        if (current == null) {
        	console.setColour(COLOUR_ERROR);
			console.setFont(FONT_ERROR);
            console.println("No images found.");
            return;
        }

        // Count and display results
        int count = album.getSize();
        console.setColour(COLOUR_SUBHEADING);
		console.setFont(FONT_SUBHEADING);
        console.println(count + " image(s) found (displayed in date order, earliest first):");

        // Navigate and print all
        int num = 1;
        while (current != null) {
        	console.setColour(COLOUR_BODY);
    		console.setFont(FONT_BODY_BOLD);
            console.print("\n[" + num + "] ");
            printRecord(current);
            current = album.getNext();
            num++;
        }
	}

	/**
	 * Loads example images into the image manager
	 */
	private static void loadExampleImages() {
		manager.addImage(new ImageRecord(1, "Andromeda Galaxy", "Image of the Andromeda galaxy.",
				ImageType.ASTRONOMY, LocalDate.of(2023, 1, 1), "Andromeda.png"));
		
		manager.addImage(new ImageRecord(2, "Lanyon QUB", "An image of the QUB Lanyon building.",
	            ImageType.ARCHITECTURE, LocalDate.of(2023, 2, 1), "LanyonQUB.png"));
		
		manager.addImage(new ImageRecord(3, "Bart Plays Basketball", "An image of Bart Simpson playing basketball.",
	            ImageType.SPORT, LocalDate.of(2023, 3, 1), "BartBasketball.png"));
		
		manager.addImage(new ImageRecord(4, "Mourne Mountains", "A panoramic view of the Mourne mountains.",
	            ImageType.LANDSCAPE, LocalDate.of(2023, 4, 1), "Mournes.png"));
		
		manager.addImage(new ImageRecord(5, "Homer Simpson", "Homer Simpson- A portrait of the man.",
	            ImageType.PORTRAIT, LocalDate.of(2023, 3, 1), "Homer.png"));
		
		manager.addImage(new ImageRecord(6, "Red Kite", "A Red Kite bird of prey in flight.",
	            ImageType.NATURE, LocalDate.of(2022, 4, 1), "RedKite.png"));
		
		manager.addImage(new ImageRecord(7, "Central Park", "An overhead view of Central Park New York USA.",
	            ImageType.AERIAL, LocalDate.of(2023, 5, 1), "CentralPark.png"));
		
		manager.addImage(new ImageRecord(8, "Apples", "A bunch of apples.",
	            ImageType.FOOD, LocalDate.of(2023, 6, 1), "Apples.png"));
		
		manager.addImage(new ImageRecord(9, "Programming Meme", "A Chat GPT programming meme.",
	            ImageType.OTHER, LocalDate.of(2023, 7, 1), "ChatGPT.png"));
		
	}

}

	

