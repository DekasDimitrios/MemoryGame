import java.io.*;
import java.util.Arrays;

/**
 * Handles the reading and writing in the score files
 */
public class FileManager {

    private String filename;

    /**
     * Initializes the filename bases on the gamemode and the number of players
     *
     * @param numOfPlayers an integer that states the number of players that played the game
     * @param mode         an integer that states the mode that was played
     */
    public FileManager(int numOfPlayers, int mode) {
        StringBuilder fn = new StringBuilder("files");
        if (numOfPlayers == 1) {
            fn.append("/solo");
        } else {
            fn.append("/multiplayer");
        }
        switch (mode) {
            case 0:
                fn.append("/basic.txt");
                break;
            case 1:
                fn.append("/double.txt");
                break;
            case 2:
                fn.append("/triple.txt");
                break;
            default:
                fn.append("/duel.txt");
        }
        filename = fn.toString();
    }

    /**
     * Writes the given data for solo play on the file stated by the filename
     *
     * @param name           a string that represents the user's name
     * @param steps          an int that represents the user's steps
     * @param collectedCards an int that represents the user's collected cards
     * @throws IOException the exception that should be handled when the method is called
     */
    public void soloFileWriter(String name, int steps, int collectedCards) throws IOException {
        boolean exists = false;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Object[] objs = reader.lines().toArray();
        String[] fileLines = new String[objs.length];
        int i = 0;
        for (Object s : objs) {
            fileLines[i] = (String) s;
            String[] res = ((String) s).split(" ");
            if (res[0].equals(name)) {
                exists = true;
            }
            i++;
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        if (!exists) {
            writer.write(name);
            writer.write(" ");
            writer.write(Integer.toString(steps));
            writer.write(" ");
            writer.write(Integer.toString(collectedCards));
            writer.newLine();
            writer.flush();
            writer.close();
        } else {
            String str;
            for (int j = 0; j < fileLines.length; j++) {
                String[] tokens = (fileLines[j].split(" "));
                if (tokens[0].equals(name)) {
                    if (Integer.parseInt(tokens[1]) > steps) {
                        tokens[1] = String.valueOf(steps);
                        String newLine = tokens[0] + " " + tokens[1] + " " + tokens[2];
                        fileLines[j] = newLine;
                    }
                }
            }
            str = Arrays.toString(fileLines);
            BufferedWriter reWriter = new BufferedWriter(new FileWriter(filename, false));
            String[] tokens = (str.split(", "));
            for (String s : tokens) {
                s = s.replace("[", "");
                s = s.replace("]", "");
                reWriter.write(s);
                reWriter.newLine();
            }
            reWriter.flush();
            reWriter.close();
        }
    }

    /**
     * Writes the given data for multi play on the file stated by the filename
     *
     * @param name           a string that represents the user's name
     * @param steps          an int that represents the user's steps
     * @param collectedCards an int that represents the user's collected cards
     * @param winner         a Player that represents the winner of a game
     * @throws IOException the exception that should be handled when the method is called
     */
    public void multiFileWriter(String name, int steps, int collectedCards, Player winner) throws IOException {
        boolean exists = false;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Object[] objs = reader.lines().toArray();
        String[] fileLines = new String[objs.length];
        int i = 0;
        for (Object s : objs) {
            fileLines[i] = (String) s;
            String[] res = ((String) s).split(" ");
            if (res[0].equals(name)) {
                exists = true;
            }
            i++;
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        if (!exists) {
            writer.write(name);
            writer.write(" ");
            writer.write(Integer.toString(steps));
            writer.write(" ");
            writer.write(Integer.toString(collectedCards));
            writer.write(" ");
            if (name.equals(winner.getUserName())) {
                writer.write("1");
            } else {
                writer.write("0");
            }
            writer.newLine();
            writer.flush();
            writer.close();
        } else {
            String str;
            for (int j = 0; j < fileLines.length; j++) {
                String[] tokens = (fileLines[j].split(" "));
                if (tokens[0].equals(name)) {
                    if (tokens[0].equals(winner.getUserName())) {
                        tokens[1] = String.valueOf(Integer.parseInt(tokens[1]) + steps);
                        tokens[2] = String.valueOf(Integer.parseInt(tokens[2]) + collectedCards);
                        tokens[3] = String.valueOf(Integer.parseInt(tokens[3]) + 1);
                        String newLine = tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3];
                        fileLines[j] = newLine;
                    } else {
                        tokens[1] = String.valueOf(Integer.parseInt(tokens[1]) + steps);
                        tokens[2] = String.valueOf(Integer.parseInt(tokens[2]) + collectedCards);
                        String newLine = tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3];
                        fileLines[j] = newLine;
                    }

                }
            }
            str = Arrays.toString(fileLines);
            BufferedWriter reWriter = new BufferedWriter(new FileWriter(filename, false));
            String[] tokens = (str.split(", "));
            for (String s : tokens) {
                s = s.replace("[", "");
                s = s.replace("]", "");
                reWriter.write(s);
                reWriter.newLine();
            }
            reWriter.flush();
            reWriter.close();
        }
    }

    /**
     * Reads the data for solo play from the file stated by the filename
     *
     * @return a two-dimensional array that contains the scoreboards
     * @throws IOException the exception that should be handled when the method is called
     */
    public String[][] soloFileReader() throws IOException {
        String[][] rows;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Object[] fileLines = reader.lines().toArray();
        rows = new String[fileLines.length][3];
        for (int i = 0; i < fileLines.length; i++) {
            String line = fileLines[i].toString().trim();
            String[] tokens = line.split(" ");
            rows[i][0] = tokens[0];
            rows[i][1] = tokens[1];
            rows[i][2] = tokens[2];
        }
        reader.close();
        return rows;
    }

    /**
     * Reads the data for multi play from the file stated by the filename
     *
     * @return two-dimensional array that contains the scoreboards
     * @throws IOException the exception that should be handled when the method is called
     */
    public String[][] multiFileReader() throws IOException {
        String[][] rows;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Object[] fileLines = reader.lines().toArray();
        rows = new String[fileLines.length][4];
        for (int i = 0; i < fileLines.length; i++) {
            String line = fileLines[i].toString().trim();
            String[] tokens = line.split(" ");
            rows[i][0] = tokens[0];
            rows[i][1] = tokens[1];
            rows[i][2] = tokens[2];
            rows[i][3] = tokens[3];
        }
        reader.close();
        return rows;
    }
}
