package Grazer_Bluemchen;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        UNOApp app = new UNOApp(input, System.out);
        app.Run();
        input.close();
        System.out.println("Das Programm wird beendet ...");


    }
}
