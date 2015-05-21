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

	public void makePieceSquareMosaic(ArrayList<BufferedImage> images,
			int mosaicWidth, int mosaicHeight, int rows, int squares) {
	}

	public static void makeCompressedSquareMosaic(
			ArrayList<BufferedImage> images, int mosaicWidth, int mosaicHeight,
			int rows, int squaresPrRow) {

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
		String path = "res/compressedSquareMosaic.png";
		writeImageFile(mosaic, path);

	}

	private static void drawStuff(BufferedImage componentImg, int components,
			int imageIndex, int pieceWidth, int imageCount, int mosaicHeight,
			Graphics g) {
		int imagePieceWidth = (int) (componentImg.getWidth() / (components + 0.0));
		for (int piece = 0; piece < components; piece++) {
			int xSub = piece * imagePieceWidth;
			int width = imagePieceWidth;
			int height = componentImg.getHeight();

			BufferedImage subImg = componentImg.getSubimage(xSub, 0, width,
					height);

			int offset = (imageIndex * pieceWidth);
			int xDraw = (piece * pieceWidth * imageCount) + offset;

			g.drawImage(subImg, xDraw, 0, pieceWidth, mosaicHeight, null);
		}
	}

	private static void drawStuff(BufferedImage componentImg, int components,
			int imageIndex, int pieceWidth, int imageCount, int mosaicHeight,
			Graphics g, int imagepieceWidth, int height) {
		for (int piece = 0; piece < components; piece++) {
			int xSub = (piece * imagepieceWidth * imageCount) + imageIndex
					* imagepieceWidth;
			int width = imagepieceWidth;

			BufferedImage subImg = componentImg.getSubimage(xSub, 0, width,
					height);

			int offset = (imageIndex * pieceWidth);
			int xDraw = (piece * pieceWidth * imageCount) + offset;
			g.drawImage(subImg, xDraw, 0, pieceWidth, mosaicHeight, null);
		}
	}

	private static int[] getWidthHeight(
			ArrayList<BufferedImage> componentImages, boolean avrgWidth) {
		int imageCount = componentImages.size();
		int mosaicWidth = 0;
		int avrgHeight = 0;
		for (BufferedImage image : componentImages) {
			mosaicWidth += image.getWidth();
			avrgHeight += image.getHeight();
		}
		if (avrgWidth)
			mosaicWidth = (int) (mosaicWidth / (imageCount + 0.0));
		int mosaicHeight = (int) (avrgHeight / (imageCount + 0.0));
		int[] mosaicWidthHeight = { mosaicWidth, mosaicHeight };
		return mosaicWidthHeight;
	}

	public static void makeHardStripe(ArrayList<BufferedImage> componentImages,
			int components) {

		int imageCount = componentImages.size();

		int[] mosaicWidthHeight = getWidthHeight(componentImages, false);
		BufferedImage mosaic = new BufferedImage(mosaicWidthHeight[0],
				mosaicWidthHeight[1], BufferedImage.TYPE_INT_ARGB);
		Graphics g = mosaic.getGraphics();

		int pieceWidth = (int) (mosaicWidthHeight[0] / ((components * imageCount) + 0.0));
		int leftOver = mosaicWidthHeight[0]
				- (pieceWidth * components * imageCount);

		int imageIndex = 0;
		for (BufferedImage componentImg : componentImages) {
			drawStuff(componentImg, components, imageIndex, pieceWidth,
					imageCount, mosaicWidthHeight[1], g);
			imageIndex++;

			// TODO this comes later but Do It
			// fillLeftOver(leftOver, componentImg,
			// components, imagepieceWidth, height, mosaicWidth,
			// g, pieceWidth, mosaicHeight);
		}

		writeImageFile(mosaic, "res/StripeMosaic/hardStripeMosaic.png");
	}

	private static void fillLeftOver(int leftOver, BufferedImage componentImg,
			int components, int imagepieceWidth, int height, int mosaicWidth,
			Graphics g, int pieceWidth, int mosaicHeight) {
		// filling in the leftOver
		// TODO make this work
		if (leftOver > 0) {
			int xSub = componentImg.getWidth() - components * imagepieceWidth;
			if (leftOver <= imagepieceWidth)
				imagepieceWidth = leftOver;
			BufferedImage subImg = componentImg.getSubimage(xSub, 0,
					imagepieceWidth, height);

			int xDraw = mosaicWidth - leftOver;
			g.drawImage(subImg, xDraw, 0, pieceWidth, mosaicHeight, null);

			leftOver -= imagepieceWidth;
		}
	}

	public static void makeBlendStripe(
			ArrayList<BufferedImage> componentImages, int components) {

		int imageCount = componentImages.size();
		int[] mosaicWidthHeight = getWidthHeight(componentImages, true);
		BufferedImage mosaic = new BufferedImage(mosaicWidthHeight[0],
				mosaicWidthHeight[1], BufferedImage.TYPE_INT_ARGB);
		Graphics g = mosaic.getGraphics();

		int pieceWidth = (int) (mosaicWidthHeight[0] / ((components * imageCount) + 0.0));
		int leftOver = mosaicWidthHeight[0]
				- (pieceWidth * components * imageCount);

		int imageIndex = 0;
		for (BufferedImage componentImg : componentImages) {
			int imagepieceWidth = (int) (componentImg.getWidth() / ((components * imageCount) + 0.0));
			int height = componentImg.getHeight();
			drawStuff(componentImg, components, imageIndex, pieceWidth,
					imageCount, mosaicWidthHeight[1], g, imagepieceWidth,
					height);
			imageIndex++;
			fillLeftOver(leftOver, componentImg, components, imagepieceWidth,
					height, mosaicWidthHeight[0], g, pieceWidth,
					mosaicWidthHeight[1]);
		}
		writeImageFile(mosaic, "res/StripeMosaic/blendedStripeMosaic.png");
	}

	private static void writeImageFile(BufferedImage mosaic, String filePath) {
		try {
			ImageIO.write(mosaic, "PNG", new File(filePath));
		} catch (IOException e) {
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
		int mosaicWidth = 150;
		int mosaicHeight = 150;
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
		images.add(face);
		System.out.println("making a 'hard' stripe mosaic of clean and dirty.");
		int pieces = 10;
		ImagesToMosaic.makeHardStripe(images, pieces);
		System.out.println("hard done.\n\n");

		System.out.println("making a 'blended' stripe mosaic of all three.");
		pieces = 50;
		ImagesToMosaic.makeBlendStripe(images, pieces);
		System.out.println("done.");
	}
}