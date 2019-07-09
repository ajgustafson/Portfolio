/**
 * This class represent a sharpen operation on an image. Because this is an operation on an image it
 * implements the ImageEditor interface. This is a type of filter operation and thus it extends the
 * Filter class.
 */
public class Sharpen extends Filter {
  /**
   * The matrix to be used as the kernel of the filter operation.
   */
  private final double[][] matrix = {{(-1.0 / 8.0), (-1.0 / 8.0), (-1.0 / 8.0), (-1.0 / 8.0),
                                      (-1.0 / 8.0)},
                                     {(-1.0 / 8.0), (1.0 / 4.0), (1.0 / 4.0), (1.0 / 4.0),
                                       (-1.0 / 8.0)},
                                     {(-1.0 / 8.0), (1.0 / 4.0), (1.0), (1.0 / 4.0), (-1.0 / 8.0)},
                                     {(-1.0 / 8.0), (1.0 / 4.0), (1.0 / 4.0), (1.0 / 4.0),
                                       (-1.0 / 8.0)},
                                     {(-1.0 / 8.0), (-1.0 / 8.0), (-1.0 / 8.0), (-1.0 / 8.0),
                                       (-1.0 / 8.0)}};


  /**
   * Do a sharpen operation on a provided image.
   *
   * @param image the image to be sharpened
   * @return a sharpened image as represented by an Image object
   */
  public Image edit(Image image) {
    return super.filter(image, this.matrix);
  }
}
