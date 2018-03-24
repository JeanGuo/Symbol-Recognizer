// Accum Meredith and Dujia Guo


package model;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Line, which is composed of a starting Point inherited from
 * BasicSymbol, and an ending Point.
 */
public class Line extends BasicSymbol {
  private Point endPoint;

  /**
   * Construct a Line object, given x,y coordinates for the starting and ending Points.
   *
   * @param x    x-coordinate of starting Point
   * @param y    y-coordinate of starting Point
   * @param xEnd x-coordinate of ending Point
   * @param yEnd y-coordinate of ending Point
   */
  public Line(double x, double y, double xEnd, double yEnd) {
    super(x, y);
    this.endPoint = new Point(xEnd, yEnd);
  }

  /**
   * Return true if this Line and the given Lines make an EquilateralTriangle.\
   * CHANGE: The threshold for equal line lengths was increased and moved to a
   * Constants class during model revisions.
   *
   * @param l2 second given line
   * @param l3 third given line
   * @return true if lines make an equilateral triangle
   */
  public boolean checkEquilateral(Line l2, Line l3) {
    return (this.checkTriangle(l2, l3)
            && (Math.abs(this.point.distance(this.endPoint) - l2.point.distance(l2.endPoint))
            < Constants.EQUILATERAL_THRESHOLD)
            && (Math.abs(this.point.distance(this.endPoint) - l3.point.distance(l3.endPoint))
            < Constants.EQUILATERAL_THRESHOLD)
            && (Math.abs(l2.point.distance(l2.endPoint) - l3.point.distance(l3.endPoint))
            < Constants.EQUILATERAL_THRESHOLD));
  }

  /**
   * Return true if this line and the given lines make a Triangle.
   *
   * @param l2 second line
   * @param l3 third line
   * @return true if lines make a triangle
   */
  public boolean checkTriangle(Line l2, Line l3) {

    if (this.checkSharedStartingVertex(l2)) {
      if (l2.checkSharedEndingVertex(l3)) {
        return l3.checkSharedStartVertexEndVertex(this);
      } else if (l2.checkSharedEndVertexStartVertex(l3)) {
        return l3.checkSharedEndingVertex(this);
      }
    } else if (this.checkSharedEndingVertex(l2)) {
      if (l2.checkSharedStartingVertex(l3)) {
        return l3.checkSharedEndVertexStartVertex(this);
      } else if (l2.checkSharedStartVertexEndVertex(l3)) {
        return l3.checkSharedStartingVertex(this);
      }
    } else if (this.checkSharedEndVertexStartVertex(l2)) {
      if (l2.checkSharedEndingVertex(l3)) {
        return l3.checkSharedStartingVertex(this);
      } else if (l2.checkSharedEndVertexStartVertex(l3)) {
        return l3.checkSharedEndVertexStartVertex(this);
      }
    } else if (this.checkSharedStartVertexEndVertex(l2)) {
      if (l2.checkSharedStartingVertex(l3)) {
        return l3.checkSharedEndingVertex(this);
      } else if (l2.checkSharedStartVertexEndVertex(l3)) {
        return l3.checkSharedStartVertexEndVertex(this);
      }
    }

    return false;
  }

  /**
   * Return true if this line and the other line share a starting point.
   *
   * @param other other line
   * @return true if shared starting point.
   */
  private boolean checkSharedStartingVertex(Line other) {
    return this.point.close(other.point);
  }

  /**
   * Return true if this line and the other line share an ending point.
   *
   * @param other other line
   * @return true if shared ending point
   */
  private boolean checkSharedEndingVertex(Line other) {
    return this.endPoint.close(other.endPoint);
  }

  /**
   * Return true if this line shares a starting point with the other line's ending point.
   *
   * @param other other line
   * @return true if shared point
   */
  private boolean checkSharedStartVertexEndVertex(Line other) {
    return this.point.close(other.endPoint);
  }

  /**
   * Return true if this line shares an ending point with the other line's starting point.
   *
   * @param other other line
   * @return true if shared point
   */
  private boolean checkSharedEndVertexStartVertex(Line other) {
    return this.endPoint.close(other.point);
  }

  /**
   * Return a string for the Line object in the format "Line: (x,y) to (xEnd,yEnd)".
   *
   * @return string in above format
   */
  public String toString() {
    return "Line: " + point.toString() + " to " + endPoint.toString();
  }


  /**
   * Check if this symbol, combined with the other symbols in the list, make up a
   * composite symbol. If so, return the composite symbol. Else, return null.
   * CHANGE: Edited checkSymbol method to also return a DeathlyHallow if one can be formed.
   * CHANGE: Added support for rectangles and deathly hallows.
   *
   * @param otherSymbols other symbols to combine with this symbol
   * @return composite symbol or null, if no composite symbol exists
   */
  @Override
  public CompositeSymbol checkSymbol(List<Symbol> otherSymbols) {

    try {
      List<Symbol> lines = this.getLines(otherSymbols);
      Line l2 = (Line) lines.get(0);
      Line l3 = (Line) lines.get(1);

      if (this.checkTriangle(l2, l3)) {
        if (this.checkEquilateral(l2, l3)) {
          otherSymbols.remove(this);
          otherSymbols.remove(l2);
          otherSymbols.remove(l3);
          return new EquilateralTriangle(this, l2, l3);
        } else {
          otherSymbols.remove(this);
          otherSymbols.remove(l2);
          otherSymbols.remove(l3);
          return new Triangle(this, l2, l3);
        }
      } else {
        if (otherSymbols.size() >= 4) {
          CompositeSymbol s = this.makeRectangle(otherSymbols);
          if (s != null) {
            return s;
          } else {
            return this.makeDeathlyHallow(otherSymbols);
          }
        } else {
          return this.makeDeathlyHallow(otherSymbols);
        }
      }
    } catch (IllegalArgumentException e) {
      if (otherSymbols.size() >= 4) {
        CompositeSymbol s = this.makeRectangle(otherSymbols);
        if (s != null) {
          return s;
        } else {
          return this.makeDeathlyHallow(otherSymbols);
        }
      } else {
        return this.makeDeathlyHallow(otherSymbols);
      }
    }
  }

  /**
   * Check if this symbol can be used to make a deathly hallow with any other symbol in the list.
   * If so, return the DeathlyHallow. If not, return null.
   *
   * @param otherSymbols list of all symbols
   * @return deathly hallow if one can be made, null otherwise
   */
  public CompositeSymbol<Symbol> makeDeathlyHallow(List<Symbol> otherSymbols) {
    // get symbols to make deathly hallow (triangle, and circle)
    try {
      List<Symbol> hallows = this.getHallows(otherSymbols);
      Triangle cloak = null;
      Circle stone = null;
      if (hallows.get(0).isCloak()) {
        cloak = (Triangle) hallows.get(0);
        stone = (Circle) hallows.get(1);
      } else if (hallows.get(1).isStone()) {
        stone = (Circle) hallows.get(0);
        cloak = (Triangle) hallows.get(1);
      }

      if (this.checkDeathlyHallow(cloak, stone)) {
        otherSymbols.remove(this);
        otherSymbols.remove(cloak);
        otherSymbols.remove(stone);
        return new DeathlyHallows(this, cloak, stone);
      } else {
        return null;
      }
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * Return true if this line, and the given triangle and circle, can form a DeathlyHallow.
   *
   * @param triangle cloak
   * @param circle   stone
   * @return true if a DeathlyHallow can be formed, false otherwise
   */
  public boolean checkDeathlyHallow(Triangle triangle, Circle circle) {
    // check if midpoints of triangle's lines are approximately at the edges of the circle
    Point2D midpoint1 = triangle.getLine1().getMidpoint();
    Point2D midpoint2 = triangle.getLine2().getMidpoint();
    Point2D midpoint3 = triangle.getLine3().getMidpoint();

    Point2D stoneCenter = circle.getCenter();
    if (Math.abs(midpoint1.distance(stoneCenter) - circle.getRadius()) < Constants.DH_THRESHOLD
            && Math.abs(midpoint2.distance(stoneCenter) - circle.getRadius())
            < Constants.DH_THRESHOLD
            && Math.abs(midpoint3.distance(stoneCenter) - circle.getRadius())
            < Constants.DH_THRESHOLD) {
      // check if this line bisects the triangle
      // line should start at one vertex of triangle and end at the opposite midpoint
      if (this.getStart().close(triangle.getVertex1And2())) {
        return this.getEnd().distance(triangle.getLine3().getMidpoint()) < Constants.DH_THRESHOLD;
      } else if (this.getStart().close(triangle.getVertex2And3())) {
        return this.getEnd().distance(triangle.getLine1().getMidpoint()) < Constants.DH_THRESHOLD;
      } else if (this.getStart().close(triangle.getVertex3And1())) {
        return this.getEnd().distance(triangle.getLine2().getMidpoint()) < Constants.DH_THRESHOLD;
      }

      if (this.getEnd().close(triangle.getVertex1And2())) {
        return this.getStart().distance(triangle.getLine3().getMidpoint()) < Constants.DH_THRESHOLD;
      } else if (this.getEnd().close(triangle.getVertex2And3())) {
        return this.getStart().distance(triangle.getLine1().getMidpoint()) < Constants.DH_THRESHOLD;
      } else if (this.getEnd().close(triangle.getVertex3And1())) {
        return this.getStart().distance(triangle.getLine2().getMidpoint()) < Constants.DH_THRESHOLD;
      }
    }
    return false;
  }

  /**
   * Return true if this line, and the given triangle and circle, can form a DeathlyHallow.
   *
   * @param triangle cloak
   * @param circle   stone
   * @return true if a DeathlyHallow can be formed, false otherwise
   */
  public boolean checkDeathlyHallow(Circle circle, Triangle triangle) {
    return checkDeathlyHallow(triangle, circle);
  }

  /**
   * Look through the given list of symbols for a cloak and stone to use to attempt
   * to make a DeathlyHallow with this line (the wand).
   *
   * @param allSymbols all symbols to search through
   * @return list of symbols, containing cloak and stone
   */
  private List<Symbol> getHallows(List<Symbol> allSymbols) {
    // look through all symbols for cloak and stone added most recently

    Triangle cloak = null;
    Circle stone = null;

    // iterate through list in reverse to get cloak:
    for (int i = allSymbols.size() - 1; i > -1; i--) {
      if (allSymbols.get(i).isCloak()) {
        cloak = (Triangle) allSymbols.get(i);
        break;
      }
    }

    // iterate through list in reverse to get stone:
    for (int i = allSymbols.size() - 1; i > -1; i--) {
      if (allSymbols.get(i).isStone()) {
        stone = (Circle) allSymbols.get(i);
        break;
      }
    }

    if (cloak == null || stone == null) {
      throw new IllegalArgumentException("No such hallows found.");
    }

    try {
      List<Symbol> hallows = new ArrayList<Symbol>();
      hallows.add(cloak);
      hallows.add(stone);
      return hallows;
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("No such hallows found.");
    }
  }

  /**
   * Get the two most recently added lines in the list of all symbols.
   *
   * @param allSymbols all symbols list
   * @return list of two most recently added lines
   */
  private List<Symbol> getLines(List<Symbol> allSymbols) {
    // should look through all our symbols and get 2 lines added most recently
    Line l2 = null;
    Line l3 = null;

    // iterate through list in reverse:
    lines:
    for (int i = allSymbols.size() - 2; i > -1; i--) {
      if (allSymbols.get(i).pieceOfTriangle()) {
        l2 = (Line) allSymbols.get(i);
        for (int j = i - 1; j > -1; j--) {
          if (allSymbols.get(j).pieceOfTriangle()) {
            l3 = (Line) allSymbols.get(j);
            break lines;
          }
        }
      }
    }

    if (l2 == null || l3 == null) {
      throw new IllegalArgumentException("No such lines found.");
    }

    try {
      List<Symbol> lineList = new ArrayList<Symbol>();
      lineList.add(l2);
      lineList.add(l3);
      return lineList;
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("No such lines found.");
    }

  }

  /**
   * Return true if this symbol could be a piece of a Snowman (i.e. this symbol is a circle).
   *
   * @return true if possible piece of snowman
   */
  public boolean pieceOfSnowman() {
    return false;
  }

  /**
   * Return true if this symbol could be a piece of a Triangle (i.e. this symbol is a line).
   *
   * @return true if possible piece of triangle
   */
  public boolean pieceOfTriangle() {
    return true;
  }

  /**
   * Draws the line onto the Graphics object.
   * CHANGE: This method was added to this class during revisions, so that the view can
   * call on this method to draw each symbol via dynamic dispatch.
   *
   * @param g Graphics object on which the line should be drawn
   */
  public void drawSymbol(Graphics g) {
    g.drawLine((int) Math.round(point.getX()), (int) Math.round(point.getY()),
            (int) Math.round(endPoint.getX()), (int) Math.round(endPoint.getY()));
  }

  /**
   * Get the starting point of the line.
   * CHANGE: This method was added to this class during revisions so that the line's
   * starting point could be used to position a string in the View.
   *
   * @return starting point of the line
   */
  public Point getStart() {
    return point;
  }

  /**
   * Get the end point of the line.
   *
   * @return end point
   */
  public Point getEnd() {
    return endPoint;
  }

  /**
   * Get the midpoint of the line.
   *
   * @return midpoint of line
   */
  public Point getMidpoint() {
    return new Point((point.getX() + endPoint.getX()) / 2, (point.getY() + endPoint.getY()) / 2);
  }

  /**
   * Get the three most recently added lines in the given list of symbols.
   *
   * @param allSymbols given list of symbols to look through
   * @return list of three most recently added lines
   */
  private List<Symbol> getRectangleLines(List<Symbol> allSymbols) {
    // get 3 most recently added lines
    Line l2 = null;
    Line l3 = null;
    Line l4 = null;

    // iterate through list in reverse:
    lines:
    for (int i = allSymbols.size() - 2; i > -1; i--) {
      if (allSymbols.get(i).pieceOfTriangle()) {
        l2 = (Line) allSymbols.get(i);
        for (int j = i - 1; j > -1; j--) {
          if (allSymbols.get(j).pieceOfTriangle()) {
            l3 = (Line) allSymbols.get(j);
            for (int k = j - 1; k > -1; k--) {
              if (allSymbols.get(k).pieceOfTriangle()) {
                l4 = (Line) allSymbols.get(k);
                break lines;
              }
            }
          }
        }
      }
    }

    if (l2 == null || l3 == null || l4 == null) {
      throw new IllegalArgumentException("No such lines found.");
    }
    try {
      List<Symbol> lineList = new ArrayList<Symbol>();
      lineList.add(l2);
      lineList.add(l3);
      lineList.add(l4);
      return lineList;
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("No such lines found.");
    }
  }

  /**
   * See if a rectangle can be made from this line and any other lines in the given list
   * of symbols. If a Rectangle can be made, return the rectangle, otherwise return null.
   *
   * @param otherSymbols given list of symbols
   * @return rectangle, if one can be made, null otherwise
   */
  private CompositeSymbol makeRectangle(List<Symbol> otherSymbols) {
    // get 3 most recently added lines
    try {
      List<Symbol> lines = this.getRectangleLines(otherSymbols);
      Line l2 = (Line) lines.get(0);
      Line l3 = (Line) lines.get(1);
      Line l4 = (Line) lines.get(2);
      if (this.checkRectangle(l2, l3, l4)) {
        otherSymbols.remove(this);
        otherSymbols.remove(l2);
        otherSymbols.remove(l3);
        otherSymbols.remove(l4);
        return new Rectangle(this, l2, l3, l4);
      } else {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Return true if a rectangle can be made with this line and the given lines. This method
   * checks all possible orderings of the lines.
   *
   * @param l2 given line 2
   * @param l3 given line 3
   * @param l4 given line 4
   * @return true if a rectangle can be made, false otherwise
   */
  public boolean checkRectangle(Line l2, Line l3, Line l4) {
    return this.checkThreeRectangleConditions(l2, l3, l4)
            || this.checkThreeRectangleConditions(l2, l4, l3)
            || this.checkThreeRectangleConditions(l3, l2, l4)
            || this.checkThreeRectangleConditions(l3, l4, l2)
            || this.checkThreeRectangleConditions(l4, l2, l3)
            || this.checkThreeRectangleConditions(l4, l3, l2);
  }

  /**
   * Return true if this line and the given lines meet all three conditions of being a rectangle,
   * which are having touching endpoints, being parallel, and having right angles. This method
   * assumes the lines are in order by touching endpoints, so it checks if this line and l3
   * are parallel and if l2 and l4 are parallel.
   *
   * @param l2 given line l2
   * @param l3 given line l3
   * @param l4 given line l4
   * @return true if all three conditions are met
   */
  private boolean checkThreeRectangleConditions(Line l2, Line l3, Line l4) {
    return this.checkLinesMeet(l2, l3, l4) && this.checkParallel(l2, l3, l4)
            && this.checkRightAngles(l2, l3, l4);
  }

  /**
   * Check if this line and the given lines have touching endpoints, such that
   * they form a four-sided polygon.
   *
   * @param l2 given line 2
   * @param l3 given line 3
   * @param l4 given line 4
   * @return true if four-sided-polygon
   */
  private boolean checkLinesMeet(Line l2, Line l3, Line l4) {
    if (this.checkLinesMatching(l2)) {
      if (l2.checkLinesMatching(l3)) {
        if (l3.checkLinesMatching(l4)) {
          if (l4.checkLinesMatching(this)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Check if this line's endpoint touches the given other line's endpoint or starting point.
   *
   * @param other given line
   * @return true if lines touch
   */
  private boolean checkLinesMatching(Line other) {
    if (this.checkLinesMatchingEndtoStart(other)) {
      return true;
    } else if (this.checkLinesMatchingEndtoEnd(other)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check if this line's endpoint touches the other's start point.
   *
   * @param other given line
   * @return true if touching end and start points
   */
  private boolean checkLinesMatchingEndtoStart(Line other) {
    return this.checkSharedEndVertexStartVertex(other);
  }

  /**
   * Check if this line's endpoing touches the other's end point. If so, reverse the other
   * line.
   *
   * @param other other line
   * @return true if touching end to end points
   */
  private boolean checkLinesMatchingEndtoEnd(Line other) {
    if (this.checkSharedEndingVertex(other)) {
      other.reverse();
      return true;
    }
    return false;
  }

  /**
   * Check if this line is parallel with l3 and if line l2 is parallel with l4.
   *
   * @param l2 given line l2
   * @param l3 given line l3
   * @param l4 given line l4
   * @return true if lines are parallel
   */
  private boolean checkParallel(Line l2, Line l3, Line l4) {
    return this.checkParallel(l3) && l2.checkParallel(l4);
  }

  /**
   * Check if this line is parallel to the given other line.
   *
   * @param other given line
   * @return true if parallel
   */
  private boolean checkParallel(Line other) {
    // check if both lines are vertical (slope division by zero)
    if ((this.endPoint.getX() - this.point.getX()) == 0 && (other.endPoint.getX() - other.point.getX()) == 0) {
      return true;
    }

    // check for division by zero for slope of one of the lines
    if ((this.endPoint.getX() - this.point.getX()) == 0) {
      return (Math.abs(((this.endPoint.getY() - this.point.getY()) /
              (this.endPoint.getX() - this.point.getX())+.00000001)
              - ((other.endPoint.getY() - other.point.getY()) / (other.endPoint.getX() - other.point.getX())))
              < Constants.PARALLEL_THRESHOLD);
    } else  if ((other.endPoint.getX() - other.point.getX()) == 0) {
      return (Math.abs(((this.endPoint.getY() - this.point.getY()) / (this.endPoint.getX() - this.point.getX()))
              - ((other.endPoint.getY() - other.point.getY()) / (other.endPoint.getX() - other.point.getX())+.00000001))
              < Constants.PARALLEL_THRESHOLD);
    }


    return (Math.abs(((this.endPoint.getY() - this.point.getY()) / (this.endPoint.getX() - this.point.getX()))
            - ((other.endPoint.getY() - other.point.getY()) / (other.endPoint.getX() - other.point.getX())))
            < Constants.PARALLEL_THRESHOLD);
  }

  /**
   * Reverse this line's starting point and ending point.
   */
  private void reverse() {
    double x = point.getX();
    double y = point.getY();
    double xEnd = endPoint.getX();
    double yEnd = endPoint.getY();
    point.setLocation(xEnd, yEnd);
    endPoint.setLocation(x, y);
  }

  /**
   * Check if this line and the given lines form right angles such that they form a rectangle.
   * The lines are assumed to be in order of touching endpoints, so this line should form a
   * right angle with l2 and l4, and l3 should form a right angle with l2 and l4.
   *
   * @param l2 given line l2
   * @param l3 given line l3
   * @param l4 given line l4
   * @return true if right angles, within the error threshold
   */
  private boolean checkRightAngles(Line l2, Line l3, Line l4) {
    return this.checkAngleBetweenLines(l2) && l2.checkAngleBetweenLines(l3)
            && l3.checkAngleBetweenLines(l4) && l4.checkAngleBetweenLines(this);
  }

  /**
   * Check if this line forms a right angle with the given line, within a threshold specified
   * by a constant in the Constants file.
   *
   * @param other given line
   * @return true if approximately a right angle
   */
  private boolean checkAngleBetweenLines(Line other) {
    // @TODO need to check for division by zero here
    double angle1 = Math.atan((this.endPoint.getY() - this.point.getY()) /
            (this.endPoint.getX() - this.point.getX()));
    double angle2 = Math.atan((other.endPoint.getY() - other.point.getY()) /
            (other.endPoint.getX() - other.point.getX()));

    double difference = Math.abs(angle1 - angle2);
    if (difference > Math.PI/2) {
      difference = Math.PI - difference;
    }

    return (Math.abs(difference - (Math.PI/2)) < Constants.ANGLE_SHRESHOLD);
  }

}
