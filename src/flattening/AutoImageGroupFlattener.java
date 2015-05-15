package flattening;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import my_classes.ComponentImage;

//TODO either make more flexible, or overload with size and everything

//TODO make all the general parts functions of their own (duh)

//TODO make runnable (extend)

//TODO remember: only static methods here, all others go into ImageGroupHandler (heavily dependent on this one)
public class AutoImageGroupFlattener {

	public static BufferedImage flatten(ArrayList<BufferedImage> images) {

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

		return combined;

	}

	public static void flatten(ArrayList<ComponentImage> images,
			String combinedPath) {

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

	public static BufferedImage flatten(BufferedImage background,
			BufferedImage overlay) {
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
			String combinedPath) {
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

	public static BufferedImage getFlat(String backgroundPath,
			String overlayPath) {
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

	public static void main(String[] args) {
		File file = new File("res/background.png");
		System.out.println(file.getAbsolutePath());
		System.out.println("flattening");
		AutoImageGroupFlattener.flatten("res/background.png",
				"res/foreground.png", "res/combined.png");
		System.out.println("done");
	}
}