package Grazer_Bluemchen;

import Grazer_Bluemchen.Cards.Card;
import Grazer_Bluemchen.Cards.CardDeck;
import Grazer_Bluemchen.Cards.CardType;
import Grazer_Bluemchen.Cards.Colors;
import Grazer_Bluemchen.DB.SqliteClient;
import Grazer_Bluemchen.Help.Help;
import Grazer_Bluemchen.Players.AllPlayers;
import Grazer_Bluemchen.Players.Bot;
import Grazer_Bluemchen.Players.Human;
import Grazer_Bluemchen.Players.Player;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private SqliteClient dbClient;
    private static final String INSERT_TEMPLATE = "INSERT INTO Sessions (Player, Session, Round, Score) VALUES ('%1s', %2d, %3d, %4d);";
    private static final String SELECT_BYPLAYERANDSESSION = "SELECT Player, SUM(Score) AS Score FROM Sessions WHERE Player = '%1s' AND Session = %2d;";
    private int round = 1;
    private int session = 1;
    private boolean newSession = true;

    // constructor
    public UNOApp(Scanner input, PrintStream output, SqliteClient dbClient) {
        this.input = input;
        this.output = output;
        currentPlayerNumber = (int) (Math.random() * (4 - 1)) + 1; // random number (1-4)
        this.dbClient = dbClient;
        //this.round = round;
    }

    // GameLoop
    public void Run() {
        initialize();
        initializeDataBase();
        //printPoints();
        printState();

        while (!exit) {
            inputPlayer();
            updateState();
            printState();
        }
    }

    private void initialize() {
        // Neue Runde
        exit = false;

        if (round > 1) {
            newRound(); // ab 2. Runde
        } else { // beim ersten Spiel

            // help ausgeben
            help.printHelp();

            // Anzahl Bots und Menschen erstellen?
            //******************************************ANFORDERUNG 5 **************************************************
            createPlayer();

            // drawPile erstellen
            deck.createDrawPile();
            //**************************************ANFORDERUNG 4 ***********************************************************
            deck.shuffle();

            // name eingeben + Handkarten austeilen
            namePlayers();

            //CurrentPlayer initialize + print
            currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);
            output.println("Startspieler (wurde zufällig gewählt): " + currentPlayer);

            // DiscardPile erstellen und erste Karte prüfen
            createDiscardPile();
        }
    }

    private void inputPlayer() {
        if (currentPlayer instanceof Bot) {
            botPlays();
        } else {
            humanPlays();
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
            if (currentPlayer.handCards.size() == 2 && currentPlayer instanceof Human) { // Bots sagen immer automatisch UNO
                if (currentPlayer.isUno()) {
                    output.println(currentPlayer + " hat UNO gesagt!");
                    currentPlayer.setUno(false);
                } else {
                    output.println("Du hast nicht UNO gesagt! Und bekommst 2 Strafkarten!");
                    currentPlayer.handCards.addAll(deck.dealCards(2));
                }
            }
        }

        CardType valid = checkValidation();
        if (valid == CardType.INVALID) {
            return;
        }

        // move cards
        if (valid != null) {
            moveCards();
        }

        // 0 Cards check - gewonnen?
        if (currentPlayer.handCards.size() == 0) {
            output.println(currentPlayer + " du hast gewonnen!");
            round++;
            int points = countPoints();
            output.println((currentPlayer + " du hast: " + points + " Punkte bekommen! "));
            printPoints();
            try {
                dbClient.executeStatement(String.format(INSERT_TEMPLATE, currentPlayer.getName(), session, round, points)); // throws SQLException
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            exit = true;
            return;
        }

        // +2 hier!!! weil Spieler switch
        if (valid == CardType.PLUS_TWO) {
            int nextPlayerIndex = allPlayers.nextPlayer(direction, currentPlayerNumber) - 1;

            allPlayers.getPlayer(nextPlayerIndex).handCards.addAll(deck.dealCards(2));
            output.println(allPlayers.getPlayer(nextPlayerIndex) + " hat 2 Karten bekommen!!");
            currentPlayerNumber = allPlayers.nextPlayer(direction, currentPlayerNumber);
            currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);
        }

        if (valid == CardType.SKIP) {
            currentPlayerNumber = allPlayers.nextPlayer(direction, currentPlayerNumber);
            currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);
            output.println(currentPlayer + ": du musst aussetzen!");
        }

        // Player Reihenfolge:
        currentPlayerNumber = allPlayers.nextPlayer(direction, currentPlayerNumber);
        currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);

    }

    private void printState() {
        //TODO: Ausgabe des aktuellen Zustands
        // der Spieler der gerade dran ist: sieht handCards

        // wenn Runde gewonnen wurde-->wenn eine spiel fertig wird
        if (exit) {

            ArrayList<HashMap<String, String>> results = null;
            try {
                results = dbClient.executeQuery(String.format(SELECT_BYPLAYERANDSESSION, currentPlayer.getName(), session));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int points = Integer.parseInt(results.get(0).get("Score"));
            output.println(currentPlayer.getName() + "Punkte: " + points);

            if (points >= 500) { // Spiel vorbei
                allPlayers.playerList.clear();
                output.println("Neues Spiel? (J/N)");
                session++;
                newSession = true;
                round = 1;
            } else {
                // Runde vorbei
                for (Player p : allPlayers.playerList) {
                    p.handCards.clear();
                }
                output.println("Neue Runde? (J/N)");
            }
            // Spiel vorbei + Runde vorbei
            deck.drawpile.clear();
            deck.discardpile.clear();
            currentPlayerNumber = (int) (Math.random() * (4 - 1)) + 1;
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

    private void newRound() {
        direction = true;
        System.out.println("Runde: " + round);

        System.out.println("Startspieler ist der Gewinner von dieser Runde: " + currentPlayer);

        deck.createDrawPile();
        deck.shuffle();

        for (Player p : allPlayers.playerList) {
            p.handCards = deck.dealCards(2); // ACHTUNG!! nur 2 Karten
        }

        createDiscardPile();

    }

    // aktuelle Punkte

    public void createPlayer() {
        while (allPlayers.playerList.size() < 4) {
            //*********************************ANFORDERUNG2 ************************************
            output.println("Mit wie vielen Bots möchtest du spielen? (0-3)");
            int botCount = 10;
            try {
                botCount = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                // ?
            }

            if (botCount >= 0 && botCount < 4) {
                for (int i = 0; i < 4 - botCount; i++) { // Humans erstellen
                    allPlayers.addPlayer(new Human(input, output));
                }
                for (int i = 0; i < botCount; i++) { // Bots erstellen
                    allPlayers.addPlayer(new Bot(input, output));
                }
            } else {
                output.println("Wähle eine Zahl von 0-3!");
            }
        }
    }

    //*********** ANFORDERUNG 1 UND 3  und 5*********************************************************
    public void namePlayers() {
        int botNumber = 1;
        for (Player p : allPlayers.playerList) {
            if (p instanceof Human) {
                output.println("Schreibe deinen Namen: ");
                p.setName(input.nextLine());
                p.handCards = deck.dealCards(2);
            } else {
                p.setName("Bot" + botNumber);
                botNumber++;
                p.handCards = deck.dealCards(7);
                System.out.println(p.getName());
            }
        }
    }

    private void initializeDataBase() {
        if (newSession) {
            for (Player p : allPlayers.playerList) {
                try {
                    dbClient.executeStatement(String.format(INSERT_TEMPLATE, p.getName(), session, round, 0)); // throws SQLException
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            newSession = false;
        }
    }

    private void printPoints() {
        for (Player p : allPlayers.playerList) {
            try {
                ArrayList<HashMap<String, String>> results = dbClient.executeQuery(String.format(SELECT_BYPLAYERANDSESSION, p.getName(), session));
                int points = Integer.parseInt(results.get(0).get("Score"));
                output.println(p.getName() + " - Punkte: " + points);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

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
        if (topCard.getName().contains("Skip")) { // vorher topCard und handcards printen
            output.println("Der erste Spieler: " + currentPlayer + " muss aussetzen!");
            currentPlayerNumber++;
            currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);
        } else if (topCard.getName().contains("+2")) {
            currentPlayer.handCards.addAll(deck.dealCards(2));
            output.println(currentPlayer + " hat 2 Karten bekommen!!");
            currentPlayerNumber++;
            currentPlayer = allPlayers.getPlayer(currentPlayerNumber - 1);
        } else if (topCard.getName().contains("Reverse")) {
            direction = false;
            output.println("Richtungswechsel!");
        } else if (topCard.getName().equals("ColorChange")) {
            deck.printDiscardPile();
            output.print(currentPlayer.getName() + ": ");
            currentPlayer.printHandCards();
            colorWish = currentPlayer.chooseColor();
            topCard.setColor(colorWish);
        }
    }

    public CardType checkValidation() {
        CardType valid = null;

        if (playedCard != null) {
            valid = deck.checkCard(playedCard);
            // check if valid or action or special
            if (valid == CardType.INVALID) {
                output.println("Diese Karte ist nicht gültig! Du bekommst jetzt eine Strafkarte! Hihihi");
                currentPlayer.handCards.addAll(deck.dealCards(1));
            } else if (valid == CardType.NORMAL) {

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

    public void contestPlus4() {
        int nextPlayerIndex = allPlayers.nextPlayer(direction, currentPlayerNumber) - 1;
        Player nextPlayer = allPlayers.getPlayer(nextPlayerIndex);

        int cheated = currentPlayer.contestPlus4(nextPlayer, topCard);

        if (cheated == 1) { // wenn geschummelt: 1
            currentPlayer.handCards.addAll(deck.dealCards(4));
        } else if (cheated == 2) { // hat nicht geschummelt: 2
            nextPlayer.handCards.addAll(deck.dealCards(6));
        } else {
            nextPlayer.handCards.addAll(deck.dealCards(4));
            output.println(nextPlayer + " hat 4 Karten bekommen!!");
        }
    }

    // Spielen Methoden
    public void humanPlays() {
        while (true) {
            String status = checkStatus(); // null - Karte abheben, String - playCard

            if (status == null) {
                currentPlayer.handCards.addAll(deck.dealCards(1));
                // print handcards
                System.out.print(currentPlayer.getName() + ": ");
                currentPlayer.printHandCards();

                playedCard = currentPlayer.playIfPossible(topCard);
                return;
            } else {
                playedCard = currentPlayer.searchHandCards(topCard, status); // Spieler wählt Karte, Eingabe k: abheben, Eingabe w: weiter
                if (playedCard != null) {
                    return;
                }
            }
        }

    }

    public void botPlays() {
        playedCard = currentPlayer.searchHandCards(topCard, null); // Bot spielt eine Karte (wenn sie passt)
        if (playedCard == null) {
            currentPlayer.handCards.addAll(deck.dealCards(1)); // Bot hebt 1 card ab
            if (currentPlayer.playIfPossible(topCard) != null && (currentPlayer.handCards.size() == 2)) { // Bot spielt, wenn möglich, die abgehobene Karte
                output.println(currentPlayer + " hat UNO gesagt!");
            }
        } else if (currentPlayer.handCards.size() == 2) {
            output.println(currentPlayer + " hat UNO gesagt!");
        }
    }

    private int countPoints() {
        int sum = 0;
        for (Player p : allPlayers.playerList) {
            for (Card c : p.handCards) {
                sum += c.getPoints();
            }
        }
        return sum;
    }

    private String checkStatus() { // gibt String für Karte die wir spielen wollen zurück
        while (true) {
            output.println("Spiele eine deiner Karten oder hebe eine Karte (mit k). Wenn du Hilfe brachst schreibe h. Wenn du Punkte anzeigen willst, schreibe 'Punkte': ");
            String playCard = input.nextLine();

            if (playCard.equalsIgnoreCase("Punkte")) {
                printPoints();
            }

            if (playCard.equalsIgnoreCase("h")) {
                help.printHelp();
            }

            if (playCard.toLowerCase().contains("uno")) {
                currentPlayer.setUno(true);
                playCard = playCard.replace("uno", "").trim();
                if (!playCard.equalsIgnoreCase("")) {
                    return playCard;
                }
            }

            // abheben & Karte Spielen noch möglich!
            if (playCard.equalsIgnoreCase("k")) {
                return null;
            }

            if (!playCard.equals("")) { // normale Karte spielen
                return playCard;
            }
        }
    }

}

