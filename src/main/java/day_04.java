import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class day_04 {
    private static List<String> necessaryCredentials = List.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    public static void main(String[] args) {

        System.out.println("--- Start ---");

        System.out.println("Puzzle answer for part 1: " + task(1));
        System.out.println("Puzzle answer for part 2: " + task(2));

        System.out.println("---- End ----");
    }

    private static long task(int subtask) {
        var count = 0L;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(".\\src\\main\\resources\\day_4.txt"));
            String line = reader.readLine();
            while (line != null) {
                StringBuilder credentialString = new StringBuilder(line);
                line = reader.readLine();
                while (line != null && !line.equals("")) {
                    credentialString.append(" ").append(line);
                    line = reader.readLine();
                }
                line = reader.readLine();

                switch (subtask) {
                    case 1:
                        if (part_1(credentialString.toString())) count++;
                        break;
                    case 2:
                        if (part_2(credentialString.toString())) count++;
                        break;
                    default:
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private static boolean part_1(String credentialString) {
        var credentials = getAllCredentials(credentialString);
        return (areValid(credentials));
    }

    private static boolean part_2(String credentialString) {
        var credentials = getAllCredentials(credentialString);
        if (!areValid(credentials)) return false;

        var birthYear = credentials.get("byr");
        if (isOutside(birthYear, 1920, 2002)) return false;

        var issueYear = credentials.get("iyr");
        if (isOutside(issueYear, 2010, 2020)) return false;

        var expirationYear = credentials.get("eyr");
        if (isOutside(expirationYear, 2020, 2030)) return false;

        var height = credentials.get("hgt");
        var heightUnit = height.substring(height.length() - 2);
        var heightValue = height.substring(0, height.length() - 2);
        if (heightUnit.equals("cm")) {
            if (isOutside(heightValue, 150, 193)) return false;
        } else if (heightUnit.equals("in")) {
            if (isOutside(heightValue, 59, 76)) return false;
        } else {
            return false;
        }

        var hairColor = credentials.get("hcl");
        if (!hairColor.matches("#[0-9a-f]{6}")) return false;

        var eyeColor = credentials.get("ecl");
        if (!eyeColor.matches("amb|blu|brn|gry|grn|hzl|oth")) return false;

        var passportId = credentials.get("pid");
        return passportId.matches("\\d{9}");
    }

    private static boolean areValid(Map<String, String> credentials) {
        return credentials.keySet().containsAll(necessaryCredentials);
    }

    private static Map<String, String> getAllCredentials(String set) {
        return Pattern.compile("\\s* \\s*")
                .splitAsStream(set.trim())
                .map(keyValueString -> keyValueString.split(":", 2))
                .collect(Collectors.toMap(entrySet -> entrySet[0], entrySet -> entrySet.length > 1 ? entrySet[1] : ""));
    }

    private static boolean isOutside(String value, int left, int right) {
        var intValue = Integer.parseInt(value);
        return intValue < left || intValue > right;
    }

}
