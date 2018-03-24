package controller;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import controller.mouse.MousePressDrag;
import controller.mouse.MouseReleaser;
import model.Point;
import model.SymbolRecognizer;
import model.SymbolRecognizerImpl;
import view.IGestureView;
import view.MockView;

import static org.junit.Assert.assertEquals;

// Accum Meredith, Guo Dujia

/**
 * JUnit test class for the IGestureController. Tests use a mock view which
 * writes to a StringBuilder.
 */
public class IGestureControllerTest {
  private IGestureController controller;
  private StringBuilder ap;
  private IGestureView view;
  private List<Point2D> mousePoints;

  /**
   * Set up for tests.
   */
  @Before
  public void setUp() {
    SymbolRecognizer model = new SymbolRecognizerImpl();
    controller = new GestureController(model);
    ap = new StringBuilder();
    view = new MockView(1000, 1000, controller, ap);
    mousePoints = new ArrayList<Point2D>();

    mousePoints.add(new Point(3, 3));
    mousePoints.add(new Point(3.12, 3.12));
    mousePoints.add(new Point(3.75, 3.75));
    mousePoints.add(new Point(4, 4));
    mousePoints.add(new Point(4.5, 4.5));
    mousePoints.add(new Point(5, 5));
  }

  /**
   * Test if the controller passes width and height to view.
   */
  @Test
  public void testControllerSetView() {
    controller.setView(view);
    assertEquals("Width:1000 Height:1000\n" +
            "Symbols:\n", ap.toString());
  }


  /**
   * Test if the getSymbols method in the controller gets the list of symbols
   * from the model.
   */
  @Test
  public void testGetSymbols() {
    for (Point2D pt : mousePoints) {
      MousePressDrag.mousePressedOrDragged(pt, controller.getMousePoints(), view);
    }
    mousePoints.add(new Point(6, 6));
    MouseReleaser.mouseReleased(mousePoints.get(6), controller.getMousePoints(), view, controller);

    mousePoints.clear();
    mousePoints.add(new Point(-3, 7));
    mousePoints.add(new Point(-3, -7));
    mousePoints.add(new Point(-10, 0));
    for (Point2D pt : mousePoints) {
      MousePressDrag.mousePressedOrDragged(pt, controller.getMousePoints(), view);
    }
    mousePoints.add(new Point(7, 0));
    MouseReleaser.mouseReleased(mousePoints.get(3), controller.getMousePoints(), view, controller);

    // test that getSymbols() gets the symbol list from the model, which should contain 2 symbols
    assertEquals(2, controller.getSymbols().size(), .001);
  }

  /**
   * Test that the getMousePoints method gets the list of mouse points stored
   * in the controller.
   */
  @Test
  public void testGetMousePoints() {
    for (Point2D pt : mousePoints) {
      MousePressDrag.mousePressedOrDragged(pt, controller.getMousePoints(), view);
    }
    // there should be 6 mouse points in the controller's mouse point list right now
    assertEquals(6, controller.getMousePoints().size(), .001);
  }

  /**
   * Test that the controller passes symbols from model to the view, to be written to a
   * StringBuilder object.
   */
  @Test
  public void testControllerPassesSymbolsToView() {

    // add a line
    for (Point2D pt : mousePoints) {
      MousePressDrag.mousePressedOrDragged(pt, controller.getMousePoints(), view);
    }
    mousePoints.add(new Point(80, 80));
    MouseReleaser.mouseReleased(mousePoints.get(6), controller.getMousePoints(), view, controller);

    // add a line
    MousePressDrag.mousePressedOrDragged(new Point(80, 85), controller.getMousePoints(), view);
    MouseReleaser.mouseReleased(new Point(80, 5), controller.getMousePoints(), view, controller);

    // add a line
    MousePressDrag.mousePressedOrDragged(new Point(2, 3), controller.getMousePoints(), view);
    MouseReleaser.mouseReleased(new Point(80, 3), controller.getMousePoints(), view, controller);

    // add a line
    MousePressDrag.mousePressedOrDragged(new Point(11, 2), controller.getMousePoints(), view);
    MouseReleaser.mouseReleased(new Point(6, 28), controller.getMousePoints(), view, controller);

    // add a circle
    MousePressDrag.mousePressedOrDragged(new Point(5, 5), controller.getMousePoints(), view);
    MousePressDrag.mousePressedOrDragged(new Point(-5, -5), controller.getMousePoints(), view);
    MousePressDrag.mousePressedOrDragged(new Point(5, -5), controller.getMousePoints(), view);
    MouseReleaser.mouseReleased(new Point(-5, 5), controller.getMousePoints(), view, controller);

    controller.setView(view);

    assertEquals("Width:1000 Height:1000\n" +
            "Symbols:\n" +
            "Triangle: [Line: (2.00,3.00) to (80.00,3.00)] " +
            "[Line: (80.00,5.00) to (80.00,85.00)] " +
            "[Line: (3.00,3.00) to (80.00,80.00)]\n" +
            "Line: (11.00,2.00) to (6.00,28.00)\n" +
            "Circle: (0.00,0.00) r=7.07\n", ap.toString());
  }

}