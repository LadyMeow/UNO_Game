package Grazer_Bluemchen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class UNOApp {
    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private AllPlayers allPlayers = new AllPlayers();

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
            //printState();
        }
    }

    private void initialize() {
        // player erstellen (name etc.)
        Player p1 = new Player();
        Player p2 = new Player();
        Player p3 = new Player();
        Player p4 = new Player();

        allPlayers.addPlayer(p1);
        allPlayers.addPlayer(p2);
        allPlayers.addPlayer(p3);
        allPlayers.addPlayer(p4);

        // drawPile erstellen
        CardDeck drawPile = new CardDeck().createDrawPile();
        drawPile.shuffle();

        // discardPile erstellen
        CardDeck discardPile = new CardDeck().createDiscardPile();

        // frage: bot oder mensch?
        for (Player p : allPlayers.allPlayer) {
            output.println("Write your name: ");
            p.name = input.next();
            p.handCards = drawPile.dealCards(7);
        }

        // shuffle
        // deal cards
        // drawpile - new CardDeck
        // discardpile - new CardDeck - erste Karte von drawpile wird aufgedeckt auf discardpile
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

        // random start player = index
        System.out.print(allPlayers.getPlayer(0).name + ": ");
        allPlayers.getPlayer(0).printHandCards();

        // erste Karte wird aufgedeckt


    }

}

