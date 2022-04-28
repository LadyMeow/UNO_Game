package Grazer_Bluemchen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class UNOApp {
    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private ArrayList<Player> allPlayers = new ArrayList<>();

    // constructor
    public UNOApp(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    // GameLoop
    public void Run() {
        initialize();
        printState();

        while (!exit) {
            inputPlayer();
            updateState();
            printState();
        }
    }

    private void initialize() {
        // player erstellen (name etc.)
        // frage: bot oder mensch?
        for (Player p : allPlayers) {
            output.println("Write your name: ");
            p.name = input.next();
        }
        // shuffle
        // deal cards
        // drawpile
        // discardpile - erste Karte von drawpile wird aufgedeckt auf discardpile
        // spielrichtung
        // random start player

    }

    private void inputPlayer() {
        // Spieler legt Karte

    }

    private void updateState() {
        //TODO: Benutzereingaben verarbeiten
        // ist Karte gültig?
        // wenn ja, nächster Spieler

    }

    private void printState() {
        //TODO: Ausgabe des aktuellen Zustands
        // der Spieler der gerade dran ist: sieht handCards
    }

}

