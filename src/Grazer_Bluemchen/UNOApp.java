package Grazer_Bluemchen;

import Grazer_Bluemchen.Help.Help;

import java.io.IOException;
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
    private Help help = new Help();

    // constructor
    public UNOApp(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
        currentPlayerNumber = (int) (Math.random() * (4 - 1)) + 1; // random number (1-4)
    }

    // GameLoop
    public void Run() throws IOException {
        initialize();
        printState();

        while (!exit) {
            inputPlayer();
            updateState();
            printState();
        }
    }

    private void initialize() throws IOException { // wenn keine Zahl von 0-3 eingegeben wird -crush!!
        // Neue Runde
        exit = false;

        // help ausgeben
        help.printHelp();

        // frage: bot oder mensch?
        output.println("Mit wie vielen Bots möchtest du spielen? (0-3)");
        int botCount = Integer.parseInt(input.nextLine());
        for (int i = 0; i < 4 - botCount; i++) { // zuerst Humans erstellen
            allPlayers.addPlayer(new Human(input, output));
        }
        for (int i = 0; i < botCount; i++) { // dann Bots
            allPlayers.addPlayer(new Bot(input, output));
        }

        // drawPile erstellen
        deck.createDrawPile();
        deck.shuffle();

        // name eingeben
        int botNumber = 1;
        for (Player p : allPlayers.allPlayer) {
            if (p instanceof Human) {
                output.println("Schreibe deinen Namen: ");
                p.setName(input.nextLine());
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
        output.println("Startspieler (wurde zufällig gewählt): " + currentPlayer);

        // DiscardPile erstellen und erste Karte prüfen
        createDiscardPile();

        // nachzählen, ob gesamt 108 Karten sind!
        System.out.println("Karten im Spiel: " + (deck.discardpile.size() + deck.drawpile.size() + allPlayers.countAllPlayerCards())); // details!

    }

    private void inputPlayer() throws IOException {
        // Bot legt Karte
        if (currentPlayer instanceof Bot) {
            playedCard = currentPlayer.searchHandCards(topCard);
            if (playedCard == null) {
                currentPlayer.handCards.addAll(deck.dealCards(1)); // Bot hebt 1 card ab
                currentPlayer.playIfPossible(topCard); // Bot spielt, wenn möglich, die abgehobene Karte
                return;
            } else if (currentPlayer.handCards.size() == 2) {
                output.println(currentPlayer + " hat UNO gesagt!");
            }
        } else { // Spieler legt Karte
            // wenn Eingabe h: eine Karte heben
            playedCard = currentPlayer.searchHandCards(topCard);

            if (playedCard == null) {
                currentPlayer.handCards.addAll(deck.dealCards(1));
                // print handcards
                System.out.print(currentPlayer.getName() + ": ");
                currentPlayer.printHandCards();

                playedCard = currentPlayer.playIfPossible(topCard);
            }
        }
    }

    private void updateState() {
        //TODO: Benutzereingaben verarbeiten
        // ist Karte gültig?
        // wenn ja, nächster Spieler

        // Textausgabe und UNO prüfen
        if (playedCard == null) {
            output.println("Du hast keine Karte gespielt, der Nächste ist dran!");
        } else {
            output.println(currentPlayer + " hat Karte: " + playedCard + " gespielt.");
            // UNO prüfen
            if (currentPlayer.handCards.size() == 1) {
                if (currentPlayer.uno) {
                    output.println(currentPlayer + " hat UNO gesagt!");
                    currentPlayer.uno = false;
                } else {
                    output.println("Du hast nicht UNO gesagt! Und bekommst 2 Strafkarten!");
                    currentPlayer.handCards.addAll(deck.dealCards(2));
                }
            }
        }

        CardType valid = checkValidation();
        if(valid == CardType.INVALID) {
            return;
        }

        // move cards
        if (valid != null) {
            moveCards();
        }

        // 0 Cards check - gewonnen?
        if (currentPlayer.handCards.size() == 0) {
            output.println(currentPlayer + " du hast gewonnen!");
            exit = true;
            return;
        }

        if (valid == CardType.SKIP) {
            currentPlayerNumber = allPlayers.nextPlayer(direction, currentPlayerNumber);
            currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);
            output.println(currentPlayer + ": du musst aussetzen!");
        }

    // Player Reihenfolge:
    currentPlayerNumber =allPlayers.nextPlayer(direction,currentPlayerNumber);
    currentPlayer =allPlayers.getPlayer(currentPlayerNumber -1);

    // nachzählen, ob gesamt 108 Karten sind!
        System.out.println("Karten im Spiel: "+(deck.discardpile.size()+deck.drawpile.size()+allPlayers.countAllPlayerCards()));
}

    private void printState() {
        //TODO: Ausgabe des aktuellen Zustands
        // der Spieler der gerade dran ist: sieht handCards

        // wenn Runde gewonnen wurde
        if (exit) {
            allPlayers.allPlayer.clear();
            deck.drawpile.clear();
            deck.discardpile.clear();
            currentPlayerNumber = (int) (Math.random() * (4 - 1)) + 1;
            output.println("Neue Runde? (J/N)");
            return;
        }

        // Karten zählen
        cardStatus();

        // print handCards from currentPlayer
        output.print(currentPlayer.getName() + ": ");
        currentPlayer.printHandCards(); // Methode anpassen, dass currentPlayer mit ausgegeben wird

        // erste Karte wird aufgedeckt
        deck.printDiscardPile();

    }

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

    public void createDiscardPile() {
        // discardPile erstellen
        deck.addToDiscardPile();
        topCard = deck.discardpile.get(0);

        // check first Card
        if (topCard.name.contains("Skip")) { // vorher topCard und handcards printen
            output.println("Der erste Spieler: " + currentPlayer + " muss aussetzen!");
            currentPlayerNumber++;
            currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);
        } else if (topCard.name.contains("+2")) {
            currentPlayer.handCards.addAll(deck.dealCards(2));
            output.println(currentPlayer + " hat 2 Karten bekommen!!");
        } else if (topCard.name.contains("Reverse")) {
            direction = false;
            output.println("Richtungswechsel!");
        } else if (topCard.name.equals("ColorChange")) {
            deck.printDiscardPile();
            output.print(currentPlayer.getName() + ": ");
            currentPlayer.printHandCards();
            colorWish = currentPlayer.chooseColor();
            topCard.setColor(colorWish);
        }
    }

    public CardType checkValidation() {
        int nextPlayerIndex = allPlayers.nextPlayer(direction, currentPlayerNumber) - 1;
        CardType valid = null;

        if (playedCard != null) {
            valid = deck.checkCard(playedCard);
            // check if valid or action or special
            if (valid == CardType.INVALID) {
                output.println("Diese Karte ist nicht gültig!");
            } else if (valid == CardType.NORMAL) {

            } else if (valid == CardType.PLUS_TWO) {
                allPlayers.getPlayer(nextPlayerIndex).handCards.addAll(deck.dealCards(2));
                output.println("Du hast 2 Karten bekommen!!");
            } else if (valid == CardType.REVERSE) {
                direction = !direction;
                output.println("Richtungswechsel!");
            } else if (valid == CardType.COLORCHANGE) {
                colorWish = currentPlayer.chooseColor();
                playedCard.setColor(colorWish); // wir müssen testen ob colorchange card beim zweiten Einsatz auch funktioniert!
                output.println("Der nächste Spieler ist dran.");
            } else if (valid == CardType.PLUS_FOUR) {
                colorWish = currentPlayer.chooseColor();
                playedCard.setColor(colorWish);
                output.println("Der nächste Spieler ist dran.");
                contestPlus4();
            }
        }
        return valid;
    }

    public void moveCards() {
            currentPlayer.removeHandCard(playedCard); // remove from handCards
            deck.addCardToDiscard(playedCard); // add to discardpile
            topCard = playedCard; // topCard aktualisiert
    }

    public void contestPlus4(){
        int nextPlayerIndex = allPlayers.nextPlayer(direction, currentPlayerNumber) - 1;
        //int prevPlayerIndex = allPlayers.prevPlayer(direction, currentPlayerNumber) - 1;

        output.println(allPlayers.getPlayer(nextPlayerIndex) + ": willst du die +4 anfechten? (J/N)");
        if(input.nextLine().equalsIgnoreCase("j")) {
            output.print(currentPlayer + ": ");
            currentPlayer.printHandCards();

            if(currentPlayer.checkContest(topCard)) {
                currentPlayer.handCards.addAll(deck.dealCards(4));
                output.println(currentPlayer + " hat geschummelt und bekommt 4 Strafkarten!");
            } else {
                allPlayers.getPlayer(nextPlayerIndex).handCards.addAll(deck.dealCards(6));
                output.println(currentPlayer + " hat NICHT geschummelt! Du bekommst jetzt 6 Strafkarten.");
            }

        } else {
            allPlayers.getPlayer(nextPlayerIndex).handCards.addAll(deck.dealCards(4));
            output.println(allPlayers.getPlayer(nextPlayerIndex) + " hat 4 Karten bekommen!!");
        }

    }

}

