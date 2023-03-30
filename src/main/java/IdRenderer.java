package main.java;

import main.java.typeOfSegment.Typ;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class IdRenderer extends DefaultTableCellRenderer {

    private int ostreIdPattern = -1;

    private Color done = new Color(0, 230, 118);
    private Color in = new Color(255, 214, 0);
    private Color will = new Color(255, 255, 255);

    private Color selected = new Color (207, 216, 220);


    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row < ostreIdPattern) cell.setBackground(done);
        if (row == ostreIdPattern) cell.setBackground(in);
        if (row > ostreIdPattern) cell.setBackground(will);

        if (isSelected) cell.setBackground(selected);


        cell.setForeground(new Color(0, 0, 0));
        return cell;
    }

    public int getOstreIdPattern() {
        return ostreIdPattern;
    }

    public void setOstreIdPattern(int ostreIdPattern) {
        this.ostreIdPattern = ostreIdPattern;
    }
}
