/**
 * This class represents a Sepia color transformation editor.  It can be used to create a sepia
 * image from a full color image.  A sepia editor has a 3x3 matrix containing prescribed factors
 * used to calculate new values for the pixel channels on the provided image.
 */
public class Sepia extends TransformColor {
  /**
   * Matrix used to transform pixel channels to sepia values in an image.
   */
  private final double[][] matrix =
  {{.393, .769, .189}, {.349, .686, .168},
   {.272, .534, .131}};

  /**
   * Perform a sepia color transformation on the provided image.  Returns a new image with sepia
   * pixel channel values.
   *
   * @param image to be edited.
   * @return new image in greyscale.
   */
  public Image edit(Image image) {
    return super.transform(image, matrix);
  }
}
