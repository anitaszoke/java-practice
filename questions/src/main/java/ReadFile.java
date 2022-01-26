import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFile {
    //separator nem lehet "?" -> ";" kell a válasz és kérdés közé
    public static final String SEPARATOR = ";";

    private List<String> questions = new ArrayList<>();
    private List<String> answers = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    //fájl beolvasása soronként, private metódus hívás - parseLine

    private void read(Path path) {
        String line;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
            while ((line = reader.readLine()) != null) {
                parseLine(line);

            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Error by parsing, general io", ioe);
        }
    }

    //a sorokat a separatornál elválasztja és 0.részt a kérdések, a 1.részt a válaszok listájához adja

    private void parseLine(String line) {
        String[] fullLine = line.split(SEPARATOR);
        String question = fullLine[0];
        String answer = fullLine[1];

        questions.add(question);
        answers.add(answer);
    }

    //generál egy random számot 0 és a kérdések max száma között

    private int randomNumberGenerator() {
        int randomMax = questions.size();
        return (int) (Math.random() * (randomMax));
    }
    //ellenőrzi, hogy vane kérdés, majd felhasz dönt, 1-válasz 2-köv. kérdés 3-kilépés, addig fut, amíg van kérdés a listában

    private void generateQuestion() {
        int index = randomNumberGenerator();

        if (questions.size() > 0) {
            System.out.println(questions.get(index));
            questions.remove(index);
            System.out.println();
            System.out.println("Válasz megmutatása - 1, következő kérdés - 2, kilépés - 3");
            int nextMove = scanner.nextInt();
            System.out.println();
            if (nextMove == 1) {
                System.out.println(answers.get(index));
                answers.remove(index);
                System.out.println();
                generateQuestion();

            } else if (nextMove == 2) {
                System.out.println();
                generateQuestion();

            } else if (nextMove == 3) {
                System.out.println("Kilépés");
            }
        } else {
            System.out.println("Megválaszolta az összes kérdést");
        }
    }


    //bekéri a fájl elérési útvonalát, elindítja a kérdezz feleleket

    public void runMenu() {
        System.out.println("Kérem adja meg a fájl elérési útvonalát");
        String path = scanner.nextLine();
        read(Path.of(path));
        System.out.println();
        generateQuestion();
    }
}
