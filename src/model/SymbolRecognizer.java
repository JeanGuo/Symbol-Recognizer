// Accum Meredith and Dujia Guo


package model;

import java.util.List;

/**
 * This interface represents a SymbolRecognizer, which has the ability to add Lines, add Circles
 * and report all Symbols contained in the SymbolRecognizer.
 */
public interface SymbolRecognizer {

  /**
   * Add a line to the SymbolRecognizer.
   *
   * @param x    x-coordinate of starting Point
   * @param y    y-coordinate of starting Point
   * @param xEnd x-coordinate of ending Point
   * @param yEnd y-coordinate of ending Point
   */
  void addLine(double x, double y, double xEnd, double yEnd);

  /**
   * Add a circle to the SymbolRecognizer.
   *
   * @param x      x-coordinate of center
   * @param y      y-coordinate of center
   * @param radius radius of circle
   */
  void addCircle(double x, double y, double radius);

  /**
   * Report all the symbols in the symbol recognizer.
   *
   * @return list of all symbols in symbol Recognizer.
   */
  List<Symbol> reportSymbols();
}
