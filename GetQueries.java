import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
public class GetQueries {
    public static void main() throws Exception{
        String[] queries = readFile("queries.txt");

        try {
            BufferedWriter writerObj = new BufferedWriter(new FileWriter("cleaned_queries.txt", false));
            for (String string : queries) {
                writerObj.write(string);
                writerObj.newLine();
            }
            writerObj.close();
            System.out.println("================================\n"
                    + "Queries successfully outputted");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //^^ this is the array of all the queries :)
    }

    public static String[] readFile(String fileName) throws Exception{
        String data = "";
        String[] text;
        ArrayList<String> qTitle = new ArrayList<String>();
        ArrayList<String> qDesc = new ArrayList<String>();
        ArrayList<String> qText = new ArrayList<String>();

        data = new String(Files.readAllBytes(Paths.get(fileName)));
        data = data.replaceAll("\n", " ").replaceAll("\r", " ");

        Pattern p1 = Pattern.compile("<title>(.*?)<desc>");
        Pattern p2 = Pattern.compile("<desc>(.*?)<narr>");
        Pattern p3 = Pattern.compile("<narr>(.*?)</top>");
        
        Matcher matcher = p1.matcher(data);
        while(matcher.find()){
            qTitle.add(matcher.group(1));
        }

        matcher = p2.matcher(data);
        while(matcher.find()){
            qDesc.add(matcher.group(1));
        }

        matcher = p3.matcher(data);
        while(matcher.find()){
            qText.add(matcher.group(1));
        }

        text = mergeList(qTitle, qDesc, qText);
        return text;
    }

    private static String[] mergeList(ArrayList<String> q1, ArrayList<String> q2 , ArrayList<String>q3) throws Exception{
        String queries = "";
        ArrayList<String> list = new ArrayList<String>();
        String[]arr;
        Preprocessing p = new Preprocessing();
        for (int i = 0; i < q1.size(); i++){
            queries = q1.get(i) + " " + q2.get(i) + " " + q3.get(i);
            queries = p.removePunct(queries.toLowerCase());
            
            queries = (p.removeStopWords(queries.split(" "))).stream().map(Object::toString).collect(Collectors.joining(" "));
            //queries = "";
            
            //String[] tokens = tokenize(data);
       // tokens = removeStopWords(tokens).toArray(String[]::new);
            list.add(queries);
        }
        
        Object[] obj = list.toArray();
        String[] str = Arrays.copyOf(obj, obj.length, String[].class);
        //arr = p.tokenize(queries);

        return str;
    }

    

}