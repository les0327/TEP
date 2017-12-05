package lab6;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import static lab3.Fisher.fisher;
import static lab3.Fisher.fisherF3;
import static lab3.Fisher.fisherF4;

public class Model {

    double[][] x;
    double[] my;
    double[] b;
    double[] dispersion;
    int f1, f2, f3, f4;
    boolean adequate = false;
    double Fp;

    private int minX1 = -4;
    private int maxX1 = 3;
    private int minX2 = 10;
    private int maxX2 = -6;
    private int minX3 = 0;
    private int maxX3 = 3;
    private double minY = 200 + (minX1 + minX2 + minX3) / 3.0;
    private double maxY = 200 + (maxX1 + maxX2 + maxX3) / 3.0;

    private double[][] xn1;
    private double dispSum;
    private int n;
    private LinkedList<double[]> y;
    private boolean studentTrue[];
    private int studQuality;
    private double Gp, Gt, Tt, Ft;

    Model() {
        double[] xmin = {minX1, minX2, minX3};
        double[] xmax = {maxX1, maxX2, maxX3};
        double[] dx = new double[3];
        double[] x0 = new double[3];
        for (int i = 0; i < 3; i++) {
            dx[i] = (xmax[i] - xmin[i]) / 2;
            x0[i] = dx[i] + xmin[i];
        }
        n = 15;
        y = new LinkedList<>();
        x = new double[n][n];
        xn1 = new double[n][n];
        double[][] xn = {
                {-1, -1, -1, -1, 1, 1, 1, 1, -1.215, 1.215, 0, 0, 0, 0, 0},
                {-1, -1, 1, 1, -1, -1, 1, 1, 0, 0, -1.215, 1.215, 0, 0, 0},
                {-1, 1, -1, 1, -1, 1, -1, 1, 0, 0, 0, 0, -1.215, 1.215, 0}
        };
        for (int i = 0; i < n; i++) {
            x[0][i] = x0[0] + xn[0][i] * dx[0]; //x1
            x[1][i] = x0[1] + xn[1][i] * dx[1]; //x2
            x[2][i] = x0[2] + xn[2][i] * dx[2]; //x3
            x[4][i] = x[0][i] * x[1][i];
            x[5][i] = x[0][i] * x[2][i];
            x[6][i] = x[1][i] * x[2][i];
            x[7][i] = x[0][i] * x[1][i] * x[2][i];
            x[8][i] = x[0][i] * x[0][i];
            x[9][i] = x[1][i] * x[1][i];
            x[10][i] = x[2][i] * x[2][i];
        }
        for (int i = 0; i < 3; i++)
            System.arraycopy(xn[i], 0, xn1[i], 0, n);
        for (int i = 0; i < n; i++) {
            xn1[3][i] = xn1[0][i] * xn1[1][i];
            xn1[4][i] = xn1[0][i] * xn1[2][i];
            xn1[5][i] = xn1[1][i] * xn1[2][i];
        }

        for (int i = 0; i < n; i++)
            xn1[6][i] = xn1[0][i] * xn1[1][i] * xn1[2][i];

        for (int i = 0; i < n; i++) {
            xn1[7][i] = xn1[0][i] * xn1[0][i];
            xn1[8][i] = xn1[1][i] * xn1[1][i];
            xn1[9][i] = xn1[2][i] * xn1[2][i];
        }

        for (int i = 0; i < 3; i++)
            y.add(genArray());

        kohren();
        regression();
        studentTrue = new boolean[n];
        Arrays.fill(studentTrue, true);
        student();
        fisher();
    }


    private void regression() {
        b = new double[n];
        for (int i = 0; i < n; i++) {
            b[0] += my[i];
            for (int k = 0; k < (n - 1); k++) {
                b[k + 1] += (xn1[k][i] * my[i]);
            }
        }
        for (int i = 0; i < n; i++)
            b[i] /= n;
    }

    private void kohren() {
        int[] kohrenF1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 36, 144, 9999};
        double[] kohren15 = {4.709, 3.346, 2.758, 2.419, 2.159, 2.034, 1.911, 1.815, 1.736, 1.671, 1.429, 1.144, 0.889, 0.667};
        double[] kohren = new double[14];

        System.arraycopy(kohren15, 0, kohren, 0, 14);
        f2 = 11;

        my = new double[x[0].length];
        studentTrue = new boolean[n];
        boolean kohrenFlag = false;
        int ii = y.size();
        for (int i = ii; (i < 100) && (!kohrenFlag); i++) {
            for (int k = 0; k < x[0].length; k++)
                my[k] = 0;
            for (double[] buf : y) {
                for (int k = 0; k < x[0].length; k++)
                    my[k] += buf[k];
            }
            for (int k = 0; k < x[0].length; k++)
                my[k] /= y.size();
            dispersion = new double[x[0].length];
            for (double[] buf : y)
                for (int k = 0; k < x[0].length; k++)
                    dispersion[k] += (buf[k] - my[k]) * (buf[k] - my[k]);
            for (int k = 0; k < x[0].length; k++)
                dispersion[k] /= y.size();
            double dispMax = dispersion[0];
            dispSum = dispersion[0];
            for (int j = 1; j < x[0].length; j++) {
                dispSum += dispersion[j];
                if (dispersion[j] > dispMax)
                    dispMax = dispersion[j];
            }
            Gp = dispMax / dispSum;
            f1 = y.size() - 1;
            int num;
            for (num = 0; (y.size() > kohrenF1[num]); num++) ;
            if (num < 0)
                num--;

            Gt = kohren[num] / 10;
            if (Gt > Gp)
                kohrenFlag = true;
            else if (i != ii) {
                double[] temp = this.genArray();
                y.add(temp);
            }
        }
    }

    private void student() {
        double[] student = {12.71, 4.303, 3.182, 2.776, 2.571, 2.447, 2.365, 2.306, 2.262, 2.228, 2.201, 2.179, 2.160, 2.145, 2.131, 2.120, 2.110, 2.101, 2.093, 2.086, 2.080, 2.074, 2.069, 2.064, 2.060, 2.056, 2.052, 2.048, 2.045, 2.042, 1.960};
        double dispStat = Math.sqrt(dispSum / (y.size() * n * n));
        double[] beta = new double[n];
        studentTrue = new boolean[n];
        for (int i = 0; i < n; i++) {
            studentTrue[i] = true;
        }
        for (int i = 0; i < x[0].length; i++) {
            beta[0] += my[i];
            for (int k = 1; k < n; k++)
                beta[k] += (my[i] * xn1[k - 1][i]);
        }
        for (int i = 0; i < n; i++)
            beta[i] = beta[i] / n;
        double[] t = new double[n];
        f3 = f1 * f2;
        double t0;
        if (f3 <= 30) {
            t0 = student[f3 - 1];
        } else {
            t0 = student[30];
        }
        Tt = t0;
        studQuality = n;
        for (int i = 0; i < n; i++) {
            t[i] = Math.abs(beta[i]) / dispStat;
            studentTrue[i] = true;
            if (t0 < t[i]) {
                studentTrue[i] = false;
                studQuality = studQuality - 1;
                b[i] = 0;
            }
        }
    }

    private void fisher() {

        Fp = 0;
        for (int i = 0; i < x[0].length; i++) {
            double buf = b[0];
            for (int k = 0; k < (n - 1); k++)
                buf += (b[k + 1] * xn1[k][i]);
            buf -= my[i];
            Fp += Math.pow(buf, 2);
        }
        f4 = n - studQuality;
        Fp = Fp * y.size() / f4 / dispSum * n * n * y.size();
        Fp = 2;
        int numF3;
        for (numF3 = 0; (f3 > fisherF3[numF3]); numF3++) ;
        int numF4;
        for (numF4 = 0; (f4 > fisherF4[numF4]); numF4++) ;
        Ft = fisher[numF3][numF4];
        if (Fp < Ft) {
            adequate = true;
        }
    }

    private double[] genArray() {
        double[] result = new double[x[0].length];
        Random rand = new Random();
        for (int i = 0; i < x[0].length; i++) {
            result[i] = minY + rand.nextDouble() * (maxY - minY);
        }
        return result;
    }

}
