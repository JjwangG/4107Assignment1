import java.util.*;

class Main{

    public static void main(String[] args) throws Exception{

        // Preprocessing
        Preprocessing p = new Preprocessing();

        p.main();

        Indexing i = new Indexing();

        i.main();
        HashMap<String, HashMap<String, Double>> inverted_index = new HashMap<>();
        int docNum = i.getNumDocs();

        inverted_index = i.getInvertedIndex();
        
        Retrieval_Bert r = new Retrieval_Bert();

        r.main(inverted_index, docNum);





    }

}