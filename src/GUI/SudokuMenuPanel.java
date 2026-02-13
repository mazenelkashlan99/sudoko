package GUI;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class SudokuMenuPanel extends JPanel {

    // Color palette
    private static final Color BACKGROUND_GRADIENT_TOP = new Color(8, 12, 24);
    private static final Color BACKGROUND_GRADIENT_BOTTOM = new Color(16, 22, 40);
    private static final Color PANEL_BG = new Color(22, 28, 46, 220);
    private static final Color ACCENT_PRIMARY = new Color(64, 224, 208); // Turquoise
    private static final Color ACCENT_SECONDARY = new Color(147, 112, 219); // Purple
    private static final Color TEXT_PRIMARY = new Color(245, 245, 255);
    private static final Color TEXT_SECONDARY = new Color(200, 210, 245);
    private static final Color BORDER_GLOW = new Color(100, 200, 255, 80);

    // Difficulty colors
    private static final Color EASY_COLOR = new Color(72, 239, 128);
    private static final Color MEDIUM_COLOR = new Color(255, 193, 7);
    private static final Color HARD_COLOR = new Color(255, 71, 87);

    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JPanel buttonPanel;

    private float titleGlow = 0.0f;
    private Timer glowTimer;
    private int selectedDifficulty = -1;
    private Point mousePosition = new Point(-1, -1);

    public SudokuMenuPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        initComponents();
        layoutComponents();
        setupAnimations();
        setupMouseMotionListener();
    }

    private void initComponents() {
        // Title with gradient
        titleLabel = new JLabel("SUDOKU") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create gradient paint
                GradientPaint gradient = new GradientPaint(
                        0, 0, ACCENT_PRIMARY,
                        getWidth() / 2, getHeight() / 2, ACCENT_SECONDARY,
                        true
                );
                g2.setPaint(gradient);

                // Add glow effect
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f + titleGlow * 0.2f));
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2 + 2,
                        (getHeight() - fm.getHeight()) / 2 + fm.getAscent() + 2);

                // Main text
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                g2.setPaint(gradient);
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() - fm.getHeight()) / 2 + fm.getAscent());

                g2.dispose();
            }
        };
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 58));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(400, 100));

        // Subtitle
        subtitleLabel = new JLabel("Choose the difficulty:");
        subtitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create buttons with custom UI
        easyButton = createPremiumButton("EASY", EASY_COLOR);
        mediumButton = createPremiumButton("MEDIUM", MEDIUM_COLOR);
        hardButton = createPremiumButton("HARD", HARD_COLOR);

        // Button panel
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
    }

    private JButton createPremiumButton(String text, Color accentColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();

                // Background with gradient
                GradientPaint gradient;

                if (getModel().isPressed()) {
                    gradient = new GradientPaint(
                            0, 0, accentColor.darker().darker(),
                            0, h, accentColor.darker()
                    );
                } else if (getModel().isRollover() || (selectedDifficulty == getDifficultyIndex(text))) {
                    gradient = new GradientPaint(
                            0, 0, accentColor,
                            0, h, accentColor.darker()
                    );
                    // Glow effect
                    g2.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 50));
                    g2.fillRoundRect(-2, -2, w + 4, h + 4, 25, 25);
                } else {
                    gradient = new GradientPaint(
                            0, 0, new Color(35, 45, 65),
                            0, h, new Color(25, 35, 55)
                    );
                }

                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, w, h, 20, 20);

                // Border with accent
                g2.setColor(accentColor);
                g2.setStroke(new BasicStroke(getModel().isRollover() ? 2.5f : 1.5f));
                g2.drawRoundRect(1, 1, w - 3, h - 3, 18, 18);

                // Text
                g2.setColor(TEXT_PRIMARY);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                int textX = (w - fm.stringWidth(text)) / 2;
                int textY = (h - fm.getHeight()) / 2 + fm.getAscent();

                // Text shadow
                g2.setColor(new Color(0, 0, 0, 50));
                g2.drawString(text, textX + 1, textY + 1);
                g2.setColor(TEXT_PRIMARY);
                g2.drawString(text, textX, textY);

                g2.dispose();
            }
        };

        button.setPreferredSize(new Dimension(240, 60));
        button.setMinimumSize(new Dimension(200, 55));
        button.setMaximumSize(new Dimension(280, 65));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Store difficulty index
        button.putClientProperty("difficulty", getDifficultyIndex(text));

        // Selection effect
        button.addActionListener(e -> {
            selectedDifficulty = getDifficultyIndex(text);
            repaint();
        });

        return button;
    }

    private int getDifficultyIndex(String text) {
        switch (text) {
            case "EASY": return 0;
            case "MEDIUM": return 1;
            case "HARD": return 2;
            default: return -1;
        }
    }

    private void setupAnimations() {
        // Glowing title animation
        glowTimer = new Timer(50, e -> {
            titleGlow = 0.5f + 0.3f * (float)Math.sin(System.currentTimeMillis() / 500.0);
            titleLabel.repaint();
        });
        glowTimer.start();
    }

    private void setupMouseMotionListener() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePosition = e.getPoint();
                repaint();
            }
        });
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add vertical spacing
        gbc.insets = new Insets(20, 50, 10, 50);

        // Title panel
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);

        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.gridwidth = GridBagConstraints.REMAINDER;
        titleGbc.insets = new Insets(30, 0, 10, 0);
        titlePanel.add(titleLabel, titleGbc);
        titleGbc.insets = new Insets(10, 0, 40, 0);
        titlePanel.add(subtitleLabel, titleGbc);

        add(titlePanel, gbc);

        // Button panel
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.gridwidth = GridBagConstraints.REMAINDER;
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;
        buttonGbc.insets = new Insets(8, 70, 8, 70);

        buttonPanel.add(easyButton, buttonGbc);
        buttonPanel.add(mediumButton, buttonGbc);
        buttonPanel.add(hardButton, buttonGbc);

        gbc.insets = new Insets(20, 80, 40, 80);
        add(buttonPanel, gbc);

        // Decorative elements
        add(createDecorativePanel(), gbc);
    }

    private JPanel createDecorativePanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw decorative Sudoku grid pattern
                g2.setColor(new Color(ACCENT_PRIMARY.getRed(), ACCENT_PRIMARY.getGreen(), ACCENT_PRIMARY.getBlue(), 30));
                g2.setStroke(new BasicStroke(1));

                int size = 15;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        int x = getWidth() / 2 - 70 + i * size;
                        int y = getHeight() / 2 - 70 + j * size;

                        if ((i + j) % 2 == 0) {
                            g2.fillRoundRect(x, y, 4, 4, 2, 2);
                        } else {
                            g2.drawRoundRect(x, y, 4, 4, 2, 2);
                        }
                    }
                }

                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(200, 80));
        return panel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();

        // Background gradient
        GradientPaint gradient = new GradientPaint(
                0, 0, BACKGROUND_GRADIENT_TOP,
                0, h, BACKGROUND_GRADIENT_BOTTOM
        );
        g2.setPaint(gradient);
        g2.fillRect(0, 0, w, h);

        // Animated particles
        drawParticles(g2, w, h);

        // Subtle radial gradient overlay
        RadialGradientPaint radial = new RadialGradientPaint(
                mousePosition.x, mousePosition.y, 300,
                new float[]{0.0f, 0.8f},
                new Color[]{new Color(ACCENT_PRIMARY.getRed(), ACCENT_PRIMARY.getGreen(), ACCENT_PRIMARY.getBlue(), 15),
                        new Color(0, 0, 0, 0)}
        );
        g2.setPaint(radial);
        g2.fillRect(0, 0, w, h);

        g2.dispose();
        super.paintComponent(g);
    }

    private void drawParticles(Graphics2D g2, int w, int h) {
        g2.setColor(new Color(ACCENT_PRIMARY.getRed(), ACCENT_PRIMARY.getGreen(), ACCENT_PRIMARY.getBlue(), 40));

        long time = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            int x = (int)((Math.sin(time / 1000.0 + i) * 0.5 + 0.5) * w);
            int y = (int)((Math.cos(time / 1500.0 + i * 2) * 0.3 + 0.5) * h);

            int size = (int)(Math.sin(time / 500.0 + i) * 2 + 3);
            g2.fillOval(x, y, size, size);
        }
    }

    // Public methods for action listeners
    public void addEasyButtonListener(ActionListener listener) {
        easyButton.addActionListener(listener);
    }

    public void addMediumButtonListener(ActionListener listener) {
        mediumButton.addActionListener(listener);
    }

    public void addHardButtonListener(ActionListener listener) {
        hardButton.addActionListener(listener);
    }

    public int getSelectedDifficulty() {
        return selectedDifficulty;
    }
}