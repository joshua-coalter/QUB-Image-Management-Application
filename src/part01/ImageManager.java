package part01;

import java.time.LocalDate;
import java.util.ArrayList;

public class ImageManager {
	private ArrayList<ImageRecord> images;
	
	public ImageManager() {
		this.images = new ArrayList<ImageRecord>();
	}
	
	/**
	 * Adds a reference to an ImageRecord object to the collection.
	 * @param image	image to be added to the collection
	 */
	public void addImage(ImageRecord image) {
		images.add(image);
	}
	
	/**
	 * Returns a reference to an ImageRecord object (identified by id) or null if none found.
	 * @param id	integer id value to image search
	 * @return image with matching id as search value
	 */
	public ImageRecord searchId(int id) {
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getId() == id) {
				return images.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns a reference to an ImageAlbum instance for images with title containing the String str.
	 * @param str	string value to be searched for in image titles (case-insensitive)
	 * @return image album of all images with titles containing string
	 */
	public ImageAlbum searchTitle(String str) {
		ImageAlbum album = new ImageAlbum();
		String lower = str.toLowerCase();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getTitle().toLowerCase().contains(lower)) {
				album.addImage(images.get(i));
			}
		}
		return album;
	}
	
	/**
	 * Returns a reference to an ImageAlbum instance for images with description containing the String str.
	 * @param str	string value to be searched for in image descriptions (case-insensitive)
	 * @return image album of all images with descriptions containing string
	 */
	public ImageAlbum searchDescription(String str) {
		ImageAlbum album = new ImageAlbum();
		String lower = str.toLowerCase();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getDescription().toLowerCase().contains(lower)) {
				album.addImage(images.get(i));
			}
		}
		return album;
	}
	
	/**
	 * Returns a reference to an ImageAlbum instance for images which match type.
	 * @param type	genre of image
	 * @return image album of all images matching type
	 */
	public ImageAlbum searchGenre(ImageType type) {
		ImageAlbum album = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getGenre() == type) {
				album.addImage(images.get(i));
			}
		}
		return album;
	}
	
	/**
	 * Returns an ImageAlbum for images taken between start and end dates.
	 * @param start	search range start date
	 * @param end	search range end date
	 * @return image album of all images within range
	 */
	public ImageAlbum searchDates(LocalDate start, LocalDate end) {
		ImageAlbum album = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getDateTaken().isAfter(start) && images.get(i).getDateTaken().isBefore(end)) {
				album.addImage(images.get(i));
			}
		}
		return album;
	}
	
	/**
	 * Returns a reference to an ImageAlbum instance for all images managed by an ImageManager object.
	 * @return image album of all images in collection
	 */
	public ImageAlbum getAllImages() {
		ImageAlbum album = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			album.addImage(images.get(i));
		}
		return album;
	}

	/**
	 * Returns the next imageID as an integer.
	 * @return next image's ID
	 */
	public int getNextId() {
		int maxId = 0;
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getId() > maxId) {
				maxId = images.get(i).getId();
			}
		}
		return maxId + 1;
	}
	
}
