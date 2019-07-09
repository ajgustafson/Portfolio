/**
 * This class represents a Greek flag image generator and implements the Generator interface.  This
 * class offers a method that generates and returns an image of a Greek Flag.  Flag size is
 * specified by the user.
 */
public class GreekFlag implements Generator {
  /**
   * Proportional height of the Greek flag. Height is (2/3) of the flag width.
   */
  private final int proportionHeight = 18;
  /**
   * Proportional width of the Greek flag.  Width is 1.5 times the flag height.
   */
  private final int proportionWidth = 27;
  /**
   * Proportional width of the stripes on the Greek flag.  Each stripe is (1/9) of the flag width.
   */
  private final int proportionStripe = 2;

  /**
   * Generates and returns an image of the Greek flag of given pixel width.  For a clear design, the
   * given pixel width must be greater than or equal to 27 pixels.
   *
   * @param size Desired pixel width of generated image.
   * @return Image of the Greek flag.
   * @throws IllegalArgumentException if size entered is less than 27 pixels.
   */
  public Image generate(int size) throws IllegalArgumentException {
    if (size < 27) {
      throw new IllegalArgumentException("Size must be greater than minimum pixel width.");
    }
    // Scale flag dimensions to user input
    int scaleFactor = (int) Math.ceil(size / proportionWidth);
    int height = proportionHeight * scaleFactor;
    int width = proportionWidth * scaleFactor;
    int stripe = proportionStripe * scaleFactor;
    // Create new pixel array
    int[][][] pixels = new int[height][width][3];

    // Paint vertical stripes across flag
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int colorNum = ImageUtil.whichColor(i, stripe);
        pixels[i][j] = getColor(colorNum).getRGB();
      }
    }
    // Fill blue square
    ImageUtil.drawStripe(pixels, 0, 0, stripe * 5, stripe * 5,
            ColorRGB.BLUE);

    // Paint vertical stripe of cross
    ImageUtil.drawStripe(pixels, 0, stripe * 2, stripe * 5, stripe,
            ColorRGB.WHITE);

    // Paint horizontal stripe of cross
    ImageUtil.drawStripe(pixels, stripe * 2, 0, stripe, stripe * 5,
            ColorRGB.WHITE);
    // Return new image of pixels
    return new Image(pixels);
  }

  /**
   * Helper function to determine which color to use to draw the flag stripes.  Returns ColorRGB
   * enumerated type that matches with given color number.  If color number is even, returns blue
   * enum.  If color number is odd, returns white enum.
   *
   * @param colorNum Number to determine color returned.
   * @return ColorRGB.BLUE if parameter is even, ColorRGB.WHITE otherwise.
   */
  private ColorRGB getColor(int colorNum) {
    if (colorNum % 2 == 0) {
      return ColorRGB.BLUE;
    } else {
      return ColorRGB.WHITE;
    }
  }

}