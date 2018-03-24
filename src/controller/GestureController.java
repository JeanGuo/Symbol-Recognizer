package controller;
// Accum Meredith, Guo Dujia

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import controller.mouse.MousePressDrag;
import controller.mouse.MouseReleaser;
import model.Symbol;
import model.SymbolRecognizer;
import view.IGestureView;

/**
 * This class represents a Gesture Controller for the GestureRecognizer program.
 * It takes a SymbolRecognizer model and passes data from the user to the model and then
 * passes data from the model back to the view.
 */
public class GestureController implements IGestureController {
  private SymbolRecognizer model;
  private IGestureView view;
  private List<Point2D> mousePoints;

  /**
   * Construct a GestureController with the given model.
   *
   * @param model the given model
   */
  public GestureController(SymbolRecognizer model) {
    this.model = model;
    mousePoints = new ArrayList<Point2D>();
  }

  /**
   * Get the list of symbols being stored in the model from the model.
   *
   * @return List of symbols from model
   */
  public List<Symbol> getSymbols() {
    return model.reportSymbols();
  }

  /**
   * Return the list of mouse points currently being stored by the controller.
   *
   * @return list of points
   */
  public List<Point2D> getMousePoints() {
    return mousePoints;
  }

  /**
   * Ask the model to add a symbol to its list of symbols, using the given list of fittedPoints.
   * If there are four points in the list, add a line. If there are three points, add a circle.
   */
  public void askModelToAddSymbol(List<Double> fittedPoints) {
    if (fittedPoints.size() != 4 && fittedPoints.size() != 3) {
      throw new IllegalArgumentException("Invalid number of doubles given. Cannot make a"
              + "line or a circle");
    }
    if (fittedPoints.size() == 4) {
      // add line to model
      model.addLine(fittedPoints.get(0), fittedPoints.get(1),
              fittedPoints.get(2), fittedPoints.get(3));
    } else if (fittedPoints.size() == 3) {
      // add circle to model
      model.addCircle(fittedPoints.get(0), fittedPoints.get(1), fittedPoints.get(2));
    }
  }


  /**
   * Use the given view as the view for this controller. Also, set the mouse
   * listener and mouse motion listener for the view.
   *
   * @param view given view
   */
  @Override
  public void setView(IGestureView view) {
    this.view = view;
    //set the mouse listener
    setMouseListener();
    repaintCanvas();
  }

  /**
   * Repaint the canvas of the controller's view.
   */
  public void repaintCanvas() {
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);
    view.rePaintCanvas();
  }

  /**
   * Create a Mouser object (which extends MouseAdapter) and pass this object
   * to the view.
   */
  private void setMouseListener() {
    Mouser mouser = new Mouser();
    view.setMouseListener(mouser);
  }

  /**
   * This class represents a Mouser object, which extends the MouseAdapter. It performs
   * an action when the user presses the mouse, drags the mouse, and releases the mouse. Those
   * actions are handled by function objects.
   */
  class Mouser extends MouseAdapter {

    /**
     * When the user presses the Mouse, pass the mouse point from the mouse event
     * to a function object along with the controller's list of stored mouse points, and the view.
     *
     * @param e mouse event captured by MouseAdapter
     */
    @Override
    public void mousePressed(MouseEvent e) {
      MousePressDrag.mousePressedOrDragged(e.getPoint(), mousePoints, view);
    }

    /**
     * When the user drags the Mouse, pass the mouse point from the mouse event
     * to a function object along with the controller's list of stored mouse points, and the view.
     *
     * @param e mouse event captured by Mouse Adapter
     */
    @Override
    public void mouseDragged(MouseEvent e) {
      MousePressDrag.mousePressedOrDragged(e.getPoint(), mousePoints, view);
    }

    /**
     * When the user releases the Mouse, pass the mouse point from the mouse event
     * to a function object along with the controller's list of stored mouse points, and the view.
     *
     * @param e mouse event captured by Mouse Adapter
     */
    @Override
    public void mouseReleased(MouseEvent e) {
      MouseReleaser.mouseReleased(e.getPoint(), mousePoints, view, GestureController.this);
    }

  }
}
