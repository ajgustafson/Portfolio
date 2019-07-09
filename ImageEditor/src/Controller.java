import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

import static java.lang.Integer.valueOf;

/**
 * This class represents a controller for a model, controller design pattern.  This controller has
 * an InputStream to receive user input in the form of a batch type text file.  Refer to the README
 * file for this program to see all current commands supported by this controller as well as proper
 * input format.  This controller takes the input file and determines how and when to call model
 * methods to complete all tasks in the input.
 */
public class Controller implements IController {

  /**
   * Store a reference to a model object that implements the IModel interface. This is necessary so
   * that the controller can directly interact with the model.
   */
  private IModel model;

  private IView view;

  /**
   * Construct a controller object that controls a model only (does not have a view). This
   * controller has a reference to an IModel object.
   *
   * @param model the model to be used by this controller
   */
  public Controller(IModel model) {
    this.model = model;
  }

  /**
   * Construct a controller object that controls a model and a view.  This controller has references
   * to an IModel and and IView.
   *
   * @param model the model to be used by this controller
   * @param view  the view to be used by this controller
   */
  public Controller(IModel model, IView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * This method allows the controller to direct a model only through a text input stream.  This
   * method has no view implementation.  Through this method, controller interprets user input and
   * decides which model methods to call.  It controls the flow of how and when model methods are
   * used.  The text input can be in a batch type series of commands.  Commands must each be on
   * separate lines, while arguments on a single line must be separated by spaces.  Consult the
   * README file for supported commands.  Resets the model after completing all commands.
   *
   * @param in the Input stream that will be parsed
   * @throws NullPointerException     if there is no uploaded image in the model when editing.
   * @throws IOException              if a user provided file path is invalid.
   * @throws IllegalArgumentException if an argument needed for model methods is missing or
   *                                  invalid.
   */
  @Override
  public void controlGo(InputStream in) throws NullPointerException, IOException,
          IllegalArgumentException {
    // Argument for model methods that accept image size as a parameter
    int size;
    // Scan from this controller's input stream
    Scanner sc = new Scanner(in);

    // Continue to interpret commands as long as there is another line in the input file
    // or user input is "quit"
    while (sc.hasNextLine()) {
      // Read input file line by line
      String line = sc.nextLine();
      // Split a single line into an array of string tokens (spaces as delimiter)
      String[] tokens = line.split("\\s+");

      // Interpret commands that require an argument for the model method
      if (tokens[0].equals("load")) {
        // Check that there is an additional command to pass to the model method as an argument
        checkArgs(tokens);
        // Read command argument as 3D pixel array
        int[][][] rbg = ImageUtil.readImage(tokens[1]);
        model.loadImage(rbg);
      } else if (tokens[0].equals("save")) {
        checkArgs(tokens);
        saveFile(tokens[1]);

        // The next group of commands require one integer parameter
        // In addition to checking for an argument, get the argument as an integer
      } else if (tokens[0].equals("checkers")) {
        checkArgs(tokens);
        size = getDigit(tokens[1]);
        model.generateCheckers(size);
      } else if (tokens[0].equals("frenchFlag")) {
        checkArgs(tokens);
        size = getDigit(tokens[1]);
        model.generateFrenchFlag(size);
      } else if (tokens[0].equals("greekFlag")) {
        checkArgs(tokens);
        size = getDigit(tokens[1]);
        model.generateGreekFlag(size);
      } else if (tokens[0].equals("horizontalRainbow")) {
        checkArgs3(tokens);
        int width = getDigit(tokens[1]);
        int height = getDigit(tokens[2]);
        model.generateHorizRainbow(width, height);
      } else if (tokens[0].equals("swissFlag")) {
        checkArgs(tokens);
        size = getDigit(tokens[1]);
        model.generateSwissFlag(size);
      } else if (tokens[0].equals("verticalRainbow")) {
        checkArgs3(tokens);
        int width = getDigit(tokens[1]);
        int height = getDigit(tokens[2]);
        model.generateVertRainbow(width, height);
      } else if (tokens[0].equals("mosaic")) {
        checkArgs(tokens);
        int seed = getDigit(tokens[1]);
        model.mosaic(seed);

        // These commands do not require any arguments
      } else if (tokens[0].equals("blur")) {
        model.blur();
      } else if (tokens[0].equals("dither")) {
        model.dither();
      } else if (tokens[0].equals("greyscale")) {
        model.greyscale();
      } else if (tokens[0].equals("sepia")) {
        model.sepia();
      } else if (tokens[0].equals("sharpen")) {
        model.sharpen();

        // Exit while loop and stop controller if input is "quit"
      } else if (tokens[0].equals("quit")) {
        model.reset();
        break;

        // If the command is not one of the above commands, throw exception
      } else {
        model.reset();
        throw new IllegalArgumentException("Could not process command.");
      }

    }
    model.reset();
  }

  @Override
  public void sharpen() throws IllegalStateException {
    model.sharpen();
    view.updateImage(model.getImage());
  }

  @Override
  public void blur() throws IllegalStateException {
    model.blur();
    view.updateImage(model.getImage());
  }

  @Override
  public void greyscale() throws IllegalStateException {
    model.greyscale();
    view.updateImage(model.getImage());
  }

  @Override
  public void sepia() throws IllegalStateException {
    model.sepia();
    view.updateImage(model.getImage());
  }

  @Override
  public void dither() throws IllegalStateException {
    model.dither();
    view.updateImage(model.getImage());
  }

  @Override
  public void mosaic(int seed) throws IllegalStateException, IllegalArgumentException {
    model.mosaic(seed);
    view.updateImage(model.getImage());
  }

  @Override
  public void vertRainbow(int width, int height) throws IllegalArgumentException {
    model.generateVertRainbow(width, height);
    view.updateImage(model.getImage());
  }

  @Override
  public void horizRainbow(int width, int height) throws IllegalArgumentException {
    model.generateHorizRainbow(width, height);
    view.updateImage(model.getImage());
  }

  @Override
  public void checkers(int size) throws IllegalArgumentException {
    model.generateCheckers(size);
    view.updateImage(model.getImage());
  }

  @Override
  public void loadFile(String filename) throws IOException {
    int[][][] rgb = ImageUtil.readImage(filename);
    model.loadImage(rgb);
    view.updateImage(model.getImage());
  }

  @Override
  public void saveFile(String filename) throws IOException {
    BufferedImage image = model.getImage();
    String extension = filename.substring(filename.indexOf(".") + 1);
    ImageIO.write(image, extension, new FileOutputStream(filename));
  }

  @Override
  public void undo() throws IllegalStateException {
    model.undo();
    view.updateImage(model.getImage());
  }

  @Override
  public void redo() throws IllegalStateException {
    model.redo();
    view.updateImage(model.getImage());
  }

  /**
   * Verify input for method commands that require one argument.  If token array has exactly 2
   * items, do nothing - the tokens can be used to call a method.  Otherwise, throw
   * IllegalArgumentException, as the input has too many or too few instructions.
   *
   * @param tokens String array of command tokens to be checked.
   * @throws IllegalArgumentException if there is not exactly 2 string tokens (one method command
   *                                  and one method argument)
   */
  private void checkArgs(String[] tokens) throws IllegalArgumentException {
    if (tokens.length != 2) {
      throw new IllegalArgumentException("Incorrect number of command arguments.");
    }
  }

  /**
   * Verify input for method commands that require two arguments.  If token array has exactly 3
   * items, do nothing - the tokens can be used to call a method.  Otherwise, throw
   * IllegalArgumentException, as the input has too many or too few instructions.
   *
   * @param tokens String array of command tokens to be checked.
   * @throws IllegalArgumentException if there is not exactly 3 string tokens (one method command
   *                                  and two method arguments)
   */
  private void checkArgs3(String[] tokens) throws IllegalArgumentException {
    if (tokens.length != 3) {
      throw new IllegalArgumentException("Incorrect number of command arguments.");
    }
  }

  /**
   * Modify input for method commands that require an integer argument.  This method takes a string
   * token and converts it to an int if the token represents a positive integer.
   *
   * @param token String token to be converted to an int.
   * @return int representation of given token.
   * @throws IllegalArgumentException if token does not represent a positive integer.
   */
  private int getDigit(String token) throws IllegalArgumentException {
    Integer digit;
    try {
      digit = valueOf(token);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid command argument - argument not an integer.");
    }
    return digit.intValue();
  }
}
