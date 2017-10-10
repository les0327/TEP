package lab2;

import java.util.Arrays;

public class Main {

    // 0.99 - 0, 0.98 - 1, 0.95 - 2, 0.90 - 3
    public static void main(String[] args) {
        Model model = new Model(0);
        model.regression();
        double[] x1  = model.x1;
        double[] x2  = model.x2;
        double[] x1n = model.x1n;
        double[] x2n = model.x2n;
        double[] my  = model.my;
        double[] a   = model.a;
        double[] b   = model.b;

        String s1 = String.format("Normal equation of regression: y = %.3f + %.3f*x1 +%.3f*x2",  b[0], b[1], b[2]);
        String s2 = String.format("Natural equation of regression: y = %.3f + %.3f*x1 +%.3f*x2", a[0], a[1], a[2]);

        System.out.printf("%s%n%s%n", s1, s2);

        int l = 9;
        System.out.printf(
                "%3s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s%n", "N", "X1", "X2", "Xn1", "Xn2", "m(y)"
        );

        for (int i = 0; i < 3; i++) {
            System.out.printf("%3d %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f%n", i, x1[i], x2[i], x1n[i], x2n[i], my[i]);
        }

        model.y.forEach(s -> System.out.println(Arrays.toString(s)));


    }
}
