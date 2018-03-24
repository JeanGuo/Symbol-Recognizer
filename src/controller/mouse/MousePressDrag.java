package controller.mouse;
// Accum Meredith, Guo Dujia


import java.awt.geom.Point2D;
import java.util.List;
import model.Point;
import view.IGestureView;

/**
 * This class represents a function object that will be called on when a mouse is pressed
 * or when the mouse is dragged.
 */
public class MousePressDrag {

  /**
   * Add the given point to the given list of points and repaint the view.
   *
   * @param pt     given point
   * @param points given list of points
   * @param view   given view
   */
  public static void mousePressedOrDragged(Point2D pt, List<Point2D> points, IGestureView view) {
    points.add(new Point(pt.getX(), pt.getY()));
    view.rePaintCanvas();
  }
}
