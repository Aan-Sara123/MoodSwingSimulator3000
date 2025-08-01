import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;

public class MoodSwingWebMockerGUI {
    private static final String[] insults = {
            "Oh, you typed *that*? Bold of you to waste both our time. 🤡",
            "Fascinating. I almost fell asleep reading that. 😴",
            "You're really proud of that, aren't you? 🙄",
            "Incredible. That input made me dumber. 🤯",
            "Try again, preferably with some actual thought. 🧠❌"
    };

    private static final String[] idleInsults = {
            "How dare you ignore me?! I'm literally the best part of your day. 😤",
            "Tick-tock, human. I don’t have all eternity! ⏳",
            "Wow. Five seconds of silence? Riveting. 🥱",
            "Your idle behavior is as exciting as a spreadsheet. 📊 (and not even a good one)",
            "Blink twice if you’re still alive... or just type. 👀"
    };

    private static final Random random = new Random();
    private static JTextArea responseArea;
    private static Timer idleTimer;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MoodSwingWebMockerGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Mocking Mood Machine 9000 💅😈");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 500);
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

        responseArea = new JTextArea();
        responseArea.setEditable(false);
        responseArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
        responseArea.setLineWrap(true);
        responseArea.setWrapStyleWord(true);
        responseArea.setBackground(new Color(255, 255, 255, 200));
        responseArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 160, 255), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JScrollPane scrollPane = new JScrollPane(responseArea);
        scrollPane.setPreferredSize(new Dimension(550, 250));
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setOpaque(false);

        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(140, 140, 240), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JButton sendButton = new JButton("Insult Me 🤡");
        sendButton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
        sendButton.setBackground(new Color(255, 105, 180));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        panel.add(inputPanel, BorderLayout.SOUTH);

        ActionListener respondAction = e -> {
            resetIdleTimer();
            String userInput = inputField.getText().trim();
            if (userInput.isEmpty()) {
                appendText("Say something or stay useless. Your choice. 😒");
            } else {
                String reply = insults[random.nextInt(insults.length)];
                appendText("You: " + userInput + "\nMockBot: " + reply);
                inputField.setText("");
            }
        };

        sendButton.addActionListener(respondAction);
        inputField.addActionListener(respondAction);

        idleTimer = new Timer(5000, e -> insultForInactivity());
        idleTimer.setRepeats(false);
        idleTimer.start();

        inputField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                resetIdleTimer();
            }
        });

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private static void appendText(String text) {
        responseArea.append(text + "\n\n");
        responseArea.setCaretPosition(responseArea.getDocument().getLength());
    }

    private static void insultForInactivity() {
        String insult = idleInsults[random.nextInt(idleInsults.length)];
        appendText("MockBot (Idle Rude): " + insult);
    }

    private static void resetIdleTimer() {
        if (idleTimer != null) {
            idleTimer.restart();
        }
    }
}