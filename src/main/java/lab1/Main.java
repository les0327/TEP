package lab1;

public class Main {

    public static void main(String[] args) {
        Model model = new Model(0, 10, 0, 1, 1, 1);
        int l = 10;

        System.out.printf(
                "%3s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s %"+l+"s%n",
                "N", "X1", "X2", "X3", "Y", "Xn1", "Xn2", "Xn3", "Yn"
        );
        for (int i = 0; i < 8; i++) {
            System.out.printf(
                    "%3d %"+l+".4f %"+l+".4f %"+l+".4f %"+l+".4f %"+l+".4f %"+l+".4f %"+l+".4f %"+l+".4f%n",
                    i,
                    model.x[i][0],
                    model.x[i][1],
                    model.x[i][2],
                    model.y[i],
                    model.nx[i][0],
                    model.nx[i][1],
                    model.nx[i][2],
                    model.ny[i]
            );
        }

        System.out.printf("%n%"+l+"s %"+l+"s %"+l+"s%n%"+l+".4f %"+l+".4f %"+l+".4f%n%n",
                "X01", "X02","X03", model.x0[0], model.x0[1], model.x0[2]);
        System.out.printf("%"+l+"s %"+l+"s %"+l+"s%n%"+l+".4f %"+l+".4f %"+l+".4f%n%n",
                "dX1", "dX2","dX3", model.dx[0], model.dx[1], model.dx[2]);
        System.out.printf(" %"+l+"s %"+l+"s %"+l+"s %"+l+"s%n%"+l+".4f %"+l+".4f %"+l+".4f %"+l+".4f%n%n",
                "Xopt", "Xopt","Xopt", "Yopt",
                model.x[model.optimum][0], model.x[model.optimum][1],
                model.x[model.optimum][2], model.y[model.optimum]);
    }
}
