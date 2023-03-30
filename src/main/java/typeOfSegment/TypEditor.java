package main.java.typeOfSegment;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TypEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private Typ typ;
    private final List<Typ> listTyp;

    private final String constString = "Const";
    private final String linearString = "Linear";
    private final String jumpString = "Jump";
    private final String apertureOnString = "OPEN APERTURE";
    private final String apertureOFFString = "CLOSE APERTURE";

    public TypEditor(List<Typ> listTyp) {
        this.listTyp = listTyp;
    }

    @Override
    public Object getCellEditorValue() {
        return this.typ;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof Typ) {
            this.typ = (Typ) value;
        }

        JComboBox<Typ> comboCountry = new JComboBox<Typ>();

        if(row == 0) {
            listTyp.removeAll(listTyp);
            listTyp.add(new Typ(linearString));
            listTyp.add(new Typ(jumpString));
            listTyp.add(new Typ(apertureOnString));
            listTyp.add(new Typ(apertureOFFString));
        } else {
            listTyp.removeAll(listTyp);
            listTyp.add(new Typ(linearString));
            listTyp.add(new Typ(jumpString));
            listTyp.add(new Typ(constString));
            listTyp.add(new Typ(apertureOnString));
            listTyp.add(new Typ(apertureOFFString));

        }


        for (Typ aCountry : listTyp) {
            comboCountry.addItem(aCountry);
        }

        comboCountry.setSelectedItem(typ);
        comboCountry.addActionListener(this);

//        if (isSelected) {
//            comboCountry.setBackground(new Color(25,190,15));
//        } else {
//            comboCountry.setBackground(new Color(25,255,255));
//        }

        return comboCountry;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JComboBox<Typ> comboCountry = (JComboBox<Typ>) event.getSource();
        this.typ = (Typ) comboCountry.getSelectedItem();
    }

}
