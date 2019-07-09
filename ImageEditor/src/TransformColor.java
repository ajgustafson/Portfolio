/**
 * This class represents a color transformation on an image. Color transformation operations use
 * matrix multiplication on the channels of each pixel to change the channel values.  This results
 * in an image with a different appearance. Examples of color transformations are making a greyscale
 * or sepia image from a full color image.  This class is abstract and implements the ImageEditor
 * interface.
 */
public abstract class TransformColor implements ImageEditor {

  /**
   * Transform an image with a color transformation.  A color transformation changes the color of a
   * pixel based on its current color.  The new color of this pixel is only dependent on its current
   * channel values.  This operation specifically represents a linear color transformation.  This
   * means that the new channel values of a pixel are linear combinations of its initial channel
   * values.  The provided matrix is used to calculate this linear combination.
   *
   * @param image  to be transformed.
   * @param matrix containing values used in the linear color transformation.
   * @return new transformed image.
   */
  public Image transform(Image image, double[][] matrix) {
    // Create pixel array for new transformed image
    int[][][] pixels = new int[image.getHeight()][image.getWidth()][3];
    // Get pixel array from input image
    int[][][] inputRgb = image.getRgb();
    // Iterate through each pixel in the original image and perform matrix multiplication
    // on the channel values for each pixel
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          int result = (int) Math.round((inputRgb[i][j][0] * matrix[k][0])
                  + (inputRgb[i][j][1] * matrix[k][1])
                  + (inputRgb[i][j][2] * matrix[k][2]));
          // Clamp result between 0-255 (8-bit RGB range)
          result = ImageUtil.clamp(result);
          // Put result in new pixel array
          pixels[i][j][k] = result;
        }
      }
    }
    // Return new image from new pixel array
    return new Image(pixels);
  }
}
