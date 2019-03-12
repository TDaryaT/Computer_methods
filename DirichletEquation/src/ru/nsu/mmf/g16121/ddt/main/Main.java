package ru.nsu.mmf.g16121.ddt.main;

import ru.nsu.mmf.g16121.ddt.math.DirichletEquation;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static final double leftBound = 0;
    public static final double rightBound = 1;

    public static final int NUMBERS_COUNT_OF_GRID_BY_X = 4;
    public static final int NUMBERS_COUNT_OF_GRID_BY_Y = 4;

    public static final double a = 1.;
    public static final double b = 1.;

    public static final double eps = 0.1E-6;

    public static double fi(double x, double y) {
        return Math.sin(Math.PI * x) * Math.sin(Math.PI * y);
    }

    public static double f(double x, double y) {
        return 2 * Math.pow(Math.PI, 2) * Math.sin(Math.PI * x) * Math.sin(Math.PI * y);
    }

    public static boolean isBorder(double x, double y) {
        return (x >= 0) && (y >= 0) && (x <= 1) && (y <= 1) &&
                (((-x + 0.5) == y) || ((x + 0.5) == y) ||
                        ((-x + 1.5) == y) || ((x == 1) && (y <= 0.5)) ||
                        ((y == 0) && (x >= 0.5)));
    }

    public static boolean isRegion(double x, double y) {
        return (((-x + 0.5) < y) && ((x + 0.5) > y) &&
                ((-x + 1.5) > y) && (x < 1) && (y > 0));
    }

    public static void main(String[] args) throws IOException {
        int i = 1;
        double x, y;

        //validation of the region
//        Scanner scanner = new Scanner(System.in);
//
//        while (i != 0) {
//            System.out.println("set coordinate of x: ");
//            x = scanner.nextDouble();
//            System.out.println("set coordinate of y: ");
//            y = scanner.nextDouble();
//
//            if (isBorder(x, y)) {
//                System.out.println("is Border");
//            }
//            if (isRegion(x, y)) {
//                System.out.println("is Region");
//            }
//            System.out.println("continue?(1/0)");
//            i = scanner.nextInt();
//        }

        DirichletEquation DE1 = new DirichletEquation();
        DE1.solveDirichletEquation();

        Runtime.getRuntime().exec("python3 vizualization.py");
    }
}
