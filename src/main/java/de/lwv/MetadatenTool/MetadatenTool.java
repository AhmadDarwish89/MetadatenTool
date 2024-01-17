package main.java.de.lwv.MetadatenTool;

import main.java.de.lwv.MetadatenTool.view.Startseite;

import javax.swing.*;

public class MetadatenTool {

    public static void main (String args[]){

        /* Create and display the form */

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
          
                new Startseite().setVisible(true);
            }
        });
    }
}
