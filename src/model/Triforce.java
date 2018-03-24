package model;
// Accum Meredith and Dujia Guo


import java.awt.Graphics;

/**
 * This class represents a Triforce symbol which is composed of one smaller inverted triangle
 * inside a larger triangle, surrounded by a circle.
 */
public class Triforce extends CompositeSymbol<Symbol> {

  /**
   * Construct a triforce object.
   * @param t1 triangle1
   * @param t2 triangle2
   * @param c circle
   */
  public Triforce(Triangle t1, Triangle t2, Circle c) {
    if (!t1.checkTriforce(t2, c)) {
      throw new IllegalArgumentException("Tri forces cannot be formed with givens");
    }
    symbols.add(t1);
    symbols.add(t2);
    symbols.add(c);
  }


  /**
   * Draw the triforce symbol onto the  given graphics object.
   * @param g Graphics object on which the symbol should be drawn
   */
  @Override
  public void drawSymbol(Graphics g) {
    for (Symbol s : symbols) {
      s.drawSymbol(g);
    }
  }

  public void drawSymbolString(Graphics g) {
    g.drawString("Triforce", (int) Math.round(getCircle().getCenter().getX()),
            (int) Math.round(getCircle().getCenter().getY()));
  }

  /**
   * Get the triforce's circle.
   * @return circle of triforce
   */
  private Circle getCircle() {
    return (Circle) symbols.get(2);
  }

  /**
   * Return a string representation of the triforce in the format "Triforce [] [] []".
   * @return String representation of triforce
   */
  public String toString() {
    return "Triforce: [" + symbols.get(0) + "] [" + symbols.get(1) + "] [" + symbols.get(2) + "]";
  }

}
