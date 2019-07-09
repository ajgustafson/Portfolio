/**
 * This class represents a Swiss flag image generator and implements the Generator interface.  This
 * class offers a method that generates and returns an image of a Swiss Flag.  Flag size is
 * specified by the user.
 */
public class SwissFlag implements Generator {
  /**
   * Proportional width of Swiss flag.  The Swiss flag is square, so it's width and height are
   * equal.
   */
  private final int proportionWidth = 32;
  /**
   * Proportional width of the "small" side of the center cross on the Swiss flag.  This represents
   * the height of the horizontal stripe of the cross.
   */
  private final int proportionSmall = 6;
  /**
   * Proportional width of the "large" side of the center cross on the Swiss flag.  This represents
   * the width of the vertical stripe of the cross.
   */
  private final int proportionLarge = 7;

  /**
   * Generates and returns an image of the Swiss flag of given pixel width.  For a clear design, the
   * given pixel width must be greater than or equal to 32 pixels.
   *
   * @param size Desired pixel width of generated image.
   * @return Image of the Swiss flag.
   * @throws IllegalArgumentException if size entered is less than 32 pixels.
   */
  public Image generate(int size) {
    if (size < 32) {
      throw new IllegalArgumentException("Size must be greater than minimum pixel width.");
    }
    // Scale flag dimensions to user input
    int scaleFactor = (int) Math.ceil(size / proportionWidth);
    int width = proportionWidth * scaleFactor;
    int small = proportionSmall * scaleFactor;
    int large = proportionLarge * scaleFactor;
    // Create new array of pixels
    int[][][] pixels = new int[width][width][3];
    // Fill red background
    ImageUtil.colorFill(pixels, ColorRGB.RED);
    //Vertical stripe
    ImageUtil.drawStripe(pixels, small, small + large, (large * 2) + small,
            small, ColorRGB.WHITE);
    // Horizontal stripe
    ImageUtil.drawStripe(pixels, small + large, small, small, (large * 2) + small,
            ColorRGB.WHITE);
    // Return new image of pixels
    return new Image(pixels);
  }
}