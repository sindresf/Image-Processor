package my_classes.images;

import java.util.ArrayList;

//TODO rework this whole mess to be more transparent and usable
public class ImageGroup {
	private ArrayList<ComponentImage> imageList;
	private ArrayList<ArrayList<ComponentImage>> imageMatrix;
	private GroupType type;

	public ImageGroup(ArrayList<ComponentImage> images, int something) {
		this.imageList = images;
		type = GroupType.LIST;
	}

	public ImageGroup(ArrayList<ArrayList<ComponentImage>> imageMatrix,
			double somethingOther) {
		this.imageMatrix = imageMatrix;
		type = GroupType.MATRIX;
	}

	public ImageGroup(ImageGroup group) {
		switch (group.getGroupType()) {
		case LIST:
			this.imageList = group.imageList;
			break;
		case MATRIX:
			this.imageMatrix = group.imageMatrix;
			break;
		default:
			break;
		}
	}

	public int getImageCount() {
		switch (type) {
		case LIST:
			return imageList.size();
		case MATRIX:
			int rows = imageMatrix.size();
			int columns = imageMatrix.get(0).size();
			return rows * columns;
		default:
			return 0;
		}
	}

	public GroupType getGroupType() {
		return type;
	}
}