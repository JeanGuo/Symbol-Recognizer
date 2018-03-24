package controller.mouse;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import controller.GestureController;
import controller.IGestureController;
import model.Point;
import model.SymbolRecognizer;
import model.SymbolRecognizerImpl;
import view.IGestureView;
import view.MockView;

import static org.junit.Assert.assertEquals;

// Accum Meredith, Guo Dujia

/**
 * JUnit test class for the MousePressDrag function object.
 */
public class MousePressDragTest {
  private IGestureView view;
  private IGestureController controller;
  private SymbolRecognizer model;
  private List<Point2D> mousePoints;


  /**
   * Set up for tests.
   */
  @Before
  public void setUp() {
    StringBuilder sb = new StringBuilder();
    SymbolRecognizer model = new SymbolRecognizerImpl();
    controller = new GestureController(model);
    view = new MockView(1000, 1000, controller, sb);

    mousePoints = new ArrayList<Point2D>();
    mousePoints.add(new Point(3, 4));
    mousePoints.add(new Point(4, 5));
    mousePoints.add(new Point(5, 6));
    mousePoints.add(new Point(6, 7));
  }

  /**
   * Test that the MousePressDrag function object does indeed add a point
   * to the controller's stored list of points.
   */
  @Test
  public void testMousePressDragAddsPointsToControllerList() {
    for (Point2D pt : mousePoints) {
      MousePressDrag.mousePressedOrDragged(pt, controller.getMousePoints(), view);
    }
    assertEquals(4, controller.getMousePoints().size(), .001);
  }
}