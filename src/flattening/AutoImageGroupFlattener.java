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
	// TODO see if it can be made to take no size parameters
	// TODO look into MAKING them transparent!
	// TODO look into file type filtering!
	public static void flatten(String folderWithFiles, int width, int height) {
		ArrayList<BufferedImage> images = new ArrayList<>();
		try {

			Files.walk(Paths.get(folderWithFiles)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					System.out.println("drawing: " + filePath);
					BufferedImage image;

					try {
						image = ImageIO.read(filePath.toFile());
						images.add(image);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage combined = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = combined.getGraphics();
		for (BufferedImage image : images) {
			g.drawImage(image, 0, 0, null);
		}

		String filepath = "res/folderflatten.png";
		writePNGTo(combined, filepath);
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
		if (positioning.equals("corner")) {
			for (ComponentImage image : images) {
				g.drawImage(image, 0, 0, null);
			}
		} else if (positioning.equals("center")) {
			for (ComponentImage image : images) {
				int[] relativeXY = {
						(int) (width / 2.0 - image.getWidth() / 2.0),
						(int) (height / 2.0 - image.getHeight() / 2.0) };
				g.drawImage(image, relativeXY[0], relativeXY[1], null);
			}
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

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		if (positioning.equals("corner")) {
			for (ComponentImage image : images) {
				g.drawImage(image, 0, 0, null);
			}
		} else if (positioning.equals("center")) {
			for (ComponentImage image : images) {
				int[] relativeXY = {
						(int) (width / 2.0 - image.getWidth() / 2.0),
						(int) (height / 2.0 - image.getHeight() / 2.0) };
				g.drawImage(image, relativeXY[0], relativeXY[1], null);
			}
		}

		writePNGTo(combined, combinedPath);

	}

	// DOES ITS THING
	public static BufferedImage flatten(ComponentImage background,
			ComponentImage overlay, String positioning) {
		// create the new image, canvas size is the max. of both image sizes
		int w = Math.max(background.getWidth(), overlay.getWidth());
		int h = Math.max(background.getHeight(), overlay.getHeight());

		BufferedImage combined = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		if (positioning.equals("corner")) {
			g.drawImage(background, 0, 0, null);
			g.drawImage(overlay, 0, 0, null);
		} else if (positioning.equals("center")) {
			int[] imageXY = { background.getWidth(), background.getHeight() };
			int[] relativeXY = {
					(int) (imageXY[0] / 2.0 - overlay.getWidth() / 2.0),
					(int) (imageXY[1] / 2.0 - overlay.getHeight() / 2.0) };
			g.drawImage(background, 0, 0, null);
			g.drawImage(overlay, relativeXY[0], relativeXY[1], null);
		}

		return combined;
	}

	// DOES ITS THING
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
		if (positioning.equals("corner")) {
			g.drawImage(background, 0, 0, null);
			g.drawImage(overlay, 0, 0, null);
		} else if (positioning.equals("center")) {
			int[] imageXY = { background.getWidth(), background.getHeight() };
			int[] relativeXY = {
					(int) (imageXY[0] / 2.0 - overlay.getWidth() / 2.0),
					(int) (imageXY[1] / 2.0 - overlay.getHeight() / 2.0) };
			g.drawImage(background, 0, 0, null);
			g.drawImage(overlay, relativeXY[0], relativeXY[1], null);
		}

		writePNGTo(combined, combinedPath);
	}

	// DOES ITS THING
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
		if (positioning.equals("corner")) {
			g.drawImage(background, 0, 0, null);
			g.drawImage(overlay, 0, 0, null);
		} else if (positioning.equals("center")) {
			int[] imageXY = { background.getWidth(), background.getHeight() };
			int[] relativeXY = {
					(int) (imageXY[0] / 2.0 - overlay.getWidth() / 2.0),
					(int) (imageXY[1] / 2.0 - overlay.getHeight() / 2.0) };
			g.drawImage(background, 0, 0, null);
			g.drawImage(overlay, relativeXY[0], relativeXY[1], null);
		}

		return combined;
	}

	private int[] getCenterPositioning() {
		int[] xy = new int[2];

		return xy;
	}

	private static void writePNGTo(BufferedImage image, String filepath) {
		// Save as new image
		try {
			ImageIO.write(image, "PNG", new File(filepath));
		} catch (IOException e) {
			// TODO Remember to look into solutions for catches
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		testBasic();
		testFolder();
	}

	private static void testBasic() {
		System.out.println("flattening");
		AutoImageGroupFlattener.flatten("res/background.png",
				"res/foreground.png", "res/combined.png", "corner");
		System.out.println("done");

		System.out.println("getting flattened cornered image");
		BufferedImage cornered = AutoImageGroupFlattener.flatten(
				"res/background.png", "res/foreground.png", "corner");
		System.out.println("got it");
		System.out.println("storing it in 'cornered.png'");
		try {
			ImageIO.write(cornered, "PNG", new File("res/cornered.png"));
			System.out.println("done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("failed");
			e.printStackTrace();
		}

		System.out.println("getting flattened centered image");
		BufferedImage centered = AutoImageGroupFlattener.flatten(
				"res/background.png", "res/foreground.png", "center");
		System.out.println("got it");
		System.out.println("storing it in 'cornered.png'");
		try {
			ImageIO.write(centered, "PNG", new File("res/centered.png"));
			System.out.println("done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("failed");
			e.printStackTrace();
		}
	}

	public static void testFolder() {
		System.out
				.println("trying to flatten entire folder: 'res/IMGFolderTest'");
		AutoImageGroupFlattener.flatten("res/IMGFolderTest", 3600, 2000);
		System.out.println("done");
	}
}