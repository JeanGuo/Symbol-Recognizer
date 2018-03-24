
package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


// Accum Meredith and Dujia Guo


/**
 * JUnit test class for the Line class.
 */
public class LineTest {
  private Line l1;
  private Line l2;
  private Line l3;
  private Line reversel1;
  private Line reversel2;
  private Line reversel3;

  private Line nearl2;

  private Line equi1;
  private Line equi2;
  private Line equi3;


  /**
   * Set up for tests.
   * CHANGE: In revising the model, these triangles were made larger, to account for
   * different error thresholds in determining which lines form triangles.
   */
  @Before
  public void setUp() {
    l1 = new Line(0, 0, 0, 5);
    l1 = new Line(0, 0, 0, 50);
    l2 = new Line(0, 50, 50, 0);
    l3 = new Line(50, 0, 0, 0);

    reversel1 = new Line(0, 50, 0, 0);
    reversel2 = new Line(50, 0, 0, 50);
    reversel3 = new Line(0, 0, 50, 0);

    nearl2 = new Line(.9, 50.3, 50.4, 0.7);

    equi1 = new Line(0, 0, 1, Math.sqrt(3));
    equi2 = new Line(1, Math.sqrt(3), 2, 0);
    equi3 = new Line(2, 0, 0, 0);

  }

  /**
   * Test that check triangle method works, regardless of the order in which
   * the lines are passed to the method, and regardless of which is the starting
   * vertex and which is the ending vertex.
   */
  @Test
  public void testCheckTriangleAllOrderingsQuadrant1() {
    assertTrue(l1.checkTriangle(l2, l3));
    assertTrue(l1.checkTriangle(l3, l2));
    assertTrue(l2.checkTriangle(l1, l3));
    assertTrue(l2.checkTriangle(l3, l1));
    assertTrue(l3.checkTriangle(l1, l2));
    assertTrue(l3.checkTriangle(l2, l1));

    assertTrue(reversel1.checkTriangle(reversel2, reversel3));
    assertTrue(reversel1.checkTriangle(reversel3, reversel2));
    assertTrue(reversel2.checkTriangle(reversel1, reversel3));
    assertTrue(reversel2.checkTriangle(reversel3, reversel1));
    assertTrue(reversel3.checkTriangle(reversel1, reversel2));
    assertTrue(reversel3.checkTriangle(reversel2, reversel1));

  }

  /**
   * Test if when lines are close to each other but not exactly touching, the check triangle
   * method still works.
   */
  @Test
  public void testCheckTrianglePointsNotExactlyMatching() {
    assertTrue(l1.checkTriangle(nearl2, l3));
  }

  /**
   * Test if the check triangle method works,
   * if the triangle is in the quadrant between negative x axis and positive y axis.
   */
  @Test
  public void testCheckTriangleQuadrant2() {
    Line l1q2 = new Line(-65, 23, -32, 11);
    Line l2q2 = new Line(-3, 5, -32, 11);
    Line l3q2 = new Line(-65, 23, -3, 5);
    assertTrue(l1q2.checkTriangle(l2q2, l3q2));

  }

  /**
   * Test if the check triangle method works,
   * if the triangle is in the quadrant between negative X and negative Y axes.
   */
  @Test
  public void testCheckTriangleQuadrant3() {
    Line l1q3 = new Line(-112, -112, -30, -45);
    Line l2q3 = new Line(-112, -112, -16, -80);
    Line l3q3 = new Line(-16, -80, -30, -45);
    assertTrue(l1q3.checkTriangle(l2q3, l3q3));
  }

  /**
   * Test if the check Triangle method works,
   * if the triangle is in the quadrant between positive X and negative Y axes.
   */
  @Test
  public void testCheckTriangleQuandrant4() {
    Line l1q4 = new Line(13, -50, 4, -11);
    Line l2q4 = new Line(4, -11, 45, -3);
    Line l3q4 = new Line(13, -50, 45, -3);
    assertTrue(l1q4.checkTriangle(l2q4, l3q4));
  }


  /**
   * Check if the testEquilateralTriangle method returns true if an equilateral triangle
   * is passed to it, or returns false if a unequilateral triangle is passed to it.
   */
  @Test
  public void testEquilateralTriangle() {
    assertTrue(equi1.checkEquilateral(equi2, equi3));
    assertFalse(l1.checkEquilateral(l2, l3));
  }


  /**
   * Check if the EquilateralTriangle method works if the lines lay inside the quadrant between
   * the negative X and Y axes.
   */
  @Test
  public void testEquilateralTriangleNegativeCoordinates() {
    Line equi1q3 = new Line(-3, -3, -2, Math.sqrt(3) - 3);
    Line equi2q3 = new Line(-2, Math.sqrt(3) - 3, -1, -3);
    Line equi3q3 = new Line(-1, -3, -3, -3);
    assertTrue(equi1q3.checkEquilateral(equi2q3, equi3q3));
  }

  /**
   * Test if the toString method for equilateral triangles returns a string in
   * the correct format.
   */
  @Test
  public void testToString() {
    assertEquals("Line: (0.00,0.00) to (0.00,50.00)", l1.toString());
  }

  /**
   * Test if a checkRectangle method returns true when given lines that form a Rectangle.
   */
  @Test
  public void testRectangle() {
    Line l1 = new Line(0, 0, 50, 0);
    Line l2 = new Line(50, 0, 50, 50);
    Line l3 = new Line(50, 50, 0, 50);
    Line l4 = new Line(0, 50, 0, 0);
    assertTrue(l1.checkRectangle(l2, l3, l4));
  }

  @Test
  public void testParallelogramWithoutRightAngles() {
    Line l1 = new Line(0, 0, 500, 0);
    Line l2 = new Line(500, 0, 650, 500);
    Line l3 = new Line(650, 500, 150, 500);
    Line l4 = new Line(150, 500, 0, 0);
    assertFalse(l1.checkRectangle(l2, l3, l4));
  }

  @Test
  public void testPolygonWithoutParallelLines() {
    Line l1 = new Line(-10, 0, 450, 0);
    Line l2 = new Line(448, 2, 610, 345);
    Line l3 = new Line(611, 347, -10, 200);
    Line l4 = new Line(-10, 200, -10, 0);
    assertFalse(l1.checkRectangle(l2, l3, l4));
  }

  /**
   * Test that four lines, not touching within the threshold, do not form a rectangle
   * even if they are parallel and would form right angles at their intersections.
   */
  @Test
  public void testFourLinesNotTouching() {
    Line l1 = new Line(-500, 0, 300, 0);
    Line l2 = new Line(300, 55, 300, 780);
    Line l3 = new Line(300, 900, -500, 900);
    Line l4 = new Line(-500, 750, -500, -80);
    assertFalse(l1.checkRectangle(l2, l3, l4));
  }

  @Test
  public void testGetStart() {
    Line line = new Line(11, 13, 14, 15);
    assertEquals(11, line.getStart().getX(), .01);
    assertEquals(13, line.getStart().getY(), .01);
  }

  @Test
  public void testGetEnd() {
    Line line = new Line(10, 12, 3, 7);
    assertEquals(3, line.getEnd().getX(), .01);
    assertEquals(7, line.getEnd().getY(), .01);
  }

  @Test
  public void testDeathlyHallows() {
    Line l1 = new Line(-5, 0, 5, 0);
    Line l2 = new Line(0, 9, 5, 0);
    Line l3 = new Line(0, 9, -5, 0);
    Triangle cloak = new Triangle(l1, l2, l3);
    Circle stone = new Circle(0, 4.25, 4);
    Line wand = new Line(0, 9, 0, 0);

    assertTrue(wand.checkDeathlyHallow(cloak, stone));
    assertTrue(wand.checkDeathlyHallow(stone, cloak));
  }

  @Test
  public void testDeathlyHallowsNotFormedWhenCircleOffCenter() {
    Line l1 = new Line(-5, 0, 5, 0);
    Line l2 = new Line(0, 9, 5, 0);
    Line l3 = new Line(0, 9, -5, 0);
    Triangle cloak = new Triangle(l1, l2, l3);
    Circle stone = new Circle(40, 34.25, 4);
    Line wand = new Line(0, 9, 0, 0);

    assertFalse(wand.checkDeathlyHallow(cloak, stone));
    assertFalse(wand.checkDeathlyHallow(stone, cloak));
  }

  @Test
  public void testDeathlyHallowsNotFormedWhenTriangleTooSmall() {
    Line l1 = new Line(-5, 0, 5, 0);
    Line l2 = new Line(0, 9, 5, 0);
    Line l3 = new Line(0, 9, -5, 0);
    Triangle cloak = new Triangle(l1, l2, l3);
    Circle stone = new Circle(0, 0, 50);
    Line wand = new Line(0, 80, 0, 0);
    assertFalse(wand.checkDeathlyHallow(cloak, stone));
  }

  @Test
  public void testDeathlyHallowsNotFormedWhenLineAskew() {
    Line l1 = new Line(-50, 0, 50, 0);
    Line l2 = new Line(0, 90, 50, 0);
    Line l3 = new Line(0, 90, -50, 0);
    Triangle cloak = new Triangle(l1, l2, l3);
    Circle stone = new Circle(0, 40.25, 4);
    Line wand = new Line(0, 9, -30, 0);
    assertFalse(wand.checkDeathlyHallow(cloak, stone));
  }

}