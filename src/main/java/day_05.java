import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day_05 {

    public static void main(String[] args) {

        System.out.println("--- Start ---");

        var seatIds = readAndCalculateSeatIdsFromFile(".\\src\\main\\resources\\day_05.txt");

        Collections.sort(seatIds);

        var max = seatIds.get(seatIds.size() - 1);
        var freeSeatId = findSeatIdOfFreeSeat(seatIds);

        System.out.println("Puzzle answer for part 1: " + max);
        System.out.println("Puzzle answer for part 2: " + freeSeatId);

        System.out.println("---- End ----");
    }

    private static Integer findSeatIdOfFreeSeat(List<Integer> seatIds) {
        for (var freeSeatId = seatIds.get(0) + 1; freeSeatId < seatIds.size(); freeSeatId++) {
            if (!seatIds.contains(freeSeatId)) {
                return freeSeatId;
            }
        }
        return null;
    }

    private static List<Integer> readAndCalculateSeatIdsFromFile(String fileName) {
        var seatIds = new ArrayList<Integer>();

        try {
            var reader = new BufferedReader(new FileReader(fileName));
            while (reader.ready()) {
                seatIds.add(calculateSeatIdFrom(reader.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return seatIds;
    }

    private static Integer calculateSeatIdFrom(String seatString) {
        var j = 0;
        var row = 0;
        var col = 0;

        for (var i = 64; i > 0; i = i / 2) {
            if (seatString.charAt(j++) == 'B') row = row + i;
            if (j > 3 && seatString.charAt(j + 2) == 'R') col = col + i;
        }

        return row * 8 + col;
    }
}