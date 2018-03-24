// Accum Meredith and Dujia Guo


package model;

import java.awt.Graphics;

/**
 * This class represents an EquilateralTriangle which is a Triangle with all three
 * sides of equal length.
 */
public class EquilateralTriangle extends Triangle {

  /**
   * Construct a EquilateralTriangle object, given three lines.
   *
   * @param l1 line 1 of triangle
   * @param l2 line 2 of triangle
   * @param l3 line 3 of triangle
   */
  public EquilateralTriangle(Line l1, Line l2, Line l3) {
    super(l1, l2, l3);
    if (!l1.checkEquilateral(l2, l3)) {
      throw new IllegalArgumentException("Lines cannot form an equilateral triangle.");
    }
  }

  /**
   * Return a string in the format "Equilateral Triangle: [Line1] [Line2] [Line3]".
   *
   * @return string in above format
   */
  public String toString() {
    return "Equilateral " + super.toString();
  }

  /**
   * Draws the equilateral triangle onto the Graphics object, along with a string "Equilateral
   * Triangle".
   * CHANGE: This method was added to this class during revisions, so that the view can
   * call on this method to draw each symbol via dynamic dispatch.
   *
   * @param g Graphics object on which the triangle should be drawn
   */
  @Override
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
    g.drawString("Equilateral Triangle", (int) Math.round(symbols.get(0).getStart().getX()),
            (int) Math.round(symbols.get(0).getStart().getY()));
  }
}
