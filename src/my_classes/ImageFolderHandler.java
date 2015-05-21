package my_classes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

public class ImageFolderHandler {

	public static ArrayList<BufferedImage> getImageFiles(String folderPath) {
		ArrayList<BufferedImage> images = new ArrayList<>();
		// All image file extensions acceptable
		final List<String> extensions = Arrays.asList(".JPG", ".png");

		Stream<String> filePaths = walk(folderPath);
		Stream<String> filteredPaths = filterStream(filePaths, extensions);
		filteredPaths.forEach(filePath -> {
			addImageToList(filePath, images);
		});

		return images;
	}

	public static ArrayList<BufferedImage> getImageFiles(String folderPath,
			String extention) {
		ArrayList<BufferedImage> images = new ArrayList<>();
		final List<String> extensions = Arrays.asList(extention);

		Stream<String> filePaths = walk(folderPath);
		Stream<String> filteredPaths = filterStream(filePaths, extensions);
		filteredPaths.forEach(filePath -> {
			addImageToList(filePath, images);
		});
		return images;
	}

	private static Stream<String> walk(String folderPath) {
		Stream<String> stream = null;
		try {
			stream = Files.walk(Paths.get(folderPath)).map(Object::toString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream;
	}

	private static Stream<String> filterStream(Stream<String> walkStream,
			List<String> extensions) {
		return walkStream
				.filter(s -> extensions.stream().anyMatch(s::endsWith));
	}

	private static void addImageToList(String filePath,
			ArrayList<BufferedImage> images) {
		try {
			BufferedImage image = ImageIO.read(new File(filePath));
			images.add(image);
			image.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}