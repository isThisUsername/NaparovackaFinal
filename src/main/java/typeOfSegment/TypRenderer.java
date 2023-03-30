package main.java.typeOfSegment;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TypRenderer extends DefaultTableCellRenderer {

    private int ostreIdPattern = -1;

    private Color done = new Color(0, 230, 118);
    private Color in = new Color(255, 214, 0);
    private Color will = new Color(255, 255, 255);

    private Color selected = new Color (207, 216, 220);

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Typ) {
            Typ typ = (Typ) value;
            setText(typ.getTyp());
        }

        if (row < ostreIdPattern) setBackground(done);
        if (row == ostreIdPattern) setBackground(in);
        if (row > ostreIdPattern) setBackground(will);

        if (isSelected) setBackground(selected);
        setForeground(new Color(0, 0, 0));

        return this;
    }

    public void setOstreIdPattern(int ostreIdPattern) {
        this.ostreIdPattern = ostreIdPattern;
    }
}