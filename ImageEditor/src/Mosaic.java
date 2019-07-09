import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a mosaic operation. This is an image process that gives an image a "stained
 * glass window" effect in which the picture consists of joined, small irregular-shaped clusters of
 * colors.
 */
public class Mosaic {
  /**
   * Create an image mosaic. This is done by randomly picking a set of points in the image called
   * seeds. Each pixel in the image is then paired to the seed that is closest to it (by euclidean
   * distance). This creates a cluster of pixels for each seed. Then the color of each pixel in the
   * image is replaced with the average color of its cluster.
   *
   * @param image    the original image to be operated on
   * @param numSeeds the number of seeds for the operation
   * @return an image object with the data for the mosaic image
   * @throws IllegalArgumentException if numSeeds is greater than the number of pixels in the given
   *                                  image
   */
  public Image edit(Image image, int numSeeds) throws IllegalArgumentException {
    if (numSeeds < 1) {
      throw new IllegalArgumentException("Seed must be greater than 0");
    }
    checkNumSeeds(image, numSeeds);
    // pick our random pixel seeds
    List<Point2D> seeds = pickSeeds(image, numSeeds);
    int height = image.getHeight();
    int width = image.getWidth();
    // variable that holds the classification to a seed for a pixel
    int classified;
    // pixel array of original image
    int[][][] rgb = image.getRgb();
    // variable that keeps track of total sum for each rgb value for a seed
    int[][] sum = new int[numSeeds][3];
    // a 2D array that has the classification for each pixel
    int[][] classifiedImage = new int[height][width];
    // number of entries for each seed
    int[] entries = new int[numSeeds];
    // loop through each pixel in the original image
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        // create a point object for the pixel
        Point2D point = new Point2D(r, c);
        // classify the point
        classified = point.minDistance(seeds);
        // update classifiedImage array and number of entries
        classifiedImage[r][c] = classified;
        entries[classified] += 1;
        for (int k = 0; k < 3; k++) {
          // update sum array for appropriate channel
          sum[classified][k] += rgb[r][c][k];
        }
      }
    }
    // get the average of each rgb value for a cluster
    int[][] averaged = new int[numSeeds][3];
    for (int i = 0; i < numSeeds; i++) {
      for (int k = 0; k < 3; k++) {
        averaged[i][k] = Math.round(sum[i][k] / entries[i]);
      }
    }
    return generateImage(classifiedImage, averaged, height, width);
  }

  /**
   * Randomly pick seeds for the mosaic algorithm. The seeds are returned in a list that are of size
   * = numSeeds. There are no duplicates allowed in the returned list.
   *
   * @param i        the image that is being mosaic'd
   * @param numSeeds the number of seeds for the mosaic operation
   * @return a list of Point2D objects that represents the seeds to be used
   */
  private List<Point2D> pickSeeds(Image i, int numSeeds) {
    Random r = new Random();
    List<Point2D> seeds = new ArrayList<>();
    while (seeds.size() < numSeeds) {
      int row = r.nextInt(i.getHeight());
      int column = r.nextInt(i.getWidth());
      Point2D seed = new Point2D(row, column);
      if (!(seeds.contains(seed))) {
        seeds.add(seed);
      }
    }
    return seeds;
  }

  /**
   * Generate the new image based the mosaic algorithm. Returns this new image.
   *
   * @param classified a 2D array that contains the classification for each pixel
   * @param averaged   a 2D array that has the averaged rgb value for each classification
   * @param height     the height of the image to be generated
   * @param width      the width of the image to be generated
   * @return a mosaic'd image
   */
  private Image generateImage(int[][] classified, int[][] averaged, int height, int width) {
    int[][][] newImage = new int[height][width][3];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        int classification = classified[r][c];
        for (int k = 0; k < 3; k++) {
          newImage[r][c][k] = averaged[classification][k];
        }
      }
    }
    return new Image(newImage);
  }

  /**
   * Checks that the number of seeds argument for mosaic does not exceed the number of pixels in the
   * image.  If the number of seeds does exceed the number of pixels, an illegal argument exception
   * is thrown.
   *
   * @param image    being edited by mosaic
   * @param numSeeds for mosaic operation
   * @throws IllegalArgumentException if numSeeds exceeds the number of pixels in the image
   */
  private void checkNumSeeds(Image image, int numSeeds) throws IllegalArgumentException {
    int numPixels = image.getWidth() * image.getHeight();
    if (numSeeds > numPixels) {
      throw new IllegalArgumentException("Number of seeds cannot exceed number of pixels in the"
              + " image.");
    }
  }
}
