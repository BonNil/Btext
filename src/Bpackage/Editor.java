package Bpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Bonny on 2015-01-03.
 */
public class Editor {

    //Stuff
    JFrame mainFrame;
    JPanel backPanel;
    JPanel buttonPanel;
    JTextArea editArea;

    //Menu stuff
    JMenuBar menuBar;
    JMenuItem sMenuItem;
    JMenuItem lMenuItem;
    JMenuItem sfMenuItem;

    public void buildGUI(){

        //initialize shit
        mainFrame = new JFrame("Btext v0.1");
        backPanel = new JPanel();
        menuBar = new JMenuBar();
        editArea = new JTextArea();
        buttonPanel = new JPanel();

        mainFrame.setContentPane(backPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon saveIcon = new ImageIcon("saveSmall.png");
        ImageIcon loadIcon = new ImageIcon("loadSmall.png");

        //set up FILE menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        //SAVE menu item
        sMenuItem = new JMenuItem("Save", saveIcon);
        sMenuItem.setMnemonic(KeyEvent.VK_S);
        sMenuItem.setToolTipText("Save your text");

        //LOAD menu item
        lMenuItem = new JMenuItem("Load", loadIcon);
        lMenuItem.setMnemonic(KeyEvent.VK_L);
        lMenuItem.setToolTipText("Open a file");

        //set up TEXT menu
        JMenu textMenu = new JMenu("Text");
        fileMenu.setMnemonic(KeyEvent.VK_T);

        //SMALLERFONT menu item
        sfMenuItem = new JMenuItem("Decrease size");
        //sfMenuItem.setMnemonic(KeyEvent.VK_S);
        sfMenuItem.setToolTipText("Decrease the size of all text in document");

        buildMenuItemListeners();

        fileMenu.add(sMenuItem);
        fileMenu.add(lMenuItem);
        menuBar.add(fileMenu);
        mainFrame.setJMenuBar(menuBar);

        //set layout for backPanel
        backPanel.setLayout(new BorderLayout());

        //add stuff to backPanel
        mainFrame.getContentPane().add(BorderLayout.NORTH, buttonPanel);
        mainFrame.getContentPane().add(BorderLayout.CENTER, editArea);

        //Final configurations for the mainFrame
        mainFrame.setBounds(50, 50, 300, 300);
        mainFrame.pack();
        mainFrame.setSize(600, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }








    public void smallerFontSize(){
        /**
         * Reduce the size of the font in the JTextPane editor area.
         */
            // get the current font
            Font f = editArea.getFont();

            // create a new, smaller font from the current font
            Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize()-1);

            // set the new font in the editing area
            editArea.setFont(f2);
    }

    public void largerFontSize(){
        /**
         * Reduce the size of the font in the JTextPane editor area.
         */
        // get the current font
        Font f = editArea.getFont();

        // create a new, smaller font from the current font
        Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize()+1);

        // set the new font in the editing area
        editArea.setFont(f2);
    }

    public void buildMenuItemListeners(){

        //Add actionlistener for sMenuItem, the SAVE menu button
        sMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Save");

                int userSelection = chooser.showSaveDialog(mainFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION){

                    String fileName = chooser.getSelectedFile().toString();
                    if (!fileName.endsWith(".txt"))
                        fileName += ".txt";

                    try(FileWriter fw = new FileWriter(fileName)) {

                        editArea.write(fw);
                    }catch (Exception ex){
                        System.out.println("Something went wrong when saving");
                        ex.printStackTrace();
                    }
                }
            }
        });

        //Add actionlistener for sMenuItem, the LOAD menu button
        lMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Open file");

                int userSelection = chooser.showOpenDialog(mainFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION){
                    FileReader reader;

                    try {
                        reader = new FileReader(chooser.getSelectedFile());
                        editArea.read(reader, chooser.getSelectedFile());
                        if (reader != null){
                            try {
                                reader.close();
                            }catch (Exception ex){
                                System.out.println("Something went wrong when closing loaded file");
                                ex.printStackTrace();
                            }
                        }
                    }catch(Exception ex){
                        System.out.println("Something went wrong when loading file");
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

}
