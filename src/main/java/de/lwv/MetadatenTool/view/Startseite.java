package main.java.de.lwv.MetadatenTool.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Startseite extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlMain1;
    private JButton ToolButton;
    private JPanel JP_Home;
    private JLabel ImageLabel;

    public Startseite() {

        setContentPane(pnlMain);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 315);
        setTitle("LWVHessen");
        this.setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        ToolButton.setForeground(Color.WHITE);
        ToolButton.setBackground(new Color(0, 102, 204)); // Blue background color
        ToolButton.setFont(new Font("Arial", Font.BOLD, 16));
        ToolButton.setFocusPainted(false); // Remove the default focus border
        ToolButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ToolButton.setText("Tool zur Verwaltung von Metadaten\n für Schriftstückvorlagen");

        try {
            ImageLabel.setIcon(new ImageIcon(getClass().getResource("/image/logo2.jpg")));
        }catch (Exception e){
            e.printStackTrace();
        }

            ToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setVisible(false);
                    new Hauptseite().setVisible(true);
                }catch (Exception error){

                    JOptionPane.showMessageDialog(null, error.getMessage() ,"ahmad " , JOptionPane.INFORMATION_MESSAGE);

                }

            }
        });
    }

    public static void main(String args[]) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Startseite().setVisible(true);
            }
        });
    }



}
