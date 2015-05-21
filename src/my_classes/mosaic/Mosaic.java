package my_classes.mosaic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Mosaic {
	private BufferedImage mosaic; // the 'image' of it self
	private Graphics g; // the graphics version of the image
	// width of mosaic, and each piece Drawn to it's graphic
	private int mosaicWidth, pieceWidth;
	// height of mosaic, and each piece Drawn to it's graphic
	private int mosaicHeight, pieceHeight;
	// total pieces to be Drawn, and the number of images they're from
	private int pieceCount, componentImageCount;
	// currently prosessed iamge, and it's piece (or subImg)
	private BufferedImage currentComponentImage, currentSubImg;
	// which image out of the image count
	private int componentImageIndex;
	// the list of all the images to be used
	private ArrayList<BufferedImage> componentImages;

	public Mosaic(ArrayList<BufferedImage> componentImages) {
		this.componentImages = componentImages;
		this.componentImageCount = componentImages.size();
		g = null;
		mosaic = null;
		pieceWidth = 0;
		pieceCount = 0;
		mosaicWidth = 0;
		pieceHeight = 0;
		mosaicHeight = 0;
		currentSubImg = null;
		componentImageIndex = 0;
		currentComponentImage = this.componentImages.get(componentImageIndex);
	}

	public int sumComponentHeights() {
		int sum = 0;
		for (BufferedImage image : componentImages) {
			sum += image.getHeight();
		}
		return sum;
	}

	public void averageComponentHeights() {
		if (mosaicHeight == 0) {
			for (BufferedImage image : componentImages) {
				mosaicHeight += image.getHeight();
			}
		}
		mosaicHeight = (int) (mosaicHeight / (componentImageCount + 0.0));
	}

	public int sumComponentWidth() {
		int sum = 0;
		for (BufferedImage image : componentImages) {
			sum += image.getWidth();
		}
		return sum;
	}

	public void averageComponentWidth() {
		if (mosaicWidth == 0) {
			for (BufferedImage image : componentImages) {
				mosaicWidth += image.getWidth();
			}
		}
		mosaicWidth = (int) (mosaicWidth / (componentImageCount + 0.0));
	}

	public int getCurrentComponentImagePieceWidth() {
		int imagePieceWidth = (int) (currentComponentImage.getWidth() / (componentImageCount + 0.0));
		return imagePieceWidth;
	}

	public void makeSubImg(int xSub, int subWidth, int subHeight) {
		currentSubImg = currentComponentImage.getSubimage(xSub, 0, subWidth,
				subHeight);
	}

	public void setMosaic(int width, int height) {
		mosaic = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		mosaicWidth = width;
		mosaicHeight = height;
	}

	public BufferedImage getCurrentComponentImage() {
		return currentComponentImage;
	}

	public void nextComponentImage() {
		componentImageIndex++;
		this.currentComponentImage = componentImages.get(componentImageIndex);
	}

	public void drawCurrentSub(int xDraw) {
		g.drawImage(currentSubImg, xDraw, 0, pieceWidth, mosaicHeight, null);
	}
}