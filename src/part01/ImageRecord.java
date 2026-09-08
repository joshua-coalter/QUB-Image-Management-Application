package part01;

import java.time.LocalDate;

public class ImageRecord {

	private int id;
	private String title;
	private String description;
	private ImageType genre;
	private LocalDate dateTaken;
	private String thumbnail;
	
	/**
	 * Image Record constructor
	 * @param id	unique image ID
	 * @param title	title of image
	 * @param description description of image content
	 * @param genre genre of image from Enum
	 * @param dateTaken date image was photographed (YYYY-MM-DD)
	 * @param thumbnail	file name and extension as saved in Images folder
	 */
	public ImageRecord(int id, String title, String description, ImageType genre, LocalDate dateTaken, String thumbnail) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.genre = genre;
		this.dateTaken = dateTaken;
		this.thumbnail = thumbnail;
	}
	
	/**
	 * 
	 * @return image ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return image title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 
	 * @return image description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * @return image genre
	 */
	public ImageType getGenre() {
		return genre;
	}
	
	/**
	 * 
	 * @return image date taken
	 */
	public LocalDate getDateTaken() {
		return dateTaken;
	}
	
	/**
	 * 
	 * @return image thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}
	
	/**
	 * 
	 * @param title image title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 
	 * @param description image description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 
	 * @param genre image genre
	 */
	public void setGenre(ImageType genre) {
		this.genre = genre;
	}
	
	/**
	 * 
	 * @param dateTaken image date taken (YYYY-MM-DD)
	 */
	public void setDateTaken(LocalDate dateTaken) {
		this.dateTaken = dateTaken;
	}
	
	/**
	 * 
	 * @param thumbnail image thumbnail file name including extension
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	//A toString method which return a String (no line breaks) with details of all instance values, appropriately formatted.
	@Override
	public String toString() {
		String details = "ID: " + getId();
		details += " | Title: " + getTitle();
		details += " | Description: " + getDescription();
		details += " | Genre: " + getGenre();
		details += " | Date Taken: " + getDateTaken();
		details += " | Thumbnail: " + getThumbnail();
		return details;
	}
}
