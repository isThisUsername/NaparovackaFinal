package main.java.naparovaniSchema;

import main.java.typeOfSegment.Typ;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SegmentTableModel extends AbstractTableModel {

    private final String constString = "Const";
    private final String linearString = "Linear";
    private final String jumpString = "Jump";
    private final String apertureOnString = "OPEN APERTURE";
    private final String apertureOFFString = "CLOSE APERTURE";

    private final int sloupecekId = 0;
    private final int sloupecekType = 1;
    private final int sloupecekDuration = 2;
    private final int sloupecekPercentage = 3;

    private final String[] columnNames = new String[] {"Id", "Type", "Duration [ms]", "Percentage [%]"};

    private List<Segment> listSegment = new ArrayList<>();

    public SegmentTableModel(List<Segment> listSegment) { //konstruktor
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
        Segment segment = listSegment.get(rowIndex);
        switch (columnIndex) {
            case 1:
                segment.setTyp((Typ) value);
                if(value.toString().equals(jumpString)) {
                    segment.setDelka(0);
                }
                if(value.toString().equals(apertureOnString)) {
                    segment.setDelka(0);
                    if (rowIndex == 0) {
                        segment.setProcento(0);
                    }
                }
                if(value.toString().equals(apertureOFFString)) {
                    segment.setDelka(0);
                    if (rowIndex == 0) {
                        segment.setProcento(0);
                    }
                }
                if(value.toString().equals(constString)) {
                }
                if(value.toString().equals(linearString)) {
                }
                break;
            case 2:
                segment.setDelka((int) value);
                break;
            case 3:
                segment.setProcento((int) value);
                break;
        }
    }

    public void setListSegment(List<Segment> listSegment) {
        this.listSegment = listSegment;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object returnValue = null;

        Segment segment = listSegment.get(rowIndex);

        switch (columnIndex) {
            case 0:
                returnValue = rowIndex + 1;
                break;
            case 1:
                returnValue = segment.getTyp();
                break;
            case 2:
                returnValue = segment.getDelka();
                break;
            case 3:
                returnValue = segment.getProcento();
                break;
        }
        return returnValue;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if((getValueAt(rowIndex, 1).toString().equals(constString)) && (columnIndex == sloupecekPercentage)) {
            return false;
        }
        if((getValueAt(rowIndex, 1).toString().equals(jumpString)) && (columnIndex == sloupecekDuration) ) {
            return false;
        }
        if((getValueAt(rowIndex, 1).toString().equals(apertureOnString)) && ((columnIndex == sloupecekDuration) || (columnIndex == sloupecekPercentage))) {
            return false;
        }
        if((getValueAt(rowIndex, 1).toString().equals(apertureOFFString)) && ((columnIndex == sloupecekDuration) || (columnIndex == sloupecekPercentage))) {
            return false;
        }
        return columnIndex > 0;
    }
}
