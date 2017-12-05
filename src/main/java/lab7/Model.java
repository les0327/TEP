package lab7;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Model {

    public final static int n = 2; //quantity of factors
    public final static int k = n + 1; //quantity ofexperiments
    public final static double[] dx = {10, 11};
    public final static int optimumCriterion = 4;
    int x1 = 440;
    int x2 = 220;
    int ro = 1;
    double p = 1 / (n * Math.sqrt(2)) * (n - 1 + Math.sqrt(n + 1));
    double q = 1 / (n * Math.sqrt(2)) * (Math.sqrt(n + 1) - 1);
    double[][] x = new double[n + 1][2];
    double[] y = new double[n + 1];

    public double[][] findOptimum() {
        double[] x0 = {Math.random() * 90 + 10,
                Math.random() * 90 + 10};
        double[][] xBound = new double[n][2];
        for (byte i = 0; i < n; i++) {
            xBound[i][0] = x0[i] - dx[i];
            xBound[i][1] = x0[i] + dx[i];
        }
        double[][] initialPlan = {
                {x0[0], x0[1]},
                {x0[0] + p * dx[0] * ro, x0[1] + q * dx[1] * ro},
                {x0[0] + q * dx[0] * ro, x0[1] + p * dx[1] * ro},
        };
        for (byte i = 0; i < k; i++) {
            System.out.println(Arrays.toString(initialPlan[i]));
        }
        System.out.println();
        double[] y = new double[k];
        double[][] x = new double[k][n];
        for (byte i = 0; i < k; i++) {
            System.arraycopy(initialPlan[i], 0, x[i], 0, n);
        }
        double[] ctr = {0, 0, 0};
        double yMin, yNewMin;
        byte iMin, iNewMin;
        double[] xNew;
        Set<Byte> excluded = new HashSet<>();
        boolean optimumReached = false;
        int iOptimum = -1;
        while (iOptimum == -1) {
            for (byte i = 0; i < k; i++) {
                y[i] = getResponseFunction(x[i][0], x[i][1]);
            }
            excluded.clear();
            do {
                yMin = Double.MAX_VALUE;
                iMin = -1;
                xNew = new double[]{0, 0};
                for (byte i = 0; i < k; i++) {
                    if (y[i] < yMin && !excluded.contains(i)) {
                        yMin = y[i];
                        iMin = i;
                    }
                }
                for (byte i = 0; i < k; i++) {

                    if (i != iMin) {
                        xNew[0] += x[i][0];
                        xNew[1] += x[i][1];
                    }
                }
                xNew[0] -= x[iMin][0];
                xNew[1] -= x[iMin][1];
                yNewMin = getResponseFunction(xNew[0],
                        xNew[1]);
                iNewMin = -1;
                for (byte i = 0; i < k; i++) {
                    if (i != iMin && y[i] < yNewMin) {
                        iNewMin = i;
                    }
                }
                if (iNewMin == -1) {
                    excluded.add(iMin);
                    continue;
                }
                for (byte i = 0; i < n; i++) {
                    if ((xNew[i] < xBound[i][0] || xNew[i] > xBound[i][1])) {
                        excluded.add(iMin);
                        break;
                    }
                }
            }
            while (excluded.contains(iMin));
            x[iMin][0] = xNew[0];
            x[iMin][1] = xNew[1];
            for (byte i = 0; i < k; i++) {
                if (i == iMin) {
                    ctr[i] = 0;
                } else {
                    ctr[i]++;
                    if (ctr[i] >= optimumCriterion) {
                        iOptimum = i;
                        break;
                    }
                }
            }
        }
        return new double[][]{
                initialPlan[0],
                initialPlan[1],
                initialPlan[2],
                x[0],
                x[1],
                x[2],
                xBound[0],
                xBound[1],
                {iOptimum,
                        getResponseFunction(x[iOptimum][0], x[iOptimum][1])}
        };
    }

    private double getResponseFunction(double x0, double x1) {
        return x1 - x2;
    }

    public String getFunctionToString() {
        return "y = x1 - x2";
    }
}
