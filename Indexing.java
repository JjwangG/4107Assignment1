import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Indexing {
  private Map<String, Map<String, Integer>> index;

  public Indexing() {
    index = new HashMap<>();
  }

  public void buildIndex(String directory) {
    //System.out.println(directory);
    File dir = new File(directory);
    for (File file : dir.listFiles()) {
      if (!file.isFile()) {
        continue;
      }
      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] words = line.split("\\s+");
          for (String word : words) {
            word = word.toLowerCase();
            Map<String, Integer> frequencyMap = index.get(word);
            if (frequencyMap == null) {
              frequencyMap = new HashMap<>();
              index.put(word, frequencyMap);
            }
            Integer frequency = frequencyMap.get(file.getName());
            if (frequency == null) {
              frequency = 0;
            }
            frequencyMap.put(file.getName(), frequency + 1);
          }
        }
      } catch (IOException e) {
        System.err.println("An error occurred while reading the file: " + e.getMessage());
      }
    }
  }

  public Map<String, Integer> getDocumentFrequency(String word) {
    return index.get(word.toLowerCase());
  }

  public List<String> getDocuments(String word) {
    Map<String, Integer> frequencyMap = getDocumentFrequency(word);
    if (frequencyMap == null) {
      return new ArrayList<>();
    }
    Set<String> documentSet = frequencyMap.keySet();
    return new ArrayList<>(documentSet);
  }

  public static void main(String[] args) {
    Indexing index = new Indexing();
    index.buildIndex("docs");
    
    Map<String, Integer> frequencyMap = index.getDocumentFrequency("word");
    System.out.println(frequencyMap);
    List<String> documents = index.getDocuments("word");
    System.out.println(documents);
  }
}
