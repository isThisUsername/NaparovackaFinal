package main.java;

import main.java.naparovaniSchema.SegmentTableModel;

import java.awt.*;
import java.util.Random;

public class Canva extends Canvas {

    private SegmentTableModel model;
    private String jmenoPatternu;

    private final int rozmerX;
    private final int rozmerY;

    private int suma;

    private final String constString = "Const";
    private final String linearString = "Linear";
    private final String jumpString = "Jump";
    private final String apertureOnString = "OPEN APERTURE";
    private final String apertureOFFString = "CLOSE APERTURE";

    private int mod = 0;

    private Color prvniBarva = new Color (
            new Random().ints(0,255).findFirst().getAsInt(),
            new Random().ints(0,255).findFirst().getAsInt(),
            new Random().ints(0,255).findFirst().getAsInt());
    private Color druhaBarva = new Color (
            new Random().ints(0,255).findFirst().getAsInt(),
            new Random().ints(0,255).findFirst().getAsInt(),
            new Random().ints(0,255).findFirst().getAsInt());

    private final Color[] mojePaleta = {
            new Color(45, 0, 247),
            new Color(106, 0, 244),
            new Color(137, 0, 242),
            new Color(160, 0, 242),
            new Color(177, 0, 232),
            new Color(188, 0, 221),
            new Color(209, 0, 209),
            new Color(219, 0, 182),
            new Color(229, 0, 164),
            new Color(242, 0, 137)};

    private final Color[] mojePaleta2 = {
            new Color(3, 7, 30),
            new Color(55, 6, 23),
            new Color(106, 4, 15),
            new Color(157, 2, 8),
            new Color(208, 0, 0),
            new Color(220, 47, 2),
            new Color(232, 93, 4),
            new Color(244, 140, 6),
            new Color(250, 163, 7),
            new Color(255, 186, 8)};

    private final Color[] mojePaleta1 = {
            new Color(116, 0, 184),
            new Color(105, 48, 195),
            new Color(94, 96, 206),
            new Color(83, 144, 217),
            new Color(78, 168, 222),
            new Color(72, 191, 227),
            new Color(86, 207, 225),
            new Color(100, 223, 223),
            new Color(114, 239, 221),
            new Color(128, 255, 219)};

    private Color jezdec = new Color(255, 213, 0);

    private boolean loading = true;
    private int pseudobooleanCervena = 0;

    private final Stroke dashed = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{9}, 0);


    Canva(int rozmerX, int rozmerY, SegmentTableModel model) {
        this.rozmerX = rozmerX;
        this.rozmerY = rozmerY;
        this.model = model;
        this.setSize(rozmerX, rozmerY);
        this.setBackground(mojePaleta2[0]);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        switch (mod) {
            case 0:
                g2.setStroke(new BasicStroke(3));
                g2.setColor(Color.white);
                suma = 0;
                for (int i = 0; i < model.getRowCount(); i++) {
                    suma += (int) model.getValueAt(i, 2);
                }

//                if (loading) {
//                    for (int s = 0; s < mojePaleta.length; s++) {
//                        g2.setColor(mojePaleta[s]);
//                        g2.fillRect(s * rozmerX / 10, 0, rozmerX / 10, rozmerY);
//                        try {
//                            Thread.sleep(30);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    g2.setFont(new Font("Courier New", 1, 100));
//                    g2.setColor(Color.white);
//                    g2.drawString("WELCOME", 10, rozmerY - 10);
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    loading = false;
//                }

                if (loading) {
                    for (int s = 0; s < rozmerX; s++) {
                        g2.setColor(new Color(
                                prvniBarva.getRed()+ Math.round(s*(druhaBarva.getRed()-prvniBarva.getRed())/rozmerX),
                                prvniBarva.getGreen()+ Math.round(s*(druhaBarva.getGreen()-prvniBarva.getGreen())/rozmerX),
                                prvniBarva.getBlue()+ Math.round(s*(druhaBarva.getBlue()-prvniBarva.getBlue())/rozmerX)));
                        g2.fillRect(s, 0, 1, rozmerY);
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    g2.setFont(new Font("Courier New", 1, 100));
                    g2.setColor(Color.white);
                    g2.drawString("WELCOME & EVAPORATE", 10, rozmerY - 10);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loading = false;
                }

                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 1).toString().equals(linearString)) {
                        if (i == 0) {
                            g2.drawLine(0, rozmerY, rozmerX * ((int) model.getValueAt(0, 2)) / suma,
                                    rozmerY - rozmerY / 100 * ((int) model.getValueAt(0, 3)));
                        } else {
                            g2.drawLine(rozmerX * souradniceX(i) / suma, rozmerY - rozmerY / 100 * ((int) model.getValueAt(i - 1, 3)),
                                    rozmerX * souradniceX(i + 1) / suma, rozmerY - rozmerY / 100 * ((int) model.getValueAt(i, 3)));
                        }

                    }
                    if (model.getValueAt(i, 1).toString().equals(jumpString)) {
                        if (i == 0) {
                            g2.drawLine(0, rozmerY, 0, rozmerY - rozmerY / 100 * ((int) model.getValueAt(0,
                                    3)));
                        } else {
                            g2.drawLine(rozmerX * souradniceX(i) / suma, rozmerY - rozmerY / 100 * ((int) model.getValueAt(i - 1, 3)),
                                    rozmerX * souradniceX(i + 1) / suma, rozmerY - rozmerY / 100 * ((int) model.getValueAt(i, 3)));
                        }

                    }

                    if (model.getValueAt(i, 1).toString().equals(constString)) {
                        g2.drawLine(rozmerX * souradniceX(i) / suma, rozmerY - rozmerY / 100 * ((int) model.getValueAt(i - 1, 3)),
                                rozmerX * souradniceX(i + 1) / suma, rozmerY - rozmerY / 100 * ((int) model.getValueAt(i - 1, 3)));
                    }
                    if (model.getValueAt(i, 1).toString().equals(apertureOnString)) {
                        pseudobooleanCervena = -1;
                        g2.setColor(new Color(112, 224, 0));
                        g2.setStroke(new BasicStroke(5));
                        g2.drawLine(rozmerX * souradniceX(i) / suma, 0, rozmerX * souradniceX(i) / suma, rozmerY);
                        g2.drawString("OPENED", rozmerX * souradniceX(i) / suma + 4, 12);
                    }
                    if (model.getValueAt(i, 1).toString().equals(apertureOFFString)) {
                        pseudobooleanCervena = 1;
                        g2.setColor(new Color(255, 0, 0));
                        g2.setStroke(new BasicStroke(5));
                        g2.drawLine(rozmerX * souradniceX(i) / suma, 0, rozmerX * souradniceX(i) / suma, rozmerY);
                        g2.drawString("CLOSED", rozmerX * souradniceX(i) / suma + 4, 12);
                    }

                    if (model.getValueAt(i, 1).toString().equals(apertureOFFString) || model.getValueAt(i, 1).toString().equals(apertureOnString)) {

                    } else {
                        g2.setStroke(dashed);
                        g2.drawLine(rozmerX * souradniceX(i) / suma, 0, rozmerX * souradniceX(i) / suma, rozmerY);
                        g2.setStroke(new BasicStroke(3));
                    }

                    g2.setStroke(new BasicStroke(5));
                }
                break;
            case 2:
                for (int s = 0; s < mojePaleta.length; s++) {
                    g2.setColor(mojePaleta2[s]);
                    g2.fillRect(s * rozmerX / 10, 0, rozmerX / 10, rozmerY);

                }
                g2.setFont(new Font("Courier New", 1, 100));
                g2.setColor(Color.white);
                g2.drawString("0ms Linear! (JUMP?)", 10, rozmerY - 10);
                break;
            case 1:
                for (int s = 0; s < mojePaleta.length; s++) {
                    g2.setColor(mojePaleta1[s]);
                    g2.fillRect(s * rozmerX / 10, 0, rozmerX / 10, rozmerY);

                }
                g2.setFont(new Font("Courier New", 1, 70));
                g2.setColor(Color.white);
                g2.drawString("Percentage out of 0% - 100%", 10, rozmerY - 10);
                break;
        }
    }

    public int souradniceX(int pozice) {
        int interniSuma = 0;
        for (int i = 0; i < pozice; i++) {
            interniSuma += (int) model.getValueAt(i, 2);
        }
        return interniSuma;
    }

    public void updateCanvu(SegmentTableModel model, String jmenoPatternu) {
        this.model = model;
        this.jmenoPatternu = jmenoPatternu;
        repaint();
    }

    public void setMod(int mod) {
        this.mod = mod;
    }
}
