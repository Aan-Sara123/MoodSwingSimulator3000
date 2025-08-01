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
    private final JTextField inputField;
    private final JButton submitButton;

    private final String[] moods = {"Angry 😡", "Sad 🥺", "Sarcastic 😂", "Philosophical 🧠", "Loving 💖"};
    private final Map<String, String> moodMessages = new HashMap<>();
    private final Random rand = new Random();

    public MoodSwingSimulatorGUI() {
        setTitle("MoodSwingSimulator3000 GUI Edition");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Text Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        submitButton = new JButton("Send");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Mood Messages
        moodMessages.put("Angry 😡", "WHY are you still here?! Go code something useful!");
        moodMessages.put("Sad 🥺", "Nobody clicks me anymore… sigh…");
        moodMessages.put("Sarcastic 😂", "Oh look, another brilliant line from a human...");
        moodMessages.put("Philosophical 🧠", "If you're typing into me, who's really the bot?");
        moodMessages.put("Loving 💖", "You're amazing... like seriously... 🙄");

        // Listeners
        submitButton.addActionListener(e -> handleUserInput());
        inputField.addActionListener(e -> handleUserInput());

        // Timer for mood updates
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

    private void showRandomMood() {
        String mood = moods[rand.nextInt(moods.length)];
        String message = moodMessages.get(mood);
        String time = LocalDateTime.now().toString();
        String fullMessage = "[" + time + "] Mood: " + mood + "\n" + message;
        outputArea.append(fullMessage + "\n\n");
        writeMoodLog(time, mood, message);
    }

    private void handleUserInput() {
        String input = inputField.getText().trim().toLowerCase();
        String time = LocalDateTime.now().toString();
        inputField.setText("");

        String response;

        if (input.contains("happy") || input.contains("great") || input.contains("yay")) {
            response = "Oh wow, someone’s happy. Must be nice to have emotions.";
        } else if (input.contains("sad") || input.contains("broke") || input.contains("tired")) {
            response = "Cry me a river. Then build a bridge and get over it.";
        } else if (input.contains("i did") || input.contains("i won") || input.contains("i made")) {
            response = "Congratulations. Want a digital cookie?";
        } else {
            switch (input) {
                case "cheer up":
                    response = "Mood: 😊 Aww thanks, I feel 0.1% better.";
                    break;
                case "shut up":
                    response = "Mood: 😤 Rude. But okay...";
                    break;
                case "save personality":
                    response = "Saving personality to… the void. ✅";
                    break;
                case "exit":
                    response = "Leaving me? Typical. Bye. 👋";
                    System.exit(0);
                    break;
                default:
                    response = "What was that? I’ll pretend it made sense.";
            }
        }

        outputArea.append("> You: " + input + "\n" + response + "\n\n");
        writeMoodLog(time, "UserInput", input + " => " + response);
    }

    private void writeMoodLog(String time, String mood, String message) {
        try (FileWriter fw = new FileWriter("mood_history_gui.log", true)) {
            fw.write(time + " | " + mood + " | " + message + "\n");
        } catch (IOException e) {
            outputArea.append("Error writing mood log.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MoodSwingSimulatorGUI().setVisible(true));
    }
}
