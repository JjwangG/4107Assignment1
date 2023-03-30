import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
class Preprocessing{

    public static void main(String[]args) throws Exception{ 
        long startTime = System.nanoTime();
        String data = readAllFiles();
        data = removePunct(data);
       
        String[] tokens = tokenize(data);
        tokens = stopWords(tokens).toArray(String[]::new);
        //data = removeStopWords(data);
        
        try {
            BufferedWriter writerObj = new BufferedWriter(new FileWriter("tokens1.txt", false));
            for (String string : tokens) {
                writerObj.write(string);
                writerObj.newLine();
            }
            writerObj.close();
            System.out.println("================================\n"
                    + "Tokens successfully generated");
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.println("Total time: "+totalTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //returns a string with just the text and head contents in lower case, 
    public static String readFile(String fileName) throws Exception{
        String data = "";
        String text = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        data = data.replaceAll("\n", " ").replaceAll("\r", " ");

        Pattern p = Pattern.compile("<TEXT>(.*?)</TEXT>");
        Matcher matcher = p.matcher(data);

        while(matcher.find()){
            text += (matcher.group(1) + "\n");
        }

        Pattern p2 = Pattern.compile("<HEAD>(.*?)</HEAD>");
        Matcher matcher2 = p2.matcher(data);

        while(matcher2.find()){
            text += (matcher2.group(1) + "\n");
        }

        return text.toLowerCase();
    }

    //input: string of all the text
    //output: all the punctuation removed
    public static String removePunct(String data) {
        //replace all the punctuation except for $, hypens, and decimal points
        //
        Pattern p = Pattern.compile("[\\p{Punct}&&[^-/$a-z0-9\\d+\\.\\d+\\s]]+");
        Pattern p2 = Pattern.compile("((?<![0-9])\\.)|(\\.(?![0-9]))");
        //[^-/$a-z0-9\\d+\\.\\d+\\s]"
        
        data = p.matcher(data).replaceAll("");
        data = p2.matcher(data).replaceAll("");
        return data;
    }

    //input: string of text
    //output: array of all the tokens 
    public static String[] tokenize(String data){
        String[] arr = data.split("\\s+");
        String[]unique = Arrays.stream(arr).distinct().toArray(String[]::new);
        return unique;
    }

    //input: string array of all the tokens
    //output: string arr with all the stop words removed
    public static ArrayList<String> stopWords(String[] data) throws Exception{
        ArrayList<String> stopArray = initialiseArray();
        ArrayList<String> tokens = new ArrayList<String>();

        for (String word : data){
            if (!(stopArray.contains(word))){
                tokens.add(word);
            }
        }

        return tokens;
    } 

    //initializes the stopwords array
    private static ArrayList<String> initialiseArray() throws Exception{
        ArrayList<String> arr = new ArrayList<String>();
        
        BufferedReader br = new BufferedReader(new FileReader(new File("ss.txt")));
        String line;
        while ((line = br.readLine()) != null) {
            arr.add(line);
        }
        br.close();
        return arr;
    }

    //parses through all the documents in the doc folder
    private static String readAllFiles() throws Exception{
        String path = "docs";
        String data = "";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                data += readFile(path+ "/" + file.getName());
                //System.out.println(file.getName());
            }
        }
        return data;
    }
}