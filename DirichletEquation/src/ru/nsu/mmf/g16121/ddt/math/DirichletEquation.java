package ru.nsu.mmf.g16121.ddt.math;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static ru.nsu.mmf.g16121.ddt.main.Main.*;

public class DirichletEquation {
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

    public void solveDirichletEquation() {
        double x = leftBound + stepX;
        double y = leftBound + stepY;

        //create counter
        int k = 0;

        //create matrix of 2/1/0 (region/border/no region)
        int[][] indicate = new int[NUMBERS_COUNT_OF_GRID_BY_Y + 1][NUMBERS_COUNT_OF_GRID_BY_X];

        //find out how many interior points, fill in matrix indicate
        // and fill the values on the border
        for (int i = 0; i <= NUMBERS_COUNT_OF_GRID_BY_Y; i++) {
            for (int j = 0; j <= NUMBERS_COUNT_OF_GRID_BY_X; j++) {
                if (isRegion(x, y)) {
                    indicate[i][j] = 2;
                    ++k;
                } else if (isBorder(x, y)) {
                    u[i][j] = fi(x, y);
                    indicate[i][j] = 1;
                } else {
                    indicate[i][j] = 0;
                }
            }
        }

        //create matrix for Az=R
        double[][] A = new double[k][k];
        double[] R = new double[k];

        //fill in matrix A and R
        for (int i = 1; i < NUMBERS_COUNT_OF_GRID_BY_Y; i++) {
            for (int j = 1; j < NUMBERS_COUNT_OF_GRID_BY_X; j++) {
                if (indicate[i][j] == 2) {
                    A[k][k] = 4 * (a + b);

                    if (indicate[i - 1][j] == 2) {

                    } else if (indicate[i - 1][j] == 1) {

                    } else {
                        System.out.println("error");
                    }

                    if (indicate[i][j - 1] == 2) {

                    } else if (indicate[i][j - 1] == 1) {

                    } else {
                        System.out.println("error");
                    }

                    if (indicate[i + 1][j] == 2) {


                    } else if (indicate[i + 1][j] == 1) {

                    } else {
                        System.out.println("error");
                    }

                    if (indicate[i][j + 1] == 2) {

                    } else if (indicate[i][j + 1] == 1) {

                    } else {
                        System.out.println("error");
                    }
                    ++k;
                }
                x = x + stepX;
            }

            y = y + stepY;
        }

        //print matrix A
        printMatrix(A);


        writeForPython("result.txt");

    }
}
