package controller.mouse;
// Accum Meredith, Guo Dujia

import java.awt.geom.Point2D;
import java.util.List;

import controller.FitSymbol;
import controller.IGestureController;
import model.Point;
import view.IGestureView;

/**
 * This class represents a function object that will be called on when a mouse is released.
 */
public class MouseReleaser {

  /**
   * Add the given point to the given list of points, then fit a line or circle
   * to the given list of points. Pass the points for the fitted line or circle to the
   * controller. Clear the point list (as this was the last mouse point in a
   * single dragging motion) and repaint the View.
   *
   * @param pt     given point
   * @param points given point list
   * @param view   given view
   * @param controller  given controller
   */
  public static void mouseReleased(Point2D pt, List<Point2D> points, IGestureView view,
                                           IGestureController controller) {
    points.add(new Point(pt.getX(), pt.getY()));
    List<Double> fittedPoints = FitSymbol.fitPoints(points);
    // clear the mouse points list
    points.clear();
    controller.askModelToAddSymbol(fittedPoints);
    view.rePaintCanvas();
  }
}
