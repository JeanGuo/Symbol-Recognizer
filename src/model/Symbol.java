// Accum Meredith and Dujia Guo


package model;


import java.awt.Graphics;
import java.util.List;

/**
 * This interface represents a Symbol. All classes that implement this interface must have a
 * method that will check whether this symbol can make another symbol if combined with a given
 * list of symbols.
 */
public interface Symbol {

  /**
   * Check if this symbol, combined with the other symbols in the list, make up a
   * composite symbol. If so, return the composite symbol. Else, return null.
   *
   * @param otherSymbols other symbols to combine with this symbol
   * @return composite symbol or null, if no composite symbol exists
   */
  CompositeSymbol checkSymbol(List<Symbol> otherSymbols);

  /**
   * Return true if this symbol could be a piece of a Snowman (i.e. this symbol is a circle).
   *
   * @return true if possible piece of snowman
   */
  boolean pieceOfSnowman();

  /**
   * Return true if this symbol could be a piece of a Triangle (i.e. this symbol is a line).
   *
   * @return true if possible piece of triangle
   */
  boolean pieceOfTriangle();

  /**
   * Draws the symbol onto the Graphics object.
   * CHANGE: This method was added to this interface during revisions, so that the view can
   * call on this method to draw each symbol via dynamic dispatch.
   *
   * @param g Graphics object on which the symbol should be drawn
   */
  void drawSymbol(Graphics g);

  /**
   * Return true if this object is a triangle.
   * @return true if triangle
   */
  boolean isCloak();

  /**
   * Return true if this object is a circle.
   * @return true if circle
   */
  boolean isStone();

  /**
   * Return true if this object is a triangle.
   * @return true if triangle
   */
  boolean isTriangle();

  /**
   * Draw the string naming this object onto the given Graphics object.
   * @param g given graphics object
   */
  void drawSymbolString(Graphics g);
}
