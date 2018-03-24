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
 * JUnit test class for the MouseReleaser function object.
 */
public class MouseReleaserTest {
  private IGestureView view;
  private StringBuilder sb;
  private IGestureController controller;
  private List<Point2D> mousePoints;

  /**
   * Set up for tests.
   */
  @Before
  public void setUp() {
    sb = new StringBuilder();
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
   * Test that the MouseReleaser function object passes the list of mouse
   * points to the view and then clears the controller's list of
   * mouse points.
   */
  @Test
  public void testMouseReleaser() {
    for (Point2D pt : mousePoints) {
      MousePressDrag.mousePressedOrDragged(pt, controller.getMousePoints(), view);
    }
    assertEquals(4, controller.getMousePoints().size(), .001);
    MouseReleaser.mouseReleased(new Point(8, 9), controller.getMousePoints(), view, controller);
    controller.setView(view);

    // MouseReleaser.mouseReleased() should clear list to 0 after being called
    assertEquals(0, controller.getMousePoints().size(), .001);
    // Confirm that MouseReleaser.mouseReleased() passed the info on to the view
    assertEquals("Width:1000 Height:1000\n" +
            "Symbols:\n" +
            "Line: (3.00,4.00) to (8.00,9.00)\n", sb.toString());
  }

}