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

	public static void makeHardStripe(ArrayList<BufferedImage> componentImages,
			int components) {

		// setup
		int imageCount = componentImages.size();
		System.out.println("imageCount = " + imageCount);
		int mosaicWidth = 0;
		int avrgHeight = 0;
		for (BufferedImage image : componentImages) {
			mosaicWidth += image.getWidth();
			avrgHeight += image.getHeight();
		}
		int mosaicHeight = (int) (avrgHeight / (imageCount + 0.0));
		System.out.println("mosaic width and height: " + mosaicWidth + ", "
				+ mosaicHeight);
		BufferedImage mosaic = new BufferedImage(mosaicWidth, mosaicHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = mosaic.getGraphics();

		// for draw scaling
		int pieceWidth = (int) (mosaicWidth / ((components * imageCount) + 0.0));
		System.out.println("draw pieceWidth = " + pieceWidth);

		int imageIndex = 0;
		// going through the images
		for (BufferedImage componentImg : componentImages) {
			// making pieceWidth for the particular image based of it's width
			// for subImg
			int imagePieceWidth = (int) (componentImg.getWidth() / (components + 0.0));
			System.out.println();
			System.out.println("image " + imageIndex);
			System.out.println("image width = " + componentImg.getWidth());
			System.out.println("sub pieceWidth = " + imagePieceWidth);
			for (int piece = 0; piece < components; piece++) {
				int xSub = piece * imagePieceWidth;
				int width = imagePieceWidth;
				int height = componentImg.getHeight();

				System.out.println("piece sub X: " + xSub);
				// THIS IS WHOLLY RELIANT ON THE IMAGE'S DOMENTIONS!
				BufferedImage subImg = componentImg.getSubimage(xSub, 0, width,
						height);

				// draw coords
				int offset = (imageIndex * pieceWidth);
				int xDraw = (piece * pieceWidth * imageCount) + offset;

				// THIS IS IRRESPECTIVE OF IMAGE DIMENTIONS"
				System.out.println("piece draw X: " + xDraw);
				g.drawImage(subImg, xDraw, 0, pieceWidth, mosaicHeight, null);
			}
			imageIndex++;
		}

		// saving to disk
		try {

			ImageIO.write(mosaic, "PNG", new File(
					"res/StripeMosaic/hardstripeMosaic.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void makeBlendStripe(
			ArrayList<BufferedImage> componentImages, int components) {
		// setup
		int imageCount = componentImages.size();
		System.out.println("imageCount = " + imageCount);
		int avrgWidth = 0;
		int avrgHeight = 0;
		for (BufferedImage image : componentImages) {
			avrgWidth += image.getWidth();
			avrgHeight += image.getHeight();
		}
		int mosaicWidth = (int) (avrgWidth / (imageCount + 0.0));
		int mosaicHeight = (int) (avrgHeight / (imageCount + 0.0));
		System.out.println("mosaic width and height: " + mosaicWidth + ", "
				+ mosaicHeight);
		BufferedImage mosaic = new BufferedImage(mosaicWidth, mosaicHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = mosaic.getGraphics();

		// for draw scaling
		int pieceWidth = (int) (mosaicWidth / ((components * imageCount) + 0.0));
		int leftOver = mosaicWidth - (pieceWidth * components * imageCount);
		System.out.println("draw pieceWidth = " + pieceWidth);
		System.out.println("leftover pixels: " + leftOver);

		int imageIndex = 0;
		// drawing/combining
		System.out.println("imageCount = " + imageCount);
		for (BufferedImage componentImg : componentImages) {
			int imagepieceWidth = (int) (componentImg.getWidth() / ((components * imageCount) + 0.0));
			System.out.println();
			System.out.println("image " + imageIndex);
			System.out.println("image width = " + componentImg.getWidth());
			System.out.println("subImg pieceWidth = " + imagepieceWidth);
			int height = componentImg.getHeight();

			for (int piece = 0; piece < components; piece++) {
				int xSub = (piece * imagepieceWidth * imageCount) + imageIndex
						* imagepieceWidth;
				int width = imagepieceWidth;

				System.out.println("piece sub X: " + xSub);
				BufferedImage subImg = componentImg.getSubimage(xSub, 0, width,
						height);

				// draw coords
				int offset = (imageIndex * pieceWidth);
				int xDraw = (piece * pieceWidth * imageCount) + offset;
				System.out.println("piece draw X: " + xDraw);
				g.drawImage(subImg, xDraw, 0, pieceWidth, mosaicHeight, null);
			}

			// filling in the leftOver
			// TODO make this work
			if (leftOver > 0) {
				int xSub = componentImg.getWidth() - components
						* imagepieceWidth;
				System.out.println("piece sub x: " + xSub);
				if (leftOver <= imagepieceWidth)
					imagepieceWidth = leftOver;
				BufferedImage subImg = componentImg.getSubimage(xSub, 0,
						imagepieceWidth, height);

				int xDraw = mosaicWidth - leftOver;
				System.out.println("piece draw x: " + xDraw);
				g.drawImage(subImg, xDraw, 0, pieceWidth, mosaicHeight, null);

				leftOver -= imagepieceWidth;
				System.out.println("leftover = " + leftOver);
			}
			imageIndex++;
		}

		// saving to disk
		try {
			ImageIO.write(mosaic, "PNG", new File(
					"res/StripeMosaic/blendedstripeMosaic.png"));

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
		System.out.println("imageCount = " + imageCount);
		int componentsPrImage = components;
		if (type.equals("blend"))
			componentsPrImage /= (componentImages.size() + 0.0);
		for (BufferedImage componentImg : componentImages) {
			int imagepieceWidth = (int) (componentImg.getWidth() / (componentsPrImage + 0.0));
			System.out.println();
			System.out.println("image " + imageIndex);
			System.out.println("iamge width = " + componentImg.getWidth());
			System.out.println("pieceWidth = " + imagepieceWidth);
			int height = Math.min(componentImg.getHeight(), mosaicHeight);
			for (int piece = 0; piece < componentsPrImage; piece++) {
				if (type.equals("hard"))
					x = piece * imagepieceWidth;
				else if (type.equals("blend"))
					x = (piece * imagepieceWidth * imageCount) + imageIndex
							* imagepieceWidth;
				int y = 0;
				int width = imagepieceWidth;

				System.out.println("piece sub X: " + x);
				// THIS IS WHOLLY RELIANT ON THE IMAGE'S DOMENTIONS!
				BufferedImage subImg = componentImg.getSubimage(x, y, width,
						height);

				// draw coords
				int pieceWidth = (int) ((mosaicWidth + 0.0) / components);
				int offset = (imageIndex * pieceWidth);
				x = (piece * pieceWidth * imageCount) + offset;

				y = 0;
				// THIS IS IRRESPECTIVE OF IMAGE DIMENTIONS"
				g.drawImage(subImg, x, 0, pieceWidth, mosaicHeight, null);
			}
			imageIndex++;
		}

		// saving to disk
		try {
			if (type.equals("hard"))
				ImageIO.write(mosaic, "PNG", new File(
						"res/StripeMosaic/hardstripeMosaic.png"));
			else if (type.equals("blend"))
				ImageIO.write(mosaic, "PNG", new File(
						"res/StripeMosaic/blendedstripeMosaic.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// testCompressedSquareMosaic();
		testStripeMosaic();
	}

	public static void testCompressedSquareMosaic() {
		System.out.println("getting 9 images from 'IMGFolderTest.");
		BufferedImage im1 = null, im2 = null, im3 = null, im4 = null, im5 = null, im6 = null, im7 = null, im8 = null, im9 = null;
		String pathish = "res/IMGFolderTest/P31017";
		try {
			im1 = ImageIO.read(new File(pathish + "13.JPG"));
			System.out.print(".");
			im2 = ImageIO.read(new File(pathish + "16.JPG"));
			System.out.print(".");
			im3 = ImageIO.read(new File(pathish + "18.JPG"));
			System.out.print(".");
			im4 = ImageIO.read(new File(pathish + "19.JPG"));
			System.out.print(".");
			im5 = ImageIO.read(new File(pathish + "23.JPG"));
			System.out.print(".");
			im6 = ImageIO.read(new File(pathish + "26.JPG"));
			System.out.print(".");
			im7 = ImageIO.read(new File(pathish + "29.JPG"));
			System.out.print(".");
			im8 = ImageIO.read(new File(pathish + "31.JPG"));
			System.out.println(".");
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
			System.out.print(".");
			dirty = ImageIO.read(new File("res/StripeMosaic/dirty.jpg"));
			System.out.println(".");
			face = ImageIO.read(new File("res/StripeMosaic/face.png"));
			System.out.println("gottem");
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<BufferedImage> images = new ArrayList<>();
		images.add(clean);
		images.add(dirty);
		System.out.println("making a 'hard' stripe mosaic of clean and dirty.");
		int pieces = 10;
		// ImagesToMosaic.makeHardStripe(images, pieces);
		System.out.println("hard done.\n\n");

		images.add(face);
		System.out.println("making a 'blended' stripe mosaic of all three.");
		pieces = 20;
		ImagesToMosaic.makeBlendStripe(images, pieces);
		System.out.println("done.");
	}
}