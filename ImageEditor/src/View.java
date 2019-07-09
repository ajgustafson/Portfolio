import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import static java.lang.Integer.valueOf;

/**
 * Class to represent the view for this image processing application.  The view displays information
 * to the user of the application.  This view implements a graphical user interface where the user
 * can see the current loaded or generated image, can use menu lists to choose action items, can
 * click buttons to choose action items, and can type and run a batch script with image processing
 * commands.
 */
public class View extends JFrame implements IView {
  /**
   * Listener that waits for and handles user input.
   */
  private SelectionListener listener;
  /**
   * Panel on the left side of the view.  Contains display image and all buttons that are used for
   * interactive mode (other than batch script buttons).
   */
  private JPanel leftPanel;
  /**
   * Panel on the right side of the view.  Contains text box and buttons to run a batch script.
   */
  private JPanel batchPanel;
  /**
   * Displays title for the image to be displayed.
   */
  private JLabel imageLabel;
  /**
   * Display window for user to choose a file to open.
   */
  private JLabel fileOpenDisplay;
  /**
   * Display window for user to choose a file save path.
   */
  private JLabel fileSaveDisplay;
  /**
   * Text box for user to type batch script.
   */
  private JTextArea sTextArea;
  /**
   * Displays current image.
   */
  private ImageIcon image = new ImageIcon();

  /**
   * Construct a view with a given controller.  The view will display the given caption as its
   * title.  The view displays the image for the user along with menu items and buttons that allow
   * user to interactively run operations on the image and a panel for the user to run a batch type
   * script.  The view communicates user input to the given controller and the controller updates
   * the view accordingly.
   *
   * @param caption    to title this view
   */
  public View(String caption) {
    super(caption);
  }

  @Override
  public void setListener(IController controller) {
    listener = new SelectionListener(controller);
    listener.setUp();
  }

  @Override
  public void display() throws IllegalStateException {
    if (listener == null) {
      throw new IllegalStateException("Set up listener before displaying view.");
    }
    setSize(700, 400);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // create main panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

    // create left panel
    leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

    createImagePanel();
    createMenu();
    createUndoButton();
    createRedoButton();
    createFileButtons();
    createBatchPanel();

    // add left and right panels to main panel
    mainPanel.add(leftPanel, BorderLayout.LINE_START);
    mainPanel.add(batchPanel, BorderLayout.LINE_END);

    setContentPane(mainPanel);
    setVisible(true);
  }

  @Override
  public void updateImage(BufferedImage newImage) throws IllegalStateException {
    try {
      // ImageIcon uses a caching strategy so you need to flush each time this is called
      ImageIcon tempImage = new ImageIcon(newImage);
      tempImage.getImage().flush();
      image.setImage(tempImage.getImage());
      imageLabel.setIcon(image);
      // update UI
      imageLabel.repaint();
      // put scrollbar back in if necessary
      imageLabel.revalidate();
    } catch (IllegalStateException e) {
      showMessage(e.getMessage(), true);
    }
  }

  /**
   * Create a Jpanel object that will hold an image. This panel is added to the left JPanel of the
   * main panel. The image is initialized to be a grey box when first created. If an image is too
   * big for the dimensions of the panel then a scrollbar is displayed.
   */
  private void createImagePanel() {
    // image panel
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Your image"));
    imagePanel.setLayout(new GridLayout(1, 0, 30, 30));
    leftPanel.add(imagePanel);
    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);

    imageLabel.setIcon(image);
    imageScrollPane.setPreferredSize(new Dimension(100, 300));
    imagePanel.add(imageScrollPane);
    leftPanel.add(imagePanel);
  }

  /**
   * Create all the different menu bars. There is a file menu with items for opening and saving
   * files. There is an edit menu with undo and redo items. There is a filter menu that has options
   * for blur, dither, greyscale, mosaic, sepia, and sharpen. Lastly there is generate menu with
   * options checkerboard, horizontal rainbow, and vertical rainbow. Action listeners for each menu
   * item are also added to the created menu in this function.
   */
  private void createMenu() {
    // Create menu bar
    JMenuBar menuBar = new JMenuBar();
    // Create file menu
    JMenu fileMenu = new JMenu("File");
    JMenuItem load = new JMenuItem("Open file");
    load.addActionListener(listener);
    JMenuItem save = new JMenuItem("Save file");
    save.addActionListener(listener);
    fileMenu.add(load);
    fileMenu.add(save);
    menuBar.add(fileMenu);
    // Create edit menu
    JMenu editMenu = new JMenu("Edit");
    JMenuItem undo = new JMenuItem("Undo");
    undo.addActionListener(listener);
    JMenuItem redo = new JMenuItem("Redo");
    redo.addActionListener(listener);
    editMenu.add(undo);
    editMenu.add(redo);
    menuBar.add(editMenu);
    // Create Filter menu
    JMenu filterMenu = new JMenu("Filter");
    JMenuItem blur = new JMenuItem("Blur");
    blur.addActionListener(listener);
    JMenuItem dither = new JMenuItem("Dither");
    dither.addActionListener(listener);
    JMenuItem greyscale = new JMenuItem("Greyscale");
    greyscale.addActionListener(listener);
    JMenuItem mosaic = new JMenuItem("Mosaic");
    mosaic.addActionListener(listener);
    JMenuItem sepia = new JMenuItem("Sepia");
    sepia.addActionListener(listener);
    JMenuItem sharpen = new JMenuItem("Sharpen");
    sharpen.addActionListener(listener);
    filterMenu.add(blur);
    filterMenu.add(dither);
    filterMenu.add(greyscale);
    filterMenu.add(mosaic);
    filterMenu.add(sepia);
    filterMenu.add(sharpen);
    menuBar.add(filterMenu);

    // Create generate menu
    JMenu generateMenu = new JMenu("Generate");
    JMenuItem checkerboard = new JMenuItem("Checkerboard");
    checkerboard.addActionListener(listener);
    JMenuItem horzRainbow = new JMenuItem("Horizontal rainbow");
    horzRainbow.addActionListener(listener);
    JMenuItem vertRainbow = new JMenuItem("Vertical rainbow");
    vertRainbow.addActionListener(listener);
    generateMenu.add(checkerboard);
    generateMenu.add(horzRainbow);
    generateMenu.add(vertRainbow);
    menuBar.add(generateMenu);

    // Create batch menu
    JMenu batchMenu = new JMenu("Batch");
    JMenuItem batch = new JMenuItem("Run batch");
    batch.addActionListener(listener);
    batchMenu.add(batch);
    menuBar.add(batchMenu);

    super.setJMenuBar(menuBar);
  }

  /**
   * Create a button for "undo." This button will undo the most recent filter option. This button is
   * placed in the left panel of the main panel. An action listener for the button is also added in
   * this method.
   */
  private void createUndoButton() {
    // Undo button
    JPanel undoPanel = new JPanel();
    undoPanel.setLayout(new FlowLayout());
    leftPanel.add(undoPanel);
    JButton undoButton = new JButton("Undo");
    undoButton.setActionCommand("Undo");
    undoButton.addActionListener(listener);
    undoPanel.add(undoButton);
  }

  /**
   * Create a button for "redo." This button will redo the most recent filter option that has been
   * undone. This button is placed in the left panel of the main panel under the undo button. An
   * action listener for the button is also added in this method.
   */
  private void createRedoButton() {
    // Redo button
    JPanel redoPanel = new JPanel();
    redoPanel.setLayout(new FlowLayout());
    leftPanel.add(redoPanel);
    JButton redoButton = new JButton("Redo");
    redoButton.setActionCommand("Redo");
    redoButton.addActionListener(listener);
    redoPanel.add(redoButton);
  }

  /**
   * Create load image and save image buttons. These buttons are in the the left panel of the main
   * panel under the undo and redo buttons. An action listener for each of these buttons is also
   * added in this method.
   */
  private void createFileButtons() {
    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    leftPanel.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Load image");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(listener);
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    leftPanel.add(filesavePanel);
    JButton fileSaveButton = new JButton("Save image");
    fileSaveButton.setActionCommand("Save file");
    fileSaveButton.addActionListener(listener);
    filesavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);
  }

  /**
   * This method creates a batch script text box for the user and adds a run batch script button.
   * These components are placed in the right panel of the main panel. The text box for the batch
   * script has a scrollbar if needed. An action listener is added to the run batch button.
   */
  private void createBatchPanel() {
    // create batch panel
    batchPanel = new JPanel();
    batchPanel.setLayout(new BoxLayout(batchPanel, BoxLayout.Y_AXIS));

    // Create batch script area
    sTextArea = new JTextArea(1, 5);
    JScrollPane scrollPane = new JScrollPane(sTextArea);
    sTextArea.setLineWrap(true);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Batch script"));
    batchPanel.add(scrollPane);

    // Create button for batch script
    JButton runBatch = new JButton("Run batch script");
    runBatch.setActionCommand("Run batch");
    runBatch.addActionListener(listener);
    batchPanel.add(runBatch);
  }

  /**
   * Show a message to the user in a dialog box with the provided message. If the message is an
   * error make the message an ERROR_MESSAGE, otherwise make it a PLAIN_MESSAGE.
   *
   * @param message the message you wish to be displayed
   * @param error   true if the message represents an error, false otherwise
   */
  private void showMessage(String message, boolean error) {
    if (error) {
      JOptionPane.showMessageDialog(View.this, message, "Error",
              JOptionPane.ERROR_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(View.this, message, "Message",
              JOptionPane.PLAIN_MESSAGE);
    }
  }

  /**
   * Shows a dialog box with given message and a text box for user input.  Gets and returns an
   * integer value from user.  If user does not type an integer, an error message is displayed,
   * prompting the user to enter an integer.  This process continues until the user enters a valid
   * integer.
   *
   * @param message describing the input needed from the user.  This message will be displayed to
   *                the user above the text box.
   * @return integer value that user inputs.
   */
  private int getIntegerInput(String message) {
    while (true) {
      String input = JOptionPane.showInputDialog(message);
      try {
        return valueOf(input);
      } catch (NumberFormatException e) {
        showMessage("You must choose a valid integer", true);
      }
    }
  }



  /**
   * Private class representing listeners for all of the menu items and buttons a user can select in
   * the view.  A selection listener performs an action when it receives an ActionEvent from a menu
   * item or button.
   */
  private class SelectionListener implements ActionListener {
    /**
     * Map that contains all actions that SelectionListeners can perform.  Maps command string keys
     * to their Runnable object values.
     */
    private Map<String, Runnable> actionMap;
    /**
     * Controller for this view.
     */
    private IController controller;

    /**
     * Construct a SelectionListener object.
     *
     * @param controller for this view.
     */
    public SelectionListener(IController controller) {
      this.controller = controller;
    }

    /**
     * Set up the actionMap with all actions that SelectionListeners can perform.  Map keys are
     * Strings that represent action commands.  Map values are Runnable objects that run the
     * corresponding command.
     */
    public void setUp() {
      actionMap = new HashMap<>();
      actionMap.put("Open file", new Open());
      actionMap.put("Save file", new Save());
      actionMap.put("Run batch", new Batch());
      actionMap.put("Undo", new Undo());
      actionMap.put("Redo", new Redo());
      actionMap.put("Blur", new BlurOp());
      actionMap.put("Dither", new DitherOp());
      actionMap.put("Greyscale", new GreyOp());
      actionMap.put("Mosaic", new MosaicOp());
      actionMap.put("Sepia", new SepiaOp());
      actionMap.put("Sharpen", new SharpenOp());
      actionMap.put("Checkerboard", new CheckerboardOp());
      actionMap.put("Horizontal rainbow", new HorizontalRainbowOp());
      actionMap.put("Vertical rainbow", new VerticalRainbowOp());
    }

    /**
     * This method specifies what action(s) should happen given a provided action event and it
     * executes the running of these actions.
     *
     * @param e the action event that happened
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      if (actionMap.containsKey(e.getActionCommand())) {
        actionMap.get(e.getActionCommand()).run();
      }
    }

    /**
     * Class that represents the action performed when user chooses to open a file.
     */
    private class Open implements Runnable {
      public void run() {
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, GIF, & PNG Images", "jpg", "gif", "png");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(View.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          fileOpenDisplay.setText(f.getAbsolutePath());
          showMessage("Processing...", false);
          try {
            controller.loadFile(f.getAbsolutePath());
          } catch (IOException error) {
            showMessage("That's not a valid filepath", true);
          }
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to save a file.
     */
    private class Save implements Runnable {
      public void run() {
        final JFileChooser fchooser = new JFileChooser(".");
        int retvalue = fchooser.showSaveDialog(View.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          fileSaveDisplay.setText(f.getAbsolutePath());
          try {
            controller.saveFile(f.getAbsolutePath());
          } catch (IOException error) {
            showMessage("That's not a valid filepath", true);
          }
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to run a batch file.
     */
    private class Batch implements Runnable {
      @Override
      public void run() {
        showMessage("Processing...", false);
        try {
          controller.controlGo(new ByteArrayInputStream(sTextArea.getText().getBytes()));
          sTextArea.setText("");
        } catch (Exception error) {
          showMessage(error.getMessage(), true);
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to undo an action.
     */
    private class Undo implements Runnable {
      @Override
      public void run() {
        try {
          controller.undo();
        } catch (Exception e) {
          showMessage(e.getMessage(), true);
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to redo an undo action.
     */
    private class Redo implements Runnable {
      @Override
      public void run() {
        try {
          controller.redo();
        } catch (Exception e) {
          showMessage(e.getMessage(), true);
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to blur an image.
     */
    private class BlurOp implements Runnable {
      @Override
      public void run() {
        try {
          controller.blur();
        } catch (IllegalStateException error) {
          showMessage("You must load a picture first", true);
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to dither an image.
     */
    private class DitherOp implements Runnable {
      @Override
      public void run() {
        try {
          controller.dither();
        } catch (IllegalStateException error) {
          showMessage("You must load a picture first", true);
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to greyscale an image.
     */
    private class GreyOp implements Runnable {
      @Override
      public void run() {
        try {
          controller.greyscale();
        } catch (IllegalStateException error) {
          showMessage("You must load a picture first", true);
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to mosaic an image. The user
     * must specify a seed size through a message box.
     */
    private class MosaicOp implements Runnable {
      @Override
      public void run() {
        try {
          int seed = getIntegerInput("What seed size do you want?");
          showMessage("Processing...", false);
          controller.mosaic(seed);
        } catch (IllegalArgumentException e) {
          showMessage(e.getMessage(), true);
        } catch (IllegalStateException e) {
          showMessage("You must have loaded an image first", true);
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to sepia an image.
     */
    private class SepiaOp implements Runnable {
      @Override
      public void run() {
        try {
          controller.sepia();
        } catch (IllegalStateException error) {
          showMessage("You must load a picture first", true);
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to sharpen an image.
     */
    private class SharpenOp implements Runnable {
      @Override
      public void run() {
        try {
          controller.sharpen();
        } catch (IllegalStateException error) {
          showMessage("You must load a picture first", true);
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to create a checkerboard image.
     * The user must put in a size for squares through a message box.
     */
    private class CheckerboardOp implements Runnable {
      @Override
      public void run() {
        int size = getIntegerInput("What size squares do you want?");
        try {
          controller.checkers(size);
        } catch (IllegalArgumentException e) {
          showMessage(e.getMessage(), true);
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to create a horizontal rainbow
     * image. The user must specify a height and width through a message box.
     */
    private class HorizontalRainbowOp implements Runnable {
      @Override
      public void run() {
        int width = getIntegerInput("What image width do you want?");
        int height = getIntegerInput("What image height do you want?");
        try {
          controller.horizRainbow(width, height);
        } catch (IllegalArgumentException e) {
          showMessage(e.getMessage(), true);
        }
      }
    }

    /**
     * Class that represents the action performed when user chooses to create a vertical rainbow
     * image. The user must specify a height and width through a message box.
     */
    private class VerticalRainbowOp implements Runnable {
      @Override
      public void run() {
        int width = getIntegerInput("What image width do you want?");
        int height = getIntegerInput("What image height do you want?");
        try {
          controller.vertRainbow(width, height);
        } catch (IllegalArgumentException e) {
          showMessage(e.getMessage(), true);
        }
      }
    }
  }
}






