package main.java.de.lwv.MetadatenTool.controller;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ToolController {

    static int MetaCount = 0;
    static int KonfigCount = 0;
    static int BaumCount = 0;

    static boolean toggle = false;
    static StringBuilder textAraetxt = new StringBuilder("");
    public static final String ANLAGE_USER_NR = "CCM-ADMIN";
    public static final String ANLAGE_ORGNR = "CCM-ADMIN";
    public static final String ANLAGE_DATUM = "sysdate";
    public static boolean process;


    public static StringBuilder getTextAraetxt() {
        return textAraetxt;
    }

    public static int Berechnunen_Min(int ID) {

        int existingNumber = ID / 100;
        int newNumber = 00;
        int Number_Min = (existingNumber * 100) + newNumber;

        return Number_Min;
    }

    public static int Berechnunen_Max(int ID) {

        int existingNumber = ID / 100;
        int maxNumber = 99;
        int Number_Max = (existingNumber * 100) + maxNumber;

        return Number_Max;
    }

    public static String getFolderPath() {
        // Holen des aktuellen Arbeitsverzeichnisses
        String workingDir = System.getProperty("user.dir");
        // Erstellen des Ordnerpfads (im aktuellen Verzeichnis)
        String folderPath = workingDir + File.separator + "Skripts";
        // Erstellen des Ordners, falls er nicht existiert
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folderPath;
    }

    public static String getFilePath(String filename) {

        String folderPath = getFolderPath();
        // Erstellen des Dateipfads im neuen Ordner
        String filePath = folderPath + File.separator + filename.concat(".sql");
        return filePath;
    }

    public static void write_vorlage_Query(String filePath, int Number_Min, int Number_Max, int ID, String BEZEICHNUNG, String VERSION, String URL, String VORLAGE_ART_CODE, String PHASE_CODE, String GELOESCHT, String ANLAGE_DATUM_VORLAGE, String ANLAGE_USER_NR_VORLAGE, String ANLAGE_ORGNR_VORLAGE, String TECHNISCHE_BEZEICHNUNG) {
        try {
            // Create a BufferedWriter to write to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

            // Write the text to the file

            writer.write("spool P:\\V-Modell\\CCM\\Betrieb\\Vorlagenkonfigurationen\\logs\\" + TECHNISCHE_BEZEICHNUNG + ".LOG\n" +
                    "\n" +
                    "declare \n" +
                    "  lc_anlage_user_nr  edok_vorlage.anlage_user_nr%type := 'CCM-ADMIN';\n" +
                    "  lc_anlage_orgnr    edok_vorlage.anlage_orgnr%type := 'CCM-ADMIN';\n" +
                    "    \n" +
                    "begin\n" +
                    "-- Vorlagenkonfiguration für diese Vorlage löschen\n" +
                    "delete from edok_vorlage_folgeschreiben_konfiguration where edok_vorlage_folgeschreiben_id between " + Number_Min + " and " + Number_Max + ";\n" +
                    "delete from edok_vorlagenbaum_vorlage where id between " + Number_Min + " and " + Number_Max + ";\n" +
                    "delete from edok_metadatum where id between " + Number_Min + " and " + Number_Max + ";\n" +
                    "delete from edok_vorlage where id between " + Number_Min + " and " + Number_Max + ";\n" +
                    "\n" +
                    "-- Vorlage in Tabelle edok_vorlage hinzufügen");
            writer.newLine();
            writer.write("insert into `edok_vorlage` (`id`, `bezeichnung`, `version`, 'url', 'vorlage_art_code', 'phase_code', 'geloescht', 'anlage_datum', 'anlage_user_nr', 'anlage_orgnr', 'technische_bezeichnung') \n" +
                    "values ('" + ID + "', '" + BEZEICHNUNG + "', '" + VERSION + "', '" + URL + "', '" + VORLAGE_ART_CODE + "', '" + PHASE_CODE + "', '" + GELOESCHT + "', '" + ANLAGE_DATUM_VORLAGE + "', '" + ANLAGE_USER_NR_VORLAGE + "', '" + ANLAGE_ORGNR_VORLAGE + "', '" + TECHNISCHE_BEZEICHNUNG + "');");
            writer.newLine();
            // Close the writer
            writer.close();
            addTextArea_vorlage();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving text to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public static void write_meta_Query(String filePath, int IDMETA, String EDOK_VORLAGE_ID, String META_CODE, String VALUE, String ANLAGE_DATUM_META, String ANLAGE_USER_NR_Meta, String ANLAGE_ORGNR_META) {
        Löschen_comitted(filePath);
        String MetaQuery = "insert into `edok_metadatum ` (`id`, `edok_vorlage_id`, `meta_code`, 'value', 'anlage_datum', 'anlage_user_nr', 'anlage_orgnr') \n" +
                "values ('" + IDMETA + "', '" + EDOK_VORLAGE_ID + "', '" + META_CODE + "', '" + VALUE + "', '" + ANLAGE_DATUM_META + "', '" + ANLAGE_USER_NR_Meta + "', '" + ANLAGE_ORGNR_META + "');";
        try {
            // Write the text to the file
            if (MetaCount == 0) {
                // Create a BufferedWriter to write to the file
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                writer.newLine();
                writer.write("-- Metadaten der Vorlage in Tabelle edok_metadatum hinzufügen ");
                writer.newLine();
                writer.write(MetaQuery);
                writer.newLine();
                addTextArea_First_meta(textAraetxt);
                // Close the writer
                writer.close();
                MetaCount++;
            } else {
                String targetWord = "Metadaten";
                FileReader fileReader = new FileReader(filePath);
                BufferedReader reader = new BufferedReader(fileReader);
                StringBuilder fileContent = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");

                    if (line.contains(targetWord)) {
                        // Insert the new text under the target word
                        fileContent.append(MetaQuery).append("\n");
                    }
                }
                reader.close();
                // Write the updated content back to the file
                FileWriter fileWriter = new FileWriter(filePath, false);
                fileWriter.write(fileContent.toString());
                fileWriter.close();
                MetaCount++;
                EditTextArea_meta(textAraetxt);
            }
        } catch (
                IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving text to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void write_baum_Query(String filePath, int ID_BAUM, String EDOK_VORLAGENBAUM_ID, String EDOK_VORLAGE_ID_BAUM, String ANLAGE_DATUM_BAUM, String ANLAGE_USER_NR_BAUM, String ANLAGE_ORGNR_BAUM, String ORDNER) {
        Löschen_comitted(filePath);
        try {
            // Write the text to the file
            if (BaumCount == 0) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                writer.newLine();
                writer.write("-- Vorlagenbaumeintrag in Tabelle edok_vorlagenbaum_vorlage hinzufügen\n");
                writer.write("-- Ordner = " + ORDNER);
                addTextArea_First_baum(textAraetxt);
                writer.newLine();
                writer.write("insert into `edok_vorlagenbaum_vorlage` (`id`, `edok_vorlagenbaum_id`, `edok_vorlage_id`, 'anlage_datum', 'anlage_user_nr', 'anlage_orgnr') \n" +
                        "values ('" + ID_BAUM + "', '" + EDOK_VORLAGENBAUM_ID + "', '" + EDOK_VORLAGE_ID_BAUM + "', '" + ANLAGE_DATUM_BAUM + "', '" + ANLAGE_USER_NR_BAUM + "', '" + ANLAGE_ORGNR_BAUM + "');");
                writer.newLine();
                // Close the writer
                writer.close();
                BaumCount++;
            } else {
                String targetWord = "Vorlagenbaumeintrag";
                String newText = "-- Ordner = " + ORDNER + "\n" +
                        "insert into `edok_vorlagenbaum_vorlage` (`id`, `edok_vorlagenbaum_id`, `edok_vorlage_id`, 'anlage_datum', 'anlage_user_nr', 'anlage_orgnr') \n" +
                        "values ('" + ID_BAUM + "', '" + EDOK_VORLAGENBAUM_ID + "', '" + EDOK_VORLAGE_ID_BAUM + "', '" + ANLAGE_DATUM_BAUM + "', '" + ANLAGE_USER_NR_BAUM + "', '" + ANLAGE_ORGNR_BAUM + "');";

                FileReader fileReader = new FileReader(filePath);
                BufferedReader reader = new BufferedReader(fileReader);
                StringBuilder fileContent = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");

                    if (line.contains(targetWord)) {
                        // Insert the new text under the target word
                        fileContent.append(newText).append("\n");
                    }
                }
                reader.close();
                // Write the updated content back to the file
                FileWriter fileWriter = new FileWriter(filePath, false);
                fileWriter.write(fileContent.toString());
                fileWriter.close();
                BaumCount++;
                EditTextArea_baum(textAraetxt);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving text to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void write_konfig_Query(String filePath, int ID_KONFIG, String EDOK_VORLAGE_HAUPTSCHREIBEN_ID, String EDOK_VORLAGE_FOLGESCHREIBEN_ID, String OPTIONAL, String VORBELEGT, int REIHENFOLGE_NR, String DATEN_UEBERGEBEN, String HAUPTSCHREIBEN_ANHAENGEN, String ANLAGE_DATUM_KONFIG, String ANLAGE_USER_NR_KONFIG, String ANLAGE_ORGNR_KONFIG, String FOLGESCHREIBEN) {
        Löschen_comitted(filePath);
        try {
            // Write the text to the file
            if (KonfigCount == 0) {
                // Create a BufferedWriter to write to the file
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                writer.newLine();
                writer.write("-- Folgeschreibenkonfiguration in Tabelle edok_vorlage_folgeschreiben_konfiguration hinzufügen\n" +
                        "-- Folgeschreiben = " + FOLGESCHREIBEN);
                addTextArea_First_konfig(textAraetxt);

                writer.newLine();
                writer.write("insert into `edok_vorlage_folgeschreiben_konfiguration ` (`id`, `edok_vorlage_hauptschreiben_id`, `edok_vorlage_folgeschreiben_id`, 'optional', 'vorbelegt', 'reihenfolge_nr', 'daten_uebergeben', 'hauptschreiben_anhaengen', 'anlage_datum', 'anlage_user_nr', 'anlage_orgnr') \n" +
                        "values ('" + ID_KONFIG + "', '" + EDOK_VORLAGE_HAUPTSCHREIBEN_ID + "', '" + EDOK_VORLAGE_FOLGESCHREIBEN_ID + "', '" + OPTIONAL + "', '" + VORBELEGT + "', '" + REIHENFOLGE_NR + "', '" + DATEN_UEBERGEBEN + "', '" + HAUPTSCHREIBEN_ANHAENGEN + "', '" + ANLAGE_DATUM_KONFIG + "', '" + ANLAGE_USER_NR_KONFIG + "', '" + ANLAGE_ORGNR_KONFIG + "');");
                writer.newLine();
                // Close the writer
                writer.close();
                KonfigCount++;
            } else {

                String targetWord = "Folgeschreibenkonfiguration";
                String newText = "-- Folgeschreiben = " + FOLGESCHREIBEN + "\n" +
                        "insert into `edok_vorlage_folgeschreiben_konfiguration ` (`id`, `edok_vorlage_hauptschreiben_id`, `edok_vorlage_folgeschreiben_id`, 'optional', 'vorbelegt', 'reihenfolge_nr', 'daten_uebergeben', 'hauptschreiben_anhaengen', 'anlage_datum', 'anlage_user_nr', 'anlage_orgnr') \n" +
                        "values ('" + ID_KONFIG + "', '" + EDOK_VORLAGE_HAUPTSCHREIBEN_ID + "', '" + EDOK_VORLAGE_FOLGESCHREIBEN_ID + "', '" + OPTIONAL + "', '" + VORBELEGT + "', '" + REIHENFOLGE_NR + "', '" + DATEN_UEBERGEBEN + "', '" + HAUPTSCHREIBEN_ANHAENGEN + "', '" + ANLAGE_DATUM_KONFIG + "', '" + ANLAGE_USER_NR_KONFIG + "', '" + ANLAGE_ORGNR_KONFIG + "');";
                FileReader fileReader = new FileReader(filePath);
                BufferedReader reader = new BufferedReader(fileReader);
                StringBuilder fileContent = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");

                    if (line.contains(targetWord)) {
                        // Insert the new text under the target word
                        fileContent.append(newText).append("\n");

                    }
                }

                reader.close();

                // Write the updated content back to the file
                FileWriter fileWriter = new FileWriter(filePath, false);
                fileWriter.write(fileContent.toString());
                fileWriter.close();
                KonfigCount++;
                EditTextArea_konfig(textAraetxt);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving text to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void write_Comitted(String filename) {
        try {
            // Create a BufferedWriter to write to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            // Write the text to the file
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.write("  commit;\n" +
                    "end;\n" +
                    "/\n" +
                    "spool off");
            writer.newLine();
            // Close the writer
            writer.close();
            addTextArea_comitted(textAraetxt);
            process = true;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving text to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void openFile(String filename) {

        // Create a File object representing the folder
        File folder = new File(filename);

        try {
            // Check if Desktop is supported (available)
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();

                // Check if the folder exists before trying to open it
                if (folder.exists()) {
                    desktop.open(folder);
                } else {
                    JOptionPane.showMessageDialog(null, "Folder does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Desktop not supported.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error opening folder.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void Löschen_meta(String filename) {
        Löschen_comitted(filename);
        String startWord1 = "Metadaten";
        String startWord2 = "insert into `edok_metadatum";
        String endWord = ");";
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String previousLine = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains(startWord1) || (line.contains(startWord2))) {
                    if (line.contains(startWord2)) MetaCount--;
                    previousLine = null;
                    toggle = true;
                } else {
                    if (previousLine != null) {
                        text.append(previousLine).append("\n");
                    }
                    if (line.contains(endWord) && toggle == true) {
                        previousLine = null;
                        toggle = false;
                        continue;
                    }
                    previousLine = line;
                }
            }
            // Append the last line if it wasn't skipped
            if (previousLine != null) {
                text.append(previousLine).append("\n");
            }
            //reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(text.toString());
            // writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        DeleteTextArea_meta(textAraetxt);
    }

    public static void Löschen_konfig(String filename) {
        Löschen_comitted(filename);
        String startWord1 = "Folgeschreibenkonfiguration";
        String startWord2 = "Folgeschreiben";
        String startWord3 = "insert into `edok_vorlage_folgeschreiben_konfiguration";
        String endWord = ");";

        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String previousLine = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains(startWord1) || (line.contains(startWord2)) || (line.contains(startWord3))) {
                    if (line.contains(startWord3)) KonfigCount--;
                    previousLine = null;
                    toggle = true;
                } else {
                    if (previousLine != null) {
                        text.append(previousLine).append("\n");
                    }
                    if (line.contains(endWord) && toggle == true) {
                        previousLine = null;
                        toggle = false;
                        continue;
                    }
                    previousLine = line;
                }
            }
            // Append the last line if it wasn't skipped
            if (previousLine != null) {
                text.append(previousLine).append("\n");
            }

            //reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(text.toString());
            // writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        DeleteTextArea_konfig(textAraetxt);
    }

    public static void Löschen_baum(String filename) {
        Löschen_comitted(filename);
        String startWord1 = "Vorlagenbaumeintrag";
        String startWord2 = "Ordner";
        String startWord3 = "insert into `edok_vorlagenbaum_vorlage";
        String endWord = ");";
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String previousLine = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains(startWord1) || (line.contains(startWord2)) || (line.contains(startWord3))) {
                    if (line.contains(startWord3)) BaumCount--;
                    previousLine = null;
                    toggle = true;
                } else {
                    if (previousLine != null) {
                        text.append(previousLine).append("\n");
                    }
                    if (line.contains(endWord) && toggle == true) {
                        previousLine = null;
                        toggle = false;
                        continue;
                    }
                    previousLine = line;
                }
            }
            // Append the last line if it wasn't skipped
            if (previousLine != null) {
                text.append(previousLine).append("\n");
            }

            //reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(text.toString());
            // writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        DeleteTextArea_baum(textAraetxt);
    }

    public static void Löschen_comitted(String filename) {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
            //reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (text.indexOf("  commit;") != -1) {
            int i = text.indexOf("  commit;");
            text.delete(i - 3, text.length());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(text.toString());
            // writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        DeleteTextArea_comitted(textAraetxt);
    }

    public static String getFormattedDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);
        return formattedDate;
    }

    public static String getFormattedTime() {
        Date currentDate = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(currentDate);
        return formattedTime;
    }

    private static StringBuilder addTextArea_vorlage() {
        String formattedDate = ToolController.getFormattedDate();
        String formattedTime = ToolController.getFormattedTime();
        textAraetxt = new StringBuilder("**************************** Details *****************************\n" +
                "Time: " + formattedTime + " Date: " + formattedDate + "\n" +
                "*****************************************************************\n" +
                "   Query          Anzahl\n" +
                "1.Vorlage\t1");
        return textAraetxt;
    }

    private static StringBuilder addTextArea_First_meta(StringBuilder textAraetxt) {
        textAraetxt.append("\n" + "2.Meta\t1");
        return textAraetxt;
    }

    private static StringBuilder EditTextArea_meta(StringBuilder textAraetxt) {
        int startIndex = textAraetxt.indexOf("2.Meta");
        textAraetxt = textAraetxt.replace(startIndex, startIndex + 8, "2.Meta\t" + MetaCount);
        return textAraetxt;

    }

    private static StringBuilder DeleteTextArea_meta(StringBuilder textAraetxt) {
        int startIndex = textAraetxt.indexOf("2.Meta");
        textAraetxt = textAraetxt.delete(startIndex - 1, startIndex + 8);
        return textAraetxt;

    }

    private static StringBuilder addTextArea_First_baum(StringBuilder textAraetxt) {
        textAraetxt.append("\n" + "4.Baum\t1");
        return textAraetxt;
    }

    private static StringBuilder EditTextArea_baum(StringBuilder textAraetxt) {
        int startIndex = textAraetxt.indexOf("4.Baum");
        textAraetxt = textAraetxt.replace(startIndex, startIndex + 8, "4.Baum\t" + BaumCount);
        return textAraetxt;

    }

    private static StringBuilder DeleteTextArea_baum(StringBuilder textAraetxt) {
        int startIndex = textAraetxt.indexOf("4.Baum");
        textAraetxt = textAraetxt.delete(startIndex - 1, startIndex + 8);
        return textAraetxt;

    }

    private static StringBuilder addTextArea_First_konfig(StringBuilder textAraetxt) {
        textAraetxt.append("\n" + "3.Konfig\t1");
        return textAraetxt;
    }

    private static StringBuilder EditTextArea_konfig(StringBuilder textAraetxt) {
        int startIndex = textAraetxt.indexOf("3.Konfig");
        textAraetxt = textAraetxt.replace(startIndex, startIndex + 10, "3.Konfig\t" + KonfigCount);
        return textAraetxt;

    }

    private static StringBuilder DeleteTextArea_konfig(StringBuilder textAraetxt) {
        int startIndex = textAraetxt.indexOf("3.Konfig");
        textAraetxt = textAraetxt.delete(startIndex - 1, startIndex + 10);
        return textAraetxt;

    }

    private static StringBuilder addTextArea_comitted(StringBuilder textAraetxt) {
        textAraetxt = textAraetxt.append("\n" +
                "****************************************************** Comitted!");
        return textAraetxt;
    }

    private static StringBuilder DeleteTextArea_comitted(StringBuilder textAraetxt) {
        int startIndex = textAraetxt.indexOf("****************************************************** Comitted!");
        boolean containsComitt = startIndex != -1;
        if (containsComitt) {
            textAraetxt = textAraetxt.delete(startIndex - 1, textAraetxt.length());
            return textAraetxt;
        } else {

            return textAraetxt;
        }
    }

    public static void ResetCounters() {
        MetaCount = 0;
        KonfigCount = 0;
        BaumCount = 0;
    }

    public static DefaultComboBoxModel<String> loadValuesFromIni(String filename) {
        DefaultComboBoxModel<String> COMPOBOX = new DefaultComboBoxModel<>();
        // Use a LinkedHashMap to maintain the order
        Map<String, String> dropdownOptions = new LinkedHashMap<>();
        Properties properties = new Properties();

        try (InputStream inputStream = ToolController.class.getResourceAsStream("/ini/"+ filename + ".ini")) {
            //"/resources/ini/"+
            if (inputStream != null) {
                properties.load(inputStream);
                // Get all keys and add them to the LinkedHashMap
                for (String key : properties.stringPropertyNames()) {
                    String value = properties.getProperty(key);
                    if (!value.isEmpty()) {
                        dropdownOptions.put(key, value);
                    }
                }
            }
            // Create a Vector and add non-empty options to it in reverse order
            Vector<String> reversedValues = new Vector<>();
            for (String value : dropdownOptions.values()) {
                reversedValues.add(value);
            }
            // Add options to the dropdown in reverse order, excluding empty values
            for (int i = reversedValues.size() - 1; i >= 0; i--) {
                COMPOBOX.addElement(reversedValues.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return COMPOBOX;
    }
}