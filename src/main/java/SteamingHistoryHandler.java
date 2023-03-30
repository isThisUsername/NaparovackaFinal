package main.java;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class SteamingHistoryHandler {

    private final File file = new File("res/export/STEAMING HISTORY.txt");

    private BufferedWriter w;
    private BufferedReader r;

    public SteamingHistoryHandler() {
        try {
            copyFiles();
        } catch (Exception ex) {errorViewer(ex);}
    }

    public void dopis(String coZapsat) {
        try {
            w.write(coZapsat);
        } catch (IOException e) {
            errorViewer(e);
        }
    }

    public void copyFiles() {
        try {
            BufferedReader bR = new BufferedReader(new FileReader(file));
            BufferedWriter bW = new BufferedWriter(new FileWriter(new File("res/set/NEMAZAT.txt")));

            String radek = bR.readLine();
            bW.write(radek);
            while ((radek = bR.readLine()) != null) {
                bW.newLine();
                bW.write(radek);
            }
            bR.close();
            bW.close();

            r = new BufferedReader(new FileReader(new File("res/set/NEMAZAT.txt")));
            w = new BufferedWriter(new FileWriter(file));

            radek = r.readLine();
            w.write(radek);
            while ((radek = r.readLine()) != null) {
                w.newLine();
                w.write(radek);
            }
            w.newLine();
            r.close();
        } catch (Exception ex) {
            errorViewer(ex);
        }
    }

    public void killHistory() {
        try {
            w.close();
        } catch (IOException e) {
            errorViewer(e);
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
