package Grazer_Bluemchen;

public class Main {

    public static void main(String[] args) {

        CardDeck deck = new CardDeck();

        for (int i = 0; i < 2; i++) {
            // 2x: 1-9 in all colors
            for (int j = 1; j <= 9; j++) {
                Card c = new Card("R", j);
                deck.addCard(c);
            }
            for (int j = 1; j <= 9; j++) {
                Card c = new Card("B", j);
                deck.addCard(c);
            }
            for (int j = 1; j <= 9; j++) {
                Card c = new Card("G", j);
                deck.addCard(c);
            }
            for (int j = 1; j <= 9; j++) {
                Card c = new Card("Y", j);
                deck.addCard(c);
            }
        }


        deck.printAllCards();
    }
}
