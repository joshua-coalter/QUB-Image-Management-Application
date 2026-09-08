package part01;

import java.util.ArrayList;

public class ImageAlbum {
	
	private ArrayList<ImageRecord> images;
	private int currentIndex;
	
	public ImageAlbum() {
		this.images = new ArrayList<>();
		currentIndex = -1;
	}
	
	/**
	 * Adds image to the ImageAlbum in date order (earliest first).
	 * @param image	The ImageRecord of the image to be added to the album
	 */
	public void addImage(ImageRecord image) {
		int index;
		for(index = 0; index < images.size(); index++) {
			if (image.getDateTaken().isBefore(images.get(index).getDateTaken())) {
				break;
			}
		}
		images.add(index, image);
	}
	
	/**
	 * Returns the first or ‘earliest’ image in an ImageAlbum, or null if there are no images in the album.
	 * @return first image's ImageRecord
	 */
	public ImageRecord getFirst() {
		if (images.size() == 0) {
			currentIndex = -1;
			return null;
		} else {
			currentIndex = 0;
			return images.get(currentIndex);
		}
	}
	
	/**
	 * Returns the image ‘next’ to the last image accessed, or null if no images in the album, or no ‘next’ image exists, or no images previously accessed.
	 * @return next image's ImageRecord
	 */
	public ImageRecord getNext() {
		if (images.size() == 0 || currentIndex == -1) {
			return null;
		} else if (currentIndex + 1 == images.size()) {
			return null;
		} else {
			currentIndex++;
			return images.get(currentIndex);
		}
	}
	
	/**
	 * Returns the image ‘previous’ to the last image accessed, or null if no images in the album, or no ‘previous’ image exists, or no images previously accessed.
	 * @return previous image's ImageRecord
	 */
	public ImageRecord getPrevious() {
		if (images.size() == 0 || currentIndex == -1) {
			return null;
		} else if (currentIndex - 1 == -1) {
			return null;
		} else {
			currentIndex--;
			return images.get(currentIndex);
		}
	}

	/**
	 * Returns the ImageAlbum size as an integer
	 * @return album size
	 */
	public int getSize() {
		return images.size();
	}
	
}
