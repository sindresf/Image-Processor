package flattening;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import my_classes.ImageComponent;

//TODO either make more flexible, or overload with size and everything

//TODO make all the general parts functions of their own (duh)
public class ImageGroupFlattening {

	public BufferedImage Flatten(ArrayList<BufferedImage> images) {
		File path = new File(" "); // base path of the images

		// load source images
		BufferedImage image = null, overlay = null;
		try {
			image = ImageIO.read(new File(path, "image.png"));
			overlay = ImageIO.read(new File(path, "overlay.png"));
		} catch (IOException e) {
			// TODO Remember to look into solutions for catches
			e.printStackTrace();
		}

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

	public void Flatten(ArrayList<ImageComponent> images, String combinedPath) {

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
			ImageIO.write(combined, "PNG", new File(combinedPath,
					"combined.png"));
		} catch (IOException e) {
			// TODO Remember to look into solutions for catches
			e.printStackTrace();
		}

	}

	public BufferedImage Flatten(BufferedImage background, BufferedImage overlay) {
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

	public void Flatten(String backgroundPath, String overlayPath,
			String combinedPath) {
		// load source images
		BufferedImage background = null, overlay = null;
		try {
			background = ImageIO.read(new File(backgroundPath, "image.png"));
			overlay = ImageIO.read(new File(overlayPath, "overlay.png"));
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

		// Save as new image
		try {
			ImageIO.write(combined, "PNG", new File(combinedPath,
					"combined.png"));
		} catch (IOException e) {
			// TODO Remember to look into solutions for catches
			e.printStackTrace();
		}
	}

}