public class Lab1 {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Введите аргументы!");
            return;
        }

        double[] k = new double[3];
        for (int i = 0; i < Math.min(3, args.length); i++)
            k[i] = Double.parseDouble(args[i]);

        double[] ans = solveQuadratic(k[0], k[1], k[2]);
        if (ans == null) {
            System.out.println("Действительных решений нет!");
        } else if (ans.length == 1) {
            if (Double.isNaN(ans[0]))
                System.out.println("x может быть любым");
            else if (Double.isInfinite(ans[0]))
                System.out.println("Решений нет!");
            else
                System.out.println("x = " + ans[0]);
        } else {
            System.out.println("x1 = " + ans[0]);
            System.out.println("x2 = " + ans[1]);
        }
    }

    private static double[] solveQuadratic(double a, double b, double c) {
        if (a == 0) {
            return new double[] {-c / b};
        }

        double d = b * b - 4 * a * c;
        if (d < 0)
            return null;

        double[] ans = new double[2];
        ans[0] = (-b + Math.sqrt(d)) / (2 * a);
        ans[1] = (-b - Math.sqrt(d)) / (2 * a);
        return ans;
    }
}
