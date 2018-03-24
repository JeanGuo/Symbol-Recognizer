// Accum Meredith and Dujia Guo

package model;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * This class represents a Triangle, which is composed of a list of Lines, inherited from
 * CompositeSymbol.
 */
public class Triangle extends CompositeSymbol<Line> {

  /**
   * Construct a Triangle object, given three lines.
   *
   * @param l1 line 1 of triangle
   * @param l2 line 2 of triangle
   * @param l3 line 3 of triangle
   */
  public Triangle(Line l1, Line l2, Line l3) {
    if (!l1.checkTriangle(l2, l3)) {
      throw new IllegalArgumentException("Lines cannot form a Triangle.");
    }
    symbols.add(l1);
    symbols.add(l2);
    symbols.add(l3);
  }

  /**
   * Return a string in the format "Triangle: [Line1] [Line2] [Line3]".
   *
   * @return string in above format
   */
  public String toString() {
    String str = "Triangle:";
    for (Line l : symbols) {
      str = str + " [" + l.toString() + "]";
    }
    return str;
  }

  /**
   * Draws the triangle onto the Graphics object, along with a string "Triangle".
   * CHANGE: This method was added to this class during revisions, so that the view can
   * call on this method to draw each symbol via dynamic dispatch.
   *
   * @param g Graphics object on which the triangle should be drawn
   */
  public void drawSymbol(Graphics g) {
    for (Line l : symbols) {
      l.drawSymbol(g);
    }
  }

  /**
   * Draw the string naming this object onto the given Graphics object.
   * @param g given graphics object
   */
  public void drawSymbolString(Graphics g) {
    g.drawString("Triangle", (int) Math.round(symbols.get(0).getStart().getX()),
            (int) Math.round(symbols.get(0).getStart().getY()));
  }

  @Override
  public boolean isCloak() {
    return true;
  }

  public Line getLine1() {
    return symbols.get(0);
  }

  public Line getLine2() {
    return symbols.get(1);
  }

  public Line getLine3() {
    return symbols.get(2);
  }


  public Point getVertex1And2() {
    Line l1 = getLine1();
    Line l2 = getLine2();
    return getVertex(l1, l2);
  }

  public Point getVertex2And3() {
    Line l1 = getLine2();
    Line l2 = getLine3();
    return getVertex(l1, l2);

  }

  public Point getVertex3And1() {
    Line l1 = getLine3();
    Line l2 = getLine1();
    return getVertex(l1, l2);
  }

  private Point getVertex(Line l1, Line l2) {
    double distance1 = l1.getStart().distance(l2.getStart());
    double distance2 = l1.getStart().distance(l2.getEnd());
    double distance3 = l1.getEnd().distance(l2.getStart());
    double distance4 = l1.getEnd().distance(l2.getEnd());

    if (distance1 < distance2 && distance1 < distance3 && distance1 < distance4) {
      return l1.getStart().average(l2.getStart());
    } else if (distance2 < distance1 && distance2 < distance3 && distance2 < distance4) {
      return l1.getStart().average(l2.getEnd());
    } else if (distance3 < distance1 && distance3 < distance2 && distance3 < distance4) {
      return l1.getEnd().average(l2.getStart());
    } else {
      return l1.getEnd().average(l2.getEnd());
    }
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
      //return makeDeathlyHallow(otherSymbols);
      CompositeSymbol s = makeTriforce(otherSymbols);
      if (s != null) {
        return s;
      } else {
        return makeDeathlyHallow(otherSymbols);
      }
    } catch (IllegalArgumentException e) {
      return makeTriforce(otherSymbols);
    }
  }

  /**
   * CHANGE: Added this method to make a Deathly Hallow.
   */
  private CompositeSymbol makeDeathlyHallow(List<Symbol> otherSymbols) {
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


  /**
   * Return a Triforce if one can be made with this triangle and another triangle and circle,
   * most recently added to the given list of symbols.
   * @param otherSymbols given list of symbols
   * @return triforce if one can be made, null otherwise
   */
  private CompositeSymbol makeTriforce(List<Symbol> otherSymbols) {
    Triangle t = null;
    Circle c = null;

    for (int i = otherSymbols.size() - 2; i > -1; i--) {
      if (otherSymbols.get(i).isTriangle()) {
        t = (Triangle) otherSymbols.get(i);
        break;
      }
    }

    for (int i = otherSymbols.size() - 2; i > -1; i--) {
      if (otherSymbols.get(i).isStone()) {
        c = (Circle) otherSymbols.get(i);
        break;
      }
    }

    if (t == null || c == null) {
      return null;
    }

    if (this.checkTriforce(t, c)) {
      otherSymbols.remove(this);
      otherSymbols.remove(t);
      otherSymbols.remove(c);
      return new Triforce(this, t, c);
    } else {
      return null;
    }

  }

  /**
   * Return true if this triangle, the given triangle, and the given circle form a
   * triforce symbol.
   * @param t given triangle
   * @param c given circle
   * @return true if triforce can be made
   */
  public boolean checkTriforce(Triangle t, Circle c) {
    Triangle small;
    Triangle big;
    if (this.perimeter() >= t.perimeter()) {
      big = this;
      small = this;
    } else {
      big = t;
      small = this;
    }

    Point midpoint1 = big.getLine1().getMidpoint();
    Point midpoint2 = big.getLine2().getMidpoint();
    Point midpoint3 = big.getLine3().getMidpoint();

    Point vertex1 = small.getVertex1And2();
    Point vertex2 = small.getVertex2And3();
    Point vertex3 = small.getVertex3And1();

    if (checkMidpointsAndVerticesMatch(midpoint1, midpoint2, midpoint3, vertex1, vertex2, vertex3)
            || checkMidpointsAndVerticesMatch(midpoint1, midpoint3, midpoint2, vertex1, vertex2, vertex3)
            || checkMidpointsAndVerticesMatch(midpoint2, midpoint1, midpoint3, vertex1, vertex2, vertex3)
            || checkMidpointsAndVerticesMatch(midpoint2, midpoint3, midpoint1, vertex1, vertex2, vertex3)
            || checkMidpointsAndVerticesMatch(midpoint3, midpoint1, midpoint2, vertex1, vertex2, vertex3)
            || checkMidpointsAndVerticesMatch(midpoint3, midpoint2, midpoint1, vertex1, vertex2, vertex3)) {
      // enforce that vertices of large triangle are about the distance of the radius from the
      // center of the circle
      Point vertexBig1 = big.getVertex1And2();
      Point vertexBig2 = big.getVertex2And3();
      Point vertexBig3 = big.getVertex3And1();

      Point2D center = c.getCenter();
      double radius = c.getRadius();

      if (Math.abs(vertexBig1.distance(center) - radius) < 50
              && Math.abs(vertexBig2.distance(center) - radius) < 50
              && Math.abs(vertexBig3.distance(center) - radius) < 50) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }

  }

  /**
   * Return true, since this object is a triangle.
   * @return true
   */
  @Override
  public boolean isTriangle() {
    return true;
  }

  /**
   * Return true if mid1 is close to vertex1, mid2 is close to vertex2, and mid3 is close
   * to vertex3. These points represent the midpoints of one triangle and the vertices of another
   * triangle.
   * @param mid1
   * @param mid2
   * @param mid3
   * @param vertex1
   * @param vertex2
   * @param vertex3
   * @return true if midpoints and vertices are close, as described above.
   */
  private boolean checkMidpointsAndVerticesMatch(Point mid1, Point mid2, Point mid3,
                                                 Point vertex1, Point vertex2, Point vertex3) {
    return mid1.close(vertex1) && mid2.close(vertex2) && mid3.close(vertex3);
  }

  /**
   * Return the perimeter of this triangle, as the sum of each of its composite line segments.
   * @return perimeter
   */
  private double perimeter() {
    Point2D vertex1 = this.getVertex1And2();
    Point2D vertex2 = this.getVertex2And3();
    Point2D vertex3 = this.getVertex3And1();

    return vertex1.distance(vertex2) + vertex2.distance(vertex3) + vertex3.distance(vertex1);
  }


}
