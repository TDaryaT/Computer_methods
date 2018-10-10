package ru.nsu.mmf.g16121.ddt.math;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static ru.nsu.mmf.g16121.ddt.main.Main.*;

public class HeatEquations {
    private static final double leftBound = 0;
    private static final double rightBound = 1;

    private static final int NUMBERS_COUNT_OF_GRID_BY_X = 500;
    private static final int NUMBERS_COUNT_OF_GRID_BY_T = 500;

    private static final double stepX = (rightBound - leftBound) / NUMBERS_COUNT_OF_GRID_BY_X;
    private static final double stepT = (rightBound - leftBound) / NUMBERS_COUNT_OF_GRID_BY_T;

    private static final double COURANT_NUMBER_ANALOGUE =
            2.0 * COEFFICIENT_AT_SECOND_DERIVATIVE * stepT / Math.pow(stepX, 2);

    /**
     * @param rightPart - this is array of the right part of linear equation system Au = rightPart, where
     *                  A is triDiagonal matrix with coeff:
     *                  diag - is coefficient of the matrix A on the diagonal;
     *                  overDiagonal - the coefficient of the matrix And the overdiagonal and subdiagonal;
     *                  <p>
     * @return solution matrix of a linear system.
     */

    private static double[] sweepMethodWithConstCoef(double[] rightPart) {
        double diag = 1.0 + COURANT_NUMBER_ANALOGUE;
        double overDiagonal = -COURANT_NUMBER_ANALOGUE * 0.5;

        double[] u = new double[rightPart.length];

        double[] alpha = new double[rightPart.length - 1];
        double[] beta = new double[rightPart.length - 1];

        alpha[rightPart.length - 2] = -overDiagonal / diag;
        beta[rightPart.length - 2] = rightPart[rightPart.length - 1] / diag;

        for (int i = rightPart.length - 3; i >= 0; i--) {
            alpha[i] = -(overDiagonal / (diag + overDiagonal * alpha[i + 1]));
            beta[i] = ((rightPart[i + 1] - beta[i + 1] * overDiagonal) / (diag + overDiagonal * alpha[i + 1]));
        }

        u[0] = ((rightPart[0] - overDiagonal * beta[0]) / (diag + overDiagonal * alpha[0]));
        for (int i = 1; i < rightPart.length; i++) {
            u[i] = alpha[i - 1] * u[i - 1] + beta[i - 1];
        }
        return u;
    }

    /**
     * Tn this method <>writeInTxt</> we write points surface in txt file: x - in 1st column, t - in 2d column,
     * exact value of the func - 3d column and our func value in 4d column (for gnuplot)
     */

    private static void writeInTxt(double[][] u) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("funcrig.txt");

        double x;
        double t = leftBound;
        for (int i = 0; i <= NUMBERS_COUNT_OF_GRID_BY_T; i++) {
            x = leftBound;
            for (int j = 0; j <= NUMBERS_COUNT_OF_GRID_BY_X; j++) {
                writer.println(x + "\t" + t + "\t" + u(x, t) + "\t" + u[i][j]);
                x += stepX;
            }
            t += stepT;
        }
        writer.close();
    }

    private static void writeErrorInTxt(double[][] u) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("error.txt");

        double x;
        double t = leftBound;
        for (int i = 0; i <= NUMBERS_COUNT_OF_GRID_BY_T; i++) {
            x = leftBound;
            for (int j = 0; j <= NUMBERS_COUNT_OF_GRID_BY_X; j++) {
                writer.println(x + "\t" + t + "\t" + (u(x, t) - u[i][j]));
                x += stepX;
            }
            t += stepT;
        }
        writer.close();
    }

    /**
     * Tn this method <>solveHeatEquation</> solve the heat equation solves
     * the heat equation using the Euler method, with initial conditions (<></>, and
     * writes data to a text file to display the result.
     */

    public static void solveHeatEquation() throws FileNotFoundException {
        double[][] u = new double[NUMBERS_COUNT_OF_GRID_BY_T + 1][NUMBERS_COUNT_OF_GRID_BY_X + 1];

        //The first row of the matrix is filled by the initial data.
        double x = leftBound;
        for (int i = 0; i <= NUMBERS_COUNT_OF_GRID_BY_X; i++) {
            u[0][i] = mu(x);
            x += stepX;
        }

        //The first and second columns are filled with source data.
        double t = leftBound;
        for (int j = 0; j <= NUMBERS_COUNT_OF_GRID_BY_T; j++) {
            u[j][0] = mu1(t);
            u[j][NUMBERS_COUNT_OF_GRID_BY_X] = mu2(t);
            t += stepT;
        }

        //build the right part for the sweep method
        t = leftBound;
        for (int j = 0; j <= NUMBERS_COUNT_OF_GRID_BY_T - 1; j++) {
            x = leftBound + stepX;
            double[] rightPart = new double[NUMBERS_COUNT_OF_GRID_BY_X - 1];
            for (int i = 0; i <= NUMBERS_COUNT_OF_GRID_BY_X - 2; i++) {
                rightPart[i] = u[j][i + 1] + stepT * f(x, t + stepT);
                x += stepX;
            }
            rightPart[0] += COURANT_NUMBER_ANALOGUE * 0.5 * mu1(t + stepT);
            rightPart[NUMBERS_COUNT_OF_GRID_BY_X - 2] += COURANT_NUMBER_ANALOGUE * 0.5 * mu2(t + stepT);

            //fill the rest of the matrix
            System.arraycopy(sweepMethodWithConstCoef(rightPart),
                    0, u[j + 1], 1, NUMBERS_COUNT_OF_GRID_BY_X - 1);
            t += stepT;
        }

        //write in the txt for display the result
        writeInTxt(u);
        writeErrorInTxt(u);
    }
}
