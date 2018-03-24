// Accum Meredith and Dujia Guo

package model;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a Circle, which is composed of a center Point (inherited from
 * BasicSymbol) and a radius.
 */
public class Circle extends BasicSymbol {
  private double radius;

  /**
   * Construct a Circle, given coordinates for the center and an integer for the radius.
   *
   * @param x      x-coordinate of center
   * @param y      y-coordinate of center
   * @param radius radius of circle
   */
  public Circle(double x, double y, double radius) {
    super(x, y);
    if (radius <= 0) {
      throw new IllegalArgumentException("Radius must be greater than zero.");
    }
    this.radius = radius;
  }

  /**
   * Return true if this Circle and the given Circles can make a Snowman.
   *
   * @param c2 circle two
   * @param c3 circle three
   * @return true if snowman can be made
   */
  public boolean checkSnowman(Circle c2, Circle c3) {
    if (this.radius == c2.radius || this.radius == c3.radius || c2.radius == c3.radius) {
      return false;
    }
    Circle small = null;
    Circle medium = null;
    Circle large = null;

    double smallRadius = Math.min(this.radius, Math.min(c2.radius, c3.radius));
    double largeRadius = Math.max(this.radius, Math.max(c2.radius, c3.radius));
    if (this.radius == smallRadius) {
      small = this;
      if (c2.radius == largeRadius) {
        large = c2;
        medium = c3;
      } else {
        large = c3;
        medium = c2;
      }
    } else if (c2.radius == smallRadius) {
      small = c2;
      if (this.radius == largeRadius) {
        large = this;
        medium = c3;
      } else {
        large = c3;
        medium = this;
      }
    } else {
      small = c3;
      if (this.radius == largeRadius) {
        large = this;
        medium = c2;
      } else {
        large = c2;
        medium = this;
      }
    }

    return this.checkCollinear(c2, c3) && small.checkTouching(medium, large);

  }

  /**
   * Return true if this circle's center is collinear with the given circles' centers.
   * CHANGE: Collinear threshold for the circle class was changed to make it easier
   * for a user to draw a Snowman. This constant was also placed in a Constants class.
   *
   * @param c2 circle two
   * @param c3 circle three
   * @return true if collinear centers
   */
  private boolean checkCollinear(Circle c2, Circle c3) {
    // check if they're all on the same line
    return (((this.point.getY() - c2.point.getY())
            * (this.point.getX() - c3.point.getX()))
            - ((this.point.getY() - c3.point.getY())
            * (this.point.getX() - c2.point.getX())) < Constants.COLLINEAR_THRESHOLD);
  }

  /**
   * Return true if this circle is touching the medium circle and the medium circle
   * is touching the large circle.
   * CHANGE: Threshold for touching circles was changed to allow for user error when drawing,
   * and this threshold constant was moved to a Constants class.
   *
   * @param medium medium Circle in potential Snowman
   * @param large  large Circle in potential Snowman
   * @return true if circles are touching
   */
  private boolean checkTouching(Circle medium, Circle large) {
    // circles are touching if the distance between their radii is equal to the sum of their radii
    double distanceBetween1 = this.point.distance(medium.point) - (this.radius + medium.radius);
    double distanceBetween2 = medium.point.distance(large.point) - (medium.radius + large.radius);

    return Math.abs(distanceBetween1) < Constants.CIRCLES_TOUCHING_THRESHOLD
            && Math.abs(distanceBetween2) < Constants.CIRCLES_TOUCHING_THRESHOLD;
  }

  /**
   * Return String representation of Circle in format "Circle: (x,y) r=_".
   *
   * @return string in above format
   */
  public String toString() {
    String r = String.format("%.2f", radius);
    return "Circle: " + point.toString() + " r=" + r;
  }


  /**
   * Check if this symbol, combined with the other symbols in the list, make up a
   * composite symbol. If so, return the composite symbol. Else, return null.
   *
   * @param otherSymbols other symbols to combine with this symbol
   * @return composite symbol or null, if no composite symbol exists
   */
  @Override
  public CompositeSymbol checkSymbol(List<Symbol> otherSymbols) {
    try {
      List<Symbol> circles = this.getCircles(otherSymbols);
      Circle c2 = (Circle) circles.get(0);
      Circle c3 = (Circle) circles.get(1);
      if (this.checkSnowman(c2, c3)) {
        otherSymbols.remove(this);
        otherSymbols.remove(c2);
        otherSymbols.remove(c3);
        return new Snowman(this, c2, c3);
      } else {
        //return this.makeDeathlyHallow(otherSymbols);
        CompositeSymbol s = makeTriforce(otherSymbols);
        if (s != null) {
          return s;
        } else {
          return makeDeathlyHallow(otherSymbols);
        }
      }
    } catch (IllegalArgumentException e) {
      try {
        //return this.makeDeathlyHallow(otherSymbols);
        CompositeSymbol s = makeTriforce(otherSymbols);
        if (s != null) {
          return s;
        } else {
          return makeDeathlyHallow(otherSymbols);
        }
      } catch (Exception n) {
        return null;
      }
    }
  }

  /**
   * Make a triforce and return it, if one can be made using this circle and the two
   * most recently added triangles in the otherSymbols list. Else, return null.
   * @param otherSymbols list of symbols to check
   * @return null if no triforce can be made, else return the triforce
   */
  private CompositeSymbol makeTriforce(List<Symbol> otherSymbols) {
    Triangle t1 = null;
    Triangle t2 = null;

    triangles:
    for (int i = otherSymbols.size() - 2; i > -1; i--) {
      if (otherSymbols.get(i).isTriangle()) {
        t1 = (Triangle) otherSymbols.get(i);
        for (int j = otherSymbols.size() - 3; j > -1; j--) {
          if (otherSymbols.get(j).isTriangle()) {
            t2 = (Triangle) otherSymbols.get(j);
            break triangles;
          }
        }
      }
    }

    if (t1 == null || t2 == null) {
      return null;
    }

    if (t1.checkTriforce(t2, this)) {
      otherSymbols.remove(this);
      otherSymbols.remove(t1);
      otherSymbols.remove(t2);
      return new Triforce(t1, t2, this);
    } else {
      return null;
    }
  }


  /**
   * Given a list of symbols, get the last two circles added to the list.
   *
   * @param allSymbols list of all symbols
   * @return list containing two circles
   */
  private List<Symbol> getCircles(List<Symbol> allSymbols) {
    // look through all symbols and get 2 most recently added circles
    Circle c2 = null;
    Circle c3 = null;

    // iterate through list in reverse:
    circles:
    for (int i = allSymbols.size() - 2; i > -1; i--) {
      if (allSymbols.get(i).pieceOfSnowman()) {
        c2 = (Circle) allSymbols.get(i);
        for (int j = i - 1; j > -1; j--) {
          if (allSymbols.get(j).pieceOfSnowman()) {
            c3 = (Circle) allSymbols.get(j);
            break circles;
          }
        }
      }
    }

    if (c2 == null || c3 == null) {
      throw new IllegalArgumentException("No such circles found.");
    }

    try {
      List<Symbol> circleList = new ArrayList<Symbol>();
      circleList.add(c2);
      circleList.add(c3);
      return circleList;
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("No such circles found.");
    }
  }

  /**
   * Return true if this symbol could be a piece of a Snowman (i.e. this symbol is a circle).
   *
   * @return true if possible piece of snowman
   */
  public boolean pieceOfSnowman() {
    return true;
  }

  /**
   * Return true if this symbol could be a piece of a Triangle (i.e. this symbol is a line).
   *
   * @return true if possible piece of triangle
   */
  public boolean pieceOfTriangle() {
    return false;
  }


  /**
   * Draws the circle onto the Graphics object.
   * CHANGE: This method was added to this class during revisions, so that the
   * view can call on this method to draw each symbol via dynamic dispatch.
   *
   * @param g Graphics object on which the circle should be drawn
   */
  public void drawSymbol(Graphics g) {
    g.drawOval((int) Math.round(point.getX() - radius), (int) Math.round(point.getY() - radius),
            (int) Math.round(radius * 2), (int) Math.round(radius * 2));
  }

  /**
   * Return the center point of the circle.
   * CHANGE: This method was added to the model during revisions so that the circle's
   * center can be used to position a "Snowman" string in the View.
   *
   * @return circle's center point
   */
  public Point2D getCenter() {
    return point;
  }

  /**
   * Return true if this symbol could be the Stone portion of a DeathlyHallow
   *
   * @return true
   */
  @Override
  public boolean isStone() {
    return true;
  }

  /**
   * Return the radius of this circle.
   *
   * @return radius of this circle
   */
  public double getRadius() {
    return radius;
  }


  /**
   * Check if this circle could be used to make a DeathlyHallow with the other symbols
   * in the given list. If a hallow can be made, return it, otherwise return null.
   *
   * @param otherSymbols given list of symbols
   * @return DeathlyHallow, if one can be made, null otherwise
   */
  public CompositeSymbol makeDeathlyHallow(List<Symbol> otherSymbols) {
    // get symbols to make deathly hallow (triangle, and circle)

    // find most recent line
    Line wand = null;
    for (int i = otherSymbols.size() - 2; i > -1; i--) {
      if (otherSymbols.get(i).pieceOfTriangle()) {
        wand = (Line) otherSymbols.get(i);
        break;
      }
    }
    if (wand != null) {
      return wand.makeDeathlyHallow(otherSymbols);
    } else {
      return null;
    }

  }

}
