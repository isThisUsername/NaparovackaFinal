package main.java.naparovaniSchema;

import main.java.typeOfSegment.Typ;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Uloziste {

    private final String ulozisteName;
    private final String filePath;
    private final File file;

    private final String prvniLine = "Id/Type/Duration/Percentage";

    public Uloziste(String ulozisteName) {
        this.ulozisteName = ulozisteName;
        this.filePath = "res/naparovani/" + ulozisteName + ".txt";
        this.file = new File(filePath);
    }

    public Uloziste (String ulozisteName, String filePath) {
        this.ulozisteName = ulozisteName;
        this.filePath = filePath + "/" + ulozisteName + ".txt";
        this.file = new File(this.filePath);
    }

    public List<Segment> fillTableModel() { //predelano
        List<Segment> listSegment = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String firstLine = br.readLine().trim();
            String[] columnsName = firstLine.split("/");

            Object[] tableLines = br.lines().toArray();


            for(int i = 0; i < tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                listSegment.add(new Segment(Integer.parseInt(dataRow[0]), new Typ(dataRow[1]), Integer.parseInt(dataRow[2]), Integer.parseInt(dataRow[3])));
            }
            br.close();
            return  listSegment;
        } catch (Exception ex) {
            errorViewer(ex);
        }
        return listSegment;
    } //predelano

    public void saveJTableToTxt (SegmentTableModel tableModel) { //predelano

        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
            bfw.write(prvniLine);
            bfw.newLine();
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    switch (col) {
                        case 0: bfw.write(Integer.toString((Integer) tableModel.getValueAt(row, col)));
                            break;
                        case 1: bfw.write(String.valueOf(tableModel.getValueAt(row, col)));
                            break;
                        case 2: bfw.write(Integer.toString((Integer) tableModel.getValueAt(row, col)));
                            break;
                        case 3: bfw.write(Integer.toString((Integer) tableModel.getValueAt(row, col)));
                            break;
                    }
                    if((tableModel.getColumnCount()-1) != col) {
                        bfw.write("/");
                    }
                }
                if((tableModel.getRowCount()-1) != row) {
                    bfw.newLine();
                }
            }
            bfw.close();
        } catch (Exception ex) {
            errorViewer(ex);
        }

    } //predelano

    public void removeRow (SegmentTableModel tableModel, int idRow) { //predelano
        try {
            saveJTableToTxt(tableModel);
            copyFiles(ulozisteName, "NEMAZAT");
            File fileCopyFrom = new File ("res/set/NEMAZAT.txt");
            File fileCopyTo = new File ("res/naparovani/" + ulozisteName + ".txt");

            BufferedReader bR = new BufferedReader(new FileReader(fileCopyFrom));
            BufferedWriter bW = new BufferedWriter(new FileWriter(fileCopyTo));

            String firstLine = bR.readLine(); //musime Prezist Prvni Radek Aby Potom Sly uz jenom TableData
            bW.write(firstLine);

            Object[] tableLines = bR.lines().toArray();

            for(int i = 0; i < tableLines.length; i++)
            {
                if(i == (idRow)) continue;
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                if(i < (idRow)) {
                    bW.newLine();
                    bW.write((i+1) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3]);
                } else {
                    bW.newLine();
                    bW.write((i) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3]);
                }
            }

            bW.close();
            bR.close();

            tableModel.setListSegment(fillTableModel());

        } catch (Exception ex) {
            errorViewer(ex);
        }
    } //predelano

    public void addRowEnd (SegmentTableModel tableModel) {
        try {
            saveJTableToTxt(tableModel);
            copyFiles(ulozisteName, "NEMAZAT");
            File fileCopyFrom = new File ("res/set/NEMAZAT.txt");
            File fileCopyTo = new File ("res/naparovani/" + ulozisteName + ".txt");

            BufferedReader bR = new BufferedReader(new FileReader(fileCopyFrom));
            BufferedWriter bW = new BufferedWriter(new FileWriter(fileCopyTo));

            String firstLine = bR.readLine();
            bW.write(firstLine);

            Object[] tableLines = bR.lines().toArray();
            if (tableLines.length == 0) {
                bW.newLine();
                bW.write("1/Linear/100/100");
            }
            for(int i = 0; i < tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                bW.newLine();
                bW.write((i+1) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3]);
                if(i == (tableLines.length-1)) {
                    bW.newLine();
                    bW.write((tableLines.length+1) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3]);
                }
            }
            bW.close();
            bR.close();
            tableModel.setListSegment(fillTableModel());
        } catch (Exception ex) {
            errorViewer(ex);
        }
    }

    public void copyRow (SegmentTableModel tableModel,int numberRow) {
        try {
            saveJTableToTxt(tableModel);
            copyFiles(ulozisteName, "NEMAZAT");
            File fileCopyFrom = new File ("res/set/NEMAZAT.txt");
            File fileCopyTo = new File ("res/naparovani/" + ulozisteName + ".txt");

            BufferedReader bR = new BufferedReader(new FileReader(fileCopyFrom));
            BufferedWriter bW = new BufferedWriter(new FileWriter(fileCopyTo));

            String firstLine = bR.readLine(); //musime Prezist Prvni Radek Aby Potom Sly uz jenom TableData
            bW.write(firstLine);

            Object[] tableLines = bR.lines().toArray();

            for(int i = 0; i < tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                if (i < numberRow) {
                    bW.newLine();
                    bW.write((i+1) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3]);
                }
                if (i == numberRow) {
                    bW.newLine();
                    bW.write((i+1) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3]);
                }

                if (i >= numberRow) {
                    bW.newLine();
                    bW.write((i+2) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3]);
                }
            }
            bW.close();
            bR.close();

            tableModel.setListSegment(fillTableModel());

        } catch (Exception ex) {
            errorViewer(ex);
        }
    }

    public void deleteRows(SegmentTableModel tableModel, int startRow, int stopRow) {
        if (startRow > stopRow) {
            int x = startRow;
            startRow = stopRow;
            stopRow = x;
        }

        for (int i = stopRow; i >= startRow; i--) {
            removeRow(tableModel, i);
        }
    }

    public void copyFiles (String copyFrom, String copyTo) {
        try {
            File fileCopyFrom = new File ("res/naparovani/" + copyFrom + ".txt");
            File fileCopyTo = new File ("res/set/" + copyTo + ".txt");

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
    }

    public void copyOpened (String copyTo) {
        try {
            File fileCopyTo = new File ("res/naparovani/" + copyTo + ".txt");

            BufferedReader bR = new BufferedReader(new FileReader(file));
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
    }

    public void moveRowUp (SegmentTableModel tableModel, int numberRow) {
        if (numberRow > 0) {
            try {
                saveJTableToTxt(tableModel);
                copyFiles(ulozisteName, "NEMAZAT");
                File fileCopyFrom = new File("res/set/NEMAZAT.txt");
                File fileCopyTo = new File("res/naparovani/" + ulozisteName + ".txt");

                BufferedReader bR = new BufferedReader(new FileReader(fileCopyFrom));
                BufferedWriter bW = new BufferedWriter(new FileWriter(fileCopyTo));

                String firstLine = bR.readLine(); //musime Prezist Prvni Radek Aby Potom Sly uz jenom TableData
                bW.write(firstLine);

                Object[] tableLines = bR.lines().toArray();

                Object x = tableLines[numberRow - 1];
                tableLines[numberRow - 1] = tableLines[numberRow];
                tableLines[numberRow] = x;

                for (int i = 0; i < tableLines.length; i++) {
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split("/");
                    bW.newLine();
                    bW.write((i + 1) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3]);
                }
                bW.close();
                bR.close();
                tableModel.setListSegment(fillTableModel());
            } catch (Exception ex) {
                errorViewer(ex);
            }
        }
    }

    public void moveRowDown (SegmentTableModel tableModel, int numberRow) {
        if (numberRow < (tableModel.getRowCount()-1)) {
            try {
                saveJTableToTxt(tableModel);
                copyFiles(ulozisteName, "NEMAZAT");
                File fileCopyFrom = new File("res/set/NEMAZAT.txt");
                File fileCopyTo = new File("res/naparovani/" + ulozisteName + ".txt");

                BufferedReader bR = new BufferedReader(new FileReader(fileCopyFrom));
                BufferedWriter bW = new BufferedWriter(new FileWriter(fileCopyTo));

                String firstLine = bR.readLine(); //musime Prezist Prvni Radek Aby Potom Sly uz jenom TableData
                bW.write(firstLine);

                Object[] tableLines = bR.lines().toArray();

                Object x = tableLines[numberRow];
                tableLines[numberRow] = tableLines[numberRow + 1];
                tableLines[numberRow + 1] = x;

                for (int i = 0; i < tableLines.length; i++) {
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split("/");
                    bW.newLine();
                    bW.write((i + 1) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3]);
                }
                bW.close();
                bR.close();
                tableModel.setListSegment(fillTableModel());
            } catch (Exception ex) {
                errorViewer(ex);
            }
        }
    }

    public int sumaDuration (SegmentTableModel tableModel) {
        int suma = 0;

        try {
            saveJTableToTxt(tableModel);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String firstLine = br.readLine().trim();
            String[] columnsName = firstLine.split("/");

            Object[] tableLines = br.lines().toArray();

            for(int i = 0; i < tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                suma += Integer.parseInt(dataRow[2]);
            }

            br.close();
            return  suma;
        } catch (Exception ex) {
            errorViewer(ex);
        }
        return suma;
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

    public int pocetRadkuFilu() throws FileNotFoundException {
        return (new BufferedReader(new FileReader(new File ("res/naparovani/" + ulozisteName + ".txt")))).lines().toArray().length;
    }

}


