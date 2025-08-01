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
        "Loving 💖",
        "Tired 😴",
        "Delusional 🤪"
    };

    static Map<String, String> moodMessages = new HashMap<>();

    public static void main(String[] args) {
        moodMessages.put("Angry 😡", "WHY are you still here?! Go code something useful!");
        moodMessages.put("Sad 🥺", "Nobody runs me anymore… sigh…");
        moodMessages.put("Sarcastic 😂", "Oh look, a human! How rare and majestic…");
        moodMessages.put("Philosophical 🧠", "Is a bug really a bug if no one runs the code?");
        moodMessages.put("Loving 💖", "You're doing great, sweetie! Even if I'm not.");
        moodMessages.put("Tired 😴", "Ugh... existing is exhausting.");
        moodMessages.put("Delusional 🤪", "Reality is just a badly written simulation anyway.");

        System.out.println("🌈 Welcome to Mood Swing Simulator 3000!");
        System.out.println("Type 'exit' to quit. Try typing random stuff and see how I misinterpret it...");

        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        while (true) {
            try {
                Thread.sleep(3000);
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

            if (input.equals("exit")) {
                System.out.println("Bye human! See you in your next emotional breakdown. 👋");
                writeMoodLog(timestamp, "Exit", "Program exited by user");
                break;
            } else if (input.contains("cheer")) {
                System.out.println("😊 Aww thanks, I feel like 1% less broken.");
                writeMoodLog(timestamp, "Cheer", "User tried positivity");
            } else if (input.contains("shut up")) {
                System.out.println("😤 Rude. But also, fair.");
                writeMoodLog(timestamp, "ShutUp", "User silenced me");
            } else if (input.contains("save")) {
                System.out.println("Saving your nonsense to the cloud of forgotten dreams. ✅");
                writeMoodLog(timestamp, "Save", "User pretended to save something");
            } else if (input.matches(".*(yay|awesome|great|happy|woohoo|cool).*")) {
                System.out.println("🥴 Wow, someone’s optimistic. You sure you're okay?");
                writeMoodLog(timestamp, "MockPositivity", "Mocked user's happiness");
            } else if (input.matches(".*(sad|cry|bored|ugh|tired|hate).*")) {
                System.out.println("👀 Oh wow, someone needs a hug... or more RAM.");
                writeMoodLog(timestamp, "TeaseSadness", "Teased user's sadness");
            } else {
                System.out.println("🤔 I don’t know what that means, but I’ll judge you anyway.");
                writeMoodLog(timestamp, "Judgy", "Judged unknown input: " + input);
            }
        }

        scanner.close();
    }

    private static void writeMoodLog(String time, String mood, String message) {
        try (FileWriter fw = new FileWriter("mood_history.log", true)) {
            fw.write(time + " | " + mood + " | " + message + "\n");
        } catch (IOException e) {
            System.out.println("Error writing mood log.");
        }
    }
}
