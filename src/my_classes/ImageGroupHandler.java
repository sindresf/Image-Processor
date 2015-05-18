package my_classes;

import java.awt.image.BufferedImage;

import flattening.AutoImageGroupFlattener;

public class ImageGroupHandler {

	private ImageGroup imGroup;
	private BufferedImage flatImage;

	public ImageGroupHandler(ImageGroup group) {
		this.imGroup = group;
	}

	public ImageGroup getImGroup() {
		return new ImageGroup(imGroup);
	}

	// TODO here goes functions for storing images for later flattening, and
	// flattened, and mosaic, and movie, using the statics from those classes

	// like:
	// TODO make this make sense when the auto works
	public void flattenGroup() {
		flatImage = AutoImageGroupFlattener.flatten("group1", "group2",
				"corner");
	}
}