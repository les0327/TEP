package lab3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import static lab3.Fisher.fisher;
import static lab3.Fisher.fisherF3;
import static lab3.Fisher.fisherF4;

public class Model {

    int minY = 180;
    int maxY = 225;
    int minX1 = -25;
    int maxX1 = -5;
    int minX2 = -30;
    int maxX2 = 45;
    int minX3 = -5;
    int maxX3 = 5;

    int[] x1  = {-25, -25, -5, -5};
    int[] x2  = {-30, 45, -30, 45};
    int[] x3  = {-5, 5, 5, -5};

    int[] x1n = {-1, -1, 1, 1};
    int[] x2n = {-1, 1, -1, 1};
    int[] x3n = {-1, 1, 1, -1};

    double[] my            = new double[4];
    double[] a             = new double[4];
    double[] b             = new double[4];
    double[] dispersion    = new double[4];
    LinkedList<double[]> y = new LinkedList<>();

    double dispSum;
    int f1;
    int f2=4;
    int f3;
    int f4;
    boolean studentTrue[] = new boolean[4];
    boolean adecvat;
    int studQuality=4;
    double Fp;

    Model() {

        int[] kohrenF1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 36, 144, 9999};

        double[] kohren = {0.9065, 0.7679, 0.6841, 0.6287, 0.5892, 0.5598, 0.5365, 0.5175, 0.5017, 0.4884, 0.4366, 0.3720, 0.3093, 0.2500};

        double[] student = {12.71, 4.303, 3.182, 2.776, 2.571, 2.447, 2.365, 2.306, 2.262, 2.228, 2.201, 2.179, 2.160, 2.145, 2.131, 2.120, 2.110, 2.101, 2.093, 2.086, 2.080, 2.074, 2.069, 2.064, 2.060, 2.056, 2.052, 2.048, 2.045, 2.042, 1.960};


        for (int i=0; i < 4; i++)
            y.add(this.genArray());


        boolean kohrenFlag = false;

        for(int i = 3; (i <= 100)&&(!kohrenFlag); i++) {
            Arrays.fill(my, 0);

            if (i!=3) {
                double[] temp = this.genArray();
                y.add(temp);
            }

            for (double[] buf : y) {
                my[0] += buf[0];
                my[1] += buf[1];
                my[2] += buf[2];
                my[3] += buf[3];
            }

            my[0] /= y.size();
            my[1] /= y.size();
            my[2] /= y.size();
            my[3] /= y.size();

            dispersion = new double[4];
            for (double[] buf : y) {
                dispersion[0] += (buf[0] - my[0]) * (buf[0] - my[0]);
                dispersion[1] += (buf[1] - my[1]) * (buf[1] - my[1]);
                dispersion[2] += (buf[2] - my[2]) * (buf[2] - my[2]);
                dispersion[3] += (buf[3] - my[3]) * (buf[3] - my[3]);
            }

            dispersion[0] /= y.size();
            dispersion[1] /= y.size();
            dispersion[2] /= y.size();
            dispersion[3] /= y.size();

            double dispMax = dispersion[0];
            dispSum = dispersion[0];

            for (int j = 1; j <= 3; j++) {
                dispSum += dispersion[j];
                if (dispersion[j] > dispMax)
                    dispMax = dispersion[j];
            }
            double Gp = dispMax / dispSum;
            f1 = y.size() - 1;


            int num;
            for (num=0; (i>kohrenF1[num]);num++);

            if(kohren[num]>Gp)
                kohrenFlag=true;
        }
        this.regression();

        double dispStat = Math.sqrt(dispSum /(y.size()*16));
        double[] beta = new double[4];

        for(int i = 0; i < 4; i++){
            beta[0]+= my[i];
            beta[1]+= (my[i]*x1n[i]);
            beta[2]+= (my[i]*x2n[i]);
            beta[3]+= (my[i]*x3n[i]);
        }

        for(int i = 0; i < 4; i++)
            beta[i] /= 4;

        double[] t = new double[4];

        f3=f1*f2;
        double t0;
        if(f3<=30){
            t0=student[f3-1];
        }
        else {
            t0=student[30];
        }

        for(int i = 0; i < 4; i++) {
            t[i] = Math.abs(beta[i]) / dispStat;
            studentTrue[i] = true;
            if(t0>t[i]) {
                studentTrue[i] = false;
                studQuality--;
//                a[i] = 0;
//                b[i] = 0;
            }
        }


        ///Fisher
        Fp=0;
        for(int i=0; i<=3; i++){
            Fp+=Math.pow((b[0]+b[1]*x1n[i]+b[2]*x2n[i]+b[3]*x3n[i]- my[i]),2);
        }
        f4 = 4 - studQuality;
        Fp = Fp * y.size()/f4/dispSum*16*y.size();


        int numF3;
        for(numF3=0; (f3>fisherF3[numF3]);numF3++);
        int numF4;
        for(numF4=0; (f4>fisherF4[numF4]);numF4++);
        if(Fp < fisher[numF3][numF4]) {
            adecvat = true;
        }


    }

    private void regression() {
        b = new double[4];
        for(int i=0; i<=3; i++){
            b[0]+=my[i];
            b[1]+=(x1n[i]*my[i]);
            b[2]+=(x2n[i]*my[i]);
            b[3]+=(x3n[i]*my[i]);
        }

        for(int i=0; i<=3; i++)
            b[i]/=4;

        double dx1 = Math.abs(minX1 - maxX1) / 2;
        double dx2 = Math.abs(minX2 - maxX2) / 2;
        double dx3 = Math.abs(minX3 - maxX3) / 2;
        double x10 = (minX1 + maxX1) / 2;
        double x20 = (minX2 + maxX2) / 2;
        double x30 = (minX3 + maxX3) / 2;
        a = new double[4];
        a[0] = b[0] - b[1]*x10/dx1-b[2]*x20/dx2-b[3]*x30/dx3;
        a[1]=b[1]/dx1;
        a[2]=b[2]/dx2;
        a[3]=b[3]/dx3;
    }

    private double[] genArray(){
        double[] result = new double[4];
        Random rand = new Random();
        for (int i=0; i< 4; i++){
            result[i]= minY + rand.nextDouble() * (maxY - minY);
        }
        return result;
    }
}

