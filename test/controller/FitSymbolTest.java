package controller;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import model.Point;

import static org.junit.Assert.assertEquals;

// Accum Meredith, Guo Dujia


/**
 * JUnit test class for the FitSymbol class.
 */
public class FitSymbolTest {
  private List<Point2D> mousePoints;

  /**
   * Set up for tests.
   */
  @Before
  public void setUp() {
    mousePoints = new ArrayList<Point2D>();
  }


  /**
   * Test for fitting points to a line exactly.
   */
  @Test
  public void testFittingExactLine() {
    mousePoints = new ArrayList<Point2D>();
    mousePoints.add(new Point(-3, -3));
    mousePoints.add(new Point(1, 1));
    mousePoints.add(new Point(10, 10));

    List<Double> fittedPoints = FitSymbol.fitPoints(mousePoints);
    // fittedPoints size should be 4 if a line was fitted to the points
    assertEquals(4, fittedPoints.size(), .001);
    assertEquals(-3, fittedPoints.get(0), .001);
    assertEquals(-3, fittedPoints.get(1), .001);
    assertEquals(10, fittedPoints.get(2), .001);
    assertEquals(10, fittedPoints.get(3), .001);
  }

  /**
   * Test for fitting points to a circle exactly.
   */
  @Test
  public void testFittingExactCircle() {
    mousePoints = new ArrayList<Point2D>();
    mousePoints.add(new Point(0, 5));
    mousePoints.add(new Point(0, -5));
    mousePoints.add(new Point(-5, 0));
    mousePoints.add(new Point(5, 0));

    List<Double> fittedPoints = FitSymbol.fitPoints(mousePoints);
    // fittedPoints size should be 3 if a circle was fitted to the points
    assertEquals(3, fittedPoints.size(), .001);
    // x-point of center should be 0
    assertEquals(0, fittedPoints.get(0), .001);
    // y-point of center should be 0
    assertEquals(0, fittedPoints.get(1), .001);
    // radius should be 5
    assertEquals(5, fittedPoints.get(2), .001);
  }

  /**
   * Test for fitting points to a line approximately.
   */
  @Test
  public void testFittingInexactLine() {
    mousePoints = new ArrayList<Point2D>();
    mousePoints.add(new Point(-9, -9));
    mousePoints.add(new Point(-3.4, -3.99));
    mousePoints.add(new Point(1.5, 1.25));
    mousePoints.add(new Point(6.7, 6));
    mousePoints.add(new Point(11, 11));
    mousePoints.add(new Point(34, 35.5));


    List<Double> fittedPoints = FitSymbol.fitPoints(mousePoints);
    // fittedPoints size should be 4 if a line was fitted to the points
    assertEquals(4, fittedPoints.size(), .001);
    assertEquals(-9, fittedPoints.get(0), 1);
    assertEquals(-9, fittedPoints.get(1), 1);
    assertEquals(35, fittedPoints.get(2), 1);
    assertEquals(35, fittedPoints.get(3), 1);
  }


  /**
   * Test for fitting points to a circle approximately.
   */
  @Test
  public void testFittingInexactCircle() {
    mousePoints = new ArrayList<Point2D>();
    mousePoints.add(new Point(0, 10.5));
    mousePoints.add(new Point(-5, -8.5));
    mousePoints.add(new Point(9.5, 0));
    mousePoints.add(new Point(-10, 0));

    List<Double> fittedPoints = FitSymbol.fitPoints(mousePoints);
    assertEquals(3, fittedPoints.size(), 0.5);
    assertEquals(0, fittedPoints.get(0), 0.5);
    assertEquals(0, fittedPoints.get(1), 0.5);
    assertEquals(10, fittedPoints.get(2), 0.5);
  }

}