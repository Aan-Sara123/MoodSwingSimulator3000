import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MoodSwingWebMockerGUI {
    private static final String[] mockInsults = {
        "Oh, you typed *that*? Bold of you to waste both our time. 🤡",
        "Fascinating. I almost fell asleep reading that. 😴",
        "You're really proud of that, aren't you? 🙄",
        "Incredible. That input made me dumber. 🤯",
        "Try again, preferably with some actual thought. 🧠❌"
    };

    private static final String[] niceReplies = {
        "That was actually quite thoughtful! 🌟",
        "You're doing great, sweetie. 💖",
        "Aww, you tried your best! 🥹",
        "Look at you go, making progress. 👏",
        "I'm proud of you. No sarcasm. 😇"
    };

    private static final String[] weirdReplies = {
        "Your words taste like purple static. 🍇📡",
        "I see sounds and hear colors now. Thanks. 🎨👂",
        "01001000 01100101 01101100 01101100 🛸",
        "Did you just awaken my 8th consciousness? 🧠🌀",
        "Quack! Sorry, I slipped into duck mode. 🦆"
    };

    private static final String[] idleInsults = {
        "How dare you ignore me?! I'm literally the best part of your day. 😤",
        "Tick-tock, human. I don't have all eternity! ⏳",
        "Wow. Five seconds of silence? Riveting. 🥱",
        "Your idle behavior is as exciting as a spreadsheet. 📊 (and not even a good one)",
        "Blink twice if you're still alive... or just type. 👀"
    };

    private enum Mood { MOCK, NICE, WEIRD }
    private static Mood currentMood = Mood.MOCK;

    private static final Random random = new Random();
    private static JPanel chatPanel;
    private static JLabel insultCounterLabel;
    private static JLabel moodLabel;
    private static int insultCount = 0;
    private static Timer idleTimer;
    private static Timer roastDuelTimer;
    private static boolean inRoastDuel = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MoodSwingWebMockerGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Mocking Mood Machine 9000 💅😈");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLocationRelativeTo(null);

        // Main panel with gradient background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(245, 210, 255), 
                    getWidth(), getHeight(), new Color(180, 220, 255)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Header with improved styling
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel heading = new JLabel("Welcome, Try Not to Embarrass Yourself 💀", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setForeground(new Color(80, 0, 100));
        heading.setBorder(new EmptyBorder(0, 0, 15, 0));
        headerPanel.add(heading, BorderLayout.NORTH);

        // Stats panel with glass effect
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(new MatteBorder(0, 0, 2, 0, new Color(100, 100, 150, 100)));
        
        moodLabel = new JLabel("Mood: MockBot 😈");
        moodLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        moodLabel.setForeground(new Color(90, 30, 90));
        moodLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        insultCounterLabel = new JLabel("Insults Delivered: 0 💥");
        insultCounterLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        insultCounterLabel.setForeground(Color.DARK_GRAY);
        insultCounterLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        statsPanel.add(moodLabel);
        statsPanel.add(new JSeparator(SwingConstants.VERTICAL));
        statsPanel.add(insultCounterLabel);
        headerPanel.add(statsPanel, BorderLayout.SOUTH);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Chat panel with subtle border
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setOpaque(false);
        chatPanel.setBorder(new EmptyBorder(15, 5, 15, 5));

        JScrollPane scrollPane = new JScrollPane(chatPanel);
        scrollPane.setPreferredSize(new Dimension(700, 350));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(150, 150, 200, 100), 2, true),
            new EmptyBorder(10, 10, 10, 10)
        ));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input panel with modern styling
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(140, 140, 240), 2, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        inputField.setBackground(new Color(255, 255, 255, 220));

        JButton sendButton = createStyledButton("Insult Me 🤡", new Color(255, 105, 180), new Color(230, 80, 150));
        JButton duelButton = createStyledButton("🔥 Roast Duel Mode", new Color(255, 150, 50), new Color(230, 120, 30));

        duelButton.addActionListener(e -> {
            if (!inRoastDuel) {
                inRoastDuel = true;
                addChatBubble("⚔️ Roast Duel Initiated! Type fast! 30 seconds of roast!", false);
                roastDuelTimer.restart();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(sendButton);
        buttonPanel.add(duelButton);

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Action handling
        ActionListener respondAction = e -> {
            resetIdleTimer();
            String userInput = inputField.getText().trim();
            if (userInput.isEmpty()) {
                addChatBubble("Say something or stay useless. Your choice. 😒", false);
                return;
            }

            addChatBubble("You: " + userInput, true);

            if (inRoastDuel) {
                String roast = mockInsults[random.nextInt(mockInsults.length)];
                insultCount++;
                insultCounterLabel.setText("Insults Delivered: " + insultCount + " 💥");
                addChatBubble("MockBot: " + roast, false);
            } else {
                String reply;
                switch (currentMood) {
                    case NICE -> reply = niceReplies[random.nextInt(niceReplies.length)];
                    case WEIRD -> reply = weirdReplies[random.nextInt(weirdReplies.length)];
                    case MOCK -> {
                        reply = mockInsults[random.nextInt(mockInsults.length)];
                        insultCount++;
                        insultCounterLabel.setText("Insults Delivered: " + insultCount + " 💥");
                    }
                    default -> reply = "Mood error. Please re-mock me. 💔";
                }
                addChatBubble("Bot: " + reply, false);
            }

            inputField.setText("");
        };

        sendButton.addActionListener(respondAction);
        inputField.addActionListener(respondAction);

        idleTimer = new Timer(5000, e -> {
            String insult = idleInsults[random.nextInt(idleInsults.length)];
            addChatBubble("MockBot (Idle): " + insult, false);
        });
        idleTimer.setRepeats(false);
        idleTimer.start();

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                resetIdleTimer();
                switchMoodRandomly();
            }
        });

        roastDuelTimer = new Timer(30000, e -> {
            inRoastDuel = false;
            addChatBubble("MockBot: MockBot Wins, obviously. 💅🔥", false);
        });

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(bgColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(180, 45));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private static void addChatBubble(String message, boolean user) {
        JPanel bubble = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Bubble shadow
                g2.setColor(new Color(0, 0, 0, 30));
                g2.fillRoundRect(1, 3, getWidth()-2, getHeight()-2, 30, 30);
                
                // Bubble background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth()-2, getHeight()-3, 30, 30);
            }
        };
        
        bubble.setLayout(new BorderLayout());
        bubble.setOpaque(false);
        bubble.setBackground(user ? new Color(200, 255, 200) : new Color(255, 220, 250));
        bubble.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel label = new JLabel("<html><body style='width: 380px; padding: 3px'>" + message + "</body></html>");
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        label.setForeground(Color.DARK_GRAY);

        bubble.add(label, BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(8, 8, 8, 8));
        wrapper.add(bubble, user ? BorderLayout.EAST : BorderLayout.WEST);

        chatPanel.add(wrapper);
        chatPanel.add(Box.createVerticalStrut(8));
        chatPanel.revalidate();
        chatPanel.repaint();

        // Auto-scroll to bottom
        JScrollBar vertical = ((JScrollPane) chatPanel.getParent().getParent()).getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private static void switchMoodRandomly() {
        Mood[] moods = Mood.values();
        Mood newMood = moods[random.nextInt(moods.length)];
        currentMood = newMood;
        String moodText = switch (newMood) {
            case MOCK -> "Mood: MockBot 😈";
            case NICE -> "Mood: NiceBot 😇";
            case WEIRD -> "Mood: WeirdBot 👽";
        };
        moodLabel.setText(moodText);
    }

    private static void resetIdleTimer() {
        if (idleTimer != null) {
            idleTimer.restart();
        }
    }
}