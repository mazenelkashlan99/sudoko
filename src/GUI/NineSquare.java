package Gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class NineSquare extends JPanel {
    private static final int CELL_SIZE = 60;
    private static final int FONT_SIZE = 28;
    private JTextField[] fields;
    private int squareIndex;

    public NineSquare(Color bgColor, int index) {
        this.squareIndex = index;
        setLayout(new GridLayout(3, 3, 3, 3));
        setBackground(Color.BLACK);
        initFields();
        initGui();
        setBackground(bgColor);
        setPreferredSize(new Dimension(CELL_SIZE * 3, CELL_SIZE * 3));
    }

    private void initFields() {
        fields = new JTextField[9];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new JTextField(2);
            fields[i].setDocument(new NumericalDocument());
            fields[i].setHorizontalAlignment(JTextField.CENTER);
            fields[i].setFont(new Font("Segoe UI", Font.BOLD, FONT_SIZE));
            fields[i].setBackground(Color.BLACK);
            fields[i].setForeground(new Color(220, 220, 255));
            fields[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 150, 255), 2),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)
            ));
            fields[i].setCaretColor(new Color(100, 150, 255));

            final int cellIndex = i;
            fields[i].getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void changedUpdate(javax.swing.event.DocumentEvent e) { notifyChange(); }
                public void insertUpdate(javax.swing.event.DocumentEvent e) { notifyChange(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { notifyChange(); }
                private void notifyChange() {
                    Container parent = NineSquare.this.getParent();
                    while (parent != null && !(parent instanceof Board)) parent = parent.getParent();
                    if (parent instanceof Board) {
                        Board board = (Board) parent;
                        if (board.getOnBoardChange() != null)
                            board.getOnBoardChange().run();
                    }
                }
            });
            fields[i].addKeyListener(new NavigationKeyListener(this, i));
        }
    }

    private void initGui() {
        for (JTextField field : fields) add(field);
        setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, new Color(70, 130, 200)));
    }

    public void focusCell(int cellIndex) {
        if (cellIndex >= 0 && cellIndex < fields.length) {
            fields[cellIndex].requestFocus();
            fields[cellIndex].selectAll();
        }
    }

    public int getSquareIndex() { return squareIndex; }
    public JTextField getCell(int cellIndex) { return (cellIndex >= 0 && cellIndex < fields.length) ? fields[cellIndex] : null; }
    public void setCellValue(int index, int value) {
        if (index < 0 || index >= fields.length) return;
        if (value == 0) fields[index].setText("");
        else fields[index].setText(String.valueOf(value));
    }
    public void setCellEditable(int index, boolean editable) {
        if (index >= 0 && index < fields.length) fields[index].setEditable(editable);
    }
    
    private class NavigationKeyListener extends KeyAdapter {
        private NineSquare nineSquare;
        private int cellIndex;
        public NavigationKeyListener(NineSquare nineSquare, int cellIndex) { this.nineSquare = nineSquare; this.cellIndex = cellIndex; }
        @Override
        public void keyPressed(KeyEvent e) {
            Board board = (Board) getParent();
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                e.consume(); board.navigateLeft(nineSquare, cellIndex);
            } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                e.consume(); board.navigateRight(nineSquare, cellIndex);
            } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                e.consume(); board.navigateUp(nineSquare, cellIndex);
            } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                e.consume(); board.navigateDown(nineSquare, cellIndex);
            }
        }
    }

    public static class NumericalDocument extends PlainDocument {
        private static final String ALLOWED_CHARS = "123456789";
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            if (getLength() == 0 && str.length() == 1 && ALLOWED_CHARS.contains(str)) {
                super.insertString(offs, str, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    public void setCellBackground(int cellIndex, Color color) {
        if (cellIndex >= 0 && cellIndex < fields.length) {
            fields[cellIndex].setBackground(color);
        }
    }

    public void setCellTextColor(int cellIndex, Color color) {
        if (cellIndex >= 0 && cellIndex < fields.length) {
            fields[cellIndex].setForeground(color);
        }
    }
}