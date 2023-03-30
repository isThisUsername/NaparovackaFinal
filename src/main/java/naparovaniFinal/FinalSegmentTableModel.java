package main.java.naparovaniFinal;

import main.java.typeOfBoat.TypeBoat;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class FinalSegmentTableModel extends AbstractTableModel {

    private final String[] columnNames = new String[]{"Id", "Name", "Note", "Boat", "Cycles", "Delay [?h?m?s]", "Identifier"};

    private List<FinalSegment> listSegment = new ArrayList<>();

    public FinalSegmentTableModel(List<FinalSegment> listSegment) { //konstruktor
        this.listSegment = listSegment;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    @Override
    public int getRowCount() {
        return listSegment.size();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        FinalSegment segment = listSegment.get(rowIndex);
        switch (columnIndex) {
            case 1:
                segment.setJmenoZdroje((String) value);
                break;
            case 2:
                segment.setNoteSteam((String) value);
                break;
            case 3:
                segment.setSystem((TypeBoat) value);
                break;
            case 4:
                segment.setKolikratOpakovat((int) value);
                break;
            case 5:
                segment.setDobaDelay((String) value);
                break;
            case 6:
                segment.setCode((String) value);
                break;
        }
    }

    public void setListSegment(List<FinalSegment> listSegment) {
        this.listSegment = listSegment;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object returnValue = null;
        FinalSegment segment = listSegment.get(rowIndex);

        switch (columnIndex) {
            case 0:
                returnValue = rowIndex + 1;
                break;
            case 1:
                returnValue = segment.getJmenoZdroje();
                break;
            case 2:
                returnValue = segment.getNoteSteam();
                break;
            case 3:
                returnValue = segment.getSystem();
                break;
            case 4:
                returnValue = segment.getKolikratOpakovat();
                break;
            case 5:
                returnValue = segment.getDobaDelay();
                break;
            case 6:
                returnValue = segment.getCode();
                break;
        }
        return returnValue;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if ((columnIndex >= 2) && (columnIndex <= 5)) return true;
        return false;
    }

}
