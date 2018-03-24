// Accum Meredith and Dujia Guo


package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class represents a Composite Symbol which is composed of a list of
 * other Symbols. The class is generic, where T must be a class that extends Symbol.
 */
public abstract class CompositeSymbol<T extends Symbol> implements Symbol {
  protected List<T> symbols;

  /**
   * Construct a new CompositeSymbol as an empty list of symbols.
   */
  public CompositeSymbol() {
    symbols = new ArrayList<T>();
  }

  /**
   * Check if this symbol, combined with the other symbols in the list, make up a
   * composite symbol. If so, return the composite symbol. Else, return null.
   *
   * @param otherSymbols other symbols to combine with this symbol
   * @return composite symbol or null, if no composite symbol exists
   */
  public CompositeSymbol checkSymbol(List<Symbol> otherSymbols) {
    return null;
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
    return false;
  }

  /**
   * Return true if this is a triangle.
   * @return true if cloak (of deathly hallow)
   */
  public boolean isCloak() {
    return false;
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
  public boolean isTriangle() {return false;}

}
