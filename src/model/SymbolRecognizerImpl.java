// Accum Meredith and Dujia Guo


package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the SymbolRecognizer interface. It contains a list of all the symbols
 * that have been added and it checks if added symbols form composite symbols with other
 * added symbols. If so, a composite symbol replaces those symbols in the list.
 */
public class SymbolRecognizerImpl implements SymbolRecognizer {
  private List<Symbol> symbolList;

  /**
   * Construct a SymbolRecognizerImplementation.
   */
  public SymbolRecognizerImpl() {
    symbolList = new ArrayList<Symbol>();
  }

  /**
   * Add a line to the SymbolRecognizer, checking if it makes a composite symbol
   * with previously added symbols.
   *
   * @param x    x-coordinate of starting Point
   * @param y    y-coordinate of starting Point
   * @param xEnd x-coordinate of ending Point
   * @param yEnd y-coordinate of ending Point
   */
  @Override
  public void addLine(double x, double y, double xEnd, double yEnd) {
    Line l = new Line(x, y, xEnd, yEnd);
    symbolList.add(l);

    // check most recent 3 added symbols
    CompositeSymbol composite = checkComposite();
    if (composite != null) {
      // remove symbols and add the shape
      symbolList.add(composite);
      addComposite(composite);
    }
  }

  /**
   * Check if the given composite symbol can be used to form another symbol with the
   * other symbols in the model. If so, add that symbol and remove the others.
   * @param s given symbol to be added
   */
  private void addComposite(Symbol s) {
    CompositeSymbol composite = checkComposite();
    if (composite != null) {
      // remove symbols and add the shape
      symbolList.add(composite);
    }
  }


  /**
   * Add a circle to the SymbolRecognizer, checking if it makes a composite symbol
   * with previously added symbols.
   *
   * @param x      x-coordinate of center
   * @param y      y-coordinate of center
   * @param radius radius of circle
   */
  @Override
  public void addCircle(double x, double y, double radius) {
    Circle c = new Circle(x, y, radius);
    symbolList.add(c);

    // check most recent 3 added symbols
    CompositeSymbol<Symbol> composite = checkComposite();
    if (composite != null) {
      // remove symbols and add the shape
      symbolList.add(composite);
      addComposite(composite);
    }
  }

  /**
   * Report all the symbols in the symbol recognizer.
   *
   * @return list of all symbols in symbol Recognizer.
   */
  @Override
  public List<Symbol> reportSymbols() {
    return symbolList;
  }

  /**
   * Return a string in the format: "Symbols:\n" followed by each Symbol's toString on a new line.
   *
   * @return string in above format
   */
  @Override
  public String toString() {
    String str = "Symbols:\n";
    for (Symbol s : symbolList) {
      str = str + s.toString() + "\n";
    }
    return str;
  }

  /**
   * Check if the most recently added symbol can be combined with other added symbols to
   * form a composite symbol.
   *
   * @return the CompositeSymbol if one exists, or null if none exists
   */
  private CompositeSymbol checkComposite() {
    // check if there are at least 4 symbols in list
    if (symbolList.size() < 3) {
      return null;
    }

    // get last items in the list that could form a symbol
    Symbol s1 = symbolList.get(symbolList.size() - 1);
    return s1.checkSymbol(symbolList);
  }


}
