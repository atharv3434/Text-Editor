import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JFileChooser fileChooser;
    private JMenuItem newFile, openFile, saveFile, exit, findText;
    private JMenuItem cut, copy, paste;

    public TextEditor() {
        setTitle("Simple Text Editor");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu searchMenu = new JMenu("Search");

        // File Menu Items
        newFile = new JMenuItem("New");
        openFile = new JMenuItem("Open");
        saveFile = new JMenuItem("Save");
        exit = new JMenuItem("Exit");

        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        exit.addActionListener(this);

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(exit);

        // Edit Menu Items
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);

        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);

        // Search Menu Item
        findText = new JMenuItem("Find");
        findText.addActionListener(this);
        searchMenu.add(findText);

        // Add Menus to Menu Bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(searchMenu);
        setJMenuBar(menuBar);

        // Text Area
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        // File Chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newFile) {
            textArea.setText("");
        } else if (e.getSource() == openFile) {
            openFile();
        } else if (e.getSource() == saveFile) {
            saveFile();
        } else if (e.getSource() == exit) {
            System.exit(0);
        } else if (e.getSource() == cut) {
            textArea.cut();
        } else if (e.getSource() == copy) {
            textArea.copy();
        } else if (e.getSource() == paste) {
            textArea.paste();
        } else if (e.getSource() == findText) {
            searchWord();
        }
    }

    private void openFile() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.read(reader, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                textArea.write(writer);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchWord() {
        String searchText = JOptionPane.showInputDialog(this, "Enter text to search:");
        if (searchText != null) {
            String content = textArea.getText();
            int index = content.indexOf(searchText);
            if (index != -1) {
                textArea.setCaretPosition(index);
                textArea.select(index, index + searchText.length());
                textArea.requestFocus();
                JOptionPane.showMessageDialog(this, "Text found!");
            } else {
                JOptionPane.showMessageDialog(this, "Text not found!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextEditor::new);
    }
}
