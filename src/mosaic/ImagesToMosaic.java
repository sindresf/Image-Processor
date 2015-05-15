package mosaic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
}