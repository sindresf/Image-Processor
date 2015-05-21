package my_classes.images;

/***
 * 
 * @author Mr.Fjermestad whole point here is to simplify storing partial images
 */
public class ImagePart extends ComponentImage {

	private int width, height;
	private int x_relative, y_relative; // the position in the "main,bigger"
										// picture

	public ImagePart(int width, int height, int imageType) {
		super(width, height, imageType);
		// TODO Auto-generated constructor stub
	}
}