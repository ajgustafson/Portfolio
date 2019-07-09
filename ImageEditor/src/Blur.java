/**
 * This class can be used to blur an image. Because this is an operation on an image it implements
 * the ImageEditor interface. A blur is a type of filter and thus extends the Filter class. A blur
 * is done using a 3x3 filter.
 */
public class Blur extends Filter {

  /**
   * The matrix to be used as the kernel of the filter operation.
   */
  private final double[][] matrix = {{(1.0 / 16.0), (1.0 / 8.0), (1.0 / 16.0)},
                                     {(1.0 / 8.0), (1.0 / 4.0), (1.0 / 8.0)},
                                     {(1.0 / 16.0), (1.0 / 8.0), (1.0 / 16.0)}};

  /**
   * Do a blur operation on a provided image.
   *
   * @param i the image to be blurred
   * @return the blurred image as represented by an Image object
   */
  public Image edit(Image i) {
    return super.filter(i, this.matrix);
  }
}
