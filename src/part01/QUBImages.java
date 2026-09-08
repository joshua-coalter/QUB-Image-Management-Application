package part01;

import java.time.LocalDate;
import java.util.Scanner;

public class QUBImages {
	// For keyboard input
	private static Scanner input = new Scanner(System.in);
	
	// Define a constant PROMPT
	static final String PROMPT = "----> ";
	
	// Define ImageManager
	private static ImageManager manager = new ImageManager();
		
	public static void main(String[] args) {
		loadExampleImages();
		
		// Initialise the menu
		String options[] = {"Add Image", "Search", "Display All", "Exit"};
		Menu mainMenu = new Menu("QUBImages", options);
		
		int choice; // The user's choice
		boolean finished = false; // controls main app loop
		do {
			// Use the menu to get the users' choice
			choice = mainMenu.getUserChoice();
			
			// Checks if user wants to quit
			if (choice == 4) {
				finished = true;
			} else {
				// Deal with the users' choice
				processChoice(choice);
			}
		} while(finished == false);
		
		input.close();
		System.out.println("\nApp Closed. Goodbye!");
	}

	/**
	 * Processes the user's menu choice
	 * @param choice - the selection made by the app user
	 */
	private static void processChoice(int choice) {
		switch(choice) {
			case 1 : addImage();
			break;
			case 2 : searchMenu();
			break;
			case 3 : displayAll();
			break;
			default: System.out.println("Option " + choice + " is invalid.");
		}
		System.out.println();
		
	}

	/**
	 * Attempts to add a new image to the system
	 */
	private static void addImage() {
		System.out.println("\n--- Add a new Image ---");
		
		// Get info for new image from user
		int id = manager.getNextId();
		
		System.out.print(PROMPT + "Enter title: ");
		String title = input.nextLine().trim();
		
		System.out.print(PROMPT + "Enter description: ");
		String description = input.nextLine().trim();
		
		ImageType genre = selectGenre();
        if (genre == null) return;
		
        LocalDate dateTaken = enterDate(PROMPT + "Enter date taken (YYYY-MM-DD): ");
        if (dateTaken == null) return;
		
		boolean validThumb = false;
        String thumbnail = "";
        do {
    		System.out.print(PROMPT + "Enter thumbnail filename: ");
			thumbnail = input.nextLine().trim();
			if (thumbnail.contains(".")) {
				validThumb = true;
			} else {
                System.out.println("Invalid filename. (must contain extension)");
			}
        } while (validThumb == false);
		
		// Create image record and display new image in console
		ImageRecord record = new ImageRecord(id, title, description, genre, dateTaken, thumbnail);
		manager.addImage(record);
		System.out.println("\nImage added successfully:");
		System.out.println(record);
	}

	/**
	 * Displays Genre selection options and lets user choose
	 * @return Selected genre
	 */
	private static ImageType selectGenre() {
        ImageType[] types = ImageType.values();
		String[] genreOptions = new String[types.length];
		for (int i = 0; i < types.length - 1; i++) {
			genreOptions[i] = types[i].name() + " - " + types[i].getDescription();
		}
		Menu genreMenu = new Menu("Genre Menu", genreOptions);
		int choice = genreMenu.getUserChoice();
		ImageType genre = types[choice];
		return genre;
    }
	
	/**
	 * Allows the user to input a valid date into the system
	 * @param prompt	the prompt string to the user before entering date (include formatting)
	 * @return the user input date
	 */
	private static LocalDate enterDate(String prompt) {
		System.out.print(prompt);
		LocalDate dateTaken = null;
        while (true) {
            String inputDate = input.nextLine().trim();
            try {
            	dateTaken = LocalDate.parse(inputDate);
                return dateTaken;
            } catch (Exception e) {
                System.out.print("Invalid date format. Please use YYYY-MM-DD: ");
            }
        }
	}
	
	/**
	 * Prints the Search Menu to console
	 */
	private static void searchMenu() {
		String[] searchOptions = {"Search by ID", "Search by Title", "Search by Description", "Search by Genre", "Search by Date Range", "Back to Main Menu"};
        Menu searchMenu = new Menu("Search Menu", searchOptions);
		
        int choice; // The user's choice
		boolean searching = true; // controls search
		while (searching) {
			choice = searchMenu.getUserChoice();
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
            case 6: searching = false; 
            break;
			}
		}
	}

	/**
	 * Searches and displays record of image matching user input ID
	 */
	private static void searchById() {
		System.out.print("\nEnter image ID to search: ");
		try {
            int id = Integer.parseInt(input.nextLine().trim());
            ImageRecord result = manager.searchId(id);
            if (result == null) {
                System.out.println("No image found with ID: " + id);
            } else {
                System.out.println("\n--- Search Result ---");
                System.out.println(result);
            }
        } catch (Exception e) {
            System.out.println("Invalid ID entered.");
        }
	}

	/**
	 * Searches and displays records of images containing user input string in title
	 */
	private static void searchByTitle() {
		System.out.print("\nEnter title keyword to search: ");
        String keyword = input.nextLine().trim();
        ImageAlbum album = manager.searchTitle(keyword);
        displayAlbum(album, "--- Title search: " + keyword + "\" ---");
	}

	/**
	 * Searches and displays records of images containing user input string in description
	 */
	private static void searchByDescription() {
		System.out.print("\nEnter description keyword to search: ");
        String keyword = input.nextLine().trim();
        ImageAlbum album = manager.searchDescription(keyword);
        displayAlbum(album, "--- Description search: \"" + keyword + "\" ---");
	}

	/**
	 * Searches and displays record of images matching user input genre
	 */
	private static void searchByGenre() {
		ImageType genre = selectGenre();
        if (genre == null) return;
        ImageAlbum album = manager.searchGenre(genre);
        displayAlbum(album, "Genre search: " + genre.name());
		
	}

	/**
	 * Searches and displays records of images within user input date range
	 */
	private static void searchByDateRange() {
		System.out.println("\n--- Search by Date Range ---");
		System.out.println();
        LocalDate start = enterDate(PROMPT + "Enter start date (YYYY-MM-DD): ");
        LocalDate end = enterDate(PROMPT + "Enter end date (YYYY-MM-DD): ");

        if (start.isAfter(end)) {
            System.out.println("Start date must not be after end date.");
            return;
        }

        ImageAlbum album = manager.searchDates(start, end);
        displayAlbum(album, "Date range: " + start + " to " + end);
		
	}

	/**
	 * Displays details of all images in the system
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
		System.out.println("\n--- " + albumName + " ---");
		
		ImageRecord current = album.getFirst();
        if (current == null) {
            System.out.println("No images found.");
            return;
        }

        // Count results
        int count = album.getSize();
        System.out.println(count + " image(s) found (displayed in date order, earliest first):\n");

        // Navigate and print all
        int num = 1;
        while (current != null) {
            System.out.println("[" + num + "] " + current);
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
		
		manager.addImage(new ImageRecord(3, "Kermit Plays Golf", "An image of Kermit the frog playing golf.",
	            ImageType.SPORT, LocalDate.of(2023, 3, 1), "KermitGolf.png"));
		
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
	            ImageType.OTHER, LocalDate.of(2023, 7, 1), "Andromeda.png"));
		
	}
}
