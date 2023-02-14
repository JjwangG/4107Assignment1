<<<<<<< HEAD
import java.io.File;
import java.util.HashMap;
import java.util.List;

class TokenInfo {
  int df;
  List<TokenOccurrence> occList;

  public TokenInfo() {
    this.df = 0;
    this.occList = new ArrayList<>();
  }
}

class TokenOccurrence {
  String document;
  int tf;

  public TokenOccurrence(String document, int tf) {
    this.document = document;
    this.tf = tf;
  }
}

public class Indexing {
  public static void main(String[] args) {
    File inputDirectory = new File("input");
    HashMap<String, TokenInfo> H = new HashMap<>();

    for (File document : inputDirectory.listFiles()) {
      HashMap<String, Integer> V = createHashMapVector(document);

      for (String token : V.keySet()) {
        if (!H.containsKey(token)) {
          H.put(token, new TokenInfo());
        }

        TokenInfo tokenInfo = H.get(token);
        tokenInfo.df++;
        tokenInfo.occList.add(new TokenOccurrence(document.getName(), V.get(token)));
      }
    }

    for (String token : H.keySet()) {
      TokenInfo tokenInfo = H.get(token);
      tokenInfo.idf = Math.log(inputDirectory.listFiles().length / tokenInfo.df);
    }

    for (File document : inputDirectory.listFiles()) {
      HashMap<String, Integer> V = createHashMapVector(document);
      double vectorLength = 0;

      for (String token : V.keySet()) {
        TokenInfo tokenInfo = H.get(token);
        vectorLength += Math.pow(tokenInfo.idf * V.get(token), 2);
      }

      vectorLength = Math.sqrt(vectorLength);
      // Store the vector length for document in H or somewhere else if needed
    }
  }

  private static HashMap<String, Integer> createHashMapVector(File document) {
    // Implement the logic to create the hash map vector for a document
    // ...
    return V;
  }
}

=======
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Indexing {

    // HashMap< term, HashMap< docNum, frequency>>
    private static HashMap<String, HashMap<String, Double>> FinalMap = new HashMap<>();

    private static int booty = 0;
    

    public static void mapMaker(String dirDocs, String dirTokens) throws Exception{

      //arraylist with single document including all sub
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String[] tok;
        File folder = new File(dirDocs);
        File[] listOfFiles = folder.listFiles();

        //clean tokens file
        tok = cleanTokens(dirTokens);

        for (File file : listOfFiles) {
            if (file.isFile()) {
              //clean 1 file
              data = readFile(dirDocs+ "/" + file.getName());

              //put into build map to iterate through the single file
              buildMap(data.get(0), tok, data.get(1));
            }
        }
        System.out.println(booty);
        //System.out.println(FinalMap);
    }

    //input: filename of a single file ex.AP880121
    //output: arrayList that contains each subtext with just the text and head contents in lower case
    public static ArrayList<ArrayList<String>> readFile(String fileName) throws Exception{
      Preprocessing proc = new Preprocessing();
      String data = "";
      data = new String(Files.readAllBytes(Paths.get(fileName)));
      data = data.replaceAll("\n", " ").replaceAll("\r", " ");

      //arrayList containing each seperate sub text as strings
      ArrayList<String> documents = new ArrayList<>();

      Pattern docPattern = Pattern.compile("<DOC>(.*?)</DOC>");
      Matcher matcher = docPattern.matcher(data);

      while(matcher.find()){
        documents.add(matcher.group(1));
      }

      

      //arrayList containing each seperate sub text as strings
      ArrayList<String> subTexts = new ArrayList<>();

      //arrayList containing each seperate sub text's document number
      ArrayList<String> subNum = new ArrayList<>();

      Pattern p = Pattern.compile("<TEXT>(.*?)</TEXT>");
      Pattern p2 = Pattern.compile("<DOCNO>(.*?)</DOCNO>");
      Matcher matcher2;

      String temp = "";
      String temp2 = "";
      for(int i = 0; i<documents.size(); i++){
        matcher = p.matcher(documents.get(i));
        matcher2 = p2.matcher(documents.get(i));
        temp = documents.get(i); //whole subdoc -> needs to be split
        while(matcher2.find()){
            subNum.add(matcher2.group(1));
        }

        while(matcher.find()){
            temp = matcher.group(1).toLowerCase();
            temp = proc.removeStopWords(temp);
            temp = proc.removePunct(temp);
            temp2 += temp;
        }
        subTexts.add(temp2);
        temp2="";
      }

      ArrayList<ArrayList<String>> f = new ArrayList<ArrayList<String>>();
      f.add(subTexts);
      f.add(subNum);

      booty += subNum.size();
      
      return f;
  }
    

  public static String[] cleanTokens (String dir){
    File dirD = new File(dir);
    ArrayList<String> tokens = new ArrayList<String>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(dirD));
      String line;
      
      while ((line = br.readLine()) != null) {
        tokens.add(line);
      }
      br.close();  
      
    } catch (IOException e) {
      System.err.println("An error occurred while reading the file: " + e.getMessage());
    }

    Object[] arr = tokens.toArray();
    String[] t = Arrays.copyOf(arr, arr.length, String[].class);
    
    return t;
  }


    //input: stringArray is one text from a document with each word seperated
    //output: HashMap< docNum, frequency>
    public static void buildMap (ArrayList<String> subDoc, String[] tokens , ArrayList<String> fileNames){
      int counter = 0;
      for (String word : tokens) {
        for (int i = 0; i < subDoc.size() ; i++) {
            
            counter = subDoc.get(i).split((" "+word+" "), -1).length-1;
            if (counter != 0){
              //check if token is already in FinalMap
              if (FinalMap == null || !FinalMap.containsKey(word)){
                //add new word with hm(filename, freq) to the FinalMap
                HashMap<String, Double> singlFre = new HashMap<>();
                singlFre.put(fileNames.get(i) ,  ((double)counter/(double)(subDoc.get(i).split(" ").length)));
                ///(subDoc.get(i).split(" ").length))
                FinalMap.put(word, singlFre);
              } else {
              //add on hm(filename, freq) to existing token entry
                  FinalMap.get(word).put(fileNames.get(i), ((double)counter/(double)(subDoc.get(i).split(" ").length)));
              }
              counter = 0;
          }
        }

      }   
    }

    //Final inverted index hash map getter
    public HashMap<String, HashMap<String, Double>> getInvertedIndex(){
      return FinalMap;
    }
      
    public static void main(String[] args) throws Exception {
        mapMaker("docs", "tokens.txt");

        try {
          BufferedWriter writerObj = new BufferedWriter(new FileWriter("invertedIndex.txt", false));
          writerObj.write(FinalMap.toString());
          writerObj.close();
          System.out.println("================================\n"
                  + "Inverted Index successfully generated");
      } catch (IOException e) {
          e.printStackTrace();
      }
      }

}
>>>>>>> 0c013a12e1f8c521f8999752070862eb036fdcc2
