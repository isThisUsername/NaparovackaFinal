package main.java.naparovaniSchema;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class PosledniOpened {

    private final File file = new File("res/set/lastOpened.txt");

    private final File fileS = new File("res/set/seznamNaparovani.txt");

    private String nameFile;

    public PosledniOpened() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            nameFile = br.readLine();
            if(nameFile == null) {
                BufferedReader idk = new BufferedReader(new FileReader(fileS));
                String firstLine = idk.readLine();
                String[] secondLine = idk.readLine().trim().split("/");
                nameFile = secondLine[4];
                System.out.println("name<" + nameFile);
                idk.close();

            }
            br.close();
        } catch (Exception ex) {
        }
    }

    public String getNameFile() {
        return nameFile;
    }

    public void ulozPosledni (String posledniName) {
        try {
            BufferedWriter bW = new BufferedWriter(new FileWriter(new File ("res/set/lastOpened.txt")));
            bW.write(posledniName);
            bW.close();
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
