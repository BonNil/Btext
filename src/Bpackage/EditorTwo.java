package Bpackage;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * Created by Bonny on 2015-01-03.
 */
public class EditorTwo {


    // Textpane and StyledDocument
    JTextPane textPane;
    StyledDocument doc;
    RTFEditorKit rtf;

    //Stuff
    JFrame mainFrame;
    JPanel backPanel;
    JPanel stylePanel;
    JComboBox styleBox;
    JToolBar toolBar;

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
        stylePanel = new JPanel();
        toolBar = new JToolBar();

        mainFrame.setContentPane(backPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initializing textpane and document
        rtf = new RTFEditorKit();
        textPane = new JTextPane();
        textPane.setEditorKit(rtf);
        doc = textPane.getStyledDocument();

        //Toolbar shit
        JToolBar bar = new JToolBar();
        bar.add(new StyledEditorKit.ForegroundAction("Black", Color.black));
        bar.add(new StyledEditorKit.ForegroundAction("Red", Color.red));
        bar.add(new StyledEditorKit.ForegroundAction("Blue", Color.blue));
        bar.add(new StyledEditorKit.FontSizeAction("12", 12));
        bar.add(new StyledEditorKit.FontSizeAction("14", 14));
        bar.add(new StyledEditorKit.FontSizeAction("16", 16));
        bar.add(new StyledEditorKit.UnderlineAction());

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

        buildMenuItemListeners();

        fileMenu.add(sMenuItem);
        fileMenu.add(lMenuItem);
        menuBar.add(fileMenu);
        mainFrame.setJMenuBar(menuBar);

        //set layout for backPanel
        backPanel.setLayout(new BorderLayout());

        stylePanel.add(bar);

        //add stuff to backPanel
        mainFrame.getContentPane().add(BorderLayout.NORTH, stylePanel);
        mainFrame.getContentPane().add(BorderLayout.CENTER, textPane);

        //Final configurations for the mainFrame
        mainFrame.setBounds(50, 50, 300, 300);
        mainFrame.pack();
        mainFrame.setSize(600, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }







/**
    public void smallerFontSize(){

            // get the current font
            Font f = editArea.getFont();

            // create a new, smaller font from the current font
            Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize()-1);

            // set the new font in the editing area
            editArea.setFont(f2);
    }

    public void largerFontSize(){

        // get the current font
        Font f = editArea.getFont();

        // create a new, smaller font from the current font
        Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize()+1);

        // set the new font in the editing area
        editArea.setFont(f2);
    }

 */

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
                    if (!fileName.endsWith(".rtf") && !fileName.endsWith(".txt")) {
                        fileName += ".rtf";
                    }

                    try {
                        FileOutputStream fwi = new FileOutputStream(chooser.getSelectedFile());
                        rtf.write(fwi, doc, doc.getStartPosition().getOffset(), doc.getLength());
                        if(fwi != null){
                            fwi.close();
                        }
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
                // Load an RTF file into the editor
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Save");
                    int userSelection = chooser.showOpenDialog(mainFrame);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        FileInputStream fi = new FileInputStream(chooser.getSelectedFile());
                        FileReader fr = new FileReader(chooser.getSelectedFile());
                        rtf.read(fr, textPane.getDocument(), 0);
                    }
                }
                catch( FileNotFoundException e1 )
                {
                    System.out.println( "File not found" );
                }
                catch( IOException e2 )
                {
                    System.out.println( "I/O error" );
                }
                catch( BadLocationException e3 )
                {
                }
            }
        });

    }


}
