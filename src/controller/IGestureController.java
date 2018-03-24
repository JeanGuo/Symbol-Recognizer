package controller;
// Accum Meredith, Guo Dujia


import java.awt.geom.Point2D;
import java.util.List;
import model.Symbol;
import view.IGestureView;

/**
 * This interface contains the methods that should be supported by the Controller for the
 * GestureRecognizer program.
 */
public interface IGestureController {

  /**
   * Use the given view as the view for this controller.
   *
   * @param view given view
   */
  void setView(IGestureView view);

  /**
   * Repaint the view's canvas.
   */
  void repaintCanvas();

  /**
   * Ask the model to add a symbol to it's list of symbols, using the given list of fittedPoints.
   */
  void askModelToAddSymbol(List<Double> fittedPoints);

  /**
   * Return the list of mouse points currently being stored by the controller.
   *
   * @return list of points
   */
  List<Point2D> getMousePoints();

  /**
   * Get the list of symbols being stored in the model from the model.
   *
   * @return List of symbols from model
   */
  List<Symbol> getSymbols();
}
