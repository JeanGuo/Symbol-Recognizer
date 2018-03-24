package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// Accum Meredith and Dujia Guo


/**
 * JUnit test class for the Snowman class.
 */
public class SnowmanTest {
  private Circle c1;
  private Circle c2;
  private Circle c3;

  /**
   * Set up for tests.
   */
  @Before
  public void setUp() {
    c1 = new Circle(0, 0, 3);
    c2 = new Circle(0, -9, 6);
  }

  /**
   * Test if the constructor throws an exception when the three circles passed to it cannot form a
   * snowman.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalArgumentException() {
    c3 = new Circle(0, -18, 3);
    Snowman snowy = new Snowman(c1, c2, c3);
  }


  /**
   * Test if the toString method for a snowman returns a string in the correct format.
   */
  @Test
  public void testToString() {
    c3 = new Circle(0, -22, 7);
    Snowman snowy = new Snowman(c1, c2, c3);
    assertEquals("Snowman: [Circle: (0.00,0.00) r=3.00] "
                    + "[Circle: (0.00,-9.00) r=6.00] [Circle: (0.00,-22.00) r=7.00]",
            snowy.toString());
  }

}