package lab2;

import lombok.Getter;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.LinkedList;
import java.util.Random;

class Model {

    private final int minX1 = -25;
    private final int maxX1 = -5;
    private final int minX2 = -30;
    private final int maxX2 = 45;

    @Getter double[] x1= {minX1, minX1, maxX1};
    @Getter double[] x2= {minX2, maxX2, minX2};
    @Getter double[] x1n={-1, -1,  1};
    @Getter double[] x2n={-1,  1, -1};
    @Getter double[] my;
    @Getter double[] a;
    @Getter double[] b;
    @Getter LinkedList<double[]> y;

    Model(int probability) {
        byte[] romanQuantity = new byte[] {2, 6, 8, 10, 12, 15, 20};
        double[][] roman = new double[][]{
                {1.73, 2.16, 2.43, 2.62, 2.75, 2.9, 3.08},
                {1.72, 2.13, 2.37, 2.54, 2.66, 2.8, 2.96},
                {1.71, 2.1, 2.27, 2.41, 2.52, 2.64, 2.78},
                {1.69, 2.0, 2.17, 2.29, 2.39, 2.49, 2.62}
        };

        my = new double[3];
        a  = new double[3];
        b = new double[3];
        y = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            y.add(this.genArray());
        }

        boolean romanFlag = false;
        for(int i = 6; (i <= 20) && (!romanFlag); i++){
            my[0] = 0;
            my[1] = 0;
            my[2] = 0;

            y.add(this.genArray());

            for(int j = 0; j < i; j++){
                double[] buf = y.get(j);
                my[0] += buf[0];
                my[1] += buf[1];
                my[2] += buf[2];
            }
            my[0] /= i;
            my[1] /= i;
            my[2] /= i;

            double[] dispersion = new double[3];
            for(int j = 0;j < i;j++){
                double[] buf = y.get(j);
                dispersion[0] +=(buf[0] - my[0]) * (buf[0] - my[0]);
                dispersion[1] +=(buf[1] - my[1]) * (buf[1] - my[1]);
                dispersion[2] +=(buf[2] - my[2]) * (buf[2] - my[2]);

            }

            dispersion[0] /= i;
            dispersion[1] /= i;
            dispersion[2] /= i;

            double deviation = 2 * Math.sqrt((i - 1) / i / (i - 4));

            double f01 = dispersion[0]/dispersion[1];
            double f02 = dispersion[0]/dispersion[2];
            double f12 = dispersion[1]/dispersion[2];

            if(dispersion[1] > dispersion[0])
                f01 = 1/ f01;
            if(dispersion[2] > dispersion[0])
                f02 = 1/ f02;
            if(dispersion[2] > dispersion[1])
                f12 = 1/ f12;

            double coef = (i-2.0)/i;

            f01 = Math.abs(f01 * coef - 1)/deviation;
            f02 = Math.abs(f02 * coef - 1)/deviation;
            f12 = Math.abs(f12 * coef - 1)/deviation;

            int num = 0;
            for(int j = 0; (j <= 6) & (i > romanQuantity[num]); j++){
                num = j;
            }
            double romanCrit = roman[probability][num];

            if((f01 < romanCrit) && (f02 < romanCrit) && (f12 < romanCrit)) {
                romanFlag = true;
            }
        }
    }


    private double[] genArray(){
        double[] result = new double[3];
        Random rand = new Random();
        for (int i = 0; i <= 2; i++){
            result[i]= 100 + rand.nextDouble() * 100;
        }
        return result;
    }


    void regression() {
        double mx1 = (x1n[0]+x1n[1]+x1n[2])/3.0;
        double mx2 = (x2n[0]+x2n[1]+x2n[2])/3.0;
        double a1  = (x1n[0]*x1n[0]+x1n[1]*x1n[1]+x1n[2]*x1n[2])/3.0;
        double a2  = (x1n[0]*x2n[0]+x1n[1]*x2n[1]+x1n[2]*x2n[2])/3.0;
        double a3  = (x2n[0]*x2n[0]+x2n[1]*x2n[1]+x2n[2]*x2n[2])/3.0;
        double a11 = (x1n[0]*my[0]+x1n[1]*my[1]+x1n[2]*my[2])/3.0;
        double a22 = (x2n[0]*my[0]+x2n[1]*my[1]+x2n[2]*my[2])/3.0;
        double my0 = (my[0]+my[1]+my[2])/3.0;

        double[][] matrix = {{1, mx1, mx2},{mx1, a1, a2},{mx2, a2, a3}};
        RealMatrix matrixTemp0 = MatrixUtils.createRealMatrix(matrix);
        double det = new LUDecomposition(matrixTemp0).getDeterminant();
        double[] vector = {my0, a11, a22};
        for (int i=0; i < 3; i++){

            double[][] temp = new double[3][3];
            for(int k = 0; k < 3; k++){
                System.arraycopy(matrix[k], 0, temp[k], 0, 3);
            }
            temp[0][i] = vector[0];
            temp[1][i] = vector[1];
            temp[2][i] = vector[2];
            RealMatrix matrixTemp = MatrixUtils.createRealMatrix(temp);
            b[i] = new LUDecomposition(matrixTemp).getDeterminant()/det;
        }

        double dx1 = Math.abs(minX1 - maxX1) / 2;
        double dx2 = Math.abs(minX2 - maxX2) / 2;
        double x10 = (minX1 + maxX1) / 2;
        double x20 = (minX2 + maxX2) / 2;
        a[0] = b[0] - b[1] * x10/dx1 - b[2] * x20/dx2;
        a[1] = b[1] / dx1;
        a[2] = b[2] / dx2;
    }

}
