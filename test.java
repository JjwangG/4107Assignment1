import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    // HashMap< term, HashMap< HashMap< docNum, occurence>, idf>>
    private HashMap<String, HashMap<String, Integer>> FinalMap;

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
                while ((indWord = br.readLine()) != null){
                    //System.out.println(indWord);
                    while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        word = word.toLowerCase();
                        System.out.println(word);
                        System.out.println("HELOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                        /*HashMap<String, Integer> frequencyMap = FinalMap.get(word);
                        if (frequencyMap == null) {
                        frequencyMap = new HashMap<>();
                        FinalMap.put(word, frequencyMap);
                        }
                        Integer frequency = frequencyMap.get(file.getName());
                        if (frequency == null) {
                        frequency = 0;
                        }
                        frequencyMap.put(file.getName(), frequency + 1);
                    */}
                    }
                    
                    
                }
                br.close();
                
              } catch (IOException e) {
                System.err.println("An error occurred while reading the file: " + e.getMessage());
              }
            }
        }

    public static void main(String[] args) {
        test index = new test();
        index.buildMap("docs", "words.txt");
        
        //Map<String, List> frequencyMap = index.getDocumentFrequency("word");
        //System.out.println(frequencyMap);
        //List<String> documents = index.getDocuments("word");
        //System.out.println(documents);
      }

}
