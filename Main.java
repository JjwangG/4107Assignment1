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
        

        Retrieval2 r = new Retrieval2();

        r.main(inverted_index, docNum);





    }

}