/**
 * This class represents a GreyScale color transformation editor.  It can be used to create a
 * greyscale image from a full color image. A greyscale editor has a 3x3 matrix containing
 * prescribed factors used to calculate new values for the pixel channels on the provided image.
 */
public class GreyScale extends TransformColor {
  /**
   * Matrix used to transform pixel channels to greyscale values in an image.
   */
  private final double[][] matrix =
  {{.2126, .7152, .0722}, {.2126, .7152, .0722},
   {.2126, .7152, .0722}};

  /**
   * Perform a greyscale color transformation on the provided image.  Returns a new image with
   * greyscale pixel channel values.
   *
   * @param image to be edited.
   * @return new image in greyscale.
   */
  public Image edit(Image image) {
    return super.transform(image, matrix);
  }
}
