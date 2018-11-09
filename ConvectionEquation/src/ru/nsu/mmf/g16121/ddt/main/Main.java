package ru.nsu.mmf.g16121.ddt.main;

import java.io.IOException;

import static ru.nsu.mmf.g16121.ddt.math.ConvectionEquation.solveConvectionEquation;

public class Main {
    public static final double leftBound = 0;
    public static final double rightBound = 1;

    public static final int NUMBERS_COUNT_OF_GRID_BY_X = 30;
    public static final int NUMBERS_COUNT_OF_GRID_BY_T = 60;

    public static final double eps = 0.1E-6;

    public static double u(double x, double t) {
        return -Math.pow(t - 0.3, 3) + Math.cos(Math.PI * x) + 2.0 * Math.PI * x;
    }

    public static double C(double x, double t) {
        return 3.0 * Math.pow(t - 0.3, 3) /
                (2.0 * Math.PI - Math.PI * Math.sin(Math.PI * x));
    }

    public static double fi0(double x) {
        return Math.cos(Math.PI * x) + 0.027 + 2.0 * Math.PI * x;
    }

    public static double u0(double t) {
        return t;
    }

    public static void main(String[] args) throws IOException {
        solveConvectionEquation();

        Runtime.getRuntime().exec("python3 vizualization.py");
        Runtime.getRuntime().exec("python3 vizualization2.py");
    }
}
