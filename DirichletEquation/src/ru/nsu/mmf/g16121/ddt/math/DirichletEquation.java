package ru.nsu.mmf.g16121.ddt.math;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static ru.nsu.mmf.g16121.ddt.main.Main.*;

public class DerihleEquation {
    private double[][] u = new double[NUMBERS_COUNT_OF_GRID_BY_Y + 1]
            [NUMBERS_COUNT_OF_GRID_BY_X + 1];

    private static final double stepX = (rightBound - leftBound) /
            NUMBERS_COUNT_OF_GRID_BY_Y;
    private static final double stepY = (rightBound - leftBound) /
            NUMBERS_COUNT_OF_GRID_BY_Y;

    private void writeForPython(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {

            writer.print("[[" + (int) leftBound + ", " + (int) rightBound
                    + ", " + (NUMBERS_COUNT_OF_GRID_BY_X + 1) + "],");
            writer.print("[" + (int) leftBound + ", " + (int) rightBound
                    + ", " + (NUMBERS_COUNT_OF_GRID_BY_Y + 1) + "],");

            double x;
            double y = leftBound;

            writer.print("[");
            for (int i = 0; i <= NUMBERS_COUNT_OF_GRID_BY_Y; i++) {
                x = leftBound;
                writer.print("[");
                for (int j = 0; j < NUMBERS_COUNT_OF_GRID_BY_X; j++) {
                    writer.print(u[i][j] + ",");
                    x += stepX;
                }
                writer.print(u[i][NUMBERS_COUNT_OF_GRID_BY_X]);
                if (i == NUMBERS_COUNT_OF_GRID_BY_Y) {
                    writer.print("]");
                } else {
                    writer.print("],");
                }
                y += stepY;
            }
            writer.print("]]");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printMatrix(double[][] u) {
        int N = u.length;
        for (double[] doubles : u) {
            for (int j = 0; j < N; j++) {
                System.out.print(doubles[j] + "\t");
            }
            System.out.println();
        }
    }

    public void printDecisionMatrix() {
        printMatrix(u);
    }

    public void solveDerihleEquation() {

        double x = leftBound + stepX;
        double y = leftBound + stepY;

        for (int j = 1; j < NUMBERS_COUNT_OF_GRID_BY_Y; j++) {
            for (int i = 1; i < NUMBERS_COUNT_OF_GRID_BY_X; i++) {

            }
        }

        writeForPython("result.txt");

    }
}
