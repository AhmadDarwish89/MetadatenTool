package main.java.de.lwv.MetadatenTool.view;


import main.java.de.lwv.MetadatenTool.controller.ToolController;
import main.java.de.lwv.MetadatenTool.controller.validation.Validation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Hauptseite extends JFrame {

    private JPanel pnlMain;
    private JSpinner IDtxt;
    private JTextField BEZEICHNUNGtxt;
    private JTextField VERSIONtxt;
    private JTextField URLtxt;
    private JComboBox VORLAGE_ART_CODEtxt;
    private JComboBox PHASE_CODEtxt;
    private JTextField TECHNISCHE_BEZEICHNUNGtxt;
    private JButton saveButton;
    private JButton ZuruckButton;
    private JPanel pnlMain2;
    private JPanel pnlMain3;
    private JPanel pnlKonfig;
    private JPanel pnlVorlage;
    private JPanel pnlMeta;
    private JPanel PnlBaum;
    private JSpinner IDMETAtxt;
    private JTextField EDOK_VORLAGE_IDtxt;
    private JComboBox META_CODEtxt;
    private JTextField VALUEtxt;

    private JButton SaveMetaButton;
    private JSpinner ID_KONFIGtxt;
    private JTextField EDOK_VORLAGE_HAUPTSCHREIBEN_IDtxt;
    private JTextField EDOK_VORLAGE_FOLGESCHREIBEN_IDtxt;
    private JSpinner REIHENFOLGE_NRtxt;
    private JSpinner ID_BAUMtxt;
    private JTextField EDOK_VORLAGENBAUM_IDtxt;
    private JTextField EDOK_VORLAGE_ID_BAUMtxt;
    private JButton SaveKonfigButton;
    private JButton SaveBaumButton;
    private JCheckBox GELOESCHT_JAtxt;
    private JCheckBox GELOESCHT_NEINtxt;
    private JCheckBox OPTIONAL_JAtxt;
    private JCheckBox OPTIONAL_NEINtxt;
    private JCheckBox VORBELEGT_JAtxt;
    private JCheckBox VORBELEGT_NEINtxt;
    private JCheckBox DATEN_UEBERGEBEN_JAtxt;
    private JCheckBox DATEN_UEBERGEBEN_NEINtxt;
    private JCheckBox HAUPTSCHREIBEN_ANHAENGEN_JAtxt;
    private JCheckBox HAUPTSCHREIBEN_ANHAENGEN_NEINtxt;

    private JButton ZuruckButton2;
    private JButton fertigButton;
    private JLabel ErrorLabel;
    private JTextArea QueryArea;
    private JButton löchen_MetaButton;
    private JButton openButton;
    private JButton löchen_BaumButton;
    private JButton löchen_konfigButton;
    private JCheckBox VALUE_JA;
    private JCheckBox VALUE_NEIN;
    private JCheckBox VALUE_ANDEERE;
    private JPanel JP1_Vorlage;
    private JButton AbfragenButton;
    private JLabel Childtabelle1;
    private JLabel MiniScreen;
    private JLabel Childtabelle2;
    private JLabel Childtabelle3;
    private JTextField ORDNERtxt;
    private JTextField FOLGESCHREIBENtxt;
    private JButton comittButton;

    static String filename ="";
    SpinnerNumberModel spinnerModel_reihnfolge = new SpinnerNumberModel(1, 1, 100, 1); // (Anfangswert, Minimum, Maximum, Schritt)
    SpinnerNumberModel spinnerModel_vorlage = new SpinnerNumberModel(0,0, Integer.MAX_VALUE, 1);
    SpinnerNumberModel spinnerModel_meta = new SpinnerNumberModel(55,0, Integer.MAX_VALUE, 1);
    SpinnerNumberModel spinnerModel_baum = new SpinnerNumberModel(55,0, Integer.MAX_VALUE, 1);

    SpinnerNumberModel spinnerModel_konfig = new SpinnerNumberModel(55,0, Integer.MAX_VALUE, 1);

    JSpinner.NumberEditor editor_vorlage = new JSpinner.NumberEditor(IDtxt, "#");
    JSpinner.NumberEditor editor_Meta = new JSpinner.NumberEditor(IDMETAtxt, "#");
    JSpinner.NumberEditor editor_baum = new JSpinner.NumberEditor(ID_BAUMtxt, "#");
    JSpinner.NumberEditor editor_konfig = new JSpinner.NumberEditor(ID_KONFIGtxt, "#");


    public Hauptseite() {
        initializeComboBox();
        setContentPane(pnlMain);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 360);

        this.setLocationRelativeTo(null);
        setVisible(true);
        setTitle(" Tool zur Verwaltung von Metadaten für Schriftstückvorlagen");

        QueryArea.setEditable(false); // Make it non-editable
        QueryArea.setWrapStyleWord(true);

        // reset Child Dialogs
        pnlMeta.setVisible(false);
        pnlKonfig.setVisible(false);
        PnlBaum.setVisible(false);
        QueryArea.setVisible(false);

        setResizable(false); // Disable resizing
        ToolController.process=false;
        Childtabelle1.setForeground(Color.gray);
        Childtabelle2.setForeground(Color.gray);
        Childtabelle3.setForeground(Color.gray);
        MiniScreen.setForeground(Color.gray);
        Childtabelle2.setVisible(false);
        REIHENFOLGE_NRtxt.setModel(spinnerModel_reihnfolge);
        IDtxt.setModel(spinnerModel_vorlage);
        IDtxt.setEditor(editor_vorlage);
        IDMETAtxt.setModel(spinnerModel_meta);
        IDMETAtxt.setEditor(editor_Meta);
        ID_BAUMtxt.setModel(spinnerModel_baum);
        ID_BAUMtxt.setEditor(editor_baum);
        ID_KONFIGtxt.setModel(spinnerModel_konfig);
        ID_KONFIGtxt.setEditor(editor_konfig);

        ToolController.ResetCounters();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                if (e.getSource() == saveButton) {
                    // Get the text from the text field
                    Childtabelle2.setVisible(false);
                    int ID = (int) IDtxt.getValue();
                    //set JSpinner Meta (Min)
                    int Number_Min = ToolController.Berechnunen_Min(ID);
                    spinnerModel_meta.setValue(Number_Min);
                    spinnerModel_baum.setValue(Number_Min);
                    spinnerModel_konfig.setValue(Number_Min);
                    //set MAX JSpinner Meta
                    int Number_Max = ToolController.Berechnunen_Max(ID);
                    spinnerModel_meta.setMaximum(Number_Max);
                    spinnerModel_baum.setMaximum(Number_Max);
                    spinnerModel_konfig.setMaximum(Number_Max);

                    String BEZEICHNUNG = BEZEICHNUNGtxt.getText();
                    String VERSION = VERSIONtxt.getText();
                    String URL = URLtxt.getText();
                    String VORLAGE_ART_CODE = "a";//VORLAGE_ART_CODEtxt.getSelectedItem().toString();
                    String PHASE_CODE = "a";//PHASE_CODEtxt.getSelectedItem().toString();

                    String GELOESCHT = "";
                    if (GELOESCHT_JAtxt.isSelected()) {
                        GELOESCHT = "J";
                    }
                    if (GELOESCHT_NEINtxt.isSelected()) {
                        GELOESCHT = "N";
                    }

                    String ANLAGE_DATUM_VORLAGE = ToolController.ANLAGE_DATUM;
                    String ANLAGE_USER_NR_VORLAGE = ToolController.ANLAGE_USER_NR;
                    String ANLAGE_ORGNR_VORLAGE = ToolController.ANLAGE_ORGNR;

                    String TECHNISCHE_BEZEICHNUNG = TECHNISCHE_BEZEICHNUNGtxt.getText();
                    EDOK_VORLAGE_IDtxt.setText(IDtxt.getValue().toString());
                    EDOK_VORLAGE_ID_BAUMtxt.setText(IDtxt.getValue().toString());
                    EDOK_VORLAGE_HAUPTSCHREIBEN_IDtxt.setText(IDtxt.getValue().toString());

                    boolean isSexnummer = Validation.isSixNummer(String.valueOf(ID));
                    boolean isNull = Validation.isIDNull(ID);
                    if (!isNull) {
                        ErrorLabel.setText("ID muss nur nummer enhalten und nicht Leer Sein");
                        return;
                    }
                    if (!isSexnummer) {
                        ErrorLabel.setText("ID muss 6 Stellen sein");
                        IDtxt.setValue(0);
                        return;
                    }
                    // Specify the file path where you want to save the text
                    filename = ToolController.getFilePath(TECHNISCHE_BEZEICHNUNG);

                    //delete File if exisits
                    File file = new File(filename);
                    Validation.deleteExistingFile(file);

                    //Write Vorlage Query
                    ToolController.write_vorlage_Query(filename, Number_Min, Number_Max, ID, BEZEICHNUNG, VERSION, URL, VORLAGE_ART_CODE, PHASE_CODE, GELOESCHT, ANLAGE_DATUM_VORLAGE, ANLAGE_USER_NR_VORLAGE, ANLAGE_ORGNR_VORLAGE, TECHNISCHE_BEZEICHNUNG);
                    DisplayKonfiguration();
                    DeaktivePnlVorlage();
                    ErrorLabel.setText("");

                    //textArae_vorlage
                    StringBuilder textAraetxt_vorlage = ToolController.getTextAraetxt();
                    QueryArea.setText(textAraetxt_vorlage.toString());

                    //Border Vorlage
                    Border redBorder = BorderFactory.createLineBorder(Color.gray);
                    // Create a titled border with the red border
                    JP1_Vorlage.setBorder(BorderFactory.createTitledBorder(redBorder, "Haupttabelle"));

                    // Konfiguration Massage Dialog
                    JLabel customLabel = new JLabel("EDOK_VORLAGE Query hat erfolgreich gespeichert!");
                    Font customFont1 = new Font("Cambria Math", Font.BOLD, 15); // Change the font as needed
                    customLabel.setFont(customFont1);
                    // Show the message dialog with the custom label
                    JOptionPane.showMessageDialog(null, customLabel, "Success", JOptionPane.INFORMATION_MESSAGE);
                    AbfragenButton.setVisible(false);

                }

         }catch (Exception error) {

                    JOptionPane.showMessageDialog(null, error.getMessage(), "ahmad ", JOptionPane.INFORMATION_MESSAGE);
                }
    }
        });

        ZuruckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Startseite().setVisible(true);
            }
        });

        SaveMetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == SaveMetaButton) {
                    // Get the text from the text field

                    int IDMETA = (int) IDMETAtxt.getValue();
                    String EDOK_VORLAGE_ID = EDOK_VORLAGE_IDtxt.getText();
                    String META_CODE ="a";// META_CODEtxt.getSelectedItem().toString();


                    String VALUE = "";
                    if (VALUE_JA.isSelected()) {
                        VALUE = "J";
                    }
                    if (VALUE_NEIN.isSelected()) {
                        VALUE = "N";
                    }
                    if (VALUE_ANDEERE.isSelected()) {
                     VALUE = VALUEtxt.getText();
                    }
                    String ANLAGE_DATUM_META = ToolController.ANLAGE_DATUM;

                    String ANLAGE_USER_NR_Meta = ToolController.ANLAGE_USER_NR;
                    String ANLAGE_ORGNR_META =  ToolController.ANLAGE_ORGNR;


                    boolean isSexnummer = Validation.isSixNummer(String.valueOf(IDMETA));
                    boolean isNull = Validation.isIDNull(IDMETA);

                    if (!isNull) {
                        ErrorLabel.setText("ID muss nur nummer enhalten und nicht Leer Sein");
                        return;
                    }
                    if (!isSexnummer) {
                        ErrorLabel.setText("ID muss 6 Stellen sein");
                        IDMETAtxt.setValue(0);

                        return;
                    }
                    ToolController.write_meta_Query(filename,  IDMETA,  EDOK_VORLAGE_ID,  META_CODE,  VALUE,  ANLAGE_DATUM_META,  ANLAGE_USER_NR_Meta,  ANLAGE_ORGNR_META);
                    ErrorLabel.setText("");

                    StringBuilder textAraetxt_meta = ToolController.getTextAraetxt();
                    QueryArea.setText(textAraetxt_meta.toString());

                    löchen_MetaButton.setEnabled(true);

                    JLabel customLabel = new JLabel("METADATUM Query hat erfolgreich gespeichert!");
                    Font customFont1 = new Font("Cambria Math", Font.BOLD, 15); // Change the font as needed
                    customLabel.setFont(customFont1);
                    JOptionPane.showMessageDialog(null, customLabel, "Success", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });


        SaveBaumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == SaveBaumButton) {
                    // Get the text from the text field

                    int ID_BAUM = (int) ID_BAUMtxt.getValue();
                    String EDOK_VORLAGENBAUM_ID = EDOK_VORLAGENBAUM_IDtxt.getText();
                    boolean isNull = Validation.isIDNull(EDOK_VORLAGENBAUM_ID);
                    if (isNull) {
                        ErrorLabel.setText("EDOK_VORLAGENBAUM_ID muss nicht leer sein ");
                        return;
                    }
                    boolean isDigit = Validation.isDigit(EDOK_VORLAGENBAUM_ID);
                    if (!isDigit) {
                        ErrorLabel.setText("EDOK_VORLAGENBAUM_ID muss Nur Nummer haben");
                        return;
                    }

                    String EDOK_VORLAGE_ID_BAUM = EDOK_VORLAGE_ID_BAUMtxt.getText();
                    String ORDNER = ORDNERtxt.getText();
                    String ANLAGE_DATUM_BAUM =  ToolController.ANLAGE_DATUM;
                    String ANLAGE_USER_NR_BAUM = ToolController.ANLAGE_USER_NR;
                    String ANLAGE_ORGNR_BAUM = ToolController.ANLAGE_ORGNR;

                    boolean isSexnummer = Validation.isSixNummer(String.valueOf(ID_BAUM));
                    if (ID_BAUM == 0) {
                        ErrorLabel.setText("ID muss Nur Nummer haben");
                        return;
                    }
                    if (!isSexnummer) {
                        ErrorLabel.setText("ID muss 6 Stellen sein");
                        ID_BAUMtxt.setValue(0);
                        return;
                    }

                    ToolController.write_baum_Query(filename, ID_BAUM, EDOK_VORLAGENBAUM_ID, EDOK_VORLAGE_ID_BAUM, ANLAGE_DATUM_BAUM, ANLAGE_USER_NR_BAUM, ANLAGE_ORGNR_BAUM, ORDNER) ;
                            ErrorLabel.setText("");

                    StringBuilder textAraetxt_meta = ToolController.getTextAraetxt();
                    QueryArea.setText(textAraetxt_meta.toString());

                     löchen_BaumButton.setEnabled(true);
                     JLabel customLabel = new JLabel("VORLAGENBAUM Query hat erfolgreich gespeichert!");
                     Font customFont1 = new Font("Cambria Math", Font.BOLD, 15); // Change the font as needed
                     customLabel.setFont(customFont1);
                     JOptionPane.showMessageDialog(null, customLabel, "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        SaveKonfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == SaveKonfigButton) {
                    // Get the text from the text field

                    int ID_KONFIG = (int) ID_KONFIGtxt.getValue();
                    String EDOK_VORLAGE_HAUPTSCHREIBEN_ID = EDOK_VORLAGE_HAUPTSCHREIBEN_IDtxt.getText();
                    String EDOK_VORLAGE_FOLGESCHREIBEN_ID = EDOK_VORLAGE_FOLGESCHREIBEN_IDtxt.getText();
                    boolean isNull = Validation.isIDNull(EDOK_VORLAGE_FOLGESCHREIBEN_ID);
                    if (isNull) {
                        ErrorLabel.setText("EDOK_VORLAGE_FOLGESCHREIBEN_ID muss nicht leer sein ");
                        return;
                    }
                    boolean isDigit = Validation.isDigit(EDOK_VORLAGE_FOLGESCHREIBEN_ID);
                    if (!isDigit) {
                        ErrorLabel.setText("EDOK_VORLAGE_FOLGESCHREIBEN_ID muss Nur Nummer haben");
                        return;
                    }


                    String OPTIONAL = "";
                    if (OPTIONAL_JAtxt.isSelected()) {
                        OPTIONAL = "J";
                    }
                    if (OPTIONAL_NEINtxt.isSelected()) {
                        OPTIONAL = "N";
                    }

                    String VORBELEGT = "";
                    if (VORBELEGT_JAtxt.isSelected()) {
                        VORBELEGT = "J";
                    }
                    if (VORBELEGT_NEINtxt.isSelected()) {
                        VORBELEGT = "N";
                    }

                    String DATEN_UEBERGEBEN = "";
                    if (DATEN_UEBERGEBEN_JAtxt.isSelected()) {
                        DATEN_UEBERGEBEN = "J";
                    }
                    if (DATEN_UEBERGEBEN_NEINtxt.isSelected()) {
                        DATEN_UEBERGEBEN = "N";
                    }

                    String HAUPTSCHREIBEN_ANHAENGEN = "";
                    if (HAUPTSCHREIBEN_ANHAENGEN_JAtxt.isSelected()) {
                        HAUPTSCHREIBEN_ANHAENGEN = "J";
                    }
                    if (HAUPTSCHREIBEN_ANHAENGEN_NEINtxt.isSelected()) {
                        HAUPTSCHREIBEN_ANHAENGEN = "N";
                    }

                    int REIHENFOLGE_NR = (int) REIHENFOLGE_NRtxt.getValue();


                    String ANLAGE_DATUM_KONFIG = ToolController.ANLAGE_DATUM;
                    String ANLAGE_USER_NR_KONFIG = ToolController.ANLAGE_USER_NR;
                    String ANLAGE_ORGNR_KONFIG = ToolController.ANLAGE_ORGNR;

                    String FOLGESCHREIBEN = FOLGESCHREIBENtxt.getText();

                    boolean isSexnummer = Validation.isSixNummer(String.valueOf(ID_KONFIG));
                    if (ID_KONFIG == 0) {
                        ErrorLabel.setText("ID muss Nur Nummer haben");
                        return;
                    }
                    if (!isSexnummer) {
                        ErrorLabel.setText("ID muss 6 Stellen sein");
                        ID_KONFIGtxt.setValue(0);
                        return;
                    }

                    ToolController.write_konfig_Query(filename,  ID_KONFIG,  EDOK_VORLAGE_HAUPTSCHREIBEN_ID,  EDOK_VORLAGE_FOLGESCHREIBEN_ID,  OPTIONAL,  VORBELEGT,  REIHENFOLGE_NR, DATEN_UEBERGEBEN, HAUPTSCHREIBEN_ANHAENGEN, ANLAGE_DATUM_KONFIG, ANLAGE_USER_NR_KONFIG, ANLAGE_ORGNR_KONFIG, FOLGESCHREIBEN);
                    ErrorLabel.setText("");

                    StringBuilder textAraetxt_meta = ToolController.getTextAraetxt();
                    QueryArea.setText(textAraetxt_meta.toString());

                    löchen_konfigButton.setEnabled(true);
                    JLabel customLabel = new JLabel("FOLGESCHREIBEN_KONFIGURATION Query hat erfolgreich gespeichert!");
                    Font customFont1 = new Font("Cambria Math", Font.BOLD, 15); // Change the font as needed
                    customLabel.setFont(customFont1);
                    JOptionPane.showMessageDialog(null, customLabel, "Success", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });

        GELOESCHT_JAtxt.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    GELOESCHT_NEINtxt.setSelected(false); // Uncheck GELOESCHT_NEIN when GELOESCHT_JA is unchecked
                }
            }
        });
        GELOESCHT_NEINtxt.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    GELOESCHT_JAtxt.setSelected(false); // Uncheck GELOESCHT_JA when GELOESCHT_NEIN is unchecked
                }
            }
        });

        OPTIONAL_JAtxt.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    OPTIONAL_NEINtxt.setSelected(false); // Uncheck OPTIONAL_NEIN when OPTIONAL_JA is unchecked
                }
            }
        });
        OPTIONAL_NEINtxt.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    OPTIONAL_JAtxt.setSelected(false); // Uncheck OPTIONAL_JA when OPTIONAL_NEIN is unchecked
                }
            }
        });

        VORBELEGT_JAtxt.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    VORBELEGT_NEINtxt.setSelected(false); // Uncheck VORBELEGT_NEIN when VORBELEGT_JA is unchecked
                }
            }
        });
        VORBELEGT_NEINtxt.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    VORBELEGT_JAtxt.setSelected(false); // Uncheck VORBELEGT_JA when VORBELEGT_NEIN is unchecked
                }
            }
        });

        DATEN_UEBERGEBEN_JAtxt.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    DATEN_UEBERGEBEN_NEINtxt.setSelected(false); // Uncheck DATEN_UEBERGEBEN_NEIN when DATEN_UEBERGEBEN_JA is unchecked
                }
            }
        });
        DATEN_UEBERGEBEN_NEINtxt.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    DATEN_UEBERGEBEN_JAtxt.setSelected(false); // Uncheck DATEN_UEBERGEBEN_JA when DATEN_UEBERGEBEN_NEIN is unchecked
                }
            }
        });

        HAUPTSCHREIBEN_ANHAENGEN_JAtxt.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    HAUPTSCHREIBEN_ANHAENGEN_NEINtxt.setSelected(false); // Uncheck HAUPTSCHREIBEN_ANHAENGEN_NEIN when HAUPTSCHREIBEN_ANHAENGEN_JA is unchecked
                }
            }
        });
        HAUPTSCHREIBEN_ANHAENGEN_NEINtxt.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    HAUPTSCHREIBEN_ANHAENGEN_JAtxt.setSelected(false); // Uncheck HAUPTSCHREIBEN_ANHAENGEN_JA when HAUPTSCHREIBEN_ANHAENGEN_NEIN is unchecked
                }
            }
        });

        ZuruckButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Hauptseite().setVisible(true);
            }
        });

        fertigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                    if (fertigButton.getText()=="Schließen") {dispose();}
                    else {
                    String message = "Diese Skript ist Fertig Jetzt !!" + "\n" +
                            "Klicken Sie auf „Ja“, um eine Datei zu öffnen.";
                    int optionType = JOptionPane.OK_OPTION;
                    int result = JOptionPane.showConfirmDialog(null, message, "Open File", optionType);
                    if (result == JOptionPane.OK_OPTION) {
                        ToolController.openFile(filename);
                    }
                    // Close all this Process and retutn to first Screen
                    saveButton.setEnabled(false);
                    SaveMetaButton.setEnabled(false);
                    SaveBaumButton.setEnabled(false);
                    SaveKonfigButton.setEnabled(false);
                    openButton.setEnabled(false);
                    löchen_MetaButton.setEnabled(false);
                    löchen_BaumButton.setEnabled(false);
                    löchen_konfigButton.setEnabled(false);
                    comittButton.setEnabled(false);
                    ZuruckButton2.setText("Neu Skript Erstellen");
                    fertigButton.setText("Schließen");
                }
            }
        });

        löchen_MetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToolController.Löschen_meta(filename);

                löchen_MetaButton.setEnabled(false);

                StringBuilder textAraetxt_meta = ToolController.getTextAraetxt();
                QueryArea.setText(textAraetxt_meta.toString());

                JLabel customLabel = new JLabel("METADATUM Query hat erfolgreich gelöcht!");
                Font customFont1 = new Font("Cambria Math", Font.BOLD, 15); // Change the font as needed
                customLabel.setFont(customFont1);
                JOptionPane.showMessageDialog(null, customLabel, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                ToolController.openFile(filename);
                try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException error) {
                    error.printStackTrace();
                }
            }
        });
        löchen_konfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToolController.Löschen_konfig(filename);
                löchen_konfigButton.setEnabled(false);
                StringBuilder textAraetxt_konfig = ToolController.getTextAraetxt();
                QueryArea.setText(textAraetxt_konfig.toString());

                JLabel customLabel = new JLabel("FOLGESCHREIBEN_KONFIGURATION Query hat Erfolgreich gelöcht!");
                Font customFont1 = new Font("Cambria Math", Font.BOLD, 15); // Change the font as needed
                customLabel.setFont(customFont1);
                JOptionPane.showMessageDialog(null, customLabel, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        löchen_BaumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToolController.Löschen_baum(filename);
                löchen_BaumButton.setEnabled(false);
                StringBuilder textAraetxt_baum = ToolController.getTextAraetxt();
                QueryArea.setText(textAraetxt_baum.toString());

                JLabel customLabel = new JLabel("VORLAGENBAUM Query hat Erfolgreich gelöcht!");
                Font customFont1 = new Font("Cambria Math", Font.BOLD, 15); // Change the font as needed
                customLabel.setFont(customFont1);
                JOptionPane.showMessageDialog(null, customLabel, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            });

        VALUE_JA.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        VALUE_NEIN.setSelected(false);// Uncheck VALUE_NEIN when VALUE_JA is unchecked
                        VALUE_ANDEERE.setSelected(false);
                    }
                }
            });
        VALUE_NEIN.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    VALUE_JA.setSelected(false);// Uncheck VALUE_JA when VALUE_NEIN is unchecked
                    VALUE_ANDEERE.setSelected(false);
                }
            }
        });
        VALUE_ANDEERE.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    VALUEtxt.setEnabled(true);
                    VALUE_JA.setSelected(false);
                    VALUE_NEIN.setSelected(false);
                }else{
                    VALUEtxt.setEnabled(false);
                }
            }
        });
        AbfragenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderPath = ToolController.getFolderPath();
                openFolder(folderPath);
            }
        });

        comittButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToolController.write_Comitted(filename);
                StringBuilder textAraetxt_feriig = ToolController.getTextAraetxt();
                QueryArea.setText(textAraetxt_feriig.toString());
                JLabel customLabel = new JLabel("Comitted_Anweisungen hat erfolgreich gespeichert!");
                Font customFont1 = new Font("Cambria Math", Font.BOLD, 15); // Change the font as needed
                customLabel.setFont(customFont1);
                JOptionPane.showMessageDialog(null, customLabel, "Success", JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }

    private void initializeComboBox() {
            DefaultComboBoxModel<String> VORLAGE_ART_CODE_COMPOBOX = ToolController.loadValuesFromIni("VORLAGE_ART_CODE");
            VORLAGE_ART_CODEtxt.setModel(VORLAGE_ART_CODE_COMPOBOX);
            VORLAGE_ART_CODEtxt.setSelectedIndex(0);

            DefaultComboBoxModel<String> COMPOBOX_PHASE_CODE = ToolController.loadValuesFromIni("PHASE_CODE");
            PHASE_CODEtxt.setModel(COMPOBOX_PHASE_CODE);
            PHASE_CODEtxt.setSelectedIndex(0);

            DefaultComboBoxModel<String> COMPOBOX_META_CODE = ToolController.loadValuesFromIni("META_CODE");
            META_CODEtxt.setModel(COMPOBOX_META_CODE);
            META_CODEtxt.setSelectedIndex(0);
        }

    public void DisplayKonfiguration(){
        setSize(900, 750);
        setLocationRelativeTo(null);
        ZuruckButton.setVisible(false);
        // open Child Dialogs
        pnlMeta.setVisible(true);
        pnlKonfig.setVisible(true);
        PnlBaum.setVisible(true);
        QueryArea.setVisible(true);
        löchen_MetaButton.setEnabled(false);
        löchen_konfigButton.setEnabled(false);
        löchen_BaumButton.setEnabled(false);
        VALUEtxt.setEnabled(false);
        Childtabelle2.setVisible(true);
        for (Component component : pnlVorlage.getComponents()) {
            component.setEnabled(false);
        }
    }

    public void DeaktivePnlVorlage() {
        IDtxt.setEnabled(false);
        BEZEICHNUNGtxt.setEnabled(false);
        VERSIONtxt.setEnabled(false);
        URLtxt.setEnabled(false);
        VORLAGE_ART_CODEtxt.setEnabled(false);
        PHASE_CODEtxt.setEnabled(false);
        GELOESCHT_JAtxt.setEnabled(false);
        GELOESCHT_NEINtxt.setEnabled(false);
        TECHNISCHE_BEZEICHNUNGtxt.setEnabled(false);
        saveButton.setEnabled(false);
    }

    private static void openFolder(String folderPath) {
        try {
            File folder = new File(folderPath);
            if (folder.exists() && folder.isDirectory()) {
                Desktop.getDesktop().open(folder);
            } else {
                System.err.println("The specified folder does not exist or is not a directory.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
                public void run() {
                            new Hauptseite().setVisible(true);
                }
            });
        }
    }
