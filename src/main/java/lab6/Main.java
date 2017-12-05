package lab6;

public class Main {

    public static void main(String[] args) {
        Model model = new Model();

        double[] x1  = model.x[0];
        double[] x2  = model.x[1];
        double[] x3  = model.x[2];

        double[] x1x2 = new double[x1.length];
        double[] x1x3 = new double[x1.length];
        double[] x2x3 = new double[x1.length];
        for (int i = 0 ; i < x1.length; i++) {
            x1x2[i] = x1[i] * x2[i];
            x1x3[i] = x1[i] * x3[i];
            x2x3[i] = x2[i] * x3[i];
        }

        double[] x1x2x3 = new double[x1.length];
        for (int i = 0; i < x1.length; i++)
            x1x2x3[i] = x1[i] * x2[i] * x3[i];

        double[] x1x1 = new double[x1.length];
        double[] x2x2 = new double[x1.length];
        double[] x3x3 = new double[x1.length];
        for (int i = 0; i < x1.length; i++) {
            x1x1[i] = x1[i] * x1[i];
            x2x2[i] = x2[i] * x2[i];
            x3x3[i] = x3[i] * x3[i];
        }

        double[] my         = model.my;
        double[] b          = model.b;
        double[] dispersion = model.dispersion;


        String s1 = String.format("Natural equation of regression:" +
                        " y = %.3f + %.3f*x1 +%.3f*x2 +%.3f*x3 + %.3fx1x2 + %.3fx1x3 + %.3fx2x3 + %.3fx1x2x3 + %.3f(x1^2) + %.3f(x2^2) + %.3f(x3^2)",
                b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7], b[8], b[9], b[10]);
        String s2 = "Equation of regression is " + ((model.adequate) ? "adequate" : "inadequate");
        String s3 = String.format("f1 = %d, f2 = %d, f3 = %d, f4 = %d, Fp = %f", model.f1, model.f2, model.f3, model.f4, model.Fp);

        System.out.printf("%s%n%s%n%s%n", s1, s2, s3);

        int l = 9;
        System.out.printf(
                "%3s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s%n",
                "N", "X1", "X2", "X3", "X1X2", "X1X3", "X2X3", "X1X2X3", "X1^2", "X2^2", "X3^2", "m(y)", "D"
        );

        for (int i = 0; i < 8; i++) {
            System.out.printf("%3d %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f %"+l+".2f%n",
                    i, x1[i], x2[i], x3[i], x1x2[i], x1x3[i], x2x3[i], x1x2x3[i], x1x1[i], x2x2[i], x3x3[i],  my[i], dispersion[i]);
        }
    }
}
