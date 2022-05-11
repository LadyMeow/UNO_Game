package Grazer_Bluemchen.Test;

import Grazer_Bluemchen.CardDeck;
import Grazer_Bluemchen.Human;
import Grazer_Bluemchen.Player;

public class TestApp {

    public static void main(String[] args) {

        CardDeck drawPile = new CardDeck();
        drawPile.createDrawPile();

        drawPile.printAllCards();
        System.out.println(drawPile.countCards());
        drawPile.shuffle();
        drawPile.printAllCards();

        Player nr1 = new Human();
        System.out.println(nr1);

        // added dealt Cards to player nr1
        nr1.addHandCards(drawPile.dealCards(7));

        System.out.println(nr1);
        System.out.println(drawPile.countCards());


        // methode für summe von allen Cards im Spiel

    }
}
