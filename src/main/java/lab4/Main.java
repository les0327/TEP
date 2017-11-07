package lab4;

public class Main {

    public static void main(String[] args) {
        Model model = new Model();
        double[] x1  = model.x[0];
        double[] x2  = model.x[1];
        double[] x3  = model.x[2];
        double[] x1n = model.xn[0];
        double[] x2n = model.xn[1];
        double[] x3n = model.xn[2];
        double[] x1x2n = model.xn[3];
        double[] x1x3n = model.xn[4];
        double[] x2x3n = model.xn[5];
        double[] x1x2x3n = model.xn[6];

        double[] my         = model.my;
        double[] a          = model.a;
        double[] b          = model.b;
        double[] dispersion = model.dispersion;


        String s1 = String.format("Normal equation of regression: y = %.3f + %.3f*x1 +%.3f*x2 +%.3f*x3 + %.3fx1x2 + %.3fx1x3 %.3fx1x3 %.3fx1x2",
                b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7]);
        String s2 = String.format("Natural equation of regression: y = %.3f + %.3f*x1 +%.3f*x2 +%.3f*x3 + %.3fx1x2 + %.3fx1x3 %.3fx1x3 %.3fx1x2",
                a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7]);
        String s3 = "Equation of regression is " + ((model.adecvat) ? "adequate" : "inadequate");
        String s4 = String.format("f1 = %d, f2 = %d, f3 = %d, f4 = %d, Fp = %f", model.f1, model.f2, model.f3, model.f4, model.Fp);

        System.out.printf("%s%n%s%n%s%n%s%n", s1, s2, s3, s4);

        int l = 9;
        System.out.printf(
                "%3s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s%n",
                "N", "X1", "X2", "X3", "Xn1", "Xn2", "Xn3", "X1X2", "X1X3", "X2X3", "X1X2X3", "m(y)", "D"
        );

        for (int i = 0; i < 8; i++) {
            System.out.printf("%3d %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f%n",
                    i, x1[i], x2[i], x3[i], x1n[i], x2n[i], x3n[i], x1x2n[i], x1x3n[i], x2x3n[i], x1x2x3n[i], my[i], dispersion[i]);
        }
    }
}
