package lab4;

import java.util.LinkedList;
import java.util.Random;

import static lab3.Fisher.fisher;
import static lab3.Fisher.fisherF3;
import static lab3.Fisher.fisherF4;

public class Model {
    int minX1 = -20;
    int maxX1 = 15;
    int minX2 = 10;
    int maxX2 = 60;
    int minX3 = 15;
    int maxX3 = 35;

    double minY  = 200 + (minX1 + minX2 + minX3) / 3.0;
    double maxY  = 200 + (maxX1 + maxX2 + maxX3) / 3.0;

    double[][] x = {
            {minX1, minX1, maxX1, maxX1, minX1, minX1, maxX1, maxX1},
            {minX2, maxX2, minX2, maxX2, minX2, maxX2, minX2, maxX2},
            {minX3, maxX3, maxX3, minX3, maxX3, minX3, minX3, maxX3}};
    double[][] xn = {
            {-1, -1, 1, 1,-1,- 1,1,1},
            {-1, 1, -1, 1,-1,1,- 1,1},
            {-1, 1, 1, -1,1,- 1,-1,1},
            {1,-1,- 1,1,1,-1,- 1,1},
            {1,1,-1,- 1,-1,- 1,1,1},
            {1,-1,1,- 1,-1,1,- 1,1},
            {-1,- 1,-1,- 1,1,1,1,1}
    };

    double[] my;
    double[] a;
    double[] b;
    double[] dispersion;
    double dispSum;
    int f1;
    int f2;
    int f3;
    int f4;
    int n;
    LinkedList<double[]> y;
    boolean studentTrue[];
    boolean adecvat = false;
    int studQuality;
    double Fp;

    Model() {
        n=8;
        y = new LinkedList();
        for (int i = 0; i <= 3; i++) {
            y.add(genArray());
        }
        kohren();
        regression();
        student();
        fisher();
    }

    private void kohren() {
        int[] kohrenF1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 36, 144,
                9999};
        double[] kohren4 = {
                9.065, 7.679,6.841, 6.287, 5.892,
                5.598, 5.365, 5.175, 5.017, 4.884,
                4.366, 3.720, 3.093, 2.500};
        double[] kohren8 = {
                6.798, 5.157, 4.377, 3.910, 3.595,
                3.362, 3.185, 3.043, 2.926, 2.829,
                2.462, 2.022, 1.616, 1.250};
        double[] kohren = new double[14];
        if(n==4){
            System.arraycopy(kohren4, 0, kohren, 0, 14);
            f2=4;
        } else {
            System.arraycopy(kohren8, 0, kohren, 0, 14);
            f2=8;
        }
        my = new double[n];

        a = new double[n];
        b = new double[n];
        studentTrue = new boolean[n];
        dispersion = new double[n];
        boolean kohrenFlag = false;
        int ii = y.size();
        for (int i = ii; (i <= 100) && (!kohrenFlag); i++) {
            for(int k = 0; k < n; k++)
            my[k]=0;
            if (i != ii) {
                double[] temp = this.genArray();
                y.add(temp);
            }
            for (double[] buf : y) {
                for (int k = 0; k < n; k++) {
                    my[k] += buf[k];
                }
            }
            for(int k=0; k < n; k++)
            my[k]/=y.size();
            dispersion = new double[n];
            for (double[] buf : y) {
                for (int k = 0; k < n; k++)
                    dispersion[k] += (buf[k] - my[k]) * (buf[k] - my[k]);
            }
            for(int k=0; k < n; k++)
            dispersion[k]/=y.size();
            double dispMax = dispersion[0];
            dispSum = dispersion[0];
            for (int j = 1; j < n; j++) {
                dispSum += dispersion[j];
                if (dispersion[j] > dispMax)
                dispMax = dispersion[j];
            }
            double Gp = dispMax / dispSum;
            f1 = y.size() - 1;
            int num;
            for (num = 0; (i > kohrenF1[num]); num++) ;
            if (kohren[num] > Gp)
            kohrenFlag = true;
        }
    }

    private void student() {
        double[] student = {
                12.71, 4.303, 3.182, 2.776, 2.571, 2.447, 2.365,
                2.306, 2.262, 2.228,
                2.201, 2.179, 2.160, 2.145, 2.131, 2.120, 2.110,
                2.101, 2.093, 2.086,
                2.080, 2.074, 2.069, 2.064, 2.060, 2.056, 2.052,
                2.048, 2.045, 2.042, 1.960};
        double dispStat = Math.sqrt(dispSum / (y.size() * n*n));
        double[] beta = new double[n];
        for (int i = 0; i < n; i++) {
            beta[0] += my[i];
            for(int k=0; k < (n-1);k++)
            beta[k+1]+= (my[i] * xn[k][i]);
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
        studQuality=n;
        for (int i = 0; i < n; i++) {
            t[i] = Math.abs(beta[i]) / dispStat;
            studentTrue[i] = true;
            if (t0 > t[i]) {
                studentTrue[i] = false;
                studQuality-- ;
//                a[i] = 0;
//                b[i] = 0;
            }
//            if (!studentTrue[i]) {
//                a[i] = 0;
//                b[i] = 0;
//            }
        }
    }

    private void fisher() {

        Fp = 0;
        for (int i = 0; i < n; i++) {
            double buf = b[0];
            for(int k=0; k < (n-1);k++)
            buf+= (b[k+1] * xn[k][i]);
            buf-=my[i];
            Fp += Math.pow(buf, 2);
        }
        f4 = n-studQuality;

        double z;
        if (n == 4) {
            z=16;
        }
        else {
            z=7.25;
        }
        Fp = Fp * y.size() / f4 / dispSum * z * y.size();
        int numF3;
        for (numF3 = 0; (f3 > fisherF3[numF3]); numF3++) ;
        int numF4;
        for (numF4 = 0; (f4 > fisherF4[numF4]); numF4++) ;
        if (Fp < fisher[numF3][numF4]) {
            adecvat = true;
        }
    }

    private void regression() {
        b = new double[n];
        for (int i = 0; i < n; i++) {
            b[0] += my[i];
            for(int k=0; k < (n-1);k++){
                b[k+1]+=(xn[k][i]*my[i]);
            }
        }
        for (int i = 0; i < n; i++)
        b[i] /= n;

        double[] dx = new double[7];
        double[] x0 = new double[7]; // x1, x2, x3, x12, x23, x13, x123

        dx[0] = Math.abs(minX1 - maxX1) / 2;
        dx[1] = Math.abs(minX2 - maxX2) / 2;
        dx[2] = Math.abs(minX3 - maxX3) / 2;
        dx[3] = Math.abs(minX1*minX2 - maxX1*maxX2) / 2;
        dx[4] = Math.abs(minX1*minX3 - maxX1*maxX3) / 2;
        dx[5] = Math.abs(minX2*minX3 - maxX2*maxX3) / 2;
        dx[6] = Math.abs(minX1*minX2*minX3 - maxX1*maxX2*minX3) / 2;
        x0[0] = (minX1 + maxX1) / 2;
        x0[1] = (minX2 + maxX2) / 2;
        x0[2] = (minX3 + maxX3) / 2;
        x0[3] = (minX1*minX2 + maxX1*maxX2) / 2;
        x0[4] = (minX1*minX3 + maxX1*maxX3) / 2;
        x0[5] = (minX2*minX3 + maxX2*maxX3) / 2;
        x0[6] = (minX1*minX2*minX3 + maxX1*maxX2*maxX3) / 2;


        a = new double[n];
        a[0] = b[0];
        for(int k=0; k < (n-1);k++){
            a[0]  -= (b[k+1]*x0[k]/dx[k]);
            a[k+1] = b[k+1]/dx[k];
        }
    }

    private double[] genArray(){
        double[] result = new double[n];
        Random rand = new Random();
        for (int i=0; i < n; i++){
            result[i]= minY + rand.nextDouble() * (maxY - minY);
        }
        return result;
    }
}
