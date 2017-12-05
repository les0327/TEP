package lab8;

import java.util.Random;

public class Console {
    static double[] b = {16, 29, 49, 88};
    static int n = 8;
    static int k = 4;
    static int mMin = 3;
    static int m;
    static int mOpt;
    static int mDelta = 2;
    static double DY = 0.1;
    static double[] dy = {0.1, 0.05, 0.02, 0.01, 0.001};
    static double[][] B = new double[dy.length][k];
    static double[][] A = new double[dy.length][k];
    static int[][] onesX = {
            {+1, -1, -1, -1},
            {+1, -1, -1, +1},
            {+1, -1, +1, -1},
            {+1, -1, +1, +1},
            {+1, +1, -1, -1},
            {+1, +1, -1, +1},
            {+1, +1, +1, -1},
            {+1, +1, +1, +1}
    };

    public static void main(String[] args) {
        m = mMin;
        double[] bestY = new double[n];
        for (int i = 0; i < bestY.length; i++) {
            bestY[i] = function(onesX[i][1], onesX[i][2], onesX[i][3]);
        }
        boolean cond = false;
        while (!cond) {
            double[][] badY = createY(bestY, DY);
            double[] averY = averageY(badY);

            double[] disper = createDispersion(badY, averY);
            if (Kohren(disper)) {
                cond = true;
            } else {
                m += mDelta;
            }
        }
        mOpt = m;
        for (int i = 0; i < dy.length; i++) {
            B[i] = coef(dy[i]);
            A[i] = fault(B[i]);
        }
        System.out.printf("%7s %7s %7s %7s%n", "10%", "5%", "2%", "1%");
        showArray(B);
        System.out.println();
        showArray(A);
    }

    static double function(int x1, int x2, int x3) {
        return b[0] + b[1] * x1 + b[2] * x2 + b[3] * x3;
    }

    static double[][] createY(double[] y, double delt) {
        Random rand = new Random();
        double[][] Y = new double[n][m];
        for (int i = 0; i < Y.length; i++) {
            for (int j = 0; j < Y[0].length; j++) {
                Y[i][j] = y[i] * (1 + (2 * rand.nextInt(10000) / 10000 - 1) * delt);
            }
        }
        return Y;
    }

    static double[] averageY(double[][] Y) {
        double[] a = new double[n];
        double s;

        for (int i = 0; i < Y.length; i++) {
            s = 0;
            for (int j = 0; j < Y[0].length; j++) {
                s += Y[i][j];
            }
            a[i] = s / Y[0].length;
        }
        return a;
    }

    static double[] createDispersion(double[][] Y, double[]
            y) {
        double[] d = new double[n];
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < Y[0].length; j++)
                d[i] += Math.pow((Y[i][j] - y[i]), 2);
            d[i] /= (m - 1);
        }
        return d;
    }

    static boolean Kohren(double[] disp) {
        int f1 = m - 1; // column
        int f2 = n; // row
        double gt = 0;
        double[][] tableKohrena = {
                {1, 7945},
                {2, 6162},
                {3, 5209},
                {4, 4627},
                {5, 4226},
                {6, 3932},
                {7, 3704},
                {8, 3522},
                {9, 3373},
                {10, 3248}
        };
        for (double[] aTableKohrena : tableKohrena) {
            if (f1 == aTableKohrena[0]) {
                gt = aTableKohrena[1] / 10000;
            }
        }
        double max = disp[0];
        double sum = 0;
        for (double aDisp : disp) {
            sum += aDisp;
            if (aDisp > max) {
                max = aDisp;
            }
        }
        double gp = max / sum;
        if (gp < gt) {
            return true;
        } else {
            return false;
        }
    }

    static void showArray(double[][] Array) {
        for (double[] aArray : Array) {
            for (int j = 0; j < Array[0].length; j++) {
                System.out.printf("| %5.2f |", aArray[j]);
            }
            System.out.println();
        }
    }

    static void showArray(double[] Array) {
        for (double aArray : Array) System.out.print(aArray + " | ");
        System.out.println();

    }

    static double[] coef(double error) {
        double[] b = new double[k];
        double[] bestY = new double[n];
        for (int i = 0; i < bestY.length; i++) {
            bestY[i] = function(onesX[i][1], onesX[i][2], onesX[i][3]);
        }
        double[][] badY = createY(bestY, error);
        double[] averY = averageY(badY);
        double[] disper = createDispersion(badY, averY);
        boolean whichDisp = Kohren(disper);
        double a;
        for (int j = 0; j < b.length; j++) {
            a = 0;
            for (int i = 0; i < averY.length; i++) {
                a += averY[i] * onesX[i][j];
            }
            b[j] = a / n;
        }
        return b;
    }

    static double[] fault(double[] a) {
        double[] f = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            f[i] = Math.abs(a[i] - b[i]) / b[i];
        }
        return f;
    }
}
