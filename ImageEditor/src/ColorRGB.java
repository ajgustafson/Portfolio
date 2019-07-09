/**
 * This enum represents a color. A color has a name and an rgb value. An rgb value is a three
 * element array with each element ranging from 0-255.
 */
public enum ColorRGB {
  RED(new int[]{255, 0, 0}), ORANGE(new int[]{255, 165, 0}),
  YELLOW(new int[]{255, 255, 0}), GREEN(new int[]{0, 255, 0}),
  BLUE(new int[]{0, 0, 255}), INDIGO(new int[]{75, 0, 130}),
  VIOLET(new int[]{148, 0, 211}), WHITE(new int[]{255, 255, 255}),
  BLACK(new int[]{0, 0, 0});

  /**
   * A 3 element array representing rgb values.
   */
  int[] rgb;

  /**
   * Initialize the rgb field for a specified color.
   *
   * @param rgb the rgb value array for a given color
   */
  private ColorRGB(int[] rgb) {
    this.rgb = rgb;
  }

  /**
   * Return a copy of the rgb array for a specified color.
   *
   * @return a copy of the rgb array for a color
   */
  public int[] getRGB() {
    return rgb.clone();
  }
}
