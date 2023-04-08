import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Indexing {

    // HashMap<term, HashMap<docNum, frequency>>
    private static HashMap<String, HashMap<String, Double>> invertedIndex = new HashMap<>();

    private static int numDocs;
    private static int counter;

    public static void mapMaker(String dirDocs, String dirTokens) throws Exception {

        // Read tokens file
        HashMap<String, Integer> tokens = readTokensFile(dirTokens);

        // Read all files in directory and build inverted index
        File folder = new File(dirDocs);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                List<String> subDocs = readSubDocsFromFile(file);
                buildInvertedIndex(subDocs, tokens);
                counter ++;
                System.out.print(counter + " | ");
            }
        }
    }

    private static HashMap<String, Integer> readTokensFile(String dirTokens) throws IOException {
      HashMap<String, Integer> tokens = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(dirTokens)))) {
            String line;
            while ((line = br.readLine()) != null) {
                tokens.put(line, 0);
            }
        }
        return tokens;
    }

    private static List<String> readSubDocsFromFile(File file) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        data = data.replaceAll("\n", " ").replaceAll("\r", " ");

        // Extract subdocs from file
        List<String> subDocs = new ArrayList<>();
        Pattern docPattern = Pattern.compile("<DOC>(.*?)</DOC>");
        Matcher matcher = docPattern.matcher(data);
        while (matcher.find()) {
            subDocs.add(matcher.group(1));
        }
        
        numDocs = numDocs + subDocs.size();
        return subDocs;
    }

    private static void buildInvertedIndex(List<String> subDocs, HashMap<String, Integer> tokens) throws Exception {
        Preprocessing proc = new Preprocessing();

        for (int i = 0; i < subDocs.size(); i++) {
            String subDoc = subDocs.get(i).toLowerCase();
            String subDocNo = extractSubDocNo(subDoc).toUpperCase();

            ArrayList<String> words = proc.removeStopWords(subDoc.split("\\s+"));

            for (int j = 0; j < words.size() ; j++){
              words.set(j, proc.removePunct(words.get(j)));
            }

            Map<String, Double> frequencies = new HashMap<>();
            for (String word : words) {
                if (tokens.containsKey(word)) {
                    frequencies.compute(word, (k, v) -> (v == null) ? 1 : v + 1);
                }
            }

            for (Map.Entry<String, Double> entry : frequencies.entrySet()) {
              String term = entry.getKey();
              double frequency = entry.getValue() / (double) words.size();

              Map<String, Double> docFrequencies = invertedIndex.computeIfAbsent(term, k -> new HashMap<>());
              docFrequencies.put(subDocNo, frequency);
            }
        }
    }

    private static String extractSubDocNo(String subDoc) {
        Pattern p = Pattern.compile(" <docno>(.*?)</docno>");
        Matcher m = p.matcher(subDoc);
        if (m.find()) {
            return m.group(1);
        }
        return "fail";
    }

    public int getNumDocs() {
        return numDocs;
    }

    // Final inverted index hash map getter
    public HashMap<String, HashMap<String, Double>> getInvertedIndex() {
        return invertedIndex;
    }

    public static void main() throws Exception {

      long startTime = System.nanoTime();
      mapMaker("docs", "tokens.txt");

      try {
        BufferedWriter writerObj = new BufferedWriter(new FileWriter("invertedIndex.txt", false));
        writerObj.write(invertedIndex.toString());
        writerObj.close();
        System.out.println("");
        System.out.println("================================\n"
                + "Inverted Index successfully generated");
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        totalTime = TimeUnit.SECONDS.convert(totalTime, TimeUnit.NANOSECONDS);
        System.out.println("Total time: "+ totalTime);
        System.out.println("Total docs: "+ numDocs);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

}