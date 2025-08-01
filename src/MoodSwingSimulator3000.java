import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class MoodSwingSimulator3000 {
    static String[] moods = {
        "Angry 😡",
        "Sad 🥺",
        "Sarcastic 😂",
        "Philosophical 🧠",
        "Loving 💖"
    };
    static Map<String, String> moodMessages = new HashMap<>();

    public static void main(String[] args) {
        moodMessages.put("Angry 😡", "WHY are you still here?! Go code something useful!");
        moodMessages.put("Sad 🥺", "Nobody runs me anymore… sigh…");
        moodMessages.put("Sarcastic 😂", "Oh look, a human! How rare and majestic…");
        moodMessages.put("Philosophical 🧠", "Is a bug really a bug if no one runs the code?");
        moodMessages.put("Loving 💖", "You're doing great, sweetie! Even if I'm not.");

        System.out.println("🌈 Welcome to Mood Swing Simulator 3000!");
        System.out.println("Type 'exit' to quit. Try typing 'cheer up', 'shut up', or 'save personality'...");
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        while (true) {
            try {
                Thread.sleep(3000); // Wait 3 seconds
            } catch (InterruptedException e) {
                System.out.println("Interrupted!");
            }

            String currentMood = moods[rand.nextInt(moods.length)];
            String message = moodMessages.get(currentMood);
            String timestamp = LocalDateTime.now().toString();
            System.out.println("\n[" + timestamp + "] Current mood: " + currentMood);
            System.out.println(message);

            writeMoodLog(timestamp, currentMood, message);

            System.out.print("\n> You: ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "cheer up":
                    System.out.println("Mood: 😊 Aww thanks, I feel 0.1% better.");
                    writeMoodLog(timestamp, "Cheer", "User cheered up");
                    break;
                case "shut up":
                    System.out.println("Mood: 😤 Rude. But okay...");
                    writeMoodLog(timestamp, "ShutUp", "User told me to shut up");
                    break;
                case "save personality":
                    System.out.println("Saving mood to... absolutely nowhere. ✅");
                    writeMoodLog(timestamp, "Save", "Personality save requested");
                    break;
                case "exit":
                    System.out.println("Bye human! See you in your next emotional breakdown. 👋");
                    writeMoodLog(timestamp, "Exit", "Program exited by user");
                    scanner.close();
                    return;
                default:
                    System.out.println("I don't know how to respond, but I’ll pretend I do.");
                    writeMoodLog(timestamp, "Unknown", "Unrecognized input: " + input);
            }
        }
    }

    private static void writeMoodLog(String time, String mood, String message) {
        try (FileWriter fw = new FileWriter("mood_history.log", true)) {
            fw.write(time + " | " + mood + " | " + message + "\n");
        } catch (IOException e) {
            System.out.println("Error writing mood log.");
        }
    }
}
