import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
class Preprocessing{

    private static String [] stopArray; 
    public static void main(String[] args) throws Exception{ 
        
        String data = readAllFiles();
        //System.out.print(data);

        initialiseArray();
        
        
        data = removeStopWords(data);
        data = removePunct(data);
        String[] tokens = tokenize(data);
         
        try {
            BufferedWriter writerObj = new BufferedWriter(new FileWriter("tokens.txt", false));
            for (String string : tokens) {
                writerObj.write(string);
                writerObj.newLine();
            }
            writerObj.close();
            System.out.println("================================\n"
                    + "Tokens successfully generated");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(duplicates(tokens));
        /* 
        for (String str : tokens) {
            System.out.println(str);
        }*/
        
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
    private static String removePunct(String data) {
        //replace all the punctuation except for $, hypens, and decimal points
        data = data.replaceAll("[^-$a-z0-9\\d+\\.\\d+\\s]", "").replaceAll("(?!\\d)\\.(?!\\d)", "").replaceAll("\\s+", " ");
        return data;
    }

    //input: string of text
    //output: array of all the tokens 
    private static String[] tokenize(String data){
        String[] arr = data.split("\\s+");
        String[]unique = Arrays.stream(arr).distinct().toArray(String[]::new);
        return unique;
    }

    //input: string of all the texts
    //output: string of the text with all the stop words removed
    private static String removeStopWords(String data){
        for (String word : stopArray) {
            data = data.replace((" "+ word+" "), " ");
        }

        return data;
    }

    private static void initialiseArray() throws Exception{
        String data = "";
        data = new String(Files.readAllBytes(Paths.get("/Users/joannawang/Projects/4107Assignment1/ss.txt")));
        data = data.replace("\n", " ").replace("\r", " ");
        stopArray = data.split(" ");
    }

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