package Grazer_Bluemchen.Cards;

import java.util.Objects;

public class Card {
    private Colors color;
    private int value;
    private String name;
    private int points;
    // name is combination of color + value (z.B: R3)


    // normal Card
    public Card(Colors color, int value) {
        this.color = color;
        this.value = value;
        this.name = color + Integer.toString(value);
        this.points = value;
    }

    // ActionCard (Reverse, +2, skip)
    public Card(Colors color, String name) {
        this.color = color;
        this.name = color + name;
        this.value = -1;
        this.points = 20;
    }

    // SpecialCard (+4, ColorChange)
    public Card(String card) {

        // special card
        if(card.equals("ColorChange") || card.equals("+4")) {
            this.name = card;
            this.points = 50;
            this.value = -1;
            return;
        }

        Colors color = Colors.valueOf(card.substring(0, 1)); // erster Char von card wird als Enum gespeichert
        String name = card.substring(1);

        // action cards
        if(card.contains("Skip") || card.contains("Reverse") || card.contains("+2")) {
            this.color = color;
            this.name = color + name;
            this.points = 20;
            return;
        }

        int value = Integer.parseInt(name);

        // normal cards
        this.color = color;
        this.value = value;
        this.name = color + name;
        this.points = value;
    }

    @Override
    public String toString() {
        return name;
    }

    public Colors getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public int getPoints() {
        return points;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(name, card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
