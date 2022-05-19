package Grazer_Bluemchen;

import java.io.PrintStream;
import java.util.Scanner;

public class UNOApp {
    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private AllPlayers allPlayers = new AllPlayers();
    private CardDeck deck = new CardDeck();
    private int currentPlayerNumber;
    private Player currentPlayer;
    private Card topCard;
    private Card playedCard;

    // constructor
    public UNOApp(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
        currentPlayerNumber = 1;
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
            allPlayers.addPlayer(new Human(input, output));
        }
        for (int i = 0; i < botCount; i++) { // dann Bots
            allPlayers.addPlayer(new Bot(input, output));
        }

        // drawPile erstellen
        deck.createDrawPile();
        deck.shuffle();

        // discardPile erstellen
        deck.addToDiscardPile();
        topCard = deck.discardpile.get(0);

        // nachzählen, ob gesamt 108 Karten sind!
        System.out.println("Karten im Spiel: " + (deck.discardpile.size() + deck.drawpile.size() + allPlayers.countAllPlayerCards()));

        // name eingeben
        int botNumber = 1;
        for (Player p : allPlayers.allPlayer) {
            if(p instanceof Human) {
                output.println("Write your name: ");
                p.setName(input.next());
                p.handCards = deck.dealCards(7);
            } else {
                p.setName("Bot" + botNumber);
                botNumber++;
                p.handCards = deck.dealCards(7);
                System.out.println(p.getName());
            }
        }

        //CurrentPlayer initialize (damit direkt auf Player zugegriffen werden kann)
        currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);

        // spielrichtung
        // random start player

    }

    private void inputPlayer() {
        // Bot legt Karte
        if(currentPlayer instanceof Bot) {
            playedCard = currentPlayer.searchHandCards(topCard);
            if(playedCard == null) {
                currentPlayer.handCards.addAll(deck.dealCards(1)); // bot hebt 1 card ab
                return;
            }
        } else { // Spieler legt Karte
            // wenn Eingabe h: eine Karte heben
            playedCard = currentPlayer.searchHandCards(topCard);
            if(playedCard == null) {
                currentPlayer.handCards.addAll(deck.dealCards(1));
                // print handcards
                System.out.print(currentPlayer.getName() + ": ");
                currentPlayer.printHandCards();

                playedCard = currentPlayer.searchHandCards(topCard);
            }
        }
        output.println("Du hast Karte: " + playedCard + " gespielt.");

    }

    private void updateState() {
        //TODO: Benutzereingaben verarbeiten
        // ist Karte gültig?
        // wenn ja, nächster Spieler

        // Card valid? - boolean
        int valid = 2; // 0: not valid card, 1: valid card played, 2: no card played (only picked a card), 3: special card was played (Farbwunsch)
        if(playedCard != null) {
            valid = deck.checkCard(playedCard);
        }

        // check Card action (Aktionskarten?)

        // move Cards
        if(valid == 1) {
            currentPlayer.removeHandCard(playedCard); // remove from handCards
            deck.addCardToDiscard(playedCard); // add to discardpile
        } else if (valid == 0){
            output.println("Diese Karte ist nicht gültig!");
            return;
        } else if(valid == 2) {
            output.println("nächster Spieler ist dran");
        } else if (valid == 3) {
            // nach Farbwunsch fragen
            // als Variable speichern (Enum)
        }
        // random start player in constructor anpassen??
        // Player Reihenfolge:
        if(currentPlayerNumber < 4) {
            currentPlayerNumber++;
        } else {
            currentPlayerNumber = 1;
        }
        currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);

        // nachzählen, ob gesamt 108 Karten sind!
        System.out.println("Karten im Spiel: " + (deck.discardpile.size() + deck.drawpile.size() + allPlayers.countAllPlayerCards()));

    }

    private void printState() {
        //TODO: Ausgabe des aktuellen Zustands
        // der Spieler der gerade dran ist: sieht handCards

        // print handCards from currentPlayer
        System.out.print(currentPlayer.getName() + ": ");
        currentPlayer.printHandCards();

        // erste Karte wird aufgedeckt
        deck.printDiscardPile();

    }

    // kommandos:
    // gültiger Name einer Karte
    // karte abheben

    // Hilfe anzeigen
    // aktuelle Punkte
    // uno rufen

}

