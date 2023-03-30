package main.java;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import main.java.naparovaniFinal.FinalSegmentTableModel;
import main.java.naparovaniFinal.UlozisteFinal;
import main.java.naparovaniSchema.PosledniOpened;
import main.java.naparovaniSchema.Uloziste;
import main.java.naparovaniSource.NaparovaniTableModel;
import main.java.naparovaniSource.UlozisteNaparovani;
import main.java.typeOfBoat.TypeBoat;
import main.java.typeOfBoat.TypeBoatEditor;
import main.java.typeOfBoat.TypeBoatRenderer;
import main.java.typeOfSegment.Typ;
import main.java.typeOfSegment.TypEditor;
import main.java.typeOfSegment.TypRenderer;
import main.java.naparovaniSchema.SegmentTableModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OknoBasicLook extends JFrame implements KeyListener {
    public JFrame frame;
    private JPanel panelCanva;
    private JButton buttonStart;
    private JButton buttonStop;
    private JButton buttonPause;
    private JButton buttonUp;
    private JButton buttonDown;
    private JButton buttonAdd;
    private JButton buttonDelete;
    private JButton buttonInfo;
    private JPanel mainPanel;
    private JTable table;
    private JProgressBar progressBar1;
    private JLabel jlabelDuration;
    private JLabel jlabelDurationMin;
    private JLabel jlabelRemaining;
    private JLabel jlabelRemainingMin;
    private JLabel jlabelStarTime;
    private JTable tableNaparovani;
    private JScrollPane jscrollPaneTableNaparovani;
    private JButton jbuttonAddNaparovani;
    private JButton jbuttonCopy;
    private JButton jbuttonDeleteNaparovani;
    private JRadioButton system1RadioButton;
    private JRadioButton system2RadioButton;
    private JRadioButton turnONRadioButton;
    private JRadioButton turnOFFRadioButton;
    private JButton CONNECTButton;
    private JButton DISCONNECTButton;
    private JRadioButton externalControlRadioButton;
    private JRadioButton internalControlRadioButton;
    private JButton openButton;
    private JTextField textFieldSourceRowName;
    private JTextField textFieldSourceRowAuthor;
    private JTextField textFieldSourceRowNotes;
    private JButton SAVEButton;
    private JButton DELETEButton;
    private JLabel jLabelFileNameExternal;
    private JLabel jLabelPathNameExternal;
    private JLabel statusJLabel;
    private JComboBox comboBoxCOM;
    private JButton UPLOADButton;
    private JRadioButton OPENEDRadioButton;
    private JRadioButton CLOSEDRadioButton;
    private JButton USEButton;
    private JTable tableFinal;
    private JButton useButtonExternal;
    private JButton FINDButton;
    private JLabel jlabelEndTime;
    private JButton NULLButton;
    private JPanel arduinoPanel;
    private JButton REPORTSFILEButton;
    private JButton percentButton;
    private JTextField dateTextField;
    private JTextField codeTextField;

    private JPanel InternalSourceEditor;

    private String pressure_string;


//    private final Color[] mojePaleta = {
//            new Color(45, 0, 247),
//            new Color(106, 0, 244),
//            new Color(137, 0, 242),
//            new Color(160, 0, 242),
//            new Color(177, 0, 232),
//            new Color(188, 0, 221),
//            new Color(209, 0, 209),
//            new Color(219, 0, 182),
//            new Color(229, 0, 164),
//            new Color(242, 0, 137)};

    private final Color[] mojePaleta = {
            new Color(45, 0, 247),
            new Color(45, 0, 247),
            new Color(45, 0, 247),
            new Color(45, 0, 247),
            new Color(45, 0, 247),
            new Color(45, 0, 247),
            new Color(45, 0, 247),
            new Color(45, 0, 247),
            new Color(45, 0, 247),
            new Color(45, 0, 247)};

    int progresCislo = 0;


    private PosledniOpened posledniOpened;
    private String openedFileName;
    private String canvaOpenedFile;
    private String openedFilePath;


    private int startRow = -1;
    private int endRow = -1;

    private int rowNaparovani = -1;

    private int rowFinal = -1;

    private Canva canva;

    private final int rozmerX = 1200;
    private final int rozmerY = 300;

    private final String constString = "Const";
    private final String linearString = "Linear";
    private final String jumpString = "Jump";
    private final String apertureOnString = "OPEN APERTURE";
    private final String apertureOFFString = "CLOSE APERTURE";

    private final int sloupecekId = 0;
    private final int sloupecekType = 1;
    private final int sloupecekDuration = 2;
    private final int sloupecekPercentage = 3;

    private Uloziste uloziste;  //prava tabulka
    private final SegmentTableModel tableModel;

    private final UlozisteNaparovani ulozisteNaparovani; // leva tabulka
    private final NaparovaniTableModel tableModelNaparovani;

    private final UlozisteFinal ulozisteFinal; // dolni tabulka
    private final FinalSegmentTableModel finalSegmentTableModel;

    private SerialPort[] ports;
    private SerialPort sp;
    private InputStream inputStream;
    private OutputStream outputStream;
    private String portName = null;

    private final Timer timer;

    private SimpleDateFormat formatter = new SimpleDateFormat("ddMMHHmm");
    private SimpleDateFormat formatterDelsi = new SimpleDateFormat("dd.MM.YYYY__HH_mm");
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
    private Date now;

    private SimpleDateFormat datovyFormat = new SimpleDateFormat("dd-mm-yyyy_HH-mm");

    private Thread finalThread;

    private String nacet = "";

    private boolean poprve = true;
    private boolean pripojenoArduino = false;

    private SteamingHistoryHandler steamingHistoryHandler;

    File finalReportfile;
    BufferedWriter finalReportWriter;

    private boolean pocitacem = false;

    private int pozicePattern = -1;
    private int poziceFinal = -1;

    private int poziceLocalJez = -1;

    private int ostreIdFinal;
    private int ostreIdPattern;

    private TypRenderer typRenderer;
    private IdRenderer idRendererPattern;
    private IdRenderer idRendererFinal;
    private TypeBoatRenderer typeBoatRenderer;


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public OknoBasicLook() throws InterruptedException {
        super("UNIVEX 450");
        setContentPane(this.mainPanel);
        this.addKeyListener(this);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                switch (e.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_F1:
                                errorViewer();
                        }
                        break;
                    case KeyEvent.KEY_RELEASED:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });


        try {
            setIconImage(ImageIO.read(new File("res/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Typ> listTyp = new ArrayList<>();
        listTyp.add(new Typ(linearString));
        listTyp.add(new Typ(jumpString));
        listTyp.add(new Typ(constString));
        listTyp.add(new Typ(apertureOnString));
        listTyp.add(new Typ(apertureOFFString));

        List<TypeBoat> listTypeBoat = new ArrayList<>();
        listTypeBoat.add(new TypeBoat("1"));
        listTypeBoat.add(new TypeBoat("2"));

        posledniOpened = new PosledniOpened();
        openedFileName = posledniOpened.getNameFile();
        canvaOpenedFile = openedFileName;

        ///////////////////////////////////////////////////////////////////// Prava Tabulka

        uloziste = new Uloziste(openedFileName);
        tableModel = new SegmentTableModel(uloziste.fillTableModel());
        table.setModel(tableModel);
        table.setRowHeight(25);

        ///////////////////////////////////////////////////////////////////// Leva Tabulka

        ulozisteNaparovani = new UlozisteNaparovani();
        tableModelNaparovani = new NaparovaniTableModel(ulozisteNaparovani.fillTableModel());
        tableNaparovani.setModel(tableModelNaparovani);
        tableNaparovani.setRowHeight(25);

        ///////////////////////////////////////////////////////////////////// Dolni tabulka
        ulozisteFinal = new UlozisteFinal();
        finalSegmentTableModel = new FinalSegmentTableModel(ulozisteFinal.fillTableModel());
        tableFinal.setModel(finalSegmentTableModel);
        tableFinal.setRowHeight(25);

        statusJLabel.setForeground(new Color(255, 23, 68)); // arduino status Con/Discon

        idRendererPattern = new IdRenderer();
        typRenderer = new TypRenderer();
        idRendererPattern.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(tableModel.getColumnClass(0), idRendererPattern);
        table.setDefaultRenderer(Typ.class, typRenderer);
        table.setDefaultEditor(Typ.class, new TypEditor(listTyp));
        table.setRowHeight(25);

        idRendererFinal = new IdRenderer();
        typeBoatRenderer = new TypeBoatRenderer();
        idRendererFinal.setHorizontalAlignment(SwingConstants.CENTER);
        tableFinal.setDefaultRenderer(Integer.class, idRendererFinal);
        tableFinal.setDefaultRenderer(String.class, idRendererFinal);
        tableFinal.setDefaultRenderer(TypeBoat.class, typeBoatRenderer);
        tableFinal.setDefaultEditor(TypeBoat.class, new TypeBoatEditor(listTypeBoat));
        tableFinal.setRowHeight(25);

        ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            comboBoxCOM.addItem(port.getSystemPortName()); //XOX
        }
        comboBoxCOM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    portName = comboBoxCOM.getSelectedItem().toString();
                } catch (NullPointerException ex) {
                }
            }
        });

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (sp.isOpen()) {
                        JOptionPane.showMessageDialog(frame,
                                "Please, disconnect Arduino!",
                                "Arduino Error",
                                JOptionPane.ERROR_MESSAGE); return;
                    }
                } catch (Exception eh) {}

                uloziste.saveJTableToTxt((SegmentTableModel) table.getModel());

                ulozisteNaparovani.saveJTableToTxt((NaparovaniTableModel) tableNaparovani.getModel());

                ulozisteFinal.saveJTableToTxt((FinalSegmentTableModel) tableFinal.getModel());

                timer.stop();

                posledniOpened.ulozPosledni(openedFileName);

                setVisible(false);

                dispose();
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        buttonStart.setBackground(new Color(0, 128, 0));
        buttonStart.setForeground(Color.white);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (statusJLabel.getText().equals("CONNECTED")) {
                    if (finalSegmentTableModel.getRowCount() > 0) {
                        String[] options = {"OK"};
                        JPanel panel = new JPanel();
                        JLabel lbl = new JLabel("Enter PRESSURE VALUE: ");
                        JTextField txt = new JTextField(15);
                        panel.add(lbl);
                        panel.add(txt);

                        int selectedOption = JOptionPane.showOptionDialog(null, panel, "PRESSURE", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        if (selectedOption == 0) {
                            pressure_string = txt.getText();
                            timer.stop();
                            grafikaRadioButton(false);
                            finalThread = new Thread(new StartNaparovani());
                            finalThread.start();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "No patterns to be evaporated!",
                                "Final Evaporation Table Error",
                                JOptionPane.ERROR_MESSAGE);
                    }


                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Arduino must be connected!",
                            "Arduino Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        buttonStop.setBackground(new Color(193, 18, 31));
        buttonStop.setForeground(Color.WHITE);
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(statusJLabel.getText().equals("CONNECTED")) poslatDoArduina("x");
            }
        });

        NULLButton.setBackground(new Color(193, 18, 31));
        NULLButton.setForeground(Color.white);
        NULLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ulozisteFinal.nullIt((FinalSegmentTableModel) tableFinal.getModel());

                tableFinal.clearSelection();
                rowFinal = -1;
                updateTableView();
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        buttonUp.setBackground(new Color(0,0,0));
        buttonUp.setForeground(Color.white);
        buttonUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startRow != -1) {
                    uloziste.moveRowUp((SegmentTableModel) table.getModel(), startRow);
                    if (startRow > 0) {
                        startRow--;
                        endRow = startRow;
                        table.setRowSelectionInterval(startRow, startRow);
                    }
                    updateTableView();
                    return;
                }
                JOptionPane.showMessageDialog(frame,
                        "The ROW must be chosen.",
                        "Steaming Pattern Warning",
                        JOptionPane.WARNING_MESSAGE);

            }
        });

        buttonDown.setBackground(new Color(0,0,0));
        buttonDown.setForeground(Color.white);
//        buttonDown.setBackground(new Color(212, 215, 0));
        buttonDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startRow != -1) {
                    uloziste.moveRowDown((SegmentTableModel) table.getModel(), startRow);
                    if (startRow < (table.getModel().getRowCount() - 1)) {
                        startRow++;
                        endRow = startRow;
                        table.setRowSelectionInterval(startRow, startRow);
                    }
                    updateTableView();
                    return;
                }
                JOptionPane.showMessageDialog(frame,
                        "The ROW must be chosen.",
                        "Steaming Pattern Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        buttonDelete.setBackground(new Color(0,0,0));
        buttonDelete.setForeground(Color.white);
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((startRow == -1) || (endRow == -1)) {
                    JOptionPane.showMessageDialog(frame,
                            "The ROW must be chosen.",
                            "Steaming Pattern Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                table.clearSelection();
                System.out.println(startRow + " " + endRow);
                uloziste.deleteRows((SegmentTableModel) table.getModel(), startRow, endRow);
                try {
                    if (uloziste.pocetRadkuFilu() == 1) {
                        uloziste.addRowEnd((SegmentTableModel) table.getModel());
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                updateTableView();
                startRow = -1;
                endRow = -1;

            }
        });

        buttonAdd.setBackground(new Color(0,0,0));
        buttonAdd.setForeground(Color.white);
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uloziste.addRowEnd((SegmentTableModel) table.getModel());
                updateTableView();
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startRow = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(startRow, startRow);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int cislo = table.rowAtPoint(e.getPoint());
                if ((cislo >= 0) && (cislo < tableModel.getRowCount())) {
                    endRow = cislo;
                } else {
                    endRow = tableModel.getRowCount() - 1;
                    table.setRowSelectionInterval(0, endRow);
                }
            }
        });

        table.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("tableCellEditor".equals(evt.getPropertyName())) {
                    if (table.isEditing())
                        processEditingStarted();
                    else
                        processEditingStopped();
                }
            }
        });
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        tableNaparovani.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                rowNaparovani = tableNaparovani.rowAtPoint(point);
                tableNaparovani.setRowSelectionInterval(rowNaparovani, rowNaparovani);

                openedFileName = tableNaparovani.getModel().getValueAt(rowNaparovani, 4).toString();
                canvaOpenedFile = openedFileName;

                uloziste = new Uloziste(openedFileName);
                tableModel.setListSegment(uloziste.fillTableModel());
                posledniOpened.ulozPosledni(openedFileName);

                textFieldSourceRowName.setText(tableNaparovani.getModel().getValueAt(rowNaparovani, 1).toString());
                textFieldSourceRowAuthor.setText(tableNaparovani.getModel().getValueAt(rowNaparovani, 2).toString());
                textFieldSourceRowNotes.setText(tableNaparovani.getModel().getValueAt(rowNaparovani, 3).toString());


                table.clearSelection();
                startRow = -1;
                endRow = -1;

                tableFinal.clearSelection();
                rowFinal = -1;

                updateTableView();
            }
        });

        tableNaparovani.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("tableCellEditor".equals(evt.getPropertyName())) {
                    if (tableNaparovani.isEditing())
                        processEditingStartedNaparovani();
                    else
                        processEditingStoppedNaparovani();
                }
            }
        });
        tableNaparovani.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        tableFinal.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                rowFinal = tableFinal.rowAtPoint(point);
                tableFinal.setRowSelectionInterval(rowFinal, rowFinal);

                openedFileName = tableFinal.getModel().getValueAt(rowFinal, 6).toString();
                canvaOpenedFile = openedFileName;

                uloziste = new Uloziste(openedFileName);
                tableModel.setListSegment(uloziste.fillTableModel());

                table.clearSelection();
                startRow = -1;
                endRow = -1;

                tableNaparovani.clearSelection();
                rowNaparovani = -1;
                updateTableView();
            }
        });

        tableFinal.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("tableCellEditor".equals(evt.getPropertyName())) {
                    if (tableFinal.isEditing())
                        processEditingStartedFinal();
                    else
                        processEditingStoppedFinal();
                }
            }
        });
        tableFinal.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


        jbuttonAddNaparovani.setBackground(new Color(193, 18, 31));
        jbuttonAddNaparovani.setForeground(Color.white);
        jbuttonAddNaparovani.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ulozisteNaparovani.addRowEnd((NaparovaniTableModel) tableNaparovani.getModel());
                updateTableView();
            }
        });

        jbuttonCopy.setBackground(new Color(0, 0, 0));
        jbuttonCopy.setForeground(Color.white);
        jbuttonCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rowNaparovani == -1) {
                    JOptionPane.showMessageDialog(frame,
                            "The ROW must be chosen.",
                            "Internal Source Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    ulozisteNaparovani.copyRow((NaparovaniTableModel) tableNaparovani.getModel(), rowNaparovani);
                    uloziste.copyOpened(tableModelNaparovani.getValueAt(rowNaparovani + 1, 4).toString());
                    updateTableView();
                }
            }
        });

        SAVEButton.setBackground(new Color(0, 0, 0));
        SAVEButton.setForeground(Color.WHITE);
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(
                        frame,
                        "Are You sure to save it?",
                        "Internal Source Option",
                        JOptionPane.YES_NO_OPTION);
                if (n == 0) {

                    if (textFieldSourceRowName.getText().equals("")) {
                        tableNaparovani.getModel().setValueAt("-", rowNaparovani, 1);
                    } else {
                        tableNaparovani.getModel().setValueAt(textFieldSourceRowName.getText(), rowNaparovani, 1);
                    }

                    if (textFieldSourceRowAuthor.getText().equals("")) {
                        tableNaparovani.getModel().setValueAt("-", rowNaparovani, 2);
                    } else {
                        tableNaparovani.getModel().setValueAt(textFieldSourceRowAuthor.getText(), rowNaparovani, 2);
                    }

                    if (textFieldSourceRowNotes.getText().equals("")) {
                        tableNaparovani.getModel().setValueAt("-", rowNaparovani, 3);
                    } else {
                        tableNaparovani.getModel().setValueAt(textFieldSourceRowNotes.getText(), rowNaparovani, 3);
                    }

                    processEditingStoppedNaparovani();
                    updateTableView();
                }
            }
        });

        DELETEButton.setBackground(new Color(0, 0, 0));
        DELETEButton.setForeground(Color.WHITE);
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(
                        frame,
                        "Are You sure to delete it?",
                        "Internal Source Option",
                        JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    if (tableModelNaparovani.getRowCount() == 1) {
                        JOptionPane.showMessageDialog(frame,
                                "There must be at least one source!",
                                "Internal Source Option",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (rowNaparovani > 0) {
                        ulozisteFinal.nullIt((FinalSegmentTableModel) tableFinal.getModel());
                        updateTableView();

                        rowNaparovani--;
                        tableNaparovani.setRowSelectionInterval(rowNaparovani, rowNaparovani);

                        String keSmazani = openedFileName;
                        openedFileName = (String) tableNaparovani.getModel().getValueAt(rowNaparovani, 4);

                        ulozisteNaparovani.removeRow((NaparovaniTableModel) tableNaparovani.getModel(), rowNaparovani + 1);

                        uloziste = new Uloziste(openedFileName);
                        tableModel.setListSegment(uloziste.fillTableModel());
                        posledniOpened.ulozPosledni(openedFileName);
                        processEditingStoppedNaparovani();
                        updateTableView();

                        ulozisteNaparovani.deleteTXT(keSmazani);
                    }
                }
            }
        });

        USEButton.setBackground(new Color(193, 18, 31));
        USEButton.setForeground(Color.white);
        USEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rowNaparovani != -1) {
                    ulozisteFinal.add((FinalSegmentTableModel) tableFinal.getModel(), tableNaparovani.getModel().getValueAt(rowNaparovani, 1).toString(), "Internal", tableNaparovani.getModel().getValueAt(rowNaparovani, 4).toString());
                    updateTableView();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "You must choose some internal source!",
                            "Internal Source Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


        canva = new Canva(rozmerX, rozmerY, (SegmentTableModel) table.getModel());
        updateTableView();
        panelCanva.add(canva);
        canva.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        system1RadioButton.setHorizontalTextPosition(SwingConstants.LEFT);
        system1RadioButton.setSelected(true);
        system1RadioButton.setEnabled(false);
        system1RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                poslatDoArduina("v1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

        system2RadioButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        system2RadioButton.setEnabled(false);
        system2RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                poslatDoArduina("v2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

        turnONRadioButton.setHorizontalTextPosition(SwingConstants.LEFT);
        turnONRadioButton.setEnabled(false);
        turnONRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                poslatDoArduina("z1");
                OPENEDRadioButton.setEnabled(true);
                CLOSEDRadioButton.setEnabled(true);
            }
        });

        turnOFFRadioButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        turnOFFRadioButton.setSelected(true);
        turnOFFRadioButton.setEnabled(false);
        turnOFFRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                poslatDoArduina("z0");

                OPENEDRadioButton.setEnabled(false);
                CLOSEDRadioButton.setEnabled(false);
            }
        });

        externalControlRadioButton.setHorizontalTextPosition(SwingConstants.LEFT);
        externalControlRadioButton.setSelected(true);
        externalControlRadioButton.setEnabled(false);
        externalControlRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                poslatDoArduina("v1");
                poslatDoArduina("e1");
                poslatDoArduina("z1");
                poslatDoArduina("c0");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                poslatDoArduina("z0");

                CLOSEDRadioButton.setSelected(true);
                turnOFFRadioButton.setSelected(true);
                system1RadioButton.setSelected(true);

                OPENEDRadioButton.setEnabled(false);
                CLOSEDRadioButton.setEnabled(false);
                system1RadioButton.setEnabled(true);
                system2RadioButton.setEnabled(true);
                turnONRadioButton.setEnabled(true);
                turnOFFRadioButton.setEnabled(true);

            }
        });

        internalControlRadioButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        internalControlRadioButton.setEnabled(false);
        internalControlRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                poslatDoArduina("c2");
                poslatDoArduina("v0");
                poslatDoArduina("e0");

                OPENEDRadioButton.setEnabled(false);
                CLOSEDRadioButton.setEnabled(false);
                system1RadioButton.setEnabled(false);
                system2RadioButton.setEnabled(false);
                turnONRadioButton.setEnabled(false);
                turnOFFRadioButton.setEnabled(false);
            }
        });

        OPENEDRadioButton.setHorizontalTextPosition(SwingConstants.LEFT);
        OPENEDRadioButton.setEnabled(false);
        OPENEDRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                poslatDoArduina("c1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

        CLOSEDRadioButton.setEnabled(false);
        CLOSEDRadioButton.setSelected(true);
        CLOSEDRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                poslatDoArduina("c0");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

        ButtonGroup groupSystem = new ButtonGroup();
        groupSystem.add(system1RadioButton);
        groupSystem.add(system2RadioButton);

        ButtonGroup groupTurn = new ButtonGroup();
        groupTurn.add(turnONRadioButton);
        groupTurn.add(turnOFFRadioButton);

        ButtonGroup groupControl = new ButtonGroup();
        groupControl.add(externalControlRadioButton);
        groupControl.add(internalControlRadioButton);

        ButtonGroup groupClona = new ButtonGroup();
        groupClona.add(OPENEDRadioButton);
        groupClona.add(CLOSEDRadioButton);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        CONNECTButton.setBackground(new Color(173, 181, 189));
        CONNECTButton.setForeground(Color.WHITE);
        CONNECTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (portName == null) {
                    JOptionPane.showMessageDialog(frame,
                            "The Arduino PORT must be chosen.",
                            "Arduino warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    try {
                        sp = SerialPort.getCommPort(portName);
                        sp.setComPortParameters(9600, 8, 1, 0);
                        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
                        inputStream = sp.getInputStream();
                        outputStream = sp.getOutputStream();
                        sp.openPort();
                    } catch (Exception exe) {
                        JOptionPane.showMessageDialog(null, "This is NOT an Arduino COM! Change it.",
                                "Arduino Connection", JOptionPane.ERROR_MESSAGE);
                    }


                    sp.addDataListener(new SerialPortDataListener() {

                        @Override
                        public int getListeningEvents() {
                            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                        }

                        @Override
                        public void serialEvent(SerialPortEvent event) {
                            byte[] newData = event.getReceivedData();
                            for (int i = 0; i < newData.length; ++i) {
                                if ((char) newData[i] != '\n') {
                                    nacet += (char) newData[i];
                                    continue;
                                } else {
                                    if (nacet.charAt(nacet.length() - 1) == '\r') {
                                        nacet = nacet.substring(0, nacet.length() - 1);
                                    }

                                    if (!pripojenoArduino) {
                                        if (nacet.equals("UNIVEX450")) {
                                            statusJLabel.setText("CONNECTED");
                                            statusJLabel.setForeground(new Color(0, 200, 83));
                                            grafikaRadioButton(true);
                                            pripojenoArduino = true;
                                        } else {
                                            try {
                                                inputStream.close();
                                                outputStream.close();
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
                                            sp.removeDataListener();
                                            sp.closePort();

                                            JOptionPane.showMessageDialog(null, "This is NOT an Arduino COM! Change it.",
                                                    "Arduino Connection", JOptionPane.ERROR_MESSAGE);
                                            return;
                                        }
                                    }


                                    switch (nacet.charAt(0)) {
                                        case 'u': //konecNaparovani
                                            poziceFinal = -1;
                                            idRendererFinal.setOstreIdPattern(poziceFinal);
                                            typeBoatRenderer.setOstreIdPattern(poziceFinal);

                                            pozicePattern = -1;
                                            idRendererPattern.setOstreIdPattern(pozicePattern);
                                            typRenderer.setOstreIdPattern(pozicePattern);
                                             updateTableView();

                                            grafikaRadioButton(true);
                                            turnONRadioButton.setSelected(false);
                                            turnOFFRadioButton.setSelected(true);

                                            progressBar1.setString("READY TO EVAP");
                                            poprve = true;
                                            steamingHistoryHandler.dopis(" -");
                                            steamingHistoryHandler.killHistory();
                                            timer.start();
                                            try {
                                                finalReportWriter.close();
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
                                            break;
                                        case 'v':
                                            if (nacet.charAt(1) == '1') {
                                                system2RadioButton.setSelected(false);
                                                system1RadioButton.setSelected(true);
                                            }
                                            if (nacet.charAt(1) == '2') {
                                                system1RadioButton.setSelected(false);
                                                system2RadioButton.setSelected(true);
                                            }
                                            break;
                                        case 'o':
                                            CLOSEDRadioButton.setSelected(false);
                                            OPENEDRadioButton.setSelected(true);
                                            break;
                                        case 'c':
                                            OPENEDRadioButton.setSelected(false);
                                            CLOSEDRadioButton.setSelected(true);
                                            break;
                                        case 'q':
                                            if (poprve) {
                                                progressBar1.setString("DOWNLOADING DATA");
                                                progressBar1.setValue(200 / 3);
                                                poprve = false;
                                            }
                                            try {
                                                finalReportWriter.write("\n" + nacet.substring(1));
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
                                            break;
                                        case 'e':
                                            steamingHistoryHandler.dopis(" " + nacet.substring(1));
                                            break;
                                        case '-':
                                            minus();
                                            break;
                                        case '+':
                                            plus();
                                            break;
                                    }
                                    nacet = "";
                                }
                            }
                        }
                    });
                } catch (Exception exception) {
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                poslatDoArduina("*");
            }
        });

        DISCONNECTButton.setBackground(new Color(255, 255, 255));
        DISCONNECTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    poslatDoArduina("c2");
                    Thread.sleep(500);
                    poslatDoArduina("e0");
                    Thread.sleep(500);
                    poslatDoArduina("v1");
                    Thread.sleep(500);
                    poslatDoArduina("z0");
                    Thread.sleep(500);


                    sp.removeDataListener();
                    sp.closePort();
                    pripojenoArduino = false;
                    statusJLabel.setText("DISCONNECTED");
                    statusJLabel.setForeground(new Color(255, 23, 68));
                    grafikaRadioButton(false);
                } catch (Exception sde) {
                }
            }
        });

        FINDButton.setBackground(new Color(0, 0, 0));
        FINDButton.setForeground(Color.white);
        FINDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxCOM.removeAllItems();
                ports = SerialPort.getCommPorts();
                for (SerialPort port : ports) {
                    comboBoxCOM.addItem(port.getSystemPortName());
                }
            }
        });

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jlabelStarTime.setText(getCurrentTimeStamp());
                jlabelEndTime.setText(sdf.format(new Date(now.getTime() + uloziste.sumaDuration(tableModel))));

                progressBar1.setForeground(mojePaleta[progresCislo]);
                progressBar1.setValue((progresCislo + 1) * 10);
                progresCislo++;
                if (progresCislo == 10) progresCislo = 0;

            }
        });
        timer.start();

        REPORTSFILEButton.setForeground(new Color(255, 255, 255));
        REPORTSFILEButton.setBackground(Color.BLACK);
        REPORTSFILEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File("res/export"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });




        progressBar1.setStringPainted(true);
        progressBar1.setForeground(Color.blue);
        progressBar1.setValue(0);
        progressBar1.setString("READY to EVAP");



        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 600));

        updateTableView();
    }

    private void plus() {
        poziceFinal++;
        idRendererFinal.setOstreIdPattern(poziceFinal);
        typeBoatRenderer.setOstreIdPattern(poziceFinal);

        pozicePattern = -1;
        idRendererPattern.setOstreIdPattern(pozicePattern);
        typRenderer.setOstreIdPattern(pozicePattern);

        openedFileName = tableFinal.getModel().getValueAt(poziceFinal, 6).toString();
        canvaOpenedFile = openedFileName;

        uloziste = new Uloziste(openedFileName);
        tableModel.setListSegment(uloziste.fillTableModel());

        updateTableView();
    }

    private void minus() {
        pozicePattern++;
        idRendererPattern.setOstreIdPattern(pozicePattern);
        typRenderer.setOstreIdPattern(pozicePattern);
        updateTableView();
    }

    private void poslatDoArduina(String mesg) {
        String m = mesg + "\n";

        try {
            outputStream.write(m.getBytes(StandardCharsets.UTF_8));
            Thread.sleep(100);
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
            System.out.println("Problem v posilani do arduina!!");
        }
    }

    protected void processEditingStopped() {
        updateTableView();
    }

    protected void processEditingStarted() {
    }

    protected void processEditingStoppedNaparovani() {
        ulozisteNaparovani.saveJTableToTxt((NaparovaniTableModel) tableNaparovani.getModel());
    }

    protected void processEditingStartedNaparovani() {
    }

    protected void processEditingStoppedFinal() {
        ulozisteFinal.saveJTableToTxt((FinalSegmentTableModel) tableFinal.getModel());
    }

    protected void processEditingStartedFinal() {
    }

    private void updateTableView() {

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableFinal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableNaparovani.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        table.revalidate();
        table.repaint();

        tableNaparovani.revalidate();
        tableNaparovani.repaint();

        tableFinal.revalidate();
        tableFinal.repaint();

        prepocitejProcenta((SegmentTableModel) table.getModel());

        table.revalidate();
        table.repaint();

        canva.setMod(CanvaModchecker((SegmentTableModel) table.getModel()));
        canva.updateCanvu((SegmentTableModel) table.getModel(), canvaOpenedFile);

        table.getColumnModel().getColumn(0).setPreferredWidth(1);
        tableNaparovani.getColumnModel().getColumn(0).setPreferredWidth(1);
        tableFinal.getColumnModel().getColumn(0).setPreferredWidth(1);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tableFinal.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tableNaparovani.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    private int CanvaModchecker(SegmentTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((model.getValueAt(i, 1).toString().equals(linearString)) && ((int) model.getValueAt(i, sloupecekDuration) == 0)) {
                return 2;
            }
            if (((int) model.getValueAt(i, sloupecekPercentage) > 100) || ((int) model.getValueAt(i, sloupecekPercentage) < 0)) {
                return 1;
            }
        }
        return 0;
    }

    private void prepocitejProcenta(SegmentTableModel model) {
        boolean prvni = false;
        boolean druha = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 1).toString().equals(constString) || model.getValueAt(i, 1).toString().equals(apertureOnString) || model.getValueAt(i, 1).toString().equals(apertureOFFString)) {
                if (i > 0) {
                    model.setValueAt(model.getValueAt(i - 1, 3), i, 3);
                }
            }
        }
    }

    public void grafikaRadioButton(boolean enabled) {
        if (enabled) {
            system1RadioButton.setEnabled(true);
            system2RadioButton.setEnabled(true);
            turnONRadioButton.setEnabled(true);
            turnOFFRadioButton.setEnabled(true);
            externalControlRadioButton.setEnabled(true);
            internalControlRadioButton.setEnabled(true);
            OPENEDRadioButton.setEnabled(false);
            CLOSEDRadioButton.setEnabled(false);
        } else {
            system1RadioButton.setEnabled(false);
            system2RadioButton.setEnabled(false);
            turnONRadioButton.setEnabled(false);
            turnOFFRadioButton.setEnabled(false);
            externalControlRadioButton.setEnabled(false);
            internalControlRadioButton.setEnabled(false);
            OPENEDRadioButton.setEnabled(false);
            CLOSEDRadioButton.setEnabled(false);
        }
    }

    public String getCurrentTimeStamp() {
        now = new Date();
        String strDate = sdf.format(now);
        return strDate;
    }

    public static void main(String[] args) throws InterruptedException {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new OknoBasicLook();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_F1) System.out.println("F1");
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        System.out.println("hgfd");
    }

    private class StartNaparovani implements Runnable {

        @Override
        public void run() {
            try {
                progressBar1.setString("PREPARING");
                turnOFFRadioButton.setSelected(false);
                turnONRadioButton.setSelected(true);

                String f = formatter.format(new Date());
                String fDelsi = formatterDelsi.format(new Date());

                steamingHistoryHandler = new SteamingHistoryHandler();
                steamingHistoryHandler.dopis(fDelsi + " -");

                poslatDoArduina("n");
                poslatDoArduina(prevodnikDataDoKodu(f));

                finalReportfile = new File("res/export/" + fDelsi + ".txt");
                finalReportWriter = new BufferedWriter(new FileWriter(finalReportfile));


                for (int finalRow = 0; finalRow < finalSegmentTableModel.getRowCount(); finalRow++) {
                    progressBar1.setString("UPLOADING " + finalRow + "/" + finalSegmentTableModel.getRowCount());

                    finalReportWriter.newLine();
                    finalReportWriter.write("Id: " +
                            finalSegmentTableModel.getValueAt(finalRow, 0) + "/Name: " +
                            finalSegmentTableModel.getValueAt(finalRow, 1) + "/Note: " +
                            finalSegmentTableModel.getValueAt(finalRow, 2) + "/Boat: " +
                            finalSegmentTableModel.getValueAt(finalRow, 3) + "/Cycles: " +
                            finalSegmentTableModel.getValueAt(finalRow, 4) + "/Before Delay: " +
                            finalSegmentTableModel.getValueAt(finalRow, 5) + "/Identifier: " +
                            finalSegmentTableModel.getValueAt(finalRow, 6));

                    if (vratOdsun((String) finalSegmentTableModel.getValueAt(finalRow, 5)) != "0") { //zpozdeni Pred Kazdym radkem
                        poslatDoArduina("s " + vratOdsun((String) finalSegmentTableModel.getValueAt(finalRow, 5)));
                    }

                    poslatDoArduina("v" + finalSegmentTableModel.getValueAt(finalRow, 3).toString()); //urci lodicku

                    for (int pocetOPakovani = 1; pocetOPakovani <= (int) finalSegmentTableModel.getValueAt(finalRow, 4); pocetOPakovani++) {
                        progressBar1.setValue((pocetOPakovani - 1) * 100 / (int) finalSegmentTableModel.getValueAt(finalRow, 4));

                        uloziste = new Uloziste(finalSegmentTableModel.getValueAt(finalRow, 6).toString());
                        tableModel.setListSegment(uloziste.fillTableModel());
                        updateTableView();
                        finalReportWriter.newLine();

                        for (int row = 0; row < tableModel.getRowCount(); row++) {
                            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                                switch (col) {
                                    case 0:
                                        finalReportWriter.write(Integer.toString((Integer) tableModel.getValueAt(row, col)));
                                        break;
                                    case 1:
                                        finalReportWriter.write(String.valueOf(tableModel.getValueAt(row, col)));
                                        switch (tableModel.getValueAt(row, col).toString()) {
                                            case jumpString:
                                                poslatDoArduina("j " + procentoDoArduina((int) tableModel.getValueAt(row, sloupecekPercentage)));
                                                break;
                                            case apertureOnString:
                                                poslatDoArduina("o");
                                                break;
                                            case apertureOFFString:
                                                poslatDoArduina("c");
                                                break;
                                            case constString:
                                                poslatDoArduina("k " + (int) tableModel.getValueAt(row, sloupecekDuration));
                                                break;
                                            case linearString:
                                                int y0;
                                                if (row == 0) {
                                                    y0 = 0;
                                                } else {
                                                    y0 = (int) tableModel.getValueAt(row - 1, sloupecekPercentage);
                                                }
                                                int y1 = (int) tableModel.getValueAt(row, sloupecekPercentage);
                                                poslatDoArduina("l " + formatujNaTriCifry(procentoDoArduina(y0)) + " " + formatujNaTriCifry(procentoDoArduina(y1)) + " " + tableModel.getValueAt(row, sloupecekDuration));
                                                break;
                                        }
                                        break;
                                    case 2:
                                        finalReportWriter.write(Integer.toString((Integer) tableModel.getValueAt(row, col)));
                                        break;
                                    case 3:
                                        finalReportWriter.write(Integer.toString((Integer) tableModel.getValueAt(row, col)));
                                        break;
                                    case 4:
                                        finalReportWriter.write(Integer.toString((Integer) tableModel.getValueAt(row, col)));
                                        break;
                                }
                                if ((tableModel.getColumnCount() - 1) != col) {
                                    finalReportWriter.write("/");
                                }
                            }
                            if ((tableModel.getRowCount() - 1) != row) {
                                finalReportWriter.newLine();
                            }
                        }
                    }
                }

                progressBar1.setValue(100 / 3);
                progressBar1.setString("EVAPORATING");

                finalReportWriter.newLine();
                finalReportWriter.newLine();
                finalReportWriter.write("Pressure: " + pressure_string + " mbar");

                finalReportWriter.newLine();
                finalReportWriter.newLine();

                openedFileName = tableFinal.getModel().getValueAt(0, 6).toString();
                canvaOpenedFile = openedFileName;

                uloziste = new Uloziste(openedFileName);
                tableModel.setListSegment(uloziste.fillTableModel());

                table.clearSelection();
                startRow = -1;
                endRow = -1;
                tableNaparovani.clearSelection();
                rowNaparovani = -1;
                tableFinal.clearSelection();
                rowFinal = -1;
                updateTableView();

                Thread.sleep(500);

                poslatDoArduina("xx");

            } catch (Exception ex) {
                StringBuilder sb = new StringBuilder("Error: ");
                sb.append(ex.getMessage());
                sb.append("\n");
                for (StackTraceElement ste : ex.getStackTrace()) {
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
    }

    public String vratOdsun (String naRozbor) {
        String[] prvniCast = naRozbor.split("h");
        int h =  Integer.parseInt(prvniCast[0]);
        String[] druhaCast = prvniCast[1].split("m");
        int m = Integer.parseInt(druhaCast[0]);
        int s = Integer.parseInt(druhaCast[1].substring(0, druhaCast[1].length()-1));

        long naReturn = h*60*60*1000+m*60*1000+s*1000;
        return String.valueOf(naReturn);
    }


    public int procentoDoArduina(int naPrepis) {
        return 255 * naPrepis / 100;
    }

    public String formatujNaTriCifry(int naFormatovnani) {
        if (naFormatovnani >= 0 && naFormatovnani <= 9) {
            return "00" + naFormatovnani;
        }
        if (naFormatovnani >= 10 && naFormatovnani <= 99) {
            return "0" + naFormatovnani;
        }
        if (naFormatovnani >= 100) {
            return "" + naFormatovnani;
        }
        return "";
    }

    public String prevodnikDataDoKodu(String naRozbor) {
        try {
            String kod = "";
            int cislo;
            for (int i = 0; i < 4; i++) {
                cislo = Integer.parseInt(naRozbor.substring(2 * i + 0, 2 * i + 2));
                if ((cislo >= 1) && (cislo <= 26)) {
                    kod += (char) (cislo + 96);
                }
                if ((cislo >= 27) && (cislo <= 52)) {
                    kod += (char) (cislo + 38);
                }
                if ((cislo >= 53) && (cislo <= 60)) {
                    kod += (char) (cislo - 4);
                }
            }
            return kod;
        } catch (Exception exception) {

        }
        return "";
    }

    public String prevodnikKoduDoData(String naRozbor) {
        try {
            String datum = "";
            int cislo;
            for (int i = 0; i < 4; i++) {
                cislo = naRozbor.charAt(i);
                if ((cislo >= 49) && (cislo <= 56)) {
                    datum += Integer.toString(cislo + 4);
                }
                if ((cislo >= 65) && (cislo <= 90)) {
                    datum += Integer.toString(cislo - 38);
                }
                if ((cislo >= 97) && (cislo <= 122)) {
                    datum += Integer.toString(cislo - 96);
                }
                datum += "";
            }
            return datum;
        } catch (Exception e) {
        }
        return "";
    }

    public void errorViewer() {
        StringBuilder sb = new StringBuilder();
        sb.append("1. Databank Editor section -> ADD NEW. Creates new evaporation pattern.\n");
        sb.append("2. Databank Editor section -> Fill Name, Author and Note text fields and press SAVE button!\n");
        sb.append("3. Steps section -> Each row represents step in evaporation pattern.\n");
        sb.append("4. Steps editor section  -> Press ADD button to add new row (step).\n");
        sb.append("5. Steps section -> In Type column you can choose from few evaporation types:\n");
        sb.append("    Linear - linear increase or decrease of current defined by Duration (ms) and final current Percentage (%) to end at.\n");
        sb.append("    Const - constant current defined by Duration (ms).\n");
        sb.append("    Jump - jump to defined Percentage (%).\n");
        sb.append("    Open Aperture - opens aperture - EVAPORATION MODE.\n");
        sb.append("    Close Aperture - opens aperture - NONEVAPORATION MODE.\n");
        sb.append("6. Steps editor section -> After setting up evaporation pattern click USE button to add it to Final Evaporation Table.\n");
        sb.append("7. Final Evaporation section -> Fill Note column if needed, choose Boat, Cycles number of repetition and .\n");
        sb.append("    Delay sets time delay before evaporation (must be in 0h0m0s format !\n");
        sb.append("8. -> 1. For another evaporation pattern repeat from point 1.\n");
        sb.append("9. Arduino Handler section -> Use FIND button to find Arduino. Choose from combo box and click CONNECT button. (status should change to green. if not use different COM.) \n");
        sb.append("10. Final Evaporation Handler section -> Click START button to evaporate. (see progress bar)\n");
        sb.append("11. To clear Final Evaporation table click CLEAR FINAL LIST button.\n");
        sb.append("\n");

        JTextArea jta = new JTextArea(sb.toString());
        JScrollPane jsp = new JScrollPane(jta) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 400);
            }
        };

        JOptionPane.showMessageDialog(
                null, jsp, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

}
