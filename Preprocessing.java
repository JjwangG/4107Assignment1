import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
class Preprocessing{
    public static void main(String[] args) throws Exception{ 
        
        String data = readFile("/Users/joannawang/Projects/4107Assignment1/docs/AP880212");
        System.out.println(data);
    }

    public static String readFile(String fileName) throws Exception{
        String data = "";
        String text = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        data = data.replace("\n", "").replace("\r", "");

        Pattern p = Pattern.compile("<TEXT>(.*?)</TEXT>");
        Matcher matcher = p.matcher(data);

        while(matcher.find()){
            text += (matcher.group(1) + "\n");
        }

        return text;
    }

    /*
     * classes for tokenization, stop word removal, stemming (not necessary)
     * add stop words
     * hm

     * 

public class Main {
  public static void main(String[] args) {
    String input = "cat AAA cat dfef frfs cat fdfrfef cat";
    
    String regex = "cat (.*?) cat";
    
    Pattern p = Pattern.compile(regex);
    Matcher matcher = p.matcher(input);
    
    while(matcher.find()){
    	System.out.println(matcher.group(1));
    }
    
  }
}

     */
}