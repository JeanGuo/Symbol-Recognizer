package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


// Accum Meredith and Dujia Guo


/**
 * JUnit test class for the Point class.
 */
public class PointTest {
  private Point x0y0;
  private Point x50y50;
  private Point nearx0y0;

  /**
   * Set up for tests.
   */
  @Before
  public void setUp() {
    x0y0 = new Point(0, 0);
    x50y50 = new Point(50, 50);
    nearx0y0 = new Point(.05, .05);
  }


  /**
   * CHANGE: Edited this test to reflect that the margin of error for point closeness
   * was changed.
   * Test if the close method returns true if the points passed to it are sufficiently close to
   * each other, and returns false when the points are too far away.
   */
  @Test
  public void testClose() {
    assertFalse(x0y0.close(x50y50));
    assertTrue(x0y0.close(nearx0y0));
  }

  /**
   * Test if the toString method for points returns a String in the correct format.
   */
  @Test
  public void testToString() {
    assertEquals("(0.00,0.00)", x0y0.toString());
    assertEquals("(0.05,0.05)", nearx0y0.toString());
  }

}