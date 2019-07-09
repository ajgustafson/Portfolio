import java.awt.image.BufferedImage;

/**
 * This interface represents all of the operations a model should be able to perform. It can do
 * image processing techniques like blur, mosaic, dither, sharpen, and convert to grey scale or
 * sepia. It can undo and redo image processing operations on a single image.  It can also load or
 * save an image and generate different image patterns.
 */
public interface IModel {
  /**
   * Perform a blur operation on an image. A blur is done by applying a 3x3 filter.
   *
   * @throws IllegalStateException if there is no current image loaded into the model
   */
  void blur() throws IllegalStateException;

  /**
   * Apply a process to an image that gives the image a "stained glass window" effect. This is done
   * by breaking the image down by choosing a set of points in the image (called seeds). Each pixel
   * in the image is then paired to the seed that is closest to it (by distance). This creates a
   * cluster of pixels for each seed. Then the color of each pixel in the image is replaced by the
   * average color of its cluster.
   *
   * @throws NullPointerException  if there is not current image loaded into the model
   * @throws IllegalStateException if the seed is less than 1 or if seed value is greater than the
   *                               number of pixels in the current image
   */
  void mosaic(int seed) throws IllegalStateException, IllegalArgumentException;

  /**
   * Perform a dithering operation on an image. Dithering is the process of breaking down an image
   * that has many colors into an image that is made of dots from just a few colors.
   *
   * @throws IllegalStateException if there is not current image loaded into the model
   */
  void dither() throws IllegalStateException;

  /**
   * Perform a sharpen operation on an image. Sharpening is done by apply a 5x5 filter.
   *
   * @throws IllegalStateException if there is no current image loaded into the model
   */
  void sharpen() throws IllegalStateException;

  /**
   * Convert an image to greyscale. A greyscale image is composed only of shades of grey. This is
   * done by applying the simple color transformation r' = g' = b' = .2126r + .7152g + .0722b to
   * each pixel.
   *
   * @throws IllegalStateException if there is no current image loaded into the model
   */
  void greyscale() throws IllegalStateException;

  /**
   * Convert an image to sepia tone. Sepia is a characteristic reddish brown tone that photos in th
   * e19th and early 20th century had. This is done using a color transformation.
   *
   * @throws IllegalStateException if there is no current image loaded into the model
   */
  void sepia() throws IllegalStateException;

  /**
   * Get the current image in the model.  If there is no current image in the model,
   * IllegalStateException is thrown.
   *
   * @throws IllegalStateException if the current image in the model is null.
   */
  BufferedImage getImage() throws IllegalStateException;

  /**
   * Load an image into the model.
   *
   * @param rgb 3D RGB pixel array for the image to be loaded
   */
  void loadImage(int[][][] rgb);

  /**
   * Undo last edit operation on an image.  This method replaces the current image in the model with
   * the image version directly preceding current image.  If no previous images exist, undo throws
   * an illegal state exception because no action exists to be undone.
   *
   * @throws IllegalStateException if there are no changes to undo.
   */
  void undo() throws IllegalStateException;

  /**
   * Redo a change to an image that was undone.  This method replaces the current image in the model
   * with the image version that existed before "undo" method called.  Redo must be called directly
   * after undo.  Redo may be called multiple times after a series of undos.  If there is no image
   * to redo, an illegal state exception is thrown.
   *
   * @throws IllegalStateException if there is no "undone" image to redo.
   */
  void redo() throws IllegalStateException;

  /**
   * Resets the state of the model by emptying undo/redo stacks and setting the current image to
   * null.  If the stacks are already empty, or the image is already null, no change is made.
   */
  void reset();

  /**
   * Generates a square rainbow with Red, Orange, Yellow, Green, Blue, Indigo, Violet colored
   * vertical stripes.
   *
   * @param width  the width of the image to be generated
   * @param height the height of the image to be generated
   * @throws IllegalArgumentException if the width is less than 16 (this is the smallest possible
   *                                  width to ensure there is at least one stripe of each color),
   *                                  or if the height is less than one (a picture must be at least
   *                                  one pixel wide)
   */
  void generateVertRainbow(int width, int height) throws IllegalArgumentException;

  /**
   * Generates a square rainbow with Red, Orange, Yellow, Green, Blue, Indigo, Violet colored
   * horizontal stripes.
   *
   * @param width  the width of the image to be generated
   * @param height the height of the image to be generated
   * @throws IllegalArgumentException if the height is less than 16 (this is the smallest possible
   *                                  height to ensure there is at least one stripe of each color),
   *                                  or if the width is less than one (a picture must be at least
   *                                  one pixel wide)
   */
  void generateHorizRainbow(int width, int height) throws IllegalArgumentException;

  /**
   * Generate a checkerboard pattern of a specified size. A checkerboard is an 8x8 board of
   * alternating black and white squares.
   *
   * @param size the size (in pixels) of each individual tile's width and height
   * @throws IllegalArgumentException if the size is less than 1 because each tile must be at least
   *                                  one pixel wide
   */
  void generateCheckers(int size) throws IllegalArgumentException;

  /**
   * Generates and returns an image of the French flag of given pixel width.  For a clear design,
   * the given pixel width must be greater than or equal to 3 pixels.
   *
   * @param size Desired pixel width of generated image.
   * @throws IllegalArgumentException if size entered is less than 3 pixels.
   */
  void generateFrenchFlag(int size) throws IllegalArgumentException;

  /**
   * Generates and returns an image of the Greek flag of given pixel width.  For a clear design, the
   * given pixel width must be greater than or equal to 27 pixels.
   *
   * @param size Desired pixel width of generated image.
   * @throws IllegalArgumentException if size entered is less than 27 pixels.
   */
  void generateGreekFlag(int size) throws IllegalArgumentException;

  /**
   * Generates and returns an image of the Swiss flag of given pixel width.  For a clear design, the
   * given pixel width must be greater than or equal to 32 pixels.
   *
   * @param size Desired pixel width of generated image.
   * @throws IllegalArgumentException if size entered is less than 32 pixels.
   */
  void generateSwissFlag(int size) throws IllegalArgumentException;
}
