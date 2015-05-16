package flattening;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import my_classes.ComponentImage;

//TODO either make more flexible, or overload with size and everything

//TODO make all the general parts functions of their own (duh)

//TODO make runnable (extend)

//TODO remember: only static methods here, all others go into ImageGroupHandler (heavily dependent on this one)
public class AutoImageGroupFlattener {

	// TODO look into this sweetass bullshitt with just a folder arg! :O :D
	public static BufferedImage flatten(String folderWithFiles) {
		try {
			Files.walk(Paths.get("/home/you/Desktop")).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					System.out.println(filePath);
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage combined = new BufferedImage(1, 1,
				BufferedImage.TYPE_INT_ARGB);
		return combined;
	}

	public static BufferedImage flatten(ArrayList<ComponentImage> images,
			String positioning, String combinedSize) {

		int width = 0;
		int height = 0;
		if (combinedSize.equals("average")) {
			int[] widthHeight = getImageAverageWidthHeight(images);
			width = widthHeight[0];
			height = widthHeight[1];
		} else if (combinedSize.equals("max")) {
			int[] widthHeight = findMaxSideLength(images);
			width = widthHeight[0];
			height = widthHeight[1];
		}
		BufferedImage combined = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = combined.getGraphics();
		for (BufferedImage image : images) {
			g.drawImage(image, 0, 0, null);
		}

		return combined;

	}

	private static int[] getImageAverageWidthHeight(
			ArrayList<ComponentImage> images) {
		int[] widthHeight = new int[2];
		for (BufferedImage image : images) {
			widthHeight[0] += image.getWidth();
			widthHeight[1] += image.getHeight();
		}
		widthHeight[0] = (int) (widthHeight[0] + 0.0) / images.size();
		widthHeight[1] = (int) (widthHeight[1] + 0.0) / images.size();
		return widthHeight;

	}

	private static int[] findMaxSideLength(ArrayList<ComponentImage> images) {
		int[] widthHeight = new int[2];
		for (BufferedImage image : images) {
			if (image.getWidth() > widthHeight[0])
				widthHeight[0] = image.getWidth();
			if (image.getHeight() > widthHeight[1])
				widthHeight[1] = image.getHeight();
		}
		return widthHeight;
	}

	public static void flatten(ArrayList<ComponentImage> images,
			String combinedPath, String positioning, String combinedSize) {

		// load source images
		// TODO make into an image check, just use all 'good ones' in the
		// Graphics combine
		BufferedImage image = null, overlay = null;
		image = images.get(0);
		overlay = images.get(1);

		// create the new image, canvas size is the max. of both image sizes
		int w = Math.max(image.getWidth(), overlay.getWidth());
		int h = Math.max(image.getHeight(), overlay.getHeight());
		BufferedImage combined = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.drawImage(overlay, 0, 0, null);

		// Save as new image
		try {
			ImageIO.write(combined, "PNG", new File(combinedPath));
		} catch (IOException e) {
			// TODO Remember to look into solutions for catches
			e.printStackTrace();
		}

	}

	public static BufferedImage flatten(ComponentImage background,
			ComponentImage overlay, String positioning) {
		// create the new image, canvas size is the max. of both image sizes
		int w = Math.max(background.getWidth(), overlay.getWidth());
		int h = Math.max(background.getHeight(), overlay.getHeight());

		BufferedImage combined = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(background, 0, 0, null);
		g.drawImage(overlay, 0, 0, null);

		return combined;
	}

	// THIS WORKS! :O :D
	public static void flatten(String backgroundPath, String overlayPath,
			String combinedPath, String positioning) {
		// load source images
		BufferedImage background = null, overlay = null;
		try {
			background = ImageIO.read(new File(backgroundPath));
			overlay = ImageIO.read(new File(overlayPath));
		} catch (IOException e) {
			// TODO Remember to look into solutions for catches
			e.printStackTrace();
		}

		// create the new image, canvas size is the max. of both image sizes
		int w = Math.max(background.getWidth(), overlay.getWidth());
		int h = Math.max(background.getHeight(), overlay.getHeight());
		BufferedImage combined = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(background, 0, 0, null); // these two
		g.drawImage(overlay, 0, 0, null); // are the normal draw

		g.drawImage(overlay, 80, 80, null); // checking moved draw
		g.drawImage(overlay, 170, 170, 150, 150, null); // checking scaled draw
		g.drawImage(background, 330, 330, 100, 100, null); // scale down draw

		// Save as new image
		try {
			ImageIO.write(combined, "PNG", new File(combinedPath));
		} catch (IOException e) {
			// TODO Remember to look into solutions for catches
			e.printStackTrace();
		}
	}

	public static BufferedImage flatten(String backgroundPath,
			String overlayPath, String positioning) {
		// load source images
		BufferedImage background = null, overlay = null;
		try {
			background = ImageIO.read(new File(backgroundPath));
			overlay = ImageIO.read(new File(overlayPath));
		} catch (IOException e) {
			// TODO Remember to look into solutions for catches
			e.printStackTrace();
		}

		// create the new image, canvas size is the max. of both image sizes
		int w = Math.max(background.getWidth(), overlay.getWidth());
		int h = Math.max(background.getHeight(), overlay.getHeight());
		BufferedImage combined = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(background, 0, 0, null);
		g.drawImage(overlay, 0, 0, null);

		return combined;
	}

	private int[] getCenterPositioning() {
		int[] xy = new int[2];

		return xy;
	}

	public static void main(String[] args) {
		File file = new File("res/background.png");
		System.out.println(file.getAbsolutePath());
		System.out.println("flattening");
		AutoImageGroupFlattener.flatten("res/background.png",
				"res/foreground.png", "res/combined.png", "corner");
		System.out.println("done");
	}
}