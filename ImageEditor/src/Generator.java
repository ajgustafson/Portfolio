/**
 * This interface represents an image Generator.  Image generators offer a method to generate images
 * of user specified size.
 */
public interface Generator {

  /**
   * Generates and returns an image of provided pixel width.  Pixel width must be greater than or
   * equal to minimum pixel width as specified by the concrete class for the Generator.  Pixel width
   * may vary in each concrete class depending on the design generated.  See documentation in the
   * concrete class for minimum pixel widths.
   *
   * @param size Desired pixel width of generated image.
   * @return Image generated.
   * @throws IllegalArgumentException if the specified size is below the minimum pixel width for
   *                                  image to be generated.
   */
  Image generate(int size) throws IllegalArgumentException;
}
