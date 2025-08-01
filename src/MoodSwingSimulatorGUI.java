import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class MoodSwingSimulatorGUI extends JFrame {

    private final JTextArea outputArea;
    private JTextField inputField;
    private JButton submitButton;
    private final JPanel mainPanel;

    private final String[] moods = {"Angry 😡", "Sad 🥺", "Sarcastic 😂", "Philosophical 🧠", "Loving 💖"};
    private final Map<String, String> moodMessages = new HashMap<>();
    private final Map<String, Color> moodColors = new HashMap<>();
    private final Random rand = new Random();

    public MoodSwingSimulatorGUI() {
        setTitle("MoodSwingSimulator3000 GUI Edition");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize mood colors
        initializeMoodColors();
        
        // Create main panel with gradient background
        mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(138, 43, 226, 20),
                    0, getHeight(), new Color(30, 144, 255, 20)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        add(mainPanel);

        // Create header panel
        createHeaderPanel();
        
        // Text Area with enhanced styling
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        
        // Use fallback fonts for better compatibility
        Font textFont = new Font("SansSerif", Font.PLAIN, 14);
        if (isSystemFontAvailable("Segoe UI")) {
            textFont = new Font("Segoe UI", Font.PLAIN, 14);
        }
        outputArea.setFont(textFont);
        
        outputArea.setBackground(new Color(248, 249, 250));
        outputArea.setForeground(new Color(33, 37, 41));
        outputArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        
        // Custom scroll pane
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 15, 10, 15),
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
        ));
        scrollPane.getViewport().setBackground(new Color(248, 249, 250));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Enhanced Input Panel
        createInputPanel();

        // Mood Messages (keeping your original content)
        moodMessages.put("Angry 😡", "WHY are you still here?! Go code something useful!");
        moodMessages.put("Sad 🥺", "Nobody clicks me anymore… sigh…");
        moodMessages.put("Sarcastic 😂", "Oh look, another brilliant line from a human...");
        moodMessages.put("Philosophical 🧠", "If you're typing into me, who's really the bot?");
        moodMessages.put("Loving 💖", "You're amazing... like seriously... 🙄");

        // Listeners (keeping your original logic)
        submitButton.addActionListener(e -> handleUserInput());
        inputField.addActionListener(e -> handleUserInput());

        // Timer for mood updates (keeping your original timing)
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRandomMood();
            }
        });
        timer.start();

        // First mood immediately
        showRandomMood();
    }
    
    // Helper method to check if system font is available
    private boolean isSystemFontAvailable(String fontName) {
        String[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String font : availableFonts) {
            if (font.equals(fontName)) {
                return true;
            }
        }
        return false;
    }
    
    // Helper method to create separator line (Java 8 compatible)
    private String createSeparator(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("-");
        }
        return sb.toString();
    }
    
    private void initializeMoodColors() {
        moodColors.put("Angry 😡", new Color(220, 53, 69));
        moodColors.put("Sad 🥺", new Color(108, 117, 125));
        moodColors.put("Sarcastic 😂", new Color(255, 193, 7));
        moodColors.put("Philosophical 🧠", new Color(102, 16, 242));
        moodColors.put("Loving 💖", new Color(220, 20, 60));
    }
    
    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Header gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(75, 0, 130),
                    0, getHeight(), new Color(138, 43, 226)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setLayout(new BorderLayout());
        
        // Use fallback fonts for title
        Font titleFont = new Font("SansSerif", Font.BOLD, 18);
        if (isSystemFontAvailable("Segoe UI")) {
            titleFont = new Font("Segoe UI", Font.BOLD, 18);
        }
        
        JLabel titleLabel = new JLabel("MoodSwing Simulator 3000", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Input panel gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(240, 242, 247),
                    0, getHeight(), new Color(255, 255, 255)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Enhanced input field
        inputField = new JTextField();
        
        // Use fallback fonts for input
        Font inputFont = new Font("SansSerif", Font.PLAIN, 14);
        if (isSystemFontAvailable("Segoe UI")) {
            inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        }
        inputField.setFont(inputFont);
        
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        inputField.setBackground(Color.WHITE);
        inputField.setForeground(new Color(73, 80, 87));
        
        // Enhanced submit button
        submitButton = new JButton("Send") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setPaint(new GradientPaint(0, 0, new Color(0, 86, 179), 0, getHeight(), new Color(0, 123, 255)));
                } else if (getModel().isRollover()) {
                    g2d.setPaint(new GradientPaint(0, 0, new Color(0, 105, 217), 0, getHeight(), new Color(0, 86, 179)));
                } else {
                    g2d.setPaint(new GradientPaint(0, 0, new Color(0, 123, 255), 0, getHeight(), new Color(0, 86, 179)));
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        
        // Use fallback fonts for button
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        if (isSystemFontAvailable("Segoe UI")) {
            buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        }
        submitButton.setFont(buttonFont);
        
        submitButton.setForeground(Color.WHITE);
        submitButton.setContentAreaFilled(false);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        submitButton.setPreferredSize(new Dimension(100, 40));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(Box.createHorizontalStrut(10), BorderLayout.LINE_END);
        inputPanel.add(submitButton, BorderLayout.EAST);
        
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
    }

    private void showRandomMood() {
        String mood = moods[rand.nextInt(moods.length)];
        String message = moodMessages.get(mood);
        String time = LocalDateTime.now().toString();
        
        // Create colorful mood display
        String timeDisplay = time.length() >= 19 ? time.substring(11, 19) : time;
        String coloredMessage = String.format("[%s]\nMood: %s\n%s", 
            timeDisplay, mood, message);
        
        outputArea.append(coloredMessage + "\n" + createSeparator(50) + "\n\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
        
        // Flash the background color briefly
        flashMoodColor(mood);
        
        writeMoodLog(time, mood, message);
    }
    
    private void flashMoodColor(String mood) {
        Color moodColor = moodColors.get(mood);
        Color originalColor = outputArea.getBackground();
        
        Timer flashTimer = new Timer(100, null);
        flashTimer.addActionListener(new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count % 2 == 0) {
                    outputArea.setBackground(new Color(moodColor.getRed(), moodColor.getGreen(), moodColor.getBlue(), 50));
                } else {
                    outputArea.setBackground(originalColor);
                }
                count++;
                if (count >= 4) {
                    flashTimer.stop();
                    outputArea.setBackground(originalColor);
                }
            }
        });
        flashTimer.start();
    }

    private void handleUserInput() {
        String input = inputField.getText().trim().toLowerCase();
        String time = LocalDateTime.now().toString();
        inputField.setText("");

        String response;

        // Your original logic preserved exactly
        if (input.contains("happy") || input.contains("great") || input.contains("yay")) {
            response = "Oh wow, someone's happy. Must be nice to have emotions.";
        } else if (input.contains("sad") || input.contains("broke") || input.contains("tired")) {
            response = "Cry me a river. Then build a bridge and get over it.";
        } else if (input.contains("i did") || input.contains("i won") || input.contains("i made")) {
            response = "Congratulations. Want a digital cookie?";
        } else {
            switch (input) {
                case "cheer up":
                    response = "Mood: Aww thanks, I feel 0.1% better.";
                    break;
                case "shut up":
                    response = "Mood: Rude. But okay...";
                    break;
                case "save personality":
                    response = "Saving personality to... the void.";
                    break;
                case "exit":
                    response = "Leaving me? Typical. Bye.";
                    System.exit(0);
                    break;
                default:
                    response = "What was that? I'll pretend it made sense.";
            }
        }

        // Enhanced output formatting
        String formattedOutput = String.format("You: %s\nBot: %s", input, response);
        outputArea.append(formattedOutput + "\n" + createSeparator(50) + "\n\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
        
        writeMoodLog(time, "UserInput", input + " => " + response);
    }

    private void writeMoodLog(String time, String mood, String message) {
        try {
            // Create logs directory if it doesn't exist
            java.io.File logDir = new java.io.File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            
            // Use timestamp in filename to avoid conflicts
            String timestamp = java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")
            );
            String logFileName = "logs/mood_history_" + timestamp + ".log";
            
            try (FileWriter fw = new FileWriter(logFileName, true)) {
                fw.write(time + " | " + mood + " | " + message + "\n");
            }
        } catch (IOException e) {
            outputArea.append("Error writing mood log.\n");
        }
    }

    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            try {
                // Fallback to cross-platform look and feel
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // Use default if both fail
            }
        }
        
        SwingUtilities.invokeLater(() -> new MoodSwingSimulatorGUI().setVisible(true));
    }
}