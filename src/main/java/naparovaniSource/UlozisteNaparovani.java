package main.java.naparovaniSource;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UlozisteNaparovani {

    private final File file = new File("res/set/seznamNaparovani.txt");
    private final String prvniLine = "Id/Type/Duration/Percentage/Aperture";
    private final String druhaLine = "1/Linear/100/100";

    private SimpleDateFormat formatter = new SimpleDateFormat("ddMMHHmmssSSS");
    String code;


    public List<Naparovani> fillTableModel() {
        List<Naparovani> listNaparovani = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String firstLine = br.readLine().trim();

            Object[] tableLines = br.lines().toArray();

            for(int i = 0; i < tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                listNaparovani.add(new Naparovani(Integer.parseInt(dataRow[0]), dataRow[1], dataRow[2], dataRow[3], dataRow[4]));
            }
            br.close();
            return  listNaparovani;
        } catch (Exception ex) {
            errorViewer(ex);
        }
        return listNaparovani;
    }

    public void saveJTableToTxt (NaparovaniTableModel tableModel) {
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
            bfw.write("Id/Name/Author/Notes/Code");
            bfw.newLine();
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    switch (col) {
                        case 0: bfw.write(Integer.toString((Integer) tableModel.getValueAt(row, col)));
                            break;
                        case 1: bfw.write((String) tableModel.getValueAt(row, col));
                            break;
                        case 2: bfw.write((String) tableModel.getValueAt(row, col));
                            break;
                        case 3: bfw.write((String) tableModel.getValueAt(row, col));
                            break;
                        case 4: bfw.write((String) tableModel.getValueAt(row, col));
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

    }

    public void removeRow (NaparovaniTableModel tableModel, int idRow) {
        try {
            saveJTableToTxt(tableModel);
            copyFiles("seznamNaparovani", "NEMAZAT");
            File fileCopyFrom = new File ("res/set/NEMAZAT.txt");
            File fileCopyTo = new File ("res/set/seznamNaparovani.txt");

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
                    bW.write((i+1) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3] + "/" + dataRow[4]);
                } else {
                    bW.newLine();
                    bW.write((i) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3] + "/" + dataRow[4]);
                }
            }

            bW.close();
            bR.close();
            tableModel.setListNaparovani(fillTableModel());
        } catch (Exception ex) {
            errorViewer(ex);
        }
    }

    public void addRowEnd (NaparovaniTableModel tableModelNaparovani) {

        try {
            code = formatter.format(new Date());
            File f = new File("res/naparovani/" + code + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(prvniLine);
            writer.newLine();
            writer.write(druhaLine);
            writer.close();

            saveJTableToTxt(tableModelNaparovani);

            copyFiles("seznamNaparovani", "NEMAZAT");
            File fileCopyFrom = new File ("res/set/NEMAZAT.txt");
            File fileCopyTo = new File ("res/set/seznamNaparovani.txt");

            BufferedReader bR = new BufferedReader(new FileReader(fileCopyFrom));
            BufferedWriter bW = new BufferedWriter(new FileWriter(fileCopyTo));

            String firstLine = bR.readLine(); //musime Prezist Prvni Radek Aby Potom Sly uz jenom TableData
            bW.write(firstLine);

            Object[] tableLines = bR.lines().toArray();
            if (tableLines.length == 0) {
                bW.newLine();
                bW.write("1/NEW/PC/-/" + code);
            }

            for(int i = 0; i < tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                bW.newLine();
                bW.write((i+1) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3] + "/" + dataRow[4]);
                if(i == (tableLines.length-1)) {
                    bW.newLine();
                    bW.write((tableLines.length+1) + "/NEW/PC/-/" + code);
                }
            }
            bW.close();
            bR.close();

            tableModelNaparovani.setListNaparovani(fillTableModel());

        } catch (Exception ex) {
            errorViewer(ex);
        }
    }

    public void deleteTXT (String txtName) {
        File f = new File("res/naparovani/" + txtName + ".txt");
        f.delete();
    }

    public void copyFiles (String copyFrom, String copyTo) {
        try {
            File fileCopyFrom = new File ("res/set/" + copyFrom + ".txt");
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

    public void prejmenujSlozky (String copyFrom, String copyTo) {
        try {
            File fileCopyFrom = new File ("res/naparovani/" + copyFrom + ".txt");
            File fileCopyTo = new File ("res/naparovani/" + copyTo + ".txt");

            if (fileCopyFrom.renameTo(fileCopyTo));
        } catch (Exception ex) {
            errorViewer(ex);
        }
    }

    public void copyRow (NaparovaniTableModel tableModel,int numberRow) {
        try {
            saveJTableToTxt(tableModel);
            code = formatter.format(new Date());
            copyFiles("seznamNaparovani", "NEMAZAT");
            File fileCopyFrom = new File ("res/set/NEMAZAT.txt");
            File fileCopyTo = new File ("res/set/seznamNaparovani.txt");

            BufferedReader bR = new BufferedReader(new FileReader(fileCopyFrom));
            BufferedWriter bW = new BufferedWriter(new FileWriter(fileCopyTo));

            String firstLine = bR.readLine(); //musime Prezist Prvni Radek Aby Potom Sly uz jenom TableData
            bW.write(firstLine);

            Object[] tableLines = bR.lines().toArray();

            for(int i = 0; i < tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                if (i <= numberRow) {
                    bW.newLine();
                    bW.write((i+1) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3] + "/" + dataRow[4]);
                }
                if (i == numberRow) {
                    bW.newLine();
                    bW.write((i+2) + "/" + dataRow[1] + " Copy/" + dataRow[2] + "/" + dataRow[3] + "/" + code);
                    File f = new File("res/naparovani/" + code + ".txt");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                    writer.write("Id/Type/Duration/Percentage");
                    writer.close();
                }
                if (i > numberRow) {
                    bW.newLine();
                    bW.write((i+2) + "/" + dataRow[1] + "/" + dataRow[2] + "/" + dataRow[3] + "/" + dataRow[4]);
                }
            }
            bW.close();
            bR.close();

            tableModel.setListNaparovani(fillTableModel());
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
