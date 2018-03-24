// Accum Meredith and Dujia Guo

package model;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * This abstract class represents a Basic Symbol, which has a Point.
 */
public abstract class BasicSymbol implements Symbol {
  protected Point point;

  /**
   * Construct a Point object, given a (x,y) coordinates.
   *
   * @param x x-coordinate of the point
   * @param y y-coordinate of the point
   */
  public BasicSymbol(double x, double y) {
    this.point = new Point(x, y);
  }

  /**
   * Return true if this is a circle.
   * @return true if circle
   */
  public boolean isStone() {
    return false;
  }

  /**
   * Return true if this is a triangle.
   * @return true if triangle
   */
  public boolean isCloak() {
    return false;
  }

  /**
   * Return point of this Basic Symbol.
   * @return point
   */
  public Point2D getPoint() {
    return point;
  }

  /**
   * Return true if this is a triangle.
   * @return true if triangle
   */
  public boolean isTriangle() {return false;}

  /**
   * Draw the string naming this object onto the given Graphics object.
   * @param g given graphics object
   */
  public void drawSymbolString(Graphics g) {return;}
}
