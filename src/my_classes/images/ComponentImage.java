package my_classes.images;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

/***
 * @author Mr.Fjermestad, just an image, but meant to be a component for
 *         Flattened images, so this Can hold relative positioning, and alpha
 *         and what not
 ***/

// TODO make this the image class it deserves to be
public class ComponentImage extends BufferedImage {

	public ComponentImage(int width, int height, int imageType,
			IndexColorModel cm) {
		super(width, height, imageType, cm);
		// TODO Auto-generated constructor stub
	}

	public ComponentImage(int width, int height, int imageType) {
		super(width, height, imageType);
		// TODO Auto-generated constructor stub
	}

	public ComponentImage(ColorModel cm, WritableRaster raster,
			boolean isRasterPremultiplied, Hashtable<?, ?> properties) {
		super(cm, raster, isRasterPremultiplied, properties);
		// TODO Auto-generated constructor stub
	}

}