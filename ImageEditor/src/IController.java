import java.io.IOException;
import java.io.InputStream;

/**
 * This interface represents a controller in a Model, Controller design pattern.  The controller can
 * take input and decide what to do based on the input.  It controls how and when the model is
 * used.
 */
public interface IController {
  /**
   * Give control of given model to this controller and run the program.  Through this method,
   * controller decides which model methods to call.  It controls the flow of how and when model
   * methods are used.
   *
   * @param in the input stream that is being parsed
   * @throws NullPointerException     if the model does not have necessary data to run operations.
   * @throws IOException              if file path names given to the controller through driver are
   *                                  invalid.
   * @throws IllegalArgumentException if arguments needed for model are invalid.
   */
  void controlGo(InputStream in) throws NullPointerException, IOException, IllegalArgumentException;

  /**
   * Tell the model to sharpen the current image in the model.
   *
   * @throws IllegalStateException if there is no current model image
   */
  void sharpen() throws IllegalStateException;

  /**
   * Tell the model to blur the current image in the model.
   *
   * @throws IllegalStateException if there is no current model image
   */
  void blur() throws IllegalStateException;

  /**
   * Tell the model to convert the current image in the model to greyscale.
   *
   * @throws IllegalStateException if there is no current model image
   */
  void greyscale() throws IllegalStateException;

  /**
   * Tell the model to convert the current image in the model to sepia.
   *
   * @throws IllegalStateException if there is no current model image
   */
  void sepia() throws IllegalStateException;

  /**
   * Tell the model to dither the current image in the model.
   *
   * @throws IllegalStateException if there is no current model image
   */
  void dither() throws IllegalStateException;

  /**
   * Tell the model to mosaic the current image in the model using the provided seed.
   *
   * @throws IllegalStateException    if there is not current model image
   * @throws IllegalArgumentException if the seed is less than 1 or greater than the number of `
   *                                  pixels
   */
  void mosaic(int seed) throws IllegalStateException, IllegalArgumentException;

  /**
   * Tell the model to generate a vertical rainbow image with the provided width and height.
   *
   * @param width  the width of the image to be generated
   * @param height the height of the image to be generated
   * @throws IllegalArgumentException if the width is less than 16 (this is the smallest possible
   *                                  width to ensure there is at least one stripe of each color),
   *                                  or if the height is less than one (a picture must be at least
   *                                  one pixel wide)
   */
  void vertRainbow(int width, int height) throws IllegalArgumentException;

  /**
   * Tell the model to generate a horizontal rainbow image with the provided width and height.
   *
   * @param width  the width of the image to be generated
   * @param height the height of the image to generated
   * @throws IllegalArgumentException if the height is less than 16 (this is the smallest possible
   *                                  height to ensure there is at least one stripe of each color),
   *                                  or if the width is less than one (a picture must be at least
   *                                  one pixel wide)
   */
  void horizRainbow(int width, int height) throws IllegalArgumentException;

  /**
   * Tell the model to generate a white and black checker board image with tiles with the size
   * provided.
   *
   * @param size the size in pixels of each tile width and height
   * @throws IllegalArgumentException if the size is less than 1 because each tile must be at least
   *                                  one pixel wide
   */
  void checkers(int size) throws IllegalArgumentException;

  /**
   * Tell the model to load the provided file.
   *
   * @param filename the name/path of the file to load
   * @throws IOException if the file path does not exist
   */
  void loadFile(String filename) throws IOException;

  /**
   * Tell the model to save the the current image in the model.
   *
   * @param filename the name/path of the file to save
   * @throws IOException if the file path does not exist
   */
  void saveFile(String filename) throws IOException;

  /**
   * Tells the model to undo the last edit operation on current image.  Returns
   * IllegalStateException if there is no edit to undo.
   *
   * @throws IllegalStateException if there is no edit on the current image to undo
   */
  void undo() throws IllegalStateException;

  /**
   * Tells the model to redo the last undo operation on current image.  Returns
   * IllegalStateException if there is no undo to redo.
   *
   * @throws IllegalStateException if there is no undo on the current image to redo
   */
  void redo() throws IllegalStateException;
}
