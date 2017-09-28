import java.util.Scanner;

public class Quadratic {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.print("Введите коэффициенты квадратного уравнения (через пробел): ");
            args = new Scanner(System.in).nextLine().split(" ");
        }

        double[] k = new double[3];
        try {
            for (int i = 0; i < Math.min(3, args.length); i++)
                k[i] = Double.parseDouble(args[i]);
        } catch (Exception e) {
            System.out.println("Коэффициенты не являются числами!");
            return;
        }

        System.out.println("Решаем квадратное уравнение: " + printEquation(k));

        double[] ans = solveQuadratic(k[0], k[1], k[2]);
        if (ans.length == 3) {
            System.out.println("x1 = " + printComplex(ans[0], ans[1]));
            System.out.println("x2 = " + printComplex(ans[0], ans[2]));
        } else if (ans.length == 1) {
            System.out.println("Это вырожденное квадратное уравнение");
            if (Double.isNaN(ans[0]))
                System.out.println("Уравнение 0х = 0 имеет множество решений");
            else if (Double.isInfinite(ans[0]))
                System.out.println("Решений нет");
            else
                System.out.println("x = " + ans[0]);
        } else {
            System.out.println("x1 = " + ans[0]);
            System.out.println("x2 = " + ans[1]);
        }
    }

    private static String printEquation(double[] k) {
        String eq = "" + k[0] + " * x^2 ";
        eq += k[1] < 0 ? " - " : " + ";
        eq += k[1] + " * x";
        eq += k[2] < 0 ? " - " : " + ";
        eq += k[2] + " = 0";
        return eq;
    }

    private static String printComplex(double d, double mn) {
        String complex = String.valueOf(d);
        complex += mn < 0 ? " - " : " + ";
        complex += Math.abs(mn);
        complex += " * i";
        return complex;
    }

    private static double[] solveQuadratic(double a, double b, double c) {
        if (a == 0) {
            return new double[]{-c / b};
        }

        double d = b * b - 4 * a * c;
        if (d < 0) {
            double[] ans = new double[3];
            ans[0] = -b / 2 / a;
            double mn = Math.sqrt(Math.abs(d)) / 2 / a;
            ans[1] = -mn;
            ans[2] = mn;
            return ans;
        }

        double[] ans = new double[2];
        ans[0] = (-b + Math.sqrt(d)) / (2 * a);
        ans[1] = (-b - Math.sqrt(d)) / (2 * a);
        return ans;
    }
}
