import java.awt.image.BufferedImage;

/**
 * This interface represents a View in a Model, Controller, View design pattern.  This view
 * functions as the user interface for an image processing application.  The view can be updated
 * with the current image in the model.
 */
public interface IView {

  /**
   * Sets an action listener for the view.  The action listener will be notified when the user
   * interacts with the view.
   *
   * @param controller action listener for this view.
   */
  void setListener(IController controller);

  /**
   * Display the view after it is all set up.  This method should only be called after calling
   * setListener to set up the listener for all menus and buttons.  After calling this method, the
   * view is ready to display information to user, receive user input, and pass user input on to the
   * controller.
   *
   * @throws IllegalStateException if the listener for the view has not been set
   */
  void display() throws IllegalStateException;

  /**
   * Updates the image display in the view.  If there is no image in the model to update, an error
   * message is displayed to the user and the program continues.
   *
   * @throws IllegalStateException if there is no image to update
   */
  void updateImage(BufferedImage newImage) throws IllegalStateException;
}
