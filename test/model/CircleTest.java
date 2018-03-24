package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

// Accum Meredith and Dujia Guo

/**
 * JUnit test class for the Circle class.
 */
public class CircleTest {
  private Circle c1;
  private Circle c2;

  /**
   * Set up for tests.
   */
  @Before
  public void setUp() {
    c1 = new Circle(0, 0, 5);
  }


  /**
   * Test if the circle constructor throws an exception for illegal parameters.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalRadiusThrowsIllegalArgumentException() {
    // radius of 0 should throw an IllegalArgumentException
    Circle c = new Circle(0, 0, 0);
  }

  /**
   * Test if the upright snowman (smallest circle on the top) works properly.
   */
  @Test
  public void testCheckSnowmanUpright() {
    c2 = new Circle(0, 12, 7);
    Circle c3 = new Circle(0, 29, 10);
    assertTrue(c1.checkSnowman(c2, c3));
    assertTrue(c2.checkSnowman(c1, c3));
    assertTrue(c3.checkSnowman(c2, c1));
  }

  /**
   * CHANGE: Edited this test to account for increased thresholds in the functions
   * to check if points are collinear and if circles are touching.
   * Check for incorrect circumstances of snowmen:
   * circles collinear but not touching;
   * circles are not arranged according to ascending or descending size;
   * circle are of the correct sizes but not collinear.
   */
  @Test
  public void testCheckSnowmanFakeSnowmen() {
    // collinear, but not touching
    c2 = new Circle(0, 100, 95);
    Circle c4 = new Circle(0, 1000, 10);
    assertFalse(c1.checkSnowman(c2, c4));
    // incorrect sizes
    Circle c5 = new Circle(0, 101, 1);
    assertFalse(c1.checkSnowman(c2, c5));
    // correct sizes, not collinear
    Circle c6 = new Circle(30, 150, 100);
    assertFalse(c1.checkSnowman(c2, c6));
  }

  /**
   * Check for snowmen whose centers lie on the positive X axis.
   */
  @Test
  public void testCheckSnowmanSidewaysRight() {
    Circle c2SidewaysRight = new Circle(18, 0, 13);
    Circle c3SidewaysRight = new Circle(51, 0, 20);
    assertTrue(c1.checkSnowman(c2SidewaysRight, c3SidewaysRight));
  }

  /**
   * Check for snowmen whose centers lie on the negative X axis.
   */
  @Test
  public void testCheckSnowmanSidewaysLeft() {
    Circle c2SidwaysLeft = new Circle(-15, 0, 10);
    Circle c3SidewaysLeft = new Circle(-46, 0, 21);
    assertTrue(c1.checkSnowman(c2SidwaysLeft, c3SidewaysLeft));
  }

  /**
   * Test if the upsidedown snowmen(smallest circle on the bottom) works properly.
   */
  @Test
  public void testCheckSnowmanUpsideDown() {
    Circle c3UpsideDown = new Circle(0, -14, 1);
    Circle c2UpsideDown = new Circle(0, -9, 4);
    assertTrue(c1.checkSnowman(c2UpsideDown, c3UpsideDown));
  }

  /**
   * Test if snowmen whose centers lie on the diagonal line between the XY axis work properly.
   */
  @Test
  public void testCheckSnowmanDiagonal() {
    Circle c2Diagnol = new Circle(15, 20, 20);
    Circle c3Diagnol = new Circle(51, 68, 40);
    assertTrue(c1.checkSnowman(c2Diagnol, c3Diagnol));
  }

  /**
   * Test the toString method of a circle returns a string in the correct format.
   */
  @Test
  public void testToString() {
    assertEquals("Circle: (0.00,0.00) r=5.00", c1.toString());
  }

}