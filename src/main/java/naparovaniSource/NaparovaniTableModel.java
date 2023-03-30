package main.java.naparovaniSource;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class NaparovaniTableModel extends AbstractTableModel {

    private final String[] columnNames = new String[] {"Id", "Name", "Author", "Notes", "Identifier"};

    private List<Naparovani> listNaparovani = new ArrayList<>();

    public NaparovaniTableModel(List<Naparovani> listNaparovani) { //konstruktor
        this.listNaparovani = listNaparovani;
    }

    public void setListNaparovani(List<Naparovani> listNaparovani) {
        this.listNaparovani = listNaparovani;
    }

    @Override
    public int getRowCount() {
        return listNaparovani.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object returnValue = null;
        Naparovani naparovani = listNaparovani.get(rowIndex);

        switch (columnIndex) {
            case 0:
                returnValue = rowIndex + 1;
                break;
            case 1:
                returnValue = naparovani.getNameNamarovani();
                break;
            case 2:
                returnValue = naparovani.getAutorNaparovani();
                break;
            case 3:
                returnValue = naparovani.getNoteNaparovani();
                break;
            case 4:
                returnValue = naparovani.getCode();
                break;
        }
        return returnValue;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 0;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Naparovani naparovani = listNaparovani.get(rowIndex);
        switch (columnIndex) {
            case 1:
                naparovani.setNameNamarovani((String) value);
                break;
            case 2:
                naparovani.setAutorNaparovani((String) value);
                break;
            case 3:
                naparovani.setNoteNaparovani((String) value);
                break;
        }
    }
}
