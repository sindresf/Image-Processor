package my_classes.pre_processors;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.jcodec.codecs.h264.io.model.VUIParameters.BitstreamRestriction;

public class ImageAverage {

	public static void convertToBitmap(BufferedImage image) {
		String dirName = "C:\\";
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
		BufferedImage img = ImageIO.read(new File(dirName, "rose.jpg"));
		ImageIO.write(img, "jpg", baos);
		baos.flush();

		String base64String = Base64.encode(baos.toByteArray());
		baos.close();

		byte[] bytearray = Base64.decode(base64String);

		BufferedImage imag = ImageIO.read(new ByteArrayInputStream(bytearray));
		ImageIO.write(imag, "jpg", new File(dirName, "snap.jpg"));
	}

	public static Color getImageAverage(BufferedImage image) {
		Color black = null;

		byte[] bitmap = someFunctionReturningABitmap();
		long redBucket = 0;
		long greenBucket = 0;
		long blueBucket = 0;
		long pixelCount = 0;

		for (int x = 0; x < bitmap.length; x++) {
			byte c = bitmap[x];

			pixelCount++;
			redBucket += Color.red(c);
			greenBucket += Color.green(c);
			blueBucket += Color.blue(c);
			// does alpha matter?
		}

		Color averageColor = Color.rgb(redBucket / pixelCount, greenBucket
				/ pixelCount, blueBucket / pixelCount);

		return black;
	}

}