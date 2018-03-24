package controller;
// Accum Meredith, Guo Dujia


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Point;

/**
 * This class stores a static method that takes a list of points and fits either a line
 * or a circle to those points.
 */
public class FitSymbol {

  /**
   * Fit a line to the points, if the goodness of fit of the line is greater than .70, or
   * fit a circle to the points if not. If a line is fitted, return 4 doubles,
   * representing the starting and ending coordinates. If a circle is fitted, return three
   * doubles representing the center points and the radius.
   *
   * @param points list of points to fit a line or circle to
   * @return list of 4 doubles if a line was fitted, or 3 doubles if a circle was fitted.
   */
  public static List<Double> fitPoints(List<Point2D> points) {
    List<Double> pointList = new ArrayList<Double>();
    double goodnessOfFit = computeLineGoodnessOfFit(points);
    if (goodnessOfFit > .70) {
      // if it's a line, add 4 doubles to the list for starting and ending points
      pointList.add(computeLineStartPoint(points).getX());
      pointList.add(computeLineStartPoint(points).getY());
      pointList.add(computeLineEndPoint(points).getX());
      pointList.add(computeLineEndPoint(points).getY());
    } else {
      // if circle, add center x, center y, and radius to list
      pointList.add(computeCircleCX(points));
      pointList.add(computeCircleCY(points));
      pointList.add(computeCircleR(points));
    }
    return pointList;
  }

  /**
   * Compute the average x-coordinate of the given points.
   *
   * @param points given points
   * @return average x value
   */
  private static double computeLineCentroidX(List<Point2D> points) {
    double averageX = 0;
    for (Point2D pt : points) {
      averageX = averageX + pt.getX();
    }
    return averageX / points.size();
  }

  /**
   * Compute the average y coordinate of the given points.
   *
   * @param points given points
   * @return average y coordinate
   */
  private static double computeLineCentroidY(List<Point2D> points) {
    double averageY = 0;
    for (Point2D pt : points) {
      averageY = averageY + pt.getY();
    }
    return averageY / points.size();
  }

  /**
   * Compute Sxy for the given points.
   *
   * @param points given points
   * @return sum of (x-avgx)*(y-avgy) for each point
   */
  private static double computeLineSXY(List<Point2D> points) {
    double sum = 0;
    double avgX = computeLineCentroidX(points);
    double avgY = computeLineCentroidY(points);
    for (Point2D pt : points) {
      sum = sum + (pt.getX() - avgX) * (pt.getY() - avgY);
    }
    return sum;
  }

  /**
   * Compute Sxx (sum of (x-avgx)*(y-avgy) for each point) with the given points.
   *
   * @param points given points
   * @return sum of (x-avgx)*(y-avgy) for each point
   */
  private static double computeLineSXX(List<Point2D> points) {
    double sum = 0;
    double avgX = computeLineCentroidX(points);
    for (Point2D pt : points) {
      sum = sum + (pt.getX() - avgX) * (pt.getX() - avgX);
    }
    return sum;
  }

  /**
   * Compute Syy (sum of (y-avgY)(y-avgY) for each point) for the given points.
   *
   * @param points given points
   * @return sum of (y-avgY)(y-avgY)
   */
  private static double computeLineSYY(List<Point2D> points) {
    double sum = 0;
    double avgY = computeLineCentroidY(points);
    for (Point2D pt : points) {
      sum = sum + (pt.getY() - avgY) * (pt.getY() - avgY);
    }
    return sum;
  }

  /**
   * Compute Q = 2*Sxy / (Sxx-Syy) for the given points.
   *
   * @param points given points
   * @return Q
   */
  private static double computeLineQ(List<Point2D> points) {
    if (computeLineSXX(points) - computeLineSYY(points) == 0) {
      // if dividing by zero, increment denominator by a small amount
      return 2 * computeLineSXY(points) / .0000000001;
    }
    return 2 * computeLineSXY(points) / (computeLineSXX(points) - computeLineSYY(points));
  }

  /**
   * Compute theta for the given points, as the arctan of Q.
   *
   * @param points given points
   * @return theta = arctan(Q)
   */
  private static double computeLineTHETA(List<Point2D> points) {
    return Math.atan(computeLineQ(points));
  }

  /**
   * Compute which theta should be used to fit the line for the given points, as the theta that
   * gives a positive value of F(theta). The two options are theta, and theta + 180 degrees.
   *
   * @param points given points
   * @return theta
   */
  private static double computeWhichTheta(List<Point2D> points) {
    // compute this for theta and theta + 180
    double theta = computeLineTHETA(points);
    double theta180 = theta + Math.toRadians(180);
    double fOfM = 2 * computeLineSXY(points) * Math.sin(theta)
            - (computeLineSYY(points) - computeLineSXX(points)) * Math.cos(theta);
    double fOfM180 = 2 * computeLineSXY(points) * Math.sin(theta180)
            - (computeLineSYY(points) - computeLineSXX(points)) * Math.cos(theta180);
    // chose value of theta that gives a positive f(theta)
    if (fOfM > fOfM180) {
      return theta;
    } else {
      return theta180;
    }
  }

  /**
   * Compute A = cos(theta)/2 for the given points.
   *
   * @param points given points
   * @return A = cos(theta)/2
   */
  private static double computeLineA(List<Point2D> points) {
    return Math.cos(computeWhichTheta(points) / 2);
  }

  /**
   * Compute B = sin(theta)/2 for the given points.
   *
   * @param points the given points
   * @return B = sin(theta)/2
   */
  private static double computeLineB(List<Point2D> points) {
    return Math.sin(computeWhichTheta(points) / 2);
  }

  /**
   * Compute T = a(x-avgx)+b(y-avgy) for each point from the given point list.
   *
   * @param points given points
   * @return List of doubles representing T for each point
   */
  private static List<Double> computeLineTForEachPoint(List<Point2D> points) {
    List<Double> listT = new ArrayList<Double>();
    double a = computeLineA(points);
    double b = computeLineB(points);
    double avgX = computeLineCentroidX(points);
    double avgY = computeLineCentroidY(points);
    for (Point2D pt : points) {
      listT.add(a * (pt.getX() - avgX) + b * (pt.getY() - avgY));
    }
    return listT;
  }

  /**
   * Compute Tmin from the list of T, for the given points.
   *
   * @param points given points
   * @return minimum T
   */
  private static double computeLineTMin(List<Point2D> points) {
    List<Double> listT = computeLineTForEachPoint(points);
    return Collections.min(listT);
  }

  /**
   * Compute Tmax from the list of T, for the given points.
   *
   * @param points given points
   * @return maximum T
   */
  private static double computeLineTMax(List<Point2D> points) {
    List<Double> listT = computeLineTForEachPoint(points);
    return Collections.max(listT);
  }

  /**
   * Compute the starting point of a line fitted to the given points.
   *
   * @param points given points
   * @return starting point
   */
  private static Point2D computeLineStartPoint(List<Point2D> points) {
    double sx = computeLineCentroidX(points);
    double sy = computeLineCentroidY(points);
    double a = computeLineA(points);
    double b = computeLineB(points);
    double tmin = computeLineTMin(points);
    return new Point(sx + tmin * a, sy + tmin * b);
  }

  /**
   * Compute the ending point of a line fitted to the given points.
   *
   * @param points given points
   * @return ending point
   */
  private static Point2D computeLineEndPoint(List<Point2D> points) {
    double sx = computeLineCentroidX(points);
    double sy = computeLineCentroidY(points);
    double a = computeLineA(points);
    double b = computeLineB(points);
    double tmax = computeLineTMax(points);
    return new Point(sx + tmax * a, sy + tmax * b);
  }

  /**
   * Compute a list of TOrtho for each point from the given points.
   *
   * @param points given points
   * @return list of TOrtho
   */
  private static List<Double> computeLineTOrthoList(List<Point2D> points) {
    List<Double> listTOrtho = new ArrayList<Double>();
    double a = computeLineA(points);
    double b = computeLineB(points);
    double avgX = computeLineCentroidX(points);
    double avgY = computeLineCentroidY(points);
    for (Point2D pt : points) {
      listTOrtho.add(b * (pt.getX() - avgX) - a * (pt.getY() - avgY));
    }
    return listTOrtho;
  }

  /**
   * Compute minimum from list of TOrtho for the given points.
   *
   * @param points given points
   * @return TOrtho min
   */
  private static Double computeLineTOMin(List<Point2D> points) {
    List<Double> listTOrtho = computeLineTOrthoList(points);
    return Collections.min(listTOrtho);
  }

  /**
   * Compute max from list of TOrtho for the given points.
   *
   * @param points given points
   * @return TOrtho amx
   */
  private static Double computeLineTOMax(List<Point2D> points) {
    List<Double> listTOrtho = computeLineTOrthoList(points);
    return Collections.max(listTOrtho);
  }

  /**
   * Compute goodness of fit for a line fitted to the given points.
   *
   * @param points given points
   * @return goodness of fit of line
   */
  private static Double computeLineGoodnessOfFit(List<Point2D> points) {
    if (computeLineTMax(points) - computeLineTMin(points) == 0) {
      // if dividing by zero, increment denominator by a small amount
      return 1 - Math.min(1, ((computeLineTOMax(points) - computeLineTOMin(points))
              / .0000000001));
    }
    return 1 - Math.min(1, ((computeLineTOMax(points) - computeLineTOMin(points))
            / (computeLineTMax(points) - computeLineTMin(points))));
  }

  /**
   * Compute Sx = sum of all x, for the given points.
   *
   * @param points given points
   * @return sum of all x
   */
  private static double computeCircleSX(List<Point2D> points) {
    double sum = 0;
    for (Point2D pt : points) {
      sum = sum + pt.getX();
    }
    return sum;
  }

  /**
   * Compute Sy = sum of all y, for the given points.
   *
   * @param points given points
   * @return sum of all y
   */
  private static double computeCircleSY(List<Point2D> points) {
    double sum = 0;
    for (Point2D pt : points) {
      sum = sum + pt.getY();
    }
    return sum;
  }

  /**
   * Compute Sxy = sum of all y*x for the given points.
   *
   * @param points given points.
   * @return Sxy
   */
  private static double computeCircleSXY(List<Point2D> points) {
    double sum = 0;
    for (Point2D pt : points) {
      sum = sum + pt.getY() * pt.getX();
    }
    return sum;
  }

  /**
   * Compute Sxx = sum of all x*x for the given points.
   *
   * @param points given points
   * @return Sxx
   */
  private static double computeCircleSXX(List<Point2D> points) {
    double sum = 0;
    for (Point2D pt : points) {
      sum = sum + pt.getX() * pt.getX();
    }
    return sum;
  }

  /**
   * Compute Syy = sum of all y*y for the given points.
   *
   * @param points given points
   * @return Syy
   */
  private static double computeCircleSYY(List<Point2D> points) {
    double sum = 0;
    for (Point2D pt : points) {
      sum = sum + pt.getY() * pt.getY();
    }
    return sum;
  }

  /**
   * Compute Sx2y2 = sum (x*x)+(y*y) for all points from the given points.
   *
   * @param points given points
   * @return Sx2y2
   */
  private static double computeCircleSX2Y2(List<Point2D> points) {
    double sum = 0;
    for (Point2D pt : points) {
      sum = sum + pt.getX() * pt.getX() + pt.getY() * pt.getY();
    }
    return sum;
  }

  /**
   * Compute Sxx2y2 = sum of x*(x*x+y*y) for all given points.
   *
   * @param points given points
   * @return Sxx2y2
   */
  private static double computeCircleSXX2Y2(List<Point2D> points) {
    double sum = 0;
    for (Point2D pt : points) {
      sum = sum + pt.getX() * (pt.getX() * pt.getX() + pt.getY() * pt.getY());
    }
    return sum;
  }

  /**
   * Compute Syx2y2 = sum of y*(x*x+y*y) for all given points.
   *
   * @param points given points
   * @return Syx2y2
   */
  private static double computeCircleSYX2Y2(List<Point2D> points) {
    double sum = 0;
    for (Point2D pt : points) {
      sum = sum + pt.getY() * (pt.getX() * pt.getX() + pt.getY() * pt.getY());
    }
    return sum;
  }

  /**
   * Compute d for all given points, to fit a circle to the given points.
   *
   * @param points given points
   * @return d
   */
  private static double computeCircleD(List<Point2D> points) {
    double sum = 0;
    sum = computeCircleSXX(points) * (points.size() * computeCircleSYY(points)
            - computeCircleSY(points) * computeCircleSY(points))
            - computeCircleSXY(points) * (points.size() * computeCircleSXY(points)
            - computeCircleSX(points) * computeCircleSY(points))
            + computeCircleSX(points) * (computeCircleSXY(points) * computeCircleSY(points)
            - computeCircleSYY(points) * computeCircleSX(points));

    return sum;
  }

  /**
   * Compute da for all given points, to fit a circle to the given points.
   *
   * @param points given points
   * @return da
   */
  private static double computeCircleDA(List<Point2D> points) {
    double sum = computeCircleSXX2Y2(points) * (points.size() * computeCircleSYY(points)
            - computeCircleSY(points) * computeCircleSY(points));
    sum = sum - computeCircleSXY(points) * (points.size() * computeCircleSYX2Y2(points)
            - computeCircleSX2Y2(points) * computeCircleSY(points));
    sum = sum + computeCircleSX(points) * (computeCircleSYX2Y2(points) * computeCircleSY(points)
            - computeCircleSYY(points) * computeCircleSX2Y2(points));

    return sum;
  }

  /**
   * Compute db for all given points, to fit a circle to the given points.
   *
   * @param points given points
   * @return db
   */
  private static double computeCircleDB(List<Point2D> points) {
    double sum = 0;
    int n = points.size();
    sum = computeCircleSXX(points) * (n * computeCircleSYX2Y2(points)
            - computeCircleSX2Y2(points) * computeCircleSY(points));
    sum = sum - computeCircleSXX2Y2(points) * (n * computeCircleSXY(points)
            - computeCircleSX(points) * computeCircleSY(points));
    sum = sum + computeCircleSX(points) * (computeCircleSXY(points) * computeCircleSX2Y2(points)
            - computeCircleSYX2Y2(points) * computeCircleSX(points));
    return sum;
  }

  /**
   * Compute dc for all given points, to fit a circle to the given points.
   *
   * @param points given points
   * @return dc
   */
  private static double computeCircleDC(List<Point2D> points) {
    double sum = 0;
    int n = points.size();
    sum = computeCircleSXX(points) * (computeCircleSYY(points) * computeCircleSX2Y2(points)
            - computeCircleSYX2Y2(points) * computeCircleSY(points));
    sum = sum - computeCircleSXY(points) * (computeCircleSXY(points) * computeCircleSX2Y2(points)
            - computeCircleSYX2Y2(points) * computeCircleSX(points));
    sum = sum + computeCircleSXX2Y2(points) * (computeCircleSXY(points) * computeCircleSY(points)
            - computeCircleSYY(points) * computeCircleSX(points));
    return sum;
  }

  /**
   * Compute a = da/d for all given points, to fit a circle to the points.
   *
   * @param points given points
   * @return a = da/d
   */
  private static double computeCircleA(List<Point2D> points) {
    if (computeCircleD(points) == 0) {
      // if dividing by zero, increment denominator by a small amount
      return computeCircleDA(points) / .0000000001;
    }
    return computeCircleDA(points) / computeCircleD(points);
  }

  /**
   * Compute b = db/d for all given points, to fit a circle to the points.
   *
   * @param points given points
   * @return b = db/d
   */
  private static double computeCircleB(List<Point2D> points) {
    if (computeCircleD(points) == 0) {
      // if dividing by zero, increment denominator by a small amount
      return computeCircleDB(points) / .0000000001;
    }
    return computeCircleDB(points) / computeCircleD(points);
  }

  /**
   * Compute c = dc/d for all given points, to fit a circle to the given points.
   *
   * @param points given points
   * @return c = dc/d
   */
  private static double computeCircleC(List<Point2D> points) {
    if (computeCircleD(points) == 0) {
      // if dividing by zero, increment denominator by a small amount
      return computeCircleDC(points) / .0000000001;
    }
    return computeCircleDC(points) / computeCircleD(points);
  }

  /**
   * Compute cx = a/2 as the center x-coordinate of the fitted circle for the given points.
   *
   * @param points given points
   * @return cx = a/2
   */
  private static double computeCircleCX(List<Point2D> points) {
    return computeCircleA(points) / 2;
  }

  /**
   * Compute cy = b/2 as the center y-coordinate of the fitted circle for the given points.
   *
   * @param points given points
   * @return cy = b/2
   */
  private static double computeCircleCY(List<Point2D> points) {
    return computeCircleB(points) / 2;
  }

  /**
   * Compute radius of the fitted circle for the given points.
   *
   * @param points given points
   * @return radius
   */
  private static double computeCircleR(List<Point2D> points) {
    return Math.sqrt(computeCircleC(points) + computeCircleCX(points) *
            computeCircleCX(points) + computeCircleCY(points) * computeCircleCY(points));
  }

  /**
   * Compute m of the fitted circle for the given points.
   *
   * @param points given points
   * @return m
   */
  private static double computeCircleM(List<Point2D> points) {
    double di = 0;
    double sumDi = 0;
    for (Point2D pt : points) {
      double x = computeCircleCX(points);
      double y = computeCircleCY(points);
      di = Math.abs(Math.pow((pt.getX() - x), 2) + Math.pow((pt.getY() - y), 2)
              - computeCircleR(points) * computeCircleR(points));
      sumDi += di;
    }
    return sumDi;
  }

  /**
   * Compute q for the fitted circle of the given points.
   *
   * @param points given points
   * @return q
   */
  private static double computeCircleQ(List<Point2D> points) {
    return Math.sqrt(computeCircleM(points)) / (points.size());
  }

  /**
   * Compute the goodness of fit for the circle fitted to the given points.
   *
   * @param points given points
   * @return goodness of fit of circle
   */
  private static double computeCircleGoodnessOfFit(List<Point2D> points) {
    if (computeCircleR(points) == 0) {
      // if dividing by zero, increment denominator by a small amount
      return 1 - Math.min(1, computeCircleQ(points) / .0000000001);
    }
    return 1 - Math.min(1, computeCircleQ(points) / computeCircleR(points));
  }
}
