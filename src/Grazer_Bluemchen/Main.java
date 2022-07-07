package Grazer_Bluemchen;

import Grazer_Bluemchen.DB.SqliteClient;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static Grazer_Bluemchen.DB.DBApp.*;

public class Main {

    private static final String CREATETABLE = "CREATE TABLE Sessions (Player varchar(100) NOT NULL, Session int NOT NULL, Round int NOT NULL, Score int NOT NULL, CONSTRAINT PK_Sessions PRIMARY KEY (Player, Session, Round));";


    public static void main(String[] args) throws IOException {


        try {
            SqliteClient client = new SqliteClient("demodatabase.sqlite");
            if (client.tableExists("Sessions")) {
                client.executeStatement("DROP TABLE Sessions;");
            }
            client.executeStatement(CREATETABLE);

//            client.executeStatement(String.format(INSERT_TEMPLATE, "Anita", 1, 1, 50));
//            client.executeStatement(String.format(INSERT_TEMPLATE, "Hans", 1, 1, 0));
//            client.executeStatement(String.format(INSERT_TEMPLATE, "Anita", 1, 2, 20));
//            client.executeStatement(String.format(INSERT_TEMPLATE, "Hans", 1, 2, 100));

            //ArrayList<HashMap<String, String>> results = client.executeQuery(String.format(SELECT_BYPLAYERANDSESSION, "Anita", 1));

//            for (HashMap<String, String> map : results) {
//                System.out.println(map.get("Player") + " hat derzeit:  " + map.get("Score") + " Punkte");
//            }

            Scanner input = new Scanner(System.in);
            UNOApp app = new UNOApp(input, System.out, client);
            app.Run();

            while (input.nextLine().equalsIgnoreCase("j")) {
                app.Run();
            }

            input.close();
            System.out.println("Das Programm wird beendet ...");
        } catch (SQLException ex) {
            System.out.println("Ups! Something went wrong:" + ex.getMessage());
        }
    }


}
