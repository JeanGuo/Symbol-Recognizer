package model; 
// Accum Meredith and Dujia Guo


import java.awt.Graphics;

/**
 * This class represents a DeathlyHallow, which is a Triangle with a circle inside, bisected
 * by a line, from Harry Potter.
 */
public class DeathlyHallows extends CompositeSymbol<Symbol> {

  /**
   * Construct a Deathly Hallow object.
   *
   * @param line     elder wand
   * @param triangle cloak
   * @param circle   stone
   */
  public DeathlyHallows(Line line, Triangle triangle, Circle circle) {
    if (!line.checkDeathlyHallow(triangle, circle)) {
      throw new IllegalArgumentException("Deathly Hallows cannot be formed with givens.");
    }
    symbols.add(line);
    symbols.add(triangle);
    symbols.add(circle);
  }

  /**
   * Return the stone (circle) of this Deathly Hallow.
   *
   * @return circle
   */
  public Circle getStone() {
    if (!symbols.get(2).isStone()) {
      throw new IllegalArgumentException("Not a stone.");
    }
    return (Circle) symbols.get(2);
  }


  /**
   * Draws the symbol onto the Graphics object.
   * CHANGE: This method was added to this interface during revisions, so that the view can
   * call on this method to draw each symbol via dynamic dispatch.
   *
   * @param g Graphics object on which the symbol should be drawn
   */
  @Override
  public void drawSymbol(Graphics g) {
    g.drawString("Deathly Hallows", (int) Math.round(getStone().getCenter().getX()),
            (int) Math.round(getStone().getCenter().getY()));
    for (Symbol s : symbols) {
      s.drawSymbol(g);
    }
  }

  /**
   * Draw the string naming this object onto the given Graphics object.
   * @param g given graphics object
   */
  public void drawSymbolString(Graphics g) {
    g.drawString("Deathly Hallows", (int) Math.round(getStone().getCenter().getX()),
            (int) Math.round(getStone().getCenter().getY()));
  }

  /**
   * Return a string representation of the DeathlyHallows in the form "DeathlyHallows: []...".
   *
   * @return string representation of DH
   */
  public String toString() {
    return "DeathlyHallows: " + " [" + symbols.get(0).toString() + "] [" + symbols.get(1).toString()
            + "] [" + symbols.get(2).toString() + "]";
  }
}
