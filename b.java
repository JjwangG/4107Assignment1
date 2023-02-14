import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class b {
    public static void main(String[] args) throws Exception {
        String[] s = initialiseArray();
        try {
            BufferedWriter writerObj = new BufferedWriter(new FileWriter("tokens.txt", false));
            for (String string : s) {
                writerObj.write(string);
                writerObj.newLine();
            }
            writerObj.close();
            System.out.println("================================\n"
                    + "Tokens successfully generated");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] initialiseArray() throws Exception{
        String data = "";
        String[]arr;
        data = new String(Files.readAllBytes(Paths.get("tokens.txt")));
        Preprocessing p = new Preprocessing();

        data = p.removeStopWords(data);
        data = data.replace("\n", " ").replace("\r", " ");
        arr = data.split("\\s+");
        return arr;
    }
}
