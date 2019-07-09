/**
 * This interface represents an image editor.  An image editor can edit an existing image by
 * changing its appearance in some way.
 */
public interface ImageEditor {

  /**
   * Edit given image.  Returns a new image that has a different appearance than the original
   * image.
   *
   * @param image to be edited.
   * @return new edited image.
   */
  Image edit(Image image);
}
