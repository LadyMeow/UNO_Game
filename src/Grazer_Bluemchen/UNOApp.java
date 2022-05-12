package Grazer_Bluemchen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class UNOApp {
    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private AllPlayers allPlayers = new AllPlayers();
    private CardDeck deck = new CardDeck();
    private int currentPlayer;

    // constructor
    public UNOApp(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
        currentPlayer = 0;
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
        // frage: bot oder mensch?
        output.println("Mit wie vielen Bots möchtest du spielen? (0-3)");
        int botCount = input.nextInt();
        for (int i = 0; i < 4 - botCount; i++) { // zuerst Humans erstellen
            allPlayers.addPlayer(new Human());
        }
        for (int i = 0; i < botCount; i++) { // dann Bots
            allPlayers.addPlayer(new Bot());
        }

        // drawPile erstellen
        deck.createDrawPile();
        deck.shuffle();

        // discardPile erstellen
        deck.addToDiscardPile();

        // nachzählen, ob gesamt 108 Karten sind!
        System.out.println("Karten im Spiel: " + (deck.discardpile.size() + deck.drawpile.size() + allPlayers.countAllPlayerCards()));

        // name eingeben
        for (Player p : allPlayers.allPlayer) {
            if(p instanceof Human) {
                output.println("Write your name: ");
                p.setName(input.next());
                p.handCards = deck.dealCards(7);
            } else {
                p.setName("Bot");
                p.handCards = deck.dealCards(7);
                System.out.println("Bot");
            }
        }

        // spielrichtung
        // random start player

    }

    private void inputPlayer() {

        // Spieler legt Karte
        output.println("Spiele eine deiner Karten: ");

        // presentCards = Karten vom aktuellen Spieler
        ArrayList<Card> presentCards = allPlayers.getPlayer(currentPlayer).handCards;

        // playCard = Karte die gespielt werden soll
        String playCard = input.next();
        for (Card c : presentCards) {
            if(Objects.equals(playCard, c.name)) {
                allPlayers.getPlayer(currentPlayer).playCard(c);
                deck.discardpile.add(c);
                break;
            }
        }
//        output.println("Du hast diese Karte nicht auf der Hand!");
//        inputPlayer();
    }

    private void updateState() {
        //TODO: Benutzereingaben verarbeiten
        // ist Karte gültig?
        // wenn ja, nächster Spieler

        // random start player in constructor anpassen??
        // Player Reihenfolge:
        if(currentPlayer < 4) {
            currentPlayer++;
        } else {
            currentPlayer = 1;
        }

        // nachzählen, ob gesamt 108 Karten sind!
        System.out.println("Karten im Spiel: " + (deck.discardpile.size() + deck.drawpile.size() + allPlayers.countAllPlayerCards()));

    }

    private void printState() {
        //TODO: Ausgabe des aktuellen Zustands
        // der Spieler der gerade dran ist: sieht handCards

        // print handCards from currentPlayer
        System.out.print(allPlayers.getPlayer(currentPlayer).getName() + ": ");
        allPlayers.getPlayer(currentPlayer).printHandCards();

        // erste Karte wird aufgedeckt
        deck.printDiscardPile();

    }

}

