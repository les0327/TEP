package lab1;

import lombok.Getter;
import lombok.Setter;

class Model {

    @Getter @Setter double     leftLimit;
    @Getter @Setter double     rightLimit;
    @Getter @Setter double[]   a;
    @Getter @Setter double[][] x;
    @Getter @Setter double[]   y;
    @Getter @Setter double[]   x0;
    @Getter @Setter double[]   dx;
    @Getter @Setter double[][] nx;
    @Getter @Setter double[]   ny;
    @Getter @Setter int        optimum;


    Model(double leftLimit, double rightLimit, double... a) {
        this.leftLimit  = leftLimit;
        this.rightLimit = rightLimit;
        this.a          = a;

        this.simulate();
    }

    private void simulate() {

        x  = new double[8][3];
        y  = new double[8];
        ny = new double[8];
        nx = new double[8][3];

        double range = (rightLimit - leftLimit);

        // fill matrix with random factors between leftLimit and rightLimit
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                x[i][j] = Math.random() * range + leftLimit;
            }
        }

        // calculate y
        for (int i = 0; i < y.length; i++) {
            y[i] = a[0] + x[i][0]*a[1] + x[i][1]*a[2] + x[i][2]*a[3];
        }

        x0 = new double[x[0].length];
        dx = new double[x[0].length];

        for (int i = 0; i < x0.length; i++) {
            double min = this.getMin(i);
            double max = this.getMax(i);

            x0[i] = (max + min) / 2;
            dx[i] = (max - min) / 2;
        }



        for (int i = 0; i < nx.length; i++) {
            for (int j = 0; j < nx[i].length; j++) {
                nx[i][j] = (x[i][j] - x0[j]) / dx[j];
            }
        }

        // calculate ny
        for (int i = 0; i < ny.length; i++) {
            ny[i] = a[0] + nx[i][0]*a[1] + nx[i][1]*a[2] + nx[i][2]*a[3];
        }

        optimum = 0;

        for (int i = 1; i < y.length; i++) {
            if (y[i] < y[optimum]) {
                optimum = i;
            }
        }

    }

    private double getMin(int column) {
        double min = x[0][column];

        for (int i = 1; i < x.length; i++) {
            if (x[i][column] < min) {
                min = x[i][column];
            }
        }

        return min;
    }


    private double getMax(int column) {
        double max = x[0][column];

        for (int i = 1; i < x.length; i++) {
            if (x[i][column] > max) {
                max = x[i][column];
            }
        }

        return max;
    }
}
