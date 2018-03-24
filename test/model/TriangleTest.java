package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// Accum Meredith and Dujia Guo


public class TriangleTest {
  private Line l1;
  private Line l2;
  private Line l3;

  @Before
  public void setUp() {
    l1 = new Line(-3, -3, 3, 3);
    l2 = new Line(3, 3, 6, -6);
  }

  /**
   * CHANGE: Changed the x point in l3 to account for larger thresholds.
   * Test if the constructor throws an illegal argument exception when the lines passed to
   * it cannot form a triangle.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalArgumentException() {
    l3 = new Line(50, 23, -3, -3);
    Triangle tri = new Triangle(l1, l2, l3);
  }

  /**
   * Test if the toString method for the triangle class returns a string
   * in the correct format.
   */
  @Test
  public void testToString() {
    l3 = new Line(6, -6, -3, -3);
    Triangle tri = new Triangle(l1, l2, l3);
    assertEquals("Triangle: [Line: (-3.00,-3.00) to (3.00,3.00)] "
            + "[Line: (3.00,3.00) to (6.00,-6.00)] "
            + "[Line: (6.00,-6.00) to (-3.00,-3.00)]", tri.toString());

  }


}