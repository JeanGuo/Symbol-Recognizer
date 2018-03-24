// Accum Meredith and Dujia Guo


package model;

import java.awt.geom.Point2D;

/**
 * This class represents a Point object, which extends the Point2D class and includes a
 * close() method that determines if two points are close enough to each other, within a certain
 * threshold.
 */
public class Point extends Point2D.Double {

  /**
   * Construct a Point by calling on the parent constructor.
   *
   * @param x x-coordinate of Point
   * @param y y-coordinate of Point
   */
  public Point(double x, double y) {
    super(x, y);
  }

  /**
   * Return true if distance between this point and the other point is less than or equal to a
   * specified threshold.
   * CHANGE : Increased the threshold for distance between points to quality as close, and moved
   * this constant to a Constants class.
   *
   * @param other other Point
   * @return true if this point and other point are close
   */
  public boolean close(Point2D other) {
    return distance(other.getX(), other.getY()) <= Constants.POINTS_CLOSE_THRESHOLD;
  }

  /**
   * Return a string representation for the Point object in the format "(x,y)".
   *
   * @return string in above format
   */
  @Override
  public String toString() {
    return String.format("(%.2f,%.2f)", x, y);
  }


  public Point average(Point2D other) {
    return new Point((this.getX()+other.getX())/2, (this.getY()+other.getY())/2);
  }



}
