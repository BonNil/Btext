package Bpackage;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.text.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import javax.swing.event.TreeSelectionListener;

/**
 * Created by Bonny on 2015-01-03.
 */
public class EditorTwo {


    //Textpane and StyledDocument
    public JTextPane textPane;
    StyledDocument doc;
    FileTree fTree;

    //Stuff
    JFrame mainFrame;
    JPanel backPanel;
    JPanel topPanel;
    JToolBar toolBar;
    JScrollPane scrollPane;

    //Menu stuff
    JMenuBar menuBar;
    JMenuItem sMenuItem;
    JMenuItem s2MenuItem;
    JMenuItem lMenuItem;

    //Stuff for FileTree class
    DefaultMutableTreeNode node;


    public void buildGUI() {

        //initialize shit
        mainFrame = new JFrame("Btext v0.1");
        backPanel = new JPanel();
        menuBar = new JMenuBar();
        topPanel = new JPanel();
        toolBar = new JToolBar();
        scrollPane = new JScrollPane();

        fTree = new FileTree(new File("."));
        buildTreeListener(fTree);


        mainFrame.setContentPane(backPanel);

        //Initializing textpane and document
        textPane = new JTextPane();
        doc = textPane.getStyledDocument();

        //Toolbar shit
        JToolBar bar = new JToolBar();
        bar.add(new StyledEditorKit.ForegroundAction("Black", Color.black));
        bar.add(new StyledEditorKit.ForegroundAction("Red", Color.red));
        bar.add(new StyledEditorKit.ForegroundAction("Blue", Color.blue));
        bar.add(new StyledEditorKit.FontSizeAction("12", 12));
        bar.add(new StyledEditorKit.FontSizeAction("16", 16));
        bar.add(new StyledEditorKit.FontSizeAction("24", 24));
        bar.add(new StyledEditorKit.UnderlineAction());

        //set up FILE menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        //setting up icons for load/save menuitems
        ImageIcon saveIcon = new ImageIcon("saveSmall.png");
        ImageIcon loadIcon = new ImageIcon("loadSmall.png");

        //SAVE AS menu item
        sMenuItem = new JMenuItem("Save as", saveIcon);
        sMenuItem.setMnemonic(KeyEvent.VK_A);
        sMenuItem.setToolTipText("Save your text");

        //SAVE menu item
        s2MenuItem = new JMenuItem("Save", saveIcon);
        s2MenuItem.setMnemonic(KeyEvent.VK_S);
        s2MenuItem.setToolTipText("Save");

        //LOAD menu item
        lMenuItem = new JMenuItem("Load", loadIcon);
        lMenuItem.setMnemonic(KeyEvent.VK_L);
        lMenuItem.setToolTipText("Open a file");

        buildMenuItemListeners();

        //Creating File menu and adding it to mainFrame
        fileMenu.add(sMenuItem);
        fileMenu.add(s2MenuItem);
        fileMenu.add(lMenuItem);
        menuBar.add(fileMenu);
        mainFrame.setJMenuBar(menuBar);

        //set layout for backPanel
        backPanel.setLayout(new BorderLayout());

        //Add to stylepanel
        topPanel.add(bar);

        //add stuff to backPanel
        mainFrame.getContentPane().add(BorderLayout.WEST, fTree);
        mainFrame.getContentPane().add(BorderLayout.NORTH, topPanel);
        mainFrame.getContentPane().add(BorderLayout.CENTER, textPane);


        //Final configurations for the mainFrame
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(50, 50, 300, 300);
        mainFrame.pack();
        mainFrame.setSize(600, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void openFile(String path) {
        if (path.toUpperCase().endsWith(".PNG") || path.toUpperCase().endsWith(".JPG")
                || path.toUpperCase().endsWith(".JPEG") || path.toUpperCase().endsWith(".GIF")) {
            try {
                textPane.getDocument().remove(0, textPane.getDocument().getLength());
                textPane.setText("ERROR --> This is an image file man!");
                textPane.setForeground(Color.RED); // set color to red, showing that the selected file is NOT a text file
                return;
            } catch (BadLocationException be) {
                be.printStackTrace();
                System.out.println("#1: Something REALLY went to shit dude!! Bad location n'stuff nigguh");
                return;
            }
        }
        try {
            FileInputStream fi = new FileInputStream((path));
            textPane.read(fi, path);
        } catch (IOException e) {
            System.out.println("Fuck me, i couldn't open the file man!");
            try {
                textPane.getDocument().remove(0, textPane.getDocument().getLength());
                textPane.setText("ERROR --> Unknown file format, cannot open!");
                textPane.setForeground(Color.RED); // set color to red, showing that the selected file is NOT a text file
            } catch (BadLocationException be) {
                be.printStackTrace();
                System.out.println("#2: Something REALLY went to shit dude!! Bad location n'stuff nigguh");
            }
        }
    }

    public void saveFile(String path){
        try {
            FileWriter fr = new FileWriter(path);
            textPane.write(fr);
            if (fr != null) {
                fr.close();
            }
        } catch (IOException ex) {
            System.out.println("Something went wrong when saving");
            ex.printStackTrace();
        }
    }


    public void buildTreeListener(FileTree fileTree) {
        // Add a listener
        fileTree.tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                node = (DefaultMutableTreeNode) e
                        .getPath().getLastPathComponent();
                System.out.println("You selected " + node);
                String balle = node.getParent().toString();
                System.out.println("Parent is " + balle);

                if (node.toString().endsWith(".txt")) {
                    textPane.setForeground(Color.BLACK); // set color to black, indicating that the file is a text file
                    if (System.getProperty("os.name").startsWith("Win")) {
                        openFile(node.getParent().toString() + "\\" + node.toString());
                    } else {
                        openFile(node.getParent().toString() + "/" + node.toString());
                    }
                } else {
                    if (System.getProperty("os.name").startsWith("Win")) {
                        openFile(node.getParent().toString() + "\\" + node.toString());
                    } else {
                        openFile(node.getParent().toString() + "/" + node.toString());
                    }
                }
            }
        });
    }


    public void buildMenuItemListeners() {

        //Add actionlistener for sMenuItem, the SAVE AS menu button
        sMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Save");

                int userSelection = chooser.showSaveDialog(mainFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    saveFile(chooser.getSelectedFile().toString());
                }
                //Recreating the FileTree by removing it, build a new one and insert it into JFrame
                mainFrame.remove(fTree);
                fTree = null;
                fTree = new FileTree(new File("."));
                buildTreeListener(fTree);
                mainFrame.add(BorderLayout.WEST, fTree);
                mainFrame.revalidate();
            }
        });

        //Add actionlistener for sMenuItem, the SAVE menu button
        s2MenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String fileName;
                if (System.getProperty("os.name").startsWith("Win")) {
                    fileName = node.getParent().toString() + "\\" + node.toString();
                } else {
                    fileName = node.getParent().toString() + "/" + node.toString();
                }
                saveFile(fileName);

            }
        });

        //Add actionlistener for sMenuItem, the LOAD menu button
        lMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Load an RTF file into the editor
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Save");
                int userSelection = chooser.showOpenDialog(mainFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    openFile(chooser.getSelectedFile().toString());
                }
            }
        });

    }

}
