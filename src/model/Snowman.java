
// Accum Meredith and Dujia Guo


package model;


import java.awt.Graphics;

/**
 * This class represents a Snowman, which is composed of a list of Circles, inherited from the
 * CompositeSymbol class. The circles must be touching and have collinear centers.
 */
public class Snowman extends CompositeSymbol<Circle> {

  /**
   * Construct a Snowman object, given three circles.
   *
   * @param c1 Circle 1
   * @param c2 Circle 2
   * @param c3 Circle 3
   */
  public Snowman(Circle c1, Circle c2, Circle c3) {
    if (!c1.checkSnowman(c2, c3)) {
      throw new IllegalArgumentException("Circles cannot form a Snowman.");
    }
    symbols.add(c1);
    symbols.add(c2);
    symbols.add(c3);
  }

  /**
   * Return a string in the format "Snowman: [Circle1] [Circle2] [Circle3]".
   *
   * @return string in above format
   */
  public String toString() {
    String str = "Snowman:";
    for (Circle c : symbols) {
      str = str + " [" + c.toString() + "]";
    }
    return str;
  }


  /**
   * Draws the Snowman onto the Graphics object, along with a string "Snowman".
   * CHANGE: This method was added to this class during revisions, so that the view can
   * call on this method to draw each symbol via dynamic dispatch.
   *
   * @param g Graphics object on which the Snowman should be drawn
   */
  public void drawSymbol(Graphics g) {
    g.drawString("Snowman", (int) Math.round(symbols.get(0).getCenter().getX()),
            (int) Math.round(symbols.get(0).getCenter().getY()));
    for (Circle c : symbols) {
      c.drawSymbol(g);
    }
  }

  /**
   * Draw the string naming this object onto the given Graphics object.
   * @param g given graphics object
   */
  public void drawSymbolString(Graphics g) {
    g.drawString("Snowman", (int) Math.round(symbols.get(0).getCenter().getX()),
            (int) Math.round(symbols.get(0).getCenter().getY()));
  }


}
