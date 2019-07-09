import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class contains utility methods to read an image from file and write to a file. This class
 * also has helper functions for editing and generating new images.
 */
public class ImageUtil {

  /**
   * Read an image file and return the contents as an array.
   *
   * @param filename the path of the file. Look at the ImageIO documentation to see which file
   *                 formats are supported.
   * @return the image as a 3D array of integer values
   */
  public static int[][][] readImage(String filename) throws IOException {
    BufferedImage input;

    input = ImageIO.read(new FileInputStream(filename));

    int[][][] result = new int[input.getHeight()][input.getWidth()][3];

    for (int i = 0; i < input.getHeight(); i++) {
      for (int j = 0; j < input.getWidth(); j++) {
        int color = input.getRGB(j, i);
        Color c = new Color(color);
        result[i][j][0] = c.getRed();
        result[i][j][1] = c.getGreen();
        result[i][j][2] = c.getBlue();
      }
    }
    return result;
  }

  /**
   * Convenience function to get the width of an image.
   *
   * @param filename the full path of the image file. Look at the ImageIO class to see which file
   *                 formats are supported
   * @return the width of the file
   * @throws IOException if the file is not found
   */

  public static int getWidth(String filename) throws IOException {
    BufferedImage input;

    input = ImageIO.read(new FileInputStream(filename));

    return input.getWidth();
  }

  /**
   * Convenience function to get the height of an image.
   *
   * @param filename the full path of the image file. Look at the ImageIO class to see which file
   *                 formats are supported
   * @return the height of the file
   * @throws IOException if the file is not found
   */
  public static int getHeight(String filename) throws IOException {
    BufferedImage input;

    input = ImageIO.read(new FileInputStream(filename));

    return input.getHeight();
  }


  /**
   * Write an image to a file in a given format.
   *
   * @param rgb      the image data as a 3D array of integers. The dimensions are row, col and
   *                 channel respectively
   * @param width    the width of the image
   * @param height   the height of the image
   * @param filename the full path of where the image must be stored. This should include the name
   *                 and extension of the file
   * @throws IOException if the file cannot be written to the provided path
   */
  public static void writeImage(int[][][] rgb, int width, int height, String
          filename)
          throws IOException {
    BufferedImage output = getBufferImage(rgb, width, height);
    String extension = filename.substring(filename.indexOf(".") + 1);
    ImageIO.write(output, extension, new FileOutputStream(filename));
  }

  /**
   * Takes a 3D integer array of RGB pixel values and returns it as a BufferedImage.
   *
   * @param rgb    3D RGB pixel array
   * @param width  of the pixel array
   * @param height of the pixel array
   * @return BufferedImage object representation of given rgb pixel array
   */
  public static BufferedImage getBufferImage(int[][][] rgb, int width, int height) {
    BufferedImage output = new BufferedImage(
            width,
            height,
            BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = rgb[i][j][0];
        int g = rgb[i][j][1];
        int b = rgb[i][j][2];

        //color is stored in 1 integer, with the 4 bytes storing ARGB in that
        //order. Each of r,g,b are stored in 8 bits (hence between 0 and 255).
        // So we put them all in one integer by using bit-shifting << as below
        int color = (r << 16) + (g << 8) + b;
        output.setRGB(j, i, color);
      }
    }
    return output;
  }

  /**
   * Returns which row or column a particular index should be in. The numbering starts at 0. Ex. If
   * making vertical stripes and width is 3 and index is 0-2 then 1 will be returned, for index 3-5
   * then 2 will be returned, etc.
   *
   * @param index the index of the pixel in question
   * @param width the width (in pixels) that columns or rows are for the picture in question
   * @return which row or column the pixel in question belongs to
   */
  public static int whichColor(int index, int width) {
    int numerator = index - (index % width);
    return numerator / width;
  }

  /**
   * Populate an array representing pixels at a provided index which a specified color.
   *
   * @param pixels the pixels array to be populated
   * @param color  an integer corresponding to which color should be put in the pixel array.
   *               Numbering is 0-6 in correspondence to colors in the order of ROYGBIV
   * @param i      the row to be populated
   * @param j      the column to be populated
   */
  private static void populateRainbow(int[][][] pixels, int color, int i, int j) {
    if (color == 0) {
      pixels[i][j] = ColorRGB.RED.getRGB();
    } else if (color == 1) {
      pixels[i][j] = ColorRGB.ORANGE.getRGB();
    } else if (color == 2) {
      pixels[i][j] = ColorRGB.YELLOW.getRGB();
    } else if (color == 3) {
      pixels[i][j] = ColorRGB.GREEN.getRGB();
    } else if (color == 4) {
      pixels[i][j] = ColorRGB.BLUE.getRGB();
    } else if (color == 5) {
      pixels[i][j] = ColorRGB.INDIGO.getRGB();
    } else {
      pixels[i][j] = ColorRGB.VIOLET.getRGB();
    }

  }

  /**
   * Generate a rainbow image of a user-specified size. The size specified is to be the number of
   * pixels of the image width and the image height. Colors are in the order ROYBGIV. All stripes
   * are of the same thickness/width (depending on whether the stripes are horizontal or vertical).
   * If this is not possible for the provided dimensions, the last strip may be up to 7 pixels
   * thinner/shorter.
   *
   * @param width     the size in pixels of the width of the image to be generated
   * @param height    the size in pixels of the height of the image to be generated
   * @param direction 0 for a vertical rainbow, non-zero for a horizontal rainbow
   * @return an Image object that contains the data for the rainbow
   */
  public static Image generateRainbow(int width, int height, int direction) {
    int[][][] pixels = new int[height][width][3];
    int stripeWidth;
    if (direction == 0) {
      stripeWidth = (int) Math.ceil(width / 7.0);
    } else {
      stripeWidth = (int) Math.ceil(height / 7.0);
    }
    int color;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (direction == 0) {
          color = ImageUtil.whichColor(j, stripeWidth);
        } else {
          color = ImageUtil.whichColor(i, stripeWidth);
        }
        ImageUtil.populateRainbow(pixels, color, i, j);
      }
    }
    return new Image(pixels);
  }

  /**
   * Draw a stripe of given color on a 3D integer array of pixels beginning at the given row and
   * column and extending the the given length and width.
   *
   * @param pixels reference to the 3D integer array representing the pixels of the image on which
   *               stripe will be drawn.
   * @param row    at which stripe will begin (should be the top-most row of stripe).
   * @param column at which stripe will begin (should be the left-most column of stripe).
   * @param length of stripe (number of pixels).
   * @param width  of stripe (number of pixels).
   * @param color  of stripe.
   */
  public static void drawStripe(int[][][] pixels, int row, int column, int length, int width,
                                ColorRGB color) {
    for (int i = row; i < row + length; i++) {
      for (int j = column; j < column + width; j++) {
        pixels[i][j] = color.getRGB();
      }
    }
  }

  /**
   * Fills a 3D integer array (representing pixels) with given color.  Color is represented with
   * ColorRGB enumerated type.
   *
   * @param pixels reference to the 3D integer array representing the pixels to be filled with given
   *               color.
   * @param color  color to fill pixels.
   */
  public static void colorFill(int[][][] pixels, ColorRGB color) {
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j] = color.getRGB();
      }
    }
  }

  /**
   * Clamps integer value between 0 and 255 (inclusive).  This helper method is useful for ensuring
   * that RGB channel values are within 8-bit range.  If the original value is within the 0-255
   * range, original value is returned.  If original value is less than zero, this method returns
   * zero.  If original value is greater than 255, this method returns 255.
   *
   * @param value to be clamped.
   * @return original value if withing 0-255 range (inclusive), 0 if original value less than 0, and
   *          255 if original value greater than 255.
   */
  public static int clamp(int value) {
    if (value < 0) {
      return 0;
    } else if (value > 255) {
      return 255;
    } else {
      return value;
    }
  }
}
