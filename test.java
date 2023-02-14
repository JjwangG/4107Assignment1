import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class test {
    // HashMap< term, HashMap< HashMap< docNum, occurence>, idf>>
    private HashMap<String, HashMap<String, Integer>> FinalMap;

    public test() {
        FinalMap = new HashMap<>();
      }

    public void buildMap(String docsDir, String wordsDir){

        //
        File dirD = new File(docsDir);
        File dirW = new File(wordsDir);
        for (File file : dirD.listFiles()) {
            //&& !file.getName().endsWith(".txt")
            if (!file.isFile() ) {
                continue;
            }
            System.out.println(file);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedReader br = new BufferedReader(new FileReader(dirW));
                String line;
                String indWord;
                
                while ((line = reader.readLine()) != null) {
                    while ((indWord = br.readLine()) != null){
                        String[] words = line.split("\\s+");
                        for (String word : words) {
                            word = word.toLowerCase();
                            word = word.replaceAll("[^-$a-z0-9\\d+\\.\\d+\\s]", "").replaceAll("(?!\\d)\\.(?!\\d)", "").replaceAll("\\s+", " ");
                            HashMap<String, Integer> frequencyMap = FinalMap.get(indWord);
                            if (frequencyMap == null) {
                            frequencyMap = new HashMap<>();
                            FinalMap.put(indWord, frequencyMap);
                            }
                            Integer frequency = frequencyMap.get(file.getName());
                            if (frequency == null) {
                            frequency = 0;
                            }
                            frequencyMap.put(file.getName(), frequency + 1);
                    }
                    //System.out.println(FinalMap);
                    }
                    
                    
                }
                br.close();
                
              } catch (IOException e) {
                System.err.println("An error occurred while reading the file: " + e.getMessage());
              }
            }
        }


        public HashMap<String, Integer> getDocumentFrequency(String word) {
          return FinalMap.get(word.toLowerCase());
        }
      
        public List<String> getDocuments(String word) {
          HashMap<String, Integer> frequencyMap = getDocumentFrequency(word);
          if (frequencyMap == null) {
            return new ArrayList<>();
          }
          Set<String> documentSet = frequencyMap.keySet();
          return new ArrayList<>(documentSet);
        }

    public static void main(String[] args) {
        test index = new test();
        index.buildMap("docs", "words.txt");
        
        HashMap<String, Integer> frequencyMap = index.getDocumentFrequency("word");
        //System.out.println(frequencyMap);
        List<String> documents = index.getDocuments("word");
        System.out.println(documents);
      }

}
