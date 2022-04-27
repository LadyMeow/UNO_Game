package Grazer_Bluemchen;

public class Card {
    public String color;
    public int value;
    public String name;
    // name is combination of color + value (z.B: R3)


    // normal Card
    public Card(String color, int value) {
        this.color = color;
        this.value = value;
        name = color + Integer.toString(value);
    }

    // ActionCard
    public Card(String color, String name) {
        this.color = color;
        this.name = color + name;

    }

    // SpecialCard
    public Card(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Card{" +
                "color='" + color + '\'' +
                ", value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
