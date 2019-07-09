import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents an image.  An image is a sequence of individual pixels.  Each pixel has a
 * position in the image (a row and column) and a color.  The color of the pixel is stored using
 * three numbers to represent three color channels - red, green, and blue.  An image has an overall
 * width and height.  An image has methods to get its sequence of pixels, its width and height, and
 * to determine if one image is equal to another. There is also a method to get a hashcode.
 */
public class Image {
  /**
   * Sequence of pixels in this image.
   */
  private int[][][] rgb;
  /**
   * Width of this image.
   */
  private int width;
  /**
   * Height of this image.
   */
  private int height;

  /**
   * Construct an image from a 3D integer array of pixels.  The first level of the array represents
   * the row position and the second level of the array represents the row position for each pixel.
   * The third level of the array represents the red, green, and blue channels of each pixel (in
   * corresponding order).
   *
   * @param rgb 3D array of pixels for new Image.
   */
  public Image(int[][][] rgb) {
    this.rgb = rgb;
    this.height = rgb.length;
    this.width = rgb[0].length;
  }

  /**
   * Return the reference for a copy of the 3D integer array that contains pixel information for
   * this image.  The first level of the array represents the row position and the second level of
   * the array represents the row position for each pixel. The third level of the array represents
   * the red, green, and blue channels of each pixel (in corresponding order).
   *
   * @return reference for a copy of the 3D integer array that contains pixel information for this
   *          image.
   */
  public int[][][] getRgb() {
    int[][][] pixels = new int[this.height][this.width][3];
    // make a deep copy and return the new reference
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < 3; k++) {
          pixels[i][j][k] = rgb[i][j][k];
        }
      }
    }
    return pixels;
  }

  /**
   * Return the width of this image.  The width is the number of pixels in one row of this image.
   *
   * @return the width of this image.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Return the height of this image.  The height is the number of pixels in one column of this
   * image.
   *
   * @return the height of this image.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns true if other image is equal to this image.  Other image is equal if it is an instance
   * of the Image class, if the width and height of other image equals width and height of this
   * image, and if the values in the pixel array for both images are deeply equal.  This equals
   * method follows rules of reflexivity, symmetry, and transitivity.
   *
   * @return true if other image is equal to this image; false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Image)) {
      return false;
    }
    Image other = (Image) o;
    return (this.width == other.width && this.height == other.height
            && Arrays.deepEquals(this.rgb, other.rgb));
  }

  /**
   * Returns the hashcode of this object.
   *
   * @return the hashcode of the object based on the rgb, height, and width fields
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.rgb, this.height, this.width);
  }
}
