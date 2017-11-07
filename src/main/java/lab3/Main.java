package lab3;

public class Main {

    public static void main(String[] args) {
        Model model = new Model();

        int[] x1  = model.x1;
        int[] x2  = model.x2;
        int[] x3  = model.x3;
        int[] x1n = model.x1n;
        int[] x2n = model.x2n;
        int[] x3n = model.x3n;
        double[] my  = model.my;
        double[] a   = model.a;
        double[] b   = model.b;
        double[] dispersion = model.dispersion;


        String s1 = String.format("Normal equation of regression: y = %.3f + %.3f*x1 +%.3f*x2 +%.3f*x3",  b[0], b[1], b[2], b[3]);
        String s2 = String.format("Natural equation of regression: y = %.3f + %.3f*x1 +%.3f*x2 +%.3f*x3", a[0], a[1], a[2], a[3]);
        String s3 = "Equation of regression is " + ((model.adecvat) ? "adequate" : "inadequate");
        String s4 = String.format("f1 = %d, f2 = %d, f3 = %d, f4 = %d, Fp = %f", model.f1, model.f2, model.f3, model.f4, model.Fp);

        System.out.printf("%s%n%s%n%s%n%s%n", s1, s2, s3, s4);

        int l = 9;
        System.out.printf(
                "%3s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s%n", "N", "X1", "X2", "X3", "Xn1", "Xn2", "Xn3", "m(y)", "D"
        );

        for (int i = 0; i < 4; i++) {
            System.out.printf("%3d %"+l+"d %"+l+"d %"+l+"d %"+l+"d %"+l+"d %"+l+"d %"+l+".2f %"+l+".2f%n",
                    i, x1[i], x2[i], x3[i], x1n[i], x2n[i], x3n[i], my[i], dispersion[i]);
        }
    }
}
