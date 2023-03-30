package main.java;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class IdFinal extends DefaultTableCellRenderer {
    private int ostreIdFinal = -1;

    private Color done = new Color(0, 230, 118);
    private Color in = new Color(255, 214, 0);
    private Color will = new Color(255, 255, 255);

    private Color selected = new Color (207, 216, 220);

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row < ostreIdFinal) cell.setBackground(done);
        if (row == ostreIdFinal) cell.setBackground(in);
        if (row > ostreIdFinal) cell.setBackground(will);

        if (isSelected) setBackground(selected);

        cell.setForeground(new Color(0, 0, 0));
        return cell;
    }

    public void setOstreIdFinal(int ostreIdFinal) {
        this.ostreIdFinal = ostreIdFinal;
    }
}
