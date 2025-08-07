import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Clinic {
    private File patientFile;
    private int day;

    public Clinic(File file) {
        this.patientFile = file;
        this.day = 1;
    }

    public Clinic(String fileName) {
        this(new File(fileName));
    }

    public String nextDay(File f) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(f);
        Scanner inputScanner = new Scanner(System.in);
        StringBuilder result = new StringBuilder();

        while (fileScanner.hasNextLine()) {
            String[] parts = fileScanner.nextLine().split(",");
            if (parts.length != 4) continue;

            String name = parts[0];
            String type = parts[1];
            String statStr = parts[2];
            String timeInStr = parts[3];

            int timeIn = Integer.parseInt(timeInStr);
            int stat = Integer.parseInt(statStr);

            System.out.printf("Consultation for %s the %s at %s.\n", name, type, timeInStr);
            System.out.printf("What is the health of %s?\n", name);

            double health;
            while (true) {
                try {
                    health = Double.parseDouble(inputScanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }

            System.out.printf("On a scale of 1 to 10, how much pain is %s in right now?\n", name);

            int painLevel;
            while (true) {
                try {
                    painLevel = Integer.parseInt(inputScanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number");
                }
            }

            Pet pet;
            if (type.equals("Dog")) {
                pet = new Dog(name, health, painLevel, Double.parseDouble(statStr));
            } else if (type.equals("Cat")) {
                pet = new Cat(name, health, painLevel, Integer.parseInt(statStr));
            } else {
                throw new InvalidPetException();
            }

            pet.speak();
            int treatTime = pet.treat();
            String timeOut = addTime(timeInStr, treatTime);

            result.append(String.format(
                "%s,%s,%s,Day %d,%s,%s,%.1f,%d\n",
                name, type, statStr, day, timeInStr, timeOut, health, painLevel
            ));
        }

        day++;
        return result.toString().trim();
    }

    public String nextDay(String fileName) throws FileNotFoundException {
        return nextDay(new File(fileName));
    }

    public boolean addToFile(String patientInfo) {
        try {
            Map<String, List<String>> map = new LinkedHashMap<>();

            if (patientFile.exists()) {
                Scanner scanner = new Scanner(patientFile);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    String name = parts[0];

                    map.putIfAbsent(name, new ArrayList<>());
                    map.get(name).add(line);
                }
                scanner.close();
            }

            String[] newParts = patientInfo.split(",");
            String name = newParts[0];

            if (map.containsKey(name)) {
                map.get(name).add(String.join(",", Arrays.copyOfRange(newParts, 3, newParts.length)));
            } else {
                map.put(name, new ArrayList<>());
                map.get(name).add(patientInfo);
            }

            PrintWriter writer = new PrintWriter(patientFile);
            for (List<String> lines : map.values()) {
                for (String line : lines) {
                    writer.println(line);
                }
            }
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String addTime(String timeInStr, int treatmentTime) {
        int hour = Integer.parseInt(timeInStr.substring(0, 2));
        int minute = Integer.parseInt(timeInStr.substring(2, 4));

        minute += treatmentTime;
        hour += minute / 60;
        minute %= 60;

        return String.format("%02d%02d", hour, minute);
    }
}
