package Grazer_Bluemchen.Help;

import java.io.*;

public class Help {

    public void printHelp() throws IOException {
        File file = new File("help.txt");
        FileReader fileReader = new FileReader(file);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // zeilenweise auslesen
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        bufferedReader.close();
    }


}
