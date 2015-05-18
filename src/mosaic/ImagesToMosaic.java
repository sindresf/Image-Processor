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

	public static void makeStripeMosaic(ArrayList<BufferedImage> images,
			int componentWidth, int combinedWidth, int combinedHeight,
			String type) {
		// setup
		BufferedImage mosaic = new BufferedImage(combinedWidth, combinedHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = mosaic.getGraphics();
		int imageCount = images.size();
		int pieces = (int) (combinedWidth / (componentWidth + 0.0) / imageCount);
		int imageIndex = 0;
		System.out.println("pieces: " + pieces);

		// drawing/combining
		for (BufferedImage compImg : images) {
			for (int piece = 0; piece < pieces; piece++) {
				int x = 0;
				if (type.equals("hard"))
					x = piece * componentWidth;
				else if (type.equals("blend"))
					x = (piece * componentWidth * imageCount) + imageIndex
							* componentWidth;
				System.out.println("xSub = " + x);
				int y = 0;
				int width = componentWidth;
				int height = combinedHeight;
				BufferedImage subImg = compImg.getSubimage(x, y, width, height);

				// draw coords
				int relWidth = componentWidth * imageCount;
				int offset = imageIndex * componentWidth;
				x = (piece * relWidth) + offset;
				y = 0;
				System.out.println("xDraw = " + x);
				g.drawImage(subImg, x, 0, componentWidth, combinedHeight, null);
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
		BufferedImage clean = null, dirty = null;
		try {
			clean = ImageIO.read(new File("res/StripeMosaic/clean.jpg"));
			dirty = ImageIO.read(new File("res/StripeMosaic/dirty.jpg"));
			System.out.println("gottem");
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<BufferedImage> images = new ArrayList<>();
		images.add(clean);
		images.add(dirty);
		System.out.println("making a 'hard' stripe mosaic of them.");
		int pieceWidth = clean.getWidth() / 10; // e.i. total image width by how
												// many pieces
		System.out.println("pieceWidth: " + pieceWidth);
		int combinedWidth = clean.getWidth() + dirty.getWidth();
		System.out.println("the combined width: " + combinedWidth);
		ImagesToMosaic.makeStripeMosaic(images, pieceWidth, combinedWidth,
				clean.getHeight(), "hard");
		System.out.println("hard done.\n\n");

		System.out.println("making a 'blended' stripe mosaic of them.");
		combinedWidth = (int) ((clean.getWidth() + dirty.getWidth()) / 2.0);
		pieceWidth = (int) (combinedWidth / 73);
		// many pieces
		System.out.println("pieceWidth: " + pieceWidth);
		System.out.println("the combined width: " + combinedWidth);
		ImagesToMosaic.makeStripeMosaic(images, pieceWidth, combinedWidth,
				clean.getHeight(), "blend");
		System.out.println("done.");
	}
}