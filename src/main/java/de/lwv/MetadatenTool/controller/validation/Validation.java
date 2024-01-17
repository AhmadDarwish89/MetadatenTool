/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.lwv.MetadatenTool.controller.validation;

import java.io.File;

/**
 *
 * @author ahmad
 */
public class Validation {

    public static boolean isDigit(String... text) {
        for (String s: text){
            if(!s.matches("[0-9]+")){
                return false;
            }
        }
        return true;
    }
    public static boolean isIDNull(int ID){
        if(ID==0){
            return false;

        }
        else return true;
    }
    public static boolean isIDNull(String ID){
        if(!ID.isEmpty()){
            return false;

        }
        else return true;
    }

    public static boolean isSixNummer(String... text){
        for (String s: text){
            if(!(s.length()==6)){
                return false;
            }
        }
        return true;
    }

    public static void deleteExistingFile(File file ) {
        if (file.exists()) {
            boolean deleted = file.delete();

            if (deleted) {
                  //  JOptionPane.showMessageDialog(null, "File is exist,and it deleted successfully.");
            } else {
                // JOptionPane.showMessageDialog(null, "Failed to delete the file.");
            }
        } else {
          //  JOptionPane.showMessageDialog(null, "File does not exist, Create new File");
        }
    }
}

