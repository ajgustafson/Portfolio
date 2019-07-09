/**
 * This class represents a filter operation on an image. A filter operation overlays a kernel (an
 * array of 2D array of numbers having odd dimensions) on each pixel of the image and uses the
 * kernel to calculate new channel values for each pixel.  This results in a image with a different
 * appearance.  Examples of filter operations include blurring or sharpening an image.  This class
 * is abstract and implements the ImageEditor interface.
 */
public abstract class Filter implements ImageEditor {
  /**
   * Filtering is an operation which has a kernel (an array of 2D array of numbers having odd
   * dimensions). Given a pixel in the image and a channel, the result of the filter can be computed
   * for that pixel and channel. This is done by placing the center of the kernel at a particular
   * pixel and the result of the filter is calculated by multiplying together corresponding numbers
   * in the kernel and the pixels and adding them. If the portions of the kernel do not overlap any
   * pixels, those pixels are not included in the computation.
   *
   * @param image  the image the filter operation should be done on
   * @param matrix the kernel for the operation
   * @return an image object containing the data of the filtered image
   */
  public Image filter(Image image, double[][] matrix) {
    int[][][] pixels = new int[image.getHeight()][image.getWidth()][3];
    int[][][] inputRgb = image.getRgb();
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          int result = getValue(i, j, k, inputRgb, matrix);
          result = ImageUtil.clamp(result);
          pixels[i][j][k] = result;
        }
      }
    }
    return new Image(pixels);
  }

  /**
   * Calculates the value of the filter operation for the specified pixel (row, column, channel) of
   * the original image. This is done by placing the center of the kernel at a particular pixel and
   * the result of the filter is calculated by multiplying together corresponding numbers in the
   * kernel and the pixels and adding them. If the portions of the kernel do not overlap any pixels,
   * those pixels are not included in the computation.
   *
   * @param row     row of the original pixel
   * @param column  column of the original pixel
   * @param channel channel of the original pixel
   * @param rgb     an array of rgb values of the original image
   * @param matrix  the kernel matrix of the filter being performed
   * @return the value for the specified pixel once the filter has been applied
   */
  private int getValue(int row, int column, int channel, int[][][] rgb, double[][] matrix) {
    double sum = 0;
    int imageHeight = rgb.length;
    int imageWidth = rgb[0].length;
    int matrixHeight = matrix.length;
    int matrixWidth = matrix[0].length;
    int mid = matrixWidth / 2;
    for (int i = 0; i < matrixHeight; i++) {
      for (int j = 0; j < matrixWidth; j++) {
        int imageRow = getColumnOrRow(i, row, mid);
        int imageColumn = getColumnOrRow(j, column, mid);
        if (imageRow >= 0 && imageRow < imageHeight && imageColumn >= 0
                && imageColumn < imageWidth) {
          sum += matrix[i][j] * rgb[imageRow][imageColumn][channel];
        }
      }
    }
    return (int) Math.round(sum);
  }

  /**
   * Return the row or column of the original image that corresponds to the kernel for a given
   * position in the kernel.
   *
   * @param filterPosition the row or column of the filter
   * @param imagePosition  the row or column of the image pixel
   * @param mid            the index of the middle of the kernel
   * @return the corresponding row or column of the image that corresponds to that provided filter
   *          position
   */
  private int getColumnOrRow(int filterPosition, int imagePosition, int mid) {
    if (filterPosition == mid) {
      return imagePosition;
    } else if (filterPosition < mid) {
      int offset = mid - filterPosition;
      return imagePosition - offset;
    } else {
      int offset = filterPosition - mid;
      return imagePosition + offset;
    }
  }
}
