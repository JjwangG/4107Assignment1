import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
class Preprocessing{

    private static String [] stopArray; 
    public static void main(String[] args) throws Exception{ 
        
        String data = readFile("/Users/joannawang/Projects/4107Assignment1/docs/AP880212");
        
        initialiseArray();
        
        data = removeStopWords(data);
        //System.out.print(data);

        /*TO DO 
         * remove punctuation
         * maybe have to deal with capitals -> I thinkn all lowercase shouldn't matter?
         * pretty much done
         */

        /* 
        String[] tokens = tokenize(data);

        
        for (String str : tokens) {
            System.out.println(str);
        }*/
        
    }

    //returns a string with just the text and head
    public static String readFile(String fileName) throws Exception{
        String data = "";
        String text = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        data = data.replace("\n", " ").replace("\r", " ");

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

        return text;
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
        String data2 = "";
        data = new String(Files.readAllBytes(Paths.get("/Users/joannawang/Projects/4107Assignment1/ss.txt")));
        data = data.replace("\n", " ").replace("\r", " ");
        stopArray = data.split(" ");
    }

    /*
     * classes for tokenization, stop word removal, stemming (not necessary)
     * add stop words
     * hm

     *
     */
}