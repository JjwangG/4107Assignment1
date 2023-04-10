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
        
        //Base Results 
        Retrieval baseR = new Retrieval();
        baseR.main(inverted_index, docNum);
        
        //Expanded Queries Results
        QERetrieval qeR = new QERetrieval();
        qeR.main(inverted_index, docNum);

        //BERT Results
        Retrieval_Bert bertR = new Retrieval_Bert();
        bertR.main(inverted_index, docNum);





    }

}