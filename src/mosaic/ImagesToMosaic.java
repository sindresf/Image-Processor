package mosaic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import my_classes.ComponentImage;

//TODO overload and think of special mosaics, like "every other" and such
public class ImagesToMosaic {

	public void makeMosaic() {
		// TODO remember to make this

		// TODO remember this neat little tidbit
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("file"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		img = img.getSubimage(50, 50, 500, 500); // 500 x 500, at 50,50
	}

	public void makeSquareMosaic() {
		// TODO and this have as much in common, and pull it all out into
		// functions (duh)
	}

	public static void makeStripeMosaic(
			ArrayList<BufferedImage> componentImages, int components,
			String type) {
		// setup
		int imageCount = componentImages.size();
		int mosaicWidth = 0;
		int avrgHeight = 0;
		for (BufferedImage image : componentImages) {
			mosaicWidth += image.getWidth();
			avrgHeight += image.getHeight();
		}
		int mosaicHeight = (int) (avrgHeight / (imageCount + 0.0));
		BufferedImage mosaic = new BufferedImage(mosaicWidth, mosaicHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = mosaic.getGraphics();
		int imageIndex = 0;

		// drawing/combining
		int x = 0;
		for (BufferedImage componentImg : componentImages) {
			int pieceWidth = (int) (componentImg.getWidth() / (components + 0.0));
			for (int piece = 0; piece < components; piece++) {
				if (type.equals("hard"))
					x = piece * pieceWidth;
				else if (type.equals("blend"))
					x = (piece * components * imageCount) + imageIndex
							* components;
				int y = 0;
				int width = pieceWidth;
				int height = Math.max(componentImg.getHeight(), mosaicHeight);
				BufferedImage subImg = componentImg.getSubimage(x, y, width,
						height);

				// draw coords
				int relWidth = components * imageCount;
				int offset = imageIndex * components;
				x = (piece * relWidth) + offset;
				y = 0;
				g.drawImage(subImg, x, 0, components, mosaicHeight, null);
			}
			imageIndex++;
		}

		// saving to disk
		try {
			if (type.equals("hard"))
				ImageIO.write(mosaic, "PNG", new File(
						"res/StripeMosaic/hardstripeCombined.png"));
			else if (type.equals("blend"))
				ImageIO.write(mosaic, "PNG", new File(
						"res/StripeMosaic/blendedstripeCombined.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("Getting the clean and dirty images.");
		BufferedImage clean = null, dirty = null, face = null;
		try {
			clean = ImageIO.read(new File("res/StripeMosaic/clean.jpg"));
			dirty = ImageIO.read(new File("res/StripeMosaic/dirty.jpg"));
			face = ImageIO.read(new File("res/StripeMosaic/face.png"));
			System.out.println("gottem");
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<BufferedImage> images = new ArrayList<>();
		images.add(clean);
		images.add(dirty);
		System.out.println("making a 'hard' stripe mosaic of them.");
		int pieces = 15;
		int combinedWidth = clean.getWidth() + dirty.getWidth();
		System.out.println("the combined width: " + combinedWidth);
		ImagesToMosaic.makeStripeMosaic(images, pieces, "hard");
		System.out.println("hard done.\n\n");

		images.add(face);
		System.out.println("making a 'blended' stripe mosaic of them.");
		combinedWidth = (int) (((clean.getWidth() + dirty.getWidth() + face
				.getWidth())) / images.size());
		pieces = 50;
		System.out.println("the combined width: " + combinedWidth);
		ImagesToMosaic.makeStripeMosaic(images, pieces, "blend");
		System.out.println("done.");
	}
}