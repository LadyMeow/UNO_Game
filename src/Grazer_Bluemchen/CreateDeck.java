package Grazer_Bluemchen;

public class CreateDeck {

    public static void main(String[] args) {

        // Deck erstellen:
        CardDeck deck = new CardDeck();

        // 1-9er cards and actioncards
        for (int i = 0; i < 2; i++) {
            for (Colors co : Colors.values()) {
                for (int j = 1; j <= 9; j++) {
                    Card c = new Card(co, j);
                    deck.addCard(c);
                }
                Card c1 = new Card(co, "+2");
                Card c2 = new Card(co, "Reverse");
                Card c3 = new Card(co, "Skip");
                deck.addCard(c1);
                deck.addCard(c2);
                deck.addCard(c3);
            }
        }

        // 0er cards
        for (Colors co : Colors.values()) {
            Card o = new Card(co, 0);
            deck.addCard(o);
        }

        // special cards
        for (int i = 0; i < 4; i++) {
            Card s1 = new Card("+4");
            Card s2 = new Card("ColorChange");
            deck.addCard(s1);
            deck.addCard(s2);
        }

        deck.printAllCards();
        System.out.println(deck.countCards());
        deck.shuffle();
        deck.printAllCards();

        Player nr1 = new Player();
        System.out.println(nr1);

        // added dealt Cards to player nr1
        nr1.addHandCards(deck.dealCards(7));

        System.out.println(nr1);
        System.out.println(deck.countCards());


        // methode für summe von allen Cards im Spiel

    }
}
