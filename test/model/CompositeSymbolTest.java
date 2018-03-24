package model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;


// Accum Meredith and Dujia Guo

/**
 * JUnit test class for the CompositeSymbol class.
 */
public class CompositeSymbolTest {
  private Symbol sym1;

  /**
   * Test if the checkSymbol method returns null when called on a composite symbol.
   */
  @Test
  public void testCheckSymbol() {
    sym1 = new Snowman(new Circle(0, 0, 3),
            new Circle(0, 9, 6), new Circle(0, 24, 9));
    assertNull(sym1.checkSymbol(new ArrayList<Symbol>()));
  }

  /**
   * Test if the PieceofSnowman method returns null when called on something that is not a circle.
   */
  @Test
  public void testPieceOfSnowman() {
    sym1 = new Line(0, 0, 3, 3);
    assertFalse(sym1.pieceOfSnowman());
  }

  /**
   * Test if the PieceOfTriangle method returns null when called on something that is not a line.
   */
  @Test
  public void testPieceOfTriangle() {
    sym1 = new Circle(0, 0, 16);
    assertFalse(sym1.pieceOfTriangle());
  }

}