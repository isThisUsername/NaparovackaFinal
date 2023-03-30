package main.java.naparovaniFinal;

import main.java.naparovaniSource.NaparovaniTableModel;
import main.java.typeOfBoat.TypeBoat;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UlozisteFinal {

    private final File file = new File("res/set/seznamFinal.txt");
    private final String firstLine = "Id/Name/Note/Boat/Cycles/Delay/Identifier";


    public List<FinalSegment> fillTableModel() {
        List<FinalSegment> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String firstLine = br.readLine().trim();
            String[] columnsName = firstLine.split("/");

            Object[] tableLines = br.lines().toArray();

            for (int i = 0; i < tableLines.length; i++) {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                list.add(new FinalSegment(Integer.parseInt(dataRow[0]), dataRow[1], dataRow[2], new TypeBoat(dataRow[3]), Integer.parseInt(dataRow[4]), dataRow[5], dataRow[6]));
            }
            br.close();
            return list;
        } catch (Exception ex) {
            errorViewer(ex);
        }
        return list;
    }

    public void saveJTableToTxt(FinalSegmentTableModel tableModel) {
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
            bfw.write(firstLine);
            bfw.newLine();
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    switch (col) {
                        case 0:
                            bfw.write(Integer.toString((Integer) tableModel.getValueAt(row, col)));
                            break;
                        case 1:
                            bfw.write((String) tableModel.getValueAt(row, col));
                            break;
                        case 2:
                            bfw.write((String) tableModel.getValueAt(row, col));
                            break;
                        case 3:
                            bfw.write(String.valueOf(tableModel.getValueAt(row, col)));
                            break;
                        case 4:
                            bfw.write(Integer.toString((Integer) tableModel.getValueAt(row, col)));
                            break;
                        case 5:
                            bfw.write((String) tableModel.getValueAt(row, col));
                            break;
                        case 6:
                            bfw.write((String) tableModel.getValueAt(row, col));
                            break;
                    }
                    if ((tableModel.getColumnCount() - 1) != col) {
                        bfw.write("/");
                    }
                }
                if ((tableModel.getRowCount() - 1) != row) {
                    bfw.newLine();
                }
            }
            bfw.close();
        } catch (Exception ex) {
            errorViewer(ex);
        }

    }

    public void add(FinalSegmentTableModel model, String name, String inEx, String code) {
        try {
            saveJTableToTxt(model);
            File fileCopyFrom = new File("res/set/seznamFinal.txt");
            File fileCopyTo = new File("res/set/NEMAZAT.txt");

            BufferedReader bR = new BufferedReader(new FileReader(fileCopyFrom));
            BufferedWriter bW = new BufferedWriter(new FileWriter(fileCopyTo));

            String radek = bR.readLine();
            bW.write(radek);
            while ((radek = bR.readLine()) != null) {
                bW.newLine();
                bW.write(radek);
            }
            bR.close();
            bW.close();
        } catch (Exception ex) {
            errorViewer(ex);
        }

        try {
            File fileCopyFrom = new File("res/set/NEMAZAT.txt");
            File fileCopyTo = new File("res/set/seznamFinal.txt");

            BufferedReader bR = new BufferedReader(new FileReader(fileCopyFrom));
            BufferedWriter bW = new BufferedWriter(new FileWriter(fileCopyTo));

            String radek = bR.readLine();
            bW.write(radek);
            int pocetRadku = 1;
            while ((radek = bR.readLine()) != null) {
                pocetRadku++;
                bW.newLine();
                bW.write(radek);
            }

            bW.newLine();
            bW.write(pocetRadku + "/" + name + "/" + inEx + "/1/1/0h0m0s/" + code);

            bR.close();
            bW.close();
            model.setListSegment(fillTableModel());
        } catch (Exception ex) {
            errorViewer(ex);
        }
    }

    public void nullIt(FinalSegmentTableModel model) {
        try {
            saveJTableToTxt(model);
            File fileCopyFrom = new File("res/set/seznamFinal.txt");
            File fileCopyTo = new File("res/set/NEMAZAT.txt");

            BufferedReader bR = new BufferedReader(new FileReader(fileCopyFrom));
            BufferedWriter bW = new BufferedWriter(new FileWriter(fileCopyTo));

            String radek = bR.readLine();
            bW.write(radek);
            while ((radek = bR.readLine()) != null) {
                bW.newLine();
                bW.write(radek);
            }
            bR.close();
            bW.close();
        } catch (Exception ex) {
            errorViewer(ex);
        }

        try {
            File fileCopyFrom = new File("res/set/NEMAZAT.txt");
            File fileCopyTo = new File("res/set/seznamFinal.txt");

            BufferedReader bR = new BufferedReader(new FileReader(fileCopyFrom));
            BufferedWriter bW = new BufferedWriter(new FileWriter(fileCopyTo));

            String radek = bR.readLine();
            bW.write(radek);

            bR.close();
            bW.close();
            model.setListSegment(fillTableModel());
        } catch (Exception ex) {
            errorViewer(ex);
        }
    }

    public void errorViewer (Exception e) {
        StringBuilder sb = new StringBuilder("Error: ");
        sb.append(e.getMessage());
        sb.append("\n");
        for (StackTraceElement ste : e.getStackTrace()) {
            sb.append(ste.toString());
            sb.append("\n");
        }
        JTextArea jta = new JTextArea(sb.toString());
        JScrollPane jsp = new JScrollPane(jta) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 400);
            }
        };
        JOptionPane.showMessageDialog(
                null, jsp, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
