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
    private boolean direction = true; // im Uhrzeigersinn
    private Colors colorWish;

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

    private void initialize() { // wenn keine Zahl von 0-3 eingegeben wird -crush!!
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
        System.out.println("Karten im Spiel: " + (deck.discardpile.size() + deck.drawpile.size() + allPlayers.countAllPlayerCards())); // details!

        // name eingeben
        int botNumber = 1;
        for (Player p : allPlayers.allPlayer) {
            if (p instanceof Human) {
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
        if (currentPlayer instanceof Bot) {
            playedCard = currentPlayer.searchHandCards(topCard);
            if (playedCard == null) {
                currentPlayer.handCards.addAll(deck.dealCards(1)); // bot hebt 1 card ab
                return;
            }
        } else { // Spieler legt Karte
            // wenn Eingabe h: eine Karte heben
            playedCard = currentPlayer.searchHandCards(topCard);

            if (playedCard == null) {
                currentPlayer.handCards.addAll(deck.dealCards(1));
                // print handcards
                System.out.print(currentPlayer.getName() + ": ");
                currentPlayer.printHandCards();

                playedCard = currentPlayer.playIfPossible();
            }
        }

        if (playedCard == null) {
            output.println("Du hast keine Karte gespielt, der Nächste ist dran!");
        } else {
            output.println("Du hast Karte: " + playedCard + " gespielt.");
        }
    }

    private void updateState() {
        //TODO: Benutzereingaben verarbeiten
        // ist Karte gültig?
        // wenn ja, nächster Spieler

        // Card valid? - boolean
        // -1: not valid card, 0: no card played (only picked a card), 1: valid card played
        // 2: +2, 3: reverse, 4: skip
        // 5: special card was played (Farbwunsch)

        if (playedCard != null) {
            int valid = 0;
            valid = deck.checkCard(playedCard);

            // check if valid or action or special
            if (valid == -1) { // no valid Card
                output.println("Diese Karte ist nicht gültig!");
                return;
            } else if (valid == 0) { // normal Card
                output.println("nächster Spieler ist dran");
            } else if (valid == 2) { // +2 Card
                if(direction) {
                    allPlayers.getPlayer(currentPlayerNumber).handCards.addAll(deck.dealCards(2));
                } else {
                    allPlayers.getPlayer(currentPlayerNumber - 2).handCards.addAll(deck.dealCards(2));
                }
                output.println("Du hast 2 Karten bekommen!!");
            } else if (valid == 3) { // Reverse Card
                direction = !direction;
                output.println("Richtungswechsel!");
            } else if (valid == 5) { // ColorChange Card
                colorWish = currentPlayer.chooseColor();
                playedCard.setColor(colorWish); // wir müssen testen ob colorchange card beim zweiten Einsatz auch funktioniert!
                output.println("Der nächste Spieler ist dran.");
            } else if (valid == 6) { // +4
                colorWish = currentPlayer.chooseColor();
                playedCard.setColor(colorWish);
                output.println("Der nächste Spieler ist dran.");
                if(direction) {
                    allPlayers.getPlayer(currentPlayerNumber).handCards.addAll(deck.dealCards(4));
                } else {
                    allPlayers.getPlayer(currentPlayerNumber - 2).handCards.addAll(deck.dealCards(4));
                }
                output.println("Du hast 4 Karten bekommen!!");
            }

            // move cards
            if (valid > 0) {
                currentPlayer.removeHandCard(playedCard); // remove from handCards
                deck.addCardToDiscard(playedCard); // add to discardpile
                topCard = playedCard; // topCard aktualisiert
            }

            if (valid == 4) { // Skip Card
                currentPlayerNumber = allPlayers.nextPlayer(direction, currentPlayerNumber);
                currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);
                output.println(currentPlayer + ": du musst aussetzen!");
            }

            // nach Farbwunsch fragen
            // als Variable speichern (Enum)
        }

        // random start player in constructor anpassen??
        // Player Reihenfolge:
        currentPlayerNumber = allPlayers.nextPlayer(direction, currentPlayerNumber);
        currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);

        // nachzählen, ob gesamt 108 Karten sind!
        System.out.println("Karten im Spiel: " + (deck.discardpile.size() + deck.drawpile.size() + allPlayers.countAllPlayerCards()));

    }

    private void printState() {
        //TODO: Ausgabe des aktuellen Zustands
        // der Spieler der gerade dran ist: sieht handCards

        // Karten zählen
        cardStatus();

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

    public void cardStatus() {
        System.out.println("Karten im Spiel: " + (deck.discardpile.size() + deck.drawpile.size() + allPlayers.countAllPlayerCards()));
        System.out.println("Discard Pile: " + deck.discardpile.size());
        System.out.println("Draw Pile: " + deck.drawpile.size());
        System.out.println("current Player: " + currentPlayer + ": " + currentPlayer.handCards.size());
        System.out.println(allPlayers.getPlayer(0) + ": " + allPlayers.getPlayer(0).handCards.size());
        System.out.println(allPlayers.getPlayer(1) + ": " + allPlayers.getPlayer(1).handCards.size());
        System.out.println(allPlayers.getPlayer(2) + ": " + allPlayers.getPlayer(2).handCards.size());
        System.out.println(allPlayers.getPlayer(3) + ": " + allPlayers.getPlayer(3).handCards.size());
    }

}

