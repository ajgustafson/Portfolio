/**
 * This class represent a Dither operation. Because this is an operation on an image it implements
 * the ImageEditor interface. This operation breaks down an image that has many colors into an image
 * that is made of dots from just a few colors. This particular implementation also makes the image
 * a black and white dithered image.
 */
public class Dither implements ImageEditor {
  /**
   * Create a dot-matrixed greyscale image by first converting the image to greyscale and then
   * dithering it according to the Floyd-Steinberg algorithm.
   *
   * @param image to be edited.
   * @return an image object of a greyscale, dithered object
   */
  public Image edit(Image image) {
    // transform into greyscale
    TransformColor gs = new GreyScale();
    Image grey = gs.edit(image);
    // get original image rgb values
    int[][][] rgb = grey.getRgb();
    // loop through each pixel and perform the Floyd-Steinberg algorithm
    for (int r = 0; r < image.getHeight(); r++) {
      for (int c = 0; c < image.getWidth(); c++) {
        int oldColor = rgb[r][c][0];
        int newColor = findClosestColor(oldColor);
        int error = oldColor - newColor;
        for (int k = 0; k < 3; k++) {
          rgb[r][c][k] = newColor;
        }

        if (c + 1 < image.getWidth()) {
          rgb[r][c + 1][0] = (int) Math.round(rgb[r][c + 1][0] + (7 / 16.0) * error);
        }

        if (r + 1 < image.getHeight() && c - 1 >= 0) {
          rgb[r + 1][c - 1][0] = (int) Math.round(rgb[r + 1][c - 1][0] + (3 / 16.0) * error);
        }

        if (r + 1 < image.getHeight()) {
          rgb[r + 1][c][0] = (int) Math.round(rgb[r + 1][c][0] + (5 / 16.0) * error);
        }

        if (r + 1 < image.getHeight() && c + 1 < image.getWidth()) {
          rgb[r + 1][c + 1][0] = (int) Math.round(rgb[r + 1][c + 1][0] + (1 / 16.0) * error);
        }

      }
    }

    return new Image(rgb);
  }

  /**
   * Return whether the current pixel is closer to 0 or 255.
   *
   * @param oldColor the r value of the pixel that needs to be translated into 0 or 255.
   * @return 0 if the old color is less than 128, 255 otherwise
   */
  private int findClosestColor(int oldColor) {
    if (oldColor < 128) {
      return 0;
    } else {
      return 255;
    }
  }
}
