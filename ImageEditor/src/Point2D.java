import java.util.List;
import java.util.Objects;

/**
 * This class represents a 2D point. A point has an x and y coordinate.  A point can return its x
 * and y values.  A point can also calculate various distances from given points.
 */
public class Point2D {
  /**
   * y coordinate/ row of the point.
   */
  private int row;

  /**
   * x coordinate / column of the point.
   */
  private int column;

  /**
   * Construct a Point2D object. A Point2D object has a row position and a column position.
   *
   * @param row    the row position (y position) of the point
   * @param column the column position (x position) of the point
   */
  public Point2D(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Return the x coordinate/column of this point.
   *
   * @return x coordinate of the point
   */
  public int getX() {
    return this.column;
  }

  /**
   * Return the y coordinate/row of this point.
   *
   * @return y coordinate of the point
   */
  public int getY() {
    return this.row;
  }

  /**
   * Finds the euclidean distance between a seed point and this point.
   *
   * @param seed the other point you want the distance from
   * @return distance between this point and seed
   */
  private double distanceToC(Point2D seed) {
    double firstTerm = Math.pow((this.row - seed.getY()), 2);
    double secondTerm = Math.pow((this.column - seed.getX()), 2);
    return Math.sqrt(firstTerm + secondTerm);
  }

  /**
   * Returns the index of the the seed closest to this point.
   *
   * @param seeds list of centroids we are comparing point to
   * @return index of the closest seed
   */
  public int minDistance(List<Point2D> seeds) {
    int minIndex = 0;
    double minDist = distanceToC(seeds.get(0));
    for (int i = 1; i < seeds.size(); i++) {
      double distance = distanceToC(seeds.get(i));
      if (distance < minDist) {
        minIndex = i;
        minDist = distance;
      }
    }
    return minIndex;
  }

  /**
   * Return whether this point and another object are equal to each other. Two points are equal if
   * they have the same row and column position.
   *
   * @param obj the other object you want to compare this point to
   * @return true if the two objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Point2D)) {
      return false;
    }
    Point2D other = (Point2D) obj;
    return (this.row == other.row && this.column == other.column);
  }

  /**
   * Return the hashcode of this point.
   *
   * @return hashcode of the this point
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.row, this.column);
  }
}
