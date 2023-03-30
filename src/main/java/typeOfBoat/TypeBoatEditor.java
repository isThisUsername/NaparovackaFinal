package main.java.typeOfBoat;

import main.java.typeOfSegment.Typ;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TypeBoatEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private TypeBoat typ;
    private final List<TypeBoat> listTyp;

    private final String jedna = "1";
    private final String dva = "2";

    public TypeBoatEditor(List<TypeBoat> listTyp) {
        this.listTyp = listTyp;
    }

    @Override
    public Object getCellEditorValue() {
        return this.typ;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof TypeBoat) {
            this.typ = (TypeBoat) value;
        }

        JComboBox<TypeBoat> comboCountry = new JComboBox<TypeBoat>();


//            listTyp.add(new TypeBoat(jedna));
//            listTyp.add(new TypeBoat(dva));



        for (TypeBoat aCountry : listTyp) {
            comboCountry.addItem(aCountry);
        }

        comboCountry.setSelectedItem(typ);
        comboCountry.addActionListener(this);

        return comboCountry;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JComboBox<TypeBoat> comboCountry = (JComboBox<TypeBoat>) event.getSource();
        this.typ = (TypeBoat) comboCountry.getSelectedItem();
    }

}
