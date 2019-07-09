import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * This class represents a driver for our MVC model. It has one main method and no other methods.
 * The program can be run in either interactive mode or script mode according to the command line
 * argument. One of these modes must be chosen.
 */
public class MCVDriver {
  /**
   * The main method of this driver. This method sets up the model and the controller and if
   * appropriate sets up the view. It takes in a command line argument which specifies whether a
   * batch script is going to be run or whether a GUI should be launched for the user to interact
   * with. If the incorrect number of arguments is provided the method prints a message to the
   * console and returns.
   *
   * @param args command line arguments. Options are "-script" followed by a script path or
   *             "-interactive" to launch a GUI.
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("You must enter command line arguments");
      return;
    }
    try {
      // Set cross-platform Java L&F (also called "Metal")
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

    } catch (UnsupportedLookAndFeelException e) {
      System.out.println("Error");
    } catch (ClassNotFoundException e) {
      System.out.println("Error");
    } catch (InstantiationException e) {
      System.out.println("Error");
    } catch (IllegalAccessException e) {
      System.out.println("Error");
    } catch (Exception e) {
      System.out.println("Error");
    }

    View.setDefaultLookAndFeelDecorated(false);
    IModel model = new Model();
    Controller controller;

    if (args[0].equals("-script")) {
      // In script mode, construct a controller with only a model (no view)
      controller = new Controller(model);
      if (args.length < 2) {
        System.out.println("You must specify a file path");
        return;
      }
      try {
        // Controller takes control and directs input
        controller.controlGo(new FileInputStream(args[1]));
      } catch (IOException e) {
        System.out.println("Not a valid batch file");
      }
    } else if (args[0].equals("-interactive")) {
      // In interactive mode, construct a view and a controller with a model + view
      View view = new View("Image Processing");
      controller = new Controller(model, view);
      // Set the controller as the listener for the view
      view.setListener(controller);
      // Display the view
      view.display();
    } else {
      System.out.println("You must either specify -script with a script path or -interactive");
    }
  }
}

