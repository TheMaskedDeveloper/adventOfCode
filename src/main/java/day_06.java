import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class day_06 {

    public static void main(String[] args) {

        System.out.println("--- Start ---");

        var puzzleInput = ".\\src\\main\\resources\\day_06.txt";

        analyze(puzzleInput);

        System.out.println("---- End ----");
    }

    private static void analyze(String puzzleInput) {
        int sumAnyoneAnsweredYes = 0;
        var sumEveryoneAnsweredYes = new AtomicInteger(0);

        try {
            var reader = new BufferedReader(new FileReader(puzzleInput));
            while (reader.ready()) {

                var answerLine = reader.readLine();
                var answerGroup = new ArrayList<String>();

                while (answerLine != null && !answerLine.equals("")) {
                    answerGroup.add(answerLine);
                    answerLine = reader.readLine();
                }

                var charCounter = countEachPresentCharacter(answerGroup);

                sumAnyoneAnsweredYes += charCounter.size();

                charCounter.forEach((k, v) -> {
                    if (v == answerGroup.size()) sumEveryoneAnsweredYes.getAndIncrement();
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Puzzle answer for part 1: " + sumAnyoneAnsweredYes);
        System.out.println("Puzzle answer for part 2: " + sumEveryoneAnsweredYes.get());
    }

    private static HashMap<Character, Integer> countEachPresentCharacter(ArrayList<String> answerSheet) {
        var charCounter = new HashMap<Character, Integer>();
        for (String answer : answerSheet) {
            for (var j = 0; j < answer.length(); j++) {
                charCounter.put(answer.charAt(j), charCounter.getOrDefault(answer.charAt(j), 0) + 1);
            }
        }
        return charCounter;
    }
}
