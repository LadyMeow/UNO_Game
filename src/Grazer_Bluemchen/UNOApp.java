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

        // name eingeben
        for (Player p : allPlayers.allPlayer) {
            if(p instanceof Human) {
                output.println("Write your name: ");
                p.name = input.next();
                p.handCards = deck.dealCards(7);
            } else {
                p.name = "Bot";
                p.handCards = deck.dealCards(7);
                System.out.println("Bot");
            }
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
        output.println("Spiele eine deiner Karten: ");
        ArrayList<Card> presentCards = allPlayers.getPlayer(currentPlayer).handCards;

        String playCard = input.next();
        for (Card c : presentCards) {
            if(Objects.equals(playCard, c.name)) {
                allPlayers.getPlayer(currentPlayer).playCard(c);
                deck.discardpile.add(c);
                break;
            }
        }

    }

    private void updateState() {
        //TODO: Benutzereingaben verarbeiten
        // ist Karte gültig?
        // wenn ja, nächster Spieler

        // nachzählen ob gesamt 108 Karten sind!!

    }

    private void printState() {
        //TODO: Ausgabe des aktuellen Zustands
        // der Spieler der gerade dran ist: sieht handCards

        deck.printDiscardPile();

        // random start player = index
        if(currentPlayer < 4) {
            currentPlayer++;
        } else {
            currentPlayer = 1;
        }
        System.out.print(allPlayers.getPlayer(currentPlayer).name + ": ");
        allPlayers.getPlayer(currentPlayer).printHandCards();
        // erste Karte wird aufgedeckt
        deck.printDiscardPile();

    }

}

