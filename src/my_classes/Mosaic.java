package my_classes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Mosaic {
	private BufferedImage mosaic;
	private Graphics g;
	private int mosaicWidth, pieceWidth;
	private int mosaicHeight, pieceHeight;
	private int pieceCount, componentImageCount;
	private BufferedImage currentComponentImage, currentSubImg;
	private int componentImageIndex;
	private ArrayList<BufferedImage> componentImages;

	public Mosaic(ArrayList<BufferedImage> componentImages) {
		this.componentImages = componentImages;
	}

	public Mosaic(int width, int height) {
		mosaic = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = mosaic.getGraphics();
		mosaicWidth = width;
		mosaicHeight = height;
	}

	public Mosaic(int width, int height, int pieceCount) {
		mosaic = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = mosaic.getGraphics();
		mosaicWidth = width;
		mosaicHeight = height;
		this.pieceCount = pieceCount;
		this.pieceWidth = (int) (mosaicWidth / ((pieceCount * componentImageCount) + 0.0));
	}

	public void calcMosaicWidthAndHeight(boolean avrgWidth) {
		int imageCount = componentImages.size();
		mosaicWidth = 0;
		int avrgHeight = 0;
		for (BufferedImage image : componentImages) {
			mosaicWidth += image.getWidth();
			avrgHeight += image.getHeight();
		}
		if (avrgWidth)
			mosaicWidth = (int) (mosaicWidth / (imageCount + 0.0));
		mosaicHeight = (int) (avrgHeight / (imageCount + 0.0));
	}

	public int getComponentImagePieceWidth() {
		// TODO supposed to do this calc:
		int imagePieceWidth = (int) (currentComponentImage.getWidth() / (componentImageCount + 0.0));
		return imagePieceWidth;
	}

	public void makeSubImg(int xSub, int subWidth, int subHeight) {
		currentSubImg = currentComponentImage.getSubimage(xSub, 0, subWidth,
				subHeight);
	}

	public void drawCurrentSub(int xDraw) {
		g.drawImage(currentSubImg, xDraw, 0, pieceWidth, mosaicHeight, null);
	}
}