import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MoodSwingSimulatorGUI {
    private static final Map<String, String[]> moodResponses = new HashMap<>();

    static {
        moodResponses.put("Happy", new String[]{"You're shining like the sun!", "Smile on, superstar!"});
        moodResponses.put("Sad", new String[]{"It’s okay to cry, even rainbows do.", "Sending virtual hugs!"});
        moodResponses.put("Angry", new String[]{"Breathe in... scream into a pillow.", "Smash that keyboard metaphorically!"});
        moodResponses.put("Sarcastic", new String[]{"Oh wow, best day ever, right?", "Totally thrilled to exist today 🙃"});
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MoodSwingSimulator 3000 😵‍💫");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JTextArea outputArea = new JTextArea();
        outputArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));

        for (String mood : moodResponses.keySet()) {
            JButton moodButton = new JButton(mood);
            moodButton.addActionListener(e -> {
                String[] responses = moodResponses.get(mood);
                String randomResponse = responses[new Random().nextInt(responses.length)];
                outputArea.setText("Mood: " + mood + "\nResponse: " + randomResponse);
            });
            buttonPanel.add(moodButton);
        }

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
