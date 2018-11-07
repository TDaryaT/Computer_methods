package ru.nsu.mmf.g16121.ddt.math;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static ru.nsu.mmf.g16121.ddt.main.Main.*;

public class ConvectionEquation {
    private static final double stepX = (rightBound - leftBound) /
            NUMBERS_COUNT_OF_GRID_BY_X;
    private static final double stepT = (rightBound - leftBound) /
            NUMBERS_COUNT_OF_GRID_BY_T;

    private static void writeResultForPython(double[][] u) {
        try (PrintWriter writer = new PrintWriter("result.txt")) {

            writer.print("[[" + (int) leftBound + ", " + (int) rightBound
                    + ", " + (NUMBERS_COUNT_OF_GRID_BY_X + 1) + "],");
            writer.print("[" + (int) leftBound + ", " + (int) rightBound
                    + ", " + (NUMBERS_COUNT_OF_GRID_BY_T + 1) + "],");

            double x;
            double t = leftBound;

            writer.print("[");
            for (int i = 0; i <= NUMBERS_COUNT_OF_GRID_BY_T; i++) {
                x = leftBound;
                writer.print("[");
                for (int j = 0; j < NUMBERS_COUNT_OF_GRID_BY_X; j++) {
                    writer.print(u[i][j] + ",");
                    x += stepX;
                }
                writer.print(u[i][NUMBERS_COUNT_OF_GRID_BY_X]);
                if (i == NUMBERS_COUNT_OF_GRID_BY_T) {
                    writer.print("]");
                } else {
                    writer.print("],");
                }
                t += stepT;
            }
            writer.print("]]");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeMainFuncForPython() {
        try (PrintWriter writer = new PrintWriter("mainFunc.txt")) {

            writer.print("[[" + (int) leftBound + ", " + (int) rightBound
                    + ", " + (NUMBERS_COUNT_OF_GRID_BY_X + 1) + "],");
            writer.print("[" + (int) leftBound + ", " + (int) rightBound
                    + ", " + (NUMBERS_COUNT_OF_GRID_BY_T + 1) + "],");

            double x;
            double t = leftBound;

            writer.print("[");
            for (int i = 0; i <= NUMBERS_COUNT_OF_GRID_BY_T; i++) {
                x = leftBound;
                writer.print("[");
                for (int j = 0; j < NUMBERS_COUNT_OF_GRID_BY_X; j++) {
                    writer.print(u(x, t) + ",");
                    x += stepX;
                }
                writer.print(u(x, t));
                if (i == NUMBERS_COUNT_OF_GRID_BY_T) {
                    writer.print("]");
                } else {
                    writer.print("],");
                }
                t += stepT;
            }
            writer.print("]]");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static double maxError(double[][] u) {

        double x = leftBound;
        double t = leftBound;
        double max = Math.abs(u[0][0] - u(x, t));
        for (int i = 0; i <= NUMBERS_COUNT_OF_GRID_BY_T; i++) {
            x = leftBound;
            for (int j = 0; j <= NUMBERS_COUNT_OF_GRID_BY_X; j++) {
                double error = Math.abs(u(x, t) - u[i][j]);
                if (error > max) {
                    max = error;
                }
                x += stepX;
            }
            t += stepT;
        }
        return max;
    }

    public static void solveConvectionEquation() throws FileNotFoundException {
        double[][] u = new double[NUMBERS_COUNT_OF_GRID_BY_T + 1]
                [NUMBERS_COUNT_OF_GRID_BY_X + 1];

        //The first row of the matrix is filled by the initial data
        double x = leftBound;
        for (int i = 0; i <= NUMBERS_COUNT_OF_GRID_BY_X; i++) {
            u[0][i] = fi0(x);
            x += stepX;
        }

        //The first column are filled with source data
        double t = leftBound;
        for (int j = 0; j <= NUMBERS_COUNT_OF_GRID_BY_T; j++) {
            u[j][0] = u0(t);
            t += stepT;
        }

        //write in the txt for display the result
        writeMainFuncForPython();
        writeResultForPython(u);
        System.out.println("Max error = " + maxError(u));
    }
}