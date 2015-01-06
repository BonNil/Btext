package Bpackage;

import rtf.AdvancedRTFEditorKit;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Collections;
import java.util.Vector;
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
    JPanel stylePanel;
    JToolBar toolBar;
    JScrollPane scrollPane;

    //Menu stuff
    JMenuBar menuBar;
    JMenuItem sMenuItem;
    JMenuItem s2MenuItem;
    JMenuItem lMenuItem;

    //Stuff for FileTree class
    DefaultMutableTreeNode node;


    public void buildGUI(){

        //initialize shit
        mainFrame = new JFrame("Btext v0.1");
        backPanel = new JPanel();
        menuBar = new JMenuBar();
        stylePanel = new JPanel();
        toolBar = new JToolBar();
        scrollPane = new JScrollPane();
        fTree = new FileTree(new File("."));


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
        stylePanel.add(bar);



        //add stuff to backPanel
        mainFrame.getContentPane().add(BorderLayout.WEST, fTree);
        mainFrame.getContentPane().add(BorderLayout.NORTH, stylePanel);
        mainFrame.getContentPane().add(BorderLayout.CENTER, textPane);


        //Final configurations for the mainFrame
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        //Add actionlistener for sMenuItem, the SAVE AS menu button
        sMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Save");

                int userSelection = chooser.showSaveDialog(mainFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION){

                    String fileName = chooser.getSelectedFile().toString();
                    if (!fileName.endsWith(".txt")) {
                        fileName += ".txt";
                    }

                    try {
                        FileWriter fr = new FileWriter(fileName);
                        textPane.write(fr);
                        if(fr != null){
                            fr.close();
                        }
                    }catch (Exception ex){
                        System.out.println("Something went wrong when saving");
                        ex.printStackTrace();
                    }
                }
            }
        });

        //Add actionlistener for sMenuItem, the SAVE menu button
        s2MenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String fileName;
                if (System.getProperty("os.name").startsWith("Win")) {
                    fileName = node.getParent().toString() + "\\" + node.toString();
                }else{
                    fileName = node.getParent().toString() + "/" + node.toString();
                }
                    try {
                        FileWriter fr = new FileWriter(fileName);
                        textPane.write(fr);
                        if (fr != null) {
                            fr.close();
                        }
                    } catch (IOException ex) {
                        System.out.println("Something went wrong when saving");
                        ex.printStackTrace();
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
                        textPane.read(fi, chooser.getSelectedFile());
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

            }
        });

    }

    public class FileTree extends JPanel {
        /**
         * Construct a FileTree
         */
        public FileTree(File dir) {
            setLayout(new BorderLayout());

            // Make a tree list with all the nodes, and make it a JTree
            JTree tree = new JTree(addNodes(null, dir));

            // Add a listener
            tree.addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {
                    node = (DefaultMutableTreeNode) e
                            .getPath().getLastPathComponent();
                    System.out.println("You selected " + node);
                    String balle = node.getParent().toString();
                    System.out.println("Parent is " + balle);

                    if (System.getProperty("os.name").startsWith("Win")) {
                        if (node.toString().endsWith(".txt")) {
                            try {
                                textPane.setForeground(Color.BLACK); // set color to black, indicating that the file is a text file
                                FileInputStream fi = new FileInputStream(node.getParent().toString() + "\\" + node.toString());
                                textPane.read(fi, node.getParent().toString() + "\\" + node.toString());
                            } catch (IOException ex) {
                                System.out.println("Something went to shit");
                                ex.printStackTrace();
                            }
                        } else {
                            try {
                                textPane.setForeground(Color.BLACK); // set color to black, indicating that the file is a text file
                                FileInputStream fi = new FileInputStream(node.getParent().toString() + "\\" + node.toString());
                                textPane.read(fi, node.getParent().toString() + "\\" + node.toString());
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                try {
                                    textPane.getDocument().remove(0, textPane.getDocument().getLength());
                                    textPane.setText("This is not a text file!");
                                    textPane.setForeground(Color.RED); // set color to red, showing that the selected file is NOT a text file
                                } catch (BadLocationException be) {
                                    be.printStackTrace();
                                    System.out.println("Something REALLY went to shit");
                                }
                            }

                        }
                    }else{
                        if (node.toString().endsWith(".txt")) {
                            try {
                                textPane.setForeground(Color.BLACK); // set color to black, indicating that the file is a text file
                                FileInputStream fi = new FileInputStream(node.getParent().toString() + "/" + node.toString());
                                textPane.read(fi, node.getParent().toString() + "/" + node.toString());
                            } catch (IOException ex) {
                                System.out.println("Something went to shit");
                                ex.printStackTrace();
                            }
                        } else {
                            try {
                                textPane.setForeground(Color.BLACK); // set color to black, indicating that the file is a text file
                                FileInputStream fi = new FileInputStream(node.getParent().toString() + "/" + node.toString());
                                textPane.read(fi, node.getParent().toString() + "/" + node.toString());
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                try {
                                    textPane.getDocument().remove(0, textPane.getDocument().getLength());
                                    textPane.setText("This is not a text file!");
                                    textPane.setForeground(Color.RED); // set color to red, showing that the selected file is NOT a text file
                                } catch (BadLocationException be) {
                                    be.printStackTrace();
                                    System.out.println("Something REALLY went to shit");
                                }
                            }

                        }
                    }

                }
            });

            // Lastly, put the JTree into a JScrollPane.
            JScrollPane scrollpane = new JScrollPane();
            scrollpane.getViewport().add(tree);
            add(BorderLayout.CENTER, scrollpane);
        }

        /**
         * Add nodes from under "dir" into curTop. Highly recursive.
         */
        DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
            String curPath = dir.getPath();
            DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
            if (curTop != null) { // should only be null at root
                curTop.add(curDir);
            }
            Vector ol = new Vector();
            String[] tmp = dir.list();
            for (int i = 0; i < tmp.length; i++)
                ol.addElement(tmp[i]);
            Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
            File f;
            Vector files = new Vector();
            // Make two passes, one for Dirs and one for Files. This is #1.
            for (int i = 0; i < ol.size(); i++) {
                String thisObject = (String) ol.elementAt(i);
                String newPath;
                if (curPath.equals("."))
                    newPath = thisObject;
                else
                    newPath = curPath + File.separator + thisObject;
                if ((f = new File(newPath)).isDirectory())
                    addNodes(curDir, f);
                else
                    files.addElement(thisObject);
            }
            // Pass two: for files.
            for (int fnum = 0; fnum < files.size(); fnum++)
                curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
            return curDir;
        }

        public Dimension getMinimumSize() {
            return new Dimension(200, 400);
        }

        public Dimension getPreferredSize() {
            return new Dimension(200, 400);
        }
    }


}
