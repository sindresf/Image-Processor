package my_classes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageFolderHandler {

	public static ArrayList<BufferedImage> getImageFiles(String folderPath) {
		System.out.println("getting image files from " + folderPath);
		ArrayList<BufferedImage> images = new ArrayList<>();
		final List<String> extensions = Arrays.asList(".JPG", ".png");
		try {
			System.out.println("walking paths");
			Files.walk(Paths.get(folderPath)).map(Object::toString)
					.filter(s -> extensions.stream().anyMatch(s::endsWith))
					.forEach(filePath -> {
						System.out.println("adding: " + filePath);
						BufferedImage image = null;
						try {
							image = ImageIO.read(new File(filePath));
							images.add(image);
							image.flush();
						} catch (Exception e) {
							e.printStackTrace();
						}

					});

		} catch (IOException e) {
			e.printStackTrace();
		}
		return images;
	}
}