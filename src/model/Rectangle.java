package model;
// Accum Meredith and Dujia Guo


import java.awt.Graphics;

/**
 * This class represents a Rectangle, which is a type of Composite Symbol composed of four
 * lines which form a polygon with parallel opposite sides and right angles between each side.
 */
public class Rectangle extends CompositeSymbol<Line> {

  /**
   * Construct a Rectangle object.
   *
   * @param l1 line 1
   * @param l2 line 2
   * @param l3 line 3
   * @param l4 line 4
   */
  public Rectangle(Line l1, Line l2, Line l3, Line l4) {
    if (!l1.checkRectangle(l2, l3, l4)) {
      throw new IllegalArgumentException("Lines cannot form a Rectangle");
    }
    symbols.add(l1);
    symbols.add(l2);
    symbols.add(l3);
    symbols.add(l4);
  }

  /**
   * Draw the Rectangle onto a Graphics object.
   *
   * @param g Graphics object on which the symbol should be drawn
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
    g.drawString("Rectangle", (int) Math.round(symbols.get(0).getStart().getX()),
            (int) Math.round(symbols.get(0).getStart().getY()));
  }

  /**
   * Return a string reprsentation for the Rectangle in the
   * form: "Rectangle [line1] [line2] [line3]".
   */
  public String toString() {
    String str = "Rectangle:";
    for (Line l : symbols) {
      str = str + " [" + l.toString() + "]";
    }
    return str;
  }
}
