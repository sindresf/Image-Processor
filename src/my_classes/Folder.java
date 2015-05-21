package my_classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Folder {

	public static ArrayList<File> getFiles(String folderPath) {
		System.out.println("getting image files from " + folderPath);
		ArrayList<File> files = new ArrayList<>();
		File folder = new File(folderPath);
		File[] fileArray = folder.listFiles();
		for (File file : fileArray)
			files.add(file);
		return files;
	}

	public static ArrayList<BufferedImage> getImageFiles(String folderPath) {
		return ImageFolderHandler.getImageFiles(folderPath);
	}

	// Get this type files

	// get this type'a files

	// etc
}