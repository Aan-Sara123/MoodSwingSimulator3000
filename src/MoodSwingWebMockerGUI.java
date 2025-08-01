import javax.swing.*;
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
        "You’re doing great, sweetie. 💖",
        "Aww, you tried your best! 🥹",
        "Look at you go, making progress. 👏",
        "I’m proud of you. No sarcasm. 😇"
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
        "Tick-tock, human. I don’t have all eternity! ⏳",
        "Wow. Five seconds of silence? Riveting. 🥱",
        "Your idle behavior is as exciting as a spreadsheet. 📊 (and not even a good one)",
        "Blink twice if you’re still alive... or just type. 👀"
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
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(255, 190, 232), 0, getHeight(),
                        new Color(168, 204, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("Welcome, Try Not to Embarrass Yourself 💀", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(70, 0, 90));
        panel.add(heading, BorderLayout.NORTH);

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(chatPanel);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setOpaque(false);

        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(140, 140, 240), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JButton sendButton = new JButton("Insult Me 🤡");
        sendButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        sendButton.setBackground(new Color(255, 105, 180));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);

        JButton duelButton = new JButton("🔥 Roast Duel Mode");
        duelButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        duelButton.setBackground(new Color(255, 150, 50));
        duelButton.setForeground(Color.WHITE);
        duelButton.setFocusPainted(false);
        duelButton.addActionListener(e -> {
            if (!inRoastDuel) {
                inRoastDuel = true;
                addChatBubble("⚔️ Roast Duel Initiated! Type fast! 30 seconds of roast!", false);
                roastDuelTimer.restart();
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(sendButton, BorderLayout.CENTER);
        buttonPanel.add(duelButton, BorderLayout.EAST);

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setOpaque(false);
        insultCounterLabel = new JLabel("Insults Delivered: 0 💥");
        insultCounterLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        insultCounterLabel.setForeground(Color.DARK_GRAY);

        moodLabel = new JLabel("Mood: MockBot 😈");
        moodLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        moodLabel.setForeground(new Color(90, 30, 90));

        topRightPanel.add(moodLabel);
        topRightPanel.add(Box.createHorizontalStrut(20));
        topRightPanel.add(insultCounterLabel);
        panel.add(topRightPanel, BorderLayout.NORTH);

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
                    default -> {
                        reply = mockInsults[random.nextInt(mockInsults.length)];
                        insultCount++;
                        insultCounterLabel.setText("Insults Delivered: " + insultCount + " 💥");
                    }
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

    private static void addChatBubble(String message, boolean user) {
        JPanel bubble = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
                // Set bubble color
                g2.setColor(getBackground());
    
                // Draw rounded rectangle bubble
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
    
        bubble.setLayout(new BorderLayout());
        bubble.setOpaque(false); // So paintComponent works correctly
        bubble.setBackground(user ? new Color(200, 255, 200) : new Color(255, 220, 250));
        bubble.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
    
        JLabel label = new JLabel("<html><body style='width: 360px'>" + message + "</body></html>");
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
        label.setForeground(Color.DARK_GRAY);
    
        bubble.add(label, BorderLayout.CENTER);
    
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(bubble, user ? BorderLayout.EAST : BorderLayout.WEST);
    
        chatPanel.add(wrapper);
        chatPanel.add(Box.createVerticalStrut(10));
        chatPanel.revalidate();
        chatPanel.repaint();
    
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
