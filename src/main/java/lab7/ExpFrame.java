package lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Arrays;

public class ExpFrame extends JFrame {
    private JPanel inputPanel = new JPanel();
    private JButton submitButton = new JButton("Find Optimum");
    private Box outputPanel = Box.createVerticalBox();
    private JLabel[] parameters = {new JLabel(""), new JLabel(""), new JLabel(""), new JLabel(""), new JLabel(""), new JLabel(""), new JLabel("")};
    private JPanel graphPanel;
    private Model model = new Model();

    public static void main(String[] args) {
        JFrame frame = new ExpFrame();
        frame.setVisible(true);
    }

    public ExpFrame() {
        super();
        submitButton.doClick();
        setTitle("Simplex");
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        submitButton.addActionListener(new SubmitListener());
        inputPanel.add(submitButton);
        for (int i = 0; i < parameters.length ;i++){
            outputPanel.add(parameters[i]);
        }
        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);
        pack();
    }

    private class SubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double[][] data = model.findOptimum();
            for (byte i = 0; i < data.length;i++){
                System.out.println(Arrays.toString(data[i]));
            }
            System.out.println();
            parameters[0].setText( "p:"+model.p);
            parameters[1].setText( "q:"+model.q);
            parameters[2].setText( "ro"+model.ro);
            parameters[3].setText( "function:"+model.getFunctionToString());
            parameters[4].setText( "x10:"+data[0][0]);
            parameters[5].setText( "x20:"+data[0][1]);
            parameters[6].setText( "Optimal y:"+data[8][1]);
            if (outputPanel.isAncestorOf(graphPanel)) {
                outputPanel.remove(graphPanel);
            }
            graphPanel = new MyPanel(data);
            outputPanel.add(graphPanel);

            pack();
            setSize(700, 700);
        }
    }

    public class MyPanel extends JPanel {
        final int PAD = 20;
        int[] labeles;
        double[][] data;
        private int x10, x20, x1min, x2min, x1max, x2max;

        // private Color color = Color.yellow;
        public MyPanel(double[][] data) {
            this.data = data;
            setSize(600, 600);
            setBackground(Color.white);
            x10 = (int) data[0][0];
            x20 = (int) data[0][1];
            x1min = (int) data[6][0];
            x2min = (int) data[7][0];
            x1max = (int) data[6][1];
            x2max = (int) data[7][1];
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            int w = 300;
            int h = 300;
// Draw ordinate.;
            g2.draw(new Line2D.Double(w + PAD, PAD, w
                    + PAD, 2 * h - PAD));
// Draw abcissa.
            g2.draw(new Line2D.Double(PAD, h - PAD,
                    2 * w - PAD, h - PAD));
            int center1 = w + PAD;
            int center2 = h - PAD;
            String s;
            double scale1 = (double) (w - 2 * PAD) / (x1max -
                    x1min) * 2;
            double scale2 = (double) (h - 2 * PAD) / (x2max -
                    x2min) * 2;
            g2.setFont(new Font(Font.DIALOG,
                    Font.PLAIN, 8));
            for (int i = 0; i < 6 ; i++){
                s = String.format( "%.2f;%.2f",data[i][0], data[i][1]);
                g2.drawString(s, center1 + (int) ((data[i][0] - data[0][0]) * scale1), center2 - (int) ((data[i][1] - data[0][1]) * scale2));
            }
            g2.draw(new Line2D.Double(center1 + (data[0][0] - data[0][0]) * scale1, center2 - (data[0][1] - data[0][1]) * scale2,center1 + (data[1][0] - data[0][0]) * scale1,center2 - (data[1][1] - data[0][1]) * scale2));
            g2.draw(new Line2D.Double(center1 + (data[1][0] - data[0][0]) * scale1, center2 - (data[1][1] - data[0][1]) * scale2, center1 + (data[2][0] - data[0][0]) * scale1, center2 - (data[2][1] - data[0][1]) * scale2));
            g2.draw(new Line2D.Double(center1 + (data[2][0] - data[0][0]) * scale1, center2 - (data[2][1] - data[0][1]) * scale2, center1 + (data[0][0] - data[0][0]) * scale1, center2 - (data[0][1] - data[0][1]) * scale2));
            g2.setColor(Color.BLUE);

            g2.draw(new Line2D.Double(center1 + (data[3][0] - data[0][0]) * scale1, center2 - (data[3][1] - data[0][1]) * scale2, center1 + (data[4][0] - data[0][0]) * scale1, center2 - (data[4][1] - data[0][1]) * scale2));
            g2.draw(new Line2D.Double(center1 + (data[4][0] - data[0][0]) * scale1, center2 - (data[4][1] - data[0][1]) * scale2, center1 + (data[5][0] - data[0][0]) * scale1, center2 - (data[5][1] - data[0][1]) * scale2));
            g2.draw(new Line2D.Double(center1 + (data[5][0] - data[0][0]) * scale1, center2 - (data[5][1] - data[0][1]) * scale2, center1 + (data[3][0] - data[0][0]) * scale1, center2 - (data[3][1] - data[0][1]) * scale2));
            g2.setColor(Color.RED);
            g2.fill(new Ellipse2D.Double(center1 - 4 + (data[(int) data[8][0] + 3][0] - data[0][0]) * scale1, center2 - 4 - (data[(int) data[8][0] + 3][1] - data[0][1]) * scale2, 8, 8));
        }
    }
}