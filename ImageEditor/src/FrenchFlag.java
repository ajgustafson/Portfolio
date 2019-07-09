/**
 * This class represents a French flag image generator and implements the Generator interface.  This
 * class offers a method that generates and returns an image of a French Flag.  Flag size is
 * specified by the user.
 */
public class FrenchFlag implements Generator {
  /**
   * Proportional height of the French flag.  Height is (2/3) of the flag width.
   */
  private final int proportionHeight = 2;
  /**
   * Proportional width of the French flag.  Width is 1.5 times the flag height.
   */
  private final int proportionWidth = 3;
  /**
   * Proportional width of the stripes on the French flag.  Each stripe is (1/3) the width of the
   * flag.
   */
  private final int proportionStripe = 1;

  /**
   * Generates and returns an image of the French flag of given pixel width.  For a clear design,
   * the given pixel width must be greater than or equal to 3 pixels.
   *
   * @param size Desired pixel width of generated image.
   * @return Image of the French flag.
   * @throws IllegalArgumentException if size entered is less than 3 pixels.
   */
  public Image generate(int size) throws IllegalArgumentException {
    if (size < 3) {
      throw new IllegalArgumentException("Size must be greater than minimum pixel width.");
    }
    // Scale flag dimensions to user input
    int scaleFactor = (int) Math.ceil(size / proportionWidth);
    int width = proportionWidth * scaleFactor;
    int height = proportionHeight * scaleFactor;
    int stripe = proportionStripe * scaleFactor;
    // Create new pixel array
    int[][][] pixels = new int[height][width][3];
    // Draw 3 stripes for flag design
    ImageUtil.drawStripe(pixels, 0, 0, pixels.length, stripe, ColorRGB.BLUE);
    ImageUtil.drawStripe(pixels, 0, stripe, pixels.length, stripe, ColorRGB.WHITE);
    ImageUtil.drawStripe(pixels, 0, stripe * 2, pixels.length, stripe, ColorRGB.RED);
    // Return image of pixels
    return new Image(pixels);
  }
}