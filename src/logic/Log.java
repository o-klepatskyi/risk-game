package logic;

import java.io.*;

public class Log {
    public static final String fileName = "Logs.txt";
    public static void initLog() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void write(String message) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fw != null;
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(message);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
