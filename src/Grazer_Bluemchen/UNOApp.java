package Grazer_Bluemchen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class UNOApp {
    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;

    ArrayList<Player> allPlayers = new ArrayList<>();

    public UNOApp(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    private void inputPlayer() {
        // Hier sehen Sie ein Pattern für Benutzereingaben
        // Solange kein gültiger Wert eingegeben wurde, wird die Eingabe wiederholt
//        do {
//            output.println("Please write your name; ");
//            playerNr = input.nextInt();
//            if (playerNr < 1 || playerNr > 4) {
//                output.println("Please chose 1-4");
//            } else {
//                break;
//            }
//        } while ();




        for (Player p : allPlayers) {
            output.println("Write your name: ");
            p.name = input.next();
        }

    }
}
