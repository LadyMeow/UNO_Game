package Grazer_Bluemchen;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        UNOApp app = new UNOApp(input, System.out);
        app.Run();

        while (input.nextLine().equalsIgnoreCase("j")) {
            app.Run();
        }

        input.close();
        System.out.println("Das Programm wird beendet ...");

    }
}
