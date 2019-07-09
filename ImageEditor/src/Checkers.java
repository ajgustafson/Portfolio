/**
 * This class represents a checkerboard pattern. It generates a checkerboard and thus implements the
 * Generator interface.
 */
public class Checkers implements Generator {
  /**
   * Generate a checkerboard pattern of a specified size. A checkerboard is an 8x8 board of
   * alternating black and white squares.
   *
   * @param size the size (in pixels) of each individual tile's width and height
   * @return an Image object representing the checkerboard that has been generated
   * @throws IllegalArgumentException if the size is less than 1 because each tile must be at least
   *                                  one pixel wide
   */
  public Image generate(int size) throws IllegalArgumentException {
    if (size < 1) {
      System.out.println(size);
      throw new IllegalArgumentException("Size must be a positive number");
    }
    int width = size * 8;
    int[][][] pixels = new int[width][width][3];
    ColorRGB color;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < width; j++) {
        color = checkerColor(i, j, size);
        pixels[i][j] = color.getRGB();
      }
    }
    return new Image(pixels);
  }

  /**
   * Determine which color the pixel at row i and column j should be.
   *
   * @param i    the row of the pixel in question
   * @param j    the column of the pixel in question
   * @param size the width/height of each individual square
   * @return a colorRGB value (white or black) based on the provided location
   */
  private ColorRGB checkerColor(int i, int j, int size) {
    int row = ImageUtil.whichColor(i, size);
    int column = ImageUtil.whichColor(j, size);
    if (row % 2 == 0 && column % 2 == 0) {
      return ColorRGB.BLACK;
    } else if (row % 2 == 0 && column % 2 != 0) {
      return ColorRGB.WHITE;
    } else if (row % 2 != 0 && column % 2 == 0) {
      return ColorRGB.WHITE;
    } else {
      return ColorRGB.BLACK;
    }

  }
}
