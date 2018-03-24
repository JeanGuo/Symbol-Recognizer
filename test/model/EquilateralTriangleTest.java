package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


// Accum Meredith and Dujia Guo

public class EquilateralTriangleTest {
  private Line l1;
  private Line l2;
  private Line l3;

  /**
   * Set up for tests.
   */
  @Before
  public void setUp() {
    l1 = new Line(0, 0, 1, Math.sqrt(3));
  }

  /**
   * CHANGE: Adjusted this test to account for different thresholds in determining
   * if a triangle is an Equilateral Triangle.
   * Test if the constructor correctly throws exceptions when the three lines given
   * is not equal in length so that it cannot construct an equilateral triangle.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalArgumentException() {
    l2 = new Line(20, Math.sqrt(3) * 20, 80, 0);
    l3 = new Line(80, 0, 0, 0);
    EquilateralTriangle tri = new EquilateralTriangle(l1, l2, l3);
  }

  /**
   * Test the toString method of an equilateral triangle.
   */
  @Test
  public void testToString() {
    l2 = new Line(1, Math.sqrt(3), 2, 0);
    l3 = new Line(2, 0, 0, 0);
    EquilateralTriangle tri = new EquilateralTriangle(l1, l2, l3);
    assertEquals("Equilateral Triangle: [Line: (0.00,0.00) to (1.00,1.73)] "
                    + "[Line: (1.00,1.73) to (2.00,0.00)] [Line: (2.00,0.00) to (0.00,0.00)]",
            tri.toString());
  }

}