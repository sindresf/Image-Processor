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

	public void makePieceSquareMosaic(ArrayList<BufferedImage> images,
			int mosaicWidth, int mosaicHeight, int rows, int squares) {
		// TODO and this have as much in common, and pull it all out into
		// functions (duh)
	}

	// TODO decide how lenient to be with input. Rows and squaresPrRow should
	// match up with the number of images for a nice picture. So: make the best
	// of it, or just abort and tell user?
	// maybe have a checkinput inbetween step?
	public static void makeCompressedSquareMosaic(
			ArrayList<BufferedImage> images, int mosaicWidth, int mosaicHeight,
			int rows, int squaresPrRow) {
		// TODO and this have as much in common, and pull it all out into
		// functions (duh)

		int squareWidth = (int) ((mosaicWidth + 0.0) / squaresPrRow);
		int squareHeight = (int) ((mosaicHeight + 0.0) / rows);

		BufferedImage mosaic = new BufferedImage(mosaicWidth, mosaicHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = mosaic.getGraphics();

		int x = 0;
		int y = 0;

		for (BufferedImage image : images) {
			g.drawImage(image, x * squareWidth, y * squareHeight, squareWidth,
					squareHeight, null);
			x++;
			if (x == squaresPrRow) {
				x = 0;
				y++;
			}
		}

		// saving to disk
		try {
			ImageIO.write(mosaic, "PNG", new File(
					"res/compressedSquareMosaic.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		if (type.equals("blend"))
			mosaicWidth = (int) (mosaicWidth / (imageCount + 0.0));
		int mosaicHeight = (int) (avrgHeight / (imageCount + 0.0));
		BufferedImage mosaic = new BufferedImage(mosaicWidth, mosaicHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = mosaic.getGraphics();
		int imageIndex = 0;

		// drawing/combining
		int x = 0;
		// TODO HERE! THE DRAW MUST CONSIDER EACH IMAGES RELATIVE PIECE WIDTH
		// FOR FOLLOWING DRAW POSITIONS! HOW!?!?!?
		// TODO maybe store each image with its piecewidth and stuff in a
		// componenthashmap thing
		for (BufferedImage componentImg : componentImages) {
			int pieceWidth = (int) (componentImg.getWidth() / (components + 0.0));
			for (int piece = 0; piece < components; piece++) {
				if (type.equals("hard"))
					x = piece * pieceWidth;
				else if (type.equals("blend"))
					x = (piece * pieceWidth * imageCount) + imageIndex
							* pieceWidth;
				int y = 0;
				int width = pieceWidth;
				int height = Math.min(componentImg.getHeight(), mosaicHeight);

				BufferedImage subImg = componentImg.getSubimage(x, y, width,
						height);

				// draw coords
				int relWidth = components * imageCount;
				int offset = imageIndex * components;
				x = (piece * pieceWidth) + offset;
				y = 0;
				g.drawImage(subImg, x, 0, subImg.getWidth(), mosaicHeight, null);
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
		testCompressedSquareMosaic();
		testStripeMosaic();
	}

	public static void testCompressedSquareMosaic() {
		System.out.println("getting 9 images from 'IMGFolderTest.");
		BufferedImage im1 = null, im2 = null, im3 = null, im4 = null, im5 = null, im6 = null, im7 = null, im8 = null, im9 = null;
		String pathish = "res/IMGFolderTest/P31017";
		try {
			im1 = ImageIO.read(new File(pathish + "13.JPG"));
			im2 = ImageIO.read(new File(pathish + "16.JPG"));
			im3 = ImageIO.read(new File(pathish + "18.JPG"));
			im4 = ImageIO.read(new File(pathish + "19.JPG"));
			im5 = ImageIO.read(new File(pathish + "23.JPG"));
			im6 = ImageIO.read(new File(pathish + "26.JPG"));
			im7 = ImageIO.read(new File(pathish + "29.JPG"));
			im8 = ImageIO.read(new File(pathish + "31.JPG"));
			im9 = ImageIO.read(new File("res/background.png"));
			System.out.println("gottem!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("setting the args up.");
		ArrayList<BufferedImage> images = new ArrayList<>();
		images.add(im1);
		images.add(im2);
		images.add(im3);
		images.add(im4);
		images.add(im5);
		images.add(im6);
		images.add(im7);
		images.add(im8);
		images.add(im9);
		int mosaicWidth = 1500;
		int mosaicHeight = 1500;
		int rows = 3;
		int squaresPrRow = 3;
		System.out.println("running the compression mosaic maker.");
		ImagesToMosaic.makeCompressedSquareMosaic(images, mosaicWidth,
				mosaicHeight, rows, squaresPrRow);
		System.out.println("done.");
	}

	public static void testStripeMosaic() {
		System.out.println("Getting the clean, dirty, and face images.");
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
		int pieces = 10;
		ImagesToMosaic.makeStripeMosaic(images, pieces, "hard");
		System.out.println("hard done.\n\n");

		images.add(face);
		System.out.println("making a 'blended' stripe mosaic of them.");
		pieces = 50;
		ImagesToMosaic.makeStripeMosaic(images, pieces, "blend");
		System.out.println("done.");
	}
}